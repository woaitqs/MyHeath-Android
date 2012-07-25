package com.buaa.shortytall.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.network.AbstractNetWorkThread;

public class GetAllPersonDrugNewsThread extends AbstractNetWorkThread implements Runnable{

    private String mUrl;
    private GetAllDrugHandler handler;
    
    @Override
    public void run() {
        try {
            String result = executeGet();
            Message msg = new Message();
            if (result != null ){
                Bundle mBundle = new Bundle();
                mBundle.putString(MyHealth.Bundle_keys.NEWS_JSON, result);
                msg.setData(mBundle);
                msg.what = MyHealth.Msg.GET_ALLNEWS_SUCCESSED;
            } else{
                // network error
                msg.what = MyHealth.Msg.GET_ALLNEWS_fAILED;
            }
            handler.sendMessage(msg);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GetAllPersonDrugNewsThread(GetAllDrugHandler handler){
        this.handler = handler;
    }
    
    public static interface GetAllDrugListener{
        
        public void getAllDrugSuccessed(String json);
        
        public void getAllDrugFailed();
    }
    
    public static class GetAllDrugHandler extends Handler{
        private GetAllDrugListener listener;
        
        public GetAllDrugHandler(GetAllDrugListener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_ALLNEWS_SUCCESSED:
                Bundle bundle = msg.getData();
                String json = bundle.getString(MyHealth.Bundle_keys.NEWS_JSON);
                listener.getAllDrugSuccessed(json);
                break;
            case MyHealth.Msg.GET_ALLNEWS_fAILED:
                listener.getAllDrugFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
        mUrl = MyHealth.Url.BASE_URL +  "/index.php/users_c/drugs_collected/format/json";
        return mUrl;
    }

}
