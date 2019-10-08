package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeConfig;
import race.question.demo.json.SerializeWriter;

import java.util.Iterator;
import java.util.Map;

/**
 * @author linyh
 */
public class MapSerializer implements ObjectSerializer {

    public static final MapSerializer instance = new MapSerializer();

    @Override
    public void write(JSONSerializer serializer, Object value) throws Exception {

        Map<?, ?> map = (Map<?, ?>) value;
        SerializeWriter out = serializer.out;

        out.write('{');

        if (map.size() > 0) {

            ObjectSerializer keySerializer = null;
            ObjectSerializer valueSerializer = null;

            Iterator<? extends Map.Entry<?, ?>> iterator = map.entrySet().iterator();
            Map.Entry<?, ?> next = iterator.next();
            Object k = next.getKey();
            Object v = next.getValue();

            writeKeyAndValue(keySerializer, k, valueSerializer, v, serializer);

            out.preSymbol = ',';

            while (iterator.hasNext()) {

                out.write(',');
                next = iterator.next();

                k = next.getKey();
                v = next.getValue();

                writeKeyAndValue(keySerializer, k, valueSerializer, v, serializer);
            }
        }

        out.write('}');

        out.preSymbol = ',';
    }

    private void writeKeyAndValue
            (ObjectSerializer keySerializer,
             Object k,
             ObjectSerializer valueSerializer,
             Object v, JSONSerializer serializer) throws Exception {

        write0(keySerializer, k, serializer);
        serializer.out.write(':');
        write0(valueSerializer, v, serializer);
    }

    private void write0(ObjectSerializer valueSerializer, Object v, JSONSerializer serializer) throws Exception {

        if (v == null) serializer.out.writeNull();
        else {
            if (valueSerializer == null)
                valueSerializer = SerializeConfig.globalInstance.getObjectWriter(v.getClass());

            if (valueSerializer == null) throw new RuntimeException("can not serializer object");
            valueSerializer.write(serializer, v);
        }
    }
}
