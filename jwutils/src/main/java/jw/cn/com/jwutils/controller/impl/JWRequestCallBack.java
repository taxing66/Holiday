package jw.cn.com.jwutils.controller.impl;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

import jw.cn.com.jwutils.controller.interfaces.JWIServiceData;
import jw.cn.com.jwutils.controller.utils.GenericUtil;
import jw.cn.com.jwutils.controller.utils.LogUtils;
import jw.cn.com.jwutils.model.net.JWDataJson;
import jw.cn.com.jwutils.model.net.JWHeader;
import jw.cn.com.jwutils.model.net.RootJson;
import jw.cn.com.jwutils.view.MessageDialog;
/**
 *网络请求回调
 * @author 陈德华
 * @create 2016-09-01
 * @editer 陈德华
 * @date 2016-09-01
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class JWRequestCallBack<I,O> {


    protected static Boolean sIsShowMessageDialog = false;

    protected WeakReference<Activity> mRef;
    public JWRequestCallBack(){

    }

    public JWRequestCallBack(Activity act) {
        mRef = new WeakReference<>(act);
    }

    protected void onStart() {
    }

    protected void onLoading(long total, long current, boolean isUploading) {
    }

    /**
     * 请求成功后，在还没有进行自动化数据解析之前回调，也就是请求成功之后最先调用的成功方法
     *
     * @param data 请求成功的数据
     */
    protected void onSuccess(JWHeader header, String data) {
    }

    /**
     * JSON数据解析后处理器，解析JSON数据后在进入数据处理流程之前优先调用此方法，可在此方法中修改解析后的DataJson数据
     *
     * @return
     */
    protected JWDataJson onAfterJsonParser(JWDataJson dataJson) {
        return dataJson;
    }

    /**
     * JSON数据解析后处理器，解析JSON数据后在进入数据处理流程之前优先调用此方法，可在此方法中修改解析后的DataJson数据
     *
     * @return
     */
    protected JWDataJson onAfterJsonParser(JWDataJson dataJson, JWIServiceData serviceData) {
        return onAfterJsonParser(dataJson);
    }

    /**
     * 请求成功并且响应数据类型为JSON，无论响应状态码是多少都调用
     * 先调用此方法再调用{@link #onSuccess(JWHeader, JWDataJson, String)}
     *
     * @param header
     * @param data
     * @param msg
     */
    protected void onSuccess(JWHeader header, JWIServiceData data, String msg) {
    }

    /**
     * 请求成功并且响应数据类型为JSON，同时响应状态码为0才会回调
     * 应历史原因，在状态码为0时调用
     *
     * @param header http头部参数
     * @param data   请求数据
     * @param msg    请求附加信息
     */
    protected void onSuccess(JWHeader header, JWDataJson<I, O> data, String msg) {
    }

    /**
     * 已处理状态码
     *
     * @param header
     * @param status
     * @param msg
     * @param data
     */
    protected void onHandledStatus(JWHeader header, int status, String msg, JWDataJson<I, O> data) {
    }


    /**
     * 未识别的状态码
     *
     * @param header http头部参数
     * @param status 未识别的状态码
     * @param msg    请求附加信息
     * @param data   请求数据
     */
    protected void onUnCatchStatus(JWHeader header, int status, String msg, JWDataJson<I, O> data) {
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

    public  boolean doOnStart(){
        try {
            onStart();
            return true;
        }catch (Exception e){
            doOnException(e, e.getMessage());
            return false;
        }
    }

    public  boolean doOnLoading(long total, long current, boolean isUploading) {
        try {
            onLoading(total, current, isUploading);
            return true;
        }catch (Exception e){
            doOnException(e, e.getMessage());
            return false;
        }
    }


    public  void doOnException(Exception e, String msg) {
        try {
            onException(e, msg);
            LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"RequestCallBack","doOnException","e:" + e + "msg:" + msg);
            e.printStackTrace();
        } catch (Exception ex) {
            LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"RequestCallBack","doOnException","o..throw a new exception when catch exception...");
            e.printStackTrace();
            ex.printStackTrace();
        } finally {
            doOnFinally();
        }
    }

    public  boolean doOnCancelled(){
        try {
            onCancelled();
            return true;
        }catch (Exception e){
            doOnException(e, e.getMessage());
            return false;
        }
    }

    public  void doOnFinally() {
        try {
            onFinally();
        } catch (Exception e) {
            LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"RequestCallBack","doOnFinally","sorry, try onFinally method and throw exception.");
            e.printStackTrace();
        }
    }

    public  void handleStringResponse(JWHeader header, String response){
        LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"RequestCallBack","handleStringResponse","call result:" + response);
        if (header.get(JWHeader.KEY_STRING_CONTENT_TYPE) == null || TextUtils.isEmpty(header.get(JWHeader.KEY_STRING_CONTENT_TYPE).toString())) {
            doOnException(new Exception("parse json fail"), "parse json fail");
            return;
        }

        try {
            onSuccess(header, response);

            if (header.get(JWHeader.KEY_STRING_CONTENT_TYPE).toString().contains(JWHeader.VALUE_STRING_CONTENT_TYPE_JSON)) {
                Type[] paramTypes = getDataJsonGenericTypes();
                RootJson rootJson = JSONObject.parseObject(response, RootJson.class);

                if (rootJson == null || rootJson.getStatus() == RootJson.TYPE_INT_STATUS_UNSET) {
                    doOnException(new Exception("parse json fail"), "parse json fail");
                    return;
                }

                JWIServiceData serviceData = ServiceDataImpl.createServiceData(rootJson.getStatus(), rootJson.getData());
                JWDataJson<I, O> dataJson = new JWDataJson<>();
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
                                        if (mRef != null && mRef.get() != null && !mRef.get().isFinishing()) {
//                                            if (PtcApplication.getInstance().getPf().doGet().getInt(PtcSharedPreferences.VALUE_STRING_ACCOUNT_LOGIN_STATUS,
//                                                    PtcSharedPreferences.VALUE_INT_NOT_LOGIN) == PtcSharedPreferences.VALUE_INT_LOGIN) {
//                                                GlobalMethods.logoutPTCat(mRef.get());
//                                            }
                                        }
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

    /**
     * @return 获取DataJson的泛型类型
     */
    protected Type[] getDataJsonGenericTypes(){
        return GenericUtil.getParameterizedTypes(JWRequestCallBack.this.getClass());
    }

}
