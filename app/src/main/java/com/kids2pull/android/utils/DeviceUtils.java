package com.kids2pull.android.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;

/**
 * Created by livnatavikasis on 10/03/2017.
 */

public class DeviceUtils {

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            return size.y;
        } else {
            return display.getHeight();
        }
    }
}
