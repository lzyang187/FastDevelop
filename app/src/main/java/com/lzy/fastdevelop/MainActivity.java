package com.lzy.fastdevelop;

import android.Manifest;
import android.os.Bundle;

import com.lzy.libpermissions.easypermissions.EasyPermissions;
import com.lzy.libpermissions.easypermissions.custom.OnPermissionListener;
import com.lzy.libview.activity.BaseActivity;
import com.lzy.utils.ListUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

public class MainActivity extends BaseActivity implements OnPermissionListener {

    private static final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkAndRequestPermissions("rationalestr", REQUEST_CODE, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
//        NotifiManager.startNotify(this, "我是标题", "我是内容", null);
//        Intent intent = new Intent(this, BaseTitleFragmentActivity.class);
//        intent.putExtra(BaseFragmentActivity.KEY_FRAGMENT_CLASS_NAME, WebViewFragment.class.getName());
//        intent.putExtra(WebViewFragment.KEY_WEBVIEW_TITLE, "内嵌浏览器");
//        intent.putExtra(WebViewFragment.KEY_WEBVIEW_URL, "https://www.baidu.com/");
//        startActivity(intent);
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
        if (!gotoSettingActivity(perms, "rationalestr")) {
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
