package race.question.demo.json;


/**
 * @author linyh
 */
public class JSONSerializer {

    public final SerializeWriter out;
    protected final SerializeConfig config;

    public JSONSerializer(SerializeWriter out, SerializeConfig config) {
        this.out = out;
        this.config = config;
    }

    public void write(Object object) throws Exception {
        if (object == null) {
            out.writeNull();
            return;
        }

        Class<?> clazz = object.getClass();
        ObjectSerializer writer = config.getObjectWriter(clazz);

        writer.write(this, object);
    }
}
