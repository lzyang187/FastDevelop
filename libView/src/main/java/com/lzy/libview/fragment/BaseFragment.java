package com.lzy.libview.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.lzy.libview.activity.ActivityResultTask;
import com.lzy.libview.dialog.CustomWaitingDialog;
import com.lzy.libview.mvp.IBasePresenter;
import com.lzy.libview.mvp.IBaseView;

import java.lang.ref.WeakReference;

/**
 * Fragment基类，所有业务Fragment均继承于此，统一定义公共逻辑
 * 1、体现Fragment在ViewPager中的生命周期变化，统一分配
 * 2、处理初始化过程
 * 3、处理Fragment中需要处理的StartActivityForResult的逻辑以及返回后的Result的分配
 * 4、为了和{@link com.lzy.libview.activity.BaseTitleFragmentActivity} 配合使用，增加的Title，Activity间通信等逻辑相关函数
 *
 * @author: cyli8
 * @date: 2018/2/28 11:03
 */

public class BaseFragment<T extends IBasePresenter> extends Fragment implements IBaseView {
    public static final String BUNDLE_ARGUMENT_ENTRY_STATS = "bundle_argument_entry_stats";
    public static final String BUNDLE_ARGUMENT_SORT_NO = "bundle_argument_sort_no";
    public static final String BUNDLE_ARGUMENT_PAGE_NO = "bundle_argument_page_no";
    public static final String BUNDLE_ARGUMENT_SSID = "bundle_argument_ssid";
//    public static final String BUNDLE_ARGUMENT_LOC_PAGE = "bundle_argument_loc_page";

    public static final String BUNDLE_ARGUMENT_STATSLOCINFO = "bundle_argument_stslocinfo";

    private static class MyHandler extends Handler {
        private WeakReference<BaseFragment> weakRef;

        private MyHandler(BaseFragment fragment) {
            weakRef = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (null == weakRef) {
                return;
            }
            BaseFragment fragment = weakRef.get();
            if (fragment == null) {
                return;
            }
            switch (msg.what) {
                default:
                    fragment.handleMessage(msg);
                    break;
            }
            //这里如果有默认事件处理 要放在这里处理
        }
    }

    private boolean mIsFirstVisible = true;
    private SparseArray<ActivityResultTask> taskMap = null;

    protected Handler mHandler;

    protected T mPresenter;

    /**
     * 这里加一个成员变量记下来fragment的setUserVisibleHint(boolean)设置的值 因为在pagerAdapter中切换tab
     * 中切换的步长超过缓存的长度在FragmentManager.java的1029行会把fragment中的true可见性改成false /shit/
     */
    private boolean mIsVisibleToUser = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new MyHandler(this);
        mIsFirstVisible = true;
        Bundle bundle = getArguments();
        if (bundle != null) {
            initArguement(bundle);
        }
        mPresenter = createPresenter();
    }

    protected T createPresenter() {
        return null;
    }

    protected void initArguement(Bundle bundle) {

    }

    /**
     * X
     * 请求或者加载数据
     */
    protected void requestOrLoadData() {
    }

    /**
     * 当界面变可见性发生变化时调用
     */
    protected void onViewVisibleChanged(boolean visible) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mIsFirstVisible && mIsVisibleToUser) {
            mIsFirstVisible = false;
            requestOrLoadData();
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser;
        if (null != getView()) {
            if (isVisibleToUser) {
                if (mIsFirstVisible) {
                    mIsFirstVisible = false;
                    requestOrLoadData();
                } else {
                    onViewVisibleChanged(true);
                }
            } else {
                onViewVisibleChanged(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {

        }
    }

    protected void handleMessage(Message msg) {
        //处理消息
    }

    public String getTitle() {
        return null;
    }

    public Intent getResultIntent() {
        return null;
    }

    protected CustomWaitingDialog mWaitingDialog;

    public final void showWaitingDialog() {
        if (null == mWaitingDialog) {
            mWaitingDialog = new CustomWaitingDialog(getActivity());
        }
        mWaitingDialog.show();
    }

    @Override
    public final void showWaitingDialog(DialogInterface.OnCancelListener l) {
        if (null == mWaitingDialog) {
            mWaitingDialog = new CustomWaitingDialog(getActivity());
        }
        if (l != null) {
            mWaitingDialog.setOnCancelListener(l);
        }
        mWaitingDialog.show();
    }

    public final void showWaitingDialog(int color) {
        if (null == mWaitingDialog) {
            mWaitingDialog = new CustomWaitingDialog(getActivity(), color);
        }
        mWaitingDialog.show();
    }

    public final void showWaitingDialog(String tips) {
        if (mWaitingDialog == null) {
            mWaitingDialog = new CustomWaitingDialog(getActivity(), tips);
        }
        mWaitingDialog.show();
    }

    @Override
    public final void showWaitingDialog(DialogInterface.OnCancelListener l, String tips) {
        if (mWaitingDialog == null) {
            mWaitingDialog = new CustomWaitingDialog(getActivity(), tips);
        }
        if (l != null) {
            mWaitingDialog.setOnCancelListener(l);
        }
        mWaitingDialog.show();
    }

    @Override
    public void updateWaittingTips(String tips) {
        if (mWaitingDialog != null) {
            mWaitingDialog.updateTips(tips);
        } else {
            showWaitingDialog(tips);
        }
    }

    @Override
    public void setWaittingDlgCancel(boolean cancel) {
        if (mWaitingDialog != null) {
            mWaitingDialog.setCanceledOnTouchOutside(cancel);
            mWaitingDialog.setCancelable(cancel);
        }
    }

    @Override
    public void dismissWaitingDialog() {
        if (null != mWaitingDialog && mWaitingDialog.isShowing()) {
            mWaitingDialog.dismiss();
            mWaitingDialog = null;
        }
    }

    @Override
    public void toast(CharSequence text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toast(int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public boolean isViewAttached() {
        return getHost() != null && getContext() != null && isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }
}
