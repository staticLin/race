package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeConfig;
import race.question.demo.json.SerializeWriter;

/**
 * @author linyh
 */
public final class ArraySerializer implements ObjectSerializer {

    /**
     * 单例
     */
    public static final ArraySerializer INSTANCE = new ArraySerializer();

    private ArraySerializer() {
    }

    /**
     * 数组的序列化方式
     * @param serializer
     * @param value
     * @throws Exception
     */
    @Override
    public void write(final JSONSerializer serializer, final Object value) throws Exception {

        final SerializeWriter out = serializer.out;
        out.write('[');

        if (value instanceof int[]) {

            final int[] array = (int[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Integer.toString(array[i]));
            }
        } else if (value instanceof byte[]) {

            final byte[] array = (byte[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Byte.toString(array[i]));
            }
        } else if (value instanceof short[]) {

            final short[] array = (short[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Short.toString(array[i]));
            }
        } else if (value instanceof long[]) {

            final long[] array = (long[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Long.toString(array[i]));
            }
        } else if (value instanceof boolean[]) {

            final boolean[] array = (boolean[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Boolean.toString(array[i]));
            }
        } else if (value instanceof double[]) {

            final double[] array = (double[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Double.toString(array[i]));
            }
        } else if (value instanceof char[]) {

            final char[] array = (char[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Character.toString(array[i]));
            }
        } else if (value instanceof float[]) {

            final float[] array = (float[]) value;

            for (int i = 0; i < array.length; i++) {

                if (i != 0) {
                    out.write(',');
                }
                out.write(Float.toString(array[i]));
            }
        } else {
            final Object[] array = (Object[]) value;

            if (array.length != 0) {

                final Object arrValue = array[0];

                final ObjectSerializer objectSerializer = SerializeConfig.GLOBAL_INSTANCE.getObjectWriter(arrValue.getClass());

                objectSerializer.write(serializer, arrValue);

                for (int i = 1; i < array.length; i++) {
                    out.write(',');
                    objectSerializer.write(serializer, array[i]);
                }
            }
        }
        out.write(']');
        out.preSymbol = ',';
    }
}
