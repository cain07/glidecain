package com.cain.glide.resource;

import android.graphics.Bitmap;
import android.util.Log;

import com.cain.glide.utils.Tool;

/**
 *
 */
public class Value {

    private final String TAG = Value.class.getSimpleName();

    private static Value value;

    public  static  Value getInstance() {
        if (null == value) {
            synchronized (Value.class){
                if (null ==value) {
                    value = new Value();
                }
            }
        }

        return value;
    }

    private Bitmap bitmap;
    // 计数 在一个队里中 显示 bitmap 使用的次数
    private int count;

    private ValueCallBack callBack;

    private String key;

    public ValueCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ValueCallBack callBack) {
        this.callBack = callBack;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 使用一次就加一
     */
    public void useAction() {
        Tool.checkNotEmpty(bitmap);

        if (bitmap.isRecycled()){
            Log.e(TAG,"useAction : bitmap 已经被回收了" + count);
        }

        count++;
    }

    /**
     *不使用 就减一
     */
    public void nonUseAction() {
        if (count-- <=0 && callBack != null) {
            Log.e(TAG,"useAction : bitmap 已经不再使用了" + count);
            callBack.valueNonUseAction(key,this);
        }
    }

    /**
     *释放 bitmap
     */
    public void recycleBitmap() {
        if (count > 0) {
            Log.e(TAG, "bitmap 正在使用 不能被释放");
            return;
        }

        if (bitmap.isRecycled()) {
            Log.e(TAG, "bitmap 已经被释放了");
            return;
        }

        bitmap.isRecycled();

        value = null;
        System.gc();
    }

}
