package race.question.demo.json;

/**
 * @author linyh
 */
public class FieldInfo {
//    public Field field;
//    public Method method;
//    public Class<String> fieldClass;

    public FieldInfo() {

    }

    public FieldInfo(String primitive, String returnType, String methodName, String fieldName) {
        this.primitive = primitive;
        this.returnType = returnType;
        this.methodName = methodName;
        this.fieldName = fieldName;
    }

    /**
     * char int long double float byte short boolean
     */
    public String primitive;

    /**
     * 如非原始类型，需要初始化一个返回类型
     * 例如:
     * getClass().getName().replace('.', '/') + ";";
     */
    public String returnType;

    /**
     * getAge
     */
    public String methodName;

    /**
     * age
     */
    public String fieldName;

    public void setPrimitive(String primitive) {
        this.primitive = primitive;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getMethodName() {
        // getAge
        return this.methodName;
    }

    public Object getFieldName() {
        // age
        return this.fieldName;
    }
}
