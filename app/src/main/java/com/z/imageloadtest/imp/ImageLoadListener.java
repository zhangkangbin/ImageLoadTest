package com.z.imageloadtest.imp;

import android.graphics.Bitmap;

public interface ImageLoadListener {
    void onSuccess(Bitmap bitmap);

    void onFail(Exception e);
}
