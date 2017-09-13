package cn.qsnark.sdk.rpc;

import cn.qsnark.sdk.rpc.utils.OkHttpClientUtil;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Hyperchain on 2017/9/12.
 */
public class TokenTest {
    public static void main(String[] args) throws Exception{
        String str = "username=" + "17706421110" + "&password=" + "123" + "&client_id=" +"123" + "&client_secret=" + "123";
        RequestBody body = RequestBody.create(null, str);
        Request req = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .post(body)
                .url("http://localhost:8080"+"/v1/token/gtoken")
                .build();
        Response response = new OkHttpClientUtil().getClient().newCall(req).execute();
        System.out.println(response.body().string());
    }
}
