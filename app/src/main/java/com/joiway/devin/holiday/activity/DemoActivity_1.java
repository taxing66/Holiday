package com.joiway.devin.holiday.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.controller.tools.net.NetManager;
import com.joiway.devin.holiday.controller.tools.net.RequestCallBack;
import com.joiway.devin.holiday.model.constant.NetUrl;
import com.joiway.devin.holiday.model.net.DataJson;
import com.joiway.devin.holiday.model.net.GetBonusWithdrawCount;
import com.joiway.devin.holiday.model.net.Header;
import com.joiway.devin.holiday.tools.LogManager;
import com.joiway.devin.holiday.view.HorizontalScrollViewEx;

import java.util.ArrayList;

import utils.MyUtils;

public class DemoActivity_1 extends Activity {
    private static final String TAG = "DemoActivity_1";

    private HorizontalScrollViewEx mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_1);
        Log.d(TAG, "onCreate");
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
        final int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight = MyUtils.getScreenMetrics(this).heightPixels;
         LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"DemoActivity_1","initView","screenW:"+screenWidth+":"+screenHeight);
        GetBonusWithdrawCount getBonusWithdrawCount = new GetBonusWithdrawCount();
        getBonusWithdrawCount.setKey_string_money_sum("44");
        getBonusWithdrawCount.setKey_string_total_money("434");
        getBonusWithdrawCount.setKey_string_withdraw_type("2");
        NetManager.httpPost(NetUrl.URL_STRING_GET_BONUS_WITHDRAW_COUNT,getBonusWithdrawCount,new RequestCallBack<String,String>(this){
            @Override
            protected void onStart() {
                super.onStart();
            }

            @Override
            protected void onSuccess(Header header, DataJson<String, String> data, String msg) {
                super.onSuccess(header, data, msg);
            }

            @Override
            protected void onFinally() {
                super.onFinally();
            }
        });

        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(
                    R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    /**
     * listview data init
     * @param layout
     */
    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Toast.makeText(DemoActivity_1.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }
}
