package com.joiway.devin.holiday.controller.Interface;

/**
 * Created by Administrator on 2016/8/10.
 */
public interface ICallbackBaseEnterprise<I,O> {
    void onStart();
    void onFinish();
    void onSuccess(I i,O o);
    void onError(Throwable exception);
}
