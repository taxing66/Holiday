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
import android.view.View;

/**
 * 自定义试图
 * Created by 德华 on 2016/6/30.
 */
public class MeasureView extends View{

    private static final int VIEW_SIZE_DIMENSION_DEFAULT = 600;

    private Canvas mCanvas;
    private Paint mPaint;
    private Rect mBounds;
    private String text;
    private Color textColor;
    private int textSize;

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasure(widthMeasureSpec), heightMeasure(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawText("hello world",0,VIEW_SIZE_DIMENSION_DEFAULT,0,0,paint);
        invalidate();
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
