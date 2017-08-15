package cn.qsnark.sdk.rpc.params;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午1:35
 */
public class RetokenParams   {

    private String grant_type;
    private String scope;
    private String client_id;
    private String client_secret;
    private String retoken;


    public RetokenParams(String grant_type, String scope, String client_id, String client_secret, String retoken) {
        this.grant_type =grant_type;
        this.scope = scope;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.retoken = retoken;

    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getScope() {
        return scope;
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
}
