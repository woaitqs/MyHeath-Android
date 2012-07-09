package com.buaa.shortytall.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ViewUtil {

    public static Bitmap convetDrawable(Drawable drawable){
        return ((BitmapDrawable)drawable).getBitmap();
    }
}
