package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeWriter;

/**
 * @author linyh
 */
public class StringSerializer implements ObjectSerializer {

    public static final StringSerializer INSTANCE = new StringSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object) throws Exception {

        if (object instanceof Number || object instanceof Boolean) {

            SerializeWriter out = serializer.out;
            out.writeFieldWithoutDoubleQuote(object.toString());
            return;
        }

        SerializeWriter out = serializer.out;
        out.writeFieldValueStringWithDoubleQuote(object.toString());
    }
}
