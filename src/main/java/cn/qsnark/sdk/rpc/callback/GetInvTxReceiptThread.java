package cn.qsnark.sdk.rpc.callback;

import cn.qsnark.sdk.HttpRequestManager.GetTxReceiptManager;
import cn.qsnark.sdk.rpc.function.FunctionDecode;
import cn.qsnark.sdk.rpc.params.GetTxReceiptParams;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;
import cn.qsnark.sdk.rpc.returns.InvokeConReturn;
import cn.qsnark.sdk.rpc.utils.AnalyzeRet;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-07
 * Time: 下午1:12
 */

public class GetInvTxReceiptThread implements Runnable {

    private String func_name;
    private String token;
    private InvokeConReturn invokeConReturn;
    private InvCallback callback;
    private String abi;
    private GetTxReceiptManager getTxReceiptManager = new GetTxReceiptManager();

    public GetInvTxReceiptThread(String func_name, String token, InvokeConReturn invokeConReturn, String abi, InvCallback callback) {
        this.func_name = func_name;
        this.abi = abi;
        this.token = token;
        this.invokeConReturn = invokeConReturn;
        this.callback = callback;
    }

    @Override
    public void run() {
        int timeout = 10;
        String ret = "";
        for (int i = 1; i < timeout; i++) {
            try {
                Thread.sleep(1000);
                GetTxReceiptParams getTxReceiptParams = new GetTxReceiptParams(token, this.invokeConReturn.getTxHash());
                GetTxReciptReturn getTxReciptReturn = new GetTxReciptReturn(this.getTxReceiptManager.SyncRequest(getTxReceiptParams));

                ret = getTxReciptReturn.getRet();
                System.out.println(ret);
                if (ret == null || ret.equals(""))
                    Thread.sleep(1000);
                if (ret != null && !ret.equals(""))
                    break;

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        List<Object> res = new AnalyzeRet().getRes(ret, abi, func_name);
        callback.onCompute(res);
    }

}
