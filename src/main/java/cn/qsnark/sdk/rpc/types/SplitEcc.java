package cn.qsnark.sdk.rpc.types;

import cn.qsnark.sdk.rpc.utils.ByteUtil;

/**
 * Created by xieyijun on 16-12-28.
 */
public class SplitEcc {
    public  byte[]  eccamount1;
    public  byte[]  eccamount2;
    public  byte[]  eccamount3;

    public SplitEcc(String eccamount1, String eccamount2, String eccaomunt3){
        this.eccamount1 = ByteUtil.hexStringToBytes(eccamount1);
        this.eccamount2 = ByteUtil.hexStringToBytes(eccamount2);
        this.eccamount3 = ByteUtil.hexStringToBytes(eccaomunt3);
//        this.eccamount1 = new BigInteger(eccamount1,10);
//        this.eccamount2 = new BigInteger(eccamount2,10);
//        this.eccamount3 = new BigInteger(eccaomunt3,10);
    }
}
