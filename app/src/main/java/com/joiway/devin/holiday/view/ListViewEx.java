package com.joiway.devin.holiday.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
/**
 *测试滑动冲动，利用内部拦截方式处理滑动冲动；
 * @author 陈德华
 * @create 2016-07-09
 * @editer 陈德华
 * @date 2016-07-09
 * @docVersion 适用于代码规范v1.0.0版本
 * http://joiway.oicp.net:8090/pages/viewpage.action?pageId=5669071
 */
public class ListViewEx extends ListView {
    private static final String TAG = "ListViewEx";

    private HorizontalScrollViewEx2 mHorizontalScrollViewEx2;

    // 分别记录上次滑动的坐标
    private int mLastX = 0;
    private int mLastY = 0;

    public ListViewEx(Context context) {
        super(context);
    }

    public ListViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewEx(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHorizontalScrollViewEx2(
            HorizontalScrollViewEx2 horizontalScrollViewEx2) {
        mHorizontalScrollViewEx2 = horizontalScrollViewEx2;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN: {
            //父视图禁止截止事件
            mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true);
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            int deltaX = x - mLastX;
            int deltaY = y - mLastY;
            Log.d(TAG, "dx:" + deltaX + " dy:" + deltaY);
            if (Math.abs(deltaX) > Math.abs(deltaY)) {
                //请求让父视图拦截事件，允许父视图触发 onInterceptTouchEvent;
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false);
            }
            break;
        }
        case MotionEvent.ACTION_UP: {
            break;
        }
        default:
            break;
        }

        mLastX = x;
        mLastY = y;
        return super.dispatchTouchEvent(event);
    }

}
