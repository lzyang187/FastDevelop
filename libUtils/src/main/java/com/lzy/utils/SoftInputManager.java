package com.lzy.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

public class SoftInputManager {

    /**
     * 弹出软键盘，有些手机不能及时弹出，故用该延时方式
     *
     * @param context
     */
    public static void showSoftInput(final Context context) {
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                cancel();
                timer.cancel();
            }
        };
        timer.schedule(task, 800);
    }

    /**
     * 弹出软键盘
     *
     * @param context
     * @param view
     */
    public static void showSoftInput(Context context, EditText view) {
        if (context != null && view != null) {
            InputMethodManager inputManager = (InputMethodManager) view
                    .getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, 0);
        }
    }

    /**
     * 显示或者隐藏输入法
     *
     * @param context
     */
    @Deprecated
    public static void hideSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean hideSoftInput(final EditText e) {
        InputMethodManager inputManager = (InputMethodManager) e
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        return inputManager.hideSoftInputFromWindow(e.getWindowToken(), 0);
    }
}
