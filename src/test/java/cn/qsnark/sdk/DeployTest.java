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
//        deployArgs();
        deploysync();
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
                "0x738fdc2553b5cdcae43952539dcb04b3ae621ee1", new DevCallback() {
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
        GetTxReciptReturn txReciptReturn = api.deploysyncContract("GZVXNOQVNKQO0XHIRAIIEA", "0x6060604052341561000f57600080fd5b5b6102328061001f6000396000f300606060405263ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416638fabb12b811461003d575b600080fd5b341561004857600080fd5b61006f60043560243573ffffffffffffffffffffffffffffffffffffffff604435166100e7565b60405183815273ffffffffffffffffffffffffffffffffffffffff8216604082015260606020820181815290820184818151815260200191508051906020019060200280838360005b838110156100d15780820151818401525b6020016100b8565b5050505090500194505050505060405180910390f35b60006100f16101a9565b60008080805480600101828161010791906101bb565b916000526020600020900160005b5087905550805481906001810161012c83826101bb565b916000526020600020900160005b50879055508054879082908790829060208082020160405190810160405280929190818152602001828054801561019157602002820191906000526020600020905b8154815260019091019060200180831161017c575b505050505091509350935093505b5093509350939050565b60206040519081016040526000815290565b8154818355818115116101df576000838152602090206101df9181019083016101e5565b5b505050565b61020391905b808211156101ff57600081556001016101eb565b5090565b905600a165627a7a72305820cc3a81b668ae149a95524f09aaeb061f2ddff0f71c789ca61593b10aa97fd8ce0029", "0x738fdc2553b5cdcae43952539dcb04b3ae621ee1");
        System.out.println(txReciptReturn.getContract_address());
        System.out.println(txReciptReturn.getCode());
        System.out.println(txReciptReturn.getTxHash());
        System.out.println(txReciptReturn.getPoststate());
        System.out.println(txReciptReturn.getRet());
        System.out.println(txReciptReturn.getStatus());
    }
}
