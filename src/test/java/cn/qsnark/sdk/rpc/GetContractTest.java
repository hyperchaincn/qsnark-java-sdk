package cn.qsnark.sdk.rpc;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

public class GetContractTest {
    public String GetContract(String filePath){
        String fileName = "/home/hyper/workspace/qsnarksdk/src/test/resources/solidity.source/"+filePath+".sol";
        File file = new File(fileName);
        Long fileLengthLong = file.length();
        byte[] fileContent = new byte[fileLengthLong.intValue()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileContent);
            inputStream.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String string = new String(fileContent);
        return string;
    }
}
