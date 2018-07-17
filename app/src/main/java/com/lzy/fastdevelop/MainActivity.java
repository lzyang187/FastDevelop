package com.lzy.fastdevelop;

import android.Manifest;
import android.os.Bundle;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lzy.bizcore.fresco.FrescoHelper;
import com.lzy.libhttp.MyHttpClient;
import com.lzy.libpermissions.easypermissions.EasyPermissions;
import com.lzy.libpermissions.easypermissions.custom.OnPermissionListener;
import com.lzy.libview.activity.BaseActivity;
import com.lzy.utils.ListUtil;
import com.mcxiaoke.packer.helper.PackerNg;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

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
        String channel = PackerNg.getChannel(this);
        Logger.e("channel:" + channel);
        Request.Builder builder = new Request.Builder();
        String url = "https://kyfw.12306.cn/otn/";
        Request request = builder.get().url(url).build();
        MyHttpClient.getInstance().getHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("https请求失败：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Logger.e("https请求成功：" + response.body().string());
            }
        });
        SimpleDraweeView sdv = findViewById(R.id.sdv);
//        FrescoHelper.loadBlurImage(sdv, "http://file.kuyinyun.com/group3/M00/9B/97/rBBGrFkLEKmAIz0HAAGGI-PGKDM212.jpg", 5, 10);
        FrescoHelper.loadGifResImage(this, sdv, R.mipmap.ic_dog);
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
