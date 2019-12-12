package com.lzy.libview.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.lzy.libview.R;

/**
 * @author: cyli8
 * @date: 2018/2/28 11:40
 */

public class BaseTitleFragmentActivity extends BaseFragmentActivity {
    public static final String KEY_ACTIVITY_TITLE = "key_activity_title";
    private Toolbar mToolbar;
    private String mTitle;
    private TextView mTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.mipmap.lib_view_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseTitleFragmentActivity.this.onBackPressed();
            }
        });
        mTitleTv = findViewById(R.id.title_tv);
        setTitle(mTitle);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.lib_view_activity_base_title;
    }

    @Override
    protected void initArgument(Intent intent) {
        mTitle = intent.getStringExtra(KEY_ACTIVITY_TITLE);
    }

    public void setTitle(String title) {
        mTitleTv.setText(title);
    }

}
