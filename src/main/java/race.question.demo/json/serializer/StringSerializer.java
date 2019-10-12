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

        SerializeWriter out = serializer.out;

        if (object instanceof Number || object instanceof Boolean) {

            out.writeFieldWithoutDoubleQuote(object.toString());
            return;
        }

        if (object instanceof Character) {
            char c = (Character) object;
            if (c == 0) {
                out.writeDoubleQuote("\\u0000");
            } else {
                out.write(c);
            }
            out.preSymbol = ',';
            return;
        }

        out.writeFieldValueStringWithDoubleQuote(object.toString());
    }
}
