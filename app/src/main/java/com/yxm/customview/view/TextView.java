package com.yxm.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.yxm.customview.R;

/**
 * @author yxm
 * 2020/6/9 22:13
 * @function
 */
public class TextView extends View {

    private int mTextSize = 15;
    private int mTextColor = Color.BLACK;
    private String mText;
    private Paint mPaint;

    public TextView(Context context) {
        this(context,null);
    }

    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TextView);
        mText = typedArray.getString(R.styleable.TextView_customText);
        mTextSize = typedArray.getDimensionPixelSize(R.styleable.TextView_customTextSize, sp2px(mTextSize));
        mTextColor = typedArray.getColor(R.styleable.TextView_customTextColor, mTextColor);
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        //2.设置背景透明
        // setBackgroundColor(Color.TRANSPARENT);
        //3.改变mPrivateFlags的值
        // setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //1.确定的值，不需要计算,给的多少就是多少
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //2.wrap_content,需要计算
        if(widthMode == MeasureSpec.AT_MOST){
            //计算的宽度,宽度与字体的长度有关，与字体的大小有关
            Rect bounds =new Rect();
            //获取文本的矩阵
            mPaint.getTextBounds(mText,0, mText.length(),bounds);
            width = bounds.width() + getPaddingLeft() + getPaddingRight();
        }
        if(heightMode == MeasureSpec.AT_MOST){
            Rect bounds =new Rect();
            mPaint.getTextBounds(mText,0, mText.length(),bounds);
            height = bounds.height() + getPaddingTop() + getPaddingBottom();
        }

        //3.match_content

        //设置宽高
        setMeasuredDimension(width,height);
    }

    //继承自ViewGroup不显示的解决方法

//1.重写dispatchDraw方法
//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        super.dispatchDraw(canvas);
//        //dy代表的是：高度的一半到baseLine的距离
//        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
//        // top是一个负值，bottom是一个正值
//        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
//        int baseLine = getHeight() / 2 + dy;
//        //x 开始的位置
//        int x = getPaddingLeft();
//        //y 基线
//        canvas.drawText(mText,x,baseLine,mPaint);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //dy代表的是：高度的一半到baseLine的距离
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        // top是一个负值，bottom是一个正值
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        //x 开始的位置
        int x = getPaddingLeft();
        //y 基线
        canvas.drawText(mText,x,baseLine,mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                Log.i("TextView","DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("TextView","MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TextView","UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    private int sp2px(float sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp,getResources().getDisplayMetrics());
    }
}
