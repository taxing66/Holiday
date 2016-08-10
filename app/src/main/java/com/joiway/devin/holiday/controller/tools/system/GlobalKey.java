package com.joiway.devin.holiday.controller.tools.system;

import com.joiway.devin.holiday.BuildConfig;

/**
 * Created by Administrator on 2016/8/9.
 */
public class GlobalKey {

    //网络请求相关配置
    /**
     * 网络请求schema - http
     */
    public static final String NET_TYPE_STRING_HTTP_SCHEMA = "http://";
    /**
     * 网络请求schema - https
     */
    public static final String NET_TYPE_STRING_HTTPS_SCHEMA = "https://";
    /**
     * 线上大数据统计服务器主机
     */
    public static final String URL_STRING_ONLINE_BIGDATA_HOST = "api.static.jianzhimao.com:8081/";

    /**
     * 是否为发布版，用于判断是否输出日志
     */
    public static final boolean VALUE_BOOLEAN_IS_DEBUG = BuildConfig.DEBUG;
    /**
     * 本地数据密钥
     */
    public static final String VALUE_STRING_LOCAL_KEY = "I6545selalajianzhimaoshiwangzhe123dxf";

    public static final String KEY_STRING_STATISTICS_DATA = "mk_info";

    /**
     * android system tag
     */
    public static final String VALUE_STRING_SYSTEM_ANDROID = "1";

    /**
     * 当前App版本号
     */
    public static final String VALUE_STRING_APP_VERSION = "1.0.0";
    /**
     * 默认错误Toast文案
     */
    public static final String VALUE_STRING_DEFAULT_ERROR_TIPS = "网络或系统繁忙";
    /**
     * AES 加密使用的key
     */
    public static final String KEY_STRING_AES_KEY = "0Mm8eFTNp5o2qXJS";

    /**
     * SM 编码使用的密钥
     */
    public static final String KEY_STRING_SM_KEY = "jiuwei-company*-leonse%$3*";
}
