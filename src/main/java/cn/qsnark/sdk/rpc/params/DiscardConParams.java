package cn.qsnark.sdk.rpc.params;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-02
 * Time: 下午2:05
 */
public class DiscardConParams {
    private String token;
    private String start;
    private String end;



    public DiscardConParams(String token, String start, String end) {
        this.token = token;
        this.start = start;
        this.end = end;

    }

    public String getToken() {
        return token;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}