package com.cain.glide.cache;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.cain.glide.resource.Value;

public class MemoryCache extends LruCache<String, Value> {

    MemoryCacheCallBack memoryCacheCallBack;

    private boolean shoudongRemove;

    public MemoryCacheCallBack getMemoryCacheCallBack() {
        return memoryCacheCallBack;
    }

    public void setMemoryCacheCallBack(MemoryCacheCallBack memoryCacheCallBack) {
        this.memoryCacheCallBack = memoryCacheCallBack;
    }

    public void shoudongRemove(String key) {
        shoudongRemove = true;
        remove(key);
        shoudongRemove = false;
    }

    /**
     * 传入最大值
     */
    public MemoryCache(int maxSize) {
        super(maxSize);
    }


    /**
     * 存储的 键值对 key
     * @param key
     * @param value
     * @return
     */
    @Override
    protected int sizeOf(String key, Value value) {
        Bitmap bitmap = value.getBitmap();
        int sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        return bitmap.getByteCount();
    }

    /**
     * lru 删除的回调
     * @param evicted
     * @param key
     * @param oldValue
     * @param newValue
     */
    @Override
    protected void entryRemoved(boolean evicted, String key, Value oldValue, Value newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        if (memoryCacheCallBack != null && !shoudongRemove) {
            memoryCacheCallBack.entryRemoveMemoryCache(key,oldValue);
        }
    }
}
