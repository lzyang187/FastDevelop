package com.lzy.libview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.Window;

import com.lzy.libview.R;

/**
 * 基础Dialog
 *
 * @author: cyli8
 * @date: 2018/2/28 10:14
 */

public class BaseDialog extends Dialog {

    /**
     * @param context     context
     * @param animStyleID 对话框弹出收起动画
     */
    public BaseDialog(Context context, int animStyleID) {
        super(context, R.style.lib_view_theme_basedialog);
        Window dialogWindow = getWindow();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogWindow.getDecorView().setBackgroundColor(Color.TRANSPARENT);

//        WindowManager.LayoutParams dialogParams = getWindow().getAttributes();
//        dialogParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        if (animStyleID > 0 && null != dialogWindow) {
            dialogWindow.setWindowAnimations(animStyleID);
        }
//        dialogWindow.setAttributes(dialogParams);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onKeyBack()) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    protected boolean onKeyBack() {
        return false;
    }
}
