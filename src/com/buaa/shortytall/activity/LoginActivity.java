package com.buaa.shortytall.activity;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.buaa.shortytall.R;

public class LoginActivity extends BaseActivity{

    private EditText mUserNameText;
    private EditText mPassWordText;
    private Button mLoginButton;
    
    @Override
    protected Context setContext() {
        return LoginActivity.this;
    }
    
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
    protected View initViews() {
        View contentView = mInflater.inflate(R.layout.login, null);
        
        return contentView;
    }
    
    
}