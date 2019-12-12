package com.lzy.libview.mvp;

import android.content.DialogInterface;

/**
 * 基础fragment页面接口
 *
 * @author: cyli8
 * @date: 2018/2/28 11:25
 */

public interface IBaseView {
    /**
     * 页面是否已依附到activity
     *
     * @return true, 已加载
     */
    boolean isViewAttached();

    /**
     * 等待框
     *
     * @param tips 提示语
     */
    void showWaitingDialog(String tips, boolean cancel, DialogInterface.OnCancelListener listener);

    /**
     * 消失等待对话框
     */
    void dismissWaitingDialog();

    /**
     * toast
     *
     * @param text 文本
     */
    void toast(CharSequence text);

    /**
     * toast
     *
     * @param resId 资源id
     */
    void toast(int resId);
}
