package com.joiway.devin.holiday.activity;

import android.support.v4.app.Fragment;

import com.joiway.devin.holiday.fragment.CrimeListFragment;

/**
 * Created by 德华 on 2016/8/2.
 */
public class CrimeListActivity extends AbsFragmentActivity {
    @Override
    public Fragment createFragment() {

        return new CrimeListFragment();
    }
}
