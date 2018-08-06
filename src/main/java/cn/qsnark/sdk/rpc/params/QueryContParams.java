package cn.qsnark.sdk.rpc.params;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-01
 * Time: 下午4:54
 */

public class QueryContParams   {

    private String token;
    private String pindex;
    private String psize;

    public QueryContParams(String token, String pindex, String psize) {

        this.token = token;
        this.pindex = pindex;
        this.psize = psize;
    }



    public String getToken() {
        return token;
    }



    public String getPindex() {
        return pindex;
    }

    public String getPsize() {
        return psize;
    }



}
