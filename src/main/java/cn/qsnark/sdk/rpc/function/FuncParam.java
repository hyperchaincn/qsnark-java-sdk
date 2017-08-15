package cn.qsnark.sdk.rpc.function;

import java.nio.charset.Charset;
import java.util.ArrayList;
//import java.util.StringJoiner;

/**
 * description:
 * <p>
 * date 10/27/16
 *
 * @author chenquan
 * @version 1.0
 */
public class FuncParam {
    private String _type;
    private String _param;

    /**
     * 合约参数(string)构造方法
     *
     * @param type  参数类型
     * @param param 参数值
     */
    public FuncParam(String type, String param) throws FunctionParamException {
        //TODO check string
        if (type.equals("string")) {
            type = "bytes32";
        }
        this._type = type;
        if (type.startsWith("int")) {
            if (!checkIntTypeValue(param)) {
                throw new FunctionParamException("invalid param element");
            }
        } else if (type.startsWith("uint")) {
            if (param.startsWith("-")) {
                throw new FunctionParamException("invalid param element, negative");
            }
        } else if (type.startsWith("bytes")) {
            String lenstr = type.substring(type.indexOf('s') + 1);
            if (!checkIntTypeValue(lenstr)) {
                throw new FunctionParamException("byte length not support!");
            }
            if (!checkBytesValue(param, Integer.valueOf(lenstr))) {
                throw new FunctionParamException("byte element invalid");
            }
        } else if (type.startsWith("address")) {
            if (!checkAddressValue(param)) {
                throw new FunctionParamException("address element invalid ");
            }

        } else if (type.startsWith("bool")) {
            if (!checkBoolValue(param)) {
                throw new FunctionParamException("bool element invalid ");
            }
        } else {
            throw new FunctionParamException("invalid Function Param type, please specific the params type");
        }

        this._param = param;

    }

    public FuncParam(String type, ArrayList<String> params) throws FunctionParamException {
        if (type.equals("string[]")) {
            type = "bytes32[]";
        }
        if (!checkArrayType(type)) {
            throw new FunctionParamException("invalid Function Param Type");
        }
        this._param = "[";
        for (int i = 0; i < params.size(); i++) {
            if (type.startsWith("int")) {
                if (!checkIntTypeValue(params.get(i))) {
                    throw new FunctionParamException("invalid param element, index:" + i);
                }
            } else if (type.startsWith("uint")) {
                if (params.get(i).startsWith("-")) {
                    throw new FunctionParamException("invalid param element, negative, index: " + i);
                } else if (!checkIntTypeValue(params.get(i))) {
                    throw new FunctionParamException("uint element invalid, index: " + i);
                }
            } else if (type.startsWith("bytes")) {
                String lenstr = type.substring(type.indexOf('s') + 1, type.indexOf('['));
                if (!checkIntTypeValue(lenstr)) {
                    throw new FunctionParamException("byte length not support!");
                }
                if (!checkBytesValue(params.get(i), Integer.valueOf(lenstr))) {
                    throw new FunctionParamException("byte element invalid, index: " + i);
                }
            } else if (type.startsWith("address")) {
                if (!checkAddressValue(params.get(i))) {
                    throw new FunctionParamException("address element invalid, index: " + i);
                }

            } else if (type.startsWith("bool")) {
                if (!checkBoolValue(params.get(i))) {
                    throw new FunctionParamException("bool element invalid, index: " + i);
                }
            } else {
                throw new FunctionParamException("invalid Function Param type, please specific the params type");
            }
            if (!(i == (params.size() - 1))) {
                this._param += params.get(i) + ",";
            } else {
                this._param += params.get(i);
            }
        }

        this._param += "]";
        this._type = type;
    }


    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_param() {
        return _param;
    }

    public void set_param(String _param) {
        this._param = _param;
    }


    private static boolean checkBoolValue(String s) {
        return s.equals("true") || s.equals("false");
    }


    private static boolean checkArrayType(String s) {

        return s.charAt(s.length() - 2) == '[' && s.endsWith("]");
    }

    /**
     * check the string value convert to byte's length
     *
     * @param s      bytes String
     * @param length byte length
     * @return valid or not
     */
    private static boolean checkBytesValue(String s, int length) {
        return s.getBytes(Charset.forName("UTF8")).length <= length;
    }

    /**
     * check the string value if the value is valid address or not
     *
     * @param s address string
     * @return valid or not
     */
    private static boolean checkAddressValue(String s) {
        return s.startsWith("0x") && s.length() == 42 && isHexInterger(s.substring(2));
    }


    /**
     * check the integer value if the value is valid or not
     *
     * @param s the sting
     * @return is valid int string or not
     */
    private static boolean checkIntTypeValue(String s) {
        if (s.startsWith("0x")) {
            return isHexInterger(s);
        } else {
            return isDecimalInteger(s);
        }
    }

    public static boolean isDecimalInteger(String s) {
        return isInteger(s, 10);
    }

    private static boolean isHexInterger(String s) {
        return isInteger(s, 16);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }

}
