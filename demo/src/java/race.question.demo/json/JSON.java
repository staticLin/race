package race.question.demo.json;

/**
 * @author linyh
 */
public class JSON {

    public static String toJSONString(Object object) throws Exception {

        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out, SerializeConfig.globalInstance);

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }
}
