package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.buaa.shortytall.R;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class PersonProfileActivity extends DefaultActivity {

	private ListView list;
	private ArrayList<HashMap<String, Object>> listdata = new ArrayList<HashMap<String,Object>>();

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return new String("个人中心");
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return PersonProfileActivity.this;
	}
	
	private HashMap<String, Object> createItem(int R,String name)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itempic", R);
		map.put("itemname", name);
		return map;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.personprofile);
		 list = (ListView)findViewById(R.id.personProfile_listview);
		 
		 listdata.add(createItem(R.drawable.search,"我的资讯"));
   		 listdata.add(createItem(R.drawable.search,"我的问题"));
		 listdata.add(createItem(R.drawable.search,"我的任务"));
		 listdata.add(createItem(R.drawable.search,"站内信"));
		 listdata.add(createItem(R.drawable.search,"我的收藏"));
		 listdata.add(createItem(R.drawable.search,"注销登陆"));
		 
		 SimpleAdapter listItemAdapter = new SimpleAdapter(PersonProfileActivity.this,
				    listdata,
	        		R.layout.profile_person_list,
	        		new String[]{"itempic","itemname"},
	        		new int[]{R.id.itempic,R.id.itemname}
	        		);
	        list.setAdapter(listItemAdapter);
	        list.setOnItemClickListener(clickItem);
	}
	OnItemClickListener clickItem = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			@SuppressWarnings("unchecked")
			HashMap<String,Object> mapTemp = (HashMap<String,Object>)list.getItemAtPosition(arg2);
 			String valueTemp = (String) mapTemp.get("itemname");
 			System.out.println("the item name  "+valueTemp);
 		    if(valueTemp.equalsIgnoreCase("我的资讯"))
 		    {
 		    	Intent intent=new Intent(PersonProfileActivity.this,ProfileNewsActivity.class);
 				startActivity(intent);
 		    }
 		   if(valueTemp.equalsIgnoreCase("我的问题"))
		    {
		    	Intent intent=new Intent(PersonProfileActivity.this,ProfileQusetionAndAnswerActivity.class);
				startActivity(intent);
		    }
 		  if(valueTemp.equalsIgnoreCase("我的任务"))
		    {
		    	Intent intent=new Intent(PersonProfileActivity.this,ProfileTaskActivity.class);
				startActivity(intent);
		    }
 		 if(valueTemp.equalsIgnoreCase("我的收藏"))
		    {
		    	Intent intent=new Intent(PersonProfileActivity.this,ProfileDrugActivity.class);
				startActivity(intent);
		    }
			
		}
	};

	
}
