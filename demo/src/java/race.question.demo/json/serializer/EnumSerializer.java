package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;

/**
 * @author linyh
 */
public class EnumSerializer implements ObjectSerializer {

    public static final EnumSerializer instance = new EnumSerializer();

    @Override
    public void write(JSONSerializer serializer, Object value) throws Exception {

        Enum a = (Enum) value;
        serializer.out.writeFieldValueStringWithDoubleQuote(a.name());
    }
}
