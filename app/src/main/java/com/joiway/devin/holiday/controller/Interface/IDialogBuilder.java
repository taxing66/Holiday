package com.joiway.devin.holiday.controller.Interface;

import android.app.Dialog;

import com.joiway.devin.holiday.model.DialogBuilderBean;

/**
 *dialog 的构建接口
 * @author 陈德华
 * @create 2016-07-03
 * @editer 陈德华
 * @date 2016-07-03
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public interface IDialogBuilder {
    /**
     * 构建builder的对象
     * @param dialogBuilderBean
     * @return
     */
    Dialog builder(DialogBuilderBean dialogBuilderBean, IDialogClickListener listener);
}
