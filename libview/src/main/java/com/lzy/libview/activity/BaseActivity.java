package com.lzy.libview.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.lzy.libview.R;
import com.lzy.libview.dialog.CustomWaitingDialog;

/**
 * 基础activity
 *
 * @author: cyli8
 * @date: 2018/2/28 10:44
 */

public class BaseActivity extends AppCompatActivity {

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
}
