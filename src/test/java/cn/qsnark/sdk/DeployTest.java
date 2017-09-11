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
        deployContract();
//        deployArgs();
//        deploysync();
    }

    public static void deployContract() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        DeployConReturn deployConReturn = api.deployContract("Bearer HOFOKHIJNQEQOPI1CE7VHG", "0x60606040523415600e57600080fd5b5b603680601c6000396000f30060606040525b600080fd00a165627a7a723058202a3ccde541853d478de1bba386c895786c9c62e02482ee324aafcd0b66b32f9a0029",
                "0x2da68f91784639b59f388c2243d5df458f90ac4e",
                new DevCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                });
        System.out.println(deployConReturn.getCode());
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
        System.out.println(deployConReturn.getStatus());
        System.out.println(deployConReturn.getTxHash());
    }

    public static void deploysync() throws IOException, InterruptedException, FunctionParamException {
        QsnarkAPI api = new QsnarkAPI();
        GetTxReciptReturn txReciptReturn = api.deploysyncContract("Bearer 60K-WEVJPMW3BXHSRUYGGA", "0x60606040523415600b57fe5b604051602080605f83398101604052515b5b505b603380602c6000396000f30060606040525bfe00a165627a7a723058206f3d39eab86ddcfb556e1c6f43a60903e1ab88beb7909c0d49d7a25b1b1a15650029", "0x885b67411d84f6aa3b1e8e5ee6730c8123423777");
        System.out.println(txReciptReturn.getContract_address());
        System.out.println(txReciptReturn.getCode());
        System.out.println(txReciptReturn.getTxHash());
        System.out.println(txReciptReturn.getPoststate());
        System.out.println(txReciptReturn.getRet());
        System.out.println(txReciptReturn.getStatus());
    }
}
