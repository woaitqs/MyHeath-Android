package com.buaa.shortytall.activity;

import android.content.Context;

public class NewsDetailActivity extends DefaultActivity {

    @Override
    protected String getActionBarTitle() {
        return "News Detail";
    }

    @Override
    protected Context getContext() {
        return NewsDetailActivity.this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        
    }

    private void startGettingDeatil(){
        
    }
}
