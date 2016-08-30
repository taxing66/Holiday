package jw.cn.com.jwutils.controller.utils;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *加密处理工具类
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class CrypUtils {

    public static final String DESEDEKey = "ILeonse0629jianzhimaoshiwangzhe123sss";
    private final static String AESDefaultKey = "1234567899123456";
    private final static String AESKey = "0Mm8eFTNp5o2qXJS";

    /**
     * Base64解码，输入的String都会被转成UTF-8形式进行解码
     *
     * @param base64Str
     * @return base64解码后的数据
     */
    public static byte[] base64Decode(String base64Str) throws Exception {
        return base64Decode(base64Str.getBytes("UTF-8"));

    }

    /**
     * Base64解码，输入的String都会被转成UTF-8形式进行解码
     *
     * @param base64Str
     * @return base64解码后的数据
     */
    public static byte[] base64Decode(byte[] base64Str) throws Exception {
        return Base64.decode(base64Str, Base64.NO_WRAP);
    }

    /**
     * Base64编码。
     *
     * @param data 需要编码的数据
     * @return base64编码后的数据
     */
    public static String base64Encode(byte[] data) throws Exception {
        return new String(Base64.encode(data, Base64.NO_WRAP), "UTF-8");
    }

    /**
     * 16进制编码，将数据都进行16进制输出成字符串。
     *
     * @param data 需要编码的数据
     * @return 编码后的字符串。
     */
    public static String hexEncode(byte[] data) {
        String out = "";

        for (byte b : data) {
            String byteHex = String.format("%x", b);
            if (byteHex.length() < 2) {
                byteHex = "0" + byteHex;
            }
            out += byteHex;//Integer.toHexString(b);
        }

        return out;
    }

    /**
     * 16进制解码，将16进制编码的字符串转成数据返回。
     *
     * @param hexStr 16进制编码字符串
     * @return 解码后的数据
     */
    public static byte[] hexDecode(String hexStr) {
        byte[] strByte = hexStr.getBytes();
        byte[] buff = new byte[strByte.length / 2];
        for (int i = 0; i < buff.length; i++) {
            buff[i] = hexToByte(strByte[i * 2], strByte[i * 2 + 1]);
        }
        return buff;
    }

    /**
     * MD5 校验值计算
     *
     * @param data 需要校验的数据
     * @return 校验后的并经过16进制编码的签名字符串
     */
    public static String md5(byte[] data) throws NoSuchAlgorithmException {
        String resultString = "";
        MessageDigest md = MessageDigest.getInstance("MD5");
        // md.digest() 该函数返回值为存放哈希值结果的byte数组
        byte[] md5 = md.digest(data);
        resultString = hexEncode(md5);
        return resultString;
    }

    /**
     * 数据3DS加密
     *
     * @param src    加密字段
     * @return
     * @throws Exception
     */
    public static String encryptTripleDes(String src) throws Exception {
        return  encryptTripleDes(src,DESEDEKey);
    }
    /**
     * 数据3DS加密
     *
     * @param src    加密字段
     * @param keyStr 加密的KEY
     * @return
     * @throws Exception
     */
    public static String encryptTripleDes(String src, String keyStr) throws Exception {
        DESedeKeySpec dks = new DESedeKeySpec(keyStr.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secureKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secureKey);
        byte[] retByte = cipher.doFinal(src.getBytes());
        String resultStr = Base64.encodeToString(retByte, Base64.DEFAULT).replaceAll("\r", "").replaceAll("\n", "");
        return resultStr;

    }

    /**
     * 数据3DS解密
     * 3 x 56 = 168个独立的密钥位。
     * key必须是长度大于等于 3*8 = 24 位
     *
     * @param src    解密的数据源
     * @return
     * @throws Exception
     */
    public static String decryptTripleDes(String src) throws Exception {
        return  decryptTripleDes(src,DESEDEKey);
    }

    /**
     * 数据3DS解密
     * 3 x 56 = 168个独立的密钥位。
     * key必须是长度大于等于 3*8 = 24 位
     *
     * @param src    解密的数据源
     * @param keyStr 解密的KEY
     * @return
     * @throws Exception
     */
    public static String decryptTripleDes(String src, String keyStr) throws Exception {
        byte[] byteSrc = Base64.decode(src, Base64.DEFAULT);
        DESedeKeySpec dks = new DESedeKeySpec(keyStr.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey secureKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secureKey);
        byte[] retByte = cipher.doFinal(byteSrc);
        return new String(retByte);
    }


    /**
     * AES CBC 加密
     *
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv  AES加密向量，必须符合标准长度（16）
     * @param src 需要加密的数据。
     * @return 加密后的数据，返回null为加密失败
     */
    public static byte[] encryptAes(byte[] key, byte[] iv, byte[] src) throws
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchProviderException,
            NoSuchPaddingException {

        SecretKeySpec AesKey = new SecretKeySpec(key, "AES");
        src = PKCS7Padding.padding(src);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, AesKey, new IvParameterSpec(iv));
        byte[] enc = cipher.doFinal(src);
        return enc;
    }

    /**
     * AES CBC 解密
     *
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv  AES加密向量，必须符合标准长度（16）
     * @param src 需要解密的数据。
     * @return 解密后的数据，返回null为解密失败
     */
    public static byte[] decryptAes(byte[] key, byte[] iv, byte[] src) throws
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException,
            NoSuchAlgorithmException,
            NoSuchProviderException,
            NoSuchPaddingException {

        SecretKeySpec AesKey = new SecretKeySpec(key, "AES");
        Cipher in = Cipher.getInstance("AES/CBC/NoPadding");
        in.init(Cipher.DECRYPT_MODE, AesKey, new IvParameterSpec(iv));
        byte[] enc = in.doFinal(src);
        return PKCS7Padding.dePadding(enc);
    }

    private static class PKCS7Padding {

        private final static int BLOCK_SIZE = 16;

        /**
         * for example: src  length:48  padding = 14,size = 34
         *
         * @param src
         * @return
         */
        public static byte[] dePadding(byte[] src) {
            int padding = src[src.length - 1];
            int size = src.length - padding;
            byte[] buff = new byte[size];

            System.arraycopy(src, 0, buff, 0, size);
            return buff;
        }

        /**
         * for example: src  length:34  block_size 16  k = 2 j 2 padding =14  buff = 48
         * 防止：IllegalBlockSizeException:
         *
         * @param src
         * @return
         */
        public static byte[] padding(byte[] src) {
            int size = src.length;
            int k = size % BLOCK_SIZE;
            int j = size / BLOCK_SIZE;
            int padding = BLOCK_SIZE - k;

            byte[] buff = new byte[size + padding];

            System.arraycopy(src, 0, buff, 0, src.length);

            for (int i = 0; i < padding; i++) {
                buff[j * BLOCK_SIZE + k + i] = (byte) padding;
            }
            return buff;
        }

    }


    /**
     * Aes 解密方式
     *
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String decryptAes(String base64Str) throws Exception {
        return decryptAes(base64Str.getBytes("UTF-8"));
    }

    /**
     * 兼职猫 客户端 解密方式
     *
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String decryptAes(byte[] base64Str) throws Exception {
        byte[] src = base64Decode(base64Str);
        byte[] iv = new byte[16];
        byte[] enc = new byte[src.length - iv.length];
        System.arraycopy(src, 0, iv, 0, iv.length);
        System.arraycopy(src, iv.length, enc, 0, enc.length);
        byte[] dst = decryptAes(AESKey.getBytes(), iv, enc);
        return new String(dst, "UTF-8");
    }

    /**
     * 兼职猫 客户端 使用默认Aes密钥解密。
     *
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String clientDecryptWithDefaultKey(String base64Str) throws Exception {
        return clientDecryptWithDefaultKey(base64Str.getBytes("UTF-8"));
    }

    /**
     * 兼职猫 客户端 使用默认Aes密钥解密。
     *
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String clientDecryptWithDefaultKey(byte[] base64Str) throws Exception {
        byte[] src = base64Decode(base64Str);
        byte[] iv = new byte[16];
        byte[] enc = new byte[src.length - iv.length];

        System.arraycopy(src, 0, iv, 0, iv.length);
        System.arraycopy(src, iv.length, enc, 0, enc.length);

        byte[] dst = decryptAes(AESDefaultKey.getBytes(), iv, enc);
        return new String(dst);
    }

    /**
     * 兼职猫 客户端 加密方式
     *
     * @param jsonStr 需要加密的字符串。
     * @return 加密数据，返回null为加密失败
     */
    public static String encryptAes(String jsonStr) throws Exception {
        return encryptAes(jsonStr.getBytes("UTF-8"));
    }

    /**
     * 兼职猫 客户端 加密方式
     *
     * @param jsonStr 需要加密的字符串。
     * @return 加密数据，返回null为加密失败
     */
    public static String encryptAes(byte[] jsonStr) throws Exception {
        byte[] iv = randomBytes(16);
        byte[] enc = encryptAes(AESKey.getBytes(), iv, jsonStr);
        byte[] dst = new byte[iv.length + enc.length];
        System.arraycopy(iv, 0, dst, 0, iv.length);
        System.arraycopy(enc, 0, dst, iv.length, enc.length);
        return base64Encode(dst);
    }


    /**
     * 隨機生成一組byte數組
     *
     * @param count
     * @return
     */
    private static byte[] randomBytes(int count) {
        byte buff[] = new byte[count];
        Random r = new Random();
        r.nextBytes(buff);
        return buff;
    }

    private static byte hexToByte(byte hexChar1, byte hexChar2) {
        byte value = 0;
        value = byteToByte(hexChar1);
        value = (byte) (value << 4);
        value += byteToByte(hexChar2);
        return value;
    }

    private static byte byteToByte(byte value) {
        if (value >= 'a' && value <= 'f') {
            return (byte) (value - 'a' + 0x0A);
        }
        if (value >= 'A' && value <= 'F') {
            return (byte) (value - 'A' + 0x0A);
        }
        if (value >= '0' && value <= '9') {
            return (byte) (value - '0');
        }
        return 0;
    }

}
