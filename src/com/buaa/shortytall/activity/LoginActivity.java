package com.buaa.shortytall.activity;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.buaa.shortytall.R;

public class LoginActivity extends BaseActivity {

    private EditText mUserNameText;
    private EditText mPassWordText;
    private Button mLoginButton;
    
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
        mUserNameText = (EditText)findViewById(R.id.login_username_edittext);
        mPassWordText = (EditText)findViewById(R.id.login_password_edittext);
        mLoginButton = (Button)findViewById(R.id.login_login_button);
    }
    
    
}