package cn.qsnark.sdk.rpc.params;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午4:54
 */

public class QueryContParams implements Params {

    private String access_token;
    private String token;
    private String appkey;
    private String pindex;
    private String psize;

    public QueryContParams(String access_token, String token, String appkey, String pindex, String psize) {
        this.access_token = access_token;
        this.token = token;
        this.appkey = appkey;
        this.pindex = pindex;
        this.psize = psize;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getToken() {
        return token;
    }

    public String getAppkey() {
        return appkey;
    }

    public String getPindex() {
        return pindex;
    }

    public String getPsize() {
        return psize;
    }

    @Override
    public String serlize() {
        return null;
    }

}
