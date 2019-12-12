package com.lzy.fastdevelop;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.bizcore.AppConfig;
import com.lzy.bizcore.helper.NotifiManager;
import com.lzy.bizcore.webview.WebViewFragment;
import com.lzy.libview.activity.BaseActivity;
import com.lzy.libview.activity.BaseFragmentActivity;
import com.lzy.libview.activity.BaseTitleFragmentActivity;
import com.lzy.libview.dialog.CustomAskDialog;
import com.lzy.libview.permission.OnPermissionListener;
import com.lzy.utils.ListUtil;
import com.lzy.utils.statusbar.StatusBarUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements OnPermissionListener {

    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.transparentStatus(this);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions("不授予权限，功能无法使用哦", REQUEST_CODE, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

        Intent intent = new Intent(this, BaseTitleFragmentActivity.class);
        intent.putExtra(BaseFragmentActivity.KEY_FRAGMENT_CLASS_NAME, WebViewFragment.class.getName());
        intent.putExtra(WebViewFragment.KEY_WEB_VIEW_URL, "https://www.baidu.com/");
        NotifiManager.startNotify(this, "我是标题", "我是内容", PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_CANCEL_CURRENT));

        CustomAskDialog dialog = new CustomAskDialog("标题", "内容");
        dialog.show(getSupportFragmentManager(), "ask");

        ImageView iv = findViewById(R.id.imageview);
        Glide.with(this)
                .load("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png")
                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new BlurTransformation(25, 5), new GrayscaleTransformation())))
                .into(iv);

        Logger.i("androidid=" + AppConfig.ANDROID_ID + " imei=" + AppConfig.IMEI +
                " width=" + AppConfig.SCREEN_WIDTH + " height=" + AppConfig.SCREEN_HEIGHT + " channel=" + AppConfig.CHANNEL);


    }

    @Override
    public void onPermGranted(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE) {
            if (ListUtil.isNotEmpty(perms) &&
                    perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Logger.d("读sd卡权限已获取");
            }
            if (ListUtil.isNotEmpty(perms) &&
                    perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Logger.d("写sd卡权限已获取");
            }
        }
    }

    @Override
    public void onPermDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE) {
            if (ListUtil.isNotEmpty(perms) &&
                    perms.contains(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Logger.d("读sd卡权限已拒绝");
            }
            if (ListUtil.isNotEmpty(perms) &&
                    perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Logger.d("写sd卡权限已拒绝");
            }
        }
        if (!gotoSettingActivity(perms, getString(R.string.lib_view_rationale_settings_dialog))) {
            Logger.d("用户没有选中不再提示");
        }
    }

    @Override
    public void onPermSystemSettingResult(int requestCode) {
        if (requestCode == REQUEST_CODE) {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Logger.d("写sd卡权限已在系统设置页面获取");
            } else {
                Logger.d("写sd卡权限已在系统设置页面被拒绝");
            }
        }
    }

}
