package race.question.demo.json;

/**
 * @author linyh
 */
public class JavaBeanSerializer implements ObjectSerializer {

    private SerializeBeanInfo beanInfo;

    public JavaBeanSerializer() {
    }

    public JavaBeanSerializer(SerializeBeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }

    @Override
    public void write(JSONSerializer serializer, Object object) throws Exception {

        FieldInfo[] fields = beanInfo.fields;

        serializer.out.preSymbol = '{';

        for (FieldInfo getter : fields) {
            Object value = getter.method.invoke(object);
            serializer.out.writeObject(getter.fieldName, value);
        }
        serializer.out.end();
    }
}
