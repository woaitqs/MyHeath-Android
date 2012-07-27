package com.buaa.shortytall;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

public class MyHealth extends Application{

    private static Context mContext;
    
    public static int SPALSH_TIME = 2000;
    
    public static final String APP_SDCARD_FOLDER = 
            Environment.getExternalStorageDirectory().getAbsolutePath();
    
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getCurrentContext(){
        return mContext;
    }
    
    public static class Body_Area{
        public static final int HEAD = 100;
        public static final int NECK = 101;
        public static final int BREAST = 102;
        public static final int BELLY = 103;
        public static final int UPPER_LIMB= 104;
        public static final int LOWER_LIMB = 105;
        public static final int REPRODUCTIVE = 106;
    }
    
    public static class Url{
        public static final String BASE_URL = "http://106.187.36.132:82";
    }
    
    public static class Msg{
        public static final int GET_ALLNEWS_SUCCESSED = 0;
        public static final int GET_ALLNEWS_fAILED = 1;
        public static final int FLASH_IMG_FINISHED = 2;
        public static final int IMG_LOADED_COMPLETED = 3;
        public static final int GET_NEWS_DETAIL_SUCCESSED = 4;
        public static final int GET_NEWS_DETAIL_FAILED= 5;
        public static final int GET_ALLCOMMENTS_SUCCESSED = 7;
        public static final int GET_ALLCOMMENTS_fAILED = 8;
        public static final int GET_ALLPERSONNEWS_SUCCESSED = 9;
        public static final int GET_ALLPERSONNEWS_FAILD = 10;
        public static final int NEWS_FRESH = 11;
    }
    
    public static class Bundle_keys{
        public static final String NEWS_JSON = "news_json";
        public static final String PERSON_NEWS_JSON = "person_news_json";
        public static final String COMMENTS_JSON = "comments_json";
        public static final String DISEASE_TYPE = "disease_type";
        public static final String DETAIL_JSON = "detail_json";
        public static final String NEWS_ID = "news_id";
        public static final String DISEASE = "disease";
        
        public static final String DISEASE_NAME = "disease_name";
        public static final String DISEASE_DESCRIPTION = "disease_description";
        public static final String DISEASE_DRUGS = "disease_drugs";
        public static final String DISEASE_TIPS = "disease_tips";
    }
}
