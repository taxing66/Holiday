package com.joiway.devin.holiday;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joiway.devin.holiday.activity.AbsActivity;

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
    }
}
