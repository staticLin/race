package race.question.demo.json;

/**
 * @author linyh
 */
public class SerializeWriter {

    private final static ThreadLocal<char[]> BUF_LOCAL = new ThreadLocal<>();

    private final static int BUFFER_THRESHOLD = 1024 * 128;

    protected char[] buf;

    protected int count;

    public char preSymbol;

    public SerializeWriter() {

        preSymbol = '{';

        buf = BUF_LOCAL.get();

        if (buf == null) {
            buf = new char[2048];
        } else {
            BUF_LOCAL.set(null);
        }
    }

    public void write(int c) {
        int newcount = count + 1;
        if (newcount > buf.length) {
            expandCapacity(newcount);
        }
        buf[count] = (char) c;
        count = newcount;
    }

    public void write(String text) {
        if (text == null) {
            writeNull();
            return;
        }

        write(text, 0, text.length());
    }

    public void write(String str, int off, int len) {
        int newcount = count + len;
        if (newcount > buf.length) {
            expandCapacity(newcount);
        }
        str.getChars(off, off + len, buf, count);
        count = newcount;
    }

    public void writeNull() {
        writeDoubleQuote("null");
    }

    public void expandCapacity(int minimumCapacity) {

        int newCapacity = buf.length + (buf.length >> 1) + 1;

        if (newCapacity < minimumCapacity) {
            newCapacity = minimumCapacity;
        }

        char[] newValue = new char[newCapacity];
        System.arraycopy(buf, 0, newValue, 0, count);

        if (buf.length < BUFFER_THRESHOLD) {
            char[] charsLocal = BUF_LOCAL.get();
            if (charsLocal == null || charsLocal.length < buf.length) {
                BUF_LOCAL.set(buf);
            }
        }

        buf = newValue;
    }

    public void close() {

        if (buf.length <= BUFFER_THRESHOLD) {
            BUF_LOCAL.set(buf);
        }

        this.buf = null;
    }

    @Override
    public String toString() {
        return new String(buf, 0, count);
    }

    public void writeDoubleQuote(String fieldName) {

        write('\"');
        write(fieldName);
        write('\"');
    }

    public void writeFieldValueStringWithDoubleQuote(String name) {

        writeDoubleQuote(name);
        preSymbol = ',';
    }

    public void writeFieldWithoutDoubleQuote(String name) {

        write(name);
        preSymbol = ',';
    }

    public void writePrefix(String key) {
        write(preSymbol);
        writeDoubleQuote(key);
        write(':');
    }

    public void writeObject(String fieldName, Object value) throws Exception {

        if (value == null) {
            return;
        }

        // 原始类型
        Class<?> clazz = value.getClass();

        writePrefix(fieldName);

        ObjectSerializer objectSerializer = SerializeConfig.GLOBAL_INSTANCE.getObjectWriter(clazz);

        objectSerializer.write(this, value);
    }

    public void end() {

        if (preSymbol == ',') {
            write('}');
        } else if (preSymbol == '{') {
            write("{}");
        }
    }
}
