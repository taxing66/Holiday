package com.joiway.devin.holiday.controller.Interface.impl;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.joiway.devin.holiday.controller.Interface.IServiceData;
import com.joiway.devin.holiday.model.constant.PostParam;

import java.util.List;

/**
 * 网络回到获取JSON数据解析类型工具类
 *
 * @author 林佳楠
 * @create 2016-05-18
 * @editor 林佳楠
 * @date 2016-05-18
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public class ServiceData implements IServiceData {
    /**
     * 服务器数据的 data 层
     */
    private String dataJson;
    private int status;

    private String listJson;
    private String obj;
    private int mPageSum;
    private int mTotalRow;

    public static ServiceData createServiceData(int status, String dataJson) {
        return new ServiceData(status, dataJson);
    }

    private ServiceData(int status, String dataJson) {
        this.dataJson = dataJson;
        this.status = status;

        if (!TextUtils.isEmpty(dataJson)) {
            try {
                org.json.JSONObject jsonObject = new org.json.JSONObject(dataJson);
                if (jsonObject.has(PostParam.JSON_KEY_STRING_OBJ)) {
                    obj = jsonObject.getJSONObject(PostParam.JSON_KEY_STRING_OBJ).toString();
                }
                if (jsonObject.has(PostParam.JSON_KEY_STRING_LIST)) {
                    listJson = jsonObject.getJSONArray(PostParam.JSON_KEY_STRING_LIST).toString();
                }
                if (jsonObject.has(PostParam.JSON_KEY_STRING_PAGE_SUM)) {
                    mPageSum = jsonObject.getInt(PostParam.JSON_KEY_STRING_PAGE_SUM);
                }
                if (jsonObject.has(PostParam.JSON_KEY_STRING_TOTAL_ROW)) {
                    mTotalRow = jsonObject.getInt(PostParam.JSON_KEY_STRING_TOTAL_ROW);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T> List<T> getList(Class<T> clazz) {
        if (!TextUtils.isEmpty(listJson)) {
            return JSON.parseArray(listJson, clazz);
        }
        return null;
    }

    @Override
    public <T> T getObj(Class<T> clazz) {
        if (!TextUtils.isEmpty(obj)) {
            return JSON.parseObject(obj, clazz);
        }
        return null;
    }

    @Override
    public String getDataJson() {
        return dataJson;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public int getPageSum() {
        return mPageSum;
    }

    @Override
    public int getTotalRow() {
        return mTotalRow;
    }
}
