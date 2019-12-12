package com.lzy.libbasefun.rxjava;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: cyli8
 * @date: 2019-12-10 20:10
 */
public class RxUtils {

    /**
     * io和主线程切换
     */
    public static <T> ObservableTransformer<T, T> rxScheduler() {
        return rxScheduler(Schedulers.io());
    }

    public static <T> ObservableTransformer<T, T> rxScheduler(final Scheduler scheduler) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(scheduler)
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
