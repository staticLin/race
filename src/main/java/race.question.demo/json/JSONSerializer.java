package race.question.demo.json;

/**
 * @author linyh
 */
public class JSONSerializer {

    /**
     * StringBuilder
     */
    public final transient SerializeWriter out;

    /**
     * 配置属性类
     */
    protected final transient SerializeConfig config;

    /**
     * 初始化一个StringBuilder
     * @param out
     * @param config
     */
    public JSONSerializer(final SerializeWriter out, final SerializeConfig config) {
        this.out = out;
        this.config = config;
    }

    /**
     * 写一个对象到StringBuilder
     * @param object
     * @throws Exception
     */
    public void write(final Object object) throws Exception {

        if (object == null) {
            out.writeNull();
            return;
        }

        final Class<?> clazz = object.getClass();
        final ObjectSerializer writer = config.getObjectWriter(clazz);

        writer.write(this, object);
    }
}
