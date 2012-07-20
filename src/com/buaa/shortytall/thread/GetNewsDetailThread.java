package com.buaa.shortytall.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.network.AbstractNetWorkThread;

public class GetNewsDetailThread extends AbstractNetWorkThread implements Runnable{

    private String mUrl;
    private String mId;
    private GetNewsDetailHandler handler;
    
    @Override
    public void run() {
        try {
            String result = executeGet();
            Message msg = new Message();
            if (result != null ){
                Bundle mBundle = new Bundle();
                mBundle.putString(MyHealth.Bundle_keys.DETAIL_JSON, result);
                msg.setData(mBundle);
                msg.what = MyHealth.Msg.GET_NEWS_DETAIL_SUCCESSED;
            } else{
                // network error
                msg.what = MyHealth.Msg.GET_NEWS_DETAIL_FAILED;
            }
            handler.sendMessage(msg);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GetNewsDetailThread(GetNewsDetailHandler handler, String id){
        this.handler = handler;
        this.mId = id;
    }
    
    public static interface GetNewsDetailLisntener{
        
        public void getNewsDetailSuccessed(String json);
        
        public void getNewsDetailFailed();
    }
    
    public static class GetNewsDetailHandler extends Handler{
        private GetNewsDetailLisntener listener;
        
        public GetNewsDetailHandler(GetNewsDetailLisntener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_ALLNEWS_SUCCESSED:
                Bundle bundle = msg.getData();
                String json = bundle.getString(MyHealth.Bundle_keys.NEWS_JSON);
                listener.getNewsDetailSuccessed(json);
                break;
            case MyHealth.Msg.GET_ALLNEWS_fAILED:
                listener.getNewsDetailFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
        mUrl = MyHealth.Url.BASE_URL +  "/index.php/news_c/spec/id/%s";
        mUrl = String.format(mUrl, mId);
        return mUrl;
    }
}
