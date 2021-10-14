package com.cain.glide.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;

@SuppressLint("ValidFragment")
public class ActivityFragmentManager  extends Fragment {


    private LifecycleCallBack lifecycleCallBack;

    @SuppressLint("ValidFragment")
    public ActivityFragmentManager(LifecycleCallBack lifecycleCallBack) {
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
