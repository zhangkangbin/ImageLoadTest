package com.z.imageloadtest.cache;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class DiskLruCacheHelper {
    private static DiskLruCache diskLruCache;

    private DiskLruCacheHelper() {
    }

    public static DiskLruCache get() {
        if (diskLruCache == null) {
            synchronized (DiskLruCacheHelper.class) {

                if (diskLruCache == null) {
                    try {
                        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/imageTest");
                        diskLruCache = DiskLruCache.open(file,
                                22, 1, 1024 * 1024);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        return diskLruCache;
    }
}
