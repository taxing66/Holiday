package com.joiway.devin.holiday.controller.tools.net;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.joiway.devin.holiday.controller.Interface.ICallbackBase;
import com.joiway.devin.holiday.controller.tools.data.ReflectionUtils;
import com.joiway.devin.holiday.controller.tools.file.FileUtils;
import com.joiway.devin.holiday.controller.tools.system.GlobalKey;
import com.joiway.devin.holiday.model.NetAccessBean;
import com.joiway.devin.holiday.model.NetAccessEntity;
import com.joiway.devin.holiday.model.NetBackBean;
import com.joiway.devin.holiday.model.NetBackEntity;
import com.joiway.devin.holiday.tools.LogManager;
import com.joiway.lib.base.cryptolib.CryptoJavaLib;
import com.joiway.lib.base.cryptolib.CryptoNativeLib;

import org.xutils.common.Callback;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用途描述
 * 网络管理器
 * @author 陈德华
 * @create 2016-08-10
 * @editer 陈德华
 * @date 2016-08-10
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NetManager extends NetBase {

    private static NetManager sNetManager;
    private Context mContext;
    private NetManager(Context context) {
       mContext = context.getApplicationContext();
    }

    public static NetManager getInstance(Context context) {
        if (sNetManager == null) {
            sNetManager = new NetManager(context);
        }
        return sNetManager;
    }

    /**
     * 适合 兼职猫的网络请求模块
     * Http访问
     *
     * @param URL
     * @param params   上报参数实体
     * @param callback 回调
     * @param <P>      上报参数实体必须继承{@link NetAccessEntity}
     * @param <B>      返回的参数实体必须继承{@link NetBackEntity}
     */
    public static final <P extends NetAccessEntity, B extends NetBackEntity> void httpPost(String URL, P params, NetCallback<B> callback) {
        if (sNetManager != null) {
             sNetManager.processHttp(Method.POST, URL, params, callback);
        }
    }

    /**
     * 适合 兼职猫的网络请求模块 不需要集体参数的接口
     * Http访问
     *
     * @param URL
     * @param params   上报参数实体
     * @param callback 回调
     * @param <P>      上报参数实体必须继承{@link NetAccessEntity}
     * @param <B>      返回的参数实体必须继承{@link NetBackEntity}
     */
    public static final <P , B extends NetBackEntity> void httpPost(String URL, P params, NetCallback<B> callback) {
        if (sNetManager != null) {
            sNetManager.processHttp(Method.POST, URL, params, callback);
        }
    }

    /**
     * 适合 兼职猫的网络请求模块
     * Http访问
     *
     * @param URL
     * @param params   上报参数实体
     * @param <P>      上报参数实体必须继承{@link NetAccessEntity}
     */
    public static final <P extends NetAccessEntity> void httpPost(String URL, P params,  RequestCallBack requestCallBack) {
        if (sNetManager != null) {
           sNetManager.processHttp(Method.POST, URL, params, requestCallBack);
        }
    }

    /**
     * 适合 兼职猫的网络请求模块
     * Https访问
     *
     * @param URL
     * @param params   上报参数实体
     * @param callback 回调
     * @param <B>      返回的参数实体必须继承{@link NetBackEntity}
     */
    public  final <B extends NetBackEntity> void httpsPost(String URL, NetAccessEntity params, NetCallback<B> callback) {
        if (sNetManager != null) {
            if (params == null) {
                params = new NetAccessEntity();
            }
           sNetManager.processHttps(Method.POST, URL, params, callback,SSLFactory.getInstance(mContext).getSSLSockeFactory(SSLFactory.VALUE_SSL_FOR_ONLINE));
        }
    }


    /**
     * post获取File同步方法
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @return
     */
    public  File downloadSync(String url, Object params) throws Throwable {
        return getInstance(mContext).processHttpSync(Method.POST, url, getNetAccessBean(params), File.class);
    }

    /**
     * post同步方法
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @param clzz   服务器返回实体数据
     * @return
     */
    public  <B extends NetBackBean> NetBackBean postSync(String url, Object params, TypeReference<B> clzz) throws Throwable {
        String jsonStr = getInstance(mContext).processHttpSync(Method.POST, url, getNetAccessBean(params), String.class);
        return getNetBackBean(jsonStr, clzz);
    }

    /**
     * post同步方法
     *
     * @param url    访问的方法
     * @param params 访问实体数据
     * @param clzz   服务器返回实体数据
     * @return
     */
    public  <B extends NetBackBean> NetBackBean postSync(String url, Object params, File file, TypeReference<B> clzz) throws Throwable {
        String jsonStr = getInstance(mContext).processHttpSync(Method.POST, url, getNetAccessBean(params, file), String.class);
        return getNetBackBean(jsonStr, clzz);
    }

    @Override
    protected <P> void readyToProcess(Method method, String URL, P params, ICallbackBase callback) {

    }

    @Override
    protected <B> B successProcess(String URL, String serverBack, Class<B> entityClass, ICallbackBase callback) {
        B entity = JsonUtils.fromJson(serverBack, entityClass);
        return entity;
    }

    @Override
    protected void failProcess(Throwable exception, ICallbackBase callback) {
    }

    /**
     * 获取加密的网络接口访问实体
     * params 中的file 可以不用添加进去，另外携带在file字段上
     *
     * @param params
     * @return
     */
    private static NetAccessBean getNetAccessBean(Object params) {
        String json = JsonUtils.toJsonString(JsonUtils.toMap(params));
        try {
            NetAccessBean accessBean = new NetAccessBean();
            accessBean.setParam(CryptoNativeLib.serverEncrypt(json, GlobalKey.KEY_STRING_AES_KEY.getBytes("UTF-8")));
            String sm = toSM(accessBean, defaultParamName());
            accessBean.setSm(sm);
            return accessBean;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据模式放入SM中的参数，只需要param;
     * @return
     */
    private static ArrayList<String> defaultParamName() {
        ArrayList<String> paramName = new ArrayList<>();
        paramName.add("param");
        return paramName;

    }

    /**
     * 获取加密的网络接口访问实体
     *
     * @param params
     * @return
     */
    private static NetAccessBean getNetAccessBean(Object params, File file) {
        String json = JsonUtils.toJsonString(JsonUtils.toMap(params));
        try {
            NetAccessBean accessBean = new NetAccessBean();
            accessBean.setParam(CryptoNativeLib.serverEncrypt(json, GlobalKey.KEY_STRING_AES_KEY.getBytes("UTF-8")));
            String sm = toSM(accessBean,defaultParamName());
            accessBean.setSm(sm);
            accessBean.setFile(file);
            return accessBean;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取已加密的网络访问返回实体
     *
     * @param serviceBackJson
     * @param clzz
     * @param <B>
     * @return
     */
    private static <B extends NetBackBean> NetBackBean getNetBackBean(String serviceBackJson, TypeReference<B> clzz) {
        String jsonStr;
        try {
            if (!TextUtils.isEmpty(serviceBackJson)) {
                jsonStr = CryptoJavaLib.decryptAes(serviceBackJson);
                return JSON.parseObject(jsonStr, clzz);
            }
        } catch (Exception e) {
            LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "NetManager", "getNetBackBean", "service back decryption fail." + e);
        }
        return null;
    }

    /**
     * 添加SM计算
     *
     * @param params
     * @return
     */
    private static <P> String toSM(P params) {
        ArrayList<String> paramlist = new ArrayList<>();

        Field[] fields = ReflectionUtils.getAllField(params.getClass());

        //设值
        for (Field field : fields) {
            String name = field.getName();
            if (!name.equals("sm") && !name.equals("file")) {
                paramlist.add(name);
            }
        }
        return toSM(params, paramlist);
    }

    public static <P> String toSM(P params, ArrayList<String> paramName) {
        return toSM(params, paramName, GlobalKey.KEY_STRING_SM_KEY);
    }

    /**
     * @param params
     * @param paramName
     * @param x         计算SM 的因子
     * @return
     */
    private static <P> String toSM(P params, ArrayList<String> paramName, String x) {
        String smValue = "";

        Class pClass = params.getClass();

        //排序计算
        Collections.sort(paramName);
        for (String name : paramName) {
            Object value;
            try {
                Field field = ReflectionUtils.findFieldWithLoopThrough(pClass, name);
                if (field == null)
                    throw new NoSuchFieldException(pClass.getName() + " 找不到字段：" + name);

                field.setAccessible(true);
                value = field.get(params);

                if (value == null) continue;

                if (value instanceof File) {
                    String md5;
                    try {
                        md5 = CryptoJavaLib.md5(FileUtils.getBytes((File) value));
                    } catch (Exception e) {
                        e.printStackTrace();
                        md5 = "0";
                    }
                    smValue += md5;
                } else {

                    //如果类型是 基本类型 则转成String
                    if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                        smValue += String.valueOf(value);
                    } else {
                        //如果是 其他Object类型
                        smValue += JsonUtils.toJsonString(value);
                    }
                }
                field.setAccessible(false);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String s1 = getEgNum(smValue);
        try {
            s1 = CryptoJavaLib.md5((s1 + x).getBytes());//密码因子，增加破解难度
            return CryptoJavaLib.md5(s1.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getEgNum(String s) {
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m1 = p.matcher(s);
        return m1.replaceAll("").trim();
    }

    public  abstract class NetCallback<Entity> implements ICallbackBase<Entity> {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess(Entity e) {

        }

        @Override
        public void onError(Throwable exception) {

        }

        @Override
        public void onFinish() {

        }
    }
}
