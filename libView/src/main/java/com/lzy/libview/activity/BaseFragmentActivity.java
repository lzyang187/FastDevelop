package com.lzy.libview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.lzy.libview.R;
import com.lzy.libview.fragment.BaseFragment;

/**
 * 用于承载和显示Fragment内容的类，实现相关通用逻辑
 *
 * @author: cyli8
 * @date: 2018/2/28 11:00
 */

public class BaseFragmentActivity extends BaseActivity {
    public static final String KEY_FRAGMENT_CLASS_NAME = "fragment_class_name";

    public static final int INIT_WITH_FRAGMENT = 1;
    public static final int INIT_WITH_VIEW = 2;

    protected BaseFragment mFragment;
    protected FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        if (getInitMode() == INIT_WITH_FRAGMENT) {
            Intent intent = getIntent();
            mFragment = getFragment(intent);
            if (null == mFragment) {
                finish();
                return;
            }

            // 管理器
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, mFragment, "fragment");
            fragmentTransaction.commitAllowingStateLoss();
        } else {// 使用View进行初始化

        }
    }

    protected int getInitMode() {
        return INIT_WITH_FRAGMENT;
    }

    protected int getLayoutID() {
        return R.layout.lib_view_activity_fragment;
    }

    protected BaseFragment getFragment(Intent intent) {
        String clsName = intent.getStringExtra(KEY_FRAGMENT_CLASS_NAME);
        if (null != clsName) {
            try {
                BaseFragment fragment = (BaseFragment) Class.forName(clsName).newInstance();
                fragment.setArguments(intent.getExtras());
                return fragment;
            } catch (Exception e) {
            }
        }
        return null;
    }

    protected Intent getResultIntent() {
        if (null != mFragment) {
            return mFragment.getResultIntent();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != mFragment) {
            mFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                Intent intent = getResultIntent();
                if (null != intent) {
                    setResult(RESULT_OK, intent);
                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        if (null != mFragment && mFragment instanceof IOnKeyDownEvent) {
            if (((IOnKeyDownEvent) mFragment).onKeyDownEvent(keyCode, event)) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (null != mFragment) {
            if (mFragment.onBackPressed()) {
                return;
            }
        }
        super.onBackPressed();
    }
}
