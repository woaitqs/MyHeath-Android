package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.SimpleAdapter;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.buaa.shortytall.R;
import com.buaa.shortytall.thread.GetAllTasksThread;
import com.buaa.shortytall.thread.GetAllTasksThread.GetAllTasksHandler;
import com.buaa.shortytall.thread.GetAllTasksThread.GetAllTasksListener;
import com.buaa.shortytall.util.JsonUtil;



public class ProfileTaskActivity extends DefaultActivity implements GetAllTasksListener {
	
	private ListView list;
	// 存储数据的数组列表
	private ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
	// 适配器
	private	SimpleAdapter listItemAdapter;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		GetAllTasksThread.GetAllTasksHandler handler = new GetAllTasksHandler(ProfileTaskActivity.this);
		GetAllTasksThread tasksThread = new GetAllTasksThread(handler);
		tasksThread.start();
		super.onResume();
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//menu.clear();
		 menu.clear();
	     MenuInflater inflater = getSupportMenuInflater();
	     inflater.inflate(R.menu.taskfunction, menu);
	     return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getTitle().equals("推荐给好友")){
    		//Toast.makeText(New_MainActivity.this, "test", Toast.LENGTH_SHORT).show();
			new AlertDialog.Builder(context).setMessage("推荐给好友成功").setPositiveButton("确定", null).show();				
    	}
		return super.onOptionsItemSelected(item);
	}

	

	
	
	protected void initViews() {
		// TODO Auto-generated method stub
		 setContentView(R.layout.personal_task);
 
		 list = (ListView)findViewById(R.id.personaltasks_listview);
		 list.setDivider(null);
	     listItemAdapter = new SimpleAdapter(ProfileTaskActivity.this,
	        		listData,
	        		R.layout.task_list,
	        		new String[]{"task_title","task_description"},
	        		new int[]{R.id.task_title_textview,R.id.task_description_textview}
	        		);
	     list.setAdapter(listItemAdapter);
	}
	


	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "我的任务";
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return ProfileTaskActivity.this;
	}

	@Override
	public void getAllTasksSuccessed(String json) {
		// TODO Auto-generated method stub
		listData = JsonUtil.prasePersonalTaskJson(json);
		listItemAdapter = new SimpleAdapter(ProfileTaskActivity.this,
	        		listData,
	        		R.layout.task_list,
	        		new String[]{"task_title","task_description"},
	        		new int[]{R.id.task_title_textview,R.id.task_description_textview}
	        		);
	     list.setAdapter(listItemAdapter);
//		Message msg = new Message();
//        msg.what = 1;
//        handler.sendMessage(msg);
		
	} 
	
	

	@Override
	public void getAllTasksFailed() {
		// TODO Auto-generated method stub
		System.out.println("server shi sb");
		
	}



}
