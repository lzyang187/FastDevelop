package com.lzy.libhttp;

import com.lzy.libhttp.https.HttpsUtil;
import com.lzy.libhttp.interceptor.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 单例获取{@link OkHttpClient}的实例
 *
 * @author: cyli8
 * @date: 2018/7/11 15:51
 */
public class MyHttpClient {
    private OkHttpClient mHttpClient;
    private static MyHttpClient mInstance;

    public static MyHttpClient getInstance() {
        if (mInstance == null) {
            synchronized (MyHttpClient.class) {
                if (mInstance == null) {
                    mInstance = new MyHttpClient();
                }
            }
        }
        return mInstance;
    }

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }

    private MyHttpClient() {
        //设置可访问所有的https网站
        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(null, null, null);
        mHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(HttpConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HttpConstants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new LogInterceptor(false))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();

    }

}
