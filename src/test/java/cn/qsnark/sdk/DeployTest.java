package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.ComCallback;
import cn.qsnark.sdk.rpc.returns.DeployArgsConReturn;
import cn.qsnark.sdk.rpc.returns.DeployConReturn;

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
//        deployContract();
        deployArgs();

    }

    //顺序字符串
    //对应参数String token, String account_token, String bin, String from, int id, String _private, ComCallback callback
    public static void deployContract() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        DeployConReturn deployConReturn = api.deployContract("Bearr FWYHAQMTWE6BPQWHPSGFXQ", "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029",
                "0x910419d6088088bf0c6c9c67d81375a89f648be0",
                new ComCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                });
        System.out.println(deployConReturn.getCode());
        System.out.println(deployConReturn.getMessage());
        System.out.println(deployConReturn.getError());
        System.out.println(deployConReturn.getStatus());
        System.out.println(deployConReturn.getTxHash());

    }

    public static void deployArgs() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"num1\\\",\\\"type\\\":\\\"uint32\\\"},{\\\"name\\\":\\\"num2\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"name\\\":\\\"add\\\",\\\"outputs\\\":[],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"getSum\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint32\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"increment\\\",\\\"outputs\\\":[],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"}]";
        DeployArgsConReturn deployArgsConReturn = api.deployArgsContract("Bearer AYU2ZNLBOPQUQSPUWOGZXW",
         abi, "[]", "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029",
                "0x910419d6088088bf0c6c9c67d81375a89f648be0", new ComCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                });

        System.out.println(deployArgsConReturn.getCode());
        System.out.println(deployArgsConReturn.getMessage());
        System.out.println(deployArgsConReturn.getError());
        System.out.println(deployArgsConReturn.getStatus());
        System.out.println(deployArgsConReturn.getTxHash());
    }
}
