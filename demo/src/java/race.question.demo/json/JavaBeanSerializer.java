package race.question.demo.json;

/**
 * @author linyh
 */
public class JavaBeanSerializer implements ObjectSerializer {

    public JavaBeanSerializer() {
    }

    public JavaBeanSerializer(SerializeBeanInfo beanInfo) {
    }

    @Override
    public void write(JSONSerializer serializer, Object object) throws Exception {
        throw new UnsupportedOperationException();
    }
}
