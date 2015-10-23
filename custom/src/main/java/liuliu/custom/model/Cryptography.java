package liuliu.custom.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by Administrator on 2015/9/30.
 */
public class Cryptography {

    private static String CHARSET = "UTF-8";
    private static String SALT = "";
    private static String Password = "A2D097D7A3208C31BBB1\r\n                             9A3564F88E27EF967ECC\r\n                             4AC3285F3A90DFD9FA1F\r\n                             386C94F4D9DB63B8DA5C\r\n                             C263041CCEE181EF74FF\r\n                             1BB637BAF9D604E33C76\r\n                             85D229EFC0534400F607\r\n                             8A8360150F968EA7F843\r\n                             39E21432A7DCBA489697\r\n                             B4EF779F4B0C6F08B89F";

    // 密码加密
    public static String HashPassword(String password, String salt) {

        try {
            password = password.trim();
            for (int i = 0; i < 3; i++) {
                password = HashAsString(password).substring(0, 30);
            }
            return EncryptString(password, salt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 数据加密 */
    public static String EncryptString(String str) {
        return EncryptString(str, SALT);
    }

    /** 加密 */
    public static String EncryptString(String str, String salt) {
        try {
            byte[] textBytes = str.getBytes(CHARSET);
            byte[] keyBytes = GetSymmetricKey(Password + salt);
            byte[] ivBytes = GetSymmetricVector(Password + salt);

            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

            return new BASE64Encoder().encode(cipher.doFinal(textBytes));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 数据解密 */
    public static String DecryptString(String str) {
        return DecryptString(str, SALT);
    }

    /** 数据解密 */
    public static String DecryptString(String str, String salt) {
        try {
            byte[] textBytes = new BASE64Decoder().decodeBuffer(str);
            byte[] keyBytes = GetSymmetricKey(Password + salt);
            byte[] ivBytes = GetSymmetricVector(Password + salt);

            AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
            return new String(cipher.doFinal(textBytes), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }//http://url.cn/ZFqTVc

    // SHA1散列计算
    protected static String HashAsString(String decript) {
        try {
            byte messageDigest[] = HashAsBytes(decript);
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    protected static byte[] HashAsBytes(String decript) {
        try {
            MessageDigest digest = MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes(CHARSET));
            return digest.digest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static byte[] GetSymmetricKey(String password)
            throws UnsupportedEncodingException {
        // 使用SHA1加密的长度为20的字节数组。
        byte[] hash = HashAsBytes(password);
        // 创建一个32位的密钥字节数组。
        byte[] result = new byte[32];

        // 获取加密数组中的前16位并填充两次。
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 16; j++) {
                result[i * 16 + j] = hash[j];
            }
        }

        return result;
    }

    protected static byte[] GetSymmetricVector(String password)
            throws UnsupportedEncodingException {
        // 使用SHA1加密的长度为20的字节数组。
        byte[] hash = HashAsBytes(password);
        // 创建一个16位的密钥字节数组。
        byte[] result = new byte[16];
        // 获取加密数组中的前16位。
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 16; j++) {
                result[i * 16 + j] = hash[j];
            }
        }

        return result;
    }

    public static String md5(String source) throws UnsupportedEncodingException {
        return new String(md5(source.getBytes(CHARSET)));
    }

    public static byte[] md5(byte[] source) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            MessageDigest md = MessageDigest
                    .getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest();
            byte str[] = new byte[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = (byte) hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = (byte) hexDigits[byte0 & 0xf];
            }
            return str;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
