package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.DevCallback;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.returns.*;

import java.util.List;

/**
 * Created by linxin on 2017/8/21.
 */
public class QsnarkAPITest {
    public static void main(String[] args) throws Exception {

        QsnarkAPI api = new QsnarkAPI();
        GetTokenReturn getTokenReturn = api.getAccess_Token("123", "123", "17706421110", "123");
        String refresh = getTokenReturn.getRefresh_token();

        RetokenReturn retokenReturn = api.refAccess_Token("123", "123", refresh);
        String token = retokenReturn.getToken_type() + " " + retokenReturn.getAccess_token();
        System.out.println(token);

        CreteAccountReturn creteAccountReturn = api.createAccount(token);
        String address = creteAccountReturn.getAddress();

        String s = "contract Accumulate{ function Accumulate(uint32 param1, uint32 param2){ uint32 sum = param1 + param2;}}";
        CompileReturn compileReturn = api.compileContract(token, s);
        String bin = compileReturn.getCts_bin();

        DeployConReturn deployConReturn = api.deployContract(token, bin, address, new DevCallback() {
            @Override
            public void onCompute(String address) {
                System.out.println(address);
            }
        });

        Thread.currentThread().sleep(1000);

        String dephash = deployConReturn.getTxHash();

        GetTxReciptReturn getTxReciptReturn = api.getTxReceipt(token, dephash);

        String to = getTxReciptReturn.getContract_address();

        GetTxReciptReturn txReciptReturn = api.deploysyncContract(token, bin, address);

        FuncParamReal param1 = new FuncParamReal("uint32", 1);
        FuncParamReal param2 = new FuncParamReal("uint32", 2);
        DeployConReturn deployargConReturn = api.deployArgsContract(token,
                bin,
                address, new DevCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                }, compileReturn.getCts_abi(),param1,param2);

        Thread.currentThread().sleep(2000);
        String abi = compileReturn.getCts_abi();

        InvokeConReturn invokeConReturn = api.invokeContract(token, true, address
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


        String deparghash = deployargConReturn.getTxHash();

        GetTxReciptReturn getTxReciptReturndeparg = api.getTxReceipt(token, deparghash);


        QueryBlockReturn queryBlockReturn = api.queryBlock(token, "number", 1);
        PageBlocksReturn pageBlocksReturn = api.pageBlocks(token, 1, 1);
        RangeBlocksReturn rangeBlocksReturn = api.rangeBlocks(token, 1, 2);
      /*  NodesChainReturn nodesConReturn = api.nodesChain(token);*/
//        QueryContReturn queryContReturn = api.queryContract(token, "1", "11");
        StatusConReturn statusConReturn = api.statusContract(token, address);
        CountTraReturn countTraReturn = api.countTransaction(token);
        QueryTranReturn qreturn = api.queryTransaction(token, dephash);
        DiscardConReturn discardConReturn = api.discardTransaction(token, "1", "1581776001230590326");


    }

}
