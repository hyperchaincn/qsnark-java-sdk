package cn.qsnark.sdk.rpc.function;

import cn.qsnark.sdk.rpc.types.IntType;
import cn.qsnark.sdk.rpc.types.SolidityType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Param {
    public Boolean indexed;
    public String name;
    public SolidityType type;

    public static List<?> decodeList(List<Param> params, byte[] encoded) {
        List<Object> result = new ArrayList<Object>(params.size());

        int offset = 0;
        for (Param param : params) {
            Object decoded = param.type.isDynamicType()
                    ? param.type.decode(encoded, IntType.decodeInt(encoded, offset).intValue())
                    : param.type.decode(encoded, offset);
            result.add(decoded);

            offset += param.type.getFixedSize();
        }

        return result;
    }

    @Override
    public String toString() {
        return format("%s%s%s", type.getCanonicalName(), (indexed != null && indexed) ? " indexed " : " ", name);
    }
}