package com.cain.glide.core;

import android.util.Log;

import com.cain.glide.fragment.LifecycleCallBack;

/**
 * 加载图片 资源
 */
public class RequestTargetEngine implements LifecycleCallBack {

    private final String Tag = this.getClass().getSimpleName();

    @Override
    public void glideInitAction() {
        Log.e(Tag,"glideInitAction：glide 初始化了.....");
    }

    @Override
    public void glideStopAction() {
        Log.e(Tag,"glideStopAction：glide 停止了.....");

    }

    @Override
    public void glideRecycleAction() {
        Log.e(Tag,"glideRecycleAction：glide 销毁了....");
    }


}
