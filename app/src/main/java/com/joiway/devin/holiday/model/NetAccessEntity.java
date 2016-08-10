package com.joiway.devin.holiday.model;


import com.joiway.devin.holiday.controller.tools.net.JsonUtils;
import com.joiway.devin.holiday.controller.tools.system.GlobalKey;

import java.io.Serializable;

/**
 * Created by 潘阳君 on 2016/1/12.
 *【豆浆框架】-【控制层】
 *【网络模块 访问数据模型】
 * 所有使用【豆浆框架】网络访问的，必须继承这个数据模型进行访问
 */
public class NetAccessEntity implements Serializable{
    public static final long serialVersionUID = 1L;
    /**
     * timestamp : 1452566340384
     * request_tag : de50dee2228c83dadb293e3438f42ea5
     * userid : 3106169
     * statistics_data : {"userid":"3106169","sys_info_sign":"","app_version":"3.3.0"}
     * sm :
     */


    private String timestamp = "";
    private String request_tag = "";
    private String userid = "0";
    private String versions = GlobalKey.VALUE_STRING_APP_VERSION;
    private String system = GlobalKey.VALUE_STRING_SYSTEM_ANDROID;
    private String token = "0"; //签名授权码

    /**
     * userid : 3106169
     * sys_info_sign :
     * app_version : 3.3.0
     */

    private StatisticsDataEntity statistics_data;
    private String sm;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersions() {
        return versions;
    }

    public void setVersions(String versions) {
        this.versions = versions;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setRequest_tag(String request_tag) {
        this.request_tag = request_tag;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setStatistics_data(StatisticsDataEntity statistics_data) {
        this.statistics_data = statistics_data;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getRequest_tag() {
        return request_tag;
    }

    public String getUserid() {
        return userid;
    }

    public StatisticsDataEntity getStatistics_data() {
        return statistics_data;
    }

    public String getSm() {
        return sm;
    }

    public static class StatisticsDataEntity {
        private String userid;
        private String sys_info_sign;
        private String app_version;

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setSys_info_sign(String sys_info_sign) {
            this.sys_info_sign = sys_info_sign;
        }

        public void setApp_version(String app_version) {
            this.app_version = app_version;
        }

        public String getUserid() {
            return userid;
        }

        public String getSys_info_sign() {
            return sys_info_sign;
        }

        public String getApp_version() {
            return app_version;
        }

        @Override
        public String toString() {
            return JsonUtils.toJsonString(this);
        }
    }
}
