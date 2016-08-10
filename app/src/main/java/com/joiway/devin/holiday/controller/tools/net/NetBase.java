package com.joiway.devin.holiday.controller.tools.net;

import android.text.TextUtils;
import android.util.Log;

import com.joiway.devin.holiday.controller.Interface.ICallbackBase;
import com.joiway.devin.holiday.controller.tools.data.ReflectionUtils;
import com.joiway.devin.holiday.model.net.Header;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

/**
 * Created by 潘阳君 on 2016/1/11.
 * 【豆浆框架】-【控制层】
 * 【网络模块 内核层】
 * 现使用xUtils网络框架实现的Http/Https访问，如需要更换网络内核则重写该实现即可
 */
public abstract class NetBase {
    public static final String PUBLIC_TEXT_PLATFORM = "platform";
    public static final String PUBLIC_TEXT_SYSTEM = "system";
    public static final String PUBLIC_TEXT_TIMESTAMP = "timestamp";
    public static final String PUBLIC_TEXT_VERSIONS = "versions";
    public static final String TAG = "NetBase";

    public enum Method {
        GET, POST
    }

    /**
     * 请求池
     */
    private HashMap<Callback.CommonCallback, Callback.Cancelable> mRequestPool;

    public NetBase() {
        mRequestPool = new HashMap<>(30);
    }

    public synchronized void cancleAll() {
        Iterator<Callback.Cancelable> list = mRequestPool.values().iterator();
        while (list.hasNext()) {
            Callback.Cancelable cancelable = list.next();
            if (!cancelable.isCancelled()) {
                try {
                    cancelable.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "cancleAll: " + e.toString(), e);
                }
            }
        }
    }

    protected synchronized Callback.Cancelable putToRequestPool(Callback.CommonCallback callback, Callback.Cancelable cancelable) {
        mRequestPool.put(callback, cancelable);
        return cancelable;
    }

    protected synchronized void removeRequest(Callback.CommonCallback callback) {
        mRequestPool.remove(callback);
    }

    /**
     * 獲取xUtils 訪問 入參的httpParams;
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     * @param ssl
     * @param <P>
     * @return
     */
    protected final <P> RequestParams prepareToProcess(Method method, String URL, P params, RequestCallBack callback, SSLSocketFactory ssl) {
        if (callback != null) {
            callback.onStart();
        }

        Object entity;

        if (params == null) {
            entity = new Object();
        } else {
            entity = params;
        }

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
                Log.e(TAG, "processHttps: 反射取值错误，Class：" + entity.getClass().getName() + " Field:" + field.getName(), e);
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
     * 獲取xUtils 訪問 入參的httpParams;
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     * @param ssl
     * @param <P>
     * @param <B>
     * @return
     */
    protected final <P, B> RequestParams prepareToProcess(Method method, String URL, P params, ICallbackBase<B> callback, SSLSocketFactory ssl) {
        if (callback != null) {
            callback.onStart();
        }

        Object entity;

        if (params == null) {
            entity = new Object();
        } else {
            entity = params;
        }

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
                Log.e(TAG, "processHttps: 反射取值错误，Class：" + entity.getClass().getName() + " Field:" + field.getName(), e);
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
     * 执行网络处理
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P, B> Callback.Cancelable processHttp(final Method method, final String URL, P params, final ICallbackBase<B> callback) {
        return processHttps(method, URL, params, callback, null);
    }

    /**
     * 企业版本的执行网络处理
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P> Callback.Cancelable processHttp(final Method method, final String URL, P params, final RequestCallBack callback) {
        return processHttps(method, URL, params, callback, null);
    }

    /**
     * enterprise 执行网络处理
     *
     * @param method
     * @param URL
     * @param params
     */
    protected final <P> Callback.Cancelable processHttps(final Method method, final String URL, P params, final RequestCallBack requestCallBack, SSLSocketFactory ssl) {

        RequestParams httpParams = prepareToProcess(method, URL, params, requestCallBack, ssl);
        if(requestCallBack.doOnStart()){
            httpParams.setRequestTracker(new RequestTracker() {
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
                    if (result == null || !(result instanceof String) || TextUtils.isEmpty((String)result)) {
                        requestCallBack.doOnException(new Exception("Http response is null"), "Http response is null");
                        return;
                    }
                    requestCallBack.handleStringResponse(doParseHeaderFromXUtilResponse(request), (String)result);
                }

                private Header doParseHeaderFromXUtilResponse(UriRequest uriRequest){
                    Header header = null;
                    if(uriRequest != null){
                        header = new Header();
                        Map<String, List<String>> headerMap = uriRequest.getResponseHeaders();
                        List<String> headerValues;
                        for(String key : headerMap.keySet()){
                            if(key != null){
                                headerValues = headerMap.get(key);
                                if(headerValues.size() == 1){
                                    header.put(key.toLowerCase(), headerValues.get(0).toLowerCase());
                                }else {
                                    header.put(key.toLowerCase(), headerValues);
                                }
                            }
                        }
                    }
                    return header;
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
            });
            Callback.CommonCallback<String> cb = new Callback.ProgressCallback<String>() {

                @Override
                public void onSuccess(String result) {
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

                @Override
                public void onWaiting() {
                }

                @Override
                public void onStarted() {
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    requestCallBack.doOnLoading(total, current, isUploading);
                }
            };
            //访问
            return putToRequestPool(cb, process(method, httpParams, cb));
        }else {
            return null;
        }


    }

    /**
     * 执行网络处理
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P, B> Callback.Cancelable processHttps(final Method method, final String URL, P params, final ICallbackBase<B> callback, SSLSocketFactory ssl) {

        RequestParams httpParams = prepareToProcess(method, URL, params, callback, ssl);

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
                    faildProcess(ex, callback);
                    if (callback != null)
                        callback.onError(ex);
                }
                removeRequest(this);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                faildProcess(cex, callback);
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
        return putToRequestPool(cb, process(method, httpParams, cb));
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
        RequestParams httpParams = prepareToProcess(method, URL, params, (ICallbackBase<B>)null, ssl);
        return processSync(method, httpParams, bClass);
    }

    /**
     * 包装实体数据，一般情况下不使用
     *
     * @param entity 实体数据
     * @return 包装好的实体数据
     */
    protected Object pickupParam(Object entity) {
        return entity;
    }

    /**
     * 执行网络操作，使用xUtils引擎  perform
     *
     * @param method
     * @param params
     * @param callback
     */
    protected Callback.Cancelable process(Method method, RequestParams params, Callback.CommonCallback<String> callback) {
        switch (method) {
            case GET:
                return x.http().get(params, callback);
            case POST:
                return x.http().post(params, callback);
        }
        return null;
    }

    protected <T> T processSync(Method method, RequestParams params, Class<T> tClass) throws Throwable {

        Log.i(TAG, "request: " + params.toString());
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
    protected abstract void faildProcess(Throwable exception, ICallbackBase callback);


}
