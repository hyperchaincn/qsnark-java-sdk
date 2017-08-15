package cn.qsnark.sdk.rpc.base;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-05-31
 * Time: 下午4:40
 */
public enum HeadType {
    Content_Type("application/json"),
    Accept("application/json"),
//    URL("127.0.0.1:8080");
    URL("172.16.100.121:8080");

    private String type;

    private HeadType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }


}
