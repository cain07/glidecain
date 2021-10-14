package com.cain.glide.core;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

public class RequestManagerRetriever {

    public RequestManager get(Activity mainActivity) {

        return new RequestManager(mainActivity);
    }

    public RequestManager get(FragmentActivity mainActivity) {
        return new RequestManager(mainActivity);
    }

    public RequestManager get(Context mainActivity) {
        return new RequestManager(mainActivity);
    }
}
