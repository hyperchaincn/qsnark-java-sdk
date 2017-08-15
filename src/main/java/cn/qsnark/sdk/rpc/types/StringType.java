package cn.qsnark.sdk.rpc.types;

import java.nio.charset.Charset;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */
public class StringType extends BytesType {
    public StringType() {
        super("string");
    }

    @Override
    public byte[] encode(Object value) {
        if (!(value instanceof String)) throw new RuntimeException("String value expected for type 'string'");
        return super.encode(((String) value).getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        return new String((byte[]) super.decode(encoded, offset), Charset.forName("UTF-8"));
    }
}
