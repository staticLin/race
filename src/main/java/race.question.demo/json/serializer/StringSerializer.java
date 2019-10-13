package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeWriter;

/**
 * @author linyh
 */
public final class StringSerializer implements ObjectSerializer {

    /**
     * 单例
     */
    public static final StringSerializer INSTANCE = new StringSerializer();

    private StringSerializer() {
    }

    @Override
    public void write(final JSONSerializer serializer, final Object object) throws Exception {

        final SerializeWriter out = serializer.out;

        if (object instanceof Number || object instanceof Boolean) {

            out.writeFieldWithoutDoubleQuote(object.toString());

        } else if (object instanceof Character) {

            final char charValue = (Character) object;

            if (charValue == 0) {
                out.writeDoubleQuote("\\u0000");
            } else {
                out.write(charValue);
            }

            out.preSymbol = ',';
        } else {

            out.writeFieldValueStringWithDoubleQuote(object.toString());
        }
    }
}
