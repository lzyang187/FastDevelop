package com.lzy.libfileprovider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * 适配7.0文件uri获取工具类
 *
 * @author: cyli8
 * @date: 2018/7/23 10:09
 */
public class FileProviderUtil {
    private static final String AUTHORITY_FILE_PROVIDER_SUFFIX = ".fastdevelop.fileProvider";

    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = getUriByNougat(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    private static Uri getUriByNougat(Context context, File file) {
        return FileProvider.getUriForFile(context, context.getPackageName() + AUTHORITY_FILE_PROVIDER_SUFFIX, file);
    }

    public static void setIntentDataAndType(Context context, Intent intent, String type,
                                            File file, boolean writeAble) {
        intent.setDataAndType(getUriForFile(context, file), type);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
    }
}
