package com.buaa.shortytall.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

public class ViewUtil {

    public static Bitmap convetDrawable(Drawable drawable){
        if (drawable instanceof NinePatchDrawable){
            return null;
        }
        return ((BitmapDrawable)drawable).getBitmap();
    }
}
