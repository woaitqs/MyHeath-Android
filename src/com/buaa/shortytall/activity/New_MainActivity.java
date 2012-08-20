package com.buaa.shortytall.activity;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import com.buaa.shortytall.R;
import com.buaa.shortytall.adapter.ViewPagerAdapter;
import com.buaa.shortytall.view.fragment.CalcFragment;
import com.buaa.shortytall.view.fragment.CheckFragment;
import com.buaa.shortytall.view.fragment.HomeFragment;
import com.buaa.shortytall.view.fragment.New_BaseFragment;
import com.buaa.shortytall.view.fragment.SearchFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class New_MainActivity extends New_BaseActivity {

    protected ViewPager viewPager;
    protected ArrayList<New_BaseFragment> tabFragments;
    private New_BaseFragment currentTabFragment;
    protected ViewPagerAdapter viewPagerAdapter;
    private String myQusetion;
    
    protected TabListener tabListener = new TabListener() {
        
        @Override
        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            for (int i = 0; i < tabFragments.size(); ++i) {
                if (tabFragments.get(i).getTab() == tab) {
                    tabFragments.get(i).onTabUnselected();
                    break;
                }
            }
        }
        
        @Override
        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            for (int i = 0; i < tabFragments.size(); ++i) {
                if (tabFragments.get(i).getTab() == tab) {
                    int k = viewPager.getCurrentItem() % tabFragments.size() - i;
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - k, true);
                    tabFragments.get(i).onTabSelected();
                    currentTabFragment = tabFragments.get(i);
                    break;
                }
            }
        }
        
        @Override
        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            
        }
    };
    
    protected OnPageChangeListener pageChangeListener =  new ViewPager.OnPageChangeListener() {
        
        @Override
        public void onPageSelected(int arg0) {
            Tab tab = tabFragments.get(arg0 % tabFragments.size()).getTab();
            getSupportActionBar().selectTab(tab);
        }
        
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            
        }
        
        @Override
        public void onPageScrollStateChanged(int arg0) {
            for (int i = 0; i < tabFragments.size(); ++i) {
                tabFragments.get(i).onPageScrollStateChanged(arg0);
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_base);
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        tabFragments = new ArrayList<New_BaseFragment>();
        initTabs();
        if (tabFragments.size() > 0) {
            currentTabFragment = tabFragments.get(0);
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < tabFragments.size(); ++i) {
            addMessageHandler(tabFragments.get(i));
            tabFragments.get(i).getTab().setTabListener(tabListener);
            getSupportActionBar().addTab(tabFragments.get(i).getTab());
            fragmentTransaction.add(tabFragments.get(i), tabFragments.get(i).getClass().getName());
        }
        fragmentTransaction.commit();
        viewPagerAdapter.setData(tabFragments);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOnPageChangeListener(pageChangeListener);
    }

    @Override
    protected synchronized void handleMessage(Message message) {
        super.handleMessage(message);
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        if (currentTabFragment != null && currentTabFragment.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.function, menu);
        
        return true;
    }

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	
    	if(item.getTitle().equals(getString(R.string.personal_center))){
    		//Toast.makeText(New_MainActivity.this, "test", Toast.LENGTH_SHORT).show();
    		Intent intent = new Intent(New_MainActivity.this,PersonProfileActivity.class);
    		startActivity(intent);
    	}
    	if(item.getTitle().equals(getString(R.string.query_doctor))){
    		//Toast.makeText(New_MainActivity.this, "test", Toast.LENGTH_SHORT).show();
    		EditText myEditText = new EditText(this); 
    		new AlertDialog.Builder(New_MainActivity.this)
			.setTitle("资讯内容")
			.setIcon(R.drawable.ic_launcher)
			.setView(myEditText)
			.setPositiveButton("确定", null)
			.setNegativeButton("取消", null)
			.show();
    		myQusetion = myEditText.getText().toString();
    	}
		return super.onOptionsItemSelected(item);
	}

	@Override
    protected void initTabs() {
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        tabFragments.add(new HomeFragment(handler, this));
        tabFragments.add(new CalcFragment(handler, this));
        tabFragments.add(new CheckFragment(handler, this));
//        tabFragments.add(new Sear  chFragment(handler, this));
    }
}
