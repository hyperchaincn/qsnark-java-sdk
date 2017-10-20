package cn.qsnark.sdk.rpc.utils;

import cn.qsnark.sdk.rpc.function.FunctionDecode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzeRet {
    public List<Object> getRes(String ret,String abi,String func_name) {
        List<Object> res = null;
        Map<Object,Object> map = new HashMap<>();
        abi = abi.replace("\\", "");
        if (ret != null && !ret.equals(""))

        {
            try {
                String result = FunctionDecode.resultDecode(func_name, abi, ret);
                System.out.println("result:"+result);
                System.out.println("------------");
                if (result != null && !result.equals("")) {
                    JSONArray array = JSONArray.fromObject(result);
                    res = new ArrayList<Object>();

                    for (int i = 0; i < array.size(); i++) {
                        String temp = array.get(i).toString();
                        JSONObject jsonObject = JSONObject.fromObject(temp);
                        Object s = null;
                        String key=null;
                        String value=null;
                        System.out.println(jsonObject.toString());
                        if (jsonObject.containsKey("mayvalue")) {
                            if (jsonObject.containsKey("type") && jsonObject.getString("type").equals("array")) {
                                s = getArray(jsonObject);
                                map.put(s,"array");
                            } else {
                                s = jsonObject.getString("mayvalue");
                                 key = jsonObject.getString("mayvalue");
                                 value = jsonObject.getString("type");
                                map.put(key,value);
                            }
                        }

                        res.add(s);
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println(map);
        return res;
    }

    private Map<Object,Object> getArray(JSONObject jsonObject) {
        List<Object> list = new ArrayList<Object>();
        Map<Object,Object> map = new HashMap<>();
        JSONArray array = JSONArray.fromObject(jsonObject.getString("value"));
        for (int i = 0; i < array.size(); i++) {
            String temp = array.get(i).toString();
            JSONObject jo = JSONObject.fromObject(temp);
//            System.out.println("when i="+i+" object:"+jo.toString());
            if (jo.containsKey("type") && jo.getString("type").equals("array")) {
                list.add(getArray(jo));
                map.put(getArray(jo),"array");
            } else {
                list.add(jo.getString("mayvalue"));
                String key = jo.getString("mayvalue");
                String value = jo.getString("type");
                map.put(key,value);
            }
        }
        return map;
    }
}
