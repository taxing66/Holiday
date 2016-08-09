package com.joiway.devin.holiday.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * 自定义试图
 * Created by 德华 on 2016/6/30.
 *
 *
 *
 */
public class MeasureView extends View{

    private static final int VIEW_SIZE_DIMENSION_DEFAULT = 600;
    private static final String TAG = "devin";

    private Canvas mCanvas;
    private Paint mPaint;
    private Rect mBounds;
    private String text;
    private Color textColor;
    private int textSize;
    private Context cxt;

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cxt = context;
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(TAG, "MeasureView: ");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasure(widthMeasureSpec), heightMeasure(heightMeasureSpec));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.d(TAG, "onTouchEvent: event:"+event.getX()+":"+event.getY());
        Log.d(TAG, "onTouchEvent: event:"+event.getRawX()+":"+event.getRawY());
        Log.d(TAG, "onTouchEvent: touchslop:"+ ViewConfiguration.get(cxt).getScaledTouchSlop());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_BUTTON_PRESS:
                break;
            case MotionEvent.ACTION_BUTTON_RELEASE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_HOVER_ENTER:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: "+this.getHeight()+":"+this.getWidth());
        Log.d(TAG, "onDraw: "+this.getTop()+":"+this.getBottom());
        Log.d(TAG, "onDraw: "+this.getLeft()+":"+this.getRight())  ;
        Log.d(TAG, "onDraw: "+this.getX()+":"+this.getY());
        Log.d(TAG, "onDraw: "+this.getTranslationX()+this.getTranslationY());
    }

    /***
     * measure the height
     * @param heightMeasureSpec
     * @return
     */
    private int heightMeasure(int heightMeasureSpec) {
        int measureMode = MeasureSpec.getMode(heightMeasureSpec);
        int measureSize = MeasureSpec.getSize(heightMeasureSpec);
        if (measureMode==MeasureSpec.EXACTLY){
            return measureSize;
        }
        return Math.min(VIEW_SIZE_DIMENSION_DEFAULT,measureSize);
    }

    /**
     * measure the with
     * @param widthMeasureSpec
     * @return
     */
    private int widthMeasure(int widthMeasureSpec) {
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureSize = MeasureSpec.getSize(widthMeasureSpec);
        if (measureMode==MeasureSpec.EXACTLY){
            return measureSize;
        }
        return Math.min(VIEW_SIZE_DIMENSION_DEFAULT,measureSize);
    }
}
