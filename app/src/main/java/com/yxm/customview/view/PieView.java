package com.yxm.customview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yxm.customview.data.PieData;
import com.yxm.customview.utils.Utils;

import java.util.ArrayList;

/**
 * Created by yxm on 2020/3/24
 *
 * @function
 */
public class PieView extends View {

    private Paint mPaint = new Paint();
    private Paint mNamePaint;
    private int[] mColors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.DKGRAY};
    private ArrayList<PieData> mData;
    private float mStartAngle = 0;
    private int mWidth;
    private int mHeight;
    private int mOffsetLength = Utils.dp2px(getContext(), 20);

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mNamePaint = new Paint();

        mNamePaint.setAntiAlias(true);
        mNamePaint.setColor(Color.BLACK);
        mNamePaint.setTextSize(20);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (null == mData)
            return;
        float currentAngle = mStartAngle;
        canvas.translate(mWidth / 2, mHeight / 2);
        float radius = (float) (Math.min(getWidth(), getHeight()) / 2 * 0.8);

        RectF rectF = new RectF(-radius, -radius, radius, radius);

        for (PieData item : mData) {
            if (mData.indexOf(item) == 2) {
                canvas.save();
                canvas.translate(mOffsetLength * (float) Math.cos(Math.toRadians(currentAngle + item.getAngle() / 2)),
                        mOffsetLength * (float) Math.sin(Math.toRadians(currentAngle + item.getAngle() / 2)));
            }
            mPaint.setColor(item.getColor());
            canvas.drawArc(rectF, currentAngle, item.getAngle(), true, mPaint);
            currentAngle += item.getAngle();
            if (mData.indexOf(item) == 2) {
                canvas.restore();
            }
        }
    }

    //设置起始角度
    public void setStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }

    public void setData(ArrayList<PieData> data) {
        this.mData = data;
        initData(data);
        invalidate();
    }

    private void initData(ArrayList<PieData> data) {
        if (null == data || data.isEmpty())
            return;
        float sumValue = 0;
        for (PieData item : data) {
            sumValue += item.getValue();
            int j = data.indexOf(item) % mColors.length;
            item.setColor(mColors[j]);
        }

        for (PieData item : data) {
            float percentage = item.getValue() / sumValue;
            float angle = percentage * 360;
            item.setPercentage(percentage);
            item.setAngle(angle);
        }
    }
}
