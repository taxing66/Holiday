package com.joiway.devin.holiday.controller.tools.net;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.joiway.devin.holiday.controller.Interface.IMessageDialog;
import com.joiway.devin.holiday.controller.Interface.IServiceData;
import com.joiway.devin.holiday.controller.Interface.impl.DialogListenerImpl;
import com.joiway.devin.holiday.controller.Interface.impl.ServiceData;
import com.joiway.devin.holiday.controller.tools.data.ReflectionUtils;
import com.joiway.devin.holiday.model.net.DataJson;
import com.joiway.devin.holiday.model.net.Header;
import com.joiway.devin.holiday.model.net.RootJson;
import com.joiway.devin.holiday.tools.LogManager;
import com.joiway.devin.holiday.view.MessageDialog;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

/**
 * 网络请求回调
 *
 * @author 林佳楠
 * @create 2015-09-014
 * @editer 林佳楠
 * @date 2016-06-01
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public class RequestCallBack<I, O>{

    protected static Boolean sIsShowMessageDialog = false;

    protected WeakReference<Activity> mRef;

    public RequestCallBack(Activity act) {
        mRef = new WeakReference<>(act);
    }

    protected void onStart() {
    }

    /**
     * 请求成功后，在还没有进行自动化数据解析之前回调，也就是请求成功之后最先调用的成功方法
     * 原生請求返回的數據；
     * @param data 请求成功的数据
     */
    protected void onSuccess(Header header, String data) {
    }

    /**
     * JSON数据解析后处理器，解析JSON数据后在进入数据处理流程之前优先调用此方法，可在此方法中修改解析后的DataJson数据
     *
     * @return
     */
    protected DataJson onAfterJsonParser(DataJson dataJson) {
        return dataJson;
    }

    /**
     * JSON数据解析后处理器，解析JSON数据后在进入数据处理流程之前优先调用此方法，可在此方法中修改解析后的DataJson数据
     *
     * @return
     */
    protected DataJson onAfterJsonParser(DataJson dataJson, IServiceData serviceData) {
        return onAfterJsonParser(dataJson);
    }

    /**
     * 请求成功并且响应数据类型为JSON，无论响应状态码是多少都调用
     * 先调用此方法再调用{@link #onSuccess(Header, DataJson, String)}
     *
     * @param header
     * @param data
     * @param msg
     */
    protected void onSuccess(Header header, IServiceData data, String msg) {
    }

    /**
     * 请求成功并且响应数据类型为JSON，同时响应状态码为0才会回调
     * 应历史原因，在状态码为0时调用
     *
     * @param header http头部参数
     * @param data   请求数据
     * @param msg    请求附加信息
     */
    protected void onSuccess(Header header, DataJson<I, O> data, String msg) {
    }
    /**
     * 已处理状态码
     *
     * @param header
     * @param status
     * @param msg
     * @param data
     */
    protected void onHandledStatus(Header header, int status, String msg, DataJson<I, O> data) {
    }


    /**
     * 未识别的状态码
     *
     * @param header http头部参数
     * @param status 未识别的状态码
     * @param msg    请求附加信息
     * @param data   请求数据
     */
    protected void onUnCatchStatus(Header header, int status, String msg, DataJson<I, O> data) {
    }

    /**
     * 请求发生异常，网络异常或者数据解析错误
     *
     * @param e
     * @param msg
     */
    protected void onException(Exception e, String msg){
    }

    protected void onCancelled() {
    }

    /**
     * 无论发生什么情况，请求结束都会回调接口
     */
    protected void onFinally() {
    }

    public final boolean doOnStart(){
        try {
            onStart();
            return true;
        }catch (Exception e){
            doOnException(e, e.getMessage());
            return false;
        }
    }


    public final void doOnException(Exception e, String msg) {
        try {
            onException(e, msg);
            LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"RequestCallBack","doOnException","e:" + e + "msg:" + msg);
                e.printStackTrace();
        } catch (Exception ex) {
                 LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"RequestCallBack","doOnException","o..throw a new exception when catch exception...");
                e.printStackTrace();
                ex.printStackTrace();
        } finally {
            doOnFinally();
        }
    }

    public final boolean doOnCancelled(){
        try {
            onCancelled();
            return true;
        }catch (Exception e){
            doOnException(e, e.getMessage());
            return false;
        }
    }

    public final void doOnFinally() {
        try {
            onFinally();
        } catch (Exception e) {
                 LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"RequestCallBack","doOnFinally","sorry, try onFinally method and throw exception.");
                e.printStackTrace();
        }
    }

    public final void handleStringResponse(Header header, String response){
          LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"RequestCallBack","handleStringResponse", "call result:" + response);
        if (header.get(Header.KEY_STRING_CONTENT_TYPE) == null || TextUtils.isEmpty(header.get(Header.KEY_STRING_CONTENT_TYPE).toString())) {
            doOnException(new Exception("parse json fail"), "parse json fail");
            return;
        }

        try {
            onSuccess(header, response);

            if (header.get(Header.KEY_STRING_CONTENT_TYPE).toString().contains(Header.VALUE_STRING_CONTENT_TYPE_JSON)) {
                Type[] paramTypes = ReflectionUtils.getParameterizedTypes(RequestCallBack.this.getClass());
                RootJson rootJson = JSONObject.parseObject(response, RootJson.class);

                if (rootJson == null || rootJson.getStatus() == RootJson.TYPE_INT_STATUS_UNSET) {
                    doOnException(new Exception("parse json fail"), "parse json fail");
                    return;
                }

                IServiceData serviceData = ServiceData.createServiceData(rootJson.getStatus(), rootJson.getData());
                DataJson<I, O> dataJson = new DataJson<>();
                try {
                    Class<I> iClass = (Class<I>) paramTypes[0];
                    Class<O> oClass = (Class<O>) paramTypes[1];
                    dataJson.setList(serviceData.getList(iClass));
                    dataJson.setObj(serviceData.getObj(oClass));
                    dataJson.setPageSum(serviceData.getPageSum());
                    dataJson.setTotalRow(serviceData.getTotalRow());
                    onAfterJsonParser(dataJson, serviceData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                onSuccess(header, serviceData, rootJson.getMsg());

                switch (rootJson.getStatus()) {
                    case RootJson.TYPE_INT_STATUS_SUCCESS:
                        onSuccess(header, dataJson, rootJson.getMsg());
                        break;
                    //所有網絡請求總攔截：账号被禁用
                    case RootJson.TYPE_INT_STATUS_ACCOUNT_DISABLED:
                        synchronized (sIsShowMessageDialog) {
                            if (mRef != null && mRef.get() != null && !mRef.get().isFinishing() && !sIsShowMessageDialog) {
                                Activity act = mRef.get();
                                IMessageDialog msgDialog;
                                if(act instanceof IMessageDialog){
                                    msgDialog = (IMessageDialog) act;
                                    msgDialog.doCleanMsgDialog();
                                }else {
                                    msgDialog = MessageDialog.newInstance(act);
                                }
                                msgDialog.setMsgDialogMsg(rootJson.getMsg());
                                msgDialog.doHideMsgDialogLeftBtn(true);
                                msgDialog.setMsgDialogCancelable(false);
                                msgDialog.setMsgDialogListener(new DialogListenerImpl(){
                                    @Override
                                    public void onClickPositiveBtn(Dialog dialog) {
                                        super.onClickPositiveBtn(dialog);
                                        synchronized (sIsShowMessageDialog) {
                                            sIsShowMessageDialog = false;
                                        }
                                    }
                                });
                                msgDialog.doShowMsgDialog();
                                sIsShowMessageDialog = true;
                            }
                        }
                        onHandledStatus(header, rootJson.getStatus(), rootJson.getMsg(), dataJson);
                        break;
                    default:
                        onUnCatchStatus(header, rootJson.getStatus(), rootJson.getMsg(), dataJson);
                        break;
                }
            }
            doOnFinally();
        } catch (Exception e) {
            doOnException(e, e.toString());
        }
    }
}