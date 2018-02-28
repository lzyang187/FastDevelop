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
     * 显示等待框
     *
     * @param l 取消观察者
     */
    void showWaitingDialog(DialogInterface.OnCancelListener l);

    /**
     * 显示等待框
     *
     * @param l    取消观察者
     * @param tips 提示语
     */
    void showWaitingDialog(DialogInterface.OnCancelListener l, String tips);

    /**
     * 更新等待框提示语
     *
     * @param tips 提示语
     */
    void updateWaittingTips(String tips);

    /**
     * 设置对话框是否可以被取消
     *
     * @param cancel true,可取消
     */
    void setWaittingDlgCancel(boolean cancel);

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
