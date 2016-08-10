package com.joiway.devin.holiday.model.net;

/**
 *大数据请求入参实体
 * @author 陈德华
 * @create 2016-08-10
 * @editer 陈德华
 * @date 2016-08-10
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class BigDataMkBean {
    private String sys_info;
    private String sys_info_sign;
    private String  type;
    private String userid;

    public String getSys_info() {
        return sys_info;
    }

    public void setSys_info(String sys_info) {
        this.sys_info = sys_info;
    }

    public String getSys_info_sign() {
        return sys_info_sign;
    }

    public void setSys_info_sign(String sys_info_sign) {
        this.sys_info_sign = sys_info_sign;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
