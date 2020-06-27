package com.yxm.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yxm.customview.R;
import com.yxm.customview.utils.Utils;

/**
 * @Author yxm
 * @Email yxmbest@163.com
 * 2020/6/20 21:33
 * @Description 圆形百分比进度条
 */
public class CircleProgressView extends View {

    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private Paint mProgressPercentPaint;
    private int mProgressTextSize = 20;
    private int mBorderWidth = 5;
    private int mOuterColor = Color.BLACK;
    private int mInnerColor = Color.RED;
    private int mProgressPercentColor = Color.BLUE;

    private int currentValue = 0;
    private int maxValue = 100;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attributes) {
        this(context, attributes, 0);
    }

    public CircleProgressView(Context context, AttributeSet attributes, int defStyle) {
        super(context, attributes, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attributes, R.styleable.CircleProgressView);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.CircleProgressView_circleProgressBorderWidth, Utils.sp2px(this,mBorderWidth));
        mOuterColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressOuterColor, mOuterColor);
        mInnerColor = typedArray.getColor(R.styleable.CircleProgressView_circleProgressInnerColor, mInnerColor);
        mProgressPercentColor = typedArray.getColor(R.styleable.CircleProgressView_progressPercentColor, mProgressPercentColor);
        mProgressTextSize = (int) typedArray.getDimension(R.styleable.CircleProgressView_progressTextSize,Utils.sp2px(this,mProgressTextSize));
        typedArray.recycle();
        mOuterPaint = getPaintByColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeWidth(mBorderWidth);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerPaint = getPaintByColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);

        mProgressPercentPaint = getPaintByColor(mProgressPercentColor);
        mProgressPercentPaint.setTextSize(Utils.sp2px(this,mProgressTextSize));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //宽高不一致时，取最小值，使View始终是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        //1.画底部的整圆
        int center = width / 2;
        int radius = center - mBorderWidth / 2;
        @SuppressWarnings("DrawAllocation")
        RectF rectF = new RectF(center - radius, center - radius,
                center + radius,center + radius);
        canvas.drawCircle(center,center,radius,mOuterPaint);
        //2.根据百分比画进度的圆弧
        if(maxValue == 0){
            return;
        }
        float ratio = (float) currentValue / maxValue;
        canvas.drawArc(rectF,0,ratio * 360,false,mInnerPaint);

        //3.画百分比Text
        String text = currentValue + "%";
        Rect bounds = new Rect();
        mProgressPercentPaint.getTextBounds(text,0,text.length(),bounds);
        int dx = (width - bounds.width()) / 2 + getPaddingLeft();
        Paint.FontMetricsInt fm = mProgressPercentPaint.getFontMetricsInt();
        int dy = (fm.bottom - fm.top) / 2 - fm.bottom;
        int baseLine = height / 2 + dy;
        if(currentValue == maxValue){
            canvas.drawText("Done!",dx,baseLine,mProgressPercentPaint);
        }else {
            canvas.drawText(text,dx,baseLine,mProgressPercentPaint);
        }
    }

    public synchronized void setMaxValue(int value){
        maxValue = value;
    }

    public synchronized void setCurrentValue(int currentValue){
        this.currentValue = currentValue;
        postInvalidate();
    }

    public CircleProgressView setProgressTextSize(int progressTextSize) {
        this.mProgressTextSize = progressTextSize;
        return this;
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        paint.setDither(true);
        return paint;
    }
}
