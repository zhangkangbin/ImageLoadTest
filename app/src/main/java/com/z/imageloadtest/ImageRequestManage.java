package com.z.imageloadtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageRequestManage {

    private Handler handler = new Handler(Looper.getMainLooper());

    private LinkedBlockingQueue<ImageRequest> linkedBlockingQueue = new LinkedBlockingQueue<>();

    private static ImageRequestManage imageRequestManageInstance;

    public static ImageRequestManage get(Context context) {
        if (imageRequestManageInstance == null) {
            synchronized (ImageRequestManage.class) {
                if (imageRequestManageInstance == null) {
                    imageRequestManageInstance = new ImageRequestManage();
                }
            }

        }

        return imageRequestManageInstance;
    }

    private ImageRequestManage() {
    }

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    public void addRequest(ImageRequest imageRequest) {
        linkedBlockingQueue.add(imageRequest);
        fixedThreadPool.execute(new ExecuteRunnable());
    }


    class ExecuteRunnable implements Runnable {

        @Override
        public void run() {

            try {
                //    Thread.sleep(2000);
                ImageRequest imageRequest = linkedBlockingQueue.take();
                final ImageRequest.Builder builder = imageRequest.getBuilder();

                Log.d("ExecuteRunnable", builder.getTag());

                final Bitmap bitmap = getBitmap(builder.getUrl());


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.getImageView().setImageBitmap(bitmap);
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }


    private Bitmap getBitmap(String url) {

        try {
            OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
            Request request = new Request.Builder()
                    .url(url)//请求接口。如果需要传参拼接到接口后面。
                    .build();//创建Request 对象
            Response response = client.newCall(request).execute();//得到Response 对象
            if (response.isSuccessful()) {
                Log.d("kwwl", "response.code()==" + response.code());
                Log.d("kwwl", "response.message()==" + response.message());

                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                //此时的代码执行在子线程，修改UI的操作请使用handler跳转到UI线程。

                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
