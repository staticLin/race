package race.question.demo.json;

/**
 * @author linyh
 */
public interface ObjectSerializer {

    void write(JSONSerializer serializer, Object object) throws Exception;
}
