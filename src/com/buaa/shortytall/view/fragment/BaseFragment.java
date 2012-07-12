package com.buaa.shortytall.view.fragment;

import com.buaa.shortytall.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class BaseFragment extends Fragment{

    public BaseFragment() {
        super();
    }
    
    protected Handler handler;
    protected Context context;
    protected LinearLayout fragmentFrameView;
    protected View contentView;
    protected boolean dataLoaded;
    protected LayoutInflater inflater;
    
    protected LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
            LayoutParams.FILL_PARENT);
    
    public BaseFragment(Handler handler, Context context) {
        this.handler = handler;
        this.context = context;
        inflater = LayoutInflater.from(context);
        fragmentFrameView = (LinearLayout)inflater.inflate(R.layout.null_frame, null);
        contentView = inflater.inflate(getLayout(), null);
        onInflatedView();
        fragmentFrameView.addView(contentView);
    }
    
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        throw new UnsupportedOperationException("this base fragment does not support xml definition");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }
    
    public abstract void handleMessage(Message message);
    
    public abstract void onInflatedView();
    
    protected abstract int getLayout();
    
    @Override
    public View getView() {
        return fragmentFrameView;
    }
    
}
