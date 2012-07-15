package com.buaa.shortytall.view.fragment;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.buaa.shortytall.R;
import com.buaa.shortytall.view.AsyncInflater;
import com.buaa.shortytall.view.InflateListener;

public abstract class New_BaseFragment extends SherlockFragment implements InflateListener, ViewPager.OnPageChangeListener{
    
    protected Handler handler;
    protected Context context;
    protected LinearLayout fragmentFrameView;
    protected View contentView;
    protected boolean dataLoaded;
    protected LayoutInflater inflater;

    protected Tab tab;
    protected TextView customTabView;
    protected boolean isCurrentTab;
    
    protected LinearLayout.LayoutParams defaultLayoutParams = new LinearLayout.LayoutParams(
            LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    
    public New_BaseFragment(Handler handler,Context context) {
        this.handler = handler;
        this.context = context;
        inflater = LayoutInflater.from(context);
        fragmentFrameView = (LinearLayout) inflater.inflate(R.layout.new_base_fragment, null);
        
        int layoutId = getAsyncInitViewResId();
        if (layoutId > 0) {
            asyncInitView(layoutId);
        }
        tab = initTab();
    }
    
    protected abstract Tab initTab();
    
    public Tab getTab() {
        return tab;
    }
    
    @Override
    public final void onInflatedView(View view) {
        contentView = view;
        fragmentFrameView.addView(view, defaultLayoutParams);
        if (preOnInflated()) {
            onInflated();
        }

    };
    
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub

    }

    public void onTabSelected() {
        isCurrentTab = true;
    }
    public void onTabUnselected() {
        
    }
    
    private void asyncInitView(int layoutId) {
        AsyncInflater.getInstance().asyncInflate(LayoutInflater.from(context),
                layoutId, new WeakReference<Handler>(handler),
                new WeakReference<InflateListener>(this));
    }
    
    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        
        throw new UnsupportedOperationException(
                "this base fragment does not support xml definition");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    public abstract void handleMessage(Message message);
    
    public abstract int getAsyncInitViewResId();
    
    protected abstract void onInflated();
    protected boolean preOnInflated() {
        return true;
    }

    @Override
    public View getView() {
        return fragmentFrameView;
    }
    
    protected void setActionBarTitle(String title) {
        if (context instanceof SherlockFragmentActivity) {
            SherlockFragmentActivity activity = (SherlockFragmentActivity) context;
            activity.getSupportActionBar().setTitle(title);
        }

    }

    protected void setDisplayHomeAsUpEnabled(boolean enable) {
        if (context instanceof SherlockFragmentActivity) {
            SherlockFragmentActivity activity = (SherlockFragmentActivity) context;
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        }
    }

    public void onActionModeFinished(ActionMode mode) {

    }

    public void onActionModeFinished(android.view.ActionMode mode) {

    }

    public void onActionModeStarted(ActionMode mode) {

    }

    public void onActionModeStarted(android.view.ActionMode mode) {

    }
    
    public void onNetworkChange() {
        
    }
    
}
