package com.joiway.devin.holiday.model;

import com.joiway.devin.holiday.controller.abstracts.AbsHolder;

/**
 * Created by Administrator on 2016/9/24.
 */

public class ListItemStringBean {
    public static final int PUBLIC_LIST_VIEW_BUTTON = 1;
    public static final int PUBILC_LIST_VIEW_SINGLE = 0;
    private String name;

    /**
     * 选择用哪个Holder去显示子项的数据
     */
    private int holderTag;

    public int getHolderTag() {
        return holderTag;
    }

    public void setHolderTag(int holderTag) {
        this.holderTag = holderTag;
    }

    public ListItemStringBean(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
