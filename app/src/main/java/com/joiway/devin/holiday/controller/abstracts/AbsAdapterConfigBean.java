package com.joiway.devin.holiday.controller.abstracts;

import android.content.Context;

import java.util.List;

/**
 *抽象holder实体
 * @author 陈德华
 * @create 2016-09-24
 * @editer 陈德华
 * @date 2016-09-24
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public  class AbsAdapterConfigBean<T> {
    private List<T> mListData;
    private Context mContext;

    public List<T> getListData() {
        return mListData;
    }

    public void setListData(List<T> listData) {
        mListData = listData;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
