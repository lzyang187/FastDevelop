package com.lzy.libview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.libview.R;

/**
 * @author: cyli8
 * @date: 2018/2/28 11:40
 */

public class BaseTitleFragmentActivity extends BaseFragmentActivity implements View.OnClickListener {
    protected View mTitleLayout;
    protected ImageView mBackView;
    protected TextView mTitleTv;
    protected ImageView mRightBtn;
    protected View mFragmentContainer;
    protected TextView mRightTv;
    protected RelativeLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootLayout = (RelativeLayout) findViewById(R.id.root_layout);
        mTitleLayout = findViewById(R.id.title_layout);
        mBackView = (ImageView) findViewById(R.id.go_back);
        mBackView.setOnClickListener(this);
        mRightBtn = (ImageView) findViewById(R.id.right_btn);
        mRightBtn.setOnClickListener(this);
        mTitleTv = (TextView) findViewById(R.id.title);
        mRightTv = (TextView) findViewById(R.id.right_tv);
        mRightTv.setOnClickListener(this);
        if (getInitMode() == INIT_WITH_FRAGMENT) {
            mFragmentContainer = findViewById(R.id.fragment_container);
        } else {
            // 干掉这个控件
            mFragmentContainer = findViewById(R.id.fragment_container);
            if (null != mFragmentContainer) {
                ViewParent parent = mFragmentContainer.getParent();
                if (null != parent && parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(mFragmentContainer);
                }
                mFragmentContainer = null;
            }
            // 重新进行组织:
            int layoutid = getCenterLayoutID();
            if (layoutid > 0) {
                try {
                    mFragmentContainer = LayoutInflater.from(this).inflate(layoutid, null);
                } catch (Exception e) {
                    //
                }
                if (null != mFragmentContainer) { // 加入到整体布局
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    lp.addRule(RelativeLayout.BELOW, R.id.title_layout);
                    rootLayout.addView(mFragmentContainer, lp);
                }
            }
        }
        mTitleTv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTitleTv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mTitleTv.setText(getMenuTitle());
                if (null != mFragment && mFragment instanceof IClickRightButtonAble) {
                    CharSequence rightTv = ((IClickRightButtonAble) mFragment).getRightButtonTv();
                    if (!TextUtils.isEmpty(rightTv)) {
                        setRightTv(rightTv);
                    }
                    int resId = ((IClickRightButtonAble) mFragment).getRightButtonImgId();
                    if (resId > 0) {
                        setRightBtnImg(resId);
                    }
                }
            }
        });
        mTitleLayout.setOnClickListener(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.lib_view_activity_fragment_title;
    }

    protected int getCenterLayoutID() {
        return 0;
    }

    protected void hideTitleLayout() {
        if (mTitleLayout != null) {
            mTitleLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBackView) {
            onClickBack();
        } else if (v == mRightBtn) {
            onClickRightBtn();
        } else if (v == mRightTv) {
            onClickRightTv();
        } else if (v == mTitleLayout) {
            if (mFragment instanceof IListBacktop) {
                ((IListBacktop) mFragment).scrollToTop();
            }
        }
    }

    public String getMenuTitle() {
        if (null == mFragment) {
            return null;
        }
        CharSequence title = mFragment.getTitle();
        return null == title ? null : title.toString();
    }

    public void refreshTitle() {
        if (null == mFragment || null == mTitleTv) {
            return;
        }
        CharSequence title = mFragment.getTitle();
        mTitleTv.setText(title);
    }

    public void onClickBack() {
        Intent intent = getResultIntent();
        if (null != intent) {
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    public void setBackBtnImg(int resId) {
        mBackView.setImageResource(resId);
    }

    public void setRightBtnImg(int resId) {
        mRightBtn.setImageResource(resId);
        mRightBtn.setVisibility(View.VISIBLE);
        mRightTv.setVisibility(View.GONE);
    }

    public void onClickRightBtn() {
        if (mFragment instanceof IClickRightButtonAble) {
            ((IClickRightButtonAble) mFragment).onClickRightBtn();
        }
    }

    public void onClickRightTv() {
        if (mFragment instanceof IClickRightButtonAble) {
            ((IClickRightButtonAble) mFragment).onClickRightTv();
        }
    }

    public void setRightTv(CharSequence text) {
        mRightTv.setText(text);
        mRightTv.setVisibility(View.VISIBLE);
        mRightBtn.setVisibility(View.GONE);
    }

    public void hideRightView() {
        mRightTv.setVisibility(View.GONE);
        mRightBtn.setVisibility(View.GONE);
    }

    public void showRightView(int type) {
        if (type == 1) {
            mRightTv.setVisibility(View.VISIBLE);
            mRightBtn.setVisibility(View.GONE);
        } else if (type == 2) {
            mRightTv.setVisibility(View.GONE);
            mRightBtn.setVisibility(View.VISIBLE);
        }
    }

    public TextView getRightBtnTv() {
        return mRightTv;
    }
}
