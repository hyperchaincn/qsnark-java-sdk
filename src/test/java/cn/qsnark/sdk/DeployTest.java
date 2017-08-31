package cn.qsnark.sdk;

import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.callback.DevCallback;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionParamException;
import cn.qsnark.sdk.rpc.returns.DeployConReturn;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: linxin
 * Date: 2017-06-05
 * Time: 下午6:58
 */

public class DeployTest {
    public static void main(String[] args) throws IOException, InterruptedException, FunctionParamException {
//        deployContract();
        deployArgs();
//        deploysync();
    }

    //顺序字符串
    //对应参数String token, String account_token, String bin, String from, int id, String _private, DevCallback callback
    public static void deployContract() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        DeployConReturn deployConReturn = api.deployContract("Bearer 60K-WEVJPMW3BXHSRUYGGA", "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029",
                "0x91049d6088088bf0c6c9c67d81375a89f648be0",
                new DevCallback() {
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

    public static void deployArgs() throws IOException, InterruptedException, FunctionParamException {
        FuncParamReal param1 = new FuncParamReal("uint32", 1);
        FuncParamReal param2 = new FuncParamReal("uint32", 2);
        QsnarkAPI api = new QsnarkAPI();
        String abi = "[{\"constant\":false,\"inputs\":[{\"name\":\"num1\",\"type\":\"uint32\"},{\"name\":\"num2\",\"type\":\"uint32\"}],\"name\":\"add\",\"outputs\":[],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"getSum\",\"outputs\":[{\"name\":\"\",\"type\":\"uint32\"}],\"payable\":false,\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"increment\",\"outputs\":[],\"payable\":false,\"type\":\"function\"}]";
        System.out.println(abi);
        DeployConReturn deployConReturn = api.deployArgsContract("Bearer 60K-WEVJPMW3BXHSRUYGGA",
                "0x60606040523415600b57fe5b604051602080605f83398101604052515b5b505b603380602c6000396000f30060606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029",
                "0x885b67411d84f6aa3b1e8e5ee6730c8123423777", new DevCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                }, abi);

        System.out.println(deployConReturn.getCode());
        System.out.println(deployConReturn.getMessage());
        System.out.println(deployConReturn.getError());
        System.out.println(deployConReturn.getStatus());
        System.out.println(deployConReturn.getTxHash());
    }

    public static void deploysync() throws IOException, InterruptedException, FunctionParamException {
        QsnarkAPI api = new QsnarkAPI();
        GetTxReciptReturn txReciptReturn = api.deploysyncContract("Bearer 60K-WEVJPMW3BXHSRUYGGA", "0x60606040523415600b57fe5b604051602080605f83398101604052515b5b505b603380602c6000396000f30060606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029", "0x885b67411d84f6aa3b1e8e5ee6730c8123423777");
        System.out.println(txReciptReturn.getContract_address());
        System.out.println(txReciptReturn.getCode());
        System.out.println(txReciptReturn.getMessage());
        System.out.println(txReciptReturn.getError());
        System.out.println(txReciptReturn.getTxHash());
        System.out.println(txReciptReturn.getPoststate());
        System.out.println(txReciptReturn.getRet());
        System.out.println(txReciptReturn.getStatus());
    }
}
