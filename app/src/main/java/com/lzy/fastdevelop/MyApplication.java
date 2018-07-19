package com.lzy.fastdevelop;

import android.app.Application;

import com.lzy.bizcore.AppConfig;
import com.lzy.bizcore.fresco.FrescoHelper;
import com.lzy.libhttp.OkGoHelper;
import com.lzy.utils.log.MLog;

/**
 * 应用的Application
 *
 * @author: cyli8
 * @date: 2018/2/27 15:34
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.init(this, BuildConfig.DEBUG, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME,
                getString(R.string.app_name), BuildConfig.APPLICATION_ID);
        MLog.initLogger(BuildConfig.DEBUG);
        OkGoHelper.init(this);
        FrescoHelper.init(this);
    }
}

