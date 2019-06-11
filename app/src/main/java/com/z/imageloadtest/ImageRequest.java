package com.z.imageloadtest;

import android.widget.ImageView;

import com.z.imageloadtest.imp.ImageLoadListener;

public class ImageRequest {
    private static final ImageRequest ourInstance = new ImageRequest();

    public static ImageRequest getInstance() {
        return ourInstance;
    }

    public ImageRequest setBuilder(Builder builder) {
        this.builder = builder;
        return this;
    }

    public Builder getBuilder() {
        return builder;
    }


    private Builder builder;

    public static class Builder {
        private String url;
        private ImageView imageView;
        private ImageLoadListener imageLoadListener;

        public String getTag() {
            return tag;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        private String tag;

        public String getUrl() {
            return url;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public ImageLoadListener getImageLoadListener() {
            return imageLoadListener;
        }

        public Builder setImageLoadListener(ImageLoadListener imageLoadListener) {
            this.imageLoadListener = imageLoadListener;
            return this;
        }

        public ImageRequest build() {
            return getInstance().setBuilder(this);
        }

    }


}
