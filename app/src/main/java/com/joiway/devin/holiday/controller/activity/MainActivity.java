package com.joiway.devin.holiday.controller.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joiway.devin.holiday.R;
import com.joiway.devin.holiday.controller.tools.system.GlobalMethod;
import com.joiway.lib.base.cryptolib.CryptoJavaLib;
import com.joiway.lib.base.cryptolib.CryptoNativeLib;
import com.joiway.lib.utils.NumberUtils;
import com.joiway.devin.holiday.controller.tools.data.ReflectionUtils;
import com.joiway.devin.holiday.model.Son;
import com.joiway.devin.holiday.controller.tools.system.LogManager;
import com.joiway.devin.holiday.view.CustomDrawable;

public class MainActivity extends AbsActivity {
    private static final int PRIVATE_TEXT_PLUS_LEVEL = 1;
    private TextView tvTitle;
    private ImageView ivClip;
    private int mLevel;
    private Button btnEnter;
    private ImageView btnStatus;
    private Intent intent;
    private ClipDrawable mDrawable;
    private int level;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PRIVATE_TEXT_PLUS_LEVEL:
                    mDrawable.setLevel(+50);
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
        LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "MainActivity", "onCreate", "color:" + NumberUtils.transformColor(R.color.reveal_color, R.color.colorPrimary, 40)

        );
        String test = "hello world";
        String test1 = "hello world";
//        for (int i = 0; i < 1000; i++) {
//            test+="hello world";
//            test1+="hello world";
//        }
        try {
            LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate",CryptoJavaLib.encryptTripleDes(test1));
            test1  = CryptoJavaLib.encryptTripleDes(test1,CryptoJavaLib.DESEDEKey);
             LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate",CryptoJavaLib.decryptTripleDes(test1));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate_1", CryptoJavaLib.encryptAes(test));
            test = CryptoJavaLib.encryptAes(test);
             LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate_1",CryptoJavaLib.decryptAes(test));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate", CryptoNativeLib.md5("hello world".getBytes()));
//          LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate", CryptoNativeLib.hexEncode(CryptoNativeLib.md5("hello world".getBytes()).getBytes()));
//          LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate", CryptoNativeLib.hexDecode(CryptoNativeLib.hexEncode(CryptoNativeLib.md5("hello world".getBytes()).getBytes())).toString());
//        for (int i = 0; i < GlobalMethod.randomBytes(16).length; i++) {
//            LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onCreate",":"+GlobalMethod.randomBytes(16)[i]);
//        }
//        LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "MainActivity", "onCreate", ReflectionUtils.findMethod(Son.class, "grow", null)
//                .getClass().toString());
        initTitleBar();
        mLevel = 0;
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(R.string.happy_everyday);
        btnEnter = (Button) findViewById(R.id.btn_enter);
        btnStatus = (ImageView) findViewById(R.id.btn_status);
        mDrawable = (ClipDrawable) btnStatus.getDrawable();
        mDrawable.setLevel(8000);
        level = 10000;
        CustomDrawable customDrawable = new CustomDrawable(Color.parseColor("#0ac39e"));
        btnStatus.setImageDrawable(customDrawable);
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawable = (ClipDrawable) btnStatus.getDrawable();
                level -= 1000;
                mDrawable.setLevel(Math.abs(level));
            }
        });
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, DemoActivity_2.class);
                startActivity(intent);
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
                valueAnimator.setDuration(3000);
                valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
                valueAnimator.setRepeatMode(ValueAnimator.RESTART);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
//                         LogManager.logDebug(LogManager.DEVELOPER_DEVIN,"MainActivity","onAnimationUpdate",animation.getAnimatedValue()+"");
                    }
                });
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                valueAnimator.start();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(btnEnter, "alpha", 1f, 0f);

            }
        });
        ivClip = (ImageView) findViewById(R.id.iv_clip);
        mDrawable = (ClipDrawable) ivClip.getDrawable();
//        mDrawable.setLevel(mDrawable.getLevel()+1000);
        LogManager.logDebug(LogManager.DEVELOPER_DEVIN, "MainActivity", "onCreate", "test_debug");
//        mHandler.sendEmptyMessage(PRIVATE_TEXT_PLUS_LEVEL);
    }
}
