package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;

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
    public void write(final JSONSerializer serializer, final Object value) throws Exception {

        final Enum enumValue = (Enum) value;
        serializer.out.writeFieldValueStringWithDoubleQuote(enumValue.name());
    }
}
