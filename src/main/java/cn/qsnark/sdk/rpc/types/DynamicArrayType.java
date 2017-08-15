package cn.qsnark.sdk.rpc.types;

import cn.qsnark.sdk.rpc.utils.ByteUtil;

import java.util.List;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */
public class DynamicArrayType extends ArrayType {
    public DynamicArrayType(String name) {
        super(name);
    }

    @Override
    public String getCanonicalName() {
        return elementType.getCanonicalName() + "[]";
    }

    @Override
    public byte[] encodeList(List l) {
        byte[][] elems;
        if (elementType.isDynamicType()) {
            elems = new byte[l.size() * 2 + 1][];
            elems[0] = IntType.encodeInt(l.size());
            int offset = l.size() * 32;
            for (int i = 0; i < l.size(); i++) {
                elems[i + 1] = IntType.encodeInt(offset);
                byte[] encoded = elementType.encode(l.get(i));
                elems[l.size() + i + 1] = encoded;
                offset += 32 * ((encoded.length - 1) / 32 + 1);
            }
        } else {
            elems = new byte[l.size() + 1][];
            elems[0] = IntType.encodeInt(l.size());

            for (int i = 0; i < l.size(); i++) {
                elems[i + 1] = elementType.encode(l.get(i));
            }
        }
        return ByteUtil.merge(elems);
    }

    @Override
    public Object decode(byte[] encoded, int origOffset) {
        int len = IntType.decodeInt(encoded, origOffset).intValue();
        origOffset += 32;
        int offset = origOffset;
        Object[] ret = new Object[len];

        for (int i = 0; i < len; i++) {
            if (elementType.isDynamicType()) {
                ret[i] = elementType.decode(encoded, origOffset + IntType.decodeInt(encoded, offset).intValue());
            } else {
                ret[i] = elementType.decode(encoded, offset);
            }
            offset += elementType.getFixedSize();
        }
        return ret;
    }

    @Override
    public boolean isDynamicType() {
        return true;
    }
}