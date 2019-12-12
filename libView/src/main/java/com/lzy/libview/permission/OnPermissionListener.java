package com.lzy.libview.permission;

import java.util.List;

/**
 * @author: cyli8
 * @date: 2019-12-11 17:38
 */
public interface OnPermissionListener {
    void onPermGranted(int requestCode, List<String> perms);

    void onPermDenied(int requestCode, List<String> perms);

    void onPermSystemSettingResult(int requestCode);
}
