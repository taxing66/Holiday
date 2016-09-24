package com.joiway.devin.holiday.controller.abstracts;

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
public abstract void setData(T t,int position);
}
