package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.InvCallback;
import cn.qsnark.sdk.rpc.returns.InvokeConReturn;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-09
 * Time: 下午7:23
 */
public class InvokeTest {
    public static void main(String[] args) throws Exception {
//        invokeContract();
//        invokeContract2();
//        invokeContractNopay();
        invokeContractNopay2();
    }


    //给出payload 顺序参数格式
    //String func_name, String from, String payload, String _private, String to, String abi, InvCallback callback
    public static void invokeContract() throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"num1\\\",\\\"type\\\":\\\"uint32\\\"},{\\\"name\\\":\\\"num2\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"name\\\":\\\"add\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"}]";
        InvokeConReturn invokeConReturn = api.invokeContract("Bearer D5VV0OZOP9ODQYU6EH0KKQ","add", "0x9e33ffae1477a33233126c6680d418e0fb1ed219"
                , "0x60606040523415600b57fe5b5b608b8061001a6000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146020575bfe5b3415602757fe5b603c63ffffffff600435811690602435166055565b6040805163ffffffff9092168252519081900360200190f35b8181015b929150505600a165627a7a7230582065c0eab350c9d860178b3851d8b3558d7280114749be7ce9f035c8afbe3ae0350029"
                , "0x692a70d2e424a56d2c6c27aa97d1a86395877b3a", abi, new InvCallback() {
                    @Override
                    public void onCompute(List ret) {
                        System.out.println(ret);
                    }
                });

    }


    //给出payload json字符窜格式
    public static void invokeContract2() throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"num1\\\",\\\"type\\\":\\\"uint32\\\"},{\\\"name\\\":\\\"num2\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"name\\\":\\\"add\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"}]";
        String s = "{\n" +
                "  \"token\": \"Bearer D5VV0OZOP9ODQYU6EH0KKQ\",\n" +
                "  \"func_name\": \"add\",\n" +
                "  \"abi\": \"" + abi + "\",\n" +
                "  \"from\": \"0x9e33ffae1477a33233126c6680d418e0fb1ed219\",\n" +
                "  \"payload\": \"3ad14af300000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000001\",\n" +
                "  \"to\": \"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\"\n" +
                "   }";
        System.out.println(s);
        InvokeConReturn invokeConReturn = api.invokeContract(s, new InvCallback() {
            @Override
            public void onCompute(List ret) {
                System.out.println(ret);
            }
        });

    }
    //给出jsonContract字符串格式 未给出payload 后台自动生成
    public static void invokeContractNopay() throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"num1\\\",\\\"type\\\":\\\"uint32\\\"},{\\\"name\\\":\\\"num2\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"name\\\":\\\"add\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"}]";
        String jsonContract = "{\"uint33\":\"1\",\"uint32\":\"1\"}";
        InvokeConReturn invokeConReturn = api.invokeContractNopay("Bearer D5VV0OZOP9ODQYU6EH0KKQ","add", jsonContract, "0x9e33ffae1477a33233126c6680d418e0fb1ed219"
                , "0x4dffc0eecde676583ebde1c29d39a0319bc8b1c7", abi, new InvCallback() {
                    @Override
                    public void onCompute(List ret) {
                        System.out.println(ret);
                    }
                });

    }

    //给出jsonContract字符串格式 未给出payload 后台自动生成 所有参数以json字符串的方式给出
    public static void invokeContractNopay2() throws Exception {
        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"num1\\\",\\\"type\\\":\\\"uint32\\\"},{\\\"name\\\":\\\"num2\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"name\\\":\\\"add\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"}]";
        String jsonContract = "{\"uint33\":\"1\",\"uint32\":\"1\"}";
        String s = "{\n" +
                "  \"token\": \"Bearer D5VV0OZOP9ODQYU6EH0KKQ\",\n" +
                "  \"func_name\": \"add\",\n" +
                "  \"jsonContract\": " + jsonContract + ",\n" +
                "  \"from\": \"0x9e33ffae1477a33233126c6680d418e0fb1ed219\",\n" +
                "  \"to\": \"0x692a70d2e424a56d2c6c27aa97d1a86395877b3a\",\n" +
                "  \"abi\": \"" + abi + "\"\n" +
                "}";
        System.out.println(s);
        InvokeConReturn invokeConReturn = api.invokeContractNopay(s, new InvCallback() {
            @Override
            public void onCompute(List ret) {
                System.out.println(ret);
            }
        });
    }

}
