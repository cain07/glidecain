package com.cain.glide.core;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.cain.glide.fragment.ActivityFragmentManager;
import com.cain.glide.fragment.FragmentActivityFragmentManager;

public class RequestManager {


    private final String Fragment_Activity_NAME = "Fragment_Activity_NAME";
    private final String Activity_NAME = "Activity_NAME";
    private final int NEXT_HANDLE_MSG = 99545;

    private Context requestManagerContext;

    private RequestTargetEngine requestTargetEngine;

    {
        if (requestTargetEngine == null) {
            requestTargetEngine = new RequestTargetEngine();
        }
    }


    public RequestManager(Activity mainActivity) {
        this.requestManagerContext = mainActivity;

        android.app.FragmentManager fragmentManager = mainActivity.getFragmentManager();

        android.app.Fragment fragmentByTag = fragmentManager.findFragmentByTag(Activity_NAME);

        if (fragmentByTag == null ) {
            fragmentByTag = new ActivityFragmentManager(requestTargetEngine);

            fragmentManager.beginTransaction().add(fragmentByTag,Activity_NAME).commitAllowingStateLoss();
        }

        myHandler.sendEmptyMessage(NEXT_HANDLE_MSG);
    }

    public RequestManager(FragmentActivity mainActivity) {
        this.requestManagerContext = mainActivity;

        FragmentManager supportFragmentManager = mainActivity.getSupportFragmentManager();

        Fragment fragmentByTag = supportFragmentManager.findFragmentByTag(Fragment_Activity_NAME);

        if (fragmentByTag == null) {
            fragmentByTag = new FragmentActivityFragmentManager(requestTargetEngine);
            supportFragmentManager.beginTransaction().add(fragmentByTag,Fragment_Activity_NAME).commitAllowingStateLoss();
        }

        myHandler.sendEmptyMessage(NEXT_HANDLE_MSG);

    }

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            return false;
        }
    });

    public RequestManager(Context mainActivity) {

        this.requestManagerContext = mainActivity;

    }
}
