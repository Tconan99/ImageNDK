package com.conan.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

public class JavaImageUtil {

    public static float brightness = 0.2f;
    public static float constrat = 0.2f;

    public static Bitmap convertBitmap(@NonNull Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        for (int x=0; x<width; ++x) {
            for (int y=0; y<height; ++y) {
                int color = bitmap.getPixel(x, y);

                int a = Color.alpha(color);
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);

                result.setPixel(x, y, Color.argb(a, convert(r), convert(g), convert(b)));
            }
        }

        return result;
    }


    private static boolean isRunning = false;
    private static Bitmap result = null;
    public static void convertBitmap(@NonNull final Bitmap bitmap, final CallBack callBack) {

        if (isRunning) {
            return;
        }
        isRunning = true;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                boolean isSuccess = true;
                if (msg.what!=1) {
                    isSuccess = false;
                }
                if (callBack!=null) {
                    callBack.onFinish(isSuccess, result);
                }
                isRunning = false;
            }
        };

        new Thread() {
            @Override
            public void run() {
                boolean isSuccess = true;
                try {
                    result = convertBitmap(bitmap);
                } catch (Exception e) {
                    isSuccess = false;
                } finally {
                    handler.sendEmptyMessage(isSuccess?1:0);
                }

            }
        }.start();
    }

    public static int convert(int r) {
        int bab = (int)(255*brightness);
        float ca = 1.0f + constrat;
        ca = ca * ca;
        int cab = (int)(ca*65536) + 1;

        int ri = r - bab;
        r = ri>255?255:(ri<0?0:ri);
        ri = r - 128;
        ri = (ri*cab)>>16;
        ri = ri + 128;
        r = ri>255?255:(ri<0?0:ri);
        return r;

    }

    public interface CallBack {
        void onFinish(boolean isSuccess, Bitmap bitmap);
    }
}
