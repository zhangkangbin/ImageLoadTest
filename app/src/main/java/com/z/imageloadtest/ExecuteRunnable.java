package com.z.imageloadtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.bumptech.glide.load.data.BufferedOutputStream;
import com.z.imageloadtest.cache.DiskLruCache;
import com.z.imageloadtest.cache.DiskLruCacheHelper;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class ExecuteRunnable implements Runnable {
    private LinkedBlockingQueue<ImageRequest> linkedBlockingQueue;
    private ImageRequest.Builder builder;

    public ExecuteRunnable(LinkedBlockingQueue<ImageRequest> linkedBlockingQueue) {
        this.linkedBlockingQueue = linkedBlockingQueue;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    builder.getImageView().setImageBitmap(bitmap);
                    builder.getImageLoadListener().onSuccess(bitmap);
                    break;
                case 2:

                    builder.getImageLoadListener().onFail((Exception) msg.obj);
                    break;

                default:

                    break;

            }

        }
    };


    @Override
    public void run() {
        try {
            ImageRequest imageRequest = linkedBlockingQueue.take();
            builder = imageRequest.getBuilder();
//            Log.d("ExecuteRunnable", builder.getTag());

            final Bitmap bitmap = getBitmap(builder.getUrl());
            Message message = Message.obtain();

            if (bitmap != null) {
                message.what = 1;
                message.obj = bitmap;
                handler.sendMessage(message);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private Bitmap getBitmap(String url) {

        try {


            Log.d("mytest", "getBitmap" + url.hashCode() + "");

            DiskLruCache.Snapshot cache = DiskLruCacheHelper.get().get(url.hashCode() + "");
            if (cache != null) {
                Log.d("mytest", "缓存中取");
                InputStream inputStream = cache.getInputStream(0);

                return BitmapFactory.decodeStream(inputStream);
            }

            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
            Request request = new Request.Builder()
                    .url(url)//请求接口。如果需要传参拼接到接口后面。
                    .build();//创建Request 对象
            Response response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                Log.d("mytest", "response.code()==" + response.code());
                Log.d("mytest", "response.message()==" + response.message());


                byte[] bytes = response.body().bytes();
                DiskLruCache.Editor editor = DiskLruCacheHelper.get().edit(url.hashCode() + "");
                OutputStream outputStream = editor.newOutputStream(0);

                if (outputStream != null) {
                    outputStream.write(bytes);
                    outputStream.close();

                }
                editor.commit();

                return null;
            }
        } catch (Exception e) {
            Log.d("mytest", "e" + e.getLocalizedMessage());
            Message message = Message.obtain();
            message.what = 2;
            message.obj = e;
            handler.sendMessage(message);
        }

        return null;

    }
}
