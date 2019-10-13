package race.question.demo.json;

/**
 * @author linyh
 */
public final class JsonUtil {

    private JsonUtil(){

    }

    /**
     * 序列化对象为Json字符串
     * @param object
     * @return
     * @throws Exception
     */
    public static String toJSONString(final Object object) throws Exception {

        final SerializeWriter out = new SerializeWriter();

        try {
            final JSONSerializer serializer = new JSONSerializer(out, SerializeConfig.GLOBAL_INSTANCE);

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }
}
