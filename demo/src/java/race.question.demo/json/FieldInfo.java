package race.question.demo.json;

import java.lang.reflect.Method;

/**
 * @author linyh
 */
public class FieldInfo {

    public FieldInfo(String primitive, String returnType, String methodName, String fieldName, Method method) {
        this.primitive = primitive;
        this.returnType = returnType;
        this.methodName = methodName;
        this.fieldName = fieldName;
        this.method = method;
    }

    public Method method;

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

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getPrimitive() {
        return primitive;
    }

    public void setPrimitive(String primitive) {
        this.primitive = primitive;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
