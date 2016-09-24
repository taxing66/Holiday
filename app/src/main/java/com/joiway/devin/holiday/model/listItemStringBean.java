package com.joiway.devin.holiday.model;

import com.joiway.devin.holiday.controller.abstracts.AbsHolder;

/**
 * Created by Administrator on 2016/9/24.
 */

public class ListItemStringBean {
    private String name;


    public ListItemStringBean(String name){
        setName(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
