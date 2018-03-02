package com.lzy.libpermissions.easypermissions.custom;

import android.content.Context;

/**
 * 针对不同手机适配跳转到设置页面
 *
 * @author: cyli8
 * @date: 2017/12/26 17:13
 */

public interface IRequestPermission {

    boolean gotoPermissionManager(Context context);
}
