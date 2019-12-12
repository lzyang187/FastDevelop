package com.lzy.libview.custom;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * 可监听布局的大小变化以及软键盘的弹出或隐藏
 *
 * @author: cyli8
 * @date: 2018/2/28 17:17
 */

public class ResizeLayout extends RelativeLayout {
    public interface OnSizeChangedListener {
        void onSizeChanged(int l, int t, int r, int b, int h,
                           boolean showing);
    }

    private OnSizeChangedListener mSizeChangedListener;

    private boolean mShowInput;
    private int mScreenHeight;

    public ResizeLayout(Context context) {
        super(context);
        mScreenHeight = getScreenHeight(context);
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenHeight = getScreenHeight(context);
    }

    public ResizeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScreenHeight = getScreenHeight(context);
    }

    public void setOnSizeChangedListener(OnSizeChangedListener l) {
        mSizeChangedListener = l;
    }

    @Override
    protected void onSizeChanged(int w, final int h, int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh > h && oldh < mScreenHeight && (oldh - h) > (mScreenHeight / 3)) { //这里阀值设为屏幕高的1/3
            // 软键盘弹出
            mShowInput = true;
        } else if ((h - oldh) > (mScreenHeight / 3)) {
            // 软键盘收起
            mShowInput = false;
        }
    }

    private int mTop = 0;
    private int mBot = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mTop == 0 && mBot == 0) {
            mTop = t;
            mBot = b;
        } else {
            int h = mBot - mTop;
            mTop = t;
            mBot = b;
            if (null != mSizeChangedListener && changed) {
                mSizeChangedListener.onSizeChanged(l, t, r, b, h, mShowInput);
            }
        }
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return context.getResources().getDisplayMetrics().heightPixels;
        }
        Point point = new Point();
        wm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }

}
