package cn.qsnark.sdk.HttpRequestManager;

import cn.qsnark.sdk.rpc.base.BaseConfig;
import cn.qsnark.sdk.rpc.params.SignTransactionParams;
import com.github.kevinsawicki.http.HttpRequest;
import okhttp3.*;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午3:42
 */
public class SignatureManager {

    private static Logger logger = Logger.getLogger(SignatureManager.class);
    //media type
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //这是一个单例
    public OkHttpClient httpClient = new OkHttpClient();


    public String sourceURL = BaseConfig.URL.getType() + "/v1/dev/transaction/signature?";


    public String SyncRequest(SignTransactionParams params) throws IOException {
        Request req = null;
        req = Post(params);

        logger.debug("[REQUEST] " + params);
        logger.debug("[REQUEST] CURL： " + "curl -X POST --head " + "'" + params + "'");
        Response response = null;
        try {
            response = this.httpClient.newCall(req).execute();
        } catch (IOException e) {
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
    public Request Post(SignTransactionParams params) throws HttpRequest.HttpRequestException {
        RequestBody body = RequestBody.create(JSON, params.getBody().toString());
        String randomURL = sourceURL;
        Request request = null;

        request = new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", params.getToken())
                .post(body)
                .url(randomURL + "token="+params.get_token())
                .build();
        return request;

    }

}
