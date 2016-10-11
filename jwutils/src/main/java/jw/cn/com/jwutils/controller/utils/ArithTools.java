package jw.cn.com.jwutils.controller.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * 防失真运算工具，与数字格式化操作
 * 封闭double运算，防数度失真的方法 ,
 * 运算后保留多少位小数，小数是否四舍五入。
 *
 * @author 陈德华
 * @create 2016-10-11
 * @editer 陈德华
 * @date 2016-10-11
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */

public class ArithTools {
    /**
     * 四舍五入
     */
    public static final int ROUND_MODE_HELF_UP = BigDecimal.ROUND_HALF_UP;

    /**
     * 直接加值
     */
    public static final int ROUND_MODE_UP = BigDecimal.ROUND_UP;

    /**
     * 直接去值
     */
    public static final int ROUND_MODE_DOW = BigDecimal.ROUND_DOWN;
    /**
     * ROUND_HALF_UP  四舍五入， ROUND_UP 小数直接增加 ROUND_DOWN 小数直接删除
     * Rounding mode to round towards "nearest neighbor" unless both neighbors are equidistant, in which case round up.
     *
     * @param a
     * @param b
     * @return
     */
    public static double add(double a, double b) {
        BigDecimal ba = BigDecimal.valueOf(a);
        BigDecimal bb = BigDecimal.valueOf(b);
        return ba.add(bb).doubleValue();
    }

    public static double sub(double a, double b) {
        BigDecimal ba = BigDecimal.valueOf(a);
        BigDecimal bb = BigDecimal.valueOf(b);
        return ba.subtract(bb).doubleValue();
    }

    public static double mul(double a, double b) {
        BigDecimal ba = BigDecimal.valueOf(a);
        BigDecimal bb = BigDecimal.valueOf(b);
        return ba.multiply(bb).doubleValue();
    }

    public static double div(double a, double b) {
        BigDecimal ba = BigDecimal.valueOf(a);
        BigDecimal bb = BigDecimal.valueOf(b);
        return ba.divide(bb).doubleValue();
    }


    /**
     * 一个方法第一要保留精度计算，限定式保留N位小数，小数后面的处理方式，直接去除，直接进位，四舍五入，进位，让四舍五入为默认进位方式。
     * @param a
     * @param b
     * @param capacity
     * @param roundMode
     * @return
     */
    public static String addFormat(double a, double b, int capacity, int roundMode) {
        return setCapacityFormat(add(a,b),capacity,roundMode);
    }

    public static String subFormat(double a, double b, int capacity, int roundMode) {
        return setCapacityFormat(sub(a,b),capacity,roundMode);
    }

    public static String mulFormat(double a, double b, int capacity, int roundMode) {
        return setCapacityFormat(mul(a,b),capacity,roundMode);
    }

    public static String divFormat(double a, double b, int capacity, int roundMode) {
        return setCapacityFormat(div(a,b),capacity,roundMode);
    }


    /**
     * 一个方法第一要保留精度计算，保留N位小数，小数后面的处理方式，直接去除，直接进位，四舍五入，进位，让四舍五入为默认进位方式。
     * @param a
     * @param b
     * @param capacity
     * @param roundMode
     * @return
     */
    public static double add(double a, double b, int capacity, int roundMode) {
        return setCapacity(add(a,b),capacity,roundMode);
    }

    public static double sub(double a, double b, int capacity, int roundMode) {
        return setCapacity(sub(a,b),capacity,roundMode);
    }

    public static double mul(double a, double b, int capacity, int roundMode) {
        return setCapacity(mul(a,b),capacity,roundMode);
    }

    public static double div(double a, double b, int capacity, int roundMode) {
        return setCapacity(div(a,b),capacity,roundMode);
    }

    /**
     * 把一个数处理成固定小数的值
     * @param a
     * @param capacity
     * @param roundMode
     * @return
     */
    public static String setCapacityFormat(double a,int capacity, int roundMode){
        double num = Double.valueOf(setCapacity(a,capacity, roundMode)); //可能没那么多小数，后续继续处理
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(capacity);
        return nf.format(num);
    }

    /**
     * 把一个数处理成固定小数的百分比值
     * @param a
     * @param capacity
     * @param roundMode
     * @return
     */
    public static String setPercentFormat(double a,int capacity, int roundMode){
        double num = Double.valueOf(setCapacity(a,capacity, roundMode)); //可能没那么多小数，后续继续处理
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(capacity);
        return nf.format(num);
    }

    /**
     * 把一个数处理成保留小数的值，不够小数不去自己加
     * @param a
     * @param capacity
     * @param roundMode
     * @return
     */
    public static double setCapacity(double a,int capacity, int roundMode){
        BigDecimal result = BigDecimal.valueOf(a);
        return  result.setScale(capacity, roundMode).doubleValue();
    }

    /**
     * 设置限定两位小数并四舍五入的方法操作数据
     * @param a
     * @return
     */
    public static final String setTowCapacityFormat(double a){
        return setCapacityFormat(a,2,ROUND_MODE_HELF_UP);
    }

}
