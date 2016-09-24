package com.joiway.devin.holiday.controller.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.controller.abstracts.AbsHolder;
import com.joiway.devin.holiday.controller.activity.ListViewActivity;
import com.joiway.devin.holiday.model.ListItemStringBean;
import com.joiway.devin.holiday.tools.ToastManager;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import jw.cn.com.jwutils.controller.utils.LogUtils;

/**
 * Created by Administrator on 2016/9/24.
 */

public class ListViewsHolder extends AbsHolder<ListItemStringBean> {

    public ListViewsHolder(Context context){
        this.mContext = context;
    }
    private Context mContext;
    @ViewInject(R.id.tv_content)
    private TextView tvContent;
    private ListItemStringBean mListItemStringBean;
    private int mPosition;

    @Override
    public void setData(ListItemStringBean listItemStringBean, int position) {
        tvContent.setText(listItemStringBean.getName());
        this.mListItemStringBean = listItemStringBean;
        this.mPosition = position;
        LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"ListViewActivity","setData", JSON.toJSONString(listItemStringBean));
    }

    @Event(value = {R.id.tv_content})
    private void clickBtn(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                ToastManager.showToastShort(mContext, "hello" + mPosition);
                break;
        }
    }

}
