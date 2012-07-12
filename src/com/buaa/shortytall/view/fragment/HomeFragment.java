package com.buaa.shortytall.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.buaa.shortytall.R;
import com.buaa.shortytall.adapter.NewsAdapter;
import com.buaa.shortytall.adapter.OriginViewPagerAdapter;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.util.ViewUtil;
import com.buaa.shortytall.view.DotView;
import com.buaa.shortytall.view.NavigationBar;

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener{

    public HomeFragment() {
        super();
    }

    private ViewPager mViewPager;
    private OriginViewPagerAdapter mPageAdapter;
    private NavigationBar mNavigationBar;
    private LayoutInflater mInflater;
    private DotView mDotView;
    private ListView mListView;
    private List<News> mNews;
    private NewsAdapter mNewsAdapter;
    private List<View> mViews;
    private List<Bitmap> mBitmaps;

    
    
    private void initViewPageView(){
        mBitmaps = new ArrayList<Bitmap>();
        mBitmaps.add(ViewUtil.convetDrawable(getResources().getDrawable(R.drawable.page1)));
        mBitmaps.add(ViewUtil.convetDrawable(getResources().getDrawable(R.drawable.page2)));
        mBitmaps.add(ViewUtil.convetDrawable(getResources().getDrawable(R.drawable.page3)));
    }
    
    public HomeFragment(Handler handler, Context context) {
        super(handler, context);
    }

    @Override
    public void handleMessage(Message message) {
        
    }

    @Override
    public void onInflatedView() {
        mListView = (ListView)contentView.findViewById(R.id.main_listview);

        View specailView = inflater.inflate(R.layout.specialnews, null);
        mViewPager = (ViewPager)specailView.findViewById(R.id.main_view_pager);
        mDotView = (DotView)specailView.findViewById(R.id.main_dotview);
        
        mNavigationBar.setTitleContent(R.string.app_name);
        
        initViewPageView();
        
        //inflate this
        mViews = new ArrayList<View>();
        for (Bitmap bitmap : mBitmaps){
            View view = mInflater.inflate(R.layout.viewpager, null);
            ImageView img = (ImageView)view.findViewById(R.id.view_page_image);
            img.setImageBitmap(bitmap);
            mViews.add(view);
        }
        
        mPageAdapter = new OriginViewPagerAdapter(context, mViews);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOnPageChangeListener(this);
        
        mNewsAdapter = new NewsAdapter(context, null);
        
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
    protected int getLayout() {
        return R.layout.homefragment;
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
