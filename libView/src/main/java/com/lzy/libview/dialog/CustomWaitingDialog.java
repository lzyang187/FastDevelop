package com.lzy.libview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lzy.libview.R;
import com.lzy.utils.StringUtil;

/**
 * 等待框
 *
 * @author: cyli8
 * @date: 2018/2/28 10:47
 */

public class CustomWaitingDialog extends Dialog {

    private int mBgColor;
    private String mTips;
    private TextView mTipsTv;

    public CustomWaitingDialog(@NonNull Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        mBgColor = Color.TRANSPARENT;
    }

    public CustomWaitingDialog(@NonNull Context context, int color) {
        super(context);
        setCanceledOnTouchOutside(false);
        mBgColor = color;
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
        findViewById(R.id.root).setBackgroundColor(mBgColor);
        if (StringUtil.isTrimEmpty(mTips)) {
            findViewById(R.id.tips_tv).setVisibility(View.GONE);
        } else {
            mTipsTv = ((TextView) findViewById(R.id.tips_tv));
            mTipsTv.setVisibility(View.VISIBLE);
            mTipsTv.setText(mTips);
        }
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    public void updateTips(String tips) {
        mTipsTv.setText(tips);
    }
}
