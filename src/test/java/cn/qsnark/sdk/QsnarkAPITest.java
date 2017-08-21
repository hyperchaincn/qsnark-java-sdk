package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.ComCallback;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.params.PageBlocksParams;
import cn.qsnark.sdk.rpc.returns.*;

import java.util.List;

/**
 * Created by linxin on 2017/8/21.
 */
public class QsnarkAPITest {
    public static void main(String[] args) throws Exception {

        QsnarkAPI api = new QsnarkAPI();
        GetTokenReturn getTokenReturn = api.getAccess_Token("123", "123", "yeyc", "hello");
        String refresh = getTokenReturn.getRefresh_token();

        RetokenReturn retokenReturn = api.refAccess_Token("123", "123", refresh);
        String token = retokenReturn.getToken_type()+" "+retokenReturn.getAccess_token();
        System.out.println(token);

        CreteAccountReturn creteAccountReturn = api.createAccount(token);
        String address = creteAccountReturn.getAddress();

        String s = "contract Accumulator{    uint32 sum = 0;   function increment(){         sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";
        CompileReturn compileReturn = api.compileContract(token, s);
        String bin = compileReturn.getCts_bin();
        DeployConReturn deployConReturn = api.deployContract(token, bin, address, new ComCallback() {
            @Override
            public void onCompute(String address) {
                System.out.println(address);
            }
        });
        Thread.currentThread().sleep(1000);
        String dephash = deployConReturn.getTxHash();

        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt(token, dephash);

        String to = getTxReciptReturn.getContract_address();


        String abi = "[{\"constant\":false,\"inputs\":[{\"name\":\"num1\",\"type\":\"uint32\"},{\"name\":\"num2\",\"type\":\"uint32\"}],\"name\":\"add\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getSum\",\"outputs\":[{\"name\":\"\",\"type\":\"uint32\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"increment\",\"outputs\":[],\"payable\":false,\"type\":\"function\"}]\n";

        InvokeConReturn invokeConReturn = api.invokeContract(token,true, address
                , to, abi, new InvCallback() {
                    @Override
                    public void onCompute(List ret) {
                        System.out.println(ret);
                    }
                }, "increment");
        Thread.currentThread().sleep(2000);

        String invhash = invokeConReturn.getTxHash();

        GetTxReciptReturn getTxReciptReturndep = api.getTxReceipt(token, invhash);

        MainTainReturn mainTainReturn = api.maintainContract(token, address, 1, bin, to);


        DeployConReturn deployConReturn2  = api.deployArgsContract(token,
                bin,
                address, new ComCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                },abi);

        Thread.currentThread().sleep(1000);

        String deparghash = deployConReturn2.getTxHash();

        GetTxReciptReturn getTxReciptReturndeparg = api.getTxReceipt(token, deparghash);



        QueryBlockReturn queryBlockReturn = api.queryBlock(token, "number", 1);

        PageBlocksReturn pageBlocksReturn = api.pageBlocks(token,1,1);
        RangeBlocksReturn rangeBlocksReturn = api.rangeBlocks(token, 1, 2);
        NodesChainReturn nodesConReturn = api.nodesChain(token);
        QueryContReturn queryContReturn = api.queryContract(token, "1", "11");
        StatusConReturn statusConReturn = api.statusContract(token, address);
        CountTraReturn countTraReturn = api.countTransaction(token);
        QueryTranReturn qreturn = api.queryTransaction(token, dephash);
        DiscardConReturn discardConReturn = api.discardTransaction(token, "1", "1581776001230590326");


    }

}
