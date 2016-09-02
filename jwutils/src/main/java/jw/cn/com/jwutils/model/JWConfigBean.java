package jw.cn.com.jwutils.model;

import android.app.Application;

/**
 * 应用配置信息实体
 *
 * @author 陈德华
 * @create 2016-08-31
 * @editer 陈德华
 * @date 2016-08-31
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class JWConfigBean {

    private static JWConfigBean sConfigBean;
    private int httpsClientKeystoreRawId;
    private int httpsTrustKeystoreRawId;
    private String httpsClientAssetStr;
    private String httpsTrustKeystoreAssetStr;
    private String httpsKeystorePwd;
    private boolean isDebug;
    private Application mApplication;

    private JWConfigBean(){

    }

    private JWConfigBean(Application application){
          this.mApplication = application;
    }

    public static JWConfigBean getInstance(Application application){
        if (sConfigBean==null){
            synchronized (JWConfigBean.class){
                if (sConfigBean==null){
                    return new JWConfigBean(application);
                }
            }
        }
        return sConfigBean;
    }

    public static JWConfigBean getConfigBean() {
        return sConfigBean;
    }

    public static void setConfigBean(JWConfigBean configBean) {
        sConfigBean = configBean;
    }

    public void setApplication(Application application) {
        mApplication = application;
    }

    public Application getApplication() {
        return mApplication;
    }

    public int getHttpsClientKeystoreRawId() {
        return httpsClientKeystoreRawId;
    }

    public void setHttpsClientKeystoreRawId(int httpsClientKeystoreRawId) {
        this.httpsClientKeystoreRawId = httpsClientKeystoreRawId;
    }

    public int getHttpsTrustKeystoreRawId() {
        return httpsTrustKeystoreRawId;
    }

    public void setHttpsTrustKeystoreRawId(int httpsTrustKeystoreRawId) {
        this.httpsTrustKeystoreRawId = httpsTrustKeystoreRawId;
    }

    public String getHttpsClientAssetStr() {
        return httpsClientAssetStr;
    }

    public void setHttpsClientAssetStr(String httpsClientAssetStr) {
        this.httpsClientAssetStr = httpsClientAssetStr;
    }

    public String getHttpsTrustKeystoreAssetStr() {
        return httpsTrustKeystoreAssetStr;
    }

    public void setHttpsTrustKeystoreAssetStr(String httpsTrustKeystoreAssetStr) {
        this.httpsTrustKeystoreAssetStr = httpsTrustKeystoreAssetStr;
    }

    public String getHttpsKeystorePwd() {
        return httpsKeystorePwd;
    }

    public void setHttpsKeystorePwd(String httpsKeystorePwd) {
        this.httpsKeystorePwd = httpsKeystorePwd;
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }
}
