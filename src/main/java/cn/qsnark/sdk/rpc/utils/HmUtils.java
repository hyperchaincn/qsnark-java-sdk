package cn.qsnark.sdk.rpc.utils;

import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionEncode;
import cn.qsnark.sdk.rpc.types.SplitEcc;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;

/**
 * Created by xieyijun on 16-12-26.
 */
public class HmUtils {
    //将一个经过椭圆曲线加密之后的值进行切分，传进来是一个byte[]
    //返回值是三个Biginteger类型
    public static  SplitEcc SplitEccamount(byte[] EccamountByte){
//        BigInteger eccamountBig = new BigInteger(1,EccamountByte);
//        String eccamountStr = eccamountBig.toString(10); //转化为１０进制数
//
//        String eccamount1 = eccamountStr.substring(0,75);
//        String eccamount2 = eccamountStr.substring(75,150);
//        String eccamount3 = eccamountStr.substring(150,eccamountStr.length());
//
//        SplitEcc splitEcc = new SplitEcc(eccamount1,eccamount2,eccamount3);
        String eccamountStr = Hex.toHexString(EccamountByte);
        String eccamount1 = eccamountStr.substring(0,64);
        String eccamount2 = eccamountStr.substring(64,128);
        String eccamount3 = eccamountStr.substring(128,eccamountStr.length())+"000000";
        SplitEcc splitEcc = new SplitEcc(eccamount1,eccamount2,eccamount3);
        return splitEcc;
    }

    //将从合约中得到的eccamount凑成一个Biginteger
    public static byte[] ComposeToEccamount(String ecctransfer1,String ecctransfer2,String ecctransfer3){
        StringBuilder sb = new StringBuilder();
        sb.append(ecctransfer1);
        sb.append(ecctransfer2);
        String temp = ecctransfer3.substring(0,58);
        //System.out.println("temp is ++++++++++++++++++++++++++++++++++++"+temp);
        sb.append(temp);
        //sb.toString();

        return ByteUtil.hexStringToBytes(sb.toString());
    }

    //生成调用合约中Transfer的payload
    //在Qsnark中用户可以选择使用此方法生成payload去调用invoke接口

    public static String GetInvokeTransferPayload(){
        FuncParamReal param1 = new FuncParamReal("uint32",new BigInteger("1"));
        FuncParamReal param2 = new FuncParamReal("uint32",new BigInteger("1"));
//        //传进去收款方椭圆曲线加密之后的转账金额
//        FuncParamReal param3 = new FuncParamReal("bytes32",RecsplitEcc.eccamount1);
//        FuncParamReal param4 = new FuncParamReal("bytes32",RecsplitEcc.eccamount2);
//        FuncParamReal param5 = new FuncParamReal("bytes32",RecsplitEcc.eccamount3);
//        //传进去支付方椭圆曲线加密之后的转账金额
//        FuncParamReal param6 = new FuncParamReal("bytes32",PaysplitEcc.eccamount1);
//        FuncParamReal param7 = new FuncParamReal("bytes32",PaysplitEcc.eccamount2);
//        FuncParamReal param8 = new FuncParamReal("bytes32",PaysplitEcc.eccamount3);

//        System.out.println(RecsplitEcc.eccamount1.toString(10));
//        System.out.println(RecsplitEcc.eccamount2.toString(10));
//        System.out.println(RecsplitEcc.eccamount3.toString(10));

//        FuncParamReal param9 = new FuncParamReal("address",to);

//        String payload = FunctionEncode.encodeFunction("Transfer",param1,param2,param3,param4,param5,param6,param7,param8,param9);
        String a = "subtract";
        String payload = FunctionEncode.encodeFunction(a,param1,param2);
        System.out.println(payload);
        return payload;
    }
}
