package race.question.demo.json;

import java.lang.reflect.Method;

/**
 * @author linyh
 */
public class FieldInfo {

    /**
     * 方法对象
     */
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

    /**
     * 序列化元信息
     * @param primitive
     * @param returnType
     * @param methodName
     * @param fieldName
     * @param method
     */
    public FieldInfo(final String primitive,
                     final String returnType,
                     final String methodName,
                     final String fieldName,
                     final Method method) {

        this.primitive = primitive;
        this.returnType = returnType;
        this.methodName = methodName;
        this.fieldName = fieldName;
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(final Method method) {
        this.method = method;
    }

    public String getPrimitive() {
        return primitive;
    }

    public void setPrimitive(final String primitive) {
        this.primitive = primitive;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(final String returnType) {
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(final String methodName) {
        this.methodName = methodName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }
}
