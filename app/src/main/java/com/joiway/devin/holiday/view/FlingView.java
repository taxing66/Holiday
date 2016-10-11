package com.joiway.devin.holiday.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import jw.cn.com.jwutils.controller.utils.LogUtils;

/**
 * 自定义视图
 * @author 陈德华
 * @create 2016-09-28
 * @editer 陈德华
 * @date 2016-09-28
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class FlingView extends View {
    public FlingView(Context context) {
        super(context);
    }

    public FlingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        VelocityTracker v = VelocityTracker.obtain();
        v.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                v.computeCurrentVelocity(1000);
                LogUtils.logDebug(LogUtils.DEVELOPER_DEVIN,"FlingView","onTouchEvent",v.getXVelocity()+":"+v.getYVelocity());
                break;
            case MotionEvent.ACTION_UP:
                v.clear();
                v.recycle();
                break;
        }
        return super.onTouchEvent(event);
    }

}
