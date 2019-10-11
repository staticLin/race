package race.question.demo.json;

/**
 * @author linyh
 */
public class JsonUtil {

    public static String toJSONString(Object object) throws Exception {

        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out, SerializeConfig.GLOBAL_INSTANCE);

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }
}
