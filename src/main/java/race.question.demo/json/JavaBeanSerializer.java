package race.question.demo.json;

/**
 * @author linyh
 */
public class JavaBeanSerializer implements ObjectSerializer {

    /**
     * 序列化元信息
     */
    private transient SerializeBeanInfo beanInfo;

    /**
     * asm构造的类为其子类，需要使用此构造器进行初始化
     * @see SerializeConfig
     */
    public JavaBeanSerializer() {

    }

    /**
     * 基本反射方式的构造器
     *
     * @param beanInfo
     */
    public JavaBeanSerializer(final SerializeBeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }

    @Override
    public void write(final JSONSerializer serializer, final Object object) throws Exception {

        final FieldInfo[] fields = beanInfo.fields;

        serializer.out.preSymbol = '{';

        for (final FieldInfo getter : fields) {
            final Object value = getter.method.invoke(object);
            serializer.out.writeObject(getter.fieldName, value);
        }
        serializer.out.end();
    }
}
