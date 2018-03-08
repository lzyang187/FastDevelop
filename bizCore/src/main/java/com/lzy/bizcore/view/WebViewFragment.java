package com.lzy.bizcore.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.bizcore.AppConfig;
import com.lzy.bizcore.R;
import com.lzy.libview.activity.BaseTitleFragmentActivity;
import com.lzy.libview.fragment.BaseFragment;
import com.lzy.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;

/**
 * 内嵌浏览器页面
 *
 * @author: cyli8
 * @date: 2018/3/8 09:55
 */

public class WebViewFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "WebViewFragment";

    public static final String KEY_WEBVIEW_TITLE = "key_webview_title";
    public static final String KEY_WEBVIEW_URL = "key_webview_url";
    public static final String KEY_WEBVIEW_FROM = "key_webview_from";
    /**
     * 映射webview缩放比例的参数
     */
    private static final String SCAL_PARAM = "mDefaultScale";

    //提供的内嵌web页面的分辨率宽度
    private static final float WEB_WIDTH = 480.0f;
    protected RelativeLayout mWebViewLayout;
    protected WebView mWebView;
    protected String mUrl;
    private WebViewClient mWebClient = null;
    private String mTitle;
    protected int mFromPage;
    private Activity mActivity;
    private ProgressBar mProgressBar;
    private ViewStub mFailedViewStub;
    protected View mFailedLLyt;
    private TextView mEmptyTipsTv;
    private TextView mEmptyBtn;
    protected boolean mIsLoadFailed;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(KEY_WEBVIEW_TITLE);
            mUrl = bundle.getString(KEY_WEBVIEW_URL);
            Logger.t(TAG).i("h5地址: " + mUrl);
            mFromPage = bundle.getInt(KEY_WEBVIEW_FROM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutID(), null);
        initView(view);
        buildWebView(mWebViewLayout);
        mWebView.setVisibility(View.VISIBLE);
        mWebView.loadUrl(mUrl);
        return view;
    }

    protected void initView(View view) {
        mProgressBar = (ProgressBar) view.findViewById(R.id.web_view_pb);
        mWebViewLayout = (RelativeLayout) view.findViewById(R.id.webview_layout);
        mFailedViewStub = ((ViewStub) view.findViewById(R.id.vstub_query_failed));
    }

    protected int getLayoutID() {
        return R.layout.biz_core_fragment_webview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public String getTitle() {
        return mTitle;
    }


    /**
     * 创建内嵌的webview，并对相关属性进行设置
     *
     * @param relativeLayout 页面布局
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    protected void buildWebView(RelativeLayout relativeLayout) {
        mWebView = new WebView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(mWebView, layoutParams);
        mWebView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    try {
                        Field defaultScale = WebView.class.getDeclaredField(SCAL_PARAM);
                        defaultScale.setAccessible(true);
                        defaultScale.setFloat(mWebView, mActivity
                                .getWindowManager().getDefaultDisplay().getWidth() / WEB_WIDTH);

                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        try {
                            Field zoomManager;
                            zoomManager = WebView.class.getDeclaredField("mZoomManager");
                            zoomManager.setAccessible(true);
                            Object zoomValue = zoomManager.get(mWebView);
                            Field defaultScale = zoomManager.getType()
                                    .getDeclaredField(SCAL_PARAM);
                            defaultScale.setAccessible(true);
                            defaultScale.setFloat(zoomValue, mActivity.getWindowManager()
                                    .getDefaultDisplay().getWidth() / WEB_WIDTH);
                        } catch (SecurityException e1) {
                            e1.printStackTrace();
                        } catch (IllegalArgumentException e1) {
                            e1.printStackTrace();
                        } catch (IllegalAccessException e1) {
                            e1.printStackTrace();
                        } catch (NoSuchFieldException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDatabaseEnabled(true);
        String dir = mActivity.getApplicationContext()
                .getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dir);
        webSettings.setGeolocationEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pageStart();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!mIsLoadFailed) {
                    mWebView.setVisibility(View.VISIBLE);
                }
                pageEnd();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                pageEnd();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (StringUtil.equals(mUrl, failingUrl)) {
                    mIsLoadFailed = true;
                    showFailedLayout(true, null);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
                // super.onReceivedSslError(view, handler, error);
                // 接受所有网站的证书，忽略SSL错误，执行访问网页
                handler.proceed();
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                changeProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (StringUtil.isTrimEmpty(mTitle)) {
                    if (!TextUtils.isEmpty(title)) {
                        mTitle = title;
                    } else {
                        mTitle = AppConfig.APP_NAME;
                    }
                    Activity a = getActivity();
                    if (a instanceof BaseTitleFragmentActivity) {
                        ((BaseTitleFragmentActivity) getActivity()).refreshTitle();
                    }
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWebViewLayout.removeAllViews();
        if (null != mWebView) {
            mWebView.setVisibility(View.GONE);
            mWebView.removeAllViews();
            mWebView.destroy();
        }
    }

    private void pageStart() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void pageEnd() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void changeProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    protected void initFailedLayout() {
        if (null != mFailedLLyt) {
            return;
        }
        mFailedLLyt = mFailedViewStub.inflate();
        mEmptyTipsTv = (TextView) mFailedLLyt.findViewById(R.id.tv_empty);
        mEmptyBtn = (TextView) mFailedLLyt.findViewById(R.id.btn_empty);
        mFailedViewStub = null;
    }

    protected void showFailedLayout(boolean show, String tips) {
        if (getActivity() == null) {
            return;
        }
        if (show) {
            initFailedLayout();
            mFailedLLyt.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            mFailedLLyt.setOnClickListener(this);
            Drawable drawable = getResources().getDrawable(R.mipmap.lib_view_icon_network_error);
            mEmptyTipsTv.setText(R.string.lib_view_net_fail_tip);
            if (!StringUtil.isTrimEmpty(tips)) {
                mEmptyTipsTv.setText(tips);
            }
            mEmptyTipsTv.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            mEmptyBtn.setVisibility(View.GONE);
        } else {
            if (null != mFailedLLyt) {
                mFailedLLyt.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mFailedLLyt) {
            showFailedLayout(false, null);
            mIsLoadFailed = false;
            mWebView.loadUrl(mUrl);
        }
    }

}
