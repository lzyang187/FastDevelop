package com.lzy.utils.fresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.ByteConstants;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lzy.library_utils.R;
import com.lzy.utils.CommonExecuter;
import com.lzy.utils.StringUtil;
import com.lzy.utils.system.ScreenUtil;
import com.lzy.utils.url.UrlHelper;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author: cyli8
 * @date: 2018/2/23 09:00
 */

public class FrescoHelper {
    static Context appContext;
    static boolean debug = false;
    //    static ScreenDisplayHelper.ScreenParam mScreenParam;
    private static Executor executor = Executors.newSingleThreadExecutor();


    /**
     * 初始化 在Application OnCreate
     *
     * @param context
     */
    public static void initialize(final Context context) {
        appContext = context;
//        mScreenParam = ScreenDisplayHelper.getScreenInfo(appContext);
        Runnable r = new Runnable() {
            @Override
            public void run() {
//                ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
//                    @Override
//                    public int getNextScanNumberToDecode(int scanNumber) {
//                        return scanNumber + 2;
//                    }
//
//                    public QualityInfo getQualityInfo(int scanNumber) {
//                        boolean isGoodEnough = (scanNumber >= 100);
//                        return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
//                    }
//                };


//                ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(context, builder.build()).
//                        setDownsampleEnabled(true)/*.setNetworkFetcher(new HttpUrlConnectionNetworkFetcher())*/.
//                        setProgressiveJpegConfig(new SimpleProgressiveJpegConfig()).build();

                // FIXME: 2018/2/25 完善网络请求引擎
//                OkHttpClient.Builder builder = new OkHttpClient.Builder();
//                builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
//                builder.connectTimeout(HttpConst.CONNECT_TIME_OUT, TimeUnit.SECONDS);
//                builder.readTimeout(HttpConst.READ_TIME_OUT, TimeUnit.SECONDS);
//                builder.writeTimeout(HttpConst.WRITE_TIME_OUT, TimeUnit.SECONDS);
//                ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context).
//                        setDownsampleEnabled(true).setNetworkFetcher(
////                        new HttpUrlConnectionNetworkFetcher()
//                        new OkHttpNetworkFetcher(builder.build()))
////                        .setImageCacheStatsTracker(new KuyinImageCacheStatsTracker())
//                /*.setBitmapsConfig(Bitmap.Config.ARGB_4444)*/.build();
//                Fresco.initialize(context, /*getConfigureCaches(context)*/config);
            }
        };
        CommonExecuter.run(r);
    }

    private static int MAX_MEM = 100 * ByteConstants.MB;

//    private static ImagePipelineConfig getConfigureCaches(Context context) {
//        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
//                MAX_MEM,// 内存缓存中总图片的最大大小,以字节为单位。
//                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
//                MAX_MEM,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
//                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
//                Integer.MAX_VALUE);// 内存缓存中单个图片的最大大小。
//
//        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
//            @Override
//            public MemoryCacheParams get() {
//                return bitmapCacheParams;
//            }
//        };
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.hostnameVerifier(HttpsUtils.UnSafeHostnameVerifier);
//        builder.connectTimeout(HttpConst.CONNECT_TIME_OUT, TimeUnit.SECONDS);
//        builder.readTimeout(HttpConst.READ_TIME_OUT, TimeUnit.SECONDS);
//        builder.writeTimeout(HttpConst.WRITE_TIME_OUT, TimeUnit.SECONDS);
//
//        return ImagePipelineConfig.newBuilder(context).
//                setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams).
//                setDownsampleEnabled(true).
//                setNetworkFetcher(/*new HttpUrlConnectionNetworkFetcher()*/new OkHttpNetworkFetcher(builder.build())).
//                build();
//    }

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
            final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
            if (null != tagUri && TextUtils.equals(url, tagUri.toString())) {
                // 不需要再次加载
                return;
            }
            Uri uri = Uri.parse(url);
            sdv.setImageURI(uri, callerContext);
            sdv.setTag(R.id.ic__uri, uri);
        } else {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.ic__uri, null);
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
            sdv.setTag(R.id.ic__uri, Uri.parse(highResUrl));
        } else {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.ic__uri, null);
        }
    }

    /**
     * 加载本地Res目录下图片 注意是图片
     *
     * @param sdv
     * @param resID ＜=0 则用占位图
     */
    public static void loadResImage(SimpleDraweeView sdv, int resID) {
        if (sdv == null) {
            return;
        }
        if (resID <= 0) {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.ic__uri, null);
            return;
        }
        Uri uri = Uri.parse("res://" + appContext.getPackageName() + "/" + resID);
        final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
        if (null != tagUri && null != uri && uri.toString().equals(tagUri.toString())) {
            // 不需要再次加载
            return;
        }
        sdv.setImageURI(uri);
        sdv.setTag(R.id.ic__uri, uri);
    }

    /**
     * 加载本地Res目录下图片 注意是图片
     *
     * @param sdv
     * @param ＜=0 则用占位图
     */
    public static void loadGifFileImage(SimpleDraweeView sdv, File file) {
        if (sdv == null) {
            return;
        }
        if (file == null || !file.exists()) {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.ic__uri, null);
            return;
        }
        Uri uri = Uri.parse("file://" + file.getAbsolutePath());
        final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
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

        sdv.setTag(R.id.ic__uri, uri);
    }


    /**
     * 加载本地Res目录下图片 注意是图片
     *
     * @param sdv
     * @param resID ＜=0 则用占位图
     */
    public static void loadGifResImage(SimpleDraweeView sdv, int resID) {
        if (sdv == null) {
            return;
        }
        if (resID <= 0) {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.ic__uri, null);
            return;
        }
        Uri uri = Uri.parse("res://" + appContext.getPackageName() + "/" + resID);
        final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
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

        sdv.setTag(R.id.ic__uri, uri);
    }

    /**
     * 加载本地文件图片
     *
     * @param sdv
     * @param
     */
    public static void loadFileImage(SimpleDraweeView sdv, String path) {
        Uri uri = Uri.parse("file://" + path);
        final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
        if (null != tagUri && null != uri && uri.toString().equals(tagUri.toString())) {
            // 不需要再次加载
            return;
        }
        sdv.setImageURI(uri);
        sdv.setTag(R.id.ic__uri, uri);
    }

    /**
     * 加载本地文件图片
     *
     * @param sdv
     * @param
     */
    public static void loadFileImage(SimpleDraweeView sdv, String path, int width,
                                     int height) {
        Uri uri = Uri.parse("file://" + path);
        final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
        if (null != tagUri && null != uri && uri.toString().equals(tagUri.toString())) {
            // 不需要再次加载
            return;
        }
        DraweeController controller;
        ImageRequest request =
                ImageRequestBuilder.newBuilderWithSource(uri).setResizeOptions(new ResizeOptions(width, height)).build();
        controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(sdv.getController())
                .setControllerListener(defaultCtrlListener).build();
        sdv.setController(controller);
        sdv.setTag(R.id.ic__uri, uri);
    }

    public static void loadProgressiveImg(SimpleDraweeView sdv, String url) {
        if (UrlHelper.isFrescoAllowUrl(url)) {
            final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
            if (null != tagUri && null != url && url.equals(tagUri.toString())) {
                // 不需要再次加载
                return;
            }
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
                    .build();
            PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .build();
            sdv.setController(controller);
            sdv.setTag(R.id.ic__uri, uri);
        } else {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.ic__uri, null);
        }
    }

    /**
     * 预加载到磁盘缓存
     *
     * @param url
     */
    public static boolean prefetchToDiskCache(String url, Object callerContext, DataSubscriber<Void> bbds) {
        if (UrlHelper.isUrlValid(url)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<Void> dataSource = imagePipeline.prefetchToDiskCache(ImageRequest.fromUri(url), callerContext);
            if (dataSource == null) {
                return false;
            }
            if (null != bbds) {
                dataSource.subscribe(bbds, executor);
            }
            return true;
        }
        return false;
    }

    public static boolean prefetchToDiskCache(String url, Object callerContext) {
        return prefetchToDiskCache(url, callerContext, null);
    }

    /**
     * 预加载到内存缓存
     *
     * @param url
     */
    public static void prefetchToBitmapCache(String url, Object callerContext, DataSubscriber<Void> bbds) {
        if (UrlHelper.isUrlValid(url)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<Void> dataSource = imagePipeline.prefetchToBitmapCache(ImageRequest.fromUri(url), callerContext);
            if (null != dataSource && null != bbds) {
                dataSource.subscribe(bbds, executor);
            }
        }
    }

    public static DataSource<CloseableReference<CloseableImage>> fetchDecodedImage(String url, Object callerContext) {
        if (UrlHelper.isUrlValid(url)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            return imagePipeline.fetchDecodedImage(ImageRequest.fromUri(url), callerContext);
        } else {
            return null;
        }
    }

    public static boolean isInDiskCache(String url) {
        if (UrlHelper.isUrlValid(url)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<Boolean> dataSource = imagePipeline.isInDiskCache(ImageRequest.fromUri(url));
            if (dataSource != null) {
                try {
                    return dataSource.hasResult() && dataSource.isFinished();
                } finally {
                    dataSource.close();
                }
            }
        }
        return false;
    }

    /**
     * 判断url在内存中是否有缓存
     */
    public static boolean isInMenCache(String url) {
        if (UrlHelper.isUrlValid(url)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            return imagePipeline.isInBitmapMemoryCache(ImageRequest.fromUri(url));
        }
        return false;
    }

    /**
     * 从缓存获取bitmap 生命周期不能超过其方法 如要 请copy一份bitmap
     *
     * @param url
     * @param bitmapData
     */
    public static void fetchImageFromBitmapCache(String url, Object callerContext, BitmapData bitmapData) {
        DataSource<CloseableReference<CloseableImage>> dataSource = fetchImageFromBitmapCache(url, callerContext);
        if (dataSource == null) {
            return;
        }
        CloseableReference<CloseableImage> closeableImageRef = dataSource.getResult();
        Bitmap bitmap = null;
        if (closeableImageRef != null &&
                closeableImageRef.get() instanceof CloseableBitmap) {
            bitmap = ((CloseableBitmap) closeableImageRef.get()).getUnderlyingBitmap();
        }
        try {
            if (bitmapData != null) {
                bitmapData.onNewResultImpl(bitmap);
            }
        } finally {
            dataSource.close();
            CloseableReference.closeSafely(closeableImageRef);
        }
    }

    public interface BitmapData {
        void onNewResultImpl(@Nullable Bitmap bitmap);
    }

    /**
     * 从磁盘获取 bitmap 生命周期不能超过其方法 如要 请copy一份bitmap
     *
     * @param url
     * @param bbds
     */
    public static DataSource<?> fetchDecodedImage(String url, Object callerContext, BaseBitmapDataSubscriber bbds) {
        DataSource<CloseableReference<CloseableImage>> dataSource = fetchDecodedImage(url, callerContext);
        if (dataSource == null) {
            return null;
        }
        dataSource.subscribe(bbds, executor);
        return dataSource;
    }

    public static DataSource<CloseableReference<CloseableImage>> fetchImageFromBitmapCache(String url, Object callerContext) {
        if (UrlHelper.isUrlValid(url)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            return imagePipeline.fetchImageFromBitmapCache(ImageRequest.fromUri(url), callerContext);
        } else {
            return null;
        }
    }


    public static void loadImage(SimpleDraweeView sdv, String url, ControllerListener ctrlListener) {
        if (sdv == null) {
            return;
        }
        if (UrlHelper.isFrescoAllowUrl(url)) {
            final Uri tagUri = (Uri) sdv.getTag(R.id.ic__uri);
            if (null != tagUri && null != url && url.toString().equals(tagUri.toString())) {
                // 不需要再次加载
                return;
            }
            Uri uri = Uri.parse(url);
            DraweeController controller;
            controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setOldController(sdv.getController())
                    .setControllerListener(ctrlListener).build();
            sdv.setController(controller);

            sdv.setTag(R.id.ic__uri, uri);
        } else {
            sdv.setImageURI((Uri) null);
            sdv.setTag(R.id.ic__uri, null);
        }
    }

    public static ControllerListener defaultCtrlListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFinalImageSet(
                String id,
                @Nullable ImageInfo imageInfo,
                @Nullable Animatable anim) {
            if (imageInfo == null) {
                return;
            }
//            if (Logger.logFlag) {
//                QualityInfo qualityInfo = imageInfo.getQualityInfo();
//                Logger.log().d(String.format(Locale.getDefault(), "Final image received! " +
//                                "Size %d x %d Quality level %d, good enough: %s, full quality: %s",
//                        imageInfo.getWidth(),
//                        imageInfo.getHeight(),
//                        qualityInfo.getQuality(),
//                        qualityInfo.isOfGoodEnoughQuality(),
//                        qualityInfo.isOfFullQuality()));
//            }
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
//            FLog.d("Intermediate image received");
//            if (Logger.logFlag) {
//                Logger.log().e("Intermediate image received:" + id);
//            }
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
//            FLog.e(getClass(), throwable, "Error loading %s", id);
//            if (Logger.logFlag) {
//                Logger.log().e(throwable.toString() + String.format("Error loading %s", id));
//            }
        }
    };

    /**
     * 反初始化 在Application onTerminate
     */
    public static void shutDown() {
        Fresco.shutDown();
        appContext = null;
    }

    /**
     * 清除图片缓存
     */
    public static void cleanMemCache() {
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
    public static long getCacheSize() {
        File cacheDir = appContext.getApplicationContext().getCacheDir();
        File imageCacheDir = new File(cacheDir, "image_cache");
        return getDirSize(imageCacheDir);
    }

    /**
     * 递归获取目录下文件大小
     *
     * @param file
     * @return 字节大小
     */
    private static long getDirSize(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] children = file.listFiles();
                long size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {
                return file.length();
            }
        } else {
            return 0;
        }
    }

    public interface OnLoadImageListener {
        void onLoadImageComplete(int loadId, @Nullable Bitmap bitmap);

        void onLoadImageFailed(int loadId, String path, int errCode);
    }

    /**
     * 异步加载本地图片
     *
     * @param context
     * @param path
     * @param width
     * @param height
     */
    public static void loadImageFromSDCard(Context context, final int loadId, final OnLoadImageListener listener,
                                           final String path, final int width, final int height) {
        DataSubscriber dataSubscriber = new BaseDataSubscriber<CloseableReference<CloseableBitmap>>() {
            @Override
            public void onNewResultImpl(DataSource<CloseableReference<CloseableBitmap>> dataSource) {
                if (!dataSource.isFinished()) {
                    return;
                }
                CloseableReference<CloseableBitmap> imageReference = dataSource.getResult();
                if (imageReference != null) {
                    final CloseableReference<CloseableBitmap> closeableReference = imageReference.clone();
                    try {
                        CloseableBitmap closeableBitmap = closeableReference.get();
                        Bitmap bitmap = closeableBitmap.getUnderlyingBitmap();
                        if (bitmap != null && !bitmap.isRecycled()) {
                            if (null != listener) {
                                listener.onLoadImageComplete(loadId, bitmap);
                            }
                        }
                    } finally {
                        imageReference.close();
                        closeableReference.close();
                    }
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
                Throwable throwable = dataSource.getFailureCause();
                // handle failure
                if (null != listener) {
                    listener.onLoadImageFailed(loadId, path, -1);
                }
            }
        };

        Uri uri = Uri.parse("file://" + path);
        getBitmap(context, uri, width, height, dataSubscriber);
    }

    /**
     * @param context
     * @param uri
     * @param width
     * @param height
     * @param dataSubscriber
     */
    private static void getBitmap(Context context, Uri uri, int width, int height, DataSubscriber dataSubscriber) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(uri);
        //
        if (width > 0 && height > 0) {
            builder.setResizeOptions(new ResizeOptions(width, height));
        }
        ImageRequest request = builder.build();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(request, context);
        dataSource.subscribe(dataSubscriber, UiThreadImmediateExecutorService.getInstance());
    }

    public static File getCacheFile(String url) {
        if (!StringUtil.isEmpty(url)) {
            ImageRequest imageRequest = ImageRequest.fromUri(url);
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance()
                    .getEncodedCacheKey(imageRequest, null);
            BinaryResource resource = ImagePipelineFactory.getInstance()
                    .getMainFileCache().getResource(cacheKey);
            if (null != resource) {
                File file = ((FileBinaryResource) resource).getFile();
                return file;
            }
        }
        return null;
    }

    public static final String buildResizeImageUrl(String url, int w, int h) {
        if (null == url || w <= 0 || h <= 0) {
            return url;
        }
        StringBuilder sb = new StringBuilder(url);
        sb.append("?w=").append(w).append("&h=").append(h);
        return sb.toString();
    }

    public static final String formatThumbUrlForRingshow(String url) {
//        if (null == mScreenParam) {
//            mScreenParam = ScreenDisplayHelper.getScreenInfo(appContext);
//        }
        String thumbUrl = url;
        int thumbSize = ScreenUtil.getScreenWidth(appContext) * 496 / 1080;
        thumbUrl += "?w=" + thumbSize + "&h=" + thumbSize;
        return thumbUrl;
    }

    /**
     * 通过改变url的宽高参数加载图片
     *
     * @param width  请求图片url的宽
     * @param height 请求图片url的高
     */
    public static void loadResizeImage(SimpleDraweeView sdv, String url, int width, int height) {
        if (!StringUtil.isEmpty(url)) {
//            if (width > 0 && height > 0) {
//                url = url + "?s=" + width + "," + height;
//            }
//            FrescoHelper.loadImage(sdv, url);
            FrescoHelper.loadImage(sdv, url, width, height);
        }
    }

    /**
     * 以高斯模糊显示。
     *
     * @param draweeView View
     * @param url        url
     * @param iterations 迭代次数，越大越模糊。
     * @param blurRadius 模糊图半径，必须大于0，越大越模糊。
     */
    public static void showUrlBlur(SimpleDraweeView draweeView, String url, int iterations, int blurRadius) {
        try {
            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setPostprocessor(new IterativeBoxBlurPostProcessor(iterations, blurRadius))
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
}
