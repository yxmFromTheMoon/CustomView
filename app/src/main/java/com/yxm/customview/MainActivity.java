package com.yxm.customview;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.yxm.customview.view.QQStepView;

public class MainActivity extends AppCompatActivity {

    private QQStepView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepView = findViewById(R.id.step_view);
        stepView.setStepMax(4000);

        //属性动画
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0,3000);
        valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            int currentStep = (int)animation.getAnimatedValue();
            stepView.setCurrentStep(currentStep);
        });
        valueAnimator.start();
    }

}
