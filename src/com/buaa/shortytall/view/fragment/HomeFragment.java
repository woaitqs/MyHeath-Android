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

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.buaa.shortytall.R;
import com.buaa.shortytall.adapter.NewsAdapter;
import com.buaa.shortytall.adapter.OriginViewPagerAdapter;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.util.ViewUtil;
import com.buaa.shortytall.view.DotView;

public class HomeFragment extends New_BaseFragment implements ViewPager.OnPageChangeListener{

    
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
        mBitmaps.add(ViewUtil.convetDrawable(context.getResources().getDrawable(R.drawable.page1)));
        mBitmaps.add(ViewUtil.convetDrawable(context.getResources().getDrawable(R.drawable.page2)));
        mBitmaps.add(ViewUtil.convetDrawable(context.getResources().getDrawable(R.drawable.page3)));
    }
    
    private void initListView(){
        LayoutInflater mInflater = LayoutInflater.from(context);
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
        mPageAdapter = new OriginViewPagerAdapter(context, mViewPagerViews);
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
    
    public HomeFragment(Handler handler, Context context) {
        super(handler, context);
    }

    @Override
    protected Tab initTab() {
        Tab tab = ((SherlockFragmentActivity)context).getSupportActionBar()
                .newTab();
        tab.setText("Home");
        tab.setIcon(context.getResources().getDrawable(R.drawable.home));
        return tab;
    }

    @Override
    public void handleMessage(Message message) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getAsyncInitViewResId() {
        return R.layout.home;
    }

    @Override
    protected void onInflated() {
        mListView = (ListView)contentView.findViewById(R.id.main_listview);
        initListView();
    }

}
