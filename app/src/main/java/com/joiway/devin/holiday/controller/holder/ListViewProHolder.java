package com.joiway.devin.holiday.controller.holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.controller.abstracts.AbsHolder;
import com.joiway.devin.holiday.controller.adapter.ListViewAdapter;
import com.joiway.devin.holiday.model.ListItemStringBean;
import com.joiway.devin.holiday.tools.ToastManager;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import jw.cn.com.jwutils.controller.utils.LogUtils;

/**
 * 有删除和复制功能的holder
 */

public class ListViewProHolder<T> extends AbsHolder<ListItemStringBean> {
    /**
     * 该holder所能显示的视图
     */
    public static final int PUBLIC_INT_RESOURCE_ID = R.layout.view_list_view_item_second;
    public ListViewProHolder(Context context, List<T> listData, ListViewAdapter<T> tListViewAdapter){
        this.mContext = context;
        this.mListData = listData;
        this.mListViewAdapter = tListViewAdapter;
    }

    private List<T> mListData;
    private ListViewAdapter<T> mListViewAdapter;
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

    @Override
    public int getResourceId(int holderTag) {
        return PUBLIC_INT_RESOURCE_ID;
    }

    @Event(value = {R.id.tv_content,R.id.btn_delete})
    private void clickBtn(View v) {
        switch (v.getId()) {
            case R.id.tv_content:
                ToastManager.showToastShort(mContext, "hello" + mPosition);
                break;
            case R.id.btn_delete:
                if (mListData.remove(mListItemStringBean)){
                    mListViewAdapter.notifyDataSetChanged();
                    ToastManager.showToastShort(mContext, "delete success");
                }
                break;
        }
    }
    @Event(value = {R.id.tv_content},type = View.OnLongClickListener.class)
    private  boolean clickBtnLong(View v){
        switch (v.getId()){
            case R.id.tv_content:


                break;
        }
        return true;
    }

}
