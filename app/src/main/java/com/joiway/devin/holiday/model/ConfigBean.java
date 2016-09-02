package com.joiway.devin.holiday.model;


/**
 * app 信息配置实体根据开发模式实例化该配置实体:
 * 根据模式，初始化相应的配置变量（证书查找参数，网络请求链接）
 * 开发模式：1为编辑模式配置，2为测试模式配置，3上线模式配置，4、预发布模式配置
 *
 * @author 陈德华
 * @create 2016-08-29
 * @editer 陈德华
 * @date 2016-08-29
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class ConfigBean {
    private static ConfigBean sConfigBean;
    private static final int MODE_DEVELOPER = 1;
    private static final int MODE_TEST = 2;
    private static final int MODE_ONLINE = 3;
    private static final int MODE_PRE_ONLINE = 4;
    public static final int MODE_CURRENT = MODE_ONLINE;

    /**
     * http 请求根部
     */
    private String netRootHttpPost;
    /**
     * https 请求根部
     */
    private String netRootHttpsPost;
    private String netHttpBigData;
    private String netHttpDownloadApp;
    private int httpsClientKeystoreRawId;
    private int httpsTrustKeystoreRawId;

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

    /**
     * client keystore
     */
    private String httpsClientKeystoreName;
    /**
     * trust keystore
     */
    private String httpsTrustKeystoreName;
    /**
     * keystore password
     */
    private String httpsKeystorePwd;
    /**
     * application database version
     */
    private int dbVersion;
    /**
     * application version
     */
    private String appVersion;

    private ConfigBean() {
    }

    private ConfigBean(int mode) {
        setDbVersion(17);
        setAppVersion("1.6.4");
        switch (mode) {
            case MODE_DEVELOPER:
                setHttpsTrustKeystoreName("test_truststore");
                setHttpsClientKeystoreName("test_client");
                setHttpsKeystorePwd("j13579w");
                break;
            case MODE_TEST:
                setHttpsTrustKeystoreName("test_truststore");
                setHttpsClientKeystoreName("test_client");
                setHttpsKeystorePwd("j13579w");
                break;
            case MODE_ONLINE:
                setHttpsTrustKeystoreName("truststore");
                setHttpsClientKeystoreName("client");
                setHttpsKeystorePwd("20jw15gz");
                break;
            case MODE_PRE_ONLINE:
                setHttpsTrustKeystoreName("truststore");
                setHttpsClientKeystoreName("client");
                setHttpsKeystorePwd("20jw15gz");
                break;
        }
    }

    public static ConfigBean getInstance(int mode) {
        if (sConfigBean == null) {
            synchronized (ConfigBean.class) {
                if (sConfigBean == null) {
                    sConfigBean = new ConfigBean(mode);
                }
            }
        }
        return sConfigBean;
    }

    public void setNetHttpDownloadApp(String netHttpDownloadApp) {
        this.netHttpDownloadApp = netHttpDownloadApp;
    }

    public String getNetHttpDownloadApp() {
        return netHttpDownloadApp;
    }

    public void setNetHttpBigData(String netHttpBigData) {
        this.netHttpBigData = netHttpBigData;
    }

    public String getNetHttpBigData() {
        return netHttpBigData;
    }

    public String getNetRootHttpPost() {
        return netRootHttpPost;
    }

    public void setNetRootHttpPost(String netRootHttpPost) {
        this.netRootHttpPost = netRootHttpPost;
    }

    public String getNetRootHttpsPost() {
        return netRootHttpsPost;
    }

    public void setNetRootHttpsPost(String netRootHttpsPost) {
        this.netRootHttpsPost = netRootHttpsPost;
    }

    public String getHttpsClientKeystoreName() {
        return httpsClientKeystoreName;
    }

    public void setHttpsClientKeystoreName(String httpsClientKeystoreName) {
        this.httpsClientKeystoreName = httpsClientKeystoreName;
    }

    public String getHttpsTrustKeystoreName() {
        return httpsTrustKeystoreName;
    }

    public void setHttpsTrustKeystoreName(String httpsTrustKeystoreName) {
        this.httpsTrustKeystoreName = httpsTrustKeystoreName;
    }

    public String getHttpsKeystorePwd() {
        return httpsKeystorePwd;
    }

    public void setHttpsKeystorePwd(String httpsKeystorePwd) {
        this.httpsKeystorePwd = httpsKeystorePwd;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
