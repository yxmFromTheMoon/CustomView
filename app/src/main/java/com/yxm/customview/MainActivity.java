package com.yxm.customview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.yxm.customview.view.ColorTrackTextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ColorTrackTextView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(R.id.colorTrackTextView);
        Button leftToRight = findViewById(R.id.leftToRight);
        Button rightToLeft = findViewById(R.id.rightToLeft);
        leftToRight.setOnClickListener(this);
        rightToLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.leftToRight:
                leftToRight();
                break;
            case R.id.rightToLeft:
                rightToLeft();
                break;
        }
    }

    private void leftToRight(){
        mView.setDirection(ColorTrackTextView.Direction.LEFT_TO_RIGHT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                mView.setProgress(value);
            }
        });
        valueAnimator.start();
    }

    private void rightToLeft(){
        mView.setDirection(ColorTrackTextView.Direction.RIGHT_TO_LEFT);
        ValueAnimator valueAnimator = ObjectAnimator.ofFloat(0,1);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                mView.setProgress(value);
            }
        });
        valueAnimator.start();
    }
}
