package race.question.demo.json.serializer;

import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeConfig;
import race.question.demo.json.SerializeWriter;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author linyh
 */
public final class CollectionSerializer implements ObjectSerializer {

    /**
     * 单例
     */
    public static final CollectionSerializer INSTANCE = new CollectionSerializer();

    private CollectionSerializer() {
    }

    @Override
    public void write(final SerializeWriter out, final Object object) throws Exception {

        final Collection list = (Collection) object;

        out.write('[');
        if (!list.isEmpty()) {

            final Iterator iterator = list.iterator();
            final Object element = iterator.next();
            final Class eleClass = element.getClass();

            final ObjectSerializer objectSerializer = SerializeConfig.GLOBAL_INSTANCE.getObjectWriter(element.getClass());

            if (!plainValue(eleClass)) {
                out.preSymbol = '{';
            }

            objectSerializer.write(out, element);

            while (iterator.hasNext()) {
                out.write(',');
                objectSerializer.write(out, iterator.next());
            }
        }
        out.write(']');

        out.preSymbol = ',';
    }

    private boolean plainValue(final Class eleClass) {

        return Enum.class.isAssignableFrom(eleClass)
                || String.class.isAssignableFrom(eleClass)
                || eleClass.isArray()
                || Collection.class.isAssignableFrom(eleClass);
    }

}
