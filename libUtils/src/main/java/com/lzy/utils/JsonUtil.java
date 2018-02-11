package com.lzy.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: cyli8
 * @date: 2018/2/11 16:50
 */

public class JsonUtil {
    /**
     * 将对象序列化成JSON字符串
     *
     * @param obj
     * @return json类型字符串
     */
    public static String toJSONString(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    /**
     * 将JSON字符串反序列化成对象
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                return JSON.parseObject(jsonStr, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON字符串反序列化成List
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        try {
            if (!TextUtils.isEmpty(jsonStr)) {
                return JSON.parseArray(jsonStr, clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
