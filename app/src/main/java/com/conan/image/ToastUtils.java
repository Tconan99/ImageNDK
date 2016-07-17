package com.conan.image;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by Tconan on 2016/7/14.
 */
public class ToastUtils {

    public static Toast mToast;
    public static void show(CharSequence content) {

        if (TextUtils.isEmpty(content)) {
            return;
        }

        if (mToast!=null) {
            mToast.cancel();
        }

        mToast = Toast.makeText(App.context(), content, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
