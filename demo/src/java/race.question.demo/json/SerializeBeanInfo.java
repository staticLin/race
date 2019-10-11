package race.question.demo.json;

/**
 * @author linyh
 */
class SerializeBeanInfo {

    Class<?> beanType;
    FieldInfo[] fields;

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(Class<?> beanType) {
        this.beanType = beanType;
    }

    public FieldInfo[] getFields() {
        return fields;
    }

    public void setFields(FieldInfo[] fields) {
        this.fields = fields;
    }
}
