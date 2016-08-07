package com.joiway.devin.holiday.controller.tools.system;

import android.util.Log;

import com.joiway.devin.holiday.BuildConfig;

/**
 *log 工具类 管理log
 * 设计思想 首先日志输出按照开发者标识做为tag标识，然后认清输出日志类型
 * info 信息型 ，debug 调试型 , error 错误日志输出  ,warn 警告信息，assert 断言
 * @author 陈德华
 * @create 2016-07-01
 * @editer 陈德华
 * @date 2016-07-01
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public final class LogManager {
    //项目开发开发人员：devin
    public static final String DEVELOPER_DEVIN ="developer_devin";
    private static final boolean isDebug = BuildConfig.DEBUG;
    private LogManager(){}


    /**
     * 调试日志
     * @param tag
     * @param className
     * @param methodName
     * @param content
     */
    public static void logDebug(String tag ,String className,String methodName,String content){
          String tip = "debug log export："+"<className:"+className+"-methodName:"+methodName+">";
        if (isDebug){
              Log.d(tag,tip+content);
          }
    }
    /**
     * 错误日志
     * @param tag
     * @param content
     */
    public static  void logError(String tag ,String className,String methodName,String content){
        String tip = "debug log export："+"<className:"+className+"-methodName:"+methodName+">";
        if (isDebug){
            Log.e(tag,tip+content);
        }
    }
    /**
     * 信息输出日志
     * @param tag
     * @param content
     */
    public static  void logInfo(String tag ,String className,String methodName,String content){
        String tip = "debug log export："+"<className:"+className+"-methodName:"+methodName+">";
        if (isDebug){
            Log.i(tag,tip+content);
        }
    }
    /**
     * 警告日志
     * @param tag
     * @param content
     */
    public static  void logWarn(String tag ,String className,String methodName,String content){
        String tip = "debug log export："+"<className:"+className+"-methodName:"+methodName+">";
        if (isDebug){
            Log.w(tag,tip+content);
        }
    }
}
