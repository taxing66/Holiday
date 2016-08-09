package com.joiway.devin.holiday.model;

import java.io.File;
import java.io.Serializable;

/**
 * 用途描述
 * 网络访问共有实体
 *
 * @author 潘阳君
 * @create 2016/7/6
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class NetAccessBean implements Serializable{

    private String sm;
    private String param;
    private File file;

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
