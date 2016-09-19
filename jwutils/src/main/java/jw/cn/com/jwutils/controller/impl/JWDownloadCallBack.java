package jw.cn.com.jwutils.controller.impl;

import android.app.Activity;

import java.io.File;
import java.lang.reflect.Type;

import jw.cn.com.jwutils.model.net.JWHeader;

/**
 * 下载请求回调
 * Created by Jianan on 2015/9/14.
 */
public abstract class JWDownloadCallBack extends JWRequestCallBack{

    public JWDownloadCallBack(Activity act){
        super(act);
    }

    @Override
    protected Type[] getDataJsonGenericTypes() {
        return new Type[]{Object.class, Object.class};
    }

    public final void handleDownloadFile(JWHeader header, File target){
        try {
            onSuccess(header, target);
            doOnFinally();
        }catch (Exception e){
            doOnException(e, e.getMessage());
        }
    }

    /**
     * 文件正常下载完毕后回调
     * @param header
     * @param target
     */
    protected abstract void onSuccess(JWHeader header, File target);
}