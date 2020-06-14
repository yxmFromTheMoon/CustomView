package com.yxm.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author yxm
 * 2020/4/28 10:53
 * @function
 */
public class PathTest extends View {

    Paint mPaint;
    int mWidth;
    int mHeight;

    public PathTest(Context context) {
        this(context, null);
    }

    public PathTest(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);           // 画笔颜色 - 黑色
        mPaint.setStyle(Paint.Style.STROKE);    // 填充模式 - 描边
        mPaint.setStrokeWidth(10);              // 边框宽度 - 10
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.scale(1, -1);
        Path path = new Path();
        Path src = new Path();
//        path.lineTo(200, 200); //从上一次结束的点到指定点画一条直线
        //path.setLastPoint(0,0);//设置上次结束的位置，重置当前path中最后一个点位置，如果在绘制之前调用，效果和moveTo一样
        //path.moveTo(0,0);//设置下次操作的起点位置
//        path.lineTo(200, 0);
//        path.close();
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        src.addCircle(0, 0, 100, Path.Direction.CW);
        //path.addPath(src, 0, 0);
        path.lineTo(100,100);
        RectF oval = new RectF(0,0,300,300);
        path.arcTo(oval,0,359.999f,true);
        //path.addArc(oval,0,180);
        canvas.drawPath(path, mPaint);
    }
}