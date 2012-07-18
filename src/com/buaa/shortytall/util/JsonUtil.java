package com.buaa.shortytall.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.bean.News.NewsBuider;

public class JsonUtil {

    public static class Keys{
        
        //general
        public static final String ERRORCODE = "error";
        
        //this is for news
        public static final String NEWS = "news";
        public static final String ITEM = "item";
        public static final String ID = "id";
        public static final String _ID = "_id";
        public static final String TITLE = "title";
        public static final String ICON = "icon";
        public static final String DETAILS = "details";
        public static final String PUBLISH_TIME = "publish_time";
        public static final String CATEGROY = "category";
        public static final String TAGS = "tags";
        public static final String COMMENTS = "comments";
    }
    
    
    private static String paresTimeLine(long date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(date * 1000L));
    }
    
    public static ArrayList<News> praseNewsJson(String json){
        ArrayList<News> allnews = new ArrayList<News>();
        try {
            
            JSONObject jsonobject = new JSONObject(json);
            if (jsonobject.getInt(Keys.ERRORCODE) == 0){
                JSONArray newses = jsonobject.getJSONArray(Keys.NEWS);
                for (int i = 0 ; i < newses.length(); i ++){
                    JSONObject news = (JSONObject)newses.get(i);
                    NewsBuider builder = new NewsBuider();
                    News newsitem = builder.build();
                    newsitem = builder.setAvatart(news.getString(Keys.ICON))
                    .setTitle(news.getString(Keys.TITLE))
                    .setContent(news.getString(Keys.DETAILS))
                    .setDate(paresTimeLine(news.getLong(Keys.PUBLISH_TIME)))
                    .setId(news.getString(Keys._ID)).build();
                    
                    allnews.add(newsitem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allnews;
    }
}
