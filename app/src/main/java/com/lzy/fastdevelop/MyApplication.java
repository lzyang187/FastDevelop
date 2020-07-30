package com.lzy.fastdevelop;

import android.app.Application;

import com.lzy.bizcore.AppConfig;
import com.lzy.libbasefun.lru.DiskLruCacheMgr;
import com.lzy.libhttp.MyOkHttpClient;
import com.lzy.utils.StringUtil;
import com.lzy.utils.log.MLog;
import com.mcxiaoke.packer.helper.PackerNg;

import java.io.File;

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
        String channel = PackerNg.getChannel(this);
        if (StringUtil.isEmpty(channel)) {
            channel = getString(R.string.official_channel);
        }
        AppConfig.init(this, BuildConfig.DEBUG, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME,
                getString(R.string.app_name), BuildConfig.APPLICATION_ID, channel);
//        MLog.initLogger(AppConfig.APP_NAME, BuildConfig.DEBUG);
        MyOkHttpClient.setCacheFile(new File(getExternalCacheDir(), "http"));
        DiskLruCacheMgr.getInstance().init(this);
    }
}

