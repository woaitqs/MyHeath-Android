package com.buaa.shortytall.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.network.AbstractNetWorkThread;

public class GetAllPersonalNewsThread extends AbstractNetWorkThread implements Runnable{

    private String mUrl;
    private GetAllPersonalNewsHandler handler;
    
    @Override
    public void run() {
        try {
            String result = executeGet();
            Message msg = new Message();
            if (result != null ){
                Bundle mBundle = new Bundle();
                mBundle.putString(MyHealth.Bundle_keys.PERSON_NEWS_JSON, result);
                msg.setData(mBundle);
                msg.what = MyHealth.Msg.GET_ALLPERSONNEWS_SUCCESSED;
                System.out.println("get all personnews successsed");
            } else{
                // network error
                msg.what = MyHealth.Msg.GET_ALLPERSONNEWS_FAILD;
            }
            handler.sendMessage(msg);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    public GetAllPersonalNewsThread(GetAllPersonalNewsHandler handler){
        this.handler = handler;
    }
    
    public static interface GetAllPersonNewsListener{
        
        public void getAllPersonalNewsSuccessed(String json);
        
        public void getAllPersonalNewsFailed();
    }
    
    public static class GetAllPersonalNewsHandler extends Handler{
        private GetAllPersonNewsListener listener;
        
        public GetAllPersonalNewsHandler(GetAllPersonNewsListener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_ALLPERSONNEWS_SUCCESSED:
                Bundle bundle = msg.getData();
                String json = bundle.getString(MyHealth.Bundle_keys.PERSON_NEWS_JSON);
                listener.getAllPersonalNewsSuccessed(json);
                break;
            case MyHealth.Msg.GET_ALLPERSONNEWS_FAILD:
                listener.getAllPersonalNewsFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
        mUrl = MyHealth.Url.BASE_URL +"/index.php/users_c/news_individual/format/json";
        return mUrl;
    }

}
