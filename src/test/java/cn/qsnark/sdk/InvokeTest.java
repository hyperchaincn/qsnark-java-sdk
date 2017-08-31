package cn.qsnark.sdk;

import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionEncode;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;
import cn.qsnark.sdk.rpc.returns.InvokeConReturn;

import java.io.IOException;
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
//        invokesyncContract();
    }

    public static void invokeContract() throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        FuncParamReal param1 = new FuncParamReal("uint32", 1);
        FuncParamReal param2 = new FuncParamReal("uint32", 2);
        String abi = "[{\"constant\":false,\"inputs\":[],\"name\":\"getAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"num1\",\"type\":\"uint32\"},{\"name\":\"num2\",\"type\":\"uint32\"}],\"name\":\"add\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getSum\",\"outputs\":[{\"name\":\"\",\"type\":\"uint32\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getString\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"increment\",\"outputs\":[],\"payable\":false,\"type\":\"function\"}]";
        System.out.println(abi);
        InvokeConReturn invokeConReturn = api.invokeContract("Bearer ZWUEW9TLN3Q_EMELYQFVAA", true,"0x7c2b111bce226cde5848a6304dc36922b7099491"
                , "0x83665f52cd2d201269da2faa6c877d2d037b991b", abi, new InvCallback() {
                    @Override
                    public void onCompute(List ret) {
                        System.out.println(ret);
                    }
                }, null);
        System.out.println(invokeConReturn.getCode());
        System.out.println(invokeConReturn.getMessage());
        System.out.println(invokeConReturn.getError());
        System.out.println(invokeConReturn.getData());
        System.out.println(invokeConReturn.getStatus());
        System.out.println(invokeConReturn.getTxHash());

    }
    public static void invokesyncContract() throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\"constant\":false,\"inputs\":[],\"name\":\"getAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"num1\",\"type\":\"uint32\"},{\"name\":\"num2\",\"type\":\"uint32\"}],\"name\":\"add\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getSum\",\"outputs\":[{\"name\":\"\",\"type\":\"uint32\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getString\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"increment\",\"outputs\":[],\"payable\":false,\"type\":\"function\"}]";
        System.out.println(abi);
        GetTxReciptReturn getTxReciptReturn = api.invokesyncContract("Bearer FFDCMMO4NXSHO29OVURYGW", true,"0x7c2b111bce226cde5848a6304dc36922b7099491"
                , "0x83665f52cd2d201269da2faa6c877d2d037b991b", abi, "getString");
    }
}
