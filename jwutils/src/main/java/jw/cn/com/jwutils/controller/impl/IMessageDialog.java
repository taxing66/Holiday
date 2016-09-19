package jw.cn.com.jwutils.controller.impl;

import jw.cn.com.jwutils.controller.interfaces.IDialogClickListener;

/**
 * 信息对话框接口
 *
 * @author 林佳楠
 * @create 2016-06-22
 * @editor 林佳楠
 * @date 2016-06-22
 * @docVersion 适用于代码规范v1.0.0 alpha版本
 */
public interface IMessageDialog extends IDialogBuilder {
    /**
     * 设置标题图标
     * @param resourceID
     */
    void setMsgDialogTitleIcon(int resourceID);
    /**
     * 设置标题
     * @param strTitle
     */
    void setMsgDialogTitle(String strTitle);
    /**
     * 隐藏标题栏
     */
    void doHideMsgDialogTitleBar();
    /**
     * 隐藏标题图标
     */
    void doHideMsgDialogTitleIcon();
    /**
     * 设置普通消息
     * @param strMsg
     */
    void setMsgDialogMsg(String strMsg);
    /**
     * 设置Html格式消息
     * @param strMsg
     */
    void setMsgDialogHtmlMsg(String strMsg);
    /**
     * 设置左按钮文字
     * @param strLeft
     */
    void setMsgDialogNegativeBtnText(String strLeft);
    /**
     * 设置左边按钮文字颜色
     * @param color 文字颜色资源id值
     */
    void setMsgDialogLeftBtnColor(int color);
    /**
     * 设置右按钮文字
     * @param strRight
     */
    void setMsgDialogPositiveBtnText(String strRight);
    /**
     * 设置右边按钮文字颜色
     * @param color 文字颜色资源id值
     */
    void setMsgDialogRightBtnColor(int color);
    /**
     * 隐藏左边按钮进入单按钮模式
     * @param isHide
     */
    void doHideMsgDialogLeftBtn(boolean isHide);
    /**
     * 获取BUTTON文字
     * */
    String getMsgDialogBtnText(int type);
    /**
     * 设置弹框按钮监听事件处理，默认按钮处理为关闭弹框
     * @param listener 按钮监听处理类
     */
    void setMsgDialogListener(IDialogClickListener listener);

    /**
     * 设置消息弹框能不能返回键取消
     * @param isCancelable
     */
    void setMsgDialogCancelable(boolean isCancelable);

    /**
     * 显示消息弹框
     */
    void doShowMsgDialog();

    /**
     * 取消消息弹框
     */
    void doDismissMsgDialog();

    /**
     * 清楚消息弹框设置，恢复回默认消息弹框状态
     */
    void doCleanMsgDialog();

}
