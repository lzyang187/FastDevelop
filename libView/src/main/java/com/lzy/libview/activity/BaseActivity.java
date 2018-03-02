package com.lzy.libview.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.lzy.libpermissions.easypermissions.AppSettingsDialog;
import com.lzy.libpermissions.easypermissions.EasyPermissions;
import com.lzy.libpermissions.easypermissions.PermissionRequest;
import com.lzy.libpermissions.easypermissions.custom.OnPermissionListener;
import com.lzy.libview.R;
import com.lzy.libview.dialog.CustomWaitingDialog;

import java.util.List;

/**
 * 基础activity,所有业务Activity均继承于它
 * 加入运行时权限申请的接口 2018/3/2 10:28
 *
 * @author: cyli8
 * @date: 2018/2/28 10:44
 */

public class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public static final String L_FINISH_ANIMATION_ID = "l_finish_anim_id"; // 当前界面
    public static final String R_FINISH_ANIMATION_ID = "r_finish_anim_id"; // 下级界面
    private SparseArray<ActivityResultTask> taskMap = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState,
                         @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public void finish() {
        super.finish();
//		System.gc();
        int lanimId = getIntent().getIntExtra(L_FINISH_ANIMATION_ID, -1);
        int ranimId = getIntent().getIntExtra(R_FINISH_ANIMATION_ID, -1);
        if (lanimId != -1 || ranimId != -1) {
            if (lanimId == -1) {
                lanimId = R.anim.lib_view_anim_none;
            }
            if (ranimId == -1) {
                ranimId = R.anim.lib_view_anim_none;
            }
            overridePendingTransition(lanimId, ranimId);
        }
    }

    final public void startActivity(final Intent intent, final int go,
                                    final int back) {
        intent.putExtra(R_FINISH_ANIMATION_ID, back);
        super.startActivity(intent);
        if (go != -1) {
            overridePendingTransition(go, R.anim.lib_view_anim_none);
        }
    }

    final public void startActivityForResult(final Intent intent,
                                             final int requestCode, final int go, final int back) {
        intent.putExtra(R_FINISH_ANIMATION_ID, back);
        startActivityForResult(intent, requestCode);
        if (go != -1) {
            overridePendingTransition(go, R.anim.lib_view_anim_none);
        }
    }

    final public void startActivityForResult(final Intent intent, final int requestCode, final int go, final int back, final ActivityResultTask task) {
        intent.putExtra(R_FINISH_ANIMATION_ID, back);
        if (null == taskMap) {
            taskMap = new SparseArray<>();
        }
        taskMap.put(requestCode, task);
        startActivityForResult(intent, requestCode);
        if (go != -1) {
            overridePendingTransition(go, R.anim.lib_view_anim_none);
        }
    }

    public final void startActivityForResult(final Intent intent, final int requestCode,
                                             ActivityResultTask task) {
        if (null == taskMap) {
            taskMap = new SparseArray<>(1);
        }
        taskMap.put(requestCode, task);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (taskMap != null) {
            ActivityResultTask task = taskMap.get(requestCode);
            if (null != task) {
                task.execute(resultCode, data);
            }
            taskMap.remove(requestCode);
        }
        //运行时权限的结果回调
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (mPermissionListener != null) {
                mPermissionListener.onPermSystemSettingResult(mRequestPermCode);
            }
        }
    }

    @Override
    public Resources getResources() {
        //重写getResources方法，保证客户端字体不随着系统字体变化
        Resources res = super.getResources();
        try {
            Configuration config = new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config, res.getDisplayMetrics());
        } catch (Exception e) {
        }
        return res;
    }


    protected CustomWaitingDialog mWaitingDialog;

    protected final void showWaitingDialog(String tip) {
        if (null == mWaitingDialog) {
            mWaitingDialog = new CustomWaitingDialog(this, tip);
        }
        mWaitingDialog.show();
    }

    protected final void dismissWaitingDialog() {
        if (null != mWaitingDialog && mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
            mWaitingDialog = null;
        }
    }

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

    public boolean gotoSettingActivity(List<String> perms, String rationale, String positiveStr, String negativeStr) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            //用户选中了不再提示
            new AppSettingsDialog.Builder(this).build(rationale, positiveStr,
                    negativeStr).show();
            return true;
        }
        return false;
    }
}
