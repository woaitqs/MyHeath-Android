package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.buaa.shortytall.R;
import com.buaa.shortytall.adapter.OriginViewPagerAdapter;
import com.buaa.shortytall.util.ViewUtil;
import com.buaa.shortytall.view.DotView;
import com.buaa.shortytall.view.NavigationBar;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    private OriginViewPagerAdapter mPageAdapter;
    private NavigationBar mNavigationBar;
    private LayoutInflater mInflater;
    private DotView mDotView;
    
    private List<View> mViews;
    private List<Bitmap> mBitmaps;
    
    private void initViewPageView(){
        mBitmaps = new ArrayList<Bitmap>();
        mBitmaps.add(ViewUtil.convetDrawable(getResources().getDrawable(R.drawable.page1)));
        mBitmaps.add(ViewUtil.convetDrawable(getResources().getDrawable(R.drawable.page2)));
        mBitmaps.add(ViewUtil.convetDrawable(getResources().getDrawable(R.drawable.page3)));
    }
    
    @Override
    protected void initWindows() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void initListeners() {
        
    }

    @Override
    protected void initThreads() {
        
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.main);
        mViewPager = (ViewPager)findViewById(R.id.main_view_pager);
        mNavigationBar = (NavigationBar)findViewById(R.id.main_navigationbar);
        mDotView = (DotView)findViewById(R.id.main_dotview);
        
        mNavigationBar.setTitleContent(R.string.app_name);
        
        initViewPageView();
        
        //inflate this
        mViews = new ArrayList<View>();
        mInflater = getLayoutInflater();
        for (Bitmap bitmap : mBitmaps){
            View view = mInflater.inflate(R.layout.viewpager, null);
            ImageView img = (ImageView)view.findViewById(R.id.view_page_image);
            img.setImageBitmap(bitmap);
            mViews.add(view);
        }
        
        mPageAdapter = new OriginViewPagerAdapter(MainActivity.this, mViews);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOnPageChangeListener(MainActivity.this);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        
    }

    @Override
    public void onPageSelected(int arg0) {
        mDotView.move(arg0);
    }
    
}
