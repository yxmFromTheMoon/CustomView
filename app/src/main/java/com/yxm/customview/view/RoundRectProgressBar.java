package com.yxm.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yxm.customview.R;

/**
 * @author lm
 * @time 2020/8/274:56 PM
 * @description
 */
public class RoundRectProgressBar extends View {

    private final static String TAG = RoundRectProgressBar.class.getSimpleName();

    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 圆角环的颜色
     */
    private int roundColor;

    /**
     * 进度的颜色
     */
    private int fillProgressColor;

    /**
     * 填充的颜色
     */
    private int fillColor;

    /**
     * 圆角矩形宽度
     */
    private int roundWidth;

    /**
     * 圆角矩形高度
     */
    private int roundHeight;

    /**
     * 进度条方向，0水平，1垂直
     */
    private int barOrientation;

    /**
     * 进度条最大值
     */
    private float max = 100;

    /**
     * 进度条当前值
     */
    private float progress = 30;

    public RoundRectProgressBar(Context context) {
        this(context, null);
    }

    public RoundRectProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取画笔
        paint = new Paint();
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundRectProgressBar);
        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundRectProgressBar_cbarRoundColor, Color.RED);
        fillProgressColor = mTypedArray.getColor(R.styleable.RoundRectProgressBar_cbarProgressColor, Color.GREEN);
        fillColor = mTypedArray.getColor(R.styleable.RoundRectProgressBar_cbarFillColor, Color.BLUE);
        barOrientation = mTypedArray.getInt(R.styleable.RoundRectProgressBar_cbarOrientation, 0);
        //回收TypedArray资源
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置抗锯齿效果
        paint.setAntiAlias(true);
        //设置画笔颜色
        paint.setColor(roundColor);
        //进度方向
        if (barOrientation == 0) {
            //水平，向右
            try {
                int round = roundHeight / 2;
                //RectF：绘制矩形，四个参数分别是left,top,right,bottom,类型是单精度浮点数
                RectF rf = new RectF(0, 0, roundWidth, roundHeight);
                //绘制圆角矩形，背景色为画笔颜色
                canvas.drawRoundRect(rf, round, round, paint);
                //设置progress内部是灰色
                paint.setColor(fillColor);
                RectF rectBlackBg = new RectF(2, 2, roundWidth - 2, roundHeight - 2);
                canvas.drawRoundRect(rectBlackBg, round, round, paint);
                //设置进度条进度及颜色
                float section = progress / max;
                RectF rectProgressBg = new RectF(2, 2, (roundWidth - 2) * section, roundHeight - 2);
                if (section != 0.0f) {
                    paint.setColor(fillProgressColor);
                } else {
                    paint.setColor(Color.TRANSPARENT);
                }
                canvas.drawRoundRect(rectProgressBg, round, round, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //垂直，向上
            try {
                int round = roundWidth / 2;
                //RectF：绘制矩形，四个参数分别是left,top,right,bottom,类型是单精度浮点数
                RectF rf = new RectF(0, 0, roundWidth, roundHeight);
                //绘制圆角矩形，背景色为画笔颜色
                canvas.drawRoundRect(rf, round, round, paint);
                //设置progress内部是灰色
                paint.setColor(fillColor);
                RectF rectBlackBg = new RectF(2, 2, roundWidth - 2, roundHeight - 2);
                canvas.drawRoundRect(rectBlackBg, round, round, paint);
                //设置进度条进度及颜色
                float section = progress / max;
                RectF rectProgressBg = new RectF(2, roundHeight - 2 - (roundHeight - 4) * section, roundWidth - 2, roundHeight - 2);
                if (section != 0.0f) {
                    paint.setColor(fillProgressColor);
                } else {
                    paint.setColor(Color.TRANSPARENT);
                }
                canvas.drawRoundRect(rectProgressBg, round, round, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * dip转px
     *
     * @param dip
     * @return
     */
    private int dipToPx(int dip) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        //加0.5是为了四舍五入
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * 指定自定义控件在屏幕上的大小,onMeasure方法的两个参数是由上一层控件
     * 传入的大小，而且是模式和尺寸混合在一起的数值，需要MeasureSpec.getMode(widthMeasureSpec)
     * 得到模式，MeasureSpec.getSize(widthMeasureSpec)得到尺寸
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        //MeasureSpec.EXACTLY，精确尺寸
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            roundWidth = widthSpecSize;
        } else {
            roundWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.EXACTLY || heightSpecMode == MeasureSpec.AT_MOST) {
            roundHeight = heightSpecSize;
        } else {
            roundHeight = 0;
        }
        //MeasureSpec.AT_MOST，最大尺寸，只要不超过父控件允许的最大尺寸即可，MeasureSpec.UNSPECIFIED未指定尺寸
        //if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
        //    roundHeight = dipToPx(20);
        //} else {
        //    roundHeight = heightSpecSize;
        //}
        //设置控件实际大小
        setMeasuredDimension(roundWidth, roundHeight);
    }


    /**
     * 设置进度
     *
     * @param progress
     */
    public synchronized void setProgress(float progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("value can not be negative");
        }
        if (progress > max) {
            this.progress = max;
        } else {
            this.progress = progress;
        }
        postInvalidate();
    }

    /**
     * 设置最大值
     *
     * @param max
     */
    public synchronized void setMax(float max) {
        if (max < 0) {
            throw new IllegalArgumentException("value can not be negative");
        }
        this.max = max;
    }

}
