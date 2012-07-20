package com.buaa.shortytall.thread;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.network.AbstractNetWorkThread;

public class GetAllCommentsThread extends AbstractNetWorkThread implements Runnable{

    private String mUrl;
    private String drugid;
    private GetAllCommentsHandler handler;
    
    @Override
    public void run() {
        try {
            String result = executeGet();
            assert(result != null);
            //Log.d("the result", result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public GetAllCommentsThread(GetAllCommentsHandler handler,String drug_id){
    	drugid = drug_id.toString();  
        this.handler = handler;
    }
    
    public static interface GetAllCommentsListener{
        
        public void getAllCommentsSuccessed();
        
        public void getAllCommentsFailed();
    }
    
    public static class GetAllCommentsHandler extends Handler{
        private GetAllCommentsListener listener;
        
        public GetAllCommentsHandler(GetAllCommentsListener listener){
            this.listener = listener;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MyHealth.Msg.GET_ALLNEWS_SUCCESSED:
                listener.getAllCommentsSuccessed();
                break;
            case MyHealth.Msg.GET_ALLNEWS_fAILED:
                listener.getAllCommentsFailed();
                break;
            default:
                break;
            }
        }
        
    }

    @Override
    public String getRequestUrl() {
    	//TODO api url
        mUrl = MyHealth.Url.BASE_URL +  "/index.php/drugs_c/comment"+drugid;
        return mUrl;
    }

}
