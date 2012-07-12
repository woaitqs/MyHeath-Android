package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.buaa.shortytall.R;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchActivity extends BaseActivity{

	 private SQLiteDatabase mDb;
	 private SQLiteDatabaseDao daodefault;
	 private EditText searchTextView;
	 private Button searchButton;
	 private Button searchSwitchButton;
	 private ListView list;
	 private Boolean searchFlag;
	 
	 OnClickListener mySearchButtonClickListener;
	// 存储数据的数组列表
	 ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
	 // 适配器
	 SimpleAdapter listItemAdapter;
	 
	@Override
	protected void initWindows() {
		// TODO Auto-generated method stub
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	@Override
	protected void initListeners() {
		// TODO Auto-generated method stub
	
	}

	@Override
	protected void initThreads() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
        setContentView(R.layout.search);
        searchFlag = true;
        daodefault = new SQLiteDatabaseDao();
        
        searchTextView = (EditText)findViewById(R.id.search_drugname_edittext);
        searchButton = (Button)findViewById(R.id.search_drugname_button);
        searchSwitchButton = (Button)findViewById(R.id.search_drugname_switchbutton);
        
        searchSwitchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(searchFlag == true)
				{
					searchSwitchButton.setBackgroundResource(R.drawable.search);
					searchTextView.setHint("症状");
					searchFlag = false;
				}
				else if(searchFlag == false)
				{
					searchSwitchButton.setBackgroundResource(R.drawable.search);
					searchTextView.setHint("药品名");
					searchFlag = true;
				}
				
			}
		});
        
        searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String searchdrugname = searchTextView.getText().toString();
				
				System.out.println("onclick"+searchdrugname);
				daodefault.getAllData(searchdrugname);
				SimpleAdapter listItemAdaptertemp = new SimpleAdapter(SearchActivity.this,
		        		listData,
		        		R.layout.search_drug_list,
		        		new String[]{"drugname"},
		        		new int[]{R.id.drugname}
		        		);
		        list.setAdapter(listItemAdaptertemp);
		        list.setOnItemClickListener(clickItem);
			}
		});
        
        list = (ListView) findViewById(R.id.search_drugname_listview);
        listItemAdapter = new SimpleAdapter(SearchActivity.this,
        		listData,
        		R.layout.search_drug_list,
        		new String[]{"drugname"},
        		new int[]{R.id.drugname}
        		);
        list.setAdapter(listItemAdapter);
        list.setOnItemClickListener(clickItem);
	}
	
	OnItemClickListener clickItem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			HashMap<String,Object> mapTemp = (HashMap<String,Object>)list.getItemAtPosition(arg2);
			String valueTemp = (String) mapTemp.get("id"); 
			System.out.println("valueTemp"+valueTemp);
		}
	};
	
	class SQLiteDatabaseDao {

		 public SQLiteDatabaseDao() {
		 mDb = openOrCreateDatabase("/sdcard/drug.db",
		 SQLiteDatabase.CREATE_IF_NECESSARY, null);
		 //初始化获取所有数据表数据
		 getAllData("阿司匹林");
		 }

		 // 查询所有数据
		 public void getAllData(String text) {
		 if(listData.size()>0)
		 {
			listData.clear();
		 }
	     //System.out.println("getAlldata"+ text);
		 if(searchFlag == true)
		 {
			 Cursor c = mDb.rawQuery("select * from drug where cnName like ?" , new String[] { "%"+text.toString() + "%" });
		     //int columnsSize = c.getColumnCount();
		     //System.out.println("cursor size"+columnsSize);
		     listData = new ArrayList<HashMap<String, Object>>();
		 // 获取表的内容
			 while (c.moveToNext()) 
			 {
			     HashMap<String, Object> map = new HashMap<String, Object>();
			     map.put("id", c.getString(0));
			     map.put("drugname", c.getString(5));
			     listData.add(map);
			 }
			 c.close();
		 }
		 else if(searchFlag == false)
		 {
			 Cursor c = mDb.rawQuery("select * from drug where cnName like ? or indication like ?" , new String[] { "%"+text.toString() + "%","%"+text.toString() + "%" });
		     //int columnsSize = c.getColumnCount();
		     //System.out.println("cursor size"+columnsSize);
		     listData = new ArrayList<HashMap<String, Object>>();
		 // 获取表的内容
			 while (c.moveToNext()) 
			 {
			     HashMap<String, Object> map = new HashMap<String, Object>();
			     map.put("id", c.getString(0));
			     map.put("drugname", c.getString(5));
			     listData.add(map);
			 }
			 c.close();
			 
		 }
		}
	}
}
