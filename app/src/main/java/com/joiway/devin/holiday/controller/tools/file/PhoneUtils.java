/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.joiway.devin.holiday.controller.tools.file;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Curtis
 */
public class PhoneUtils {

    private static final int ERROR = -1;

    /**
     * 获取手机屏幕尺寸.
     *
     * @param context
     * @return 例如:1920*1080，返回： int[]{1920,1080}
     */
    public static int[] getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        int[] size = new int[2];
        size[0] = wm.getDefaultDisplay().getWidth();
        size[1] = wm.getDefaultDisplay().getHeight();

        
        return size;
    }

    /**
     * 获取手机屏幕尺寸.
     *
     * @param activity
     * @return 例如:1920*1080，返回： int[]{1920,1080}
     */
    public static int[] getDisplaySize(Activity activity) {
        WindowManager wm = activity.getWindowManager();

        int[] size = new int[2];
        size[0] = wm.getDefaultDisplay().getWidth();
        size[1] = wm.getDefaultDisplay().getHeight();

        return size;
    }

    /**
     * 获取手机屏幕尺寸.
     *
     * @param wm
     * @return 例如:1920*1080，返回： int[]{1920,1080}
     */
    public static int[] getDisplaySize(WindowManager wm) {

        int[] size = new int[2];
        size[0] = wm.getDefaultDisplay().getWidth();
        size[1] = wm.getDefaultDisplay().getHeight();

        return size;
    }

    /**
     * SDCARD是否存
     */
    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取手机内部剩余存储空间
     *
     * @return
     */
    public static long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取手机内部总的存储空间
     *
     * @return
     */
    public static long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取SDCARD剩余存储空间
     *
     * @return
     */
    public static long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return availableBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 获取SDCARD总的存储空间
     *
     * @return
     */
    public static long getTotalExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * 获取系统总内存
     *
     * @param context 可传入应用程序上下文。
     * @return 总内存大单位为B。
     */
    public static long getTotalMemorySize(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            return Integer.parseInt(subMemoryLine.replaceAll("\\D+", "")) * 1024l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存单位为B。
     */
    public static long getAvailableMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    private static DecimalFormat fileIntegerFormat = new DecimalFormat("#0");
    private static DecimalFormat fileDecimalFormat = new DecimalFormat("#0.#");

    /**
     * 单位换算
     *
     * @param size 单位为B
     * @param isInteger 是否返回取整的单位
     * @return 转换后的单位
     */
    public static String formatFileSize(long size, boolean isInteger) {
        DecimalFormat df = isInteger ? fileIntegerFormat : fileDecimalFormat;
        String fileSizeString = "0M";
        if (size < 1024 && size > 0) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1024 * 1024) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1024 * 1024 * 1024) {
            fileSizeString = df.format((double) size / (1024 * 1024)) + "M";
        } else {
            fileSizeString = df.format((double) size / (1024 * 1024 * 1024)) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取手机CPU信息
     * <br>因手机而异
     * @return
     * 手机CPU所有信息的键值对.
     * <br>已知Key： 
     * <br>processor ：<strong>CPU计算核心(从0开始计算)</strong>
     * <br>model_name ：<strong>CPU计算核心模块名</strong>
     * <br>BogoMIPS ：<strong>未知</strong>
     * <br>Features ：<strong>未知</strong>
     * <br>CPU_implementer ：<strong>未知</strong>
     * <br>CPU_architecture ：<strong>未知</strong>
     * <br>CPU_variant ：<strong>未知</strong>
     * <br>CPU_part ：<strong>未知</strong>
     * <br>CPU_revision ：<strong>未知</strong>
     * <br>Hardware ：<strong>CPU硬件型号</strong>
     * <br>Revision ：<strong>CPU版本号</strong>
     * <br>Serial ：<strong>CPU生产序号</strong>
     * <br>Device ：<strong>设备名称</strong>
     * <br>Radio ：<strong>无线设备信息</strong>
     * <br>MSM_Hardware ：<strong>GPU型号</strong>
     */
    public static Map<String,String> getCpuInfo() {
        String str1 = "/proc/cpuinfo";
        String line = "";
        HashMap<String,String> cpuInfo = new HashMap<>();
        try {
            FileReader fr = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(fr);
            line = localBufferedReader.readLine();
            
            while(line != null){
                if(line.length()> 1){
                    String[] vk = line.split(":");
                    vk[0] = vk[0].trim().replaceAll("\\s", "_");
                    vk[1] = vk[1].trim();
                    cpuInfo.put(vk[0], vk[1]);
                }
                line = localBufferedReader.readLine();
            }
            localBufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cpuInfo;
    }
    
    

    /**
     * 获取手机的所有硬件信息,信息源来自{@link Build}
     * <br>若需要以外的信息,请使用此工具类中工具获取
     * @return
     * @throws Exception
     */
    public final static Map<String, String> makeOSInfo() throws Exception {
        HashMap<String, String> map = new HashMap<>();

        Build android = new Build();

        Field[] fields = Build.class.getFields();

        for (Field f : fields) {
            if(f.getName().equals("USER") || f.getName().equals("HOST") || f.getName().equals("TAGS")
                    || f.getName().equals("TAG") || f.getName().equals("UNKNOWN")) continue;
            
            try {
                f.setAccessible(true);
                Object v = f.get(android);
                if(v instanceof String)
                    map.put(f.getName(),v.toString() + "");
                f.setAccessible(false);
            } catch (Exception e) {
                // 字段被 安全隐藏， 无需处理，继续读取下个字段
            }
        }
        
        return map;
    }
}
