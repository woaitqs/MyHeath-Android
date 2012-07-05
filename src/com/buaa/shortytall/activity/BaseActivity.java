package com.buaa.shortytall.activity;

import android.app.Activity;
import android.os.Bundle;

public abstract class BaseActivity extends Activity {

    protected abstract void initWindows();
    
    protected abstract void initListeners();
    
    protected abstract void initThreads();
    
    protected abstract void initViews();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        initViews();
        initListeners();
        initThreads();
    }

}

