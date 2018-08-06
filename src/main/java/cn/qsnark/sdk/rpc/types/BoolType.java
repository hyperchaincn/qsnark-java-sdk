package cn.qsnark.sdk.rpc.types;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */

public class BoolType extends IntType {
    public BoolType() {
        super("bool");
    }

    @Override
    public byte[] encode(Object value) {
        if (!(value instanceof Boolean)) throw new RuntimeException("Wrong value for bool type: " + value);
        return super.encode(value == Boolean.TRUE ? 1 : 0);
    }

    @Override
    public Object decode(byte[] encoded, int offset) {
        return Boolean.valueOf(((Number) super.decode(encoded, offset)).intValue() != 0);
    }
}