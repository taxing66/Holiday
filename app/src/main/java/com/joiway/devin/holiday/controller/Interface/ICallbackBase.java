package com.joiway.devin.holiday.controller.Interface;

/**
 * 用途描述
 * 网络回调的基本接口
 *
 * @author 潘阳君
 * @create 2016/6/3
 * @docVersion 适用于《Android规范v1.0.0 alpha》版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public interface ICallbackBase<Entity> {
    void onStart();
    void onFinish();
    void onSuccess(Entity e);
    void onError(Throwable exception);
}
