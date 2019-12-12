package com.lzy.libview.mvp;

import android.content.Context;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author: cyli8
 * @date: 2019-12-12 13:56
 */
public abstract class AbstractBasePresenter<T extends IBaseView> implements IBasePresenter {

    protected Context mContext;
    protected T mView;

    private CompositeDisposable mCompositeDisposable;

    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void destroy() {
        cancelRequest();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

}
