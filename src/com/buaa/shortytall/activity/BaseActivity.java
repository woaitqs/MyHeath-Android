package com.buaa.shortytall.activity;

import com.buaa.shortytall.R;
import com.buaa.shortytall.view.FooterBar;
import com.buaa.shortytall.view.NavigationBar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public abstract class BaseActivity extends Activity {

    protected NavigationBar mNavigationBar;
    protected FooterBar mFootBar;
    protected View mContentView;
    protected LayoutInflater mInflater;
    
    private Context mContext;
    private LinearLayout mMainView;
    
    public BaseActivity(){
    }
    
    protected abstract Context setContext();
    
    protected abstract void initWindows();
    
    protected abstract void initListeners();
    
    protected abstract void initThreads();
    
    protected abstract View initViews();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        mContext = setContext();
        
        /*init main base view*/
        setContentView(R.layout.base);
        mInflater = LayoutInflater.from(mContext);
        mNavigationBar = (NavigationBar)findViewById(R.id.main_navigationbar);
        mFootBar = (FooterBar)findViewById(R.id.main_footer);
        mMainView = (LinearLayout)findViewById(R.id.main_content);
        mContentView = initViews();
        if (mContentView != null){
            mMainView.addView(mContentView, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        /*end init views*/
        
        initListeners();
        initThreads();
    }

}

