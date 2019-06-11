package com.z.imageloadtest;

import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.z.imageloadtest.imp.ImageLoadListener;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest("ddd");
            }
        });

        imageView = findViewById(R.id.image);


  /*      for (int i = 0; i < 10; i++) {
            addRequest(i+"");
        }
*/

    }
    private String imgUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559624" +
            "119735&di=9395094668576f56a69a5d9043f6bb41&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201804%2F29%2F20180429192715_ednpc.jpeg";
    private void addRequest(String tag) {

        ImageRequest imageRequest = new ImageRequest.Builder()
                .setUrl(imgUrl)
                .setImageView(imageView)
                .setImageLoadListener(new ImageLoadListener() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        Log.d("mytest","---success---");
                    }

                    @Override
                    public void onFail(Exception e) {

                        Log.d("mytest",e.getLocalizedMessage());
                    }
                })
                .build();

        ImageRequestManage.get(this).addRequest(imageRequest);




    }

}
