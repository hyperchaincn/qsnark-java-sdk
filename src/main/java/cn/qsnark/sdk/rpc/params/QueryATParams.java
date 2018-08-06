package cn.qsnark.sdk.rpc.params;

import net.sf.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 上午10:52
 */
public class QueryATParams implements Params {

    private String app_id;
    private String app_key;
    private String app_secret;
    private String access_type;

    public QueryATParams(String app_id, String app_key, String app_secret, String access_type) {
        this.app_id = app_id;
        this.app_key = app_key;
        this.app_secret = app_secret;
        this.access_type = access_type;
    }

    public String getApp_id() {
        return app_id;
    }

    public String getApp_key() {
        return app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public String getAccess_type() {
        return access_type;
    }

    @Override
    public String serlize() {
        JSONObject jsonObject = JSONObject.fromObject(this);
        return jsonObject.toString();
    }
}
