package com.buaa.shortytall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.buaa.shortytall.bean.News;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class NewsAdapter extends BaseAdapter{

    private Context mContext;
    private Handler mHandler;
    private List<News> mNews;
    
    public NewsAdapter(Context context, Handler handler){
        this.mContext = context;
        this.mHandler = handler;
        this.mNews = new ArrayList<News>();
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
        NewsItem item;
        if ( convertView == null || convertView.getTag() == null){
            item = new NewsItem(mContext);
        } else{
            item = (NewsItem)convertView.getTag();
        }
        
        final News news = mNews.get(position);
        item.setTitle(news.getTitle());
        item.setSubTitle(news.getSubContent());
        
        return item.getView();
    }

}
