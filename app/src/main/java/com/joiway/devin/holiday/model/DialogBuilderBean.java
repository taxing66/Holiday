package com.joiway.devin.holiday.model;

/**
 *构建Dialog 的实体
 * @author 陈德华
 * @create 2016-07-02
 * @editer 陈德华
 * @date 2016-07-02
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class DialogBuilderBean {
    public static final int VALUE_INT_DIALOG_TYPE_FOR_CALENDAR =1;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_DATE_CHOOSE =2;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_IMAGE =3;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_JOB_TYPE=4;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_LIST_MSG=5;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_LOADING_DATA =6;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_MASK = 8;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_MESSAGE=9;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_PASSWORD=10;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_RED_PACKET=11;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_RESING_FROM_BOTTOM=12;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_SCAN_CODE=13;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_SIGN_DATE_CHOOSE=14;
    public static final int VALUE_INT_DIALOG_TYPE_FOR_TIME_CHOOSE=15;
    private String title;
    private String content;
    private String positiveBtnText;
    private String negativeBtnText;
    private String deleteBtnText;
    private int type ;
    private boolean isHideTitle;
    private boolean isHidePositiveBtn;
    private boolean isHIdeNegativeBtn;
    private boolean isCancelable=true;
    /**
     * 是否清除dialog data
     */
    private boolean isCleanMsg;


    public boolean isCleanMsg() {
        return isCleanMsg;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    public void setCleanMsg(boolean cleanMsg) {
        isCleanMsg = cleanMsg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPositiveBtnText() {
        return positiveBtnText;
    }

    public void setPositiveBtnText(String positiveBtnText) {
        this.positiveBtnText = positiveBtnText;
    }

    public String getNegativeBtnText() {
        return negativeBtnText;
    }

    public void setNegativeBtnText(String negativeBtnText) {
        this.negativeBtnText = negativeBtnText;
    }

    public String getDeleteBtnText() {
        return deleteBtnText;
    }

    public void setDeleteBtnText(String deleteBtnText) {
        this.deleteBtnText = deleteBtnText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isHideTitle() {
        return isHideTitle;
    }

    public void setHideTitle(boolean hideTitle) {
        isHideTitle = hideTitle;
    }

    public boolean isHidePositiveBtn() {
        return isHidePositiveBtn;
    }

    public void setHidePositiveBtn(boolean hidePositiveBtn) {
        isHidePositiveBtn = hidePositiveBtn;
    }

    public boolean isHIdeNegativeBtn() {
        return isHIdeNegativeBtn;
    }

    public void setHIdeNegativeBtn(boolean HIdeNegativeBtn) {
        isHIdeNegativeBtn = HIdeNegativeBtn;
    }
}
