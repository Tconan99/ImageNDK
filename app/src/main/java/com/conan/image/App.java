package com.conan.image;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }


    private static Context mContext;
    public static Context context() {
        return mContext;
    }
}
