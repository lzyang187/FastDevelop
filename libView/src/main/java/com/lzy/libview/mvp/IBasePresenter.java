package com.lzy.libview.mvp;

/**
 * 基础页面presenter接口
 *
 * @author: cyli8
 * @date: 2018/2/28 11:14
 */

public interface IBasePresenter {
    /**
     * 取消页面请求
     */
    void cancelRequest();

    /**
     * 页面销毁
     */
    void destroy();
}
