package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.List;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.buaa.shortytall.adapter.NewsAdapter;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.thread.GetAllNewsThread;
import com.buaa.shortytall.thread.GetAllNewsThread.GetAllNewsHandler;
import com.buaa.shortytall.thread.GetAllPersonalNewsThread;
import com.buaa.shortytall.thread.GetAllPersonalNewsThread.GetAllPersonNewsListener;
import com.buaa.shortytall.thread.GetAllPersonalNewsThread.GetAllPersonalNewsHandler;
import com.buaa.shortytall.util.JsonUtil;


public class ProfileNewsActivity extends DefaultActivity implements GetAllPersonNewsListener{
	
	
	private ListView myList;
	private List<News> mNews;
	private NewsAdapter mNewsAdapter;
	private Handler myhandler;
	
	
	
	 @Override
	    public void onResume() {
	        super.onResume();
	        GetAllPersonalNewsHandler handler = new GetAllPersonalNewsHandler(ProfileNewsActivity.this);
	        GetAllPersonalNewsThread thread = new GetAllPersonalNewsThread(handler);
	        thread.start();
	    }
	

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "个人资讯";
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return ProfileNewsActivity.this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.personal_new);
		 myList = (ListView)findViewById(R.id.personalnews_listview);
		 mNewsAdapter =  new NewsAdapter(this, null);
		 myList.setAdapter(mNewsAdapter);
	}
	
	 

	@Override
	public void getAllPersonalNewsSuccessed(String json) {
		// TODO Auto-generated method stub
		System.out.println("the json activity get "+json);
		 ArrayList<News> data = JsonUtil.prasePersonalNewsJson(json);
	     mNewsAdapter.setData(data);   
		
	}


	@Override
	public void getAllPersonalNewsFailed() {
		// TODO Auto-generated method stub
		
		
	}
	
}
