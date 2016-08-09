/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joiway.devin.holiday.controller.tools.file;

/**
 *
 * @author Curtis
 */
public class ByteArrayUtils {
    /**
     * 字节数字字符串 转 字节数组
     * 字节数字字符串,例： {12,-111,121}
     * @param src
     * @return 
     */
    public static byte[] StringToBytes(String src){
        if(src == null) return null;
        if(src.isEmpty()||src.length()<2) return null;
        if(!src.contains("{") || !src.contains("}")) return null;
        
        src = src.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "");
        
        src = src.substring(1, src.length()-1);
        
        String byteStr[] = src.split(",");
        
        byte[] buff = new byte[byteStr.length];
        
        for(int i=0; i<byteStr.length; i++){
            buff[i] = Byte.parseByte(byteStr[i]);
        }
        return buff;
    }
    
    public static String BytesToString(byte[] src){
        String dst = "{";
        
        for(byte b:src){
            dst += b +",";
        }
        return dst.substring(0, dst.length()-1) + "}";
    }
}
