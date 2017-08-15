package cn.qsnark.sdk.rpc;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-08-14
 * Time: 下午8:51
 */


import org.testng.annotations.Test;

/**
 * Created by hanmengwei on 2017/8/14.
 */
public class QsnarkTest {


    @Test
    public void testCompileContract() throws Exception {
        QsnarkAPI s = new QsnarkAPI();
        System.out.println(s.toString());

    }

}