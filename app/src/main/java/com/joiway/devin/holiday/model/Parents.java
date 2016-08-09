package com.joiway.devin.holiday.model;


import com.joiway.devin.holiday.tools.LogManager;

/**
 * Created by Administrator on 2016/8/5.
 */
public abstract class Parents extends GrandParents {
    private int year;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override

    public void grow() {
        LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"Parents","grow","p");

    }
}
