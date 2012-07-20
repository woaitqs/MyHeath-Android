package com.buaa.shortytall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

public abstract class DefaultActivity extends SherlockActivity {

    protected Context context;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getContext();
        setDisplayHomeAsUpEnabled(true);
        setActionBarTitle(getActionBarTitle());
        super.onCreate(savedInstanceState);
    }

    protected abstract String getActionBarTitle();
    
    protected abstract Context getContext();

    private void setActionBarTitle(String title) {
        if (context instanceof SherlockActivity) {
            SherlockActivity activity = (SherlockActivity) context;
            activity.getSupportActionBar().setTitle(title);
        }
    }

    private void setDisplayHomeAsUpEnabled(boolean enable) {
        if (context instanceof SherlockActivity) {
            SherlockActivity activity = (SherlockActivity) context;
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
        case android.R.id.home:
            if (context instanceof SherlockActivity) {
                ((SherlockActivity)context).finish();
            }
            break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
}
