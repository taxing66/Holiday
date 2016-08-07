package com.joiway.devin.holiday.controller.tools.system;

import android.text.TextUtils;

/**
 *string format tools
 * @author 陈德华
 * @create 2016-07-07
 * @editer 陈德华
 * @date 2016-07-07
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class StringFormatManager {

    private  boolean testStringFormat(){
        String testA ="this book's price :%d%d";
         LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"StringFormatManager","testStringFormat",String.format(testA,34));
        return TextUtils.isEmpty(testA);
    }
}
