package cn.qsnark.sdk.rpc.types;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */

public class Bytes32Type extends SolidityType {
    public Bytes32Type(String s) {
        super(s);
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof Number) {
            BigInteger bigInt = new BigInteger(value.toString());
            return IntType.encodeInt(bigInt);
        } else if (value instanceof String) {
            byte[] ret = new byte[32];
            byte[] bytes = ((String) value).getBytes(Charset.forName("UTF-8"));
            System.arraycopy(bytes, 0, ret, 0, bytes.length);
            return ret;
        } else if (value instanceof byte[]) {
            byte[] bytes = (byte[]) value;
            byte[] ret = new byte[32];
            System.arraycopy(bytes, 0, ret, 32 - bytes.length, bytes.length);
            return ret;
        }

        return new byte[0];
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        return Arrays.copyOfRange(encoded, offset, offset + getFixedSize());
    }
}
