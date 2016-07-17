package com.conan.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.conan.ndk.image.ImageUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    Bitmap bitmap = null;

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.image);

        imageView = (ImageView) findViewById(R.id.image);

        onSourceClick(null);
    }

    @OnClick(R.id.source)
    public void onSourceClick(View view){
        long time = System.currentTimeMillis();
        imageView.setImageBitmap(bitmap);
        ToastUtils.show(String.valueOf(System.currentTimeMillis() - time));
    }

    @OnClick(R.id.java)
    public void onJavaClick(View view){
        long time = System.currentTimeMillis();
        imageView.setImageBitmap(JavaImageUtil.convertBitmap(bitmap));
        ToastUtils.show(String.valueOf(System.currentTimeMillis() - time));
    }

    @OnClick(R.id.java_callback)
    public void onJavaCallBackClick(View view){
        final long time = System.currentTimeMillis();
        JavaImageUtil.convertBitmap(bitmap, new JavaImageUtil.CallBack() {
            @Override
            public void onFinish(boolean isSuccess, Bitmap bitmap) {
                if (isSuccess) {
                    imageView.setImageBitmap(bitmap);
                }
                ToastUtils.show(String.valueOf(System.currentTimeMillis() - time));
            }
        });
    }

    @OnClick(R.id.ndk)
    public void onNdkClick(View view){
        Log.i("111", "1111111");
        long time = System.currentTimeMillis();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] buffer = new int[width*height];

        bitmap.getPixels(buffer, 0, width, 0, 0, width, height);

        int[] result = ImageUtil.convertBitmap(buffer, width, height);
        Bitmap bitmap = Bitmap.createBitmap(result, width, height, Bitmap.Config.RGB_565);
        imageView.setImageBitmap(bitmap);

        ToastUtils.show(String.valueOf(System.currentTimeMillis() - time));
    }

}
