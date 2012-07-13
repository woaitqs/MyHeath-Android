package com.buaa.shortytall.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class OriginViewPagerAdapter extends PagerAdapter {

    private List<View> mViews;
    
    public OriginViewPagerAdapter(Context context,List<View> views){
        mViews = views;
    }
    
    @Override
    public int getCount() {
        if (mViews != null){
            return mViews.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViews.get(position));
        return mViews.get(position);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == (View)arg1);
    }

}
