package cn.qsnark.sdk;

import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.returns.GetTokenReturn;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;
import cn.qsnark.sdk.rpc.returns.InvokeConReturn;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by apanoo on 2017/10/12.
 */
public class InvokeDecodeTest {
    // auth info
    private static final String auth_phone = "13056961943";
    private static final String auth_password = "123456";
    private static final String auth_client_id = "4909d978-fb21-45e2-974d-c7b6a9c17067";
    private static final String auth_client_secret = "868v4oq14w1DvGt6Bft19rQ3091t2589";

//    private static final String auth_phone = "17706421110";
//    private static final String auth_password = "123";
//    private static final String auth_client_id = "123";
//    private static final String auth_client_secret = "123";

    // from address
    private static final String FROM = "0x738fdc2553b5cdcae43952539dcb04b3ae621ee1";
    // contract address
    private static final String CONTRACT_ADDRESS = "0x52735ea369e07185bf852c4f9758a79bd63a9d8f";
    // contract ABI
    private static final String ABI = "[{\"constant\":false,\"inputs\":[],\"name\":\"getUsers\",\"outputs\":[{\"name\":\"users\",\"type\":\"address[]\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"fileHash\",\"type\":\"bytes\"}],\"name\":\"getEvidence\",\"outputs\":[{\"name\":\"code\",\"type\":\"uint256\"},{\"name\":\"fHash\",\"type\":\"bytes\"},{\"name\":\"fUpLoadTime\",\"type\":\"uint256\"},{\"name\":\"saverAddress\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"fileHash\",\"type\":\"bytes\"},{\"name\":\"fileUploadTime\",\"type\":\"uint256\"}],\"name\":\"saveEvidence\",\"outputs\":[{\"name\":\"code\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";

    static QsnarkAPI api = new QsnarkAPI();
    static String access_token = "";

    // saveEvidence sync
    private static void save_evidence(String hash) throws IOException, TxException, InterruptedException {
        FuncParamReal save_hash = new FuncParamReal("bytes", hash.getBytes(Charset.forName("UTF-8")));
        FuncParamReal save_time = new FuncParamReal("uint", System.currentTimeMillis());
        GetTxReciptReturn getTxReciptReturn = null;

        getTxReciptReturn = api.invokesyncContract(access_token, false, FROM, CONTRACT_ADDRESS, ABI, "saveEvidence", save_hash, save_time);

        if (getTxReciptReturn.getCode() != 0) {
            // invoke failed
            System.out.println(getTxReciptReturn.getStatus());
        }
    }

    // getEvidence async
    private static void get_evidence(String hash) throws IOException, TxException, InterruptedException {
        FuncParamReal save_hash = new FuncParamReal("bytes", hash.getBytes(Charset.forName("UTF-8")));
        InvokeConReturn invokeConReturn = null;

        invokeConReturn = api.invokeContract(access_token, false, FROM, CONTRACT_ADDRESS, ABI, new InvCallback() {
            @Override
            public void onCompute(java.util.List ret) {
                System.out.println(ret);
            }
        }, "getEvidence", save_hash);
    }

    // getUsers async
    private static void get_users() throws IOException, TxException, InterruptedException {
        InvokeConReturn invokeConReturn = null;

        invokeConReturn = api.invokeContract(access_token, false, FROM, CONTRACT_ADDRESS, ABI, new InvCallback() {
            @Override
            public void onCompute(java.util.List ret) {
                System.out.println(ret);
            }
        }, "getUsers");
    }

    public static void main(String[] args) throws Exception {


        GetTokenReturn getTokenReturn = api.getAccess_Token(auth_client_id, auth_client_secret, auth_phone, auth_password);

        access_token = getTokenReturn.getAccess_token();

//        save_evidence("apanoo");
//        get_evidence("apanoo");

        get_users();
    }
}

