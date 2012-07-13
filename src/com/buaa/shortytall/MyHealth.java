package com.buaa.shortytall;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

public class MyHealth extends Application{

    private static Context mContext;
    
    public static int SPALSH_TIME = 2000;
    
    public static final String APP_SDCARD_FOLDER = 
            Environment.getExternalStorageDirectory().toString() + "/.imagecache/";
    
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getCurrentContext(){
        return mContext;
    }
    
    public static class Url{
        public static final String BASE_URL = "http://192.168.2.1";
    }
    
    public static class Msg{
        public static final int GET_ALLNEWS_SUCCESSED = 0;
        public static final int GET_ALLNEWS_fAILED = 1;
        public static final int FLASH_IMG_FINISHED = 2;
    }
}
