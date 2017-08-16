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
        api.deployContract("Bearer MGDTJWVTPAENI3CZ7YVHIW", "0x60606040526000805463ffffffff19169055341561001957fe5b5b610101806100296000396000f300606060405263ffffffff60e060020a6000350416633ad14af381146034578063569c5f6d146052578063d09de08a146078575bfe5b3415603b57fe5b605063ffffffff600435811690602435166087565b005b3415605957fe5b605f60a9565b6040805163ffffffff9092168252519081900360200190f35b3415607f57fe5b605060b6565b005b6000805463ffffffff808216850184011663ffffffff199091161790555b5050565b60005463ffffffff165b90565b6000805463ffffffff8082166001011663ffffffff199091161790555b5600a165627a7a723058205196f5c898c244d3ada034d11893c7a5d67acac307f8e5db125810804cf7bb690029",
                "0x645aaec26e752d2249a7d3df9f8f6f2c10f71ac5",
                new ComCallback() {
                    @Override
                    public void onCompute(String address) {
                        System.out.println(address);
                    }
                });
    }

    //json格式字符串
    public static  void deployJsonContract() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        String s = "0x6060604052341561000c57fe5b5b610015610159565b61001d610159565b610025610159565b5050604080518082018252600181527f6e616d653100000000000000000000000000000000000000000000000000000060208083019190915282518084018452600281527f6e616d6532000000000000000000000000000000000000000000000000000000818301528351808501909452600384527f6e616d653300000000000000000000000000000000000000000000000000000091840191909152909250906100dc8364010000000061017361011182021704565b6100f28264010000000061017361011182021704565b6101088164010000000061017361011182021704565b5b5050506101bb565b600180548082016101228382610170565b916000526020600020900160005b508251908190556000908152602081815260409091208351815590830151600190910155505b50565b604080518082019091526000808252602082015290565b8154818355818115116101945760008381526020902061019491810190830161019a565b5b505050565b6101b891905b808211156101b457600081556001016101a0565b5090565b90565b610232806101ca6000396000f300606060405263ffffffff60e060020a600035041662ce8e3e811461002b578063d379dadf14610096575bfe5b341561003357fe5b61003b6100c2565b6040805160208082528351818301528351919283929083019185810191028083838215610083575b80518252602083111561008357601f199092019160209182019101610063565b5050509050019250505060405180910390f35b341561009e57fe5b6100a9600435610121565b6040805192835260208301919091528051918290030190f35b6100ca610161565b600180548060200260200160405190810160405280929190818152602001828054801561011657602002820191906000526020600020905b815481526020019060010190808311610102575b505050505090505b90565b6000818152602081905260408120548190156101545750506000818152602081905260409020805460019091015461015b565b5060009050805b5b915091565b60408051602081019091526000815290565b6001805480820161018483826101bb565b916000526020600020900160005b508251908190556000908152602081815260409091208351815590830151600190910155505b50565b8154818355818115116101df576000838152602090206101df9181019083016101e5565b5b505050565b61011e91905b808211156101ff57600081556001016101eb565b5090565b905600a165627a7a723058200f5b00ab00f7d737d02545182fd6c6ae605782c530b044de3097c6ba2e0a0bce0029";
        String jsonString = "{" + "\"token\":\"Bearer X1-HQOJ2NUKP3_FM8LG1CW\"," +
                "\n\"bin\":\"" + s + "\",\n\"from\":\"0x645aaec26e752d2249a7d3df9f8f6f2c10f71ac5\"" +
                "\n" + "}";
        System.out.println(jsonString);
        api.deployContract(
                jsonString,
                new ComCallback() {

                    @Override
                    public void onCompute(String address) {
                        System.out.println("onCompute执行了！");
                        System.out.println("address为" + address);
                    }

                });
    }

    public static  void deployArgs() throws IOException, InterruptedException {
        QsnarkAPI api = new QsnarkAPI();
        DeployArgsConReturn deployArgsConReturn = api.deployArgsContract("Bearer 3U8TGKKGNKEIO-09CVWHYQ","[{\\\"inputs\\\":[{\\\"name\\\":\\\"a\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"constructor\\\"}]",
                "1","[[\"0x123\",\"0x123\"]]","0x60606040523415600b57fe5b604051602080606483398101604052515b60008190555b505b60338060316000396000f30060606040525bfe00a165627a7a723058206a2bb8f4174cd222eed9413e8d1d9a5983a6ac5543196461c70953e7c4eaca9c0029",
                "0xeb28073ec2581727731805baab2fcbd13ea83b3f",0,"0xcf66f23e76f08c452ef21872b592d0ef8f9331d158f8bca49ac36a0bdb052f21");
        System.out.println(deployArgsConReturn.getStatus());
    }
}