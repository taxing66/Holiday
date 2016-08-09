package com.joiway.lib.utils;

/**
 * 用途描述
 * 数字处理
 *
 * @author 潘阳君
 * @create 2016/3/7
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NumberUtils {

    static{
        System.loadLibrary("numberUtils");
    }

    public static final float sin(float angle){
        return (float) Math.sin(Math.PI*angle/180);
    }

    public static final float cos(float angle){
        return (float) Math.cos(Math.PI*angle/180);
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  bytes2int（）配套使用
     */
    public static native byte[] int2bytes(int value);
    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。int2bytes（）配套使用
     */
    public static native int bytes2int(byte[] src);

    /**
     * 变换颜色
     * @param srcColor 原颜色
     * @param distColor 目标颜色
     * @param per 变换颜色百分数：0 ~ 1
     * @return
     */
    public static native int transformColor(int srcColor,int distColor,float per);

    /**
     * 无符号计算
     * @param b1
     * @param b2
     * @param operat 运算符：'+','-','*','/'
     * @return
     */
    public static native byte unsignedByteComputing(byte b1,byte b2,char operat);
    /**
     * 无符号计算
     * @param i2
     * @param i2
     * @param operat 运算符：'+','-','*','/'
     * @return
     */
    public static native int unsignedIntComputing(int i1,int i2,char operat);
    /**
     * 无符号计算
     * @param l1
     * @param l2
     * @param operat 运算符：'+','-','*','/'
     * @return
     */
    public static native long unsignedLongComputing(long l1,long l2,char operat);
}
