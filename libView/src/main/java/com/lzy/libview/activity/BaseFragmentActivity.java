package com.lzy.libview.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

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

    private BaseFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        Intent intent = getIntent();
        initArgument(intent);
        mFragment = getFragment(intent);
        if (null == mFragment) {
            finish();
            return;
        }
        // 管理器
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, mFragment, "fragment");
        fragmentTransaction.commitAllowingStateLoss();
    }

    protected void initArgument(Intent intent) {

    }

    protected int getLayoutID() {
        return R.layout.lib_view_activity_base;
    }

    protected BaseFragment getFragment(Intent intent) {
        String clsName = intent.getStringExtra(KEY_FRAGMENT_CLASS_NAME);
        if (null != clsName) {
            try {
                BaseFragment fragment = (BaseFragment) Class.forName(clsName).newInstance();
                fragment.setArguments(intent.getExtras());
                return fragment;
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
