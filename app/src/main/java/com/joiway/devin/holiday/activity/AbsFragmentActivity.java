package com.joiway.devin.holiday.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.joiway.devin.holiday.R;

/**
 * Created by 德华 on 2016/8/2.
 */
public abstract class AbsFragmentActivity extends FragmentActivity {
    public abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_framgent);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.activity_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.activity_container, fragment).commit();
        }
    }
}
