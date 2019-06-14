package com.z.imageloadtest;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


public class ImageRequestManage {


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
        fixedThreadPool.execute(new ExecuteRunnable(linkedBlockingQueue));
    }


}
