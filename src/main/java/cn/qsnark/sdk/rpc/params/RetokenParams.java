package cn.qsnark.sdk.rpc.params;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午1:35
 */
public class RetokenParams implements Params {

    private String client_id;
    private String client_secret;
    private String retoken;


    public RetokenParams(String client_id, String client_secret, String retoken) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.retoken = retoken;

    }
    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getRetoken() {
        return retoken;
    }

    @Override
    public String serlize() {
        String str =
                "&refresh_token=" + this.retoken +
                "&client_id=" + this.client_id +
                "&client_secret=" + this.client_secret;
        return str;
    }
}
