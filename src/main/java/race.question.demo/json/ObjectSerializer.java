package race.question.demo.json;

/**
 * @author linyh
 */
public interface ObjectSerializer {

    /**
     * 序列化操作
     * @param serializer
     * @param object
     * @throws Exception
     */
    void write(JSONSerializer serializer, Object object) throws Exception;
}
