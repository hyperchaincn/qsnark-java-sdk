package cn.qsnark.sdk.rpc.types;

/**
 * @author Mikhail Kalinin
 * @since 07.07.2015
 */
public abstract class SolidityType {
    protected String name;

    public SolidityType(String name) {
        this.name = name;
    }

    /**
     * The type name as it was specified in the interface description
     */
    public String getName() {
        return name;
    }

    /**
     * The canonical type name (used for the method signature creation)
     * E.g. 'int' - canonical 'int256'
     */
    public String getCanonicalName() {
        return getName();
    }

    /**
     * 取得实际类型
     *
     * @param typeName 类型名称
     * @return 返回实际类型
     */
    public static SolidityType getType(String typeName) {
        if (typeName.contains("[")) return ArrayType.getType(typeName);
        if ("bool".equals(typeName)) return new BoolType();
        if (typeName.startsWith("int") || typeName.startsWith("uint")) return new IntType(typeName);
        if ("address".equals(typeName)) return new AddressType();
        if ("string".equals(typeName)) return new StringType();
        if ("bytes".equals(typeName)) return new BytesType();
        if (typeName.startsWith("bytes")) return new Bytes32Type(typeName);
        throw new RuntimeException("Unknown type: " + typeName);
    }

    /**
     * Encodes the value according to specific type rules
     *
     * @param value 需要编码的类型
     * @return 编码之后的bytes
     */
    public abstract byte[] encode(Object value);

    public abstract Object decode(byte[] encoded, int offset);

    public Object decode(byte[] encoded) {
        return decode(encoded, 0);
    }

    /**
     * @return fixed size in bytes. For the dynamic types returns IntType.getFixedSize()
     * which is effectively the int offset to dynamic data
     */
    public int getFixedSize() {
        return 32;
    }

    public boolean isDynamicType() {
        return false;
    }

    @Override
    public String toString() {
        return getName();
    }
}

