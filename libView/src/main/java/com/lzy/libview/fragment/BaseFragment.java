package com.lzy.libview.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lzy.libview.dialog.CustomWaitingDialog;
import com.lzy.libview.mvp.AbstractBasePresenter;
import com.lzy.libview.mvp.IBaseView;

/**
 * Fragment基类，所有业务Fragment均继承于此，统一定义公共逻辑
 * 为了和{@link com.lzy.libview.activity.BaseTitleFragmentActivity} 配合使用，增加的Title，Activity间通信等逻辑相关函数
 *
 * @author: cyli8
 * @date: 2018/2/28 11:03
 */

public class BaseFragment<T extends AbstractBasePresenter> extends Fragment implements IBaseView {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            initArgument(bundle);
        }
        mPresenter = createPresenter();
    }

    protected void initArgument(Bundle bundle) {

    }

    protected T createPresenter() {
        return null;
    }

    private CustomWaitingDialog mWaitingDialog;

    @Override
    public void showWaitingDialog(String tips, boolean cancel, DialogInterface.OnCancelListener listener) {
        if (getActivity() == null) {
            return;
        }
        if (null == mWaitingDialog) {
            mWaitingDialog = new CustomWaitingDialog(getActivity(), tips);
        }
        if (listener != null) {
            mWaitingDialog.setOnCancelListener(listener);
        }
        mWaitingDialog.setCancelable(cancel);
        mWaitingDialog.show();
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
        if (isViewAttached()) {
            Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void toast(int resId) {
        if (isViewAttached()) {
            Toast.makeText(getContext(), resId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean isViewAttached() {
        return getHost() != null && getContext() != null && isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

}
