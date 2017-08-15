package cn.qsnark.sdk.rpc.function;

import cn.qsnark.sdk.rpc.types.IntType;
import cn.qsnark.sdk.rpc.types.SolidityType;
import cn.qsnark.sdk.rpc.utils.ByteUtil;
import cn.qsnark.sdk.sha3.Sha3;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * description:
 * <p>
 * date 10/27/16
 *
 * @author chenquan
 * @version 1.0
 */

public class FunctionEncode {

    /**
     * 函数输入编码(参数为FuncParam类型)
     *
     * @param functionName 函数名称
     * @param funcParams   Solidity参数
     * @return 编码之后的string
     */

    public static String encodeFunction(String functionName, FuncParam... funcParams) throws FunctionParamException {
        String funcID = FunctionEncode.encodeFunctionId(functionName, funcParams);
        String funcBody = FunctionEncode.encodeFunctionBody(funcParams);
        return funcID + funcBody;
    }

    /**
     * 无参类型编码
     *
     * @param functionName
     * @return
     */
    public static String encodeFunction(String functionName) {
        String funcID = FunctionEncode.encodeFunctionId(functionName);
        return funcID;
    }

    /**
     * encode the smartContract with Constructor method.
     * // 构造方法不需要method id!!
     *
     * @param funcParams
     * @return
     * @throws FunctionParamException
     */
    public static String encodeFunctionConstructor(FuncParam... funcParams) throws FunctionParamException {
//        String funcID = FunctionEncode.encodeFunctionId(functionName, funcParams);
        String funcBody = FunctionEncode.encodeFunctionBody(funcParams);
        return funcBody;
    }

    /**
     * 函数输入编码(参数为FuncParam类型)
     *
     * @param functionName 函数名称
     * @param funcParams   Solidity real 类型参数
     * @return 编码之后的string
     */
    public static String encodeFunction(String functionName, FuncParamReal... funcParams) {
        String funcID = FunctionEncode.encodeFunctionId(functionName, funcParams);
        String funcBody = FunctionEncode.encodeFunctionBody(funcParams);
        return funcID + funcBody;
    }

    /**
     * encode the smartContract with Constructor method.
     * REVIEW 构造函数的函数名已经被删除，需要在文档中进行修改
     * TODO 在文档中对 encodeFunctionCinstructor 方法进行修改
     *
     * @param funcParams function Real Params
     * @return encoded string
     * @throws FunctionParamException -
     */
    public static String encodeFunctionConstructor(FuncParamReal... funcParams) throws FunctionParamException {
        //        String funcID = FunctionEncode.encodeFunctionId(functionName,funcParams);
        String funcBody = FunctionEncode.encodeFunctionBody(funcParams);
        return funcBody;
    }


    /**
     * 编码方法头
     *
     * @param functionName   方法名称
     * @param functionParams 方法参数
     * @return 编码之后的方法头
     */
    static String encodeFunctionId(String functionName, FuncParamReal... functionParams) {
        String paramStr = "(";
        for (int i = 0; i < functionParams.length; i++) {
            String typeStr = SolidityType.getType(functionParams[i].get_type()).getCanonicalName();
            paramStr += typeStr;
            if (i < functionParams.length - 1) {
                paramStr += ",";
            }
        }
        paramStr += ")";
        return "0x" + Sha3.sha3(functionName + paramStr).substring(0, 8);
    }

    /**
     * 无参数方法编码
     *
     * @param functionName 方法名称
     * @return
     */
    static String encodeFunctionId(String functionName) {
        String paramStr = "()";
        return "0x" + Sha3.sha3(functionName + paramStr).substring(0, 8);
    }

    /**
     * 编码方法头(多态方法)
     *
     * @param functionName   方法名称
     * @param functionParams 方法参数
     * @return 编码之后的方法头
     */
    static String encodeFunctionId(String functionName, FuncParam... functionParams) {
        String paramStr = "(";
        for (int i = 0; i < functionParams.length; i++) {
            String typeStr = SolidityType.getType(functionParams[i].get_type()).getCanonicalName();
            paramStr += typeStr;
            if (i < functionParams.length - 1) {
                paramStr += ",";
            }
        }
        paramStr += ")";
        return "0x" + Sha3.sha3(functionName + paramStr).substring(0, 8);
    }

    /**
     * 构造方法编码
     *
     * @param functionName
     * @param functionParams
     * @return
     */
    static String encodeFunctionIdConstruction(String functionName, FuncParam... functionParams) {
        String paramStr = "(";
        for (int i = 0; i < functionParams.length; i++) {
            String typeStr = SolidityType.getType(functionParams[i].get_type()).getCanonicalName();
            paramStr += typeStr;
            if (i < functionParams.length - 1) {
                paramStr += ",";
            }
        }
        paramStr += ")";
        return Sha3.sha3(functionName + paramStr).substring(0, 8);
    }

    /**
     * 编码方法体(使用真值参数)
     *
     * @param functionParams 真值参数(不定个数)
     * @return 编码后的方法体
     */
    static String encodeFunctionBody(FuncParamReal... functionParams) {
        return encodeArguments(functionParams);
    }

    private static String encodeArguments(FuncParamReal... args) {
        int staticSize = 0;
        int dynamicCnt = 0;
        // calculating static size and number of dynamic params
        for (int i = 0; i < args.length; i++) {
            SolidityType type = SolidityType.getType(args[i].get_type());
            if (type.isDynamicType()) {
                dynamicCnt++;
            }
            staticSize += type.getFixedSize();
        }

        byte[][] bb = new byte[args.length + dynamicCnt][];
        for (int curDynamicPtr = staticSize, curDynamicCnt = 0, i = 0; i < args.length; i++) {
            SolidityType type = SolidityType.getType(args[i].get_type());
            if (type.isDynamicType()) {
                byte[] dynBB = type.encode(args[i].get_param());
                bb[i] = IntType.encodeInt(curDynamicPtr);
                bb[args.length + curDynamicCnt] = dynBB;
                curDynamicCnt++;
                curDynamicPtr += dynBB.length;
            } else {
                bb[i] = type.encode(args[i].get_param());
            }
        }

        byte[] retByte = ByteUtil.merge(bb);
        return ByteUtil.toHexString(retByte);
    }


    /**
     * 编码方法体
     *
     * @param funcParams string 参数(不定个数)
     * @return 编码后的方法体
     */
    static String encodeFunctionBody(FuncParam... funcParams) throws FunctionParamException {
        StringBuffer dataTail = new StringBuffer();
        String curType = "";
        int curOffset = 0;
        int curIndex = 0;

        String dyTypes = "";

        for (int i = 0; i < funcParams.length; i++) {

            // TODO String type Encode
            if (funcParams[i].get_type().equals("int")) {
                funcParams[i].set_type("int256");
                //dataTail.append(convert32Bytes(Integer.toHexString(Integer.parseInt(content[i]))));
            }

            // basic type
            // type int transfer to int256
            if (funcParams[i].get_type().equals("int")) {
                funcParams[i].set_type("int256");
                //dataTail.append(convert32Bytes(Integer.toHexString(Integer.parseInt(content[i]))));
            }

            //type uint32,uint64 and so on
            if (funcParams[i].get_type().startsWith("int") && !funcParams[i].get_type().equals("int") && funcParams[i].get_type().contains("int") && !funcParams[i].get_type().contains("[")) {
//                dataTail.append(convert32Bytes(Integer.toHexString(Integer.parseInt(funcParams[i].get_param()))));

                String dataString = funcParams[i].get_param();
                BigInteger originInteger = new BigInteger(funcParams[i].get_param());


                // 在solidity 中 0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff 为 -1
                // 但是 2 ** 256 也是这个值，这就无法区别是正数还是负数，所以需要缩小范围来标识正数还是负数
                // 在当前解析实现当中，采用 [-2**240,2**240]作为int的取值范围
                // 负数的最小值为 0xffff000000000000000000000000000000000000000000000000000000000000 (16)
                // 正数的最大值为 0x0000ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff (16)

                // 负数的最小值为 -1766847064778384329583297500742918515827483896875618958121606201292619775 (10)
                // 正数的最大值为 1766847064778384329583297500742918515827483896875618958121606201292619775 (10)

                BigInteger MAX_VALUE = new BigInteger("1766847064778384329583297500742918515827483896875618958121606201292619775");
                BigInteger MIN_VALUE = new BigInteger("-1766847064778384329583297500742918515827483896875618958121606201292619775");

                //处理负数
                //判断范围
                if (dataString.startsWith("-")) {
                    //如果比最小值都大的话，就是错误的
                    if (MIN_VALUE.compareTo(originInteger) >= 0) {
                        throw new FunctionParamException("the number is out of range, please check the number");
                    }
                    BigInteger WHOLE_VALUE = new BigInteger("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16);
                    originInteger = WHOLE_VALUE.add(originInteger);
                    originInteger = originInteger.add(new BigInteger("1"));
                } else {
                    //判断正数范围，如果大于该值则超出范围
                    if (MAX_VALUE.compareTo(originInteger) <= 0) {
                        throw new FunctionParamException("the number is out of range, please check the number");
                    }
                }
                dataTail.append(convert32Bytes(originInteger.toString(16)));
            }

            // type uint transfer to uint256
            if (funcParams[i].get_type().equals("uint")) {
                funcParams[i].set_type("uint256");
                //dataTail.append(convert32Bytes(Integer.toHexString(Integer.parseInt(content[i]))));
            }

            //type uint32,uint64 and so on
            if (funcParams[i].get_type().startsWith("uint") && !funcParams[i].get_type().equals("uint") && funcParams[i].get_type().contains("uint") && !funcParams[i].get_type().contains("[")) {
//                dataTail.append(convert32Bytes(Integer.toHexString(Integer.parseInt(funcParams[i].get_param()))));
                dataTail.append(convert32Bytes(BigInteger.valueOf(Long.parseLong(funcParams[i].get_param(), 10)).toString(16)));
            }

            if (funcParams[i].get_type().equals("address")) {
                dataTail.append(convert32Bytes(funcParams[i].get_param().substring(2)));
            }

            ///type bytes1 to bytes32
            if (!funcParams[i].get_type().equals("bytes") && funcParams[i].get_type().contains("bytes") && !funcParams[i].get_type().contains("[")) {
                dataTail.append(convertASC(funcParams[i].get_param()));
            }
            if (funcParams[i].get_type().equals("bool")) {
                if (funcParams[i].get_param().equals("true"))
                    dataTail.append(convert32Bytes(1 + ""));
                if (funcParams[i].get_param().equals("false"))
                    dataTail.append(convert32Bytes(0 + ""));
            }
            //dynamic type
            if (funcParams[i].get_type().equals("bytes")) {
                if (curType.isEmpty()) {
                    curOffset = 32 * funcParams.length;
                } else {
                    if (curType.equals("bytes")) {
                        curOffset += 32 * 2;
                    }
                    if (curType.startsWith("uint") && curType.contains("[")) {
                        String[] str = funcParams[curIndex].get_param().substring(1, funcParams[curIndex].get_param().length() - 1).split(",");
                        curOffset += (str.length + 1) * 32;
                    }
                    if (curType.equals("address[]")) {
                        String[] str = funcParams[curIndex].get_param().substring(1, funcParams[curIndex].get_param().length() - 1).split(",");
                        curOffset += (str.length + 1) * 32;
                    }
                }
                dataTail.append(convert32Bytes(Integer.toHexString(curOffset)));
                curType = "bytes";
                curIndex = i;
                dyTypes += "bytes-" + i + ",";
            }

            if (funcParams[i].get_type().contains("[")) {
                String subType = funcParams[i].get_type().substring(0, funcParams[i].get_type().indexOf('['));
                if (subType.contains("uint")) {
                    if (subType.equals("uint")) {
                        funcParams[i].set_type("uint256[]");
                    }
                    if (curType.isEmpty()) {
                        curOffset = 32 * funcParams.length;
                    } else {
                        if (curType.equals("bytes")) {
                            curOffset += 32 * 2;
                        }
                        if (curType.startsWith("uint") && curType.contains("[")) {
                            String[] str = funcParams[curIndex].get_param().substring(1, funcParams[curIndex].get_param().length() - 1).split(",");
                            curOffset += (str.length + 1) * 32;
                        }
                        if (curType.equals("address[]")) {
                            String[] str = funcParams[curIndex].get_param().substring(1, funcParams[curIndex].get_param().length() - 1).split(",");
                            curOffset += (str.length + 1) * 32;
                        }
                    }
                    dataTail.append(convert32Bytes(Integer.toHexString(curOffset)));
                    curType = "uint[]";
                    curIndex = i;
                    dyTypes += "uint[]-" + i + ",";
                }
                if (subType.contains("address")) {
                    if (curType.isEmpty()) {
                        curOffset = 32 * funcParams.length;
                    } else {
                        if (curType.equals("bytes")) {
                            curOffset += 32 * 2;
                        }
                        if (curType.startsWith("uint") && curType.contains("[")) {
                            String[] str = funcParams[curIndex].get_param().substring(1, funcParams[curIndex].get_param().length() - 1).split(",");
                            curOffset += (str.length + 1) * 32;
                        }
                        if (curType.equals("address[]")) {
                            String[] str = funcParams[curIndex].get_param().substring(1, funcParams[curIndex].get_param().length() - 1).split(",");
                            curOffset += (str.length + 1) * 32;
                        }
                    }
                    dataTail.append(convert32Bytes(Integer.toHexString(curOffset)));
                    curType = "address[]";
                    curIndex = i;
                    dyTypes += "address[]-" + i + ",";
                }
            }
        }

        if (!dyTypes.isEmpty()) {
            String[] dy = dyTypes.substring(0, dyTypes.length() - 1).split(",");
            for (int i = 0; i < dy.length; i++) {
                if (dy[i].contains("bytes-")) {
                    String[] s = dy[i].split("-");
                    int index = Integer.parseInt(s[1]);
                    dataTail.append(convert32Bytes(Integer.toHexString(funcParams[index].get_param().length())));
                    dataTail.append(convertASC(funcParams[index].get_param()));
                }
                if (dy[i].contains("[")) {
                    if (dy[i].contains("uint")) {
                        String[] s = dy[i].split("-");
                        int index = Integer.parseInt(s[1]);
                        String[] arr = funcParams[index].get_param().substring(1, funcParams[index].get_param().length() - 1).split(",");
                        dataTail.append(convert32Bytes(Integer.toHexString(arr.length)));
                        for (int j = 0; j < arr.length; j++) {
                            dataTail.append(convert32Bytes(Integer.toHexString(Integer.parseInt(arr[j]))));
                        }
                    }
                    if (dy[i].contains("address")) {
                        String[] s = dy[i].split("-");
                        int index = Integer.parseInt(s[1]);
                        String[] arr = funcParams[index].get_param().substring(1, funcParams[index].get_param().length() - 1).split(",");
                        dataTail.append(convert32Bytes(Integer.toHexString(arr.length)));
                        for (int j = 0; j < arr.length; j++) {
                            dataTail.append(convert32Bytes(arr[j].substring(2)));
                        }
                    }
                }
            }
        }
        return new String(dataTail);
    }

    /**
     * convert string to ASCii String buffer
     *
     * @param string
     * @return
     */
    private static String convertASC(String string) {
        String s = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < string.length(); i++) {
            sb.append(Integer.toHexString(string.charAt(i) - '0' + '0'));
        }

        int len = sb.length();
        for (int i = 0; i < 64 - len; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    private static String convert32Bytes(String string) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 64 - string.length(); i++) {
            sb.append('0');
        }
        return sb.toString() + string;
    }


    public static ArrayList<Byte> hexStringToBytes(String src) {
        if (src.isEmpty()) {
            return new ArrayList<Byte>();
        }
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        char[] charArray = src.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            System.out.println(charArray[i]);
            char chr = charArray[i];
            System.out.println(((Byte.parseByte(Character.toString(chr), 16))));
            bytes.add(Byte.parseByte(Character.toString(chr), 16));
        }

        System.out.println(bytesToHexString(bytes));
        System.out.println(bytesToHexString(notByte(bytes)));
        return bytes;
    }


    public static ArrayList<Byte> notByte(ArrayList<Byte> bytes) {
        ArrayList<Byte> bytesAgain = new ArrayList<Byte>();
        for (int i = 0; i < bytes.size(); i++) {
            bytesAgain.add(intToByte(~(byteToInt(bytes.get(i)))));
        }
        return bytesAgain;
    }

    public static String bytesToHexString(ArrayList<Byte> bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bytes.size(); i++) {
            Byte b = bytes.get(i);
            stringBuffer.append(intToHex(byteToInt(b)));

        }
        return stringBuffer.toString();
    }

    private static char intToHex(int num) {
        if (num > 15 || num <= 0) {
            return '0';
        } else {
            return Character.forDigit(num, 16);
        }
    }

    //byte 与 int 的相互转换
    private static byte intToByte(int x) {
        return (byte) x;
    }

    private static int byteToInt(byte b) {
        //Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }


}

