package com.joiway.devin.holiday.model.single;

import android.text.TextUtils;

import com.joiway.devin.holiday.controller.tools.net.NetManager;
import com.joiway.devin.holiday.controller.tools.system.GlobalKey;
import com.joiway.devin.holiday.controller.tools.system.HolidayApplication;
import com.joiway.devin.holiday.controller.tools.system.NetUtils;
import com.joiway.devin.holiday.model.constant.NetUrl;
import com.joiway.devin.holiday.model.constant.PostEnterParam;
import com.joiway.devin.holiday.tools.LogManager;
import com.joiway.lib.base.cryptolib.CryptoJavaLib;
import com.joiway.lib.security.PhoneSec;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 企业端共有入参实体
 *
 * @author 陈德华
 * @create 2016-08-10
 * @editer 陈德华
 * @date 2016-08-10
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class EnterpriseNetAccessEntity implements Serializable {
    /**
     * 大数据统计状态 - 没有初始化
     */
    private static final int VALUE_INT_MK_REGISTER_STATUS_NO_INIT = 0;
    /**
     * 大数据统计状态 - 没有注册
     */
    private static final int VALUE_INT_MK_REGISTER_STATUS_NO = 1;
    /**
     * 大数据统计状态 - 已经注册
     */
    private static final int VALUE_INT_MK_REGISTER_STATUS_YES = 2;
    /**
     * 大数据统计状态 - 正在注册中
     */
    private static final int VALUE_INT_MK_REGISTER_STATUS_PROCESSING = 3;
    /**
     * 大数据统计注册状态
     */
    public static Integer sMKRegisterStatus = VALUE_INT_MK_REGISTER_STATUS_NO_INIT;
    private String timestamp;
    private String versions = GlobalKey.VALUE_STRING_APP_VERSION;
    private String system =GlobalKey.VALUE_STRING_SYSTEM_ANDROID;
    private String sStatisticsData;

    public EnterpriseNetAccessEntity(){
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.sStatisticsData = getStatisticsData();
    }

    /**
     * 获取statistics_data
     * @return
     */
    private String getStatisticsData(){
        if(TextUtils.isEmpty(sStatisticsData) || sStatisticsData.contains("\"user_id\":\"0\"")){
            String temp = getStatisticsDataFromPf();
            if(TextUtils.isEmpty(temp)){
                sStatisticsData = doGenerateStatisticsData();
                putStatisticsDataToPf(sStatisticsData);
                 LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","getStatisticsData","generate statistics:" + sStatisticsData);
            }else{
                sStatisticsData = temp;
                 LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","getStatisticsData", "get statistics form pf:" + sStatisticsData);
            }
        }
        doRegisterMK();
        return sStatisticsData;
    }

    /**
     * 向服务器绑定mk值
     */
    private void doRegisterMK(){
        synchronized (sMKRegisterStatus){
            if(sMKRegisterStatus == VALUE_INT_MK_REGISTER_STATUS_NO_INIT){
                if(HolidayApplication.getInstance().getSpfManager().getSpf().getBoolean(GlobalKey.KEY_STRING_STATISTICS_DATA, false)){
                    sMKRegisterStatus = VALUE_INT_MK_REGISTER_STATUS_YES;
                }else{
                    sMKRegisterStatus = VALUE_INT_MK_REGISTER_STATUS_NO;
                }
            }
            if(!GlobalKey.VALUE_BOOLEAN_IS_DEBUG && sMKRegisterStatus == VALUE_INT_MK_REGISTER_STATUS_NO){
                String sysInfo = PhoneSec.getStatisticalData(HolidayApplication.getInstance());
                if(TextUtils.isEmpty(sysInfo)){
                   LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","doRegisterMK","Get device infomation fail.");
                    return;
                }else{
                     LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","doRegisterMK_1", "sys_info:\n" + sysInfo);
                    try {
                        sysInfo = CryptoJavaLib.encryptTripleDes(sysInfo, GlobalKey.VALUE_STRING_LOCAL_KEY);
                    }catch (Exception e){
                        LogManager.logError(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","doRegisterMK",e.toString());
                        return;
                    }
                }
                String mk = PhoneSec.makeMK(HolidayApplication.getInstance());
//                NetManager.httpPost(NetUrl.URL_STRING_BIG_DATA_MK,);
//                String sm = MD5Util.MD5ExamNum(sysInfo + mk + PostEnterParam.VALUE_STRING_DEVICE_ANDROID + sCompID, GlobalKey.VALUE_STRING_SM_KEY_FOR_BIGDATA).toString();
//                NetHelper.build(NetUrl.URL_STRING_BIG_DATA_MK)
//                        .addBodyParameter(PostEnterParam.KEY_STRING_SYS_INFO, sysInfo)
//                        .addBodyParameter(PostEnterParam.KEY_STRING_SYS_INFO_SIGN, mk)
//                        .addBodyParameter(PostEnterParam.KEY_STRING_TYPE, PostEnterParam.VALUE_STRING_DEVICE_ANDROID)
//                        .addBodyParameter("userid", sCompID)
//                        .addBodyParameter(PostEnterParam.KEY_STRING_SM, sm)
//                        .sendPostDirect(new RequestCallBack<String, String>(null) {
//                            @Override
//                            public void onStart() {
//                                synchronized (NetHelper.sMKRegisterStatus){
//                                    NetHelper.sMKRegisterStatus = NetHelper.VALUE_INT_MK_REGISTER_STATUS_PROCESSING;
//                                }
//                            }
//
//                            @Override
//                            public void onSuccess(Header header, DataJson data, String msg) {
//                                synchronized (NetHelper.sMKRegisterStatus){
//                                    NetHelper.sMKRegisterStatus = NetHelper.VALUE_INT_MK_REGISTER_STATUS_YES;
//                                    PtcApplication.getInstance().getPf().doPut().putBoolean(PtcSharedPreferences.KEY_STRING_IS_MK_REGISTER, true).commit();
//                                    GlobalMethods.customLog(GlobalKey.TYPE_INT_LOG_VERBOSE, null, "register mk success");
//                                }
//                            }
//
//                            @Override
//                            public void onException(Exception e, String msg) {
//                                synchronized (NetHelper.sMKRegisterStatus){
//                                    NetHelper.sMKRegisterStatus = NetHelper.VALUE_INT_MK_REGISTER_STATUS_NO;
//                                    GlobalMethods.customLog(GlobalKey.TYPE_INT_LOG_ERROR, null, "register mk fail:\n" + e.toString());
//                                }
//                            }
//
//                            @Override
//                            public void onUnCatchStatus(Header header, int status, String msg, DataJson data) {
//                                synchronized (NetHelper.sMKRegisterStatus){
//                                    NetHelper.sMKRegisterStatus = VALUE_INT_MK_REGISTER_STATUS_NO;
//                                    GlobalMethods.customLog(GlobalKey.TYPE_INT_LOG_ERROR, null, "register mk fail:" + msg);
//                                }
//                            }
//                        });
            }
        }
    }

    /**
     * 保存statistics_data到Preference
     * @param target
     */
    private void putStatisticsDataToPf(String target){
        if(TextUtils.isEmpty(target) || target.contains("\"user_id\":\"0\"")){
            return;
        }
        try {
            String encryptStr = CryptoJavaLib.decryptTripleDes(target, GlobalKey.VALUE_STRING_LOCAL_KEY);
            HolidayApplication.getInstance().getSpfManager().put(GlobalKey.KEY_STRING_STATISTICS_DATA, encryptStr);
        }catch (Exception e){
             LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","putStatisticsDataToPf",e.toString());
        }
    }

    /**
     * 生成statistics_data数据
     * @return
     */
    private String doGenerateStatisticsData(){
        try {
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("userid", sCompID);
            jsonObject.put(PostEnterParam.KEY_STRING_APP_VERSION, GlobalKey.VALUE_STRING_APP_VERSION);
            jsonObject.put(PostEnterParam.KEY_STRING_SYS_INFO_SIGN, PhoneSec.makeMK(HolidayApplication.getInstance()));
            return jsonObject.toString();
        } catch (JSONException e) {
             LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","doGenerateStatisticsData", e.toString());
            return null;
        }
    }

    /**
     * 从Preference中获取statistics_data
     * @return
     */
    private String getStatisticsDataFromPf(){
        String srcData = HolidayApplication.getInstance().getSpfManager().getSpf().getString(GlobalKey.KEY_STRING_STATISTICS_DATA, null);
        if(!TextUtils.isEmpty(srcData)){
            try {
                return CryptoJavaLib.encryptTripleDes(srcData,GlobalKey.VALUE_STRING_LOCAL_KEY);
            }catch (Exception e){
                LogManager.logError(LogManager.DEVELOPER_DEVIN,"EnterpriseNetAccessEntity","getStatisticsDataFromPf",e.toString());
                return null;
            }
        }else {
            return null;
        }
    }

}
