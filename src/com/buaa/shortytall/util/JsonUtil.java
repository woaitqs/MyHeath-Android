package com.buaa.shortytall.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.buaa.shortytall.bean.Comments;
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
        public static final String NEWS_ID = "news_id";
        
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String ICON = "icon";
        public static final String DETAILS = "details";
        public static final String PUBLISH_TIME = "publish_time";
        public static final String CATEGROY = "category";
        public static final String TAGS = "tags";
        public static final String COMMENTS = "comments";
        public static final String SCORE_AVG = "score_ave";
        public static final String NEWS_INDIVIDUAL = "news_individual";
        
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
                    Log.d("id", news.getString(Keys._ID));
                    allnews.add(newsitem);
                    //System.out.println(news.getString(Keys.ICON)+"      "+news.getLong(Keys.PUBLISH_TIME)+"     ");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allnews;
    }
    public static ArrayList<News> prasePersonalNewsJson(String json){
        ArrayList<News> allnews = new ArrayList<News>();
        try {
            
            JSONObject jsonobject = new JSONObject(json);
            if (jsonobject.getInt(Keys.ERRORCODE) == 0){
                JSONArray newses = jsonobject.getJSONArray(Keys.NEWS_INDIVIDUAL);
                for (int i = 0 ; i < newses.length(); i ++){
                    JSONObject news = (JSONObject)newses.get(i);
                    NewsBuider builder = new NewsBuider();
                    News newsitem = builder.build();
                    newsitem = builder.setAvatart("http://106.187.36.132:82/application/resources/news_imgs/news5002c8f887f3fb4017000001.jpg")
                    .setTitle(news.getString(Keys.TITLE))
                    .setContent(news.getString(Keys.DESCRIPTION))
                    .setDate(paresTimeLine((long)1342359800))
                    .setId(news.getString(Keys.NEWS_ID)).build();
                    //Log.d("id", news.getString(Keys._ID));
                    allnews.add(newsitem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allnews;
    }
    public static ArrayList<Comments> praseCommentsJson(String json){
        ArrayList<Comments> allcomments = new ArrayList<Comments>();
        try {
            
            JSONObject jsonobject = new JSONObject(json);
            JSONObject jsondrug = jsonobject.getJSONObject("drugs");
            String total = jsondrug.getString(Keys.SCORE_AVG);
            Comments commentitemtotal = new Comments("","",total,"");
            allcomments.add(commentitemtotal);
            if (jsonobject.getInt(Keys.ERRORCODE) == 0){
                JSONArray comments = jsondrug.getJSONArray(Keys.COMMENTS);
                for (int i = 0 ; i < comments.length(); i ++){
                    JSONObject comment = (JSONObject)comments.get(i);
                    Comments commentitem = new Comments(comment.getString("user"),comment.getString("description"),comment.getString("score"),comment.getString("comment_time"));
                 
                    allcomments.add(commentitem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allcomments;
    }
}
