package com.lzy.fastdevelop;

import android.app.Application;

import com.lzy.utils.log.MLog;

/**
 * @author: cyli8
 * @date: 2018/2/27 15:34
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MLog.initLogger(BuildConfig.DEBUG);
    }
}

