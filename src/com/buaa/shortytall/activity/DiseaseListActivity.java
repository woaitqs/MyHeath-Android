package com.buaa.shortytall.activity;

import com.actionbarsherlock.view.Menu;
import com.buaa.shortytall.MyHealth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DiseaseListActivity extends DefaultActivity{

    @Override
    protected String getActionBarTitle() {
        return "Detail";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent == null || intent.getIntExtra(MyHealth.Bundle_keys.DISEASE_TYPE, -1) == -1){
            finish();
        }
        int type = intent.getIntExtra(MyHealth.Bundle_keys.DISEASE_TYPE, -1);
        
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
