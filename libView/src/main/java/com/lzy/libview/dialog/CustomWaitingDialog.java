package com.lzy.libview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lzy.libview.R;

/**
 * 等待框
 *
 * @author: cyli8
 * @date: 2018/2/28 10:47
 */

public class CustomWaitingDialog extends Dialog {

    private String mTips;
    private TextView mTipsTv;

    public CustomWaitingDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
    }

    public CustomWaitingDialog(Context context, String tips) {
        super(context);
        setCanceledOnTouchOutside(false);
        mTips = tips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.lib_view_progress_dialog_layout);
        findViewById(R.id.root).setBackgroundColor(Color.TRANSPARENT);
        if (TextUtils.isEmpty(mTips)) {
            findViewById(R.id.tips_tv).setVisibility(View.GONE);
        } else {
            mTipsTv = findViewById(R.id.tips_tv);
            mTipsTv.setVisibility(View.VISIBLE);
            mTipsTv.setText(mTips);
        }
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
    }

    public void updateTips(String tips) {
        mTipsTv.setText(tips);
    }
}
