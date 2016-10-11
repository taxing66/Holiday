package com.joiway.devin.holiday.controller.abstracts;

import android.view.View;

/**
 * 抽象供继承，供多太使用,holder 映射数据到相应子视图；
 * @author 陈德华
 * @create 2016-09-24
 * @editer 陈德华
 * @date 2016-09-24
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */

public abstract class AbsHolder<T> {

    /**
     * 填充数据
     * @param t
     * @param position
     */
public abstract void setData(T t,int position);

    /**
     * 根据相应的标签类型去实例化需要的视图
     * @param holderTag
     * @return
     */
    public abstract int getResourceId(int holderTag);
}
