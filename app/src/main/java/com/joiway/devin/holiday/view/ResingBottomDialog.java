package com.joiway.devin.holiday.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.joiway.devin.holiday.R;

/**
 * Created by 德华 on 2016/7/1.
 */
public class ResingBottomDialog extends Dialog {

    public static ResingBottomDialog getInstance(Context ctx){
        return new ResingBottomDialog(ctx, R.style.ResingBottomTheme);
    }
    public ResingBottomDialog(Context context) {
        super(context);
    }

    public ResingBottomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ResingBottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rising_bottom);
    }

    @Override
    public void show() {
        super.show();
    }
}
