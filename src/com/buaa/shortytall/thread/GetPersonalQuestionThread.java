package com.buaa.shortytall.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.network.AbstractNetWorkThread;

public class GetPersonalQuestionThread extends AbstractNetWorkThread implements Runnable{

    private String mUrl;
    private GetQuestionHandler handler;
    
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
    
    public GetPersonalQuestionThread(GetQuestionHandler handler){
        this.handler = handler;
    }
    
    public static interface GetQusetionDetailLisntener{
        
        public void getQuestionSuccessed(String json);
        
        public void getQuestionFailed();
    }
    
    public static class GetQuestionHandler extends Handler{
        private GetQusetionDetailLisntener listener;
        
        public GetQuestionHandler(GetQusetionDetailLisntener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_NEWS_DETAIL_SUCCESSED:
                Bundle bundle = msg.getData();
                String json = bundle.getString(MyHealth.Bundle_keys.DETAIL_JSON);
                listener.getQuestionSuccessed(json);
                break;
            case MyHealth.Msg.GET_NEWS_DETAIL_FAILED:
                listener.getQuestionFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
        mUrl = MyHealth.Url.BASE_URL +  "/index.php/users_c/questions/format/json";
        return mUrl;
    }
}
