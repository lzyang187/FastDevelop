package com.lzy.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {

    public static final int TYPE_WIFI = 1, TYPE_MOBILE = 0, TYPE_NONE = -1;

    /**
     * 检查网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean checkNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            return true;
        }
        activeNetInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return activeNetInfo != null && activeNetInfo.isConnected();
    }


    public static int getNetWorkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            return TYPE_WIFI;
        }
        activeNetInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            return TYPE_MOBILE;
        }
        return TYPE_NONE;
    }

}

