package com.lzy.libpermissions.easypermissions.custom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.lzy.utils.IntentUtil;
import com.lzy.utils.system.RomUtil;

/**
 * 小米手机跳转到设置页面
 *
 * @author: cyli8
 * @date: 2017/12/26 17:32
 */

public class XiaoMiPermissionImpl implements IRequestPermission {

    @Override
    public boolean gotoPermissionManager(Context context) {
        Intent intentContact = new Intent();
        ComponentName componentName = ComponentName.unflattenFromString(
                "com.miui.securitycenter/com.miui.permcenter.permissions.PermissionAppsEditorActivity");
        intentContact.setComponent(componentName);
        if (IntentUtil.isActivityCanJump(context, intentContact)) {
            try {
                context.startActivity(intentContact);
                return true;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        if (RomUtil.isMIUI5(context)) {
            try {
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                intent.putExtra("extra_package_uid", pInfo.applicationInfo.uid);
            } catch (PackageManager.NameNotFoundException e) {

            }
            intent.setClassName("miui.intent.action.APP_PERM_EDITOR",
                    "com.miui.securitycenter.permission.AppPermissionsEditor");
        } else if (RomUtil.isMIUI6OrHigher(context)) {
            intent.setClassName("com.miui.securitycenter",
                    "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
        }

        if (IntentUtil.isActivityCanJump(context, intent)) {
            try {
                context.startActivity(intent);
            } catch (Throwable e) {
                return false;
            }
            return true;
        }

        if (RomUtil.isMIUI6OrHigher(context)) {
            Intent intent1 = new Intent();
            ComponentName componentName1 = ComponentName.unflattenFromString(
                    "com.miui.securitycenter/com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent1.setComponent(componentName1);
            intent1.putExtra("extra_pkgname", context.getPackageName());
            if (IntentUtil.isActivityCanJump(context, intent1)) {
                context.startActivity(intent1);
                return true;
            }
        }

        return false;
    }
}
