package com.cain.glide.core;

class GlideBuild {

    public Glide build() {
        RequestManagerRetriever requestManagerRetriever = new RequestManagerRetriever();
        Glide glide = new Glide(requestManagerRetriever);
        return glide;
    }
}
