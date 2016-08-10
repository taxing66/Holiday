package com.joiway.devin.holiday.model.constant;

import com.joiway.devin.holiday.controller.tools.system.GlobalKey;

/**
 *访问网络的地址容器
 * @author 陈德华
 * @create 2016-08-10
 * @editer 陈德华
 * @date 2016-08-10
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NetUrl {
    /**
     * 大数据统计请求的根地址
     */
    public static final String URL_STRING_ROOT_HTTP_FOR_BIG_DATA = GlobalKey.NET_TYPE_STRING_HTTP_SCHEMA + GlobalKey.URL_STRING_ONLINE_BIGDATA_HOST;
    /**
     * 大数据统计MK注册绑定
     */
    public static final String URL_STRING_BIG_DATA_MK = URL_STRING_ROOT_HTTP_FOR_BIG_DATA + "data/deviceInfo";
}
