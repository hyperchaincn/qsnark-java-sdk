package cn.qsnark.sdk.rpc.utils;

/**
 * description:
 * <p>
 * date 10/29/16
 *
 * @author chenquan
 * @version 1.0
 */

import cn.qsnark.sdk.rpc.types.ByteArrayWrapper;
import cn.qsnark.sdk.rpc.types.DataWord;
import cn.qsnark.sdk.rpc.types.KeyValueDataSource;
import org.spongycastle.jcajce.provider.digest.SHA3;
import org.spongycastle.util.encoders.DecoderException;
import org.spongycastle.util.encoders.Hex;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Utils {
    private static final DataWord DIVISOR = new DataWord(64);

    private static SecureRandom random = new SecureRandom();

    /**
     * @param number should be in form '0x34fabd34....'
     * @return String
     */
    public static BigInteger unifiedNumericToBigInteger(String number) {

        boolean match = Pattern.matches("0[xX][0-9a-fA-F]+", number);
        if (!match)
            return (new BigInteger(number));
        else {
            number = number.substring(2);
            number = number.length() % 2 != 0 ? "0".concat(number) : number;
            byte[] numberBytes = Hex.decode(number);
            return (new BigInteger(1, numberBytes));
        }
    }

    /**
     * Return formatted Date String: yyyy.MM.dd HH:mm:ss
     * Based on Unix's time() input in seconds
     *
     * @param timestamp seconds since start of Unix-time
     * @return String formatted as - yyyy.MM.dd HH:mm:ss
     */
    public static String longToDateTime(long timestamp) {
        Date date = new Date(timestamp * 1000);
        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return formatter.format(date);
    }

    public static ImageIcon getImageIcon(String resource) {
        URL imageURL = ClassLoader.getSystemResource(resource);
        ImageIcon image = new ImageIcon(imageURL);
        return image;
    }

    static BigInteger _1000_ = new BigInteger("1000");

    public static String getValueShortString(BigInteger number) {
        BigInteger result = number;
        int pow = 0;
        while (result.compareTo(_1000_) == 1 || result.compareTo(_1000_) == 0) {
            result = result.divide(_1000_);
            pow += 3;
        }
        return result.toString() + "\u00b7(" + "10^" + pow + ")";
    }

    /**
     * Decodes a hex string to address bytes and checks validity
     *
     * @param hex - a hex string of the address, e.g., 6c386a4b26f73c802f34673f7248bb118f97424a
     * @return - decode and validated address byte[]
     */
    public static byte[] addressStringToBytes(String hex) {
        final byte[] addr;
        try {
            addr = Hex.decode(hex);
        } catch (DecoderException addressIsNotValid) {
            return null;
        }

        if (isValidAddress(addr))
            return addr;
        return null;
    }

    public static boolean isValidAddress(byte[] addr) {
        return addr != null && addr.length == 20;
    }

    /**
     * @param addr length should be 20
     * @return short string represent 1f21c...
     */
    public static String getAddressShortString(byte[] addr) {

        if (!isValidAddress(addr)) throw new Error("not an address");

        String addrShort = Hex.toHexString(addr, 0, 3);

        StringBuffer sb = new StringBuffer();
        sb.append(addrShort);
        sb.append("...");

        return sb.toString();
    }

    public static SecureRandom getRandom() {
        return random;
    }

    public static double JAVA_VERSION = getJavaVersion();

    static double getJavaVersion() {
        String version = System.getProperty("java.version");

        // on android this property equals to 0
        if (version.equals("0")) return 0;

        int pos = 0, count = 0;
        for (; pos < version.length() && count < 2; pos++) {
            if (version.charAt(pos) == '.') count++;
        }
        return Double.parseDouble(version.substring(0, pos - 1));
    }

    public static String getHashListShort(List<byte[]> blockHashes) {
        if (blockHashes.isEmpty()) return "[]";

        StringBuilder sb = new StringBuilder();
        String firstHash = Hex.toHexString(blockHashes.get(0));
        String lastHash = Hex.toHexString(blockHashes.get(blockHashes.size() - 1));
        return sb.append(" ").append(firstHash).append("...").append(lastHash).toString();
    }

    public static String getNodeIdShort(String nodeId) {
        return nodeId == null ? "<null>" : nodeId.substring(0, 8);
    }

    public static long toUnixTime(long javaTime) {
        return javaTime / 1000;
    }

    public static long fromUnixTime(long unixTime) {
        return unixTime * 1000;
    }

    public static <T> T[] mergeArrays(T[]... arr) {
        int size = 0;
        for (T[] ts : arr) {
            size += ts.length;
        }
        T[] ret = (T[]) Array.newInstance(arr[0].getClass().getComponentType(), size);
        int off = 0;
        for (T[] ts : arr) {
            System.arraycopy(ts, 0, ret, off, ts.length);
            off += ts.length;
        }
        return ret;
    }

    public static String align(String s, char fillChar, int targetLen, boolean alignRight) {
        if (targetLen <= s.length()) return s;
        String alignString = repeat("" + fillChar, targetLen - s.length());
        return alignRight ? alignString + s : s + alignString;

    }

    public static String repeat(String s, int n) {
        if (s.length() == 1) {
            byte[] bb = new byte[n];
            Arrays.fill(bb, s.getBytes()[0]);
            return new String(bb);
        } else {
            StringBuilder ret = new StringBuilder();
            for (int i = 0; i < n; i++) ret.append(s);
            return ret.toString();
        }
    }

    public static List<ByteArrayWrapper> dumpKeys(KeyValueDataSource ds) {

        ArrayList<ByteArrayWrapper> keys = new ArrayList<ByteArrayWrapper>();

        for (byte[] key : ds.keys()) {
            keys.add(ByteUtil.wrap(key));
        }
        Collections.sort(keys);
        return keys;
    }

    public static DataWord allButOne64th(DataWord dw) {
        DataWord ret = dw.clone();
        DataWord d = dw.clone();
        d.div(DIVISOR);
        ret.sub(d);
        return ret;
    }

    public static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    //this method will override the exist file
    public static void writeFile(String path,String content) throws  IOException{
        File file = new File(path);
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        // write in file
        bw.write(content);
        // close connection
        bw.close();
        fw.close();
    }

    public static void writeFile(File file,String content) throws  IOException{
        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        // write in file
        bw.write(content);
        // close connection
        bw.close();
        fw.close();
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] SHA3(byte[]... inputs) {
        SHA3.DigestSHA3 sha3 = new SHA3.Digest256();
        for (byte[] input : inputs) {
            sha3.update(input);
        }
        return sha3.digest();
    }

    /**
     * Return XOR of two byte array of different or same size.
     * @param data1 data1
     * @param data2 data2
     * @return difference
     */
    public static byte[] bytesXOR(byte[] data1, byte[] data2) {
        // make data2 the largest...
        if (data1.length > data2.length) {
            byte[] tmp = data2;
            data2 = data1;
            data1 = tmp;
        }
        for (int i = 0; i < data1.length; i++) {
            data2[i] ^= data1[i];
        }
        return data2;
    }

    public static String hashToString(byte[] hash) {
        StringBuffer buff = new StringBuffer();

        for (byte b : hash) {
            buff.append(String.format("%02x", b & 0xFF));
        }

        return buff.toString();
    }

    /**
     * 将一个字符串转化为输入流
     */
    public static InputStream getStringStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see Random#nextInt(int)
     */
    public static int randInt(int min, int max) {

        // NOTE: This will (intentionally) not run as written so that folks
        // copy-pasting have to think about how to initialize their
        // Random instance.  Initialization of the Random instance is outside
        // the main scope of the question, but some decent options are to have
        // a field that is initialized once and then re-used as needed or to
        // use ThreadLocalRandom (if using at least Java 1.7).
        Random rand = new Random();;

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


    public static long genNonce(){
        SecureRandom random = new SecureRandom();
        String nonceString =  new BigInteger(120, random).toString(10);
        nonceString = nonceString.substring(0,16);
        return Long.parseLong(nonceString);
    }

    //网络传输 编码函数
    public static String transportEncode(String s){
        byte[] b = s.getBytes();
        return ByteUtil.toHexString(b);
    }
    //网络传输 解码函数
    public static String transportDecode(String s) throws UnsupportedEncodingException {
        byte[] b = ByteUtil.hexStringToBytes(s);
        return new String(b, "UTF-8");
    }
}