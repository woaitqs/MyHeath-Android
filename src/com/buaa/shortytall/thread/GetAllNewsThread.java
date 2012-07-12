package com.buaa.shortytall.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.network.AbstractNetWorkThread;

public class GetAllNewsThread extends AbstractNetWorkThread implements Runnable{

    private String mUrl;
    private GetAllNewsHandler handler;
    
    @Override
    public void run() {
        try {
            String result = executeGet();
            assert(result != null);
            Log.d("****************888", result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GetAllNewsThread(GetAllNewsHandler handler){
        this.handler = handler;
    }
    
    public static interface GetAllNewsListener{
        
        public void getAllNewsSuccessed();
        
        public void getAllNewsFailed();
    }
    
    public static class GetAllNewsHandler extends Handler{
        private GetAllNewsListener listener;
        
        public GetAllNewsHandler(GetAllNewsListener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_ALLNEWS_SUCCESSED:
                listener.getAllNewsSuccessed();
                break;
            case MyHealth.Msg.GET_ALLNEWS_fAILED:
                listener.getAllNewsFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
        mUrl = MyHealth.Url.BASE_URL +  "/myhealth1.0/index.php/news_c/all";
        return mUrl;
    }

}
