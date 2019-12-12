package com.lzy.libview.dialog;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.lzy.libview.R;


/**
 * @author: cyli8
 * @date: 2019-10-08 17:14
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.lib_view_theme_basedialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater);
        requestOrLoadData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        initWindow();
    }

    protected void initWindow() {
        if (getDialog() != null) {
            Window window = getDialog().getWindow();
            if (window != null) {
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = (int) (metrics.widthPixels * 0.8);
                window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.CENTER);
            }
        }
    }

    protected abstract View initView(LayoutInflater inflater);

    protected void requestOrLoadData() {

    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            if (manager != null) {
                //防止连续点击add多个fragment
                manager.beginTransaction().remove(this).commit();
                super.show(manager, tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
