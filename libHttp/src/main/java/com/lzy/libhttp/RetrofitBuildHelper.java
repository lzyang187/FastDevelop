package com.lzy.libhttp;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: cyli8
 * @date: 2019-11-07 08:55
 */
public class RetrofitBuildHelper {

    public static Retrofit build(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                .client(MyOkHttpClient.getInstance().getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
