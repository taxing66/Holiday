package com.joiway.devin.holiday.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.controller.Interface.IDialogClickListener;
import com.joiway.devin.holiday.controller.Interface.IMessageDialog;
import com.joiway.devin.holiday.model.DialogBuilderBean;

/**
 * 消息弹框
 * Created by Jianan on 2015/7/17.
 * 加了隐藏头部图像方法，devin; 2015.7.18
 */
public class MessageDialog extends Dialog implements IMessageDialog {
    /**
     * 状态码-左边按钮
     */
    public static final int DIALOG_BUTTON_LEFT = 0;
    /**
     * 状态码-右边按钮
     */
    public static final int DIALOG_BUTTON_RIGHT = 1;
    private static final int TEXT_HTML = 0;//Html文字
    private static final int TEXT = 1;//普通文字
    private static final int HIDE_VIEW = -1;//隐藏控件

    private LinearLayout llTitleBar; //对话框头部
    private ImageView ivTitleIcon;//对话框头部图标
    private TextView tvTitle; //对话框头部文本
    private View vTitleLine;//对话框头部线
    private TextView tvMsg;//对话框中间文本信息
    private View vBottomLine; //对话框底部中间线
    private Button btnNegative;//对话框左边按钮
    private Button btnRight;//对话框右边按钮

    private int mTitleIconResourceID = HIDE_VIEW;//标题栏图标资源ID
    private String mStrTitle;//标题文字
    private String mStrMsg;//消息内容
    private String mStrNegative;//左边按钮文字
    private String mStrBtnPositive;//右边按钮文字
    private int mBtnLeftColor;//左边按钮文字颜色
    private int mBtnRightColor;//右边按钮文字颜色
    private boolean mIsCreated = false;//是否已经调用onCreate方法初始化界面
    private int mMsgType = TEXT;//消息文本类型，默认为普通文本
    private int mGravity;//内容文本对齐方式
    private boolean mIsSignButton = false;//是否单按钮模式，默认为双按钮
    private IDialogClickListener mListener;

    public static MessageDialog newInstance(Context act) {
        return new MessageDialog(act, R.style.dialog);
    }


    private MessageDialog(Context context, int theme) {
        super(context, theme);
        mTitleIconResourceID = HIDE_VIEW;
        mStrTitle = null;
        mMsgType = TEXT;
        mIsSignButton = false;
        mStrNegative = context.getString(R.string.public_text_cancel);
        mStrBtnPositive = context.getString(R.string.public_text_ok);
        mBtnLeftColor = R.color.main_orange_fe7418;
        mBtnRightColor = R.color.main_orange_fe7418;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    /**
     * 设置标题图标
     *
     * @param resourceID
     */
    @Override
    public void setMsgDialogTitleIcon(int resourceID) {
        mTitleIconResourceID = resourceID;
        refreshTitleBar();
    }

    /**
     * 设置标题
     *
     * @param strTitle
     */
    @Override
    public void setMsgDialogTitle(String strTitle) {
        this.mStrTitle = strTitle;
        refreshTitleBar();
    }

    /**
     * 隐藏标题栏
     */
    @Override
    public void doHideMsgDialogTitleBar() {
        mTitleIconResourceID = HIDE_VIEW;
        mStrTitle = null;
        refreshTitleBar();
    }

    /**
     * 隐藏标题图标
     */
    @Override
    public void doHideMsgDialogTitleIcon() {
        mTitleIconResourceID = HIDE_VIEW;
        refreshTitleBar();
    }

    /**
     * 设置普通消息
     *
     * @param strMsg
     */
    @Override
    public void setMsgDialogMsg(String strMsg) {
        this.mMsgType = TEXT;
        this.mStrMsg = strMsg;
        refreshContent();
    }


    /**
     * 设置Html格式消息
     *
     * @param strMsg
     */
    @Override
    public void setMsgDialogHtmlMsg(String strMsg) {
        this.mMsgType = TEXT_HTML;
        this.mStrMsg = strMsg;
        refreshContent();
    }

    public void setMsgDialogGravity(int gravity) {
        this.mGravity = gravity;
        refreshGravity();
    }

    /**
     * 设置左按钮文字
     *
     * @param strNegative
     */
    @Override
    public void setMsgDialogNegativeBtnText(String strNegative) {
        this.mStrNegative = strNegative;
        refreshButtonBar();
    }

    /**
     * 设置左边按钮文字颜色
     *
     * @param color 文字颜色资源id值
     */
    @Override
    public void setMsgDialogLeftBtnColor(int color) {
        this.mBtnLeftColor = color;
        refreshButtonBar();
    }

    /**
     * 设置右按钮文字
     *
     * @param strPositive
     */
    @Override
    public void setMsgDialogPositiveBtnText(String strPositive) {
        this.mStrBtnPositive = strPositive;
        refreshButtonBar();
    }

    /**
     * 设置右边按钮文字颜色
     *
     * @param color 文字颜色资源id值
     */
    @Override
    public void setMsgDialogRightBtnColor(int color) {
        this.mBtnRightColor = color;
        refreshButtonBar();
    }

    /**
     * 隐藏左边按钮进入单按钮模式
     *
     * @param isHide
     */
    @Override
    public void doHideMsgDialogLeftBtn(boolean isHide) {
        this.mIsSignButton = isHide;
        refreshButtonBar();
    }

    /**
     * 获取BUTTON文字
     */
    @Override
    public String getMsgDialogBtnText(int type) {
        switch (type) {
            case DIALOG_BUTTON_LEFT:
                return mStrNegative;
            case DIALOG_BUTTON_RIGHT:
                return mStrBtnPositive;
            default:
                return null;
        }
    }

    /**
     * 设置弹框按钮监听事件处理，默认按钮处理为关闭弹框
     *
     * @param listener 按钮监听处理类
     */
    @Override
    public void setMsgDialogListener(IDialogClickListener listener) {
        mListener = listener;
    }

    @Override
    public void setMsgDialogCancelable(boolean isCancelable) {
        setCancelable(isCancelable);
    }

    @Override
    public void doShowMsgDialog() {
        show();
    }

    @Override
    public void doDismissMsgDialog() {
        dismiss();
    }

    @Override
    public void doCleanMsgDialog() {
        mTitleIconResourceID = HIDE_VIEW;
        mStrTitle = null;
        mMsgType = TEXT;
        mIsSignButton = false;
        mStrNegative = null;
        mStrBtnPositive = null;
        mBtnLeftColor = R.color.main_orange_fe7418;
        mBtnRightColor = R.color.main_orange_fe7418;
        mListener = null;
        mGravity = 0;

        refreshTitleBar();
        refreshContent();
        refreshButtonBar();
        refreshGravity();
        setCancelable(true);
    }

    @Override
    public Dialog builder(DialogBuilderBean dialogBuilderBean, IDialogClickListener listener) {
        if (dialogBuilderBean.isHideTitle()) {
            doHideMsgDialogTitleBar();
        }
        if (dialogBuilderBean.isHIdeNegativeBtn()) {
            doHideMsgDialogLeftBtn(true);
        }
        if (!TextUtils.isEmpty(dialogBuilderBean.getContent())) {
            setMsgDialogMsg(dialogBuilderBean.getContent());
        }
        if (!TextUtils.isEmpty(dialogBuilderBean.getNegativeBtnText())) {
            setMsgDialogNegativeBtnText(dialogBuilderBean.getNegativeBtnText());
        }
        if (!TextUtils.isEmpty(dialogBuilderBean.getPositiveBtnText())) {
            setMsgDialogPositiveBtnText(dialogBuilderBean.getPositiveBtnText());
        }
        if (listener!=null){
            setMsgDialogListener(listener);
        }
        setMsgDialogCancelable(dialogBuilderBean.isCancelable());
        return this;
    }

    //初始化
    private void init() {
        setContentView(R.layout.dialog_message);
        llTitleBar = (LinearLayout) findViewById(R.id.ic_title_bar);
        ivTitleIcon = (ImageView) findViewById(R.id.iv_title_icon);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        vTitleLine = findViewById(R.id.v_title_line);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        vBottomLine = findViewById(R.id.v_button_line);
        btnNegative = (Button) findViewById(R.id.btn_left);
        btnRight = (Button) findViewById(R.id.btn_right);

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickNegativeBtn(MessageDialog.this);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickPositiveBtn(MessageDialog.this);
            }
        });

        mIsCreated = true;
        refreshTitleBar();
        refreshContent();
        refreshGravity();
        refreshButtonBar();
    }

    //刷新标题栏
    private void refreshTitleBar() {
        if (mIsCreated) {
            if (TextUtils.isEmpty(mStrTitle) && mTitleIconResourceID == HIDE_VIEW) {
                llTitleBar.setVisibility(View.GONE);
                vTitleLine.setVisibility(View.GONE);
            } else {
                if (!TextUtils.isEmpty(mStrTitle)) {
                    tvTitle.setText(mStrTitle);
                    tvTitle.setVisibility(View.VISIBLE);
                } else {
                    tvTitle.setVisibility(View.GONE);
                }
                if (mTitleIconResourceID != HIDE_VIEW) {
                    ivTitleIcon.setImageResource(mTitleIconResourceID);
                    ivTitleIcon.setVisibility(View.VISIBLE);
                } else {
                    ivTitleIcon.setVisibility(View.GONE);
                }
                llTitleBar.setVisibility(View.VISIBLE);
                vTitleLine.setVisibility(View.VISIBLE);
            }
        }
    }

    //刷新消息内容
    private void refreshContent() {
        if (mIsCreated) {
            if (!TextUtils.isEmpty(mStrMsg)) {
                switch (mMsgType) {
                    case TEXT:
                        tvMsg.setText(mStrMsg);
                        break;
                    case TEXT_HTML:
                        tvMsg.setText(Html.fromHtml(mStrMsg));
                        break;
                    default:
                        break;
                }
                tvMsg.setVisibility(View.VISIBLE);
            } else {
                tvMsg.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void refreshGravity() {
        if (mIsCreated) {
            if (mGravity != 0) {
                tvMsg.setGravity(mGravity);
            }
        }
    }

    //刷新按钮
    private void refreshButtonBar() {
        if (mIsCreated) {
            if (mIsSignButton) {
                vBottomLine.setVisibility(View.GONE);
                btnNegative.setVisibility(View.GONE);
            } else {
                vBottomLine.setVisibility(View.VISIBLE);
                btnNegative.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(mStrNegative)) {
                    btnNegative.setText(R.string.public_text_cancel);
                } else {
                    btnNegative.setText(mStrNegative);
                }
            }
            if (TextUtils.isEmpty(mStrBtnPositive)) {
                btnRight.setText(R.string.public_text_ok);
            } else {
                btnRight.setText(mStrBtnPositive);
            }
            btnNegative.setTextColor(getContext().getResources().getColor(mBtnLeftColor));
            btnRight.setTextColor(getContext().getResources().getColor(mBtnRightColor));
        }
    }


}
