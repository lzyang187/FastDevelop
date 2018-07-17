package com.lzy.libhttp.callback;

import okhttp3.Call;

/**
 * 网络请求的结果回调，回调方法运行在主线程
 *
 * @param <T> 结果的泛型
 */
public interface OnRequestCallback<T> {
    void onResponse(Call call, T result);

    /**
     * 网络请求失败的回调
     *
     * @param failType 网络请求失败类型
     */
    void onFailed(Call call, int failType);

}