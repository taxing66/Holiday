package jw.cn.com.jwutils.controller.impl;

import android.app.Dialog;

import jw.cn.com.jwutils.controller.interfaces.IDialogClickListener;

/**
 * Dialog 点击接口的实现类
 * @author 陈德华
 * @create 2016-07-02
 * @editer 陈德华
 * @date 2016-07-02
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class DialogListenerImpl implements IDialogClickListener {
    @Override
    public void onClickNegativeBtn(Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void onClickPositiveBtn(Dialog dialog) {
        dialog.dismiss();
    }
}
