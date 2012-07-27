package com.buaa.shortytall.activity;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.thread.GetNewsDetailThread;
import com.buaa.shortytall.thread.GetNewsDetailThread.GetNewsDetailHandler;
import com.buaa.shortytall.thread.GetNewsDetailThread.GetNewsDetailLisntener;
import com.buaa.shortytall.util.JsonUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetailActivity extends DefaultActivity implements GetNewsDetailLisntener{

    private String mId;
    private TextView mTitle;
    private TextView mContent;
    private TextView mDate;
    private ProgressBar mProgress;
    
    @Override
    protected String getActionBarTitle() {
        return "News Detail";
    }

    @Override
    protected Context getContext() {
        return NewsDetailActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        mTitle = (TextView)findViewById(R.id.news_detail_title);
        mContent = (TextView)findViewById(R.id.news_detail_content);
        mDate = (TextView)findViewById(R.id.news_detail_publish_time);
        mProgress = (ProgressBar)findViewById(R.id.news_detail_circleProgressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null || TextUtils.isEmpty(intent.getStringExtra(MyHealth.Bundle_keys.NEWS_ID))){
            finish();
        }
        mId = intent.getStringExtra(MyHealth.Bundle_keys.NEWS_ID);
        startGettingDeatil(mId);
    }

    private void startGettingDeatil(String id){
        GetNewsDetailHandler handler = new GetNewsDetailHandler(NewsDetailActivity.this);
        GetNewsDetailThread thread = new GetNewsDetailThread(handler, id);
        Log.e("id", id);
        thread.start();
    }

    @Override
    public void getNewsDetailSuccessed(String json) {
//        Toast.makeText(NewsDetailActivity.this, json, Toast.LENGTH_LONG).show();
        News news = JsonUtil.praseDetailJson(json);
        mProgress.setVisibility(View.GONE);
        mTitle.setText(news.getmTitle());
        mContent.setText(news.getmContent());
        mDate.setText(news.getmDate());
    }

    @Override
    public void getNewsDetailFailed() {
        mProgress.setVisibility(View.GONE);
        Toast.makeText(NewsDetailActivity.this, getString(R.string.news_load_error), Toast.LENGTH_SHORT).show();
    }
}

