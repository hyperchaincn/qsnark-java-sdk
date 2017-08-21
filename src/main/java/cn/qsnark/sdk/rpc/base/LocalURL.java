package cn.qsnark.sdk.rpc.base;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 下午4:40
 */
public enum LocalURL {

//    URL("127.0.0.1:8080");
    URL("127.0.0.1:8080");

    private String url;

    private LocalURL(String url) {
        this.url = url;
    }

    public String getType() {
        return this.url;
    }


}
