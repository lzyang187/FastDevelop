package com.lzy.libbasefun.lru;

import android.content.Context;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/**
 * @author: cyli8
 * @date: 2019-09-29 09:27
 */
public class DiskLruCacheMgr {
    private static final String CACHE_FILE_NAME = "lruCache";
    private static final int APP_VERSION = 1;
    private static final int EACH_ENTRY_VALUE_COUNT = 1;
    private static final int MAX_SIZE = 100 * 1024 * 1024;//100M
    private static final int ENTRY_INDEX = 0;

    private static DiskLruCacheMgr mInstance;
    private DiskLruCache mDiskLruCache;

    public void init(Context context) {
        File cacheFile = new File(context.getExternalCacheDir(), CACHE_FILE_NAME);
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        mDiskLruCache = DiskLruCache.create(FileSystem.SYSTEM, cacheFile, APP_VERSION, EACH_ENTRY_VALUE_COUNT, MAX_SIZE);
    }

    private DiskLruCacheMgr() {

    }

    public static DiskLruCacheMgr getInstance() {
        if (mInstance == null) {
            synchronized (DiskLruCacheMgr.class) {
                if (mInstance == null) {
                    mInstance = new DiskLruCacheMgr();
                }
            }
        }
        return mInstance;
    }

    public boolean put(String key, Object obj) {
        if (TextUtils.isEmpty(key) || obj == null) {
            throw new NullPointerException("key or obj 为空");
        }
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            BufferedSink bufferedSink = Okio.buffer(editor.newSink(ENTRY_INDEX));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            bufferedSink.write(baos.toByteArray());
            bufferedSink.flush();
            editor.commit();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object get(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot == null) {
                return null;
            }
            Source source = snapshot.getSource(ENTRY_INDEX);
            BufferedSource bufferedSource = Okio.buffer(source);
            InputStream inputStream = bufferedSource.inputStream();
            ObjectInputStream ois = new ObjectInputStream(inputStream);
            Object object = ois.readObject();
            bufferedSource.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean remove(String key) {
        try {
            return mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
