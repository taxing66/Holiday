package com.joiway.devin.holiday.model;

import com.joiway.devin.holiday.controller.Interface.IGrow;
import com.joiway.devin.holiday.tools.LogManager;

/**
 * Created by Administrator on 2016/8/5.
 */
public abstract class GrandParents implements IGrow{
    private String name;
    public  void log(String content){
      LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"GrandParents","log","h");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void grow(int year) {
        grow();
         LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"GrandParents","grow","old:"+year);
    }
}
