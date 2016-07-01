package com.joiway.devin.holiday.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.joiway.devin.holiday.BuildConfig;
import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.tools.LogManager;
import com.joiway.devin.holiday.view.ResingBottomDialog;

public class MainActivity extends AbsActivity {
    private TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "hello ", Toast.LENGTH_SHORT).show();
        initTitleBar();
         tvTitle = (TextView) findViewById(R.id.tv_title);
         tvTitle.setText(R.string.happy_everyday);
          LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate","test_debug");
        ResingBottomDialog dialog = ResingBottomDialog.getInstance(this);
        dialog.show();
    }
}
