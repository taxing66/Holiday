package com.joiway.devin.holiday.model;

import com.joiway.devin.holiday.tools.LogManager;

/**
 * Created by Administrator on 2016/8/5.
 */
public class Son extends Parents{
    private String  sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public void grow() {
        super.grow();
         LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"Son","grow","h");
    }
}
