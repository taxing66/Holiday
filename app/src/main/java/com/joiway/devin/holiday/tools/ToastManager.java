package com.joiway.devin.holiday.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * toast manager  toast 封装管理类
 * Looper：消息泵
 *
 * @author 陈德华
 * @create 2016-07-08
 * @editer 陈德华
 * @date 2016-07-08
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class ToastManager {
    private static Handler mMainHandler;
    private static Toast mToast;

    /**
     * 获取主线程的消息泵
     * @return
     */
    private static Handler getMainHandler() {
        if (mMainHandler == null) {
            synchronized (ToastManager.class) {
                if (mMainHandler == null) {
                    return new Handler(Looper.getMainLooper());
                }
            }
        }
        return mMainHandler;
    }

    private static void showToast(final Context context, final String msg, final int duration) {
        if (mToast != null) {
            mToast.cancel();
        }
        getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                mToast = Toast.makeText(context.getApplicationContext(), msg, duration);
                mToast.show();
            }
        });
    }

    /**
     * 长弹toast
     * @param context
     * @param msg
     */
    public static void showToastLong(Context context,String  msg){
        showToast(context,msg,Toast.LENGTH_LONG);
    }

    /**
     * 短暂显示
     * @param context
     * @param msg
     */
    public static  void showToastShort(Context context,String msg){
        showToast(context,msg,Toast.LENGTH_SHORT);
    }
}
