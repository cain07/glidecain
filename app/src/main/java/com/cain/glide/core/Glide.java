package com.cain.glide.core;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.FragmentActivity;

public class Glide {


    private RequestManagerRetriever requestManagerRetriever;

    public Glide(RequestManagerRetriever requestManagerRetriever) {

    }

    public static RequestManager with(Activity mainActivity) {
        return getRetriever(mainActivity).get(mainActivity);
    }

    public static RequestManager with(FragmentActivity mainActivity) {
        return getRetriever(mainActivity).get(mainActivity);
    }

    public static RequestManager with(Context mainActivity) {
        return getRetriever(mainActivity).get(mainActivity);
    }

    public static RequestManagerRetriever getRetriever(Context context) {
        return Glide.get(context).getRequestManagerRetriever();
    }

    /**
     * glide  是 new 出来的转变
     * @param context
     * @return
     */
    public static Glide get(Context context) {
        return new GlideBuild().build();
    }


    public RequestManagerRetriever getRequestManagerRetriever() {
        return requestManagerRetriever;
    }

    public void setRequestManagerRetriever(RequestManagerRetriever requestManagerRetriever) {
        this.requestManagerRetriever = requestManagerRetriever;
    }
}
