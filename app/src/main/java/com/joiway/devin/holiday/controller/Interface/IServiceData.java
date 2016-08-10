package com.joiway.devin.holiday.controller.Interface;

import java.util.List;

/**
 * Created by Curtis on 2016/5/12.
 */
public interface IServiceData {
    <T> List<T> getList(Class<T> clazz);
    <T> T getObj(Class<T> clazz);
    String getDataJson();
    int getStatus();
    int getTotalRow();
    int getPageSum();
}
