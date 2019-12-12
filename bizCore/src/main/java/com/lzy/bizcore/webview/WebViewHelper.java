package com.lzy.bizcore.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lzy.bizcore.AppConfig;
import com.lzy.utils.IntentUtil;
import com.lzy.utils.StringUtil;
import com.orhanobut.logger.Logger;


/**
 * 封装WebView
 *
 * @author: cyli8
 * @date: 2018/3/26 14:39
 */

public class WebViewHelper {

    private static final String TAG = "WebViewHelper";

    public interface OnWebViewListener {
        void onPageStarted(WebView view, String url, Bitmap favicon);

        void onPageFinished(WebView view, String url);

        void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);

        boolean onKeyBack();
    }

    private OnWebViewListener mWebViewListener;

    public void setWebViewListener(OnWebViewListener listener) {
        mWebViewListener = listener;
    }

    public interface OnWebChromeListener {
        void onProgressChanged(WebView view, int newProgress);

        void onReceivedTitle(WebView view, String title);

        boolean onJsAlert(WebView view, String url, String message, JsResult result);
    }

    private OnWebChromeListener mWebChromeListener;

    public void setWebChromListener(OnWebChromeListener listener) {
        mWebChromeListener = listener;
    }

    protected Context mContext;

    public WebViewHelper(Context context) {
        mContext = context;
    }

    private WebViewJsInject mJsInject;

    /**
     * 设置注入的对象，在initWebView方法之前调用
     *
     * @param jsInject 注入的对象
     */
    public void setJsInject(WebViewJsInject jsInject) {
        mJsInject = jsInject;
    }

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    public WebView initWebView(boolean appContext) {
        final WebView webView;
        if (appContext) {
            webView = new WebView(mContext.getApplicationContext());
        } else {
            webView = new WebView(mContext);
        }
        //setWebViewClient部分
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.t(TAG).i("shouldOverrideUrlLoading:url:" + url);
                if (StringUtil.isEmpty(url) && !url.startsWith("http://") && !url.startsWith("https://")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (IntentUtil.isActivityCanJump(mContext, intent)) {
                        try {
                            mContext.startActivity(intent);
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.t(TAG).i("onPageStarted");
                if (mWebViewListener != null) {
                    mWebViewListener.onPageStarted(view, url, favicon);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.t(TAG).i("onPageFinished");
                //防止注入的js对象为空
                injectNative(webView);
                if (mWebViewListener != null) {
                    mWebViewListener.onPageFinished(view, url);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Logger.t(TAG).i("onReceivedError: 页面加载失败");
                if (null != error) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Logger.t(TAG).i("onReceivedError:" + error.getDescription() + error.getErrorCode());
                    }
                }
                if (mWebViewListener != null) {
                    mWebViewListener.onReceivedError(view, request, error);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                Logger.t(TAG).i("onReceivedSslError: 页面加载失败");
                handler.proceed();
            }
        });
        //按键监听部分
        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        if (mWebViewListener != null && mWebViewListener.onKeyBack()) {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
        //setWebChromeClient部分
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.t(TAG).i("onProgressChanged=" + newProgress);
                if (mWebChromeListener != null) {
                    mWebChromeListener.onProgressChanged(view, newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Logger.t(TAG).i("onReceivedTitle=" + title);
                if (mWebChromeListener != null) {
                    mWebChromeListener.onReceivedTitle(view, title);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Logger.t(TAG).i("onJsAlert : " + message);
                if (mWebViewListener != null && mWebChromeListener.onJsAlert(view, url, message, result)) {
                    result.confirm();
                    return true;
                }
                return super.onJsAlert(view, url, message, result);
            }
        });
        //WebSettings部分
        webView.requestFocus(View.FOCUS_DOWN);
        WebSettings settings = webView.getSettings();
        settings.setDatabaseEnabled(true);
        //不缓存
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setGeolocationEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setBlockNetworkLoads(false);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        //这里需要设置为true，才能让Webivew支持<meta>标签的viewport属性
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(true);
        String dir = mContext.getApplicationContext()
                .getDir("appcache", Context.MODE_PRIVATE).getPath();
        settings.setAppCachePath(dir);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setUserAgentString(settings.getUserAgentString());
        settings.setJavaScriptEnabled(true);
        injectNative(webView);
        WebView.setWebContentsDebuggingEnabled(AppConfig.DEBUG);
        //开启硬件加速，支持播放视频
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);
        return webView;
    }

    @SuppressLint("JavascriptInterface")
    private void injectNative(WebView webView) {
        if (mJsInject != null) {
            webView.addJavascriptInterface(mJsInject, WebViewJsInject.INJECT_OBJECT_ALIAS);
        }
    }

}
