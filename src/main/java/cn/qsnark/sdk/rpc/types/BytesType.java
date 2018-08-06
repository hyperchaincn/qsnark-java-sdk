package cn.qsnark.sdk.rpc.types;

import cn.qsnark.sdk.rpc.utils.ByteUtil;

import java.util.Arrays;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */

public class BytesType extends SolidityType {
    protected BytesType(String name) {
        super(name);
    }

    public BytesType() {
        super("bytes");
    }

    @Override
    public byte[] encode(Object value) {
        if (!(value instanceof byte[])) throw new RuntimeException("byte[] value expected for type 'bytes'");
        byte[] bb = (byte[]) value;
        byte[] ret = new byte[((bb.length - 1) / 32 + 1) * 32]; // padding 32 bytes
        System.arraycopy(bb, 0, ret, 0, bb.length);

        return ByteUtil.merge(IntType.encodeInt(bb.length), ret);
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        int len = IntType.decodeInt(encoded, offset).intValue();
        if (len == 0) return new byte[0];
        offset += 32;

        return Arrays.copyOfRange(encoded, offset, offset + len);
    }

    @Override
    public boolean isDynamicType() {
        return true;
    }
}