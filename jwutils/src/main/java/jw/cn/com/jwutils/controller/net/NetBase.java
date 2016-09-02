package jw.cn.com.jwutils.controller.net;

import android.text.TextUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.http.app.RequestTracker;
import org.xutils.http.request.UriRequest;
import org.xutils.x;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

import jw.cn.com.jwutils.controller.impl.JWDownloadCallBack;
import jw.cn.com.jwutils.controller.impl.JWRequestCallBack;
import jw.cn.com.jwutils.controller.interfaces.ICallbackBase;
import jw.cn.com.jwutils.controller.utils.FileUtils;
import jw.cn.com.jwutils.controller.utils.JsonUtils;
import jw.cn.com.jwutils.controller.utils.LogUtils;
import jw.cn.com.jwutils.controller.utils.ReflectionUtils;
import jw.cn.com.jwutils.model.net.JWExtFile;
import jw.cn.com.jwutils.model.net.JWHeader;
import jw.cn.com.jwutils.model.net.JWResponse;

/**
 * 网络框架基层
 *
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public abstract class NetBase {
    /**
     * 请求池
     */
    private HashMap<Callback.CommonCallback, Callback.Cancelable> mRequestPool;

    public enum Method {
        POST, GET
    }

    public NetBase() {
        mRequestPool = new HashMap<>(30);
    }

    /**
     * 准备执行网络处理
     * 在{@link #}}方法中在进行 网络访问前调用，重新封装、添加上报参数等操作在此方法进行。
     */
    protected abstract <P, I, O> String readyToProcess(Method method, String URL, P params, JWRequestCallBack<I, O> callback);

    /**
     * {@link #}执行网络处理后，成功获取服务器响应时的处理，这一步将在准备调用调用callback前。
     * 如果服务器返回数据中带加密数据，请在这一步进行解密处理，并封装成泛型
     *
     * @param serverBack  服务器返回的原文
     * @param entityClass 指定的实体
     * @return 已处理的服务器数据，若不需要处理，则将serverBack原文返回即可
     */
    protected abstract <B> B successProcess(String URL, String serverBack, Class<B> entityClass, ICallbackBase callback);

    /**
     * 执行网络处理后，服务器或网络访问失败时的处理。
     *
     * @param exception
     */
    protected abstract void faildProcess(Throwable exception, ICallbackBase callback);

    protected <P, B> B doHttpSyncPE(Method post, String url, P params, Class<B> clz) throws Throwable {
        return doHttpsSyncPE(post, url, params, clz, null);
    }

    protected <P, B> B doHttpsSyncPE(Method method, String url, P params, Class<B> clz, SSLSocketFactory ssl) throws Throwable {
        RequestParams httpParams = getXUtilsHttpParams(method, url, params, null, ssl);
        return doXUtilsHttpSync(method, httpParams, clz);
    }

    protected <P> JWResponse doHttpSync(Method post, String url, P params) throws Throwable {
        return doHttpsSync(post, url, params, null);
    }

    protected <P> JWResponse doHttpsSync(Method method, String url, P params, SSLSocketFactory ssl) throws Throwable {
        RequestParams httpParams = getXUtilsHttpParams(method, url, params, null, ssl);
        return doXUtilsHttpSync(method, httpParams);
    }


    /**
     * 执行网络处理
     *
     * @param method
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P, I, O> Callback.Cancelable doHttp(final Method method, final String URL, P params, final JWRequestCallBack<I, O> callback) {
        return doHttps(method, URL, params, callback, null);
    }

    protected <P, I, O> Callback.Cancelable doHttps(final Method method, final String URL, P params, final JWRequestCallBack<I, O> callback, SSLSocketFactory ssl) {

        RequestParams httpParams = getXUtilsHttpParams(method, URL, params, callback, ssl);

        Callback.CommonCallback<String> cb = getXUtilRequestCallBack(httpParams, callback);

        //访问
        return putToRequestPool(cb, doXUtilsHttp(method, httpParams, cb));
    }

    /**
     * 异步下载的请求方法
     * @param method
     * @param URL
     * @param params
     * @param callback
     * @param <P>
     */
    protected final <P> void doHttpDownload(final Method method, final String URL, P params, final JWDownloadCallBack callback) {
        doHttpsDownload(method, URL, params, callback, null);
    }

    protected <P> void doHttpsDownload(final Method method, final String URL, P params, final JWDownloadCallBack callback, SSLSocketFactory ssl) {
        HttpMethod httpMethod;
        if (method == Method.GET) {
            httpMethod = HttpMethod.GET;
        } else {
            httpMethod = HttpMethod.POST;
        }
        RequestParams httpParams = getXUtilsHttpParams(method, URL, params, callback, ssl);
        Callback.ProgressCallback<File> cb = getXUtilsDownloadCallBack(httpParams, callback);
        x.http().request(httpMethod, httpParams, cb);
    }

    protected Callback.ProgressCallback<File> getXUtilsDownloadCallBack(RequestParams requestParams, final JWDownloadCallBack downloadCallBack) {
        if (downloadCallBack.doOnStart()) {
            requestParams.setRequestTracker(new RequestTracker() {
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
                    if (request == null || !(result instanceof File)) {
                        downloadCallBack.doOnException(new Exception("response is null or not a file"), "response is null or not a file");
                        return;
                    }
                    JWHeader header = doParseHeaderFromXUtilResponse(request);
                    try {
                        if (header.get(JWHeader.KEY_STRING_CONTENT_TYPE).toString().contains(JWHeader.VALUE_STRING_CONTENT_TYPE_JSON)) {
                            String data = FileUtils.doReadStringFromFile((File) result);
                            LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN, "NetBase", "onSuccess", "Download file but get json:" + data);
                            downloadCallBack.handleStringResponse(header, data);
                            FileUtils.doDeleteFile((File) result);
                        } else {
                            downloadCallBack.handleDownloadFile(header, (File) result);
                        }
                    } catch (Exception e) {
                        downloadCallBack.doOnException(e, e.getMessage());
                    }
                }

                @Override
                public void onCancelled(UriRequest request) {
                    downloadCallBack.doOnCancelled();
                }

                @Override
                public void onError(UriRequest request, Throwable ex, boolean isCallbackError) {
                    ex.printStackTrace();
                    downloadCallBack.doOnException(new Exception(ex), ex.toString());
                }

                @Override
                public void onFinished(UriRequest request) {

                }
            });
            return new Callback.ProgressCallback<File>() {

                @Override
                public void onSuccess(File result) {
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }

                @Override
                public void onWaiting() {
                }

                @Override
                public void onStarted() {
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    downloadCallBack.doOnLoading(total, current, isUploading);
                }
            };
        } else {
            return null;
        }
    }

    private <I, O> Callback.CommonCallback getXUtilRequestCallBack(RequestParams requestParams, final JWRequestCallBack<I, O> requestCallBack) {
        if (requestCallBack.doOnStart()) {
            requestParams.setRequestTracker(new RequestTracker() {
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
            });
            return new Callback.ProgressCallback<String>() {

                @Override
                public void onSuccess(String result) {
                    removeRequest(this);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                }

                @Override
                public void onCancelled(CancelledException cex) {
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
        } else {
            return null;
        }
    }

    private JWHeader doParseHeaderFromXUtilResponse(UriRequest uriRequest) {
        JWHeader header = null;
        if (uriRequest != null) {
            header = new JWHeader();
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

    private synchronized Callback.Cancelable putToRequestPool(Callback.CommonCallback callback, Callback.Cancelable cancelable) {
        mRequestPool.put(callback, cancelable);
        return cancelable;
    }

    private synchronized void removeRequest(Callback.CommonCallback callback) {
        mRequestPool.remove(callback);
    }

    /**
     * 基本xUtils的网络同步请求
     *
     * @param method
     * @param params
     * @param tClass
     * @param <B>
     * @return
     * @throws Throwable
     */
    private <B> B doXUtilsHttpSync(Method method, RequestParams params, Class<B> tClass) throws Throwable {
        LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN, "NetBase", "doXUtilsHttpSync", "request: " + params.toString());
        switch (method) {
            case GET:
                return x.http().getSync(params, tClass);
            case POST:
                return x.http().postSync(params, tClass);
        }
        return null;
    }

    /**
     * 基本xUtils的网络同步请求
     *
     * @param method
     * @param params
     * @param <B>
     * @return
     * @throws Throwable
     */
    private <B> B doXUtilsHttpSync(Method method, RequestParams params) throws Throwable {
        LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN, "NetBase", "doXUtilsHttpSync", "request: " + params.toString());
        HttpMethod httpMethod = null;
        switch (method) {
            case GET:
                httpMethod = HttpMethod.GET;
                break;
            case POST:
                httpMethod = HttpMethod.POST;
                break;
        }
        return (B) doParseResponseFromBytes(x.http().requestSync(httpMethod, params, byte[].class));
    }

    private JWResponse doParseResponseFromBytes(byte[] bytes) {
        JWResponse response = null;
        if (bytes != null) {
            response = new JWResponse();
            response.setChartSet("UTF-8");
            response.setInputStream(new ByteArrayInputStream(bytes));
        }
        return response;
    }

    /**
     * 基本xUtils的网络请求
     *
     * @param method
     * @param params
     * @param callback
     * @return
     * @throws Throwable
     */
    private Callback.Cancelable doXUtilsHttp(Method method, RequestParams params, Callback.CommonCallback<String> callback) {
        LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN, "NetBase", "doXUtilsHttp", "request: " + params.toString());
        switch (method) {
            case GET:
                return x.http().get(params, callback);
            case POST:
                return x.http().post(params, callback);
        }
        return null;
    }


    /**
     * @param method
     * @param URL
     * @param params
     * @param callback
     * @param ssl
     * @param <P>
     * @param <I>
     * @param <O>
     * @return
     */
    private <P, I, O> RequestParams getXUtilsHttpParams(Method method, String URL, P params, JWRequestCallBack<I, O> callback, SSLSocketFactory ssl) {
        if (callback != null) {
            callback.doOnStart();
        }

        Object entity;

        if (params == null) {
            entity = new Object();
        } else {
            entity = params;
        }


        //排序计算
        String smValue;
        smValue = readyToProcess(method, URL, entity, callback);
        //包装实体数据
        entity = pickupParam(entity);

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
                LogUtils.logError(LogUtils.DEVELOPER_DEVIN, "NetBase", "getXUtilsHttpParams", "processHttps: 反射取值错误，Class："
                        + entity.getClass().getName() + " Field:" + field.getName() + e);
            }
            if (value != null) {
                if (value instanceof JWExtFile) {
                    httpParams.setMultipart(true);
                    httpParams.addBodyParameter(field.getName(), ((JWExtFile) value).getFile(), ((JWExtFile) value).getFileType());
                } else if (value instanceof Number || value instanceof Boolean || value instanceof String) {
                    //如果类型是 基本类型 则转成String
                    httpParams.addBodyParameter(field.getName(), value + "");
                } else {
                    //如果是 其他Object类型
                    httpParams.addBodyParameter(field.getName(), JsonUtils.toJsonStr(value));
                }
            } else {
                //为null不写入传参
//                httpParams.addBodyParameter(field.getName(), "null");
            }
            field.setAccessible(false);
        }

        httpParams.addBodyParameter("sm", smValue);
        if (ssl != null)
            httpParams.setSslSocketFactory(ssl);
        return httpParams;
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
}
