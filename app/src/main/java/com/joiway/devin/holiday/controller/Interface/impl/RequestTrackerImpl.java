package com.joiway.devin.holiday.controller.Interface.impl;

import android.text.TextUtils;

import com.joiway.devin.holiday.controller.tools.net.RequestCallBack;
import com.joiway.devin.holiday.model.net.Header;

import org.xutils.http.RequestParams;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.request.UriRequest;

import java.util.List;
import java.util.Map;

/**
 * 请求过程追踪, 适合用来记录请求日志.
 * 所有回调方法都在主线程进行.
 * <p>
 * 用法:
 * 1. 将RequestTracker实例设置给请求参数RequestParams.
 * 2. 请的callback参数同时实现RequestTracker接口;
 * 3. 注册给UriRequestFactory的默认RequestTracker.
 * 注意: 请求回调RequestTracker时优先级按照上面的顺序,
 * 找到一个RequestTracker的实现会忽略其他.
 * @author 陈德华
 * @create 2016-08-10
 * @editer 陈德华
 * @date 2016-08-10
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class RequestTrackerImpl implements RequestTracker {
    private RequestCallBack requestCallBack;
    public RequestTrackerImpl(RequestCallBack requestCallBack){
        this.requestCallBack = requestCallBack;
    }
    @Override
    public void onWaiting(RequestParams params) {

    }

    @Override
    public void onStart(RequestParams params) {

    }

    @Override
    public void onRequestCreated(UriRequest request) {

    }

    @Override
    public void onCache(UriRequest request, Object result) {

    }

    @Override
    public void onSuccess(UriRequest request, Object result) {
        if (result == null || !(result instanceof String) || TextUtils.isEmpty((String) result)) {
            requestCallBack.doOnException(new Exception("Http response is null"), "Http response is null");
            return;
        }
        requestCallBack.handleStringResponse(doParseHeaderFromXUtilResponse(request), (String) result);
    }

    @Override
    public void onCancelled(UriRequest request) {
        requestCallBack.doOnCancelled();

    }

    @Override
    public void onError(UriRequest request, Throwable ex, boolean isCallbackError) {
        requestCallBack.doOnException(new Exception(ex), ex.getMessage());
    }

    @Override
    public void onFinished(UriRequest request) {

    }

    private Header doParseHeaderFromXUtilResponse(UriRequest uriRequest) {
        Header header = null;
        if (uriRequest != null) {
            header = new Header();
            Map<String, List<String>> headerMap = uriRequest.getResponseHeaders();
            List<String> headerValues;
            for (String key : headerMap.keySet()) {
                if (key != null) {
                    headerValues = headerMap.get(key);
                    if (headerValues.size() == 1) {
                        header.put(key.toLowerCase(), headerValues.get(0).toLowerCase());
                    } else {
                        header.put(key.toLowerCase(), headerValues);
                    }
                }
            }
        }
        return header;

    }

}
