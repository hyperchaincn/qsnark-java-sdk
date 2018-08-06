package cn.qsnark.sdk.rpc.types;

import cn.qsnark.sdk.rpc.utils.ByteUtil;
import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */

public class AddressType extends IntType {
    public AddressType() {
        super("address");
    }

    @Override
    public byte[] encode(Object value) {
        if (value instanceof String && !((String) value).startsWith("0x")) {
            // address is supposed to be always in hex
            value = "0x" + value;
        }
        byte[] addr = super.encode(value);
        for (int i = 0; i < 12; i++) {
            if (addr[i] != 0) {
                throw new RuntimeException("Invalid address (should be 20 bytes length): " + Hex.toHexString(addr));
            }
        }
        return addr;
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        BigInteger bi = (BigInteger) super.decode(encoded, offset);
        return ByteUtil.bigIntegerToBytes(bi, 20);
    }
}
