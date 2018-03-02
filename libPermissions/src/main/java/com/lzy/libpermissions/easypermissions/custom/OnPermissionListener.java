package com.lzy.libpermissions.easypermissions.custom;

import java.util.List;

/**
 * 增加从设置页面回来的回调方法
 *
 * @author: cyli8
 * @date: 2018/3/2 10:05
 */

public interface OnPermissionListener {
    void onPermGranted(int requestCode, List<String> perms);

    void onPermDenied(int requestCode, List<String> perms);

    /**
     * 从系统设置页面返回的结果回调
     */
    void onPermSystemSettingResult(int requestCode);
}
