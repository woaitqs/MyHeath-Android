package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import com.buaa.shortytall.R;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class SearchActivity extends BaseActivity{

	 private SQLiteDatabase mDb;
	 private SQLiteDatabaseDao daodefault;
	 private EditText searchTextView;
	 private Button searchButton;
	 //private Button searchSwitchButton;
	 private ListView list;
	 private Boolean searchFlag;
	 private Spinner searchSwitchSpinner;
	 private List<String> spinnerList = new ArrayList<String>();  
	 private ArrayAdapter<String> spinneradapter;  
	  
	 OnClickListener mySearchButtonClickListener;
	// 存储数据的数组列表
	 ArrayList<HashMap<String, Object>> listData = new ArrayList<HashMap<String,Object>>();
	 // 适配器
	 SimpleAdapter listItemAdapter;
	 
    @Override
    protected Context setContext() {
    	return SearchActivity.this;
	    }
    
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

    private void initNavigationBar(){
	        mNavigationBar.setTitleContent(R.string.app_name);
	        mNavigationBar.setRightImage(R.drawable.more);
    }
	    
    private void initFooterBar(){
	        mFootBar.setPosition(3);
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println("onKeyDown");
		mFootBar.setVisibility(View.GONE);
		return super.onKeyDown(keyCode, event);
	}
    
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
	    mFootBar.setVisibility(View.VISIBLE);
		return super.onKeyUp(keyCode, event);
	}

	@Override
	protected View initViews() {
		// TODO Auto-generated method stub
		 View contentView = mInflater.inflate(R.layout.search, null);
		 initNavigationBar();
		 initFooterBar();
        searchFlag = true;
        daodefault = new SQLiteDatabaseDao();
        
        searchTextView = (EditText)contentView.findViewById(R.id.search_drugname_edittext);
        searchButton = (Button)contentView.findViewById(R.id.search_drugname_button);
        //searchSwitchButton = (Button)findViewById(R.id.search_drugname_switchbutton);
        searchSwitchSpinner = (Spinner)contentView.findViewById(R.id.search_switch_spinner);
        spinnerList.add("药品");
        spinnerList.add("症状");
        
        spinneradapter = new ArrayAdapter<String>(SearchActivity.this,R.layout.spinner_item, spinnerList);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchSwitchSpinner.setAdapter(spinneradapter);
        searchSwitchSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
        	@SuppressWarnings("unchecked")
			public void onItemSelected(AdapterView arg0, View arg1, int arg2, long arg3) {   
                // TODO Auto-generated method stub   
                /* 将所选mySpinner 的值带入myTextView 中*/  
                searchTextView.setHint(spinneradapter.getItem(arg2));
                String type = spinneradapter.getItem(arg2);
                if(type.equalsIgnoreCase("药品"))
                {
                	searchFlag = true;
                }
                else if(type.equalsIgnoreCase("症状"))
                {
                	searchFlag = false;
                }
                /* 将mySpinner 显示*/  
                arg0.setVisibility(View.VISIBLE);   
            }   
            @SuppressWarnings("unchecked")
			public void onNothingSelected(AdapterView arg0) {   
                // TODO Auto-generated method stub   
                arg0.setVisibility(View.VISIBLE);   
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
        
        list = (ListView) contentView.findViewById(R.id.search_drugname_listview);
        listItemAdapter = new SimpleAdapter(SearchActivity.this,
        		listData,
        		R.layout.search_drug_list,
        		new String[]{"drugname"},
        		new int[]{R.id.drugname}
        		);
        list.setAdapter(listItemAdapter);
        list.setOnItemClickListener(clickItem);
        return contentView;
	}
	
	OnItemClickListener clickItem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			HashMap<String,Object> mapTemp = (HashMap<String,Object>)list.getItemAtPosition(arg2);
 			String valueTemp = (String) mapTemp.get("id");
 			System.out.println("the drug id "+valueTemp);
			Intent intent=new Intent(SearchActivity.this,DrugDetailActivity.class);
			intent.putExtra("detail", valueTemp);
			startActivity(intent);
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