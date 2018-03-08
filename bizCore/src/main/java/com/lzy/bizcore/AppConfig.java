package com.lzy.bizcore;

import android.content.Context;

import com.lzy.utils.system.DeviceUtil;
import com.lzy.utils.system.ScreenUtil;

/**
 * 全局app信息
 *
 * @author: cyli8
 * @date: 2018/3/8 10:14
 */

public class AppConfig {
    public static String MAC;
    public static String IMEI;
    public static String ANDROID_ID;

    public static boolean DEBUG;
    public static int VERSION_CODE;
    public static String VERSION_NAME;
    public static String APP_NAME;
    public static String PACKAGE_NAME;

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static void init(Context context, boolean debug, int versionCode, String versionName,
                            String appName, String packageName) {
        DEBUG = debug;
        VERSION_CODE = versionCode;
        VERSION_NAME = versionName;
        APP_NAME = appName;
        PACKAGE_NAME = packageName;
        MAC = DeviceUtil.getMacAddress(context);
        IMEI = DeviceUtil.getIMEI(context);
        ANDROID_ID = DeviceUtil.getAndroidID(context);
        SCREEN_WIDTH = ScreenUtil.getScreenWidth(context);
        SCREEN_HEIGHT = ScreenUtil.getScreenHeight(context);
    }
}
