package com.joiway.devin.holiday.model.net;

import java.util.HashMap;

/**
 * Http头部参数
 *
 * @author 林佳楠
 * @create 2015-09-14
 * @editer 林佳楠
 * @date 2016-05-17
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public class Header {

    /**
     * http报头键值 - Accept-Charset
     */
    public static final String KEY_STRING_ACCEPT_CHARSET = "accept-charset";

    /**
     * http响应数据类型 - content-type
     */
    public static final String KEY_STRING_CONTENT_TYPE = "content-type";
    /**
     * http响应数据编码类型 - content-encoding 可能为空
     */
    public static final String KEY_STRING_CONTENT_ENCODING = "content-encoding";
    /**
     * http响应数据长度 - content-length 可能为-1，不准确
     */
    public static final String KEY_STRING_CONTENT_LENGTH = "content-length";

    /**
     * content-type值，响应数据为excel文件
     */
    public static final String VALUE_STRING_CONTENT_TYPE_EXCEL = "application/msexcel";
    /**
     * content-type值，响应数据为Json字符串
     */
    public static final String VALUE_STRING_CONTENT_TYPE_JSON = "application/json";

    private int stateCode;
    private HashMap<String, Object> headerMap = new HashMap<String, Object>();

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public Object put(String name, Object value){
        return headerMap.put(name, value);
    }

    public Object get(String name){
        return headerMap.get(name);
    }
}
