package com.lzy.utils.system;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * @author: cyli8
 * @date: 2018/2/11 17:25
 */

public class ClipboardUtil {
    public static void copyText(Context context, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            clipboardManager.setPrimaryClip(ClipData.newPlainText("simple text copy", text));
        }
    }

    public static String getText(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪贴板里是否有内容
        if (clipboardManager == null || !clipboardManager.hasPrimaryClip()) {
            return null;
        }
        ClipData clip = clipboardManager.getPrimaryClip();
        return clip.getItemAt(0).getText().toString();
    }
}
