package com.lzy.libview.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


/**
 * 渐变色的TextView，使用时在代码中调用 {@link #setColor(int, int)}
 * Created by cyli8 on 2017/9/6.
 */
public class GradientTextView extends AppCompatTextView {
    private int mOriginColor = Color.DKGRAY;
    private int mTargetColor = Color.DKGRAY;
    private Shader mShader;

    /**
     * 设置的渐变颜色值
     *
     * @param originColor 左上角的颜色值
     * @param targetColor 右下角的颜色值
     */
    public void setColor(int originColor, int targetColor) {
        mOriginColor = originColor;
        mTargetColor = targetColor;
    }

    public GradientTextView(Context context) {
        super(context);
    }

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int bottom;
    private int right;

    @Override
    protected void onDraw(Canvas canvas) {
        if (bottom <= 0 || right <= 0) {
            bottom = getHeight();
            right = getWidth();
            mShader = new LinearGradient(0, 0, right, bottom,
                    new int[]{mOriginColor, mTargetColor},
                    new float[]{0, 1}, Shader.TileMode.CLAMP);
            getPaint().setShader(mShader);
        }
        super.onDraw(canvas);
    }
}
