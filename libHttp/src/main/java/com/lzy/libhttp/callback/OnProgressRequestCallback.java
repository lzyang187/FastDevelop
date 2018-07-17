package com.lzy.libhttp.callback;

import okhttp3.Call;

/**
 * 有进度的网络请求回调
 *
 * @author: cyli8
 * @date: 2018/7/16 14:29
 */
public interface OnProgressRequestCallback<T> extends OnRequestCallback<T> {

    void onProgress(Call call, long currentSize, long totalSize, int progress);
}
