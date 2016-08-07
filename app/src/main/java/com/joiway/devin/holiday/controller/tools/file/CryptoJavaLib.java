/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joiway.devin.holiday.controller.tools.file;

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
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Java版本的加解密库，使用纯Java语言这使用此版本。
 * @version v0.01
 * @since v0.01
 * @author 潘阳君
 */
public class CryptoJavaLib {


    private final static String AESDefaultKey = "1234567899123456";
    private final static String AESKey = "1234567899123456";

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
     * AES CBC 加密
     *
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv AES加密向量，必须符合标准长度（16）
     * @param src 需要加密的数据。
     * @return 加密后的数据，返回null为加密失败
     */
    public static byte[] AESEncrypt(byte[] key, byte[] iv, byte[] src) throws
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
        //str = new String(Hex.encode(enc));  
        return enc;
    }

    /**
     * AES CBC 解密
     *
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv AES加密向量，必须符合标准长度（16）
     * @param src 需要解密的数据。
     * @return 解密后的数据，返回null为解密失败
     */
    public static byte[] AESDecrypt(byte[] key, byte[] iv, byte[] src) throws
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
        
        //str = new String(Hex.encode(enc));  
        return PKCS7Padding.depadding(enc);
    }

    private static class PKCS7Padding {

        private final static int BLOCK_SIZE = 16;

        public static byte[] depadding(byte[] src) {
            int padding = src[src.length-1];
            int size = src.length - padding;
            byte[] buff = new byte[size];
            
            System.arraycopy(src, 0, buff, 0, size);
            return buff;
        }

        public static byte[] padding(byte[] src) {
            int size = src.length;
            int k = size % BLOCK_SIZE;
            int j = size / BLOCK_SIZE;
            int padding = BLOCK_SIZE - k;
            
            byte[] buff = new byte[size + padding];
            
            System.arraycopy(src, 0, buff, 0, src.length);
            
            for (int i = 0; i < padding; i++) {
                buff[j * BLOCK_SIZE + k + i] = (byte)padding;
            }
//            buff[j * BLOCK_SIZE + k + padding] = '\0';

            return buff;
        }

    }

    /**
     * 兼职猫 客户端 解密方式
     *
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String clientDecrypt(String base64Str) throws Exception {
        return clientDecrypt(base64Str.getBytes("UTF-8"));
    }

    /**
     * 兼职猫 客户端 解密方式
     *
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String clientDecrypt(byte[] base64Str) throws Exception {
        byte[] src = base64Decode(base64Str);
        byte[] iv = new byte[16];
        byte[] enc = new byte[src.length - iv.length];

        System.arraycopy(src, 0, iv, 0, iv.length);
        System.arraycopy(src, iv.length, enc, 0, enc.length);

        byte[] dst = AESDecrypt(AESKey.getBytes(), iv, enc);

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

        byte[] dst = AESDecrypt(AESDefaultKey.getBytes(), iv, enc);
        return null;
    }

    /**
     * 兼职猫 客户端 加密方式
     *
     * @param jsonStr 需要加密的字符串。
     * @return 加密数据，返回null为加密失败
     */
    public static String clientEncrypt(String jsonStr) throws Exception {
        return clientEncrypt(jsonStr.getBytes("UTF-8"));
    }

    /**
     * 兼职猫 客户端 加密方式
     *
     * @param jsonStr 需要加密的字符串。
     * @return 加密数据，返回null为加密失败
     */
    public static String clientEncrypt(byte[] jsonStr) throws Exception {
        byte[] iv = randomBytes(16);
        byte[] enc = AESEncrypt(AESKey.getBytes(), iv, jsonStr);

        byte[] dst = new byte[iv.length + enc.length];

        System.arraycopy(iv, 0, dst, 0, iv.length);
        System.arraycopy(enc, 0, dst, iv.length, enc.length);

        return base64Encode(dst);
    }

    /**
     * 兼职猫 服务端 解密方式
     *
     * @param base64Str 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 客户端加密前的数据，返回null为解密失败
     */
    public static String serverDecrypt(String base64Str, byte[] aeskey) throws Exception {
        return serverDecrypt(base64Str.getBytes("UTF-8"), aeskey);
    }

    /**
     * 兼职猫 服务端 解密方式
     *
     * @param base64Str 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 客户端加密前的数据，返回null为解密失败
     */
    public static String serverDecrypt(byte[] base64Str, byte[] aeskey) throws Exception {
        byte[] src = base64Decode(base64Str);
        byte[] iv = new byte[16];
        byte[] enc = new byte[src.length - iv.length];

        System.arraycopy(src, 0, iv, 0, iv.length);
        System.arraycopy(src, iv.length, enc, 0, enc.length);

        byte[] dst = AESDecrypt(aeskey, iv, enc);

        return new String(dst, "UTF-8");
    }

    /**
     * 兼职猫 服务端 加密方式
     *
     * @param jsonStr 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 加密的数据，返回null为加密失败
     */
    public static String serverEncrypt(String jsonStr, byte[] aeskey) throws Exception {
        return serverEncrypt(jsonStr.getBytes("UTF-8"), aeskey);
    }

    /**
     * 兼职猫 服务端 加密方式
     *
     * @param jsonStr 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 加密的数据，返回null为加密失败
     */
    public static String serverEncrypt(byte[] jsonStr, byte[] aeskey) throws Exception {
        byte[] iv = randomBytes(16);
        byte[] enc = AESEncrypt(aeskey, iv, jsonStr);

        byte[] dst = new byte[iv.length + enc.length];

        System.arraycopy(iv, 0, dst, 0, iv.length);
        System.arraycopy(enc, 0, dst, iv.length, enc.length);

        return base64Encode(dst);
    }

    private static byte[] randomBytes(int count) {
//        byte ra;
        byte buff[] = new byte[count];
        Random r = new Random();
        r.nextBytes(buff);
//        for (int i = 0; i < count; i++) {
//            ra = ;
//            buff[i] = ra;
//        }
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
