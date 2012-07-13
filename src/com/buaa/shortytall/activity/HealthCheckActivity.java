package com.buaa.shortytall.activity;

import com.buaa.shortytall.R;

import android.content.Context;
import android.view.View;
import android.view.Window;

public class HealthCheckActivity extends BaseActivity {

    @Override
    protected Context setContext() {
        return HealthCheckActivity.this;
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
        mFootBar.setPosition(4);
        View contentView = mInflater.inflate(R.layout.splash, null);
        return contentView;
    }

}
