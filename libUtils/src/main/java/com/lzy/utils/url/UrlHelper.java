package com.lzy.utils.url;

/**
 * url帮助类
 *
 * @author: cyli8
 * @date: 2018/2/23 09:18
 */

public class UrlHelper {
    public static final String getFileNameFromUrl(final String url) {
        if (null == url || "".equalsIgnoreCase(url.trim())) {
            return url;
        }
        String flag = "/";
        String tmp = url.toLowerCase();
        int last = tmp.lastIndexOf(flag);
        if (last == url.length() - 1) {
            return null;
        }
        tmp = tmp.substring(last + 1);
        if (tmp.contains("?")) {//新上传到oss上的文件url附加了部分参数
            int index = tmp.indexOf("?");
            if (index > 0) {
                tmp = tmp.substring(0, index);
            }
        }
        String name = UrlEncode.decode(tmp);
        int length = name.length();
        if (name != null && length > 7) {
            name = name.substring(length - 7);
        }
        return name;
    }

    public static final boolean isUrlValid(String url) {
        if (null == url || "".equals(url.trim())) {
            return false;
        }
        url = url.trim();
        return url.startsWith("http://") || url.startsWith("https://");
    }

    public static boolean isFrescoAllowUrl(String url) {
        if (null == url || "".equals(url.trim())) {
            return false;
        }
        url = url.trim();
        return url.startsWith("file://") || url.startsWith("content://") | url.startsWith("res://")
                || isUrlValid(url) || isAssetsUrl(url);
    }

    public static boolean isAssetsUrl(String url) {
        if (null == url || "".equals(url.trim())) {
            return false;
        }
        url = url.trim();
        return url.startsWith("asset://");
    }

    public static boolean isHttpUrl(String url, boolean https) {
        if (null == url) {
            return false;
        }
        url = url.trim().toLowerCase();
        if (!https) {
            return url.startsWith("http://");
        }
        return url.startsWith("http://") || url.startsWith("https://");
    }
}
