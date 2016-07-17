//
// Created by Tconan on 2016/7/17.
//
#include<stdlib.h>
#include<android/bitmap.h>

jintArray JNICALL Java_com_conan_ndk_image_ImageUtil_convertBitmap(JNIEnv *env, jclass jclz, jintArray buffer, jint width, jint height) {
        jint* source = (*env)->GetIntArrayElements(env, buffer, 0);
        int newSize = width * height;

        int x = 0, y = 0;
        for (x=0; x<width; ++x) {
                for (y=0; y<height; ++y) {
                        int color = source[width*y + x];

                        int a = (color >> 24) & 0xFF;
                        int r = (color >> 16) & 0xFF;
                        int g = (color >> 8) & 0xFF;
                        int b = color & 0xFF;


                        r = convert(r);
                        g = convert(g);
                        b = convert(b);

                        source[width*y + x] = (a<<24) | (r<<16) | (g << 8) | b;
                }
        }


        jintArray result = (*env)->NewIntArray(env, newSize);
        (*env)->SetIntArrayRegion(env, result, 0, newSize, source);
        (*env)->ReleaseIntArrayElements(env, buffer, source, 0);
        return result;
}


int convert(int r) {
        float brightness = 0.2f, constrat = 0.2f;

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