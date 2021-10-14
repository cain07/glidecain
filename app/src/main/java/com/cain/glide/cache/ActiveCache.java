package com.cain.glide.cache;

import android.util.Log;

import com.cain.glide.resource.Value;
import com.cain.glide.resource.ValueCallBack;
import com.cain.glide.utils.Tool;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 活动缓存
 */
public class ActiveCache {
    private final String Tag = this.getClass().getSimpleName();

    private Map<String, WeakReference<Value>> mapList = new HashMap<>();
    private ReferenceQueue<? super Value> queue;

    private boolean isCloseThread = false;
    private boolean isShoudongRemove = false;

    private Thread thread;

    private ValueCallBack callBack;

    public ValueCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(ValueCallBack callBack) {
        this.callBack = callBack;
    }

    public void put(String key, Value value) {
        Tool.checkNotEmpty(key);
        value.setCallBack(callBack);
        mapList.put(key,new MyWeakReference(value,getQueue(),key));
    }

    public void  closeThread() {
        isCloseThread = true;
        if (null != null) {
            thread.interrupt();

            try {
                thread.join(TimeUnit.SECONDS.toMillis(5));
                if (thread.isAlive()) {
                    Log.e(Tag,"ActiveCache：线程还没有停止");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取 value
     * @param key
     * @return
     */
    public Value get(String key) {
        WeakReference<Value> valueWeakReference = mapList.get(key);
        if (valueWeakReference != null) {
            return valueWeakReference.get();
        }
        return null;
    }

    /**
     * 手动移除
     * @param key
     * @return
     */
    public Value remove(String key) {
        isShoudongRemove = true;
        WeakReference<Value> remove = mapList.remove(key);
        isShoudongRemove =false;  // 移除完毕 还原 让gc 继续自动移除
        if (null != remove) {
            return remove.get();
        }
        return null;
    }

    private ReferenceQueue<? super Value> getQueue() {
        if (queue == null) {
            queue = new ReferenceQueue<>();
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (!isCloseThread) {
                        try {
                            if (!isShoudongRemove) {
                                Reference<?> remove = queue.remove();
                                MyWeakReference  myWeakReference = (MyWeakReference) remove;

                                if (mapList != null && mapList.size() > 0 ){
                                    mapList.remove(myWeakReference.key);
                                }
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }
        return queue;
    }


    /**
     * 监听 弱引用 是否被回收
     */
    public class MyWeakReference extends WeakReference<Value> {

        String key;
        public MyWeakReference(Value referent, ReferenceQueue<? super Value> q,String key) {
            super(referent, q);
            this.key = key;
        }
    }


}
