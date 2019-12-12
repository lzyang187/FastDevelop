package com.lzy.libview.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.lzy.libview.R;
import com.lzy.libview.permission.OnPermissionListener;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

/**
 * 基础activity,所有业务Activity均继承于它
 * 加入运行时权限申请的接口 2018/3/2 10:28
 *
 * @author: cyli8
 * @date: 2018/2/28 10:44
 */

public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    //运行时权限部分
    private OnPermissionListener mPermissionListener;
    private int mRequestPermCode;

    /**
     * 检查并且申请运行时权限
     *
     * @param rationaleStr 用户拒绝后的声明需要权限的提示语，为空则用户拒绝后不再进行后续权限申请
     * @param requestCode  请求code
     * @param listener     回调监听
     * @param permissions  权限组
     */
    public void checkAndRequestPermissions(String rationaleStr, int requestCode,
                                           OnPermissionListener listener, String... permissions) {
        checkAndRequestPermissions(rationaleStr, R.string.lib_view_ok,
                R.string.lib_view_cancel, requestCode, listener, permissions);
    }

    public void checkAndRequestPermissions(String rationaleStr, int positiveId,
                                           int negativeId, int requestCode,
                                           OnPermissionListener listener, String... permissions) {
        mRequestPermCode = requestCode;
        mPermissionListener = listener;
        PermissionRequest request = new PermissionRequest.Builder(this, requestCode, permissions)
                .setRationale(rationaleStr)
                .setPositiveButtonText(positiveId)
                .setNegativeButtonText(negativeId)
                .build();
        EasyPermissions.requestPermissions(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,
                this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (mPermissionListener != null) {
            mPermissionListener.onPermGranted(requestCode, perms);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (mPermissionListener != null) {
            mPermissionListener.onPermDenied(requestCode, perms);
        }
    }

    public boolean gotoSettingActivity(List<String> perms, String rationale) {
        return gotoSettingActivity(perms, rationale, getString(R.string.lib_view_ok),
                getString(R.string.lib_view_cancel));
    }

    private boolean gotoSettingActivity(List<String> perms, String rationale, String positiveStr, String negativeStr) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //用户选中了不再提示
            new AppSettingsDialog.Builder(this)
                    .setTitle(R.string.lib_view_title_settings_dialog)
                    .setRationale(rationale)
                    .setPositiveButton(positiveStr)
                    .setNegativeButton(negativeStr)
                    .build()
                    .show();
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //运行时权限的结果回调
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (mPermissionListener != null) {
                mPermissionListener.onPermSystemSettingResult(mRequestPermCode);
            }
        }
    }

}
