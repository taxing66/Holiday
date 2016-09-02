package jw.cn.com.jwutils.controller.interfaces;

import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;

import java.io.File;

import jw.cn.com.jwutils.controller.impl.JWDownloadCallBack;
import jw.cn.com.jwutils.controller.impl.JWRequestCallBack;
import jw.cn.com.jwutils.controller.impl.NetCallback;
import jw.cn.com.jwutils.controller.net.NetManager;
import jw.cn.com.jwutils.model.JWConfigBean;
import jw.cn.com.jwutils.model.bean.NetOutputParameterBean;
import jw.cn.com.jwutils.model.net.JWResponse;

/**
 * 网络请求接口
 *
 * @author 陈德华
 * @create 2016-08-31
 * @editer 陈德华
 * @date 2016-08-31
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public interface INet {
    /**
     * 异步下载
     * @param URL
     * @param params
     * @param callback
     * @param <P>
     */
    <P> void doHttpsDownload(String URL, P params, final JWDownloadCallBack callback, JWConfigBean configBean);

    /**
     * 异步下载
     * @param URL
     * @param params
     * @param callback
     * @param <P>
     */
    <P> void doHttpDownload(String URL, P params, final JWDownloadCallBack callback);
    /**
     * 异步http请求
     * @param url
     * @param params
     * @param callback
     * @param <I>
     * @param <O>
     * @return
     */
    <I, O> Callback.Cancelable doHttp(String url, Object params, JWRequestCallBack<I, O> callback);

    /**
     * 异步https 请求
     * @param configBean
     * @param url
     * @param params
     * @param callback
     * @param <I>
     * @param <O>
     * @return
     */
    <I, O> Callback.Cancelable doHttps(JWConfigBean configBean, String url, Object params, JWRequestCallBack<I, O> callback);

    /**
     * 同步不加密 https
     * @param configBean
     * @param url
     * @param params
     * @return
     */
   JWResponse doHttpsSync(JWConfigBean configBean, String url, Object params);

    /**
     * 同步不加密http
     * @param url
     * @param params
     * @return
     */
    JWResponse doHttpSync(String url, Object params);

    /**
     * 参数加密
     * @param url
     * @param params
     * @param clz
     * @param <B>
     * @return
     */
    <B extends NetOutputParameterBean> NetOutputParameterBean doHttpSyncPE(String url, Object params, TypeReference<B> clz);

    /**
     * 参数加密
     *
     * @param url
     * @param params
     * @param file
     * @param clz
     * @param <B>
     * @return
     */
    <B extends NetOutputParameterBean> NetOutputParameterBean doHttpsSyncPE(String url, Object params, File file, TypeReference<B> clz);

}
