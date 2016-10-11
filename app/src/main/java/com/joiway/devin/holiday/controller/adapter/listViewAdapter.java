package com.joiway.devin.holiday.controller.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.joiway.devin.holiday.controller.abstracts.AbsHolder;
import com.joiway.devin.holiday.controller.abstracts.AbsAdapterConfigBean;
import com.joiway.devin.holiday.controller.holder.ListViewProHolder;
import com.joiway.devin.holiday.controller.holder.ListViewsHolder;
import com.joiway.devin.holiday.model.ListItemStringBean;

import org.xutils.x;

import java.util.List;

/**
 * Convert
 * [kən'vɜːt]  英[kən'vɜːt]  美[kən'vɝt]
 * <p>
 * n. 皈依者；改变宗教信仰者
 * vt. 使转变；转换…；使…改变信仰
 * vi. 转变，变换；皈依；改变信仰
 * n. (Convert)人名；(法)孔韦尔
 * <p>
 * 网络释义:
 * convert - 转换; 改变; 转变
 * Audio Convert - 豪杰音频转换通; 豪杰音频通; 音频转换
 * convert payment - 红包
 * <p>
 * 供listView控制的适配器  根据不同实体，实体标签显示哪种holder.
 *
 * @author 陈德华
 * @create 2016-09-24
 * @editer 陈德华
 * @date 2016-09-24
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class ListViewAdapter<T> extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<T> mListData;
    private Context mContext;

    public ListViewAdapter(AbsAdapterConfigBean<T> a) {
        this.mContext = a.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        mListData = a.getListData();
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbsHolder mHolder;
        if (convertView == null) {
            if (mListData.get(position) instanceof ListItemStringBean) {
                final int holderTag = ((ListItemStringBean) mListData.get(position)).getHolderTag();
                mHolder = getInstanceHolder(holderTag);
                convertView = mLayoutInflater.inflate(mHolder.getResourceId(holderTag), parent, false);
                x.view().inject(mHolder, convertView);
                convertView.setTag(mHolder);
            } else {
                mHolder = null;
            }
        } else {
            mHolder = (AbsHolder<T>) convertView.getTag();
        }
        if (mHolder != null) {
            mHolder.setData(mListData.get(position), position);
        }
        return convertView;
    }

    /**
     * 根据多态，获取真正实例化的holder
     * @param holderTag
     * @return
     */
    @NonNull
    private AbsHolder getInstanceHolder(int holderTag) {
        AbsHolder mHolder;
        switch (holderTag) {
            case ListItemStringBean.PUBLIC_LIST_VIEW_BUTTON:
                mHolder = new ListViewProHolder<>(mContext,mListData,this);
                break;
            default:
                mHolder = new ListViewsHolder(mContext,mListData,this);
                break;
        }
        return mHolder;
    }
}
