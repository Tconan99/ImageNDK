package com.conan.ndk.image;

public class ImageUtil {

    static {
        System.loadLibrary("ImageNDK");
    }

    public static native int[] convertBitmap(int[] buffer, int width, int height);
}
