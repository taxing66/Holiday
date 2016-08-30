package com.joiway.devin.holiday.model;

/**
 *更新配置实体
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class UpdateConfigBackBean {
    private int patch_version;//	最新版本号，0-无更新	number
    private String url;//	oss文件路径	string

    public int getPatch_version() {
        return patch_version;
    }

    public void setPatch_version(int patch_version) {
        this.patch_version = patch_version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
