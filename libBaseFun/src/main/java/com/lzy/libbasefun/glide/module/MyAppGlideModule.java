package com.lzy.libbasefun.glide.module;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author: cyli8
 * @date: 2019-11-27 11:53
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    //更改Glide配置
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //设置Glide图片缓存位置是SD卡
        long cacheSize = 500 * 1025 * 1024;//500MB
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, cacheSize));
    }

}
