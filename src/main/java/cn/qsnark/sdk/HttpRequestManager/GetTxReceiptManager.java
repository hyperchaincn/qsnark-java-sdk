package cn.qsnark.sdk.HttpRequestManager;

import cn.qsnark.sdk.rpc.base.HeadType;
import cn.qsnark.sdk.rpc.params.GetTxReceiptParams;
import com.github.kevinsawicki.http.HttpRequest;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 上午10:29
 */
public class GetTxReceiptManager {
    private static Logger logger = Logger.getLogger(GetTxReceiptParams.class);
    //media type
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //这是一个单例
    public OkHttpClient httpClient = new OkHttpClient();


    public String sourceURL = HeadType.URL.getType() + "/v1/dev/transaction/txreceipt?";


    public String SyncRequest(GetTxReceiptParams params) throws IOException {

        Request req = null;

        req = Get(params);

        logger.debug("[REQUEST] " + params.serlize());
        logger.debug("[REQUEST] CURL： " + "curl -X POST --head " + "'" + params.serlize() + "'");
        Response response = null;
        try {
            response = this.httpClient.newCall(req).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();

                logger.error("the  url is " + this.sourceURL +"is incorrect"+ ",please resend this request.");
            }
            return response.body().string();
        } else {
            logger.error("Incoming parameters are incorrect, please re-pass the parameters");
            return response.body().string();
        }

    }

    /**
     * 发送http post 请求,仅支持application/json类型
     *
     * @param params json请求字符串
     * @return 返回请求回来的string 一般是json格式
     * @throws HttpRequest.HttpRequestException -
     */
    public Request Get(GetTxReceiptParams params) throws HttpRequest.HttpRequestException {
        String randomURL = sourceURL;
        Request request = null;

        request = new Request.Builder()
                .addHeader("Accept", "Accept: text/html")
                .addHeader("Authorization", params.getToken())
                .url("http://" + randomURL + "txhash=" + params.getTxhash())
                .build();
        return request;

    }
}
