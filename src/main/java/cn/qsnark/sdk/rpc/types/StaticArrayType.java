package cn.qsnark.sdk.rpc.types;

import cn.qsnark.sdk.rpc.utils.ByteUtil;

import java.util.List;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */
public class StaticArrayType extends ArrayType {
    int size;

    public StaticArrayType(String name) {
        super(name);
        int idx1 = name.indexOf("[");
        int idx2 = name.indexOf("]", idx1);
        String dim = name.substring(idx1 + 1, idx2);
        size = Integer.parseInt(dim);
    }

    @Override
    public String getCanonicalName() {
        return elementType.getCanonicalName() + "[" + size + "]";
    }

    @Override
    public byte[] encodeList(List l) {
        if (l.size() != size)
            throw new RuntimeException("List size (" + l.size() + ") != " + size + " for type " + getName());
        byte[][] elems = new byte[size][];
        for (int i = 0; i < l.size(); i++) {
            elems[i] = elementType.encode(l.get(i));
        }
        return ByteUtil.merge(elems);
    }

    @Override
    public Object[] decode(byte[] encoded, int offset) {
        Object[] result = new Object[size];
        for (int i = 0; i < size; i++) {
            result[i] = elementType.decode(encoded, offset + i * elementType.getFixedSize());
        }

        return result;
    }

    @Override
    public int getFixedSize() {
        // return negative if elementType is dynamic
        return elementType.getFixedSize() * size;
    }
}
