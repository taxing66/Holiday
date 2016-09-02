package jw.cn.com.jwutils.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.alibaba.fastjson.TypeReference;

import org.xutils.common.Callback;

import java.io.File;

import jw.cn.com.jwutils.controller.impl.JWDownloadCallBack;
import jw.cn.com.jwutils.controller.impl.JWRequestCallBack;
import jw.cn.com.jwutils.controller.interfaces.INet;
import jw.cn.com.jwutils.controller.net.NetManager;
import jw.cn.com.jwutils.model.JWConfigBean;
import jw.cn.com.jwutils.model.bean.NetOutputParameterBean;
import jw.cn.com.jwutils.model.net.JWResponse;

/**
 * 框架界面基类
 *
 * @author 陈德华
 * @create 2016-08-31
 * @editer 陈德华
 * @date 2016-08-31
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class JWActivity extends FragmentActivity implements INet {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public <P> void doHttpsDownload(String URL, P params, JWDownloadCallBack callback, JWConfigBean configBean) {
        NetManager.httpsPostDownload(URL,params,callback,configBean);
    }

    @Override
    public <P> void doHttpDownload(String URL, P params, JWDownloadCallBack callback) {
       NetManager.httpPostDownload(URL,params,callback);
    }

    @Override
    public <I,O> Callback.Cancelable doHttp(String url, Object params, JWRequestCallBack<I,O> callback) {
        try {
            return NetManager.httpPost(url, params, callback);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public <I,O> Callback.Cancelable doHttps(JWConfigBean configBean, String url, Object params, JWRequestCallBack<I,O> callback) {
        try {
            return NetManager.httpsPost(url, params, callback, configBean);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public JWResponse doHttpsSync(JWConfigBean configBean, String url, Object params) {
       return NetManager.httpsPostSync(url,params,configBean);
    }

    @Override
    public  JWResponse doHttpSync(String url, Object params) {
        return NetManager.httpPostSync(url,params);
    }



    @Override
    public <B extends NetOutputParameterBean> NetOutputParameterBean doHttpSyncPE(String url, Object params, TypeReference<B> clz) {
        try {
            return NetManager.httpPostSyncPE(url,params,clz);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public <B extends NetOutputParameterBean> NetOutputParameterBean doHttpsSyncPE(String url, Object params, File file, TypeReference<B> clz) {
        try {
            return NetManager.httpPostSyncPE(url,params,file,clz);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
