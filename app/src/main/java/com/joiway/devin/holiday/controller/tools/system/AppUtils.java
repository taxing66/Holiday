package com.joiway.devin.holiday.controller.tools.system;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 用途描述
 * 与App相关的工具
 *
 * @author 潘阳君
 * @create 2016/6/28
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class AppUtils {
    //版本名
    public static String getVersionName(Context context,String defVerName) {
        try {
            return getPackageInfo(context).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defVerName;
    }

    //版本号
    public static int getVersionCode(Context context, int defVerCode) {
        try {
            return getPackageInfo(context).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defVerCode;
    }

    private static PackageInfo getPackageInfo(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo pi = null;

        PackageManager pm = context.getPackageManager();
        pi = pm.getPackageInfo(context.getPackageName(),
                PackageManager.GET_CONFIGURATIONS);

        return pi;
    }
}
