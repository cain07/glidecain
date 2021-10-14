package com.cain.glide.fragment;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;

public class FragmentActivityFragmentManager extends Fragment {

    private LifecycleCallBack lifecycleCallBack;

    @SuppressLint("ValidFragment")
    public FragmentActivityFragmentManager(LifecycleCallBack lifecycleCallBack) {
        this.lifecycleCallBack = lifecycleCallBack;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (null != lifecycleCallBack) {
            lifecycleCallBack.glideInitAction();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != lifecycleCallBack) {
            lifecycleCallBack.glideStopAction();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != lifecycleCallBack) {
            lifecycleCallBack.glideRecycleAction();
        }
    }

}
