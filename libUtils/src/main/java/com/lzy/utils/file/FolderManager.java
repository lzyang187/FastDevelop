package com.lzy.utils.file;

import android.os.Environment;

import java.io.File;

/**
 * @author: cyli8
 * @date: 2018/2/11 16:07
 */

public class FolderManager {
    private static final String DEFAULT_BASE_DIR = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "fastdevelop"
            + File.separator;
}
