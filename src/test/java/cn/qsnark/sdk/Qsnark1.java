package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.DevCallback;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.returns.*;

import java.util.List;

/**
 * Created by linxin on 2017/8/23.
 */
public class Qsnark1 {
    public static void main(String[] args) throws Exception{
        QsnarkAPI api = new QsnarkAPI();
        GetTokenReturn getTokenReturn = api.getAccess_Token("123", "123", "17706421110", "123");
        String token = getTokenReturn.getAccess_token();
        CreteAccountReturn creteAccountReturn = api.createAccount(token);
        System.out.println(token);
        String address = creteAccountReturn.getAddress();

        String sourcecode=  "contract Accumulator{    uint32 sum = 0;   function increment()returns(uint32){        return sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";

        CompileReturn compileReturn = api.compileContract(token, sourcecode);

        String abi = compileReturn.getCts_abi();

        String bin = compileReturn.getCts_bin();

        FuncParamReal param1 = new FuncParamReal("uint32", 1);
        FuncParamReal param2 = new FuncParamReal("uint32", 2);

        DeployConReturn deployConReturn = api.deployArgsContract(token,
                bin,
                address, new DevCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                }, abi);
        Thread.currentThread().sleep(1000);
        String hash = deployConReturn.getTxHash();



        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt(token, hash);

        String to = getTxReciptReturn.getContract_address();

        MainTainReturn mainTainReturn = api.maintainContract(token, address, 1, bin, to);

        GetTxReciptReturn invokesyncContract = api.invokesyncContract(token, true,address
                , to, abi, "increment");



        InvokeConReturn invokeConReturn = api.invokeContract(token, true,address
                , to, abi, new InvCallback() {
                    @Override
                    public void onCompute(List ret) {
                        System.out.println(ret);
                    }
                }, "increment");

        Thread.currentThread().sleep(1000);

        DeployConReturn deployConReturn2 = api.deployContract(token, bin,
                address,
                new DevCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                });
        Thread.currentThread().sleep(1000);
        GetTxReciptReturn txReciptReturn = api.deploysyncContract(token, bin, address);



    }
}
