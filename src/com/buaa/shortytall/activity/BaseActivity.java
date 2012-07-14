package com.buaa.shortytall.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.buaa.shortytall.R;
import com.buaa.shortytall.view.FooterBar;
import com.buaa.shortytall.view.NavigationBar;

public abstract class BaseActivity extends Activity implements View.OnClickListener{

    protected NavigationBar mNavigationBar;
    protected FooterBar mFootBar;
    protected View mContentView;
    protected LayoutInflater mInflater;
    
    private Context mContext;
    private LinearLayout mMainView;
    
    protected ImageView HomeView;
    protected ImageView CalcView;
    protected ImageView PersonalView;
    protected ImageView SearchView;
    protected ImageView CheckView;
    
    
    public BaseActivity(){
    }
    
    protected abstract Context setContext();
    
    protected abstract void initWindows();
    
    protected abstract void initListeners();
    
    protected abstract void initThreads();
    
    protected abstract View initViews(); 
    
    private void initBaseActivityListener(){
        HomeView.setOnClickListener(BaseActivity.this);
        CalcView.setOnClickListener(BaseActivity.this);
        PersonalView.setOnClickListener(BaseActivity.this);
        SearchView.setOnClickListener(BaseActivity.this);
        CheckView.setOnClickListener(BaseActivity.this);
    }
    
    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.footerbar_home:
            startActivity(new Intent(mContext, MainActivity.class));
            ((Activity)mContext).finish();
            break;
        case R.id.footerbar_calc:
            break;
        case R.id.footerbar_personal:
            break;
        case R.id.footerbar_search:
        	 startActivity(new Intent(mContext, SearchActivity.class));
             ((Activity)mContext).finish();
            break;
        case R.id.footerbar_check:
            startActivity(new Intent(mContext, HealthCheckActivity.class));
            ((Activity)mContext).finish();
            break;
        }
        
    }

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
        HomeView = (ImageView)findViewById(R.id.footerbar_home);
        CalcView = (ImageView)findViewById(R.id.footerbar_calc);
        PersonalView = (ImageView)findViewById(R.id.footerbar_personal);
        SearchView = (ImageView)findViewById(R.id.footerbar_search);
        CheckView = (ImageView)findViewById(R.id.footerbar_check);
        initBaseActivityListener();
        
        mContentView = initViews();
        if (mContentView != null){
            mMainView.addView(mContentView, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        }
        /*end init views*/
        
        initListeners();
        initThreads();
    }

}

