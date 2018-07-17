package com.lzy.bizcore.fresco;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lzy.bizcore.R;
import com.lzy.utils.file.FileUtil;
import com.lzy.utils.url.UrlHelper;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * 图片加载库
 *
 * @author: cyli8
 * @date: 2018/7/17 10:30
 */
public class FrescoHelper {

    /**
     * 初始化 在Application OnCreate
     */
    public static void init(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(context, okHttpClient)
                .build();
        Fresco.initialize(context, config);
    }

    public static void loadImage(SimpleDraweeView sdv, String url, int width, int height) {
        if (null == sdv) {
            return;
        }
        if (UrlHelper.isFrescoAllowUrl(url)) {
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(sdv.getController())
                    .setImageRequest(request)
                    .build();
            sdv.setController(controller);
        }
    }

    public static void loadImage(SimpleDraweeView sdv, String url) {
        loadImage(sdv, url, (Object) null);
    }

    public static void loadImage(SimpleDraweeView sdv, String url, Object callerContext) {
        if (sdv == null) {
            return;
        }
        if (UrlHelper.isFrescoAllowUrl(url)) {
            final Uri tagUri = (Uri) sdv.getTag(R.id.biz_core_ic__uri);
            if (null != tagUri && TextUtils.equals(url, tagUri.toString())) {
                // 不需要再次加载
                return;
            }
            Uri uri = Uri.parse(url);
            if (url.endsWith("gif")) {
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(ImageRequest.fromUri(url))
                        .setAutoPlayAnimations(true)
                        .setOldController(sdv.getController())
                        .build();
                sdv.setController(controller);
            } else {
                sdv.setImageURI(uri, callerContext);
            }
            sdv.setTag(R.id.biz_core_ic__uri, uri);
        } else {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.biz_core_ic__uri, null);
        }
    }

    public static void loadImage(SimpleDraweeView sdv, String lowResUrl, String highResUrl) {
        if (sdv == null) {
            return;
        }
        if (UrlHelper.isUrlValid(highResUrl)) {
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(lowResUrl))
                    .setImageRequest(ImageRequest.fromUri(highResUrl))
                    .setOldController(sdv.getController())
                    .build();
            sdv.setController(controller);
            sdv.setTag(R.id.biz_core_ic__uri, Uri.parse(highResUrl));
        } else {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.biz_core_ic__uri, null);
        }
    }

    /**
     * 加载Res目录下图片 注意是图片
     */
    public static void loadResImage(Context context, SimpleDraweeView sdv, int resID) {
        if (sdv == null) {
            return;
        }
        if (resID <= 0) {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.biz_core_ic__uri, null);
            return;
        }
        Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + resID);
        final Uri tagUri = (Uri) sdv.getTag(R.id.biz_core_ic__uri);
        if (null != tagUri && null != uri && uri.toString().equals(tagUri.toString())) {
            // 不需要再次加载
            return;
        }
        sdv.setImageURI(uri);
        sdv.setTag(R.id.biz_core_ic__uri, uri);
    }

    /**
     * 加载文件图片
     */
    public static void loadFileImage(SimpleDraweeView sdv, File file) {
        if (sdv == null) {
            return;
        }
        if (file == null || !file.exists()) {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.biz_core_ic__uri, null);
            return;
        }
        Uri uri = Uri.fromFile(file);
        final Uri tagUri = (Uri) sdv.getTag(R.id.biz_core_ic__uri);
        if (null != tagUri && null != uri && uri.toString().equals(tagUri.toString())) {
            // 不需要再次加载
            return;
        }
        sdv.setImageURI(uri);
        sdv.setTag(R.id.biz_core_ic__uri, uri);
    }

    /**
     * 加载Res目录下的gif图片 注意是图片
     */
    public static void loadGifResImage(Context context, SimpleDraweeView sdv, int resID) {
        if (sdv == null) {
            return;
        }
        if (resID <= 0) {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.biz_core_ic__uri, null);
            return;
        }
        Uri uri = Uri.parse("res://" + context.getPackageName() + "/" + resID);
        final Uri tagUri = (Uri) sdv.getTag(R.id.biz_core_ic__uri);
        if (null != tagUri && null != uri && uri.toString().equals(tagUri.toString())) {
            // 不需要再次加载
            return;
        }
        DraweeController controller;
        controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(sdv.getController())
                .setAutoPlayAnimations(true).build();
        sdv.setController(controller);
        sdv.setTag(R.id.biz_core_ic__uri, uri);
    }

    /**
     * 加载文件下的gif图片 注意是图片
     */
    public static void loadGifFileImage(SimpleDraweeView sdv, File file) {
        if (sdv == null) {
            return;
        }
        if (file == null || !file.exists()) {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.biz_core_ic__uri, null);
            return;
        }
        Uri uri = Uri.fromFile(file);
        final Uri tagUri = (Uri) sdv.getTag(R.id.biz_core_ic__uri);
        if (null != tagUri && null != uri && uri.toString().equals(tagUri.toString())) {
            // 不需要再次加载
            return;
        }

        DraweeController controller;
        controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(sdv.getController())
                .setAutoPlayAnimations(true).build();
        sdv.setController(controller);
        sdv.setTag(R.id.biz_core_ic__uri, uri);
    }

    /**
     * 以高斯模糊显示
     *
     * @param draweeView View
     * @param url        url
     * @param iterations 迭代次数，越大越模糊。
     * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
     */
    public static void loadBlurImage(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
        try {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))// iterations, blurRadius
                    .build();
            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(draweeView.getController())
                    .setImageRequest(request)
                    .build();
            draweeView.setController(controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片缓存
     */
    public static void cleanMemoryCache() {
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    /**
     * 清除缓存 包括内存缓存 磁盘缓存
     */
    public static void clearCaches() {
        Fresco.getImagePipeline().clearCaches();
    }

    /**
     * 获取cache/image_cache大小
     * 参考ImagePipelineConfig getDefaultMainDiskCacheConfig方法
     *
     * @return 字节大小
     */
    public static long getCacheSize(Context context) {
        File cacheDir = context.getApplicationContext().getCacheDir();
        File imageCacheDir = new File(cacheDir, "image_cache");
        return FileUtil.getDirSize(imageCacheDir);
    }

}
