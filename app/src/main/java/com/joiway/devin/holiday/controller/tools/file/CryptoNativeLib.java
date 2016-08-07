/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.joiway.devin.holiday.controller.tools.file;

import java.io.UnsupportedEncodingException;

/**
 * 兼职猫动态库，包含各种高性能低成本使用库。
 * <br>本动态库是使用c/c++语言编写并编译，目前仅支持Windows、Android版本
 * @version v0.01
 * @since v0.01
 * @author Curtis
 */
public class CryptoNativeLib {

    public static final String DEFAULT_LIB = "ccrypto";

    static {
        System.loadLibrary(DEFAULT_LIB);
    }

    /**
     * Base64解码，输入的String都会被转成UTF-8形式进行解码
     * @param base64Str
     * @return base64解码后的数据
     */
    public static byte[] base64Decode(String base64Str) throws UnsupportedEncodingException {
        return base64Decode(base64Str.getBytes("UTF-8"));
    }
        /**
     * Base64解码，输入的String都会被转成UTF-8形式进行解码
     * @param base64Str
     * @return base64解码后的数据
     */
    public static native byte[] base64Decode(byte[] base64Str);

    /**
     * Base64编码。
     * @param data
     * 需要编码的数据
     * @return base64编码后的数据
     */
    public static native String base64Encode(byte[] data);

    /**
     * 16进制编码，将数据都进行16进制输出成字符串。
     * @param data 需要编码的数据
     * @return 编码后的字符串。
     */
    public static native String hexEncode(byte[] data);

    /**
     * 16进制解码，将16进制编码的字符串转成数据返回。
     * @param hexStr 16进制编码字符串
     * @return 解码后的数据
     */
    public static native byte[] hexDecode(String hexStr);

    /**
     * MD5 校验值计算
     * @param data 需要校验的数据
     * @return 校验后的并经过16进制编码的签名字符串
     */
    public static native String md5(byte[] data);

    /**
     * AES CBC 加密
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv AES加密向量，必须符合标准长度（16）
     * @param src 需要加密的数据。
     * @return 加密后的数据，返回null为加密失败
     */
    public static native byte[] AESEncrypt(byte[] key, byte[] iv, byte[] src);

    /**
     * AES CBC 解密
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv AES加密向量，必须符合标准长度（16）
     * @param src 需要解密的数据。
     * @return 解密后的数据，返回null为解密失败
     */
    public static native byte[] AESDecrypt(byte[] key, byte[] iv, byte[] src);

    /**
     * 兼职猫 客户端 解密方式
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String clientDecrypt(String base64Str) throws UnsupportedEncodingException {
        return clientDecrypt(base64Str.getBytes("UTF-8"));
    }
        /**
     * 兼职猫 客户端 解密方式
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static native String clientDecrypt(byte[] base64Str);

    /**
     * 兼职猫 客户端 使用默认Aes密钥解密。
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static String clientDecryptWithDefaultKey(String base64Str) throws UnsupportedEncodingException {
        return clientDecryptWithDefaultKey(base64Str.getBytes("UTF-8"));
    }
        /**
     * 兼职猫 客户端 使用默认Aes密钥解密。
     * @param base64Str
     * @return 服务器数据，返回null为解密失败
     */
    public static native String clientDecryptWithDefaultKey(byte[] base64Str);

    /**
     * 兼职猫 客户端 加密方式
     * @param jsonStr 需要加密的字符串。
     * @return 加密数据，返回null为加密失败
     */
    public static String clientEncrypt(String jsonStr) throws UnsupportedEncodingException {
        return clientEncrypt(jsonStr.getBytes("UTF-8"));
    }
        /**
     * 兼职猫 客户端 加密方式
     * @param jsonStr 需要加密的字符串。
     * @return 加密数据，返回null为加密失败
     */
    public static native String clientEncrypt(byte[] jsonStr);
    /**
     * 兼职猫 服务端 解密方式
     * @param base64Str 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 客户端加密前的数据，返回null为解密失败
     */
    public static String serverDecrypt(String base64Str, byte[] aeskey) throws UnsupportedEncodingException {
        return serverDecrypt(base64Str.getBytes("UTF-8"),aeskey);
    }
    /**
     * 兼职猫 服务端 解密方式
     * @param base64Str 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 客户端加密前的数据，返回null为解密失败
     */
    public static native String serverDecrypt(byte[] base64Str, byte[] aeskey);
    /**
     * 兼职猫 服务端 加密方式
     * @param jsonStr 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 加密的数据，返回null为加密失败
     */
    public static String serverEncrypt(String jsonStr, byte[] aeskey) throws UnsupportedEncodingException {
        return serverEncrypt(jsonStr.getBytes("UTF-8"),aeskey);
    }
        /**
     * 兼职猫 服务端 加密方式
     * @param jsonStr 兼职猫客户端上传数据。
     * @param aeskey AES 密钥
     * @return 加密的数据，返回null为加密失败
     */
    public static native String serverEncrypt(byte[] jsonStr, byte[] aeskey);
    /**
     * 文件加密
     * @param path 需要加密的文件路径，不支持文件夹
     * @param version 版本号
     * @param header 自定义文件头
     * @return
     */
    public static native boolean fileAesEncrypt(String path, int version, byte[] header);
    /**
     * 文件解密
     * 与上面的方法是一对的
     * @param path 需要解密的文件路径。
     * @return
     */
    public static native boolean fileAesDecrypt(String path);
    /**
     * 获取加密文件的版本号
     * {@link #fileAesEncrypt(String, int, byte[])}加密后的文件才能正确读取
     * @param path 加密文件路径
     * @return
     */
    public static native int aesFileVersion(String path);
}
