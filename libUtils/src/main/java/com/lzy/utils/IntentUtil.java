package com.lzy.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * Intent相关工具类
 *
 * @author: cyli8
 * @date: 2018/3/2 11:03
 */

public class IntentUtil {
    /**
     * 判断当前activity是否可跳转
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isActivityCanJump(Context context, Intent intent) {
        if (null == intent) return false;
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            return resolveInfo.activityInfo.exported;
        }
        return false;
    }
}
