package cn.qsnark.sdk.rpc.utils;

import okhttp3.OkHttpClient;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.security.KeyStore;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-08-11
 * Time: 下午5:24
 */
public class OkHttpClientUtil {
    public OkHttpClient getClient() {
        OkHttpClient.Builder builder = null;
        try {
            builder = new OkHttpClient.Builder();
            //ssl verifier
            KeyStore trustStore;
            trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactoryImp ssl = new SSLSocketFactoryImp(KeyStore.getInstance(KeyStore.getDefaultType()));

            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            builder.sslSocketFactory(ssl.getSSLContext().getSocketFactory(), ssl.getTrustManager())
                    .hostnameVerifier(DO_NOT_VERIFY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();

    }
}
