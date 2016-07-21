package com.joiway.devin.holiday.activity;

import android.content.Intent;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joiway.devin.holiday.BuildConfig;
import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.tools.LogManager;

public class MainActivity extends AbsActivity {
    private static final int PRIVATE_TEXT_PLUS_LEVEL = 1;
    private TextView tvTitle;
    private ImageView ivClip;
    private int mLevel;
    private Button btnEnter;
    private Intent intent;
    private ClipDrawable mDrawable;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PRIVATE_TEXT_PLUS_LEVEL:
                    mDrawable.setLevel( + 50);
                    break;
            }
            if (mLevel < 1000) {
                LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "MainActivity", "handleMessage", "level:" + mLevel);
                sendEmptyMessageDelayed(PRIVATE_TEXT_PLUS_LEVEL, 300);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "hello ", Toast.LENGTH_SHORT).show();
        initTitleBar();
        mLevel = 0;
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(R.string.happy_everyday);
        btnEnter = (Button) findViewById(R.id.btn_enter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this,DemoActivity_1.class);
                startActivity(intent);
            }
        });
        ivClip = (ImageView) findViewById(R.id.iv_clip);
        mDrawable = (ClipDrawable) ivClip.getDrawable();
//        mDrawable.setLevel(mDrawable.getLevel()+1000);
        LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "MainActivity", "onCreate", "test_debug");
//        mHandler.sendEmptyMessage(PRIVATE_TEXT_PLUS_LEVEL);
    }
}
