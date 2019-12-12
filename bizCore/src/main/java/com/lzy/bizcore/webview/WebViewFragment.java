package com.lzy.bizcore.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.lzy.bizcore.AppConfig;
import com.lzy.bizcore.R;
import com.lzy.libview.activity.BaseTitleFragmentActivity;
import com.lzy.libview.fragment.BaseFragment;
import com.lzy.utils.StringUtil;
import com.orhanobut.logger.Logger;

/**
 * 内嵌浏览器页面
 *
 * @author: cyli8
 * @date: 2018/3/8 09:55
 */

public class WebViewFragment extends BaseFragment implements WebViewHelper.OnWebViewListener, WebViewHelper.OnWebChromeListener {
    private static final String TAG = "WebViewFragment";

    public static final String KEY_WEB_VIEW_TITLE = "key_web_view_title";
    public static final String KEY_WEB_VIEW_URL = "key_web_view_url";

    protected RelativeLayout mWebViewLayout;
    protected WebView mWebView;
    protected String mUrl;
    private String mTitle;
    private ProgressBar mProgressBar;

    @Override
    protected void initArgument(Bundle bundle) {
        if (bundle != null) {
            mTitle = bundle.getString(KEY_WEB_VIEW_TITLE);
            mUrl = bundle.getString(KEY_WEB_VIEW_URL);
            Logger.t(TAG).i("h5地址: " + mUrl);
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
        mProgressBar = view.findViewById(R.id.web_view_pb);
        mWebViewLayout = view.findViewById(R.id.webview_layout);
    }

    protected int getLayoutID() {
        return R.layout.biz_core_fragment_webview;
    }

    /**
     * 创建内嵌的webview，并对相关属性进行设置
     *
     * @param relativeLayout 页面布局
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void buildWebView(RelativeLayout relativeLayout) {
        WebViewHelper helper = new WebViewHelper(getContext());
        helper.setWebViewListener(this);
        helper.setWebChromListener(this);
        mWebView = helper.initWebView(true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(mWebView, layoutParams);
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

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        pageStart();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        pageEnd();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        pageEnd();
    }

    @Override
    public boolean onKeyBack() {
        handleGoBack();
        return false;
    }

    private void handleGoBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            backToClient();
        }
    }

    private void backToClient() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        changeProgress(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        if (StringUtil.isTrimEmpty(mTitle)) {
            if (!TextUtils.isEmpty(title)) {
                mTitle = title;
            } else {
                mTitle = AppConfig.APP_NAME;
            }
            Activity a = getActivity();
            if (a instanceof BaseTitleFragmentActivity) {
                ((BaseTitleFragmentActivity) getActivity()).setTitle(mTitle);
            }
        }
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return false;
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
}
