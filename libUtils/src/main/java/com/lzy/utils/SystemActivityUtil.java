package com.lzy.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author: cyli8
 * @date: 2018/2/11 16:55
 */

public class SystemActivityUtil {
    /***
     * 打开浏览器，用户选择使用哪个浏览器
     */
    public static final void callBrowser(Context c, String url) {
        callBrower(c, url, null, null);
    }

    private static final void callBrower(Context c, String url, String packName, String activityName) {
        String httpUrl = url;
        int index = httpUrl.indexOf("http://");
        if (index < 0) {
            index = httpUrl.indexOf("https://");
        }
        if (index < 0) {
            httpUrl = "http://" + url;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri content_url = Uri.parse(httpUrl);
        intent.setData(content_url);
        if (null != packName && null != activityName) {
            intent.setClassName(packName, activityName);
        }
        c.startActivity(intent);
    }

    /**
     * 跳至拨号界面
     *
     * @param phoneNumber 电话号码
     */
    public static void dialPhone(Context context, String phoneNumber) {
        Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE" />}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static void callPhone(Context context, String phoneNumber) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }
}
