package com.lzy.libhttp.builder;

import android.net.Uri;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * @author: cyli8
 * @date: 2018/7/16 15:00
 */
public class GetBuilder {

    private String appendUrlParams(String url, LinkedHashMap<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }
}
