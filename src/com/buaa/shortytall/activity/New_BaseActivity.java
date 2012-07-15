package com.buaa.shortytall.activity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.buaa.shortytall.view.AsyncInflater;
import com.buaa.shortytall.view.InflateListener;
import com.buaa.shortytall.view.fragment.New_BaseFragment;

public abstract class New_BaseActivity extends SherlockFragmentActivity{

    //share one handler
    protected Handler handler;
    protected ArrayList<New_BaseFragment> fragments = new ArrayList<New_BaseFragment>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                New_BaseActivity.this.handleMessage(msg);
            }
            
        };
    }

    protected synchronized void handleMessage(Message msg) {
        for (int i = 0; i < fragments.size(); ++i) {
            fragments.get(i).handleMessage(msg);
        }
    }
    
    protected void asyncInflateView(ViewGroup root, int layoutId, InflateListener listener) {
        AsyncInflater.getInstance().asyncInflate(LayoutInflater.from(this), layoutId,
                new WeakReference<Handler>(handler), new WeakReference<InflateListener>(listener));
    }
    
    protected synchronized void addMessageHandler(New_BaseFragment fragment) {
        if (fragment != null) {
            fragments.add(fragment);
        }
    }
    
    public ActionMode createActionMode(ActionMode.Callback callback) {
        return getSherlock().startActionMode(callback);
    }
    
    @Override
    public void onActionModeFinished(ActionMode mode) {
        for (New_BaseFragment fragment: fragments) {
            fragment.onActionModeFinished(mode);
        }
    }
    
    protected abstract void initTabs();
    
    @Override
    public void onActionModeStarted(ActionMode mode) {
        for (New_BaseFragment fragment: fragments) {
            fragment.onActionModeStarted(mode);
        }
    }
    
    protected synchronized void removeMessageHandler(New_BaseFragment fragment) {
        fragments.remove(fragment);
    }
}
