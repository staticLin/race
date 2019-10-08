package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeConfig;
import race.question.demo.json.SerializeWriter;

/**
 * @author linyh
 */
public class ArraySerializer implements ObjectSerializer {

    public static final ArraySerializer instance = new ArraySerializer();

    @Override
    public void write(JSONSerializer serializer, Object value) throws Exception {

        SerializeWriter out = serializer.out;
        out.write('[');

        if (value instanceof int[]) {

            int[] array = (int[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Integer.valueOf(array[i]).toString());
            }
        } else if (value instanceof byte[]) {

            byte[] array = (byte[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Byte.valueOf(array[i]).toString());
            }
        } else if (value instanceof short[]) {

            short[] array = (short[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Short.valueOf(array[i]).toString());
            }
        } else if (value instanceof long[]) {

            long[] array = (long[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Long.valueOf(array[i]).toString());
            }
        } else if (value instanceof boolean[]) {

            boolean[] array = (boolean[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Boolean.valueOf(array[i]).toString());
            }
        } else if (value instanceof double[]) {

            double[] array = (double[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Double.valueOf(array[i]).toString());
            }
        } else if (value instanceof char[]) {

            char[] array = (char[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Character.valueOf(array[i]).toString());
            }
        } else if (value instanceof float[]) {

            float[] array = (float[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) out.write(',');
                out.write(Float.valueOf(array[i]).toString());
            }
        } else {
            Object[] array = (Object[]) value;

            if (array.length != 0) {

                Object arrValue = array[0];

                ObjectSerializer objectSerializer = SerializeConfig.globalInstance.getObjectWriter(arrValue.getClass());
                if (objectSerializer == null) throw new RuntimeException("can not serializer object");

                objectSerializer.write(serializer, arrValue);

                for (int i = 1; i < array.length; i++) {
                    out.write(',');
                    objectSerializer.write(serializer, array[i]);
                }
            }
        }
        out.write(']');
    }
}
