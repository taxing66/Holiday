/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joiway.devin.holiday.controller.tools.file;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.joiway.lib.base.cryptolib.CryptoJavaLib;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * 手机 机械码 生成库
 *
 * @author 潘阳君
 * @version v0.02
 *          <br>MK算法升级：遍历所有Android信息。
 *          <br>修改： 获取IMEI渠道
 *          <br>添加： 反射Android所有信息导出Map
 *          <br>添加： 便利获取IMEI
 * @since Android SDK 8
 */
public class PhoneSec {
    public final static String SERVER_FIELD_ANDROID_SDK_INT = "Android_SDK_LEVEL";
    public final static String SERVER_FIELD_ANDROID_SDK_VER = "Android_SDK_VER";
    public final static String SERVER_FIELD_BUILD_ID = "android_device_id";
    /**
     * Android手机的IMEI号，只要调用 getIMEI(android.content.Context) 此字段就会存在值
     *
     * @see #getIMEI(Context)
     */
    public static String IMEI = "";

    /**
     * Android手机的机器码，只要调用 makeMK(android.content.Context)此字段就会存在值
     *
     * @see #makeMK(Context)
     */
    public static String MK = "";

    /**
     * 建立 手机 机械码
     * <br>算法：
     * <br>反射 android.os.Build 类中所有字段
     * <br>机械码 = md5(反射值);
     *
     * @param context 上下文
     * @return 手机机械码(32个字符)
     */
    public final static String makeMK(Context context) {

        if (!TextUtils.isEmpty(MK)) {
            return MK;
        }

//        String AndroidSDK = Build.VERSION.SDK;
//        String AndroidCode = Build.VERSION.SDK_INT + "";
//        String AndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        String mk = "";
        try {

            Map phoneInfo = PhoneUtils.makeOSInfo();

            Collection infos = phoneInfo.values();
            ArrayList<String> values = new ArrayList<>();
            for (Object value : infos)
                values.add(value.toString() + "");
            Collections.sort(values);
            for (Object value : values) {
                mk += value.toString();

//                Log.d("PhoneSec", value.toString()+"\n");
            }

            if (!TextUtils.isEmpty(getIMEI(context))) {
                mk += CryptoJavaLib.md5(IMEI.getBytes("UTF-8"));
            }

            mk = CryptoJavaLib.md5(mk.getBytes("UTF-8"));

            mk = CryptoJavaLib.md5(mk.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        MK = mk;

        return mk;
    }

    /**
     * 获取Android手机的IMEI号
     *
     * @param context
     * @return
     */
    public final static String getIMEI(Context context) {
        if (TextUtils.isEmpty(IMEI)) {
            IMEI = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

            /* 不过纯APP开发SystemProperties，TelephonyProperties汇报错误，因为android.os.SystemProperties在SDK的库中是没有的，需要把Android SDK 目录下data下的layoutlib.jar文件加到当前工程的附加库路径中，就可以Import。
             */
            //IMEI = android.os.SystemProperties.get(TelephonyProperties.PROPERTY_IMEI);
        }

        if (IMEI == null) {
            IMEI = "";
        }

        return IMEI;
    }

    /**
     * 获取统计信息JSON字符串
     *
     * @return
     */
    public final static String getStatisticalData(Context context) {
        try {
            JSONObject json = new JSONObject();
            Map<String, String> hardwareInfo = PhoneUtils.makeOSInfo();

            //添加 Android 系统版本
            hardwareInfo.put(SERVER_FIELD_ANDROID_SDK_INT, Build.VERSION.SDK_INT + "");
            hardwareInfo.put(SERVER_FIELD_ANDROID_SDK_VER, "Android " + Build.VERSION.RELEASE);

            //修改ID字段，此字段与服务器中数据库 ID 列有冲突
            String ID = hardwareInfo.get("ID");
            if (!TextUtils.isEmpty(ID)) {
                hardwareInfo.put(SERVER_FIELD_BUILD_ID, ID);
            }

            //添加 IMEI
            hardwareInfo.put("IMEI", IMEI);
            //添加 屏幕尺寸
            int[] size = PhoneUtils.getDisplaySize(context);
            hardwareInfo.put("Resolution", size[0] + "*" + size[1]);
            //添加 ROM容量
            hardwareInfo.put("Memory", PhoneUtils.getTotalMemorySize(context) + "");
            //添加 RAM容量
            hardwareInfo.put("Storage", PhoneUtils.getAvailableExternalMemorySize() + "");
            //添加 CPU型号
            Map<String, String> cpuinfo = PhoneUtils.getCpuInfo();
            String cpuModel = cpuinfo.get("Hardware");
            if (TextUtils.isEmpty(cpuModel))
                cpuModel = "";
            hardwareInfo.put("Cpu", cpuModel);

            Iterator<Map.Entry<String, String>> entrys = hardwareInfo.entrySet().iterator();
            while (entrys.hasNext()) {
                Map.Entry<String, String> vk = entrys.next();
                json.put(vk.getKey(), vk.getValue());
            }
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("获取硬件信息失败:" + e.toString());
            return "";
        }
    }

}
