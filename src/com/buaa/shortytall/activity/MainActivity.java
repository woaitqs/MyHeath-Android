package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.buaa.shortytall.R;
import com.buaa.shortytall.adapter.NewsAdapter;
import com.buaa.shortytall.adapter.OriginViewPagerAdapter;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.thread.GetAllNewsThread;
import com.buaa.shortytall.thread.GetAllNewsThread.GetAllNewsHandler;
import com.buaa.shortytall.thread.GetAllNewsThread.GetAllNewsListener;
import com.buaa.shortytall.util.ViewUtil;
import com.buaa.shortytall.view.DotView;
import com.buaa.shortytall.view.FooterBar;
import com.buaa.shortytall.view.NavigationBar;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, GetAllNewsListener{

    private ViewPager mViewPager;
    private OriginViewPagerAdapter mPageAdapter;
    private DotView mDotView;
    private ListView mListView;
    private List<News> mNews;
    private NewsAdapter mNewsAdapter;
    private List<View> mViewPagerViews;
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

    private void initNavigationBar(){
        mNavigationBar.setTitleContent(R.string.app_name);
        mNavigationBar.setRightImage(R.drawable.more);
    }
    
    private void initFooterBar(){
        mFootBar.setPosition(0);
    }
    
    private void initListView(){
        View specailView = mInflater.inflate(R.layout.specialnews, null);
        mViewPager = (ViewPager)specailView.findViewById(R.id.main_view_pager);
        mDotView = (DotView)specailView.findViewById(R.id.main_dotview);
        initViewPageView();
        //inflate this
        mViewPagerViews = new ArrayList<View>();
        for (Bitmap bitmap : mBitmaps){
            View view = mInflater.inflate(R.layout.viewpager, null);
            ImageView img = (ImageView)view.findViewById(R.id.view_page_image);
            img.setImageBitmap(bitmap);
            mViewPagerViews.add(view);
        }
        mPageAdapter = new OriginViewPagerAdapter(this, mViewPagerViews);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOnPageChangeListener(this);
        mNewsAdapter = new NewsAdapter(this, null);
        mNews = new ArrayList<News>();
        mNews.add(new News("医药的春天","新的世界迎来新的春天",""));
        mNews.add(new News("医药的春天","djlfjsgjadjgoeojgsdga",""));
        mNews.add(new News("医药的春天","djlfjsgjadjgoeojgsdga",""));
        mNews.add(new News("医药的春天","djlfjsgjadjgoeojgsdga",""));
        mNews.add(new News("医药的春天","djlfjsgjadjgoeojgsdga",""));
        mNews.add(new News("医药的春天","djlfjsgjadjgoeojgsdga",""));
        mNews.add(new News("医药的春天","djlfjsgjadjgoeojgsdga",""));
        mNews.add(new News("医药的春天","djlfjsgjadjgoeojgsdga",""));
        mNewsAdapter.setData(mNews);
        mListView.addHeaderView(specailView);
        mListView.setAdapter(mNewsAdapter);
    }
    
    @Override
    protected View initViews() {
        View contentView = mInflater.inflate(R.layout.home, null);
        mListView = (ListView)contentView.findViewById(R.id.main_listview);
        initNavigationBar();
        initListView();
        initFooterBar();
        return contentView;
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

    @Override
    protected void onResume() {
        super.onResume();
        GetAllNewsThread.GetAllNewsHandler handler = new GetAllNewsHandler(MainActivity.this);
        GetAllNewsThread newsThread = new GetAllNewsThread(handler);
        newsThread.start();
    }

    @Override
    public void getAllNewsSuccessed() {
        
    }

    @Override
    public void getAllNewsFailed() {
        
    }

    @Override
    protected Context setContext() {
        return MainActivity.this;
    }
    
}
