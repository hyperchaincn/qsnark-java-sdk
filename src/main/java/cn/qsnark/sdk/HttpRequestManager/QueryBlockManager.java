package cn.qsnark.sdk.HttpRequestManager;

import cn.qsnark.sdk.rpc.base.HeadType;
import cn.qsnark.sdk.rpc.params.QueryBlockParams;
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
 * Date: 2017-05-31
 * Time: 下午4:56
 */
public class QueryBlockManager {


    private static Logger logger = Logger.getLogger(QueryBlockManager.class);
    //media type
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //这是一个单例
    public OkHttpClient httpClient = new OkHttpClient();


    public String sourceURL = HeadType.URL.getType() + "/v1/dev/block/query?";


    public String SyncRequest(QueryBlockParams params) throws IOException {

        Request req = null;
        req = Get(params);
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

                logger.error("the  url is " + this.sourceURL + "is incorrect" + ",please resend this request.");
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
    public Request Get(QueryBlockParams params) throws HttpRequest.HttpRequestException {
        String randomURL = sourceURL;
        Request request = null;

        request = new Request.Builder()
                .addHeader("Accept", HeadType.Accept.getType())
                .addHeader("Authorization", params.getToken())
                .url("http://" + randomURL + "type=" + params.getType() + "&value=" + params.getValue())
                .build();
        return request;

    }
}
