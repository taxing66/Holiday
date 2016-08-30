package jw.cn.com.jwutils.model.bean;

import java.io.Serializable;

/**
 * 网络请求返回基类实体
 *
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NetOutputParameterBean<O,I> implements Serializable {
    private int status;
    private String msg;
    private Data<O,I> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data<O, I> getData() {
        return data;
    }

    public void setData(Data<O, I> data) {
        this.data = data;
    }

    public static class Data<O, I> {
        private O object;
        private I list;

        public O getObject() {
            return object;
        }

        public void setObject(O object) {
            this.object = object;
        }

        public I getList() {
            return list;
        }

        public void setList(I list) {
            this.list = list;
        }
    }
}
