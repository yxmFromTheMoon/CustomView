package com.yxm.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yxm on 2020/3/23
 *
 * @function 画布
 */
public class CanvasView extends View {

    private Paint mPaint = new Paint();
    Rect rect;
    RectF rectF;
    private int mWidth;
    private int mHeight;

    public CanvasView(Context context){
        this(context,null);
    }

    public CanvasView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        initPaint();
        rect = new Rect(100,100,800,400);
        rectF = new RectF(100,100,800,400);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawPoint(100,200,mPaint);
        //canvas.drawPoints(new float[]{100,200,100,300},mPaint);
        //canvas.drawLine(100,200,300,200,mPaint);
//        canvas.drawLines(new float[]{
//                100,200,300,200,
//                100,300,300,200
//        },mPaint);
        //canvas.drawRect(rectF,mPaint);
        //canvas.drawRoundRect(rectF,30,30,mPaint);
        //canvas.drawOval(rectF,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(500,500,400,mPaint);
        //mPaint.setColor(Color.RED);
        //canvas.drawArc(rectF,0,90,false,mPaint);
        //canvas.drawArc(rectF,0,90,true,mPaint);
    }

    private void initPaint(){
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(20f);
    }

}
