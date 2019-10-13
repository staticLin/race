package race.question.demo.json;

/**
 * @author linyh
 */
public interface ObjectSerializer {

    /**
     * 序列化操作
     * @param writer
     * @param object
     * @throws Exception
     */
    void write(SerializeWriter writer, Object object) throws Exception;
}
