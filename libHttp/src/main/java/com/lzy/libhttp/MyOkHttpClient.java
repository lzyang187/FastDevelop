package com.lzy.libhttp;

import com.lzy.libhttp.interceptor.LogInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * @author: cyli8
 * @date: 2019-11-06 15:33
 */
public class MyOkHttpClient {

    private static File mCacheFile;

    public static void setCacheFile(File file) {
        mCacheFile = file;
    }

    private MyOkHttpClient() {
        Cache cache = new Cache(mCacheFile, 10 * 1024 * 1024);
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .addInterceptor(new LogInterceptor(true))
                .build();
    }

    private static MyOkHttpClient mInstance;

    public static synchronized MyOkHttpClient getInstance() {
        if (mInstance == null) {
            mInstance = new MyOkHttpClient();
        }
        return mInstance;
    }

    private OkHttpClient mOkHttpClient;

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

}
