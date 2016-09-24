package com.joiway.devin.holiday.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.joiway.devin.holiday.activity.DemoActivity_1;
import com.joiway.devin.holiday.activity.XUtilsActivity;
import com.joiway.devin.holiday.controller.tools.system.GlobalMethod;
import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.model.GrandParents;
import com.joiway.devin.holiday.model.Son;
import com.joiway.devin.holiday.tools.LogManager;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/7/9.
 */
public class DemoActivity_2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }


    public void onButtonClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button1:
                GrandParents grandParents = new Son();
                grandParents.grow(3);
                Field[] filds = GlobalMethod.getAllField(Son.class);
                for (int i = 0; i < filds.length; i++) {
                    try {
                        LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "DemoActivity_2", "onButtonClick", filds[i].getName());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                break;
            case R.id.button2:

                intent = new Intent(this, XUtilsActivity.class);
                startActivity(intent);

                break;
            case R.id.button3:

                intent = new Intent(this, DemoActivity_2.class);
                startActivity(intent);

                break;
            case R.id.btn_enter_list_view:
                intent = new Intent(this,ListViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
