package com.joiway.devin.holiday.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.activity.AbsActivity;
import com.joiway.devin.holiday.controller.abstracts.AbsAdapterConfigBean;
import com.joiway.devin.holiday.controller.adapter.ListViewAdapter;
import com.joiway.devin.holiday.model.ListItemStringBean;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
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
    private AbsAdapterConfigBean<ListItemStringBean> mListItemStringBeanAbsAdapterConfigBean;
    private  List<ListItemStringBean> listItemStringBeenList ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        listItemStringBeenList = new ArrayList<>();
        mListItemStringBeanAbsAdapterConfigBean = new AbsAdapterConfigBean<>();
        mListItemStringBeanAbsAdapterConfigBean.setContext(this);
        mListItemStringBeanAbsAdapterConfigBean.setListData(getListData());
        mAbsHolderBeanListViewAdapter = new ListViewAdapter(mListItemStringBeanAbsAdapterConfigBean);
        lvDisplay.setAdapter(mAbsHolderBeanListViewAdapter);
    }
    @Event(value = {R.id.btn_add_data})
    private void onClickBtn(View v){
        switch (v.getId()){
            case R.id.btn_add_data:
                listItemStringBeenList.add(new ListItemStringBean("default style:this item" + System.currentTimeMillis()));
                listItemStringBeenList.add(new ListItemStringBean("folder style:this item" + System.currentTimeMillis()));
                mAbsHolderBeanListViewAdapter.notifyDataSetChanged();
                break;
        }
    }

    private List<ListItemStringBean> getListData() {
        for (int i = 0; i < 50; i++) {
            ListItemStringBean listItemStringBean = new ListItemStringBean("default style:this item" + i);
            if (i%2==0){
                listItemStringBean.setHolderTag(ListItemStringBean.PUBLIC_LIST_VIEW_BUTTON);
            }
            listItemStringBeenList.add(listItemStringBean);
        }
        return listItemStringBeenList;
    }
}
