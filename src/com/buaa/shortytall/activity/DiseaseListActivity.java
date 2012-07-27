package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.actionbarsherlock.view.Menu;
import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;
import com.buaa.shortytall.bean.Disease;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DiseaseListActivity extends DefaultActivity{

    private HashMap<Integer, ArrayList<Disease>> mockData;
    private ListView listView;
    
    public void initMockData(){
        mockData = new HashMap<Integer, ArrayList<Disease>>();
        
        ArrayList<Disease> list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.BELLY, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.BREAST, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.HEAD, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.LOWER_LIMB, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.NECK, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.REPRODUCTIVE, list);
        list = new ArrayList<Disease>();
        list.add(new Disease("胸闷", "阿司匹林", "这是一种常见的政治", "不要太疲惫"));
        list.add(new Disease("胀痛", "阿司匹林", "这是一种不常见的政治", "不要太疲惫"));
        mockData.put(MyHealth.Body_Area.UPPER_LIMB, list);
    }
    
    @Override
    protected String getActionBarTitle() {
        return "Detail";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disease_list);
        initMockData();
        listView = (ListView)findViewById(R.id.disease_list_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    private List<String> getData(int type){
        List<String> data = new ArrayList<String>();
        for(Disease disease : mockData.get(type)){
            data.add(disease.getName());
        }
        return data;
    }
    
    private void handleIntent(Intent intent) {
        if (intent == null || intent.getIntExtra(MyHealth.Bundle_keys.DISEASE_TYPE, -1) == -1){
            finish();
        }
        final int type = intent.getIntExtra(MyHealth.Bundle_keys.DISEASE_TYPE, -1);
        assert(type >= 100 && type <= 106);
        
        switch(type){
        case MyHealth.Body_Area.HEAD:
        case MyHealth.Body_Area.BELLY:
        case MyHealth.Body_Area.BREAST:
        case MyHealth.Body_Area.LOWER_LIMB:
        case MyHealth.Body_Area.NECK:
        case MyHealth.Body_Area.REPRODUCTIVE:
        case MyHealth.Body_Area.UPPER_LIMB:
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData(type)));
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                        int arg2, long arg3) {
                    Disease disease = mockData.get(type).get(arg2);
                    Intent intent = new Intent(DiseaseListActivity.this, DiseaseDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_DESCRIPTION, disease.getDescription());
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_DRUGS, disease.getDrugs());
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_NAME, disease.getName());
                    bundle.putString(MyHealth.Bundle_keys.DISEASE_TIPS, disease.getTips());
                    
                    intent.putExtra(MyHealth.Bundle_keys.DISEASE, bundle);
                    startActivity(intent);
                }});
        default:
            break;
        }
    }

    @Override
    protected Context getContext() {
        return DiseaseListActivity.this;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        
        return super.onPrepareOptionsMenu(menu);
    }
    
}
