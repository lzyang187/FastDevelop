package com.lzy.fastdevelop;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.bizcore.fresco.FrescoHelper;
import com.lzy.libfileprovider.FileProviderUtil;
import com.lzy.libpermissions.easypermissions.EasyPermissions;
import com.lzy.libpermissions.easypermissions.custom.OnPermissionListener;
import com.lzy.libview.activity.ActivityResultTask;
import com.lzy.libview.activity.BaseActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.utils.ListUtil;
import com.lzy.utils.statusbar.StatusBarUtil;
import com.mcxiaoke.packer.helper.PackerNg;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements OnPermissionListener {

    private static final int REQUEST_CODE = 10;
    private static final int REQUEST_CODE_TAKE_PHOTO = 11;

    private File mCurrentPhotoFile;
    private SimpleDraweeView mSdv;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
//            StatusBarUtil.HideStatusAndNavigation(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.transparentStatus(this);
//        StatusBarUtil.setStatusBarColor(this, R.color.lib_view_translucent);
        setContentView(R.layout.activity_main);
//        View statusBarView = findViewById(R.id.statusbar);
//        statusBarView.getLayoutParams().height = StatusBarUtil.getStatusBarHeight(this);
//        statusBarView.setBackgroundColor(Color.TRANSPARENT);

        checkAndRequestPermissions("rationalestr", REQUEST_CODE, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
//        NotifiManager.startNotify(this, "我是标题", "我是内容", null);
//        Intent intent = new Intent(this, BaseTitleFragmentActivity.class);
//        intent.putExtra(BaseFragmentActivity.KEY_FRAGMENT_CLASS_NAME, WebViewFragment.class.getName());
//        intent.putExtra(WebViewFragment.KEY_WEBVIEW_TITLE, "内嵌浏览器");
//        intent.putExtra(WebViewFragment.KEY_WEBVIEW_URL, "https://www.baidu.com/");
//        startActivity(intent);
        String channel = PackerNg.getChannel(this);
        Logger.e("channel:" + channel);
        String url = "https://kyfw.12306.cn/otn/";
        OkGo.<String>get(url)
                .tag(this)
                .retryCount(3)
                .cacheKey("cacheKey")
                .cacheTime(5000)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
//                .headers("", "")
//                .params("", "")
//                .params("", new File(""))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Logger.e("onSuccess\n" + response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });

        mSdv = findViewById(R.id.sdv);
        FrescoHelper.loadBlurImage(mSdv, "http://file.kuyinyun.com/group3/M00/9B/97/rBBGrFkLEKmAIz0HAAGGI-PGKDM212.jpg", 5, 10);
//        FrescoHelper.loadGifResImage(this, mSdv, R.mipmap.ic_dog);

//        takePhotoNoCompress();
    }

    public void takePhotoNoCompress() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA)
                    .format(new Date()) + ".png";
            mCurrentPhotoFile = new File(Environment.getExternalStorageDirectory(), filename);
            Uri fileUri = FileProviderUtil.getUriForFile(this, mCurrentPhotoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePictureIntent, REQUEST_CODE_TAKE_PHOTO, new ActivityResultTask() {
                @Override
                public void execute(int resultCode, Intent data) {
                    if (resultCode == RESULT_OK) {
                        FrescoHelper.loadFileImage(mSdv, mCurrentPhotoFile);
                    }
                }
            });
        }
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
