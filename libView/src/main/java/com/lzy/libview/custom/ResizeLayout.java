package com.lzy.libview.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.lzy.utils.system.ScreenUtil;

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

    int count = 0;
    int count1 = 0;
    int count2 = 0;
    private OnSizeChangedListener mSizeChangedListener;

    private boolean mShowInput;
    private Context mContext;

    // 定义默认的软键盘最小高度，这是为了避免onSizeChanged在某些下特殊情况下出现的问题。
    private static final String TAG = "ResizeLayout";

    public ResizeLayout(Context context) {
        super(context);
        mContext = context;
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ResizeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setOnSizeChangedListener(OnSizeChangedListener l) {
        mSizeChangedListener = l;
    }

    @Override
    protected void onSizeChanged(int w, final int h, int oldw, final int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int height = ScreenUtil.getScreenHeight(mContext);
        if (oldh > h && oldh < height && (oldh - h) > (height / 3)) { //修复软键盘是否弹起的bug，这里阀值设为屏幕高的1/3
            // 软键盘弹出
            mShowInput = true;
        } else if ((h - oldh) > (height / 3)) {
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
}
