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
        String bin = "0x6060604052341561000c57fe5b5b610c128061001c6000396000f3006060604052361561007d5763ffffffff60e060020a60003504166307d06cd9811461007f5780632272dd321461011257806372f3a56914610127578063821f94161461013c5780638bee87b114610195578063a0e69c9d146101fd578063de8fa43114610266578063ef13315d14610288578063f16bd5a2146102e1575bfe5b341561008757fe5b610092600435610374565b6040805160208082528351818301528351919283929083019185019080838382156100d8575b8051825260208311156100d857601f1990920191602091820191016100b8565b505050905090810190601f1680156101045780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561011a57fe5b61012560043561042a565b005b341561012f57fe5b6101256004356105a4565b005b341561014457fe5b60408051602060046024803582810135601f8101859004850286018501909652858552610125958335959394604494939290920191819084018382808284375094965061070895505050505050565b005b341561019d57fe5b6101eb600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284375094965061078295505050505050565b60408051918252519081900360200190f35b341561020557fe5b60408051602060046024803582810135601f81018590048502860185019096528585526101eb95833595939460449493929092019181908401838280828437509496506107c195505050505050565b60408051918252519081900360200190f35b341561026e57fe5b6101eb610840565b60408051918252519081900360200190f35b341561029057fe5b60408051602060046024803582810135601f8101859004850286018501909652858552610125958335959394604494939290920191819084018382808284375094965061084795505050505050565b005b34156102e957fe5b6100926004356108a1565b6040805160208082528351818301528351919283929083019185019080838382156100d8575b8051825260208311156100d857601f1990920191602091820191016100b8565b505050905090810190601f1680156101045780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61037c610994565b600180548390811061038a57fe5b906000526020600020900160005b50805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152929183018282801561041d5780601f106103f25761010080835404028352916020019161041d565b820191906000526020600020905b81548152906001019060200180831161040057829003601f168201915b505050505090505b919050565b60015460008054819081908590811061043f57fe5b906000526020600020900160005b5054915082841061045d5761059e565b50825b6001830381101561050f576001805482820190811061047b57fe5b906000526020600020900160005b50600180548390811061049857fe5b906000526020600020900160005b5081546104c69290600260001961010060018416150201909116046109a6565b5060008054600183019081106104d857fe5b906000526020600020900160005b505460008054839081106104f657fe5b906000526020600020900160005b50555b600101610460565b600082815260026020526040812061052691610a2c565b60018054600019850190811061053857fe5b906000526020600020900160005b6105509190610a2c565b60008054600019850190811061056257fe5b906000526020600020900160005b50600090819055805490610588906000198301610a74565b50600180549061059c906000198301610a9e565b505b50505050565b6001546000806105b384610947565b91508282106105c15761059e565b50805b6001830381101561067357600180548282019081106105df57fe5b906000526020600020900160005b5060018054839081106105fc57fe5b906000526020600020900160005b50815461062a9290600260001961010060018416150201909116046109a6565b50600080546001830190811061063c57fe5b906000526020600020900160005b5054600080548390811061065a57fe5b906000526020600020900160005b50555b6001016105c4565b600084815260026020526040812061052691610a2c565b60018054600019850190811061053857fe5b906000526020600020900160005b6105509190610a2c565b60008054600019850190811061056257fe5b906000526020600020900160005b50600090819055805490610588906000198301610a74565b50600180549061059c906000198301610a9e565b505b50505050565b806002600060008581548110151561071c57fe5b906000526020600020900160005b50548152602001908152602001600020908051906020019061074d929190610ac8565b508060018381548110151561075e57fe5b906000526020600020900160005b50815161077c9260200190610ac8565b505b5050565b6000600180548060010182816107989190610a9e565b916000526020600020900160005b5083516107b891906020860190610ac8565b5090505b919050565b6000828152600260209081526040822083516107df92850190610ac8565b5060008054600181016107f28382610a74565b916000526020600020900160005b5084905550600180548082016108168382610a9e565b916000526020600020900160005b50835161083691906020860190610ac8565b5090505b92915050565b6001545b90565b6000828152600260209081526040909120825161086692840190610ac8565b5080600161087384610947565b8154811061075e57fe5b906000526020600020900160005b50815161077c9260200190610ac8565b505b5050565b6108a9610994565b600082815260026020818152604092839020805484516001821615610100026000190190911693909304601f810183900483028401830190945283835291929083018282801561041d5780601f106103f25761010080835404028352916020019161041d565b820191906000526020600020905b81548152906001019060200180831161040057829003601f168201915b505050505090505b919050565b6001546000905b8082101561098d578260008381548110151561096657fe5b906000526020600020900160005b505414156109815761098d565b5b60019091019061094e565b5b50919050565b60408051602081019091526000815290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106109df5780548555610a1b565b82800160010185558215610a1b57600052602060002091601f016020900482015b82811115610a1b578254825591600101919060010190610a00565b5b50610a28929150610b9b565b5090565b50805460018160011615610100020316600290046000825580601f10610a525750610a70565b601f016020900490600052602060002090810190610a709190610b9b565b5b50565b81548183558181151161077c5760008381526020902061077c918101908301610b9b565b5b505050565b81548183558181151161077c5760008381526020902061077c918101908301610bbc565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610b0957805160ff1916838001178555610a1b565b82800160010185558215610a1b579182015b82811115610a1b578251825591602001919060010190610b1b565b5b50610a28929150610b9b565b5090565b81548183558181151161077c5760008381526020902061077c918101908301610bbc565b5b505050565b81548183558181151161077c5760008381526020902061077c918101908301610b9b565b5b505050565b61084491905b80821115610a285760008155600101610ba1565b5090565b90565b61084491905b80821115610a28576000610bd68282610a2c565b50600101610bc2565b5090565b905600a165627a7a72305820a57831e9b7ffb9440c0044a5adbfba4702a17b7ab94df2e821caeb9478b93fb70029";
        String token = "AQ1_OVXWN0CMCXFNKBL3LA";
        QsnarkAPI api = new QsnarkAPI();
        GetTxReciptReturn txReciptReturn = api.deploysyncContract(token, bin, "0x738fdc2553b5cdcae43952539dcb04b3ae621ee1");
        System.out.println(txReciptReturn.getContract_address());
        System.out.println(txReciptReturn.getCode());
        System.out.println(txReciptReturn.getTxHash());
        System.out.println(txReciptReturn.getPoststate());
        System.out.println(txReciptReturn.getRet());
        System.out.println(txReciptReturn.getStatus());
    }
}
