package com.lzy.libview.dialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lzy.libview.R;

/**
 * 对话框
 *
 * @author: cyli8
 * @date: 2018/2/28 10:20
 */

public class CustomAskDialog extends BaseDialogFragment implements View.OnClickListener {

    public interface OnAskDlgListener {
        void onClickOk();

        void onClickCancel();
    }

    private TextView mOkTv;
    private TextView mCancelTv;
    private OnAskDlgListener mDlgListener;

    private String title, content;

    public CustomAskDialog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setListener(OnAskDlgListener l) {
        mDlgListener = l;
    }

    public void setCancelViewGone() {
        mCancelTv.setVisibility(View.GONE);
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate((R.layout.lib_view_ask_dlg), null);
        TextView titleTv = view.findViewById(R.id.title);
        if (TextUtils.isEmpty(title)) {
            titleTv.setVisibility(View.GONE);
        } else {
            titleTv.setText(title);
        }
        TextView contentTv = view.findViewById(R.id.content);
        if (TextUtils.isEmpty(content)) {
            contentTv.setVisibility(View.GONE);
        } else {
            contentTv.setText(content);
        }
        mOkTv = view.findViewById(R.id.dlg_ok);
        mOkTv.setOnClickListener(this);
        mCancelTv = view.findViewById(R.id.dlg_cancel);
        mCancelTv.setOnClickListener(this);
        return view;
    }

    public void setOkStr(String okStr) {
        if (!TextUtils.isEmpty(okStr)) {
            mOkTv.setText(okStr);
        }
    }

    public void setCancelStr(String cancelStr) {
        if (!TextUtils.isEmpty(cancelStr)) {
            mCancelTv.setText(cancelStr);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dlg_cancel) {
            dismiss();
            if (null != mDlgListener) {
                mDlgListener.onClickCancel();
            }
        } else if (id == R.id.dlg_ok) {
            dismiss();
            if (null != mDlgListener) {
                mDlgListener.onClickOk();
            }
        }
    }

}
