package com.joiway.devin.holiday.controller.tools.net;

import android.text.TextUtils;

import com.joiway.devin.holiday.controller.Interface.ICallbackBase;
import com.joiway.devin.holiday.controller.Interface.ISm;
import com.joiway.devin.holiday.controller.Interface.impl.RequestTrackerImpl;
import com.joiway.devin.holiday.controller.tools.data.ReflectionUtils;
import com.joiway.devin.holiday.model.net.Header;
import com.joiway.devin.holiday.model.single.EnterpriseNetAccessEntity;
import com.joiway.devin.holiday.tools.LogManager;
import com.joiway.lib.base.cryptolib.CryptoJavaLib;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLSocketFactory;

/**
 *现使用xUtils网络框架实现的Http/Https访问，如需要更换网络内核则重写该实现即可
 * @author 陈德华
 * @create 2016-08-10
 * @editer 陈德华
 * @date 2016-08-10
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public abstract class NetBase {

    public final static String SMKey = "110jiuws5424.132fdsfei(";
    /**
     * 设备平台
     */
    public static final String PUBLIC_TEXT_PLATFORM = "platform";
    /**
     * 系统
     */
    public static final String PUBLIC_TEXT_SYSTEM = "system";
    /**
     * 时间轴
     */
    public static final String PUBLIC_TEXT_TIMESTAMP = "timestamp";
    /**
     * 版本号
     */
    public static final String PUBLIC_TEXT_VERSIONS = "versions";

    /**
     * 网络请求池容量
     */
    private static final int DEFAULT_REQUEST_POOL_CAPACITY =30;

    public enum Method {
        GET, POST
    }

    /**
     * 网络请求池
     */
    private HashMap<Callback.CommonCallback, Callback.Cancelable> mRequestPool;

    public NetBase() {
        mRequestPool = new HashMap<>(DEFAULT_REQUEST_POOL_CAPACITY);
    }

    /**
     * 取消所有的网络请求
     */
    public synchronized void cancelAll() {
        Iterator<Callback.Cancelable> list = mRequestPool.values().iterator();
        while (list.hasNext()) {
            Callback.Cancelable cancelable = list.next();
            if (!cancelable.isCancelled()) {
                try {
                    cancelable.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                    LogManager.logError(LogManager.DEVELOPER_DEVIN, "NetBase", "cancelAll", "cancelAll: " + e.toString());
                }
            }
        }
    }

    /**
     * 把当前的网络请求添加到网络请求池
     * @param callback
     * @param cancelable
     * @return
     */
    protected synchronized void putToRequestPool(Callback.CommonCallback callback, Callback.Cancelable cancelable) {
        mRequestPool.put(callback, cancelable);
    }

    /**
     * 删除指定的请求在请求池
     * @param callback
     */
    protected synchronized void removeRequest(Callback.CommonCallback callback) {
        mRequestPool.remove(callback);
    }

    /**
     * 企业版本獲取xUtils 訪問 入參的httpParams;
     *
     * @param url
     * @param params
     * @param callback
     * @param ssl
     * @param <P>
     * @return
     */
    private final <P> RequestParams getRequestParams(String url, P params, RequestCallBack callback, SSLSocketFactory ssl) {
        Object entity;

        if (params == null) {
            entity = new Object();
        } else {
            entity = params;
        }
        //排序计算
        String smValue;
        smValue = toSM(params);
        if (params instanceof ISm)
        ((ISm)params).setSm(smValue);
        if (entity == null) {
            return null;
        }
        //使用 xUitls 网络引擎访问
        RequestParams httpParams = new RequestParams(url);
        httpParams.setConnectTimeout(1000 * 10);
        httpParams.setCharset("UTF-8");
        Field[] fields = ReflectionUtils.getAllField(entity.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(entity);//利用反射拿到实体属性的值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                LogManager.logError(LogManager.DEVELOPER_DEVIN, "NetBase", "getRequestParams", "processHttps: 反射取值错误，Class：" + entity.getClass().getName() + " Field:" + field.getName());
            }
            if (value != null) {
                if (value instanceof File) {
                    if (!httpParams.isMultipart())
                        httpParams.setMultipart(true);
                    httpParams.addBodyParameter(field.getName(), (File) value);
                } else if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                    //如果类型是 基本类型 则转成String
                    httpParams.addBodyParameter(field.getName(), value + "");
                } else {
                    //如果是 其他Object类型
                    httpParams.addBodyParameter(field.getName(), JsonUtils.toJsonString(value));
                }
            }
            field.setAccessible(false);
        }

        if (ssl != null)
            httpParams.setSslSocketFactory(ssl);
        return httpParams;
    }

    /**
     * 添加SM计算
     *
     * @param params
     * @return
     */
    private final static <P> String toSM(P params) {
        ArrayList<String> paramlist = new ArrayList<>();

        Field[] fields = ReflectionUtils.getAllField(params.getClass());

        //设值
        for (Field field : fields) {
            String name = field.getName();
            if (!name.equals("sm")) {
                paramlist.add(name);
            }
        }
        return toSM(params, paramlist);
    }

    public static <P> String toSM(P params, ArrayList<String> paramName) {
        return toSM(params, paramName, SMKey);
    }

    /**
     * @param params
     * @param paramName
     * @param x         计算SM 的因子
     * @return
     */
    private final static <P> String toSM(P params, ArrayList<String> paramName, String x) {
        String smValue = "";

        Class pClass = params.getClass();

        //排序计算 根据字母顺序排序
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
                        md5 = CryptoJavaLib.md5(getFileBytes((File) value));
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

    private final static byte[] getFileBytes(File f) throws Exception {
        byte[] buff = new byte[(int) f.length()];
        FileInputStream fin = new FileInputStream(f);
        fin.read(buff);
        fin.close();
        return buff;
    }

    /**
     * 求取端版本獲取xUtils 訪問 入參的httpParams;
     *
     * @param URL
     * @param params
     * @param callback
     * @param ssl
     * @param <P>
     * @param <B>
     * @return
     */
    private final <P, B> RequestParams getRequestParams(String URL, P params, ICallbackBase<B> callback, SSLSocketFactory ssl) {
        if (callback != null) {
            callback.onStart();
        }

        Object entity;

        if (params == null) {
            entity = new Object();
        } else {
            entity = params;
        }

        //排序计算
        String smValue;
        smValue = toSM(params);
        if (params instanceof ISm)
            ((ISm)params).setSm(smValue);
        if (entity == null) {
            return null;
        }
        //使用 xUitls 网络引擎访问
        RequestParams httpParams = new RequestParams(URL);
        Field[] fields = ReflectionUtils.getAllField(entity.getClass());
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;

            try {
                value = field.get(entity);//利用反射拿到实体属性的值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                LogManager.logError(LogManager.DEVELOPER_DEVIN, "NetBase", "getRequestParams", "processHttps: 反射取值错误，Class：" + entity.getClass().getName() + " Field:" + field.getName()
                );
            }
            if (value != null) {
                if (value instanceof File) {
                    if (!httpParams.isMultipart())
                        httpParams.setMultipart(true);
                    httpParams.addBodyParameter(field.getName(), (File) value);
                } else if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                    //如果类型是 基本类型 则转成String
                    httpParams.addBodyParameter(field.getName(), value + "");
                } else {
                    //如果是 其他Object类型
                    httpParams.addBodyParameter(field.getName(), JsonUtils.toJsonString(value));
                }
            }
            field.setAccessible(false);
        }

        if (ssl != null)
            httpParams.setSslSocketFactory(ssl);
        return httpParams;
    }

    /**
     * 求职端执行网络处理
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P, B> void processHttp(final Method method, final String URL, P params, final ICallbackBase<B> callback) {
         processHttps(method, URL, params, callback, null);
    }

    /**
     * 企业版本的执行网络处理
     *
     * @param method  tag for post or get
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P> void processHttp(final Method method, final String URL, P params, final RequestCallBack callback) {
         processHttps(method, URL, params, callback, null);
    }

    /**
     * enterprise 执行网络处理
     *
     * @param method
     * @param URL
     * @param params
     * @param requestCallBack
     * @param ssl
     */
    protected final <P> void processHttps(final Method method, final String URL, P params, final RequestCallBack requestCallBack, SSLSocketFactory ssl) {

        RequestParams httpParams = getRequestParams( URL, params, requestCallBack, ssl);
        if (requestCallBack.doOnStart()) {
            httpParams.setRequestTracker(new RequestTrackerImpl(requestCallBack) {});
            Callback.CommonCallback<String> cb = new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    removeRequest(this);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    removeRequest(this);
                }

                @Override
                public void onCancelled(CancelledException cex) {
                    removeRequest(this);
                }

                @Override
                public void onFinished() {
                    removeRequest(this);
                }
            };
            //访问
             putToRequestPool(cb, process(method, httpParams, cb));
        }
    }



    /**
     * 求职端执行https网络处理
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P, B> void processHttps(final Method method, final String URL, P params, final ICallbackBase<B> callback, SSLSocketFactory ssl) {

        RequestParams httpParams = getRequestParams(URL, params, callback, ssl);

        Callback.CommonCallback<String> cb = new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //获取泛型Class
                Class<B> genericClass = (Class<B>) ReflectionUtils.getParameterizedType(callback.getClass(), ICallbackBase.class, 0);
                B entity = successProcess(URL, result, genericClass, callback);

                if (callback != null) {
                    callback.onSuccess(entity);
                }
                removeRequest(this);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex instanceof java.net.ConnectException)
                    isOnCallback = true;

                if (ex instanceof HttpException)
                    isOnCallback = true;

                if (ex instanceof java.net.SocketTimeoutException)
                    isOnCallback = true;

                if (isOnCallback) {
                    failProcess(ex, callback);
                    if (callback != null)
                        callback.onError(ex);
                }
                removeRequest(this);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                failProcess(cex, callback);
                removeRequest(this);
            }

            @Override
            public void onFinished() {
                if (callback != null)
                    callback.onFinish();
                removeRequest(this);
            }
        };
        //访问
         putToRequestPool(cb, process(method, httpParams, cb));
    }

    /**
     * 执行网络处理 同步方法
     *
     * @param method
     * @param URL
     * @param params
     */
    protected final <P, B> B processHttpSync(Method method, String URL, P params, Class<B> bClass) throws Throwable {
        return processHttpsSync(method, URL, params, bClass, null);
    }

    /**
     * 网络处理 同步方法
     *
     * @param method
     * @param URL
     * @param params
     * @param bClass
     * @param ssl
     * @param <P>
     * @param <B>
     * @return
     * @throws Throwable
     */
    protected final <P, B> B processHttpsSync(Method method, String URL, P params, Class<B> bClass, SSLSocketFactory ssl) throws Throwable {
        RequestParams httpParams = getRequestParams(URL, params, (ICallbackBase<B>) null, ssl);
        return processSync(method, httpParams, bClass);
    }

    /**
     * 执行网络操作，使用xUtils引擎  perform
     *
     * @param method
     * @param params
     * @param callback
     */
    private Callback.Cancelable process(Method method, RequestParams params, Callback.CommonCallback<String> callback) {
        switch (method) {
            case GET:
                return x.http().get(params, callback);
            case POST:
                return x.http().post(params, callback);
        }
        return null;
    }

    private  <T> T processSync(Method method, RequestParams params, Class<T> tClass) throws Throwable {
         LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"NetBase","processSync","request: " + params.toString());
        switch (method) {
            case GET:
                return x.http().getSync(params, tClass);
            case POST:
                return x.http().postSync(params, tClass);
        }
        return null;
    }

    /**
     * 准备执行网络处理
     * 在{@link #processHttps}}方法中在进行 网络访问前调用，重新封装、添加上报参数等操作在此方法进行。
     */
    protected abstract <P> void readyToProcess(Method method, String URL, P params, ICallbackBase callback);

    /**
     * {@link #processHttps}执行网络处理后，成功获取服务器响应时的处理，这一步将在准备调用调用callback前。
     * 如果服务器返回数据中带加密数据，请在这一步进行解密处理，并封装成泛型
     *
     * @param serverBack  服务器返回的原文
     * @param entityClass 指定的实体
     * @return 已处理的服务器数据，若不需要处理，则将serverBack原文返回即可
     */
    protected abstract <B> B successProcess(String URL, String serverBack, Class<B> entityClass, ICallbackBase callback);

    /**
     * {@link #processHttps}执行网络处理后，服务器或网络访问失败时的处理。
     *
     * @param exception
     */
    protected abstract void failProcess(Throwable exception, ICallbackBase callback);


}
