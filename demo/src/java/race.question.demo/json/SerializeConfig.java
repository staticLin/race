package race.question.demo.json;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import race.question.demo.json.serializer.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * @author linyh
 */
public class SerializeConfig {

    private final AtomicLong seed = new AtomicLong();

    public final static SerializeConfig globalInstance = new SerializeConfig();

    private final ConcurrentHashMap<Class, ObjectSerializer> mixInSerializers;
    private final String javaBeanSerializer;
    private final String JSONSerializer;
    private final String classPath;
    private final String SerializeWriter;
    private final String packageName;

    public SerializeConfig() {

        Package pkg = this.getClass().getPackage();

        packageName = pkg.getName();
        classPath = packageName.replace('.', '/') + "/";

        javaBeanSerializer = classPath + "JavaBeanSerializer";
        JSONSerializer = classPath + "JSONSerializer";
        SerializeWriter = classPath + "SerializeWriter";

        // TODO 初始化容量？
        this.mixInSerializers = new ConcurrentHashMap<>();
        initSerialize();
    }

    private void initSerialize() {

        this.mixInSerializers.put(int[].class, ArraySerializer.instance);
        this.mixInSerializers.put(char[].class, ArraySerializer.instance);
        this.mixInSerializers.put(short[].class, ArraySerializer.instance);
        this.mixInSerializers.put(byte[].class, ArraySerializer.instance);
        this.mixInSerializers.put(long[].class, ArraySerializer.instance);
        this.mixInSerializers.put(double[].class, ArraySerializer.instance);
        this.mixInSerializers.put(boolean[].class, ArraySerializer.instance);
        this.mixInSerializers.put(float[].class, ArraySerializer.instance);
        this.mixInSerializers.put(Object[].class, ArraySerializer.instance);

        this.mixInSerializers.put(Collection.class, CollectionSerializer.instance);
        this.mixInSerializers.put(Map.class, MapSerializer.instance);

        this.mixInSerializers.put(Enum.class, EnumSerializer.instance);
        this.mixInSerializers.put(String.class, StringSerializer.instance);
        this.mixInSerializers.put(Date.class, StringSerializer.instance);
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

        if (clazz.isEnum())
            return get(Enum.class);
        else if (Map.class.isAssignableFrom(clazz))
            return get(Map.class);
        else if (isPrimitive(clazz))
            return get(String.class);
        else if (Collection.class.isAssignableFrom(clazz))
            return get(Collection.class);

        ObjectSerializer writer = get(clazz);

        if (writer == null) {
            writer = createJavaBeanSerializer(buildBeanInfo(clazz));
            put(clazz, writer);
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
        beanInfo.setBeanType(clazz);

        // FieldInfo[] -> field
        Method[] methods = clazz.getMethods();
        List<FieldInfo> fieldInfos = new ArrayList<>(methods.length >> 1);

        for (Method method : methods) {

            String methodName = method.getName();
            String propertyName;

            if (methodName.startsWith("get")) {

                if (methodName.length() < 4) {
                    continue;
                }
                if (methodName.equals("getClass")) {
                    continue;
                }
                if (methodName.equals("getDeclaringClass") && clazz.isEnum()) {
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
            FieldInfo fieldInfo = new FieldInfo(primitive, returnType, methodName, propertyName);
            fieldInfos.add(fieldInfo);
        }

        if (!fieldInfos.isEmpty()) {
            FieldInfo[] fields = new FieldInfo[fieldInfos.size()];
            fieldInfos.toArray(fields);
            beanInfo.setFields(fields);
        }

        return beanInfo;
    }

    public ObjectSerializer createJavaBeanSerializer(SerializeBeanInfo beanInfo) {

        try {
            ObjectSerializer asmSerializer = createASMSerializer(beanInfo);
            if (asmSerializer != null) {
                return asmSerializer;
            }
        } catch (Exception ignore) {
            // ignore
        }

        return new JavaBeanSerializer(beanInfo);
    }

    public JavaBeanSerializer createASMSerializer(SerializeBeanInfo beanInfo) throws Exception {
        Class<?> clazz = beanInfo.beanType;
        if (clazz.isPrimitive()) {
            throw new Exception("unsupportd class " + clazz.getName());
        }

        FieldInfo[] getters = beanInfo.fields;

        if (getters.length > 256) {
            return new JavaBeanSerializer(beanInfo);
        }

        String className = "ASMSerializer_" + seed.incrementAndGet() + "_" + clazz.getSimpleName();
        String fullClassName;
        String defineClassName = packageName + "." + className;
        String jsonObjectClassName = clazz.getName().replace('.', '/');

        fullClassName = classPath + className;

        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, fullClassName, null, classPath + "JavaBeanSerializer", null);

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

            mv = cw.visitMethod(ACC_PUBLIC, "write", "(L" + JSONSerializer + ";Ljava/lang/Object;)V", null, new String[]{"java/lang/Exception"});
            mv.visitCode();

            mv.visitVarInsn(ALOAD, 1);
            mv.visitFieldInsn(GETFIELD, JSONSerializer, "out", "L" + SerializeWriter + ";");
            mv.visitVarInsn(ASTORE, 3);

            mv.visitVarInsn(ALOAD, 3);
            mv.visitIntInsn(BIPUSH, 123);
            mv.visitFieldInsn(PUTFIELD, SerializeWriter, "preSymbol", "C");

            mv.visitVarInsn(ALOAD, 2);
            mv.visitTypeInsn(CHECKCAST, jsonObjectClassName);
            mv.visitVarInsn(ASTORE, 4);

            int index = 6;

            for (int i = 0; i < getters.length; i++) {

                FieldInfo getter = getters[i];
                mv.visitLdcInsn(getter.getFieldName());
                mv.visitVarInsn(ASTORE, 5);

                mv.visitVarInsn(ALOAD, 4);
                switch (getter.primitive) {
                    case "int":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()I", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                        break;
                    case "byte":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()B", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                        break;
                    case "short":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()S", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                        break;
                    case "boolean":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()Z", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                        break;
                    case "float":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()F", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                        break;
                    case "double":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()D", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                        break;
                    case "long":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()J", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                        break;
                    case "char":
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), "()C", false);
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                        break;
                    default:
                        mv.visitMethodInsn(INVOKEVIRTUAL, jsonObjectClassName, getter.getMethodName(), getter.returnType, false);
                        break;
                }

                mv.visitVarInsn(ASTORE, index);

                mv.visitVarInsn(ALOAD, 3);
                mv.visitVarInsn(ALOAD, 5);
                mv.visitVarInsn(ALOAD, index++);
                mv.visitMethodInsn(INVOKEVIRTUAL, SerializeWriter, "writeObject", "(Ljava/lang/String;Ljava/lang/Object;)V", false);
            }
            mv.visitVarInsn(ALOAD, 3);
            mv.visitMethodInsn(INVOKEVIRTUAL, SerializeWriter, "end", "()V", false);
            mv.visitInsn(RETURN);

            mv.visitMaxs(4, index);
            mv.visitEnd();
        }
        cw.visitEnd();

        byte[] code = cw.toByteArray();

        ASMClassLoader classLoader = ASMClassLoader.instance;
        Class<?> serializerClass = classLoader.defineClassPublic(defineClassName, code, 0, code.length);

        Constructor<?> constructor = serializerClass.getConstructor();
        Object instance = constructor.newInstance();

        return (JavaBeanSerializer) instance;
    }

    public static class ASMClassLoader extends ClassLoader {

        public static final ASMClassLoader instance = new ASMClassLoader();

        public ASMClassLoader() {
            super(getParentClassLoader());
        }

        private static ClassLoader getParentClassLoader() {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            if (classLoader == null) classLoader = JSON.class.getClassLoader();

            return classLoader;
        }

        public Class<?> defineClassPublic(String name, byte[] b, int off, int len) throws ClassFormatError {
            return defineClass(name, b, off, len);
        }
    }
}

