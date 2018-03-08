package com.lzy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 共享偏好工具类
 *
 * @author: cyli8
 * @date: 2017/9/22 16:56
 */

public class SharedPreferencesUtil {
    private SharedPreferences mSP;

    public static final String DEFAULT_SP_NAME = "fastdevelop_sp";

    public SharedPreferencesUtil(Context context) {
        mSP = context.getSharedPreferences(DEFAULT_SP_NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferencesUtil(Context context, String spName) {
        mSP = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSp() {
        return mSP;
    }

    /**
     * SP中写入String
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull final String key, @NonNull final String value) {
        put(key, value, false);
    }

    /**
     * SP中写入String
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean put(@NonNull String key, @NonNull String value, boolean isCommit) {
        if (isCommit) {
            return mSP.edit().putString(key, value).commit();
        } else {
            mSP.edit().putString(key, value).apply();
            return false;
        }
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code ""}
     */
    public String getString(@NonNull String key) {
        return getString(key, "");
    }

    /**
     * SP中读取String
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public String getString(@NonNull String key, @NonNull String defaultValue) {
        return mSP.getString(key, defaultValue);
    }

    /**
     * SP中写入int
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, int value) {
        put(key, value, false);
    }

    /**
     * SP中写入int
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean put(@NonNull String key, final int value, final boolean isCommit) {
        if (isCommit) {
            return mSP.edit().putInt(key, value).commit();
        } else {
            mSP.edit().putInt(key, value).apply();
            return false;
        }
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public int getInt(@NonNull String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public int getInt(@NonNull String key, int defaultValue) {
        return mSP.getInt(key, defaultValue);
    }

    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, long value) {
        put(key, value, false);
    }

    /**
     * SP中写入long
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean put(@NonNull String key, long value, boolean isCommit) {
        if (isCommit) {
            return mSP.edit().putLong(key, value).commit();
        } else {
            mSP.edit().putLong(key, value).apply();
            return false;
        }
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(@NonNull String key) {
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(@NonNull String key, long defaultValue) {
        return mSP.getLong(key, defaultValue);
    }

    /**
     * SP中写入float
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull String key, float value) {
        put(key, value, false);
    }

    /**
     * SP中写入float
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean put(@NonNull final String key, final float value, final boolean isCommit) {
        if (isCommit) {
            return mSP.edit().putFloat(key, value).commit();
        } else {
            mSP.edit().putFloat(key, value).apply();
            return false;
        }
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(@NonNull String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(@NonNull String key, float defaultValue) {
        return mSP.getFloat(key, defaultValue);
    }

    /**
     * SP中写入boolean
     *
     * @param key   键
     * @param value 值
     */
    public void put(@NonNull final String key, boolean value) {
        put(key, value, false);
    }

    /**
     * SP中写入boolean
     *
     * @param key      键
     * @param value    值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean put(@NonNull String key, boolean value, boolean isCommit) {
        if (isCommit) {
            return mSP.edit().putBoolean(key, value).commit();
        } else {
            mSP.edit().putBoolean(key, value).apply();
            return false;
        }
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(@NonNull String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(@NonNull String key, boolean defaultValue) {
        return mSP.getBoolean(key, defaultValue);
    }

    /**
     * SP中写入String集合
     *
     * @param key    键
     * @param values 值
     */
    public void put(@NonNull String key, @NonNull Set<String> values) {
        put(key, values, false);
    }

    /**
     * SP中写入String集合
     *
     * @param key      键
     * @param values   值
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean put(@NonNull String key, @NonNull Set<String> values, boolean isCommit) {
        if (isCommit) {
            return mSP.edit().putStringSet(key, values).commit();
        } else {
            mSP.edit().putStringSet(key, values).apply();
            return false;
        }
    }

    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@link Collections#emptySet()}
     */
    public Set<String> getStringSet(@NonNull String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * SP中读取StringSet
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public Set<String> getStringSet(@NonNull String key, @NonNull Set<String> defaultValue) {
        return mSP.getStringSet(key, defaultValue);
    }

    /**
     * SP中获取所有键值对
     *
     * @return Map对象
     */
    public Map<String, ?> getAll() {
        return mSP.getAll();
    }

    /**
     * SP中是否存在该key
     *
     * @param key 键
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(@NonNull String key) {
        return mSP.contains(key);
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(@NonNull String key) {
        remove(key, false);
    }

    /**
     * SP中移除该key
     *
     * @param key      键
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean remove(@NonNull String key, boolean isCommit) {
        if (isCommit) {
            return mSP.edit().remove(key).commit();
        } else {
            mSP.edit().remove(key).apply();
            return false;
        }
    }

    /**
     * SP中清除所有数据
     */
    public void clear() {
        clear(false);
    }

    /**
     * SP中清除所有数据
     *
     * @param isCommit {@code true}: {@link SharedPreferences.Editor#commit()}<br>
     *                 {@code false}: {@link SharedPreferences.Editor#apply()}
     */
    public boolean clear(boolean isCommit) {
        if (isCommit) {
            return mSP.edit().clear().commit();
        } else {
            mSP.edit().clear().apply();
            return false;
        }
    }

}
