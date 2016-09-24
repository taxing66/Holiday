package com.joiway.devin.holiday.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.model.ConfigBean;
import com.joiway.devin.holiday.tools.SharedPreferencesManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.xutils.common.Callback;

import jw.cn.com.jwutils.controller.activity.JWActivity;
import jw.cn.com.jwutils.controller.net.NetManager;
import jw.cn.com.jwutils.model.JWConfigBean;
import jw.cn.com.jwutils.model.bean.NetOutputParameterBean;

/**
 *所有activity 父类
 * @author 陈德华
 * @create 2016-06-30
 * @editer 陈德华
 * @date 2016-06-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public  class AbsActivity extends JWActivity {
    protected SharedPreferencesManager mSharedPreferencesManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferencesManager = SharedPreferencesManager.getInstance(this);

    }
    protected void initTitleBar(){
        RelativeLayout.LayoutParams layoutParams;
        RelativeLayout rlTitleBar;
        int top=0;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
            systemBarTintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
            systemBarTintManager.setStatusBarTintEnabled(true);
            top =systemBarTintManager.getConfig().getStatusBarHeight();
        }
        Log.d("devin","Build.VERSION.SDK_INT"+Build.VERSION.SDK_INT+"height:"+top);
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,top,0,0);
        rlTitleBar = (RelativeLayout) this.findViewById(R.id.rl_title);
        rlTitleBar.setLayoutParams(layoutParams);
    }



}
