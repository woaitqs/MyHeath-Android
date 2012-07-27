package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.buaa.shortytall.R;
import com.buaa.shortytall.adapter.DrugsAdapter;
import com.buaa.shortytall.bean.Comment;
import com.buaa.shortytall.bean.Drug;
import com.buaa.shortytall.thread.GetAllCommentsThread;
import com.buaa.shortytall.thread.GetAllCommentsThread.GetAllCommentsHandler;
import com.buaa.shortytall.thread.GetAllCommentsThread.GetAllCommentsListener;
import com.buaa.shortytall.util.JsonUtil;



public class DrugDetailActivity extends DefaultActivity implements GetAllCommentsListener{



	private SQLiteDatabase mDb;
	private SQLiteDatabaseDao daodefault;
	
	private ListView druglistview;
	private ArrayList<Comment> mComments;
	 
	// 存储数据的数组列表
	private ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
	//存储评论数据
	private ArrayList<HashMap<String, Object>> CommentslistData = new ArrayList<HashMap<String,Object>>();
	// 适配器
	private	DrugsAdapter listItemAdapter;
	private String initdrugname;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		 Intent intent=getIntent();
		 String drugid =  intent.getStringExtra("detail");
         daodefault = new SQLiteDatabaseDao();
		 daodefault.getAllData(drugid);
		 GetAllCommentsThread.GetAllCommentsHandler handler = new GetAllCommentsHandler(DrugDetailActivity.this);
	     GetAllCommentsThread commentsThread = new GetAllCommentsThread(handler,"10000");
	     commentsThread.start();
		super.onResume();
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//menu.clear();
		 menu.clear();
	     MenuInflater inflater = getSupportMenuInflater();
	     inflater.inflate(R.menu.drugdetailfunction, menu);
	     return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getTitle().equals(getString(R.string.personal_recommend))){
    		//Toast.makeText(New_MainActivity.this, "test", Toast.LENGTH_SHORT).show();
			new AlertDialog.Builder(context)
			.setTitle("推荐理由")
			.setIcon(R.drawable.ic_launcher)
			.setView(new EditText(context))
			.setPositiveButton("确定", null)
			.setNegativeButton("取消", null)
			.show();				
    	}
		if(item.getTitle().equals(getString(R.string.personal_store))){
    		//Toast.makeText(New_MainActivity.this, "test", Toast.LENGTH_SHORT).show();
			new AlertDialog.Builder(context).setMessage("收藏成功").setPositiveButton("确定", null).show();			
    	}
		if(item.getTitle().equals(getString(R.string.sendit_weibo))){
    		//Toast.makeText(New_MainActivity.this, "test", Toast.LENGTH_SHORT).show();
			EditText myedittext = new EditText(context);
			myedittext.setHint(initdrugname+"真得不错");
			new AlertDialog.Builder(context)
			.setTitle("分享到微博")
			.setIcon(R.drawable.ic_launcher)
			.setView(myedittext)
			.setPositiveButton("确定", null)
			.setNegativeButton("取消", null)
			.show();		
    	}
		return super.onOptionsItemSelected(item);
	}

	

	@Override
	public void getAllCommentsFailed() {
		// TODO Auto-generated method stub
		
	}
	
	protected void initViews() {
		// TODO Auto-generated method stub
		 setContentView(R.layout.drugdetail);
		
		 
		
		 
		 listItemAdapter = new DrugsAdapter(context, null);
		 
		 druglistview = (ListView)findViewById(R.id.drugdetail_information_listview);
		 
		 druglistview.setAdapter(listItemAdapter);
		 
		 
	}
	
	class SQLiteDatabaseDao {

		 public SQLiteDatabaseDao() {
		 mDb = openOrCreateDatabase("/sdcard/drug.db",
		 SQLiteDatabase.CREATE_IF_NECESSARY, null);
		 //初始化获取所有数据表数据
		 //getAllData("阿司匹林");
		 }

		 // 查询所有数据
		 public void getAllData(String id) {
	    
		 
			 Cursor c = mDb.rawQuery("select cnName,commonName,engName,component,indication,pack,dosage,adverseReactions,contraindications,precautions,drugInteractions,createDate,modifyDate from drug where id = ?" , new String[] {id.toString()});
		     int columnsSize = c.getColumnCount();
		     listItemAdapter.setDrugnumber(columnsSize);
		     //System.out.println("cursor size"+columnsSize);
		     listData = new ArrayList<HashMap<String, Object>>();
		 // 获取表的内容
			 while (c.moveToNext()) 
			 {
				 for(int i=0;i<columnsSize;i++)
				 { 
				
				    Drug drugitem = new Drug();
				    drugitem.setmTitle(this.changeName(i));
				  
			        String description =  c.getString(i);
			        if(description == null)
			        {
			        	description = new String("暂无");
			        }
			        String descriptionreplace =  description.replace("<br/>", "");
			        if(i==0)
				    {
				    	initdrugname = descriptionreplace;
				    }
			        //map.put("drugdetail_description",descriptionreplace);
			        drugitem.setmDrugDescription(descriptionreplace);
			        drugitem.setFlag(1);
			        
			        listItemAdapter.setDrugData(drugitem);
			      }
			 }
			 c.close();	
		}
		public String changeName(int id)
		{
			switch (id) {
			case 0:
				return new String("商品名称");
			case 1:
				return new String("通用名称");
			case 2:
				return new String("英文名称");
			case 3:
				return new String("成分");
			case 4:
				return new String("适应症");
			case 5:
				return new String("规格");
			case 6:
				return new String("用法用量");
			case 7:
				return new String("不良反应");
			case 8:
				return new String("禁忌");
			case 9:
				return new String("注意事项");
			case 10:
				return new String("药物相互作用");
			case 11:
				return new String("核准日期");
			case 12:
				return new String("修改日期");
			default:
		    	return null;
			
			}
			
		}
	}

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "药品详情";
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return DrugDetailActivity.this;
	}

	@Override
	public void getAllCommentsSuccessed(String json) {
		// TODO Auto-generated method stub
		
		ArrayList<Comment> data = JsonUtil.praseCommentsJson(json);
		int sizeofComments =  data.size()-1;
		listItemAdapter.setCommentsData(data);
		listItemAdapter.setCommentsnumber(sizeofComments);
	}

	
	

}
