package com.joiway.devin.holiday.controller.tools.system;

import android.app.Application;

import com.joiway.devin.holiday.tools.SharedPreferencesManager;
import com.joiway.lib.security.PhoneSec;

/**
 * Created by Administrator on 2016/8/10.
 */
public class HolidayApplication extends Application {
    public static HolidayApplication sHolidayApplication;
    private static SharedPreferencesManager mSharedPreferencesManager;
    public static HolidayApplication getInstance() {
        if (sHolidayApplication == null) {
            sHolidayApplication = new HolidayApplication();
        }
        return sHolidayApplication;
    }

    /**
     * 获取单例PtcSharedPreferences实例
     *
     * @return PtcSharedPreferences实例
     */
    public SharedPreferencesManager getSpfManager() {
        if (mSharedPreferencesManager == null) {
            mSharedPreferencesManager = SharedPreferencesManager.getInstance(this);
        }
        return mSharedPreferencesManager;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sHolidayApplication = this;

        //加载jzmC_0_1 native lib库
        PhoneSec.useJNI("jzmC_0_1");
    }
}
