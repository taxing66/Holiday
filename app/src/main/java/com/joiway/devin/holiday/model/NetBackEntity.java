package com.joiway.devin.holiday.model;

import java.io.Serializable;

/**
 * Created by 潘阳君 on 2016/1/12.
 * 【豆浆框架】-【控制层】
 * 【网络模块 返回数据模型】
 * 所有使用【豆浆框架】网络访问服务器并返回的，必须继承这个数据模型进行返回。
 * 此数据模型 基于 兼职猫服务器数据模型进行设计。
 * <p/>
 * 使用方式：
 * 继承后必须按照接口需求建立自己的 entity与list相关内部类
 * 例如：
 * <code>
 * private BackEntity entity;
 * public static class BackEntity{
 * private String zm_open_id;
 * private int user_status;
 * private int id;
 * private int zm_status;
 * private int user_id;
 * private int zm_score;
 * <p/>
 * public void setZm_open_id(String zm_open_id) {
 * this.zm_open_id = zm_open_id;
 * }
 * <p/>
 * public void setUser_status(int user_status) {
 * this.user_status = user_status;
 * }
 * <p/>
 * public void setId(int id) {
 * this.id = id;
 * }
 * <p/>
 * public void setZm_status(int zm_status) {
 * this.zm_status = zm_status;
 * }
 * <p/>
 * public void setUser_id(int user_id) {
 * this.user_id = user_id;
 * }
 * <p/>
 * public void setZm_score(int zm_score) {
 * this.zm_score = zm_score;
 * }
 * <p/>
 * public String getZm_open_id() {
 * return zm_open_id;
 * }
 * <p/>
 * public int getUser_status() {
 * return user_status;
 * }
 * <p/>
 * public int getId() {
 * return id;
 * }
 * <p/>
 * public int getZm_status() {
 * return zm_status;
 * }
 * <p/>
 * public int getUser_id() {
 * return user_id;
 * }
 * <p/>
 * public int getZm_score() {
 * return zm_score;
 * }
 * }
 * </code>
 */
public class NetBackEntity implements Serializable {
    public static final long serialVersionUID = 1L;

    /**
     * status : 0
     * entity : {"zm_open_id":"268801632822103615904747381","user_status":2,"id":17,"zm_status":1,"user_id":3106169,"zm_score":717}
     * msg : 提交成功
     */

    private int status;
    private String msg;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isHttpTrue() {
        if (status < 1000) {
            return true;
        }
        return false;
    }
}
