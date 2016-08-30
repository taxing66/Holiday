package jw.cn.com.jwutils.controller.interfaces;

/**
 *网络监听回调基类
 * @author 陈德华
 * @create 2016-08-30
 * @editer 陈德华
 * @date 2016-08-30
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public interface ICallbackBase<E> {
    void onStart();
    void onFinish();
    void onSuccess(E e);
    void onError(Throwable exception);
}
