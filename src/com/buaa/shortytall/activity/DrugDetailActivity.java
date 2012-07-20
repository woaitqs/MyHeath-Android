package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.buaa.shortytall.R;
import com.buaa.shortytall.thread.GetAllCommentsThread;
import com.buaa.shortytall.thread.GetAllCommentsThread.GetAllCommentsHandler;
import com.buaa.shortytall.thread.GetAllCommentsThread.GetAllCommentsListener;



public class DrugDetailActivity extends BaseActivity implements GetAllCommentsListener{

	private SQLiteDatabase mDb;
	private SQLiteDatabaseDao daodefault;
	
	private ListView list;
	private TextView userCommentsText;
	private TextView pointsText;
	private RatingBar pointsBar;
	private ListView userCommentsListView;
	 
	// 存储数据的数组列表
	private ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
	//存储评论数据
	private ArrayList<HashMap<String, Object>> CommentslistData = new ArrayList<HashMap<String,Object>>();
	// 适配器
	private	SimpleAdapter listItemAdapter;
	private	SimpleAdapter CommentslistItemAdapter;
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		GetAllCommentsThread.GetAllCommentsHandler handler = new GetAllCommentsHandler(DrugDetailActivity.this);
		GetAllCommentsThread commentsThread = new GetAllCommentsThread(handler);
		super.onResume();
	}

	@Override
	public void getAllCommentsSuccessed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAllCommentsFailed() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    protected Context setContext() {
    	return DrugDetailActivity.this;
	    }
	
	@Override
	protected void initWindows() {
		// TODO Auto-generated method stub
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initThreads() {
		// TODO Auto-generated method stub

	}
	
	private void initNavigationBar(){
        mNavigationBar.setTitleContent(R.string.app_name);
        mNavigationBar.setLeftImage(R.drawable.more);
        Intent intent=new Intent(DrugDetailActivity.this,New_MainActivity.class);
        mNavigationBar.setReback(intent);
        mNavigationBar.setVisibility(View.GONE);
    }
	private void initFooterBar(){
       mFootBar.setVisibility(View.GONE);    
    }
	@Override
	protected View initViews() {
		// TODO Auto-generated method stub
		 View contentView = mInflater.inflate(R.layout.drugdetail, null);
		 initNavigationBar();
		 initFooterBar();
		 Intent intent=getIntent();
		 String drugid =  intent.getStringExtra("detail");
		 //System.out.println("hashmap"+drugid);
		 userCommentsText = (TextView)contentView.findViewById(R.id.drugdetail_commment_textview);
		 pointsText = (TextView)contentView.findViewById(R.id.drugdetail_points_textview);
		 pointsBar = (RatingBar)contentView.findViewById(R.id.drugdetail_points_ratingBar);
		 userCommentsListView = (ListView)contentView.findViewById(R.id.drugdetail_comment_listview);
		 userCommentsListView.setDivider(null);
		 CommentslistItemAdapter = new SimpleAdapter(DrugDetailActivity.this,
				 						CommentslistData,
				 						R.layout.comments_detail_list,
				 						new String[]{"drugdetail_title","drugdetail_description"},
				 						new int[]{R.id.drugdetail_title,R.id.drugdetail_description});
		 
		 daodefault = new SQLiteDatabaseDao();
		 
		 daodefault.getAllData(drugid);
		 
		 
		 
		 
		 list = (ListView) contentView.findViewById(R.id.drugdetail_listview);
		 list.setDivider(null);
	     listItemAdapter = new SimpleAdapter(DrugDetailActivity.this,
	        		listData,
	        		R.layout.drug_detail_list,
	        		new String[]{"drugdetail_title","drugdetail_description"},
	        		new int[]{R.id.drugdetail_title,R.id.drugdetail_description}
	        		);
	     list.setAdapter(listItemAdapter);
	     return contentView;
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
		     //System.out.println("cursor size"+columnsSize);
		     listData = new ArrayList<HashMap<String, Object>>();
		 // 获取表的内容
			 while (c.moveToNext()) 
			 {
				 for(int i=0;i<columnsSize;i++)
				 { 
				
				    HashMap<String, Object> map = new HashMap<String, Object>();
			        map.put("drugdetail_title", this.changeName(i));
			        String description =  c.getString(i);
			        if(description == null)
			        {
			        	description = new String("暂无");
			        }
			        String descriptionreplace =  description.replace("<br/>", "");
			        
			        map.put("drugdetail_description",descriptionreplace);
			        listData.add(map);
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

	

}
