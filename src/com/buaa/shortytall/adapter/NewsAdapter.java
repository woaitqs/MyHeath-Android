package com.buaa.shortytall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.buaa.shortytall.R;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.network.ImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class NewsAdapter extends BaseAdapter{

    private Context mContext;
    private Handler mHandler;
    private List<News> mNews;
    
    public NewsAdapter(Context context, Handler handler){
        this.mContext = context;
        this.mHandler = handler;
        this.mNews = new ArrayList<News>();
        ImageCache.getInstance().setHandler(mHandler);
    }
    
    public void setData(List<News> mNews){
        this.mNews = mNews;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        if (mNews != null){
            return mNews.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        if (mNews != null){
            return mNews.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
        
        NewsItem newsitem = new NewsItem(mContext);
        if ( convertView == null || convertView.getTag() == null){
            newsitem = new NewsItem(mContext);
        } else{
            newsitem = (NewsItem)convertView.getTag();
        }
        
        final News news = mNews.get(position);
        final String title = news.getmTitle();
        final String date = news.getmDate();
        
        newsitem.setTitle(title);
        newsitem.setDate(date);
        
        if (news.getmAvatar() != null){
            Bitmap bitmap = ImageCache.getInstance().getCachedBitmap(news.getmAvatar());
            if (bitmap != null){
                newsitem.setAvatar(bitmap);
            }else{
                newsitem.setAvatarById(R.drawable.ic_launcher);
                ImageCache.getInstance().getBitmapFromUrl(news.getmAvatar());
            }
        }
        
        newsitem.getView().setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, news.getmId(), Toast.LENGTH_SHORT).show();
            }
        });
        
        return newsitem.getView();
    }

}
