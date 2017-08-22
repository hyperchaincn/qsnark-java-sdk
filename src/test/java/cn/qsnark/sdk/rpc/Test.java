package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.returns.GetTokenReturn;

import cn.qsnark.sdk.rpc.returns.InvokeConReturn;
import net.sf.json.JSONArray;
import org.junit.Assert;

import java.util.List;


/**
 * Created by hanmengwei on 2017/8/17.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        testInvokeContract();
    }

    //@Test
    public static void testInvokeContract() throws Exception{

        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\"constant\":false,\"inputs\":[{\"name\":\"num1\",\"type\":\"uint32\"},{\"name\":\"num2\",\"type\":\"uint32\"}],\"name\":\"add\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"a\",\"type\":\"uint256\"},{\"name\":\"b\",\"type\":\"uint256\"},{\"name\":\"c\",\"type\":\"uint256\"}],\"name\":\"add\",\"outputs\":[{\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getSum\",\"outputs\":[{\"name\":\"\",\"type\":\"uint32\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"increment\",\"outputs\":[],\"payable\":false,\"type\":\"function\"}]";



        //获取token
        GetTokenReturn tokenReturn = api.getAccess_Token("123","123","yeyc","hello");
        String token = tokenReturn.getToken_type() + " " + tokenReturn.getAccess_token();
        FuncParamReal param1 = new FuncParamReal("uint256",10);
        FuncParamReal param2 = new FuncParamReal("uint256",20);
        FuncParamReal param3 = new FuncParamReal("uint256",30);

        InvokeConReturn invokeConReturn = api.invokeContract(token,false,"0x76fd78244f6a91d2fbe047a10a9b78e682474e7a",
                "0x0313a1bafe60a0e03e000dfb2fafda7f7a7fbd3b",
                abi, new InvCallback() {
                    @Override
                    public void onCompute(List list) {
                        System.out.println("this is my test");
                        System.out.println(list);
                    }
                }, "add",param1,param2,param3);

        System.out.println("code "+invokeConReturn.getCode());
        System.out.println("txHash "+invokeConReturn.getTxHash());
        System.out.println("data "+invokeConReturn.getData());
        System.out.println("error "+invokeConReturn.getError());
        System.out.println("message "+invokeConReturn.getMessage());
        System.out.println("status "+invokeConReturn.getStatus());


    }
}
