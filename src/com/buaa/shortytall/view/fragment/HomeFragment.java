package com.buaa.shortytall.view.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
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
import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;
import com.buaa.shortytall.activity.New_MainActivity;
import com.buaa.shortytall.activity.SplashActivity;
import com.buaa.shortytall.adapter.NewsAdapter;
import com.buaa.shortytall.adapter.OriginViewPagerAdapter;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.thread.GetAllNewsThread;
import com.buaa.shortytall.thread.GetAllNewsThread.GetAllNewsHandler;
import com.buaa.shortytall.thread.GetAllNewsThread.GetAllNewsListener;
import com.buaa.shortytall.util.JsonUtil;
import com.buaa.shortytall.util.ViewUtil;
import com.buaa.shortytall.view.DotView;

public class HomeFragment extends New_BaseFragment implements ViewPager.OnPageChangeListener, GetAllNewsListener{

    
    private ViewPager mViewPager;
    private OriginViewPagerAdapter mPageAdapter;
    private ListView mListView;
    private NewsAdapter mNewsAdapter;
    private List<View> mViewPagerViews;
    private List<Bitmap> mBitmaps;
    private DotView mDotView;
    
    private int position = 0;
    
    private long mStartTime; 
    private Timer mTimer;
    
    private void initViewPageView(){
        mBitmaps = new ArrayList<Bitmap>();
        mBitmaps.add(ViewUtil.convetDrawable(context.getResources().getDrawable(R.drawable.page1)));
        mBitmaps.add(ViewUtil.convetDrawable(context.getResources().getDrawable(R.drawable.page2)));
        mBitmaps.add(ViewUtil.convetDrawable(context.getResources().getDrawable(R.drawable.page3)));
    }
    
    private final TimerTask task = new TimerTask(){

        @Override
        public void run() {
            if(task.scheduledExecutionTime() - mStartTime >= MyHealth.SPALSH_TIME){
                Message message = new Message();
                message.what = MyHealth.Msg.NEWS_FRESH;
                timerHandler.sendMessage(message);
//                task.cancel();
//                this.cancel();
            }
        }
        
    };
    
    private final Handler timerHandler =  new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case MyHealth.Msg.NEWS_FRESH:
                    if(position == 3){
                        position = 0;
                    }else {
                        position ++;
                    }
                    mDotView.move(position);
                    mViewPager.setCurrentItem(position);
                    break;
                default: return;
            }
            super.handleMessage(msg);
        }
    };
    
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
        mNewsAdapter = new NewsAdapter(context, handler);
        mListView.addHeaderView(specailView);
        mListView.setAdapter(mNewsAdapter);
    }
    
    public HomeFragment(Handler handler, Context context) {
        super(handler, context);
        mTimer = new Timer(true);
        mStartTime = System.currentTimeMillis();
        mTimer.schedule(task,0, 1000 * 3);
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
    public void onResume() {
        super.onResume();
        GetAllNewsHandler handler = new GetAllNewsHandler(HomeFragment.this);
        GetAllNewsThread thread = new GetAllNewsThread(handler);
        thread.start();
    }

    @Override
    public void handleMessage(Message message) {
        switch(message.what){
        case MyHealth.Msg.IMG_LOADED_COMPLETED:
            if (mNewsAdapter != null){
                mNewsAdapter.notifyDataSetChanged();
            }
            break;
       default:
            break;
        }
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

    @Override
    public void getAllNewsFailed() {
        
    }

    @Override
    public void getAllNewsSuccessed(String json) {
        ArrayList<News> data = JsonUtil.praseNewsJson(json);
        mNewsAdapter.setData(data);
    }

}
