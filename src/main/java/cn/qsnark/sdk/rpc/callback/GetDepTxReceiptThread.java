package cn.qsnark.sdk.rpc.callback;

import cn.qsnark.sdk.HttpRequestManager.GetTxReceiptManager;
import cn.qsnark.sdk.rpc.params.GetTxReceiptParams;
import cn.qsnark.sdk.rpc.returns.DeployConReturn;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-07
 * Time: 下午1:51
 */
public class GetDepTxReceiptThread implements Runnable {
    private String token;
    private DeployConReturn deployConReturn;
    private ComCallback callback;

    public GetDepTxReceiptThread(String token, DeployConReturn deployConReturn, ComCallback callback) {
        this.token = token;
        this.deployConReturn = deployConReturn;
        this.callback = callback;
    }

    @Override
    public void run() {
        int timeout = 10;
        String address = "";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < timeout; i++) {
            try {
                GetTxReceiptParams getTxReceiptParams = new GetTxReceiptParams(token, this.deployConReturn.getTxHash());
                GetTxReciptReturn getTxReciptReturn = new GetTxReciptReturn(new GetTxReceiptManager().SyncRequest(getTxReceiptParams));
                address = getTxReciptReturn.getContract_address();
                if (address == null || address.equals(""))
                    Thread.sleep(1000);
                if (address != null && !address.equals(""))
                    break;

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        callback.onCompute(address);
    }

}
