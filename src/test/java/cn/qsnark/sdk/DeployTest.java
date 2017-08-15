package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.ComCallback;
import cn.qsnark.sdk.rpc.returns.DeployArgsConReturn;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-05
 * Time: 下午6:58
 */

public class DeployTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        deployContract();
//        deployJsonContract();
//        deployArgs();

    }

    //顺序字符串
    //对应参数String token, String account_token, String bin, String from, int id, String _private, ComCallback callback
    public static  void deployContract() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        api.deployContract("Bearer 3OTCARJTME2JXZPG_D0PNQ", "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029",
                "0x645aaec26e752d2249a7d3df9f8f6f2c10f71ac5",
                new ComCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                });
    }

    //json格式字符串
//    public static  void deployJsonContract() throws IOException, InterruptedException {
//        QsnarkAPI api = new QsnarkAPI();
//
//        String s = "0x60606040523415600b57fe5b5b608b8061001a60003" +
//                "96000f300606060405263ffffffff60e060020a6000350416633ad14af3811460" +
//                "20575bfe5b3415602757fe5b603c63ffffffff600435811690602435166055565b6040805163ffffffff909" +
//                "2168252519081900360200190f35b8181015b929150505600a165627a7a7230582065c0eab350c9d860178b3851d8" +
//                "b3558d7280114749be7ce9f035c8afbe3ae0350029";
//        String jsonString = "{" + "\"token\":\"Bearer YPNLR5IIOWMP-TKIJQJVIA\",\n\"access_token\":\"1\",\n\"bin\":\"" + s + "\",\n\"from\":\"0xeb28073ec2581727731805baab2fcbd13ea83b3f\"" +
//                ",\n\"id\":\"" + 0 + "\",\n\"private\":\"0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21\"" +
//                "\n" + "}";
//        api.deployContract(
//                jsonString,
//                new ComCallback() {
//
//                    @Override
//                    public void onCompute(String address) {
//                        System.out.println("onCompute执行了！");
//                        System.out.println("address为" + address);
//                    }
//
//                });
//    }

    public static  void deployArgs() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        DeployArgsConReturn deployArgsConReturn = api.deployArgsContract("Bearer 3U8TGKKGNKEIO-09CVWHYQ","[{\\\"inputs\\\":[{\\\"name\\\":\\\"a\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"constructor\\\"}]",
                "1","[[\"0x123\",\"0x123\"]]","0x60606040523415600b57fe5b604051602080606483398101604052515b60008190555b505b60338060316000396000f30060606040525bfe00a165627a7a723058206a2bb8f4174cd222eed9413e8d1d9a5983a6ac5543196461c70953e7c4eaca9c0029",
                "0xeb28073ec2581727731805baab2fcbd13ea83b3f",0,"0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21");
        System.out.println(deployArgsConReturn.getStatus());
    }
}
