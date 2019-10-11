package race.question.demo.json.serializer;

import race.question.demo.json.JSONSerializer;
import race.question.demo.json.ObjectSerializer;
import race.question.demo.json.SerializeConfig;
import race.question.demo.json.SerializeWriter;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author linyh
 */
public class CollectionSerializer implements ObjectSerializer {

    public static final CollectionSerializer INSTANCE = new CollectionSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object) throws Exception {

        Collection list = (Collection) object;
        SerializeWriter out = serializer.out;

        out.write('[');
        if (list.size() > 0) {

            Iterator iterator = list.iterator();
            Object element = iterator.next();
            Class eleClass = element.getClass();

            ObjectSerializer objectSerializer = SerializeConfig.GLOBAL_INSTANCE.getObjectWriter(element.getClass());

            if (!plainValue(eleClass)) {
                out.preSymbol = '{';
            }

            objectSerializer.write(serializer, element);

            while (iterator.hasNext()) {
                out.write(',');
                objectSerializer.write(serializer, iterator.next());
            }
        }
        out.write(']');

        out.preSymbol = ',';
    }

    private boolean plainValue(Class eleClass) {

        return Enum.class.isAssignableFrom(eleClass)
                || String.class.isAssignableFrom(eleClass)
                || eleClass.isArray()
                || Collection.class.isAssignableFrom(eleClass);
    }

}
