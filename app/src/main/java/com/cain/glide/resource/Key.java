package com.cain.glide.resource;

import com.cain.glide.utils.Tool;

/**
 *
 */
public class Key {

    private String key;

    public Key(String key) {
        this.key = Tool.getSHA256StrJava(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}

