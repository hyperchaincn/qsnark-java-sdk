package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.HttpRequestManager.DeploysyncConManager;
import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.callback.DevCallback;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.returns.*;

import java.io.IOException;
import java.util.List;

public class CompileTest {
    // auth info
    private static final String auth_phone = "13056961943";
    private static final String auth_password = "123456";
    private static final String auth_client_id = "4909d978-fb21-45e2-974d-c7b6a9c17067";
    private static final String auth_client_secret = "868v4oq14w1DvGt6Bft19rQ3091t2589";

    public static void main(String[] args) throws IOException, InterruptedException, TxException {

        QsnarkAPI api = new QsnarkAPI();
        GetTokenReturn getTokenReturn = api.getAccess_Token(auth_client_id,auth_client_secret,auth_phone,auth_password);
        String token = getTokenReturn.getAccess_token();

        String contract = new GetContractTest().GetContract("c3");
        String from = api.createAccount(token).getAddress();
        contract = "contract Accumulator{    uint32 sum = 0;   function increment(){         sum = sum + 1;     }      function getSum() returns(uint32){         return sum;     }   function add(uint32 num1,uint32 num2) {         sum = sum+num1+num2;     } }";        new QsnarkAPI().compileContract("JZYNAR3MMLWBRIH5TIIHQQ",contract);
        CompileReturn compileReturn = api.compileContract(token,contract);
        String bin = compileReturn.getCts_bin();
        String abi = compileReturn.getCts_abi();

        GetTxReciptReturn getTxReciptReturn  = api.deploysyncContract(token,bin,from);


        String to = getTxReciptReturn.getContract_address();

        InvokeConReturn invokeConReturn = api.invokeContract(token, false, from, to, abi, new InvCallback() {
            @Override
            public void onCompute(List ret) {
                System.out.println(ret);
            }
        },"getSum",null);


    }
}
