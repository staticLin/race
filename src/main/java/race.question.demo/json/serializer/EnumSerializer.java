package race.question.demo.json.serializer;

import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeWriter;

/**
 * @author linyh
 */
public final class EnumSerializer implements ObjectSerializer {

    /**
     * 单例
     */
    public static final EnumSerializer INSTANCE = new EnumSerializer();

    private EnumSerializer() {
    }

    @Override
    public void write(final SerializeWriter out, final Object value) throws Exception {

        final Enum enumValue = (Enum) value;
        out.writeFieldValueStringWithDoubleQuote(enumValue.name());
    }
}
