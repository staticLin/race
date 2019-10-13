package race.question.demo.json;

/**
 * @author linyh
 */
class SerializeBeanInfo {

    /**
     * 被序列化的Class类型
     */
    Class<?> beanType;

    /**
     * 元信息
     */
    FieldInfo[] fields;

    /**
     *
     */
    public SerializeBeanInfo() {
        //
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public void setBeanType(final Class<?> beanType) {
        this.beanType = beanType;
    }

    public FieldInfo[] getFields() {
        return new FieldInfo[]{};
    }

    public void setFields(final FieldInfo[] fields) {
        // 不会用到
    }
}
