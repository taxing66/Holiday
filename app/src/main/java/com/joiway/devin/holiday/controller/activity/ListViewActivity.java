package com.joiway.devin.holiday.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.activity.AbsActivity;
import com.joiway.devin.holiday.controller.abstracts.AbsHolderBean;
import com.joiway.devin.holiday.controller.adapter.ListViewAdapter;
import com.joiway.devin.holiday.model.ListItemStringBean;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * display all kinds of listView;
 *
 * @author 陈德华
 * @create 2016-09-24
 * @editer 陈德华
 * @date 2016-09-24
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
@ContentView(R.layout.activity_list_view)
public class ListViewActivity extends AbsActivity {
    @ViewInject(R.id.lv_list_view_display)
    private ListView lvDisplay;
    private ListViewAdapter<ListItemStringBean> mAbsHolderBeanListViewAdapter;
    private AbsHolderBean<ListItemStringBean> mListItemStringBeanAbsHolderBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        mListItemStringBeanAbsHolderBean = new AbsHolderBean<>();
        mListItemStringBeanAbsHolderBean.setContext(this);
        mListItemStringBeanAbsHolderBean.setItemResourceId(R.layout.view_list_view_item_single);
        mListItemStringBeanAbsHolderBean.setListData(getListData());
        mAbsHolderBeanListViewAdapter = new ListViewAdapter(mListItemStringBeanAbsHolderBean);
        lvDisplay.setAdapter(mAbsHolderBeanListViewAdapter);
    }

    private List<ListItemStringBean> getListData() {
        List<ListItemStringBean> listItemStringBeenList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            listItemStringBeenList.add(new ListItemStringBean("default style:this item" + i));
            listItemStringBeenList.add(new ListItemStringBean("folder style:this item" + i));
        }
        return listItemStringBeenList;
    }
}
