package race.question.demo.json.serializer;

import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeConfig;
import race.question.demo.json.SerializeWriter;

import java.util.Map;

/**
 * @author linyh
 */
public final class MapSerializer implements ObjectSerializer {

    /**
     * 单例
     */
    public static final MapSerializer INSTANCE = new MapSerializer();

    private MapSerializer() {
    }

    @Override
    public void write(final SerializeWriter out, final Object value) throws Exception {

        final Map<?, ?> map = (Map<?, ?>) value;

        out.write('{');
        boolean flag = false;
        if (!map.isEmpty()) {

            ObjectSerializer keySerializer = null;
            ObjectSerializer valueSerializer = null;

            for (final Map.Entry<?, ?> entry : map.entrySet()) {

                if (flag) {
                    out.write(',');
                }
                flag = true;

                final Object key = entry.getKey();
                final Object writeValue = entry.getValue();

                if (key != null && keySerializer == null) {
                    keySerializer = SerializeConfig.GLOBAL_INSTANCE.getObjectWriter(key.getClass());
                }
                if (writeValue != null && valueSerializer == null) {
                    valueSerializer = SerializeConfig.GLOBAL_INSTANCE.getObjectWriter(writeValue.getClass());
                }

                writeKeyAndValue(keySerializer, key, valueSerializer, writeValue, out);
            }
        }

        out.write('}');

        out.preSymbol = ',';
    }

    private void writeKeyAndValue
            (final ObjectSerializer keySerializer,
             final Object key,
             final ObjectSerializer valueSerializer,
             final Object value, final SerializeWriter out) throws Exception {

        write0(keySerializer, key, out);
        out.write(':');
        write0(valueSerializer, value, out);
    }

    private void write0(final ObjectSerializer valueSerializer,
                        final Object value,
                        final SerializeWriter out) throws Exception {

        if (value == null) {
            out.writeNull();
        } else {
            valueSerializer.write(out, value);
        }
    }
}
