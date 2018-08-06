package cn.qsnark.sdk.rpc.function;

import cn.qsnark.sdk.rpc.types.IntType;
import cn.qsnark.sdk.rpc.types.NotSupportedTypeException;
import cn.qsnark.sdk.rpc.types.SolidityType;
import cn.qsnark.sdk.rpc.utils.ByteUtil;
import cn.qsnark.sdk.rpc.utils.Utils;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * <p>
 * date 10/28/16
 *
 * @author chenquan
 * @version 1.0
 */
public class FunctionDecode {
    /**
     * Ret is a inner class, and this is prepare for the decode to json string
     * the may value property means the maybe value
     */
    private static class Ret {
        String type;
        int index;
        Object value;
        String mayValue;
    }

    public static class RetV2 {
        String type;
        int index;
        Object value;
        Object mayValue;

        public String getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }

        public Object getMayValue() {
            return mayValue;
        }
    }


    /**
     * decode the smart contract invoke return data, and return a formatted json string.
     *
     * @param methodName the Smart Contract Method Name
     * @param abi        the Smart Contract Abi, which should start with `[` and end with `]`
     * @param encoded    the encode binary (hex) return data.
     * @return a formatted json string
     * @throws UnsupportedEncodingException if bytes con not decode with UTF-8 code
     */
    public static String resultDecode(String methodName, String abi, String encoded) throws UnsupportedEncodingException {
        // delete the start `0x`
        if (encoded.startsWith("0x")) {
            encoded = encoded.substring(2);
        }
        if (encoded.length() == 0) {
            return "[{\"error\":\"vm call error or result not exist\"}]";
        }
        if (encoded.length() < 32) {
            return "[{\"error\":\"method hasn't return value or param not correct\"}]";
        }
        /**
         * read the abi string to get outputs order
         * decode the abi string
         * and get the real need to handle target method body
         *
         */

        JSONArray abiArray = JSONArray.fromObject(abi);
        byte[] byteData = ByteUtil.hexStringToBytes(encoded);
        List<SolidityType> params = new ArrayList<SolidityType>();

        // method returns
        JSONArray methodReturns = new JSONArray();
        // get method return and generate the params which needs to decode
        boolean methodNameExist = false;
        for (int i = 0; i < abiArray.size(); i++) {
            JSONObject methodBody = (JSONObject) abiArray.get(i);
            if (methodBody.has("name") && methodBody.getString("name").equals(methodName) && !methodBody.getString("type").equals("constructor")) {
                methodNameExist = true;
                methodReturns = methodBody.getJSONArray("outputs");
                for (Object _return : methodReturns) {
                    JSONObject returnBody = (JSONObject) _return;
                    String returnType = returnBody.getString("type");
                    SolidityType solidityType = SolidityType.getType(returnType);
                    params.add(solidityType);
                }

            }
        }

        if (!methodNameExist) {
            return "[{\"error\":\"method name is not exist\"}]";
        }

        // use the solidity decode method to decode the return Data
        List<?> returns = FunctionDecode.decodeList(params, byteData);

        /**
         * convert the return data to Ret list
         * the Ret list was predefined.
         * this convert process need abi string to assist.
         * <p>
         *     先将return data 转换为 Ret list 用于进一步转换，
         *     转换过程需要abi进行协助
         * <p/>
         *
         */

        List<Ret> finalList = new ArrayList<Ret>();
        // convert to json
        for (int ofst = 0; ofst < returns.size(); ofst++) {
            Object ret = returns.get(ofst);
            JSONObject thisMethodRet = (JSONObject) methodReturns.get(ofst);
//            System.out.println(thisMethodRet.getString("type"));

            // if Object list
            if (ret.getClass().getName().equals("[Ljava.lang.Object;")) {

                ArrayList<Ret> arrayRet = new ArrayList<Ret>();
                Object[] array = (Object[]) ret;
                for (int offset = 0; offset < array.length; offset++) {
                    Ret ret_ele = new Ret();
                    Object ele = array[offset];
                    if (ele.getClass().getName().equals("java.math.BigInteger")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.math.BigInteger";
                        ret_ele.value = ele;
                        ret_ele.mayValue = ((BigInteger) ele).toString();
                        arrayRet.add(ret_ele);

                    } else if (ele.getClass().getName().equals("java.lang.String")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.lang.String";
                        ret_ele.value = ele;
                        ret_ele.mayValue = retToString((String) (ele));
                        arrayRet.add(ret_ele);

                    } else if (ele.getClass().getName().equals("java.lang.Boolean")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.lang.Boolean";
                        ret_ele.value = ele;
                        ret_ele.mayValue = ((Boolean) ele).toString();
                        arrayRet.add(ret_ele);

                    } else {
                        try {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
//                            ret_ele.mayValue = FunctionDecode.retToString(ByteUtils.toHexString((byte[]) ele));
                            arrayRet.add(ret_ele);
                            if (thisMethodRet.getString("type").equals("address[]")) {
                                ret_ele.type = "address";
                                ret_ele.mayValue = "0x" + ByteUtils.toHexString((byte[]) ele);
                            } else {
                                ret_ele.mayValue = retToString(ByteUtils.toHexString((byte[]) ele));
                            }
                            /**
                             * catch the json cannot convert exception
                             * this is the important exception catch
                             * IMPORTANT
                             */
                            JSONObject.fromObject("{\"data\":\"" + ret_ele.mayValue + "\"}");

                        } catch (UnsupportedEncodingException e) {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
                            ret_ele.mayValue = ByteUtils.toHexString((byte[]) ele);

                            arrayRet.add(ret_ele);
                        } catch (JSONException e) {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
                            ret_ele.mayValue = ByteUtils.toHexString((byte[]) ele);
                        }
                    }

                }
                Ret tempRet = new Ret();
                tempRet.value = arrayRet;
                tempRet.index = ofst;
                tempRet.type = "array";
                tempRet.mayValue = "array";
                finalList.add(tempRet);
            } else {
                Ret tempRet = new Ret();
                if (ret.getClass().getName().equals("[B")) {
                    tempRet.index = ofst;
                    tempRet.type = "bytes";
                    tempRet.value = ByteUtils.toHexString((byte[]) ret.getClass().cast(ret));
                    if (thisMethodRet.getString("type").equals("address")) {
                        tempRet.type = "address";
                        tempRet.mayValue = "0x" + ByteUtils.toHexString((byte[]) ret.getClass().cast(ret));

                    } else {
                        tempRet.mayValue = retToString(ByteUtils.toHexString((byte[]) ret.getClass().cast(ret)));
                    }

                    finalList.add(tempRet);
                } else {
                    tempRet.type = ret.getClass().getName();
                    tempRet.value = ret.getClass().cast(ret);
                    tempRet.mayValue = ret.getClass().cast(ret).toString();
                    tempRet.index = ofst;
                    finalList.add(tempRet);
                }
            }

        }

        /**
         * convert Ret list to json string.
         * this Ret return
         */
        StringBuffer result = new StringBuffer();
        result.append("[");
        boolean lastF1 = false, lastF2 = false;
        for (int ofset = 0; ofset < finalList.size(); ofset++) {
            FunctionDecode.Ret ret = finalList.get(ofset);
            if (ofset == (finalList.size() - 1)) {
                lastF1 = true;
            }
            // reset the lastF2  flag
            lastF2 = false;

            if (ret.type.equals("array")) {
                List<Ret> inner_rets = (List<Ret>) ret.value;
                result.append("{\"type\":\"" + ret.type + "\",\"value\":[");
                for (int ofset2 = 0; ofset2 < inner_rets.size(); ofset2++) {
                    FunctionDecode.Ret inner_ret = inner_rets.get(ofset2);
                    // this is the comma flag, if false add a comma in the end of string buffer
                    if (ofset2 == (inner_rets.size() - 1)) {
                        lastF2 = true;
                    }
                    result.append("{\"type\":\"" + inner_ret.type + "\",\"value\":\"" + inner_ret.value + "\",\"mayvalue\":\"" + inner_ret.mayValue + "\"}");
                    if (!lastF2) {
                        result.append(",");
                    }
                }
                result.append("],\"mayvalue\":\"" + ret.mayValue + "\"}");
                if (!lastF1) {
                    result.append(",");
                }
            } else {
                result.append("{\"type\":\"" + ret.type + "\",\"value\":\"" + ret.value + "\",\"mayvalue\":\"" + ret.mayValue + "\"}");
                if (!lastF1) {
                    result.append(",");
                }
            }

        }

        result.append("]");

        return result.toString();

    }


    public static String resultDecodeV2(String methodName, String abi, String encoded) throws UnsupportedEncodingException {
        // delete the start `0x`
        if (encoded.startsWith("0x")) {
            encoded = encoded.substring(2);
        }
        if (encoded.length() == 0) {
            return "[{\"error\":\"vm call error or result not exist\"}]";
        }
        if (encoded.length() < 32) {
            return "[{\"error\":\"method hasn't return value or param not correct\"}]";
        }
        /**
         * read the abi string to get outputs order
         * decode the abi string
         * and get the real need to handle target method body
         *
         */

        JSONArray abiArray = JSONArray.fromObject(abi);
        byte[] byteData = ByteUtil.hexStringToBytes(encoded);
        List<SolidityType> params = new ArrayList<SolidityType>();

        // method returns
        JSONArray methodReturns = new JSONArray();
        // get method return and generate the params which needs to decode
        boolean methodNameExist = false;
        for (int i = 0; i < abiArray.size(); i++) {
            JSONObject methodBody = (JSONObject) abiArray.get(i);
            if (methodBody.has("name") && methodBody.getString("name").equals(methodName) && !methodBody.getString("type").equals("constructor")) {
                methodNameExist = true;
                methodReturns = methodBody.getJSONArray("outputs");
                for (Object _return : methodReturns) {
                    JSONObject returnBody = (JSONObject) _return;
                    String returnType = returnBody.getString("type");
                    SolidityType solidityType = SolidityType.getType(returnType);
                    params.add(solidityType);
                }
            }
        }

        if (!methodNameExist) {
            return "[{\"error\":\"method name is not exist\"}]";
        }

        // use the solidity decode method to decode the return Data
        List<?> returns = FunctionDecode.decodeList(params, byteData);

        /**
         * convert the return data to Ret list
         * the Ret list was predefined.
         * this convert process need abi string to assist.
         * <p>
         *     先将return data 转换为 Ret list 用于进一步转换，
         *     转换过程需要abi进行协助
         * <p/>
         *
         */

        List<RetV2> finalList = new ArrayList<RetV2>();
        // convert to json
        for (int ofst = 0; ofst < returns.size(); ofst++) {
            Object ret = returns.get(ofst);
            JSONObject thisMethodRet = (JSONObject) methodReturns.get(ofst);
//            System.out.println(thisMethodRet.getString("type"));

            // if Object list
            // 如果是数组
            if (ret.getClass().getName().equals("[Ljava.lang.Object;")) {

                ArrayList<RetV2> arrayRet = new ArrayList<RetV2>();
                Object[] array = (Object[]) ret;
                for (int offset = 0; offset < array.length; offset++) {
                    RetV2 ret_ele = new RetV2();
                    Object ele = array[offset];
                    if (ele.getClass().getName().equals("java.math.BigInteger")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.math.BigInteger";
                        ret_ele.value = ele;
                        ret_ele.mayValue = ((BigInteger) ele).toString();
                        arrayRet.add(ret_ele);

                    } else if (ele.getClass().getName().equals("java.lang.String")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.lang.String";
                        ret_ele.value = ele;
                        ret_ele.mayValue = retToString((String) (ele));
                        arrayRet.add(ret_ele);

                    } else if (ele.getClass().getName().equals("java.lang.Boolean")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.lang.Boolean";
                        ret_ele.value = ele;
                        ret_ele.mayValue = ((Boolean) ele).toString();
                        arrayRet.add(ret_ele);

                    } else {
                        try {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
//                            ret_ele.mayValue = FunctionDecode.retToString(ByteUtils.toHexString((byte[]) ele));
                            arrayRet.add(ret_ele);
                            //review 这里的类型需要检查，thisMethodRet检查的是根返回值的类型 address[]
                            if (thisMethodRet.getString("type").equals("address[]")) {
                                ret_ele.type = "address";
                                ret_ele.mayValue = retToAddress(ByteUtils.toHexString((byte[]) ele));
                            } else {
                                ret_ele.mayValue = retToString(ByteUtils.toHexString((byte[]) ele));
                            }
                            /**
                             * catch the json cannot convert exception
                             * this is the important exception catch
                             * IMPORTANT
                             */
                            JSONObject.fromObject("{\"data\":\"" + ret_ele.mayValue + "\"}");

                        } catch (UnsupportedEncodingException e) {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
                            ret_ele.mayValue = ByteUtils.toHexString((byte[]) ele);

                            arrayRet.add(ret_ele);
                        } catch (JSONException e) {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
                            ret_ele.mayValue = ByteUtils.toHexString((byte[]) ele);
                        }
                    }

                }
                RetV2 tempRet = new RetV2();
                tempRet.value = arrayRet;
                tempRet.index = ofst;
                tempRet.type = "array";
                tempRet.mayValue = arrayRet;
                finalList.add(tempRet);
            } else {
                RetV2 tempRet = new RetV2();
                if (ret.getClass().getName().equals("[B")) {
                    tempRet.index = ofst;
                    tempRet.type = "bytes";
                    tempRet.value = ByteUtils.toHexString((byte[]) ret.getClass().cast(ret));
                    if (thisMethodRet.getString("type").equals("address")) {
                        tempRet.type = "address";
                        tempRet.mayValue = retToAddress(ByteUtils.toHexString((byte[]) ret.getClass().cast(ret)));
                    } else {
                        tempRet.mayValue = retToString(ByteUtils.toHexString((byte[]) ret.getClass().cast(ret)));
                    }

                    finalList.add(tempRet);
                } else {
                    tempRet.type = ret.getClass().getName();
                    tempRet.value = ret.getClass().cast(ret);
                    tempRet.mayValue = ret.getClass().cast(ret).toString();
                    tempRet.index = ofst;
                    finalList.add(tempRet);
                }
            }

        }

        /**
         * convert Ret list to json string.
         * this Ret return
         */
        StringBuffer result = new StringBuffer();
        result.append("[");
        boolean lastF1 = false, lastF2 = false;
        for (int ofset = 0; ofset < finalList.size(); ofset++) {
            FunctionDecode.RetV2 ret = finalList.get(ofset);
            if (ofset == (finalList.size() - 1)) {
                lastF1 = true;
            }
            // reset the lastF2  flag
            lastF2 = false;
            //如果是数组类型
            if (ret.type.equals("array")) {
                List<RetV2> inner_rets = (List<RetV2>) ret.value;
                result.append("{\"type\":\"" + ret.type + "\",\"mayvalue\":[");
                for (int ofset2 = 0; ofset2 < inner_rets.size(); ofset2++) {
                    FunctionDecode.RetV2 inner_ret = inner_rets.get(ofset2);
                    // this is the comma flag, if false add a comma in the end of string buffer
                    if (ofset2 == (inner_rets.size() - 1)) {
                        lastF2 = true;
                    }
                    result.append("{\"type\":\"" + inner_ret.type + "\",\"value\":\"" + inner_ret.value + "\",\"mayvalue\":\"" + inner_ret.mayValue + "\"}");
                    if (!lastF2) {
                        result.append(",");
                    }
                }
                result.append("],\"value\":" + JSONArray.fromObject(ret.mayValue).toString() + "}");
                if (!lastF1) {
                    result.append(",");
                }
            } else {
                result.append("{\"type\":\"" + ret.type + "\",\"value\":\"" + ret.value + "\",\"mayvalue\":\"" + ret.mayValue + "\"}");
                if (!lastF1) {
                    result.append(",");
                }
            }

        }

        result.append("]");

        return result.toString();

    }


    /**
     * @param methodName   方法名
     * @param abi          abi
     * @param encodedInput 需要解码的input
     * @return 解码之后的json字符串
     * @throws UnsupportedEncodingException -
     */
    public static String inputDecode(String methodName, String abi, String encodedInput) throws UnsupportedEncodingException {
        // delete the start `0x`
        if (encodedInput.startsWith("0x")) {
            encodedInput = encodedInput.substring(2);
        }
        if (encodedInput.length() == 0) {
            throw new UnsupportedEncodingException("input string is null");

        }
        if (encodedInput.length() > 0 && encodedInput.length() < 8) {
            throw new UnsupportedEncodingException("the input length is invalid, input length is :" + encodedInput.length());
        } else if (encodedInput.length() == 8) {
            return "{\"methodName\":\"" + methodName + "\",\"input\":[]}";
        } else if (encodedInput.length() > 8 && encodedInput.length() < 40) {
            throw new UnsupportedEncodingException("the input params length is invalid, input params length is :" + encodedInput.length());
        }
        /**
         * read the abi string to get outputs order
         * decode the abi string
         * and get the real need to handle target method body
         *
         */

        JSONArray abiArray = JSONArray.fromObject(abi);
        byte[] byteData = ByteUtil.hexStringToBytes(encodedInput);
        List<SolidityType> params = new ArrayList<SolidityType>();

        // method returns
        JSONArray methodInput = new JSONArray();
        // get method return and generate the params which needs to decode
        boolean methodNameExist = false;
        for (int i = 0; i < abiArray.size(); i++) {
            JSONObject methodBody = (JSONObject) abiArray.get(i);
            if (methodBody.has("name") && methodBody.getString("name").equals(methodName) && !methodBody.getString("type").equals("constructor")) {
                methodNameExist = true;
                //change output into input
                methodInput = methodBody.getJSONArray("inputs");
                for (Object _input : methodInput) {
                    JSONObject inputBody = (JSONObject) _input;
                    String returnType = inputBody.getString("type");
                    SolidityType solidityType = SolidityType.getType(returnType);
                    params.add(solidityType);
                }

            }
        }

        if (!methodNameExist) {
            return "{\"error\":\"method name is not exist or this method is constructor\"}";
        }

        // use the solidity decode method to decode the return Data
        List<?> returns = FunctionDecode.decodeList(params, byteData);

        /**
         * convert the return data to Ret list
         * the Ret list was predefined.
         * this convert process need abi string to assist.
         * <p>
         *     先将return data 转换为 Ret list 用于进一步转换，
         *     转换过程需要abi进行协助
         * <p/>
         *
         */

        List<Ret> finalList = getRets(methodInput, returns);

        /**
         * convert Ret list to json string.
         * this Ret return
         */
        StringBuffer result = getFinalString(methodName, finalList);

        return result.toString();
    }

    private static StringBuffer getFinalString(String methodName, List<Ret> finalList) {
        StringBuffer result = new StringBuffer();
        result.append("{\"methodName\":");
        result.append("\"" + methodName + "\",");
        result.append("\"decodedInput\":[");
        boolean lastF1 = false, lastF2 = false;
        for (int ofset = 0; ofset < finalList.size(); ofset++) {
            Ret ret = finalList.get(ofset);
            if (ofset == (finalList.size() - 1)) {
                lastF1 = true;
            }
            // reset the lastF2  flag
            lastF2 = false;

            if (ret.type.equals("array")) {
                List<Ret> inner_rets = (List<Ret>) ret.value;
                result.append("{\"type\":\"" + ret.type + "\",\"value\":[");
                for (int ofset2 = 0; ofset2 < inner_rets.size(); ofset2++) {
                    Ret inner_ret = inner_rets.get(ofset2);
                    // this is the comma flag, if false add a comma in the end of string buffer
                    if (ofset2 == (inner_rets.size() - 1)) {
                        lastF2 = true;
                    }
                    result.append("{\"type\":\"" + inner_ret.type + "\",\"value\":\"" + inner_ret.value + "\",\"mayvalue\":\"" + inner_ret.mayValue + "\"}");
                    if (!lastF2) {
                        result.append(",");
                    }
                }
                result.append("],\"mayvalue\":\"" + ret.mayValue + "\"}");
                if (!lastF1) {
                    result.append(",");
                }
            } else {
                result.append("{\"type\":\"" + ret.type + "\",\"value\":\"" + ret.value + "\",\"mayvalue\":\"" + ret.mayValue + "\"}");
                if (!lastF1) {
                    result.append(",");
                }
            }

        }

        result.append("]}");
        return result;
    }


    /**
     * @param abi          abi
     * @param encodedInput 需要解码的input
     * @return 解码之后的json字符串
     * @throws UnsupportedEncodingException -
     */
    public static String constrcutorDecode(String abi, String encodedInput) throws UnsupportedEncodingException {
        // delete the start `0x`
        if (encodedInput.startsWith("0x")) {
            encodedInput = encodedInput.substring(2);
        }
        if (encodedInput.length() == 0) {
            throw new UnsupportedEncodingException("input string is null");

        } else if (encodedInput.length() > 0 && encodedInput.length() < 32) {
            throw new UnsupportedEncodingException("the input params length is invalid, input params length is :" + encodedInput.length());
        }
        /**
         * read the abi string to get outputs order
         * decode the abi string
         * and get the real need to handle target method body
         *
         */

        JSONArray abiArray = JSONArray.fromObject(abi);
        byte[] byteData = ByteUtil.hexStringToBytes(encodedInput);
        List<SolidityType> params = new ArrayList<SolidityType>();

        // method returns
        JSONArray methodInput = new JSONArray();
        // get method return and generate the params which needs to decode
        boolean methodNameExist = false;
        for (int i = 0; i < abiArray.size(); i++) {
            JSONObject methodBody = (JSONObject) abiArray.get(i);
            if (methodBody.has("type") && methodBody.getString("type").equals("constructor")) {
                methodNameExist = true;
                //change output into input
                methodInput = methodBody.getJSONArray("inputs");
                for (Object _input : methodInput) {
                    JSONObject inputBody = (JSONObject) _input;
                    String returnType = inputBody.getString("type");
                    SolidityType solidityType = SolidityType.getType(returnType);
                    params.add(solidityType);
                }

            }
        }

        if (!methodNameExist) {
            return "{\"error\":\"constructor isn't exist\"}";
        }

        // use the solidity decode method to decode the return Data
        List<?> returns = FunctionDecode.decodeList(params, byteData);

        /**
         * convert the return data to Ret list
         * the Ret list was predefined.
         * this convert process need abi string to assist.
         * <p>
         *     先将return data 转换为 Ret list 用于进一步转换，
         *     转换过程需要abi进行协助
         * <p/>
         *
         */

        List<Ret> finalList = getRets(methodInput, returns);

        /**
         * convert Ret list to json string.
         * this Ret return
         */
        StringBuffer result = getFinalString("constructor", finalList);
        return result.toString();
    }

    private static List<Ret> getRets(JSONArray methodInput, List<?> returns) throws UnsupportedEncodingException {
        List<Ret> finalList = new ArrayList<Ret>();
        // convert to json
        for (int ofst = 0; ofst < returns.size(); ofst++) {
            Object ret = returns.get(ofst);
            JSONObject thisMethodRet = (JSONObject) methodInput.get(ofst);
//            System.out.println(thisMethodRet.getString("type"));

            // if Object list
            if (ret.getClass().getName().equals("[Ljava.lang.Object;")) {

                ArrayList<Ret> arrayRet = new ArrayList<Ret>();
                Object[] array = (Object[]) ret;
                for (int offset = 0; offset < array.length; offset++) {
                    Ret ret_ele = new Ret();
                    Object ele = array[offset];
                    if (ele.getClass().getName().equals("java.math.BigInteger")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.math.BigInteger";
                        ret_ele.value = ele;
                        ret_ele.mayValue = ((BigInteger) ele).toString();
                        arrayRet.add(ret_ele);

                    } else if (ele.getClass().getName().equals("java.lang.String")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.lang.String";
                        ret_ele.value = ele;
                        ret_ele.mayValue = retToString((String) (ele));
                        arrayRet.add(ret_ele);

                    } else if (ele.getClass().getName().equals("java.lang.Boolean")) {
                        ret_ele.index = offset;
                        ret_ele.type = "java.lang.Boolean";
                        ret_ele.value = ele;
                        ret_ele.mayValue = ((Boolean) ele).toString();
                        arrayRet.add(ret_ele);

                    } else {
                        try {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
//                            ret_ele.mayValue = FunctionDecode.retToString(ByteUtils.toHexString((byte[]) ele));
                            arrayRet.add(ret_ele);
                            if (thisMethodRet.getString("type").equals("address[]")) {
                                if (thisMethodRet.getString("type").startsWith("address")) {
                                    ret_ele.type = "address";
                                }
                                ret_ele.mayValue = "0x" + ByteUtils.toHexString((byte[]) ele);
                            } else {
                                ret_ele.mayValue = retToString(ByteUtils.toHexString((byte[]) ele));
                            }
                            /**
                             * catch the json cannot convert exception
                             * this is the important exception catch
                             * IMPORTANT
                             */
                            JSONObject.fromObject("{\"data\":\"" + ret_ele.mayValue + "\"}");

                        } catch (UnsupportedEncodingException e) {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            if (thisMethodRet.getString("type").startsWith("address")) {
                                ret_ele.type = "address";
                            }
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
                            ret_ele.mayValue = ByteUtils.toHexString((byte[]) ele);

                            arrayRet.add(ret_ele);
                        } catch (JSONException e) {
                            ret_ele.index = offset;
                            ret_ele.type = "bytes";
                            if (thisMethodRet.getString("type").startsWith("address")) {
                                ret_ele.type = "address";
                            }
                            ret_ele.value = ByteUtils.toHexString((byte[]) ele);
                            ret_ele.mayValue = ByteUtils.toHexString((byte[]) ele);
                        }
                    }

                }
                Ret tempRet = new Ret();
                tempRet.value = arrayRet;
                tempRet.index = ofst;
                tempRet.type = "array";
                tempRet.mayValue = "array";
                finalList.add(tempRet);
            } else {
                Ret tempRet = new Ret();
                if (ret.getClass().getName().equals("[B")) {
                    tempRet.index = ofst;
                    tempRet.type = "bytes";
                    tempRet.value = ByteUtils.toHexString((byte[]) ret.getClass().cast(ret));
                    if (thisMethodRet.getString("type").equals("address")) {
                        if (thisMethodRet.getString("type").startsWith("address")) {
                            tempRet.type = "address";
                        }
                        tempRet.mayValue = "0x" + ByteUtils.toHexString((byte[]) ret.getClass().cast(ret));
                    } else {
                        tempRet.mayValue = retToString(ByteUtils.toHexString((byte[]) ret.getClass().cast(ret)));
                        try {
                            String testJsonString = "{\"data\":\"" + tempRet.mayValue + "\"}";
                            JSONObject.fromObject(testJsonString);
                        } catch (JSONException e) {
                            tempRet.mayValue = ByteUtils.toHexString((byte[]) ret.getClass().cast(ret));
                        }

                    }

                    finalList.add(tempRet);
                } else {
                    tempRet.type = ret.getClass().getName();
                    tempRet.value = ret.getClass().cast(ret);
                    tempRet.mayValue = ret.getClass().cast(ret).toString();
                    tempRet.index = ofst;
                    finalList.add(tempRet);
                }
            }

        }
        return finalList;
    }


    /**
     * decode the ret data by Solidity decode method
     * <p>
     *
     * @param types   Solidity Type params
     * @param encoded encoded return data
     * @return Object list
     */
    private static List<?> decodeList(List<SolidityType> types, byte[] encoded) {

        List<Object> result = new ArrayList<Object>(types.size());

        int offset = 0;
        for (SolidityType type : types) {
            Object decoded = type.isDynamicType()
                    ? type.decode(encoded, IntType.decodeInt(encoded, offset).intValue())
                    : type.decode(encoded, offset);
            result.add(decoded);

            offset += type.getFixedSize();
        }

        return result;
    }


    /**
     * 解析返回值,可以解析复杂返回值,推荐用本方法解析返回值,返回值需要自己解析并强制转换类型
     *
     * @param methodName 需要解析的函数名
     * @param abiJson    相应整个合约的abi
     * @param data       返回数据
     * @return 返回值的一个Object Array list 需要自己向下转型
     */
    public static ArrayList<Object> complexDecode(String methodName, String abiJson, String data) throws NotSupportedTypeException, UnsupportedEncodingException {
        if (data.startsWith("0x")) {
            data = data.substring(2);
        }
        JSONArray jsonArray = JSONArray.fromObject(abiJson);
        ArrayList<Object> totalReturn = new ArrayList<Object>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject methodBody = (JSONObject) jsonArray.get(i);
            if (methodBody.has("name") && methodBody.getString("name").equals(methodName) && !methodBody.getString("type").equals("constructor")) {
                JSONArray returns = methodBody.getJSONArray("outputs");
                for (int offset = 0; offset < returns.size(); offset++) {
                    //get type
                    JSONObject returnBody = (JSONObject) returns.get(offset);
                    String returnType = returnBody.getString("type");
                    SolidityType solidityType = SolidityType.getType(returnType);
                    String groupData = data.substring(offset * 64, (offset + 1) * 64);
                    if (solidityType.isDynamicType()) {
                        if (solidityType.getCanonicalName().equals("bytes")) {
                            throw new NotSupportedTypeException("bytes is a not support type");
                        } else if (solidityType.getCanonicalName().endsWith("[]")) {
                            int dataStartIndex = Integer.valueOf(deleteLeftPadding(groupData), 16);
                            int ArraygoupOffset = dataStartIndex * 2 / 64;
                            String dataLengthStr = data.substring((ArraygoupOffset) * 64, (ArraygoupOffset + 1) * 64);
                            int dataGroupCount = Integer.valueOf(deleteLeftPadding(dataLengthStr), 16);
                            List<Object> arrayRet = new ArrayList<Object>();
                            for (int count = 0; count < dataGroupCount; count++) {
                                String arrayData = data.substring(dataStartIndex * 2 + (count + 1) * 64, dataStartIndex * 2 + (count + 2) * 64);
                                String mainTypeName = solidityType.getCanonicalName();
                                String subTypeName = mainTypeName.substring(0, mainTypeName.indexOf('['));
                                SolidityType subSolidityType = SolidityType.getType(subTypeName);
                                if (subSolidityType.getCanonicalName().equals("address")) {
                                    String arrayRealData = retToAddress(arrayData);
                                    arrayRet.add(arrayRealData);
                                } else {
                                    Object arrayRealData = subSolidityType.decode(ByteUtil.hexStringToBytes(arrayData), 0);
                                    arrayRet.add(arrayRealData);
                                }
                            }
                            totalReturn.add(arrayRet);
                        } else {
                            throw new NotSupportedTypeException("not dynamic type");
                        }

                    } else {
                        if (solidityType.getCanonicalName().equals("address")) {
                            totalReturn.add(retToAddress(groupData));
                        } else if (solidityType.getCanonicalName().equals("bytes32")) {
                            totalReturn.add(solidityType.decode(ByteUtil.hexStringToBytes(groupData), 0));
                        } else {
                            totalReturn.add(solidityType.decode(ByteUtil.hexStringToBytes(groupData), 0));
                        }
                    }
                }
                return totalReturn;
            }
        }
        return new ArrayList<Object>();
    }


    /**
     * 将返回值按int解析
     *
     * @param ele
     * @return
     * @throws UnsupportedEncodingException
     */
    public static BigInteger retToBigInteger(Object ele) throws UnsupportedEncodingException {
        if (ele.getClass().getName().equals("java.math.BigInteger")) {
            return (BigInteger) ele;
        } else {
            throw new UnsupportedEncodingException("not a bigInteger");
        }

    }


    /**
     * @param retGroup
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String retToAddress(String retGroup) throws UnsupportedEncodingException {
        //TODO check this if ok which don't reduce the string.
        return "0x" + retGroup.substring(24);
    }

    /**
     * 将单组返回值转为utf-8编码字符
     *
     * @param retGroup 返回值组
     * @return UTF-8编码字符串
     * @throws UnsupportedEncodingException if byte cannot decode into UTF-8
     */
    public static String retToString(String retGroup) throws UnsupportedEncodingException {
        if (retGroup.equals("")) {
            return "";
        }
        retGroup = retGroup.substring(0, reduceStringIndex(retGroup) + 1);
//        System.out.println(retGroup);
        if (checkIfAllZero(retGroup)) {
            return "00";
        }
        if (retGroup.equals("")) {
            return "";
        }
        return new String(ByteUtils.fromHexString(retGroup), "UTF-8");
    }

    private static int reduceStringIndex(String dataString) {
//        System.out.println(dataString);
        if (dataString.length() == 0) {
            return 0;
        }
        if (dataString.charAt(dataString.length() - 1) != '0') {
            return dataString.length() - 1;
        } else {
            for (int j = dataString.length() - 1; j >= 0; ) {
                if (dataString.charAt(j) == '0') {
                    j--;
                    continue;
                } else {
                    //如果dataString 0x686560 0000...
                    if (j % 2 == 0) return j + 1;
                    return j;
                }
            }
        }
        return 0;
    }

    private static boolean checkIfAllZero(String dataString) {
        for (int j = dataString.length() - 1; j >= 0; ) {
            if (dataString.charAt(j) == '0') {
                j--;
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * delete start  zero of a hex string
     *
     * @param dataString a hex binary string
     * @return the string which left zero
     */
    private static String deleteLeftPadding(String dataString) {
        if (dataString.charAt(0) != '0') {
            return dataString;
        } else {
            for (int j = 0; j < dataString.length(); j++) {
                if (dataString.charAt(j) == '0') {
                    continue;
                } else {
                    return dataString.substring(j);
                }
            }
        }
        return "0";
    }

    /**
     * 单值解析,利用传统方法解析,仅仅支持单值返回类型
     *
     * @param returnType 返回值类型
     * @param data       单值返回数据
     * @return 返回一个数值列表
     */
    @Deprecated
    public static List<String> decodeReturnValue(String returnType, String data) {
        if (data.startsWith("0x")) {
            data = data.substring(2, data.length());
        }
        List<String> list = new ArrayList<String>();

        if (returnType.contains("uint")) {
            int flag = 0;
            for (int i = 0; i < data.length(); i++) {
                if (!data.substring(i, i + 1).equals("0")) {
                    flag = i;
                    break;
                }
            }
            String value = data.substring(flag, data.length());
            list.add(Integer.parseInt(value, 16) + "");
            return list;
        } else if (returnType.contains("bool")) {
            list.add(data.substring(data.lastIndexOf("0") + 1, data.length()).equals("") ? "0" : "1");
            return list;
        } else if (returnType.contains("bytes")) {
//            int count = Integer.parseInt(returnType.substring(5, 6));
//			除去首部的0
//            String value = data.substring(2, count * 2 + 2);
            String value = data.substring(0, reduceStringIndex(data) + 1);
//			转换成字符串
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < value.length(); i = i + 2) {
                int num = Integer.parseInt(value.substring(i, i + 2), 16);
                sb.append((char) num);
            }
            list.add(sb.toString());
            return list;

        } else if (returnType.contains("address") && !returnType.contains("[")) {
            list.add("0x" + data.substring(24, data.length()));
            return list;
        } else if (returnType.contains("address[]")) {
            int i = 0;
            if (data.length() == 128) {
                return list;
            }
            data = data.substring(128, data.length());
            for (int count = 0; count < data.length(); ) {
                list.add("0x" + data.substring(24 + 64 * i, 64 + 64 * i));
                count = 64 * (i + 1);
                i++;
            }
            return list;
        } else {
            return list;
        }
    }

    public static String BytesToString(byte[] data) {
        String dataString = ByteUtil.toHexString(data);
        dataString = dataString.substring(0, reduceStringIndex(dataString) + 1);
        return new String(Utils.hexStringToByteArray(dataString));
    }
}
