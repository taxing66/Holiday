package com.joiway.devin.holiday.model.net;

import com.joiway.devin.holiday.controller.Interface.ISm;

/**
 *大数据请求入参实体
 * @author 陈德华
 * @create 2016-08-10
 * @editer 陈德华
 * @date 2016-08-10
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class BigDataMkBean  implements ISm{
    private String sys_info;
    private String sys_info_sign;
    private String  type;
    private String userid;
    private String sm;

    @Override
    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getSm() {
        return sm;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BigDataMkBean that = (BigDataMkBean) o;

        if (sys_info != null ? !sys_info.equals(that.sys_info) : that.sys_info != null)
            return false;
        if (sys_info_sign != null ? !sys_info_sign.equals(that.sys_info_sign) : that.sys_info_sign != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (userid != null ? !userid.equals(that.userid) : that.userid != null) return false;
        return sm != null ? sm.equals(that.sm) : that.sm == null;

    }

    @Override
    public int hashCode() {
        int result = sys_info != null ? sys_info.hashCode() : 0;
        result = 31 * result + (sys_info_sign != null ? sys_info_sign.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (sm != null ? sm.hashCode() : 0);
        return result;
    }
}
