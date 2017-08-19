package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionEncode;
import cn.qsnark.sdk.rpc.returns.InvokeConReturn;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-09
 * Time: 下午7:23
 */
public class InvokeTest {
    public static void main(String[] args) throws Exception {
        invokeContract();
    }

    public static void invokeContract() throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        FuncParamReal param1 = new FuncParamReal("uint32", 1);
        FuncParamReal param2 = new FuncParamReal("uint32", 2);
        String abi = "[{\"constant\":false,\"inputs\":[{\"name\":\"num1\",\"type\":\"uint32\"},{\"name\":\"num2\",\"type\":\"uint32\"}],\"name\":\"add\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getSum\",\"outputs\":[{\"name\":\"\",\"type\":\"uint32\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"increment\",\"outputs\":[],\"payable\":false,\"type\":\"function\"}]\n";

        InvokeConReturn invokeConReturn = api.invokeContract("Bearer FWYHAQMTWE6BPQWHPSGFXQ", "0x9e33ffae1477a33233126c6680d418e0fb1ed219"
                , "0x4dffc0eecde676583ebde1c29d39a0319bc8b1c7", abi, new InvCallback() {
                        @Override
                        public void onCompute(List ret) {
                            System.out.println(ret);
                        }
                }, "", param1, param2);

    }


}
