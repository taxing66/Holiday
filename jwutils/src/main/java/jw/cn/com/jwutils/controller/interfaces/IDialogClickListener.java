package jw.cn.com.jwutils.controller.interfaces;

import android.app.Dialog;

/**
 * dialog 点击监听
 *
 * @author 陈德华
 * @create 2016-07-02
 * @editer 陈德华
 * @date 2016-07-02
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public interface IDialogClickListener {
    void onClickNegativeBtn(Dialog dialog);

    void onClickPositiveBtn(Dialog dialog);
}
