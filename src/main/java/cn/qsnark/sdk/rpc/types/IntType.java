package cn.qsnark.sdk.rpc.types;

import cn.qsnark.sdk.rpc.utils.ByteUtil;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */
public class IntType extends SolidityType {
    public IntType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        if (getName().equals("int")) return "int256";
        if (getName().equals("uint")) return "uint256";
        return super.getCanonicalName();
    }

    @Override
    public byte[] encode(Object value) {
        BigInteger bigInt;
        if (value instanceof String) {
            String s = ((String) value).toLowerCase().trim();
            int radix = 10;
            if (s.startsWith("0x")) {
                s = s.substring(2);
                radix = 16;
            } else if (s.contains("a") || s.contains("b") || s.contains("c") ||
                    s.contains("d") || s.contains("e") || s.contains("f")) {
                radix = 16;
            }
            bigInt = new BigInteger(s, radix);
        } else if (value instanceof BigInteger) {
            bigInt = (BigInteger) value;
        } else if (value instanceof Number) {
            bigInt = new BigInteger(value.toString());
        } else if (value instanceof byte[]) {
            bigInt = ByteUtil.bytesToBigInteger((byte[]) value);
        } else {
            throw new RuntimeException("Invalid value for type '" + this + "': " + value + " (" + value.getClass() + ")");
        }
        return encodeInt(bigInt);
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        return decodeInt(encoded, offset);
    }

    public static BigInteger decodeInt(byte[] encoded, int offset) {
        return new BigInteger(Arrays.copyOfRange(encoded, offset, offset + 32));
    }

    public static byte[] encodeInt(int i) {
        return encodeInt(new BigInteger("" + i));
    }

    public static byte[] encodeInt(BigInteger bigInt) {
        return ByteUtil.bigIntegerToBytesSigned(bigInt, 32);
    }
}
