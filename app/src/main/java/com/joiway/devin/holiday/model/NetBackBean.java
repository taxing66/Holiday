package com.joiway.devin.holiday.model;

import java.io.Serializable;

/**
 * 用途描述
 * 网络返回实体
 *
 * @author 潘阳君
 * @create 2016/7/6
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NetBackBean<E,L> implements Serializable {
    private String msg;
    private int status;
    private Data<E,L> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Data<E, L> getData() {
        return data;
    }

    public void setData(Data<E, L> data) {
        this.data = data;
    }

    public static class Data<E,L>{
        private E entity;
        private L list;

        public E getEntity() {
            return entity;
        }

        public void setEntity(E entity) {
            this.entity = entity;
        }

        public L getList() {
            return list;
        }

        public void setList(L list) {
            this.list = list;
        }
    }
}
