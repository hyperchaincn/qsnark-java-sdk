package cn.qsnark.sdk.rpc.types;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */

public abstract class ArrayType extends SolidityType {
    public static ArrayType getType(String typeName) {
        int idx1 = typeName.indexOf("[");
        int idx2 = typeName.indexOf("]", idx1);
        if (idx1 + 1 == idx2) {
            return new DynamicArrayType(typeName);
        } else {
            return new StaticArrayType(typeName);
        }
    }

    SolidityType elementType;

    public ArrayType(String name) {
        super(name);
        int idx = name.indexOf("[");
        String st = name.substring(0, idx);
        int idx2 = name.indexOf("]", idx);
        String subDim = idx2 + 1 == name.length() ? "" : name.substring(idx2 + 1);
        elementType = SolidityType.getType(st + subDim);
    }

    @Override
    public byte[] encode(Object value) {
        if (value.getClass().isArray()) {
            List<Object> elems = new ArrayList<Object>();
            for (int i = 0; i < Array.getLength(value); i++) {
                elems.add(Array.get(value, i));
            }
            return encodeList(elems);
        } else if (value instanceof List) {
            return encodeList((List) value);
        } else {
            throw new RuntimeException("List value expected for type " + getName());
        }
    }

    public SolidityType getElementType() {
        return elementType;
    }

    public abstract byte[] encodeList(List l);
}
