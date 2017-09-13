package cn.qsnark.sdk;

import okhttp3.*;

/**
 * Created by Hyperchain on 2017/9/12.
 */
public class TokenTest {
    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
        RequestBody body = RequestBody.create(mediaType, "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"client_id\"\r\n\r\ndd7314bb-e48f-43bd-a0cc-11ebcb977d49\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"client_secret\"\r\n\r\n1108M45t16X2F399706f9p12cv10Pq3H\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"username\"\r\n\r\n17612156863\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW\r\nContent-Disposition: form-data; name=\"password\"\r\n\r\ntajnzh10\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--");
        Request request = new Request.Builder()
                .url("https://api.hyperchain.cn/v1/token/gtoken")
                .post(body)
                .addHeader("content-type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "574b3d63-5d0c-04c1-5e95-17f98d2ec692")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
    }

}
