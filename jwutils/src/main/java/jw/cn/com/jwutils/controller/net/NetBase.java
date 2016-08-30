package jw.cn.com.jwutils.controller.net;

import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.net.ssl.SSLSocketFactory;

import jw.cn.com.jwutils.controller.interfaces.ICallbackBase;
import jw.cn.com.jwutils.controller.utils.JsonUtils;
import jw.cn.com.jwutils.controller.utils.LogUtils;
import jw.cn.com.jwutils.controller.utils.ReflectionUtils;
import jw.cn.com.jwutils.model.bean.NetIncomingParameterBean;

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
    /** 请求池 */
    private HashMap<Callback.CommonCallback,Callback.Cancelable> mRequestPool;
    public enum Method {
        POST, GET
    }

    public NetBase(){
        mRequestPool = new HashMap<>(30);
    }
    /**
     * 准备执行网络处理
     * 在{@link #}}方法中在进行 网络访问前调用，重新封装、添加上报参数等操作在此方法进行。
     */
    protected  abstract <P> void readyToProcess(Method method, String URL, P params, ICallbackBase callback);

    /**
     * {@link #}执行网络处理后，成功获取服务器响应时的处理，这一步将在准备调用调用callback前。
     * 如果服务器返回数据中带加密数据，请在这一步进行解密处理，并封装成泛型
     * @param serverBack 服务器返回的原文
     * @param entityClass 指定的实体
     * @return 已处理的服务器数据，若不需要处理，则将serverBack原文返回即可
     */
    protected abstract <B> B successProcess(String URL, String serverBack, Class<B> entityClass, ICallbackBase callback);

    /**
     * 执行网络处理后，服务器或网络访问失败时的处理。
     * @param exception
     */
    protected abstract void faildProcess(Throwable exception, ICallbackBase callback);

    protected <B> B doHttpSync(Method post, String url, NetIncomingParameterBean netIncomingParameterBean, Class<B> clz)throws Throwable {
        return doHttpsSync(post,url,netIncomingParameterBean,clz,null);
    }

    private <P,B> B doHttpsSync(Method method, String url, P params, Class<B> clz, SSLSocketFactory ssl)throws Throwable {
        RequestParams httpParams = getXUtilsHttpParams(method,url,params,null,ssl);
        return doXUtilsHttpSync(method,httpParams,clz);
    }

    /**
     * 执行网络处理
     * @param method
     * @param URL
     * @param params
     * @param callback
     */
    protected final <P, B> Callback.Cancelable doHttp(final Method method, final String URL, P params, final ICallbackBase<B> callback){
        return doHttps(method, URL, params, callback, null);
    }

    protected   <P , B> Callback.Cancelable doHttps(final Method method, final String URL, P params, final ICallbackBase<B> callback, SSLSocketFactory ssl){

        RequestParams httpParams = getXUtilsHttpParams(method,URL,params,callback,ssl);

        Callback.CommonCallback<String> cb = new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //获取泛型Class
                Class<B> genericClass = (Class<B>)ReflectionUtils.getParameterizedType(callback.getClass(),ICallbackBase.class,0);
                B entity = successProcess(URL,result,genericClass,callback);

                if(callback != null) {
                    callback.onSuccess(entity);
                }
                removeRequest(this);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if(ex instanceof java.net.ConnectException)
                    isOnCallback = true;

                if(ex instanceof HttpException)
                    isOnCallback = true;

                if(ex instanceof java.net.SocketTimeoutException)
                    isOnCallback = true;

                if(isOnCallback){
                    faildProcess(ex,callback);
                    if(callback != null)
                        callback.onError(ex);
                }
                removeRequest(this);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                faildProcess(cex,callback);
                removeRequest(this);
            }

            @Override
            public void onFinished() {
                if(callback != null)
                    callback.onFinish();
                removeRequest(this);
            }
        };
        //访问
        return putToRequestPool(cb,doXUtilsHttp(method, httpParams, cb));
    }

    private synchronized Callback.Cancelable putToRequestPool(Callback.CommonCallback callback, Callback.Cancelable cancelable){
        mRequestPool.put(callback, cancelable);
        return cancelable;
    }

    private synchronized void removeRequest(Callback.CommonCallback callback){
        mRequestPool.remove(callback);
    }

    /**
     * 基本xUtils的网络同步请求
     * @param method
     * @param params
     * @param tClass
     * @param <B>
     * @return
     * @throws Throwable
     */
    private <B> B doXUtilsHttpSync(Method method, RequestParams params , Class<B> tClass) throws Throwable {
        LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"NetBase","doXUtilsHttpSync", "request: " + params.toString());
        switch (method){
            case GET: return x.http().getSync(params,tClass);
            case POST:return x.http().postSync(params,tClass);
        }
        return null;
    }

    /**
     * 基本xUtils的网络请求
     * @param method
     * @param params
     * @param callback
     * @return
     * @throws Throwable
     */
    private Callback.Cancelable doXUtilsHttp(Method method, RequestParams params ,  Callback.CommonCallback<String> callback){
        LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"NetBase","doXUtilsHttpSync", "request: " + params.toString());
        switch (method){
            case GET: return x.http().get(params,callback);
            case POST:return x.http().post(params,callback);
        }
        return null;
    }

    private <P,B> RequestParams getXUtilsHttpParams(Method method, String URL, P params, ICallbackBase<B> callback, SSLSocketFactory ssl) {
        if(callback != null) {
            callback.onStart();
        }

        Object entity;

        if(params == null){
            entity = new Object();
        }else {
            entity = params;
        }

        readyToProcess(method,URL,entity,callback);
        //包装实体数据
        entity = pickupParam(entity);

        if(entity == null){
            return null;
        }
        //使用 xUitls 网络引擎访问
        RequestParams httpParams = new RequestParams(URL);
        if(entity instanceof File) {
            httpParams.addBodyParameter(((File) entity).getName(), (File) entity);
        }else{
            Field[] fields = ReflectionUtils.getAllField(entity.getClass());
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = null;

                try {
                    value = field.get(entity);//利用反射拿到实体属性的值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    LogUtils.logError(LogUtils.DEVELOPER_DEVIN,"NetBase","getXUtilsHttpParams", "processHttps: 反射取值错误，Class："
                            + entity.getClass().getName() + " Field:" + field.getName()+e);
                }
                if (value != null) {
                    if (value instanceof File) {
                        httpParams.setMultipart(true);
                        httpParams.addBodyParameter(field.getName(), (File) value);
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
        }

        if(ssl != null)
            httpParams.setSslSocketFactory(ssl);
        return httpParams;
    }

    /**
     * 包装实体数据，一般情况下不使用
     * @param entity 实体数据
     * @return 包装好的实体数据
     */
    protected Object pickupParam(Object entity){
        return entity;
    }
}
