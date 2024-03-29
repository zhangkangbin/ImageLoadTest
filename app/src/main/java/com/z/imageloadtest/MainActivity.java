package com.z.imageloadtest;

import android.Manifest;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.z.imageloadtest.imp.ImageLoadListener;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        EasyPermissions.requestPermissions(this, "权限kkk",
                1, perms);

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
    private String imgUrl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1561361401&di" +
            "=a91d3393cfc6c71f6652dca51bed4c1b&imgtype=jpg&er=1&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201802%2F24%2F20180224130825_bsgtr.png";
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
