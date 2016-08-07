package com.joiway.devin.holiday.controller.tools.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 用途描述
 * 网络工具
 *
 * 获取网络状态
 *
 * @author 潘阳君
 * @create 2016/6/28
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NetUtils {
    private static ConnectivityManager sConnectivity;
    public static NetworkInfo getCurrentNetworkInfo(Context context){
        if (sConnectivity == null) {
            sConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return sConnectivity.getActiveNetworkInfo();
    }
}
