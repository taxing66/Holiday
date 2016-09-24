package com.joiway.devin.holiday.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;

import com.joiway.devin.holiday.controller.abstracts.AbsHolder;
import com.joiway.devin.holiday.controller.abstracts.AbsHolderBean;
import com.joiway.devin.holiday.controller.holder.ListViewsHolder;

import org.xutils.x;

import java.util.List;

/**
 * Convert
 [kən'vɜːt]  英[kən'vɜːt]  美[kən'vɝt]

 n. 皈依者；改变宗教信仰者
 vt. 使转变；转换…；使…改变信仰
 vi. 转变，变换；皈依；改变信仰
 n. (Convert)人名；(法)孔韦尔

 网络释义:
 convert - 转换; 改变; 转变
 Audio Convert - 豪杰音频转换通; 豪杰音频通; 音频转换
 convert payment - 红包

 *供listView控制的适配器  根据不同实体，实体标签显示哪种holder.
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
    private int itemResourceId;
    private Context mContext;
    public ListViewAdapter(AbsHolderBean<T> absHolderBean){
        this.mContext = absHolderBean.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        mListData = absHolderBean.getListData();
        itemResourceId = absHolderBean.getItemResourceId();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AbsHolder mHolder;
       if (convertView==null){
           mHolder = new ListViewsHolder(mContext);
           convertView = mLayoutInflater.inflate(itemResourceId,parent,false);
           x.view().inject(mHolder,convertView);
           convertView.setTag(mHolder);
       }else {
           mHolder = (AbsHolder<T>) convertView.getTag();
       }
        mHolder.setData(mListData.get(position),position);
        return convertView;
    }
}
