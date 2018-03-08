package com.lzy.utils.file;

import android.os.Environment;

import java.io.File;

/**
 * 操作文件工具类
 *
 * @author: cyli8
 * @date: 2018/2/11 16:15
 */

public class FileUtil {
    public static boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists() || !file.isDirectory()) {
            return flag;
        }

        String[] tempList = file.list();
        if (tempList == null || tempList.length == 0) {
            flag = true;
            return flag;
        }
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);// 再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    // 删除文件中的所有文件
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            File myFilePath = new File(folderPath);
            myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除某个指定的文件
     */
    public static void delFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 确保目录存在，不存在则创建
     *
     * @param dir
     * @return
     */
    public static boolean ensureDirExist(String dir) {
        File f = new File(dir);
        if (f.exists()) {
            if (!f.isDirectory()) {
                f.delete();
            } else {
                return true;
            }
        }
        boolean b = f.mkdirs();
        return b;
    }
}
