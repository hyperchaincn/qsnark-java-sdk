package cn.qsnark.sdk.HttpRequestManager;

import cn.qsnark.sdk.rpc.base.HeadType;
import cn.qsnark.sdk.rpc.params.GetTokenParams;
import cn.qsnark.sdk.rpc.utils.OkHttpClientUtil;
import com.github.kevinsawicki.http.HttpRequest;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
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
public class GetTokenManager {


    private static Logger logger = Logger.getLogger(GetTokenManager.class);
    //media type
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //这是一个单例
//    public OkHttpClient httpClient = new OkHttpClient();


//    public String sourceURL = "127.0.0.1:14000/token";
    public String sourceURL = "https://"+HeadType.TOKENURL.getType()+"/token";


    public String SyncRequest(GetTokenParams params) throws IOException {

        Request req = null;
        req = Post(params);
        logger.debug("[REQUEST] " + params.serlize());
        logger.debug("[REQUEST] CURL： " + "curl -X POST --head " + "'" + params.serlize() + "'");
        Response response = null;
        try {
//            response = this.httpClient.newCall(req).execute();
            response = new OkHttpClientUtil().getClient().newCall(req).execute();
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

    public Request Post(GetTokenParams params) throws HttpRequest.HttpRequestException {


        Request request = null;

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType,
                "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: " +
                        "form-data; name=\"grant_type\"\r\n\r\n"+params.getGrant_type()+"\r\n" +
                        "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition:" +
                        " form-data; name=\"scope\"\r\n\r\n"+params.getScope()+"\r\n------" +
                        "WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition:" +
                        " form-data; name=\"username\"\r\n\r\n"+params.getUsername()+"\r\n------" +
                        "WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; " +
                        "name=\"password\"\r\n\r\n"+params.getPassword()+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n" +
                        "Content-Disposition: form-data; name=\"client_id\"\r\n\r\n"+params.getApp_key()+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW" +
                        "\r\nContent-Disposition: form-data; name=\"client_secret\"\r\n\r\n"+params.getApp_secret()+"\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");

         request = new Request.Builder()
                .url(sourceURL)
//                .url("https://127.0.0.1:14000/token")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("authorization", "Basic MTIzOjEyMw==")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "5b54b403-d435-85eb-2453-da5b1cce8aca")
                .build();

        return request;

    }
}
