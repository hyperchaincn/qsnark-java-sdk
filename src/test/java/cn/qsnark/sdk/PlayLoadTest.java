package cn.qsnark.sdk;

import cn.hyperchain.sdk.rpc.HyperchainAPI;
import cn.hyperchain.sdk.rpc.function.FuncParamReal;
import cn.hyperchain.sdk.rpc.function.FunctionDecode;
import cn.hyperchain.sdk.rpc.function.FunctionEncode;
import cn.hyperchain.sdk.rpc.returns.ReceiptReturn;
import cn.qsnark.sdk.sha3.Sha3;
import net.sf.json.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linxin on 2018/5/7.
 */
public class PlayLoadTest {
    public static void main(String[] args) throws Exception {


        String abbi = "[{\"constant\":true,\"inputs\":[{\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"getReceiptByIndex\",\"outputs\":[{\"name\":\"receipt\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"deleteReceiptByIndex\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"uint256\"}],\"name\":\"deleteReceiptById\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"index\",\"type\":\"uint256\"},{\"name\":\"receipt\",\"type\":\"string\"}],\"name\":\"updateReceiptByIndex\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"receipt\",\"type\":\"string\"}],\"name\":\"addReceipt\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"uint256\"},{\"name\":\"receipt\",\"type\":\"string\"}],\"name\":\"addReceiptById\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"getSize\",\"outputs\":[{\"name\":\"size\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"id\",\"type\":\"uint256\"},{\"name\":\"receipt\",\"type\":\"string\"}],\"name\":\"updateReceiptById\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"id\",\"type\":\"uint256\"}],\"name\":\"getReceiptById\",\"outputs\":[{\"name\":\"receipt\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"}]";
        FuncParamReal funcParamReal11 = new FuncParamReal("uint", 0 );
        FuncParamReal funcParamReal22 = new FuncParamReal("string", "1123456432");
        String payload = FunctionEncode.encodeFunction("addReceiptById",funcParamReal11,funcParamReal22);
        System.out.println(payload);
        Map<String, JSONObject> map = getMethodMap(abbi);
        String s = decodePayload(map,payload);
    }
    private static String decodePayload(Map<String, JSONObject> methodIdMap, String payload) throws Exception {


        //获取payload对应的方法abi字符串
        JSONObject abiJSON = methodIdMap.get(payload.substring(0, 10));
        if (null == abiJSON) {
            throw new Exception("abiJSON为空！解析payload失败！");
        }
        //获取该笔交易方法名
        String methodName = abiJSON.containsKey("name") ? abiJSON.getString("name") : "CONTRACT DEPLOY";
        //参数名与方法参数映射关系
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        com.alibaba.fastjson.JSONArray inputs = abiJSON.getJSONArray("inputs");
        // 方法有参数
        if (inputs.size() != 0) {
            JSONArray inputArray = null;
            try {
                inputArray = JSONArray.fromObject(net.sf.json.JSONObject.fromObject(
                        FunctionDecode.inputDecode(methodName, JSONArray.fromObject(abiJSON).toString(), payload.substring(10))
                ).get("decodedInput"));
            } catch (UnsupportedEncodingException e) {
                System.out.println("json解析方法payload失败！");
                throw new Exception("json解析方法payload失败！" + e.getMessage());
            }
            for (int j = 0; j < inputArray.size(); j++) {
                net.sf.json.JSONObject resultObject = net.sf.json.JSONObject.fromObject(inputArray.get(j));
                if (resultObject.getString("type").equals("array")) {
                    JSONArray arrayBody = JSONArray.fromObject(resultObject.getString("value"));
                    List<String> listArray = new ArrayList<String>();
                    for (int index = 0; index < arrayBody.size(); index++) {
                        net.sf.json.JSONObject value = arrayBody.getJSONObject(index);
                        listArray.add(value.getString("mayvalue"));
                    }
                    paramsMap.put(net.sf.json.JSONObject.fromObject(inputs.get(j)).get("name").toString(), listArray);
                } else {
                    paramsMap.put(net.sf.json.JSONObject.fromObject(inputs.get(j)).get("name").toString(), resultObject.getString("mayvalue"));
                }
            }
        }
        System.out.println(paramsMap);
        return "";
    }

    private static Map<String, JSONObject> getMethodMap(String abiStr) throws IOException {

        Map<String, JSONObject> map = new HashMap<>();
        com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(abiStr);
        for (Object o : jsonArray) {
            JSONObject method = (JSONObject) o;
            map.put(getMethodID(method), method);
        }
        System.out.println(map);
        return map;
    }

    private static String getMethodID(JSONObject jsonObject) {
        String fullName = "";
        fullName += jsonObject.getString("name") + "(";
        com.alibaba.fastjson.JSONArray inputs = jsonObject.getJSONArray("inputs");
        for (int i = 0; i < inputs.size(); i++) {
            JSONObject input = inputs.getJSONObject(i);
            fullName += input.getString("type");
            if (i < inputs.size() - 1) {
                fullName += ",";
            }
        }
        fullName += ")";
        String s = "0x" + Sha3.sha3(fullName).substring(0, 8);
        System.out.println(s);
        return "0x" + Sha3.sha3(fullName).substring(0, 8);

    }
}
