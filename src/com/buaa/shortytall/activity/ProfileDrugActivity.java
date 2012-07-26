package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.buaa.shortytall.R;
import com.buaa.shortytall.activity.DrugDetailActivity.SQLiteDatabaseDao;
import com.buaa.shortytall.thread.GetAllPersonDrugNewsThread;
import com.buaa.shortytall.thread.GetAllPersonDrugNewsThread.GetAllDrugHandler;
import com.buaa.shortytall.thread.GetAllPersonDrugNewsThread.GetAllDrugListener;
import com.buaa.shortytall.thread.GetAllTasksThread;
import com.buaa.shortytall.thread.GetAllTasksThread.GetAllTasksHandler;
import com.buaa.shortytall.thread.GetAllTasksThread.GetAllTasksListener;
import com.buaa.shortytall.util.JsonUtil;



public class ProfileDrugActivity extends DefaultActivity implements GetAllDrugListener {
	
	
	private SQLiteDatabase mDb;
	private SQLiteDatabaseDao daodefault;
	
	private ListView list;
	// 存储数据的数组列表
	private ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
	
	private ArrayList<HashMap<String, Object>> drugidlist=new ArrayList<HashMap<String, Object>>(); 
	// 适配器
	private	SimpleAdapter listItemAdapter;
	private MyThread thread;
	
	//ui进程的handler    
	Handler handler= new Handler() {
	      @Override
	      public void handleMessage(Message msg) {
	             super.handleMessage(msg);
	             //list = (ListView)findViewById(R.id.personaltasks_listview);
	             listItemAdapter = new SimpleAdapter(ProfileDrugActivity.this,
	  	        		listData,
	  	        		R.layout.persondrug_list,
	  	        		new String[]{"drug_title","drug_time"},
	  	        		new int[]{R.id.persondrug_title_textview,R.id.persondrug_buy_textview}
	  	        		);
	    	     //System.out.println("listItemAdapter"+listItemAdapter);
	    	     list.setAdapter(listItemAdapter);
	    	    
	    		 //list.setOnItemClickListener(clickItem);
	    	   
	    	    
	   }
	 };
	 
	public class MyThread extends Thread implements Runnable {
		
	     public void run() {
	    	
			while (true) {
				
				if (drugidlist.size() > 0) {
					
					daodefault = new SQLiteDatabaseDao();
					daodefault.getAllData(drugidlist);
					drugidlist.clear();
					if (listData.size() > 0) {
						Message msg = new Message();
						handler.sendMessage(msg);// 向Handler发送消息，
						System.out.println("listData.size()    "+listData.size());
					}
				}
			}
	      }
	 }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thread = new MyThread();
		thread.start();
		initViews();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		GetAllPersonDrugNewsThread.GetAllDrugHandler handler = new GetAllDrugHandler(ProfileDrugActivity.this);
		GetAllPersonDrugNewsThread tasksThread = new GetAllPersonDrugNewsThread(handler);
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
		 setContentView(R.layout.personal_drug);
		  LayoutInflater inflater = LayoutInflater.from(this);
//		 View mView = inflater.inflate(R.layout.persondrug_list, null,false);
//		 TextView mLink = (TextView)mView.findViewById(R.id.persondrug_buy_textview);
//		 mLink.setText(Html.fromHtml("<b>text3:</b>Text with a " 
//				 + "<a href=\"http://www.google.com\">link</a> " 
//				 + "created in the Java source code using HTML."));
		  list = (ListView)findViewById(R.id.personaldrug_listview);
 	     listItemAdapter = new SimpleAdapter(ProfileDrugActivity.this,
 	        		listData,
 	        		R.layout.persondrug_list,
 	        		new String[]{"drug_title","drug_time"},
 	        		new int[]{R.id.persondrug_title_textview,R.id.persondrug_buy_textview}
 	        		);
 	     list.setAdapter(listItemAdapter);
 	     list.setDivider(null);
 		 list.setOnItemClickListener(clickItem);
		 
	}
	

	OnItemClickListener clickItem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			HashMap<String,Object> mapTemp = (HashMap<String,Object>)list.getItemAtPosition(arg2);
 			String valueTemp = (String) mapTemp.get("drug_id");
 			System.out.println("the drug id "+valueTemp);
			Intent intent=new Intent(ProfileDrugActivity.this,DrugDetailActivity.class);
			intent.putExtra("detail", valueTemp);
			startActivity(intent);
		}
	};

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "我的收藏";
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return ProfileDrugActivity.this;
	}

	
	
	class SQLiteDatabaseDao {

		 public SQLiteDatabaseDao() {
		 mDb = openOrCreateDatabase("/sdcard/drug.db",
		 SQLiteDatabase.CREATE_IF_NECESSARY, null);
		 //初始化获取所有数据表数据
		 //getAllData("阿司匹林");
		 }

		 // 查询所有数据
		 public void getAllData(ArrayList<HashMap<String, Object>> drugids) {
	    
		     int size =  drugids.size();
		     for(int j=0;j<size;j++)
		     { 
		    	 Cursor c = mDb.rawQuery("select id,commonName from drug where id = ?" , new String[] {drugids.get(j).get("drug_id").toString()});
		     
		     listData = new ArrayList<HashMap<String, Object>>();
		 // 获取表的内容
			 while (c.moveToNext()) 
			 {
				    HashMap<String, Object> map = new HashMap<String, Object>();
			        map.put("drug_title", c.getString(1));   
			        //map.put("drug_", c.getString(1)); 
			        map.put("drug_id",c.getString(0));
			        map.put("drug_time", drugids.get(j).get("drug_time").toString());
			        System.out.println("drug title is"+c.getString(1)+"drug id is"+c.getString(0));
			        listData.add(map);
			 }
			 c.close();	
		   }
		
		
			
		}
	}

	@Override
	public void getAllDrugSuccessed(String json) {
		// TODO Auto-generated method stub
		drugidlist = JsonUtil.prasePersonDrugJson(json);
		//System.out.println("drugidlist is :"+drugidlist);
		 if(drugidlist.size()>0)
		 {
			 System.out.println("drugidlist size is :"+drugidlist.size());
		 }
	}

	@Override
	public void getAllDrugFailed() {
		// TODO Auto-generated method stub
		
	}



}
