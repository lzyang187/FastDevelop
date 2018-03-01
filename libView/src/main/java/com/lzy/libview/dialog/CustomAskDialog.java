package com.lzy.libview.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lzy.libview.R;
import com.lzy.utils.StringUtil;

/**
 * 对话框
 * @author: cyli8
 * @date: 2018/2/28 10:20
 */

public class CustomAskDialog extends BaseDialog implements View.OnClickListener {
    public interface OnAskDlgListener {
        void onClickOk();

        void onClickCancel();
    }

    public static int KEY_BACK_TYPE_CANCEL = 0;
    public static int KEY_BACK_TYPE_OK = 1;
    public static int KEY_BACK_TYPE_DISMISS = 2;

    protected OnAskDlgListener mDlgListener;
    protected Context mContext;
    private TextView mTitleTv;
    private TextView mContent;
    private int mOkColor = Color.parseColor("#fc3259");
    private boolean mTitleLeft = false;
    private TextView mCancelBtn;
    //返回键处理事件类型，默认同取消键
    private int mKeyBackType = KEY_BACK_TYPE_CANCEL;

    public CustomAskDialog(Context context, String title, CharSequence content,
                           boolean left) {
        super(context, 0);
        mContext = context;
        mTitleLeft = left;
        init(title, content, null, null, left);
    }

    public CustomAskDialog(Context context, String title, CharSequence content,
                           String okStr, String cancelStr, boolean left) {
        super(context, 0);
        mContext = context;
        mTitleLeft = left;
        init(title, content, okStr, cancelStr, left);
    }

    public CustomAskDialog(Context context, String title, CharSequence content,
                           String okStr, String cancelStr, boolean left, int okcolor) {
        super(context, 0);
        mContext = context;
        mOkColor = okcolor;
        mTitleLeft = left;
        init(title, content, okStr, cancelStr, left);
    }

    public CustomAskDialog(Context context, String title, CharSequence content,
                           String okStr, String cancelStr, boolean titleleft,
                           boolean contentLeft) {
        super(context, 0);
        mContext = context;
        mTitleLeft = titleleft;
        init(title, content, okStr, cancelStr, contentLeft);
    }

    public void setListener(OnAskDlgListener l) {
        mDlgListener = l;
    }

    public void setContentGravity(int gravity) {
        if (null != mContent) {
            mContent.setGravity(gravity);
        }
    }

    public void updateContent(String content) {
        mContent.setText(content);
    }

    public void setTitleText(SpannableString str) {
        mTitleTv.setText(str);
    }

    public void setSubContentGravity(int gravity) {
        mContent.setGravity(gravity);
    }

    public void setCancelBtnGone() {
        mCancelBtn.setVisibility(View.GONE);
    }

    /**
     * 返回键处理类型，默认0
     *
     * @param type 0 取消键，1 ok键，2 不处理
     */
    public void setKeyBackType(int type) {
        mKeyBackType = type;
    }

    //对询问对话框进行初始化话
    private void init(String title, CharSequence content, String okStr,
                      String cancelStr, boolean left) {
        setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.lib_view_ask_dlg,
                null);

        mTitleTv = (TextView) view.findViewById(R.id.title);
        if (StringUtil.isTrimEmpty(title)) {
            mTitleTv.setVisibility(View.GONE);
        } else {
            mTitleTv.setText(title);
        }

        mContent = (TextView) view.findViewById(R.id.content);
        if (StringUtil.isTrimEmpty(content)) {
            mContent.setVisibility(View.GONE);
        } else {
            mContent.setText(content);
        }
        if (!left) {
            if (!mTitleLeft) {
                mTitleTv.setGravity(Gravity.CENTER);
            }
            mContent.setGravity(Gravity.CENTER);
        } else {
            if (!mTitleLeft) {
                mTitleTv.setGravity(Gravity.CENTER);
            }
        }
        TextView ok = (TextView) view.findViewById(R.id.dlg_ok);
        ok.setTextColor(mOkColor);
        if (!StringUtil.isTrimEmpty(okStr)) {
            ok.setText(okStr);
        }
        ok.setOnClickListener(this);
        mCancelBtn = (TextView) view.findViewById(R.id.dlg_cancel);
        if (!StringUtil.isTrimEmpty(cancelStr)) {
            mCancelBtn.setText(cancelStr);
        }
        mCancelBtn.setOnClickListener(this);
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dlg_cancel) {
            dismiss();
            if (null != mDlgListener) {
                mDlgListener.onClickCancel();
            }
//                analyseUserOptStatSafe(mStatInfo, NewStat.OPT_CANCEL);
        } else if (id == R.id.dlg_ok) {
            dismiss();
            if (null != mDlgListener) {
                mDlgListener.onClickOk();
            }
//                analyseUserOptStatSafe(mStatInfo, NewStat.OPT_CONFIRM);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //返回的话，加上判断，默认取消了
            if (null != mDlgListener) {
                if (mKeyBackType == KEY_BACK_TYPE_OK) {
                    mDlgListener.onClickOk();
                } else if (mKeyBackType == KEY_BACK_TYPE_CANCEL) {
                    mDlgListener.onClickCancel();
                } else {
                    //only dismiss
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
