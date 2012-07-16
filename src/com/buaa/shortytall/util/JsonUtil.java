package com.buaa.shortytall.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.buaa.shortytall.bean.News;

public class JsonUtil {

    public static class Keys{
        public static final String NEWS = "news";
        public static final String ITEM = "item";
        public static final String ID = "id";
        public static final String _ID = "_id";
        public static final String ICON = "icon";
        public static final String DETAILS = "details";
        public static final String PUBLISH_TIME = "publish_time";
        public static final String CATEGROY = "category";
        public static final String TAGS = "tags";
        public static final String COMMENT_COUNT = "comment_count";
        public static final String COMMENTS = "comments";
    }
    
    
    public static News praseNewsJson(String json){
        try {
            JSONObject jsonobj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
