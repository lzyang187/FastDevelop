package com.lzy.utils.url;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: cyli8
 * @date: 2018/2/23 09:22
 */

public class UrlEncode {
    public static String decode(final String url) {
        try {
            return URLDecoder.decode(url, "utf-8");
        } catch (final UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String url, final String code) {
        if (null == url) {
            return url;
        }
        final Matcher matcher = Pattern.compile("[\\u4e00-\\u9fa5]").matcher(url);
        while (matcher.find()) {
            String tmp = matcher.group();
            try {
                url = url.replaceAll(tmp, URLEncoder.encode(tmp, code));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return url;
    }

    public static String encodeGBK(final String url) {
        return encode(url, "gbk");
    }

    public static String encodeUTF8(final String url) {
        return encode(url, "utf-8");
    }
}
