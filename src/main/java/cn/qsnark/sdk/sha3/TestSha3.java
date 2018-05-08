package cn.qsnark.sdk.sha3;

/**
 * Created by linxin on 2018/5/3.
 */
public class TestSha3 {
    public static void main(String[] args) {
      String s = Sha3.sha3("0000");
      System.out.println(s);
        String s2 = Sha3.sha3("1000");
        System.out.println(s2);
    }
}
