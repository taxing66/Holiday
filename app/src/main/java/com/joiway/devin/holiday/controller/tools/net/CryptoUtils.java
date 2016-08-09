package com.joiway.devin.holiday.controller.tools.net;

import com.joiway.lib.base.cryptolib.CryptoNativeLib;
import java.io.UnsupportedEncodingException;

/**
 * Created by 潘阳君 on 2015/12/23.
 *【豆浆框架】-【库层】
 *【加解密库】
 */
public class CryptoUtils {
    
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
    public static byte[] base64Decode(byte[] base64Str){
        return CryptoNativeLib.base64Decode(base64Str);
    }

    /**
     * Base64编码。
     * @param data
     * 需要编码的数据
     * @return base64编码后的数据
     */
    public static String base64Encode(byte[] data){
        return CryptoNativeLib.base64Encode(data);
    }

    /**
     * 16进制编码，将数据都进行16进制输出成字符串。
     * @param data 需要编码的数据
     * @return 编码后的字符串。
     */
    public static String hexEncode(byte[] data){
        return CryptoNativeLib.hexEncode(data);
    }

    /**
     * 16进制解码，将16进制编码的字符串转成数据返回。
     * @param hexStr 16进制编码字符串
     * @return 解码后的数据
     */
    public static byte[] hexDecode(String hexStr){
        return CryptoNativeLib.hexDecode(hexStr);
    }

    /**
     * MD5 校验值计算
     * @param data 需要校验的数据
     * @return 校验后的并经过16进制编码的签名字符串
     */
    public static String md5(byte[] data){
        return CryptoNativeLib.md5(data);
    }

    /**
     * AES CBC 加密
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv AES加密向量，必须符合标准长度（16）
     * @param src 需要加密的数据。
     * @return 加密后的数据，返回null为加密失败
     */
    public static byte[] AESEncrypt(byte[] key, byte[] iv, byte[] src){
        return CryptoNativeLib.AESEncrypt(key, iv, src);
    }

    /**
     * AES CBC 解密
     * @param key AES密钥，必须符合标准AES密钥的长度（16、24、32）。
     * @param iv AES加密向量，必须符合标准长度（16）
     * @param src 需要解密的数据。
     * @return 解密后的数据，返回null为解密失败
     */
    public static byte[] AESDecrypt(byte[] key, byte[] iv, byte[] src){
        return CryptoNativeLib.AESDecrypt(key, iv, src);
    }

}
