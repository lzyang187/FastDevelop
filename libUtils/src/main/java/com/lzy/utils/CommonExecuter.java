package com.lzy.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: cyli8
 * @date: 2018/2/23 09:14
 */

public class CommonExecuter {
    public interface OnExecuteCompleteListener {
        void onExecuteComplete();
    }

    private class CommonRunnable implements Runnable {
        private List<Runnable> mRunnable;
        private OnExecuteCompleteListener mListener;

        public CommonRunnable(Runnable r, OnExecuteCompleteListener l) {
            mRunnable = new ArrayList<Runnable>();
            mRunnable.add(r);
            mListener = l;
        }

        public CommonRunnable(OnExecuteCompleteListener l, Runnable... rs) {
            mRunnable = new ArrayList<Runnable>();
            for (Runnable r : rs) {
                if (null != r) {
                    mRunnable.add(r);
                }
            }
            mListener = l;
        }

        @Override
        public void run() {
            if (ListUtil.isEmpty(mRunnable)) {
                return;
            }
            Log.i("CommonExecuter", "running task begin ...");
            for (Runnable r : mRunnable) {
                r.run();
            }
            Log.i("CommonExecuter", "running task end, and post the result");
            if (null == mListener) {
                return;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onExecuteComplete();
                }
            });
        }
    }

    private static CommonExecuter mInstance = null;
    private static final int CORE_THREAD_COUNT = 5;

    private Executor mExecutor;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private CommonExecuter() {
        mExecutor = new ThreadPoolExecutor(CORE_THREAD_COUNT, CORE_THREAD_COUNT,
                10L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public Executor getExecutor() {
        return mExecutor;
    }

    public static CommonExecuter getInstance() {
        if (null == mInstance) {
            synchronized (CommonExecuter.class) {
                if (null == mInstance) {
                    mInstance = new CommonExecuter();
                }
            }
        }
        return mInstance;
    }

    public static void run(Runnable runnable) {
        run(runnable, null);
    }

    public static void run(Runnable r, OnExecuteCompleteListener l) {
        getInstance().mExecutor.execute(mInstance.new CommonRunnable(r, l));
    }

    public static void run(Runnable... r) {
        run(null, r);
    }

    public static void run(OnExecuteCompleteListener l, Runnable... r) {
        getInstance().mExecutor.execute(mInstance.new CommonRunnable(l, r));
    }
}
