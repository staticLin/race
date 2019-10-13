package race.question.demo.json;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import race.question.demo.json.serializer.ArraySerializer;
import race.question.demo.json.serializer.CollectionSerializer;
import race.question.demo.json.serializer.EnumSerializer;
import race.question.demo.json.serializer.MapSerializer;
import race.question.demo.json.serializer.StringSerializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * @author linyh
 */
public class SerializeConfig {

    private final transient AtomicLong seed = new AtomicLong();

    public final static SerializeConfig GLOBAL_INSTANCE = new SerializeConfig();

    private final Map<Class, ObjectSerializer> mixInSerializers;
    private final String javaBeanSerializer;
    private final String classPath;
    private final String serializeWriter;
    private final String packageName;

    public SerializeConfig() {

        Package pkg = this.getClass().getPackage();

        packageName = pkg.getName();
        classPath = packageName.replace('.', '/') + "/";

        javaBeanSerializer = classPath + "JavaBeanSerializer";
        serializeWriter = classPath + "SerializeWriter";

        this.mixInSerializers = new HashMap<>();
        initSerialize();
    }

    private void initSerialize() {

        this.mixInSerializers.put(int[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(char[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(short[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(byte[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(long[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(double[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(boolean[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(float[].class, ArraySerializer.INSTANCE);
        this.mixInSerializers.put(Object[].class, ArraySerializer.INSTANCE);

        this.mixInSerializers.put(Collection.class, CollectionSerializer.INSTANCE);
        this.mixInSerializers.put(Map.class, MapSerializer.INSTANCE);

        this.mixInSerializers.put(Enum.class, EnumSerializer.INSTANCE);
        this.mixInSerializers.put(String.class, StringSerializer.INSTANCE);
        this.mixInSerializers.put(Date.class, StringSerializer.INSTANCE);
    }

    private ObjectSerializer get(Class type) {
        return mixInSerializers.get(type);
    }

    private void put(Class type, ObjectSerializer value) {
        if (!mixInSerializers.containsKey(type)) {
            mixInSerializers.putIfAbsent(type, value);
        }
    }

    public ObjectSerializer getObjectWriter(Class<?> clazz) {

        ObjectSerializer writer;

        if (clazz.isEnum()) {
            writer = get(Enum.class);
        } else if (Map.class.isAssignableFrom(clazz)) {
            writer = get(Map.class);
        } else if (isPrimitive(clazz)) {
            writer = get(String.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            writer = get(Collection.class);
        } else {
            writer = get(clazz);

            if (writer == null) {
                writer = createJavaBeanSerializer(buildBeanInfo(clazz));
                put(clazz, writer);
            }
        }

        return writer;
    }

    private boolean isPrimitive(Class<?> aClass) {

        return Number.class.isAssignableFrom(aClass)
                || Boolean.class.isAssignableFrom(aClass)
                || Character.class.isAssignableFrom(aClass);
    }

    public SerializeBeanInfo buildBeanInfo(Class<?> clazz) {

        SerializeBeanInfo beanInfo = new SerializeBeanInfo();

        // beanType
        beanInfo.beanType = clazz;

        // FieldInfo[] -> field
        Method[] methods = clazz.getMethods();

        List<FieldInfo> fieldInfos = new ArrayList<>();

        for (Method method : methods) {

            String methodName = method.getName();
            String propertyName;

            if (methodName.startsWith("get")) {

                if (methodName.length() < 4) {
                    continue;
                }
                if ("getClass".equals(methodName)) {
                    continue;
                }
                propertyName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
            } else if (methodName.startsWith("is")) {
                if (methodName.length() < 3) {
                    continue;
                }
                if (method.getReturnType() != Boolean.TYPE
                        && method.getReturnType() != Boolean.class) {
                    continue;
                }
                propertyName = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
            } else {
                continue;
            }

            Class type = method.getReturnType();

            String primitive;
            String returnType;

            if (type.isPrimitive()) {

                primitive = type.getName();
                returnType = "";
            } else if (type.isArray()) {
                //"()" + byte[].class.getName().replace('.', '/')
                primitive = "non";
                returnType = "()" + type.getName().replace('.', '/');
            } else {
                //Role.class.getName().replace('.', '/') + ";")
                primitive = "non";
                returnType = "()L" + type.getName().replace('.', '/') + ";";
            }

            // 是否原生类型
            // 返回类型（非原生）
            // 方法名 getAge
            // 字段名 age
            FieldInfo fieldInfo = new FieldInfo(primitive, returnType, methodName, propertyName, method);
            fieldInfos.add(fieldInfo);
        }

        if (!fieldInfos.isEmpty()) {
            FieldInfo[] fields = new FieldInfo[fieldInfos.size()];
            fieldInfos.toArray(fields);
            beanInfo.fields = fields;
        }

        return beanInfo;
    }

    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {

        ObjectSerializer asmSerializer;

        try {
            asmSerializer = createASMSerializer(beanInfo);

        } catch (Exception ignore) {
            asmSerializer = new JavaBeanSerializer(beanInfo);
        }

        if (asmSerializer == null) {
            asmSerializer = new JavaBeanSerializer(beanInfo);
        }

        return asmSerializer;
    }

    public JavaBeanSerializer createASMSerializer(SerializeBeanInfo beanInfo) throws Exception {
        Class<?> clazz = beanInfo.beanType;

        FieldInfo[] getters = beanInfo.fields;

        String className = "ASMSerializer_" + seed.incrementAndGet() + "_" + clazz.getSimpleName();
        String fullClassName;
        String defineClassName = packageName + "." + className;
        String jsonObjectClassName = clazz.getName().replace('.', '/');

        fullClassName = classPath + className;

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, fullClassName, null, javaBeanSerializer, null);

        {
            mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, javaBeanSerializer, "<init>", "()V", false);
            mv.visitInsn(RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        {
            mv = cw.visitMethod(ACC_PUBLIC, "write", "(L" + serializeWriter + ";Ljava/lang/Object;)V", null, new String[]{"java/lang/Exception"});
            mv.visitCode();

            mv.visitVarInsn(ALOAD, 1);
            mv.visitIntInsn(BIPUSH, 123);
            mv.visitFieldInsn(PUTFIELD, serializeWriter, "preSymbol", "C");

            mv.visitVarInsn(ALOAD, 2);
            mv.visitTypeInsn(CHECKCAST, jsonObjectClassName);
            mv.visitVarInsn(ASTORE, 3);

            int index = 5;

            for (FieldInfo getter : getters) {

                mv.visitLdcInsn(getter.fieldName);
                mv.visitVarInsn(ASTORE, 4);

                mv.visitVarInsn(ALOAD, 3);
                switch (getter.primitive) {
                    case "int":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()I", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                        break;
                    case "byte":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()B", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                        break;
                    case "short":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()S", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                        break;
                    case "boolean":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()Z", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                        break;
                    case "float":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()F", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                        break;
                    case "double":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()D", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                        break;
                    case "long":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()J", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                        break;
                    case "char":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, "()C", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                        break;
                    default:
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.methodName, getter.returnType, false);
                        break;
                }

                mv.visitVarInsn(ASTORE, index);

                mv.visitVarInsn(ALOAD, 1);
                mv.visitVarInsn(ALOAD, 4);
                mv.visitVarInsn(ALOAD, index++);
                mv.visitMethodInsn(INVOKEVIRTUAL, serializeWriter, "writeObject", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
            }
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEVIRTUAL, serializeWriter, "end", "()V", false);
            mv.visitInsn(RETURN);

            mv.visitMaxs(4, index);
            mv.visitEnd();
        }
        cw.visitEnd();

        byte[] code = cw.toByteArray();

        AsmClassLoader classLoader = AsmClassLoader.INSTANCE;
        Class<?> serializerClass = classLoader.defineClassPublic(defineClassName, code, 0, code.length);

        Constructor<?> constructor = serializerClass.getConstructor();

        return (JavaBeanSerializer) constructor.newInstance();
    }

    public static class AsmClassLoader extends ClassLoader {

        private static final AsmClassLoader INSTANCE = AccessController.doPrivileged(
                (PrivilegedAction<AsmClassLoader>) AsmClassLoader::new);

        public AsmClassLoader() {
            super(getParentClassLoader());
        }

        private static ClassLoader getParentClassLoader() {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            if (classLoader == null) {
                classLoader = JsonUtil.class.getClassLoader();
            }

            return classLoader;
        }

        public Class<?> defineClassPublic(String name, byte[] b, int off, int len) throws ClassFormatError {
            return defineClass(name, b, off, len);
        }
    }
}

