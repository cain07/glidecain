package com.cain.glide.disk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.cain.glide.resource.Value;
import com.cain.glide.utils.Tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 磁盘 存储
 */
public class DiskLruCacheImpl {

    private final String Tag = this.getClass().getSimpleName();

    private DiskLruCache diskLruCache;

    private final String File_Url = Environment.getExternalStorageState() + File.separator + "cain_disk_cache";

    private final int appVersion = 1;

    private final int valueCount = 1;
    private final long maxSize = 1024 * 1024 * 10;


    public DiskLruCacheImpl() {
        try {
            File  file = new File(File_Url);
            diskLruCache = DiskLruCache.open(file,appVersion,valueCount,maxSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 存入数据 写入 磁盘中
     * @param key
     * @param value
     */
    public void put(String key, Value value) {
        OutputStream outputStream = null;
        DiskLruCache.Editor edit = null;
        try {
            edit = diskLruCache.edit(key);
            outputStream = edit.newOutputStream(0);

            Bitmap bitmap = value.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
            try {
                edit.abort();
            } catch (IOException ex) {
                ex.printStackTrace();
                Log.e(Tag,"put：edit.abort() e:" + e.getMessage());
            }
        }finally {

            try {
                edit.commit();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(Tag,"put：edit.commit() e:" + e.getMessage());
            }

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(Tag,"put：outputStream.close() e:" + e.getMessage());
            }
        }
    }

    /**
     * 取数据 从磁盘中取数据
     * @param key
     * @return
     */
    public Value get(String key) {
        Tool.checkNotEmpty(key);

        InputStream inputStream = null;
        try {
            Value value = Value.getInstance();
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            inputStream = snapshot.getInputStream(0);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            value.setBitmap(bitmap);
            value.setKey(key);

            return value;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(Tag,"get：inputStream.close() e:" + e.getMessage());
                }
            }
        }
        return null;
    }
}
