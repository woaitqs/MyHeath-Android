package com.buaa.shortytall.activity;

import android.view.Window;

import com.buaa.shortytall.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected void initWindows() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void initListeners() {
        
    }

    @Override
    protected void initThreads() {
        
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.login);
    }
}