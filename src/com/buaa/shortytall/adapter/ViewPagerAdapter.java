
package com.buaa.shortytall.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.buaa.shortytall.view.fragment.New_BaseFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    public static final int TOTAL_PAGE_NUM = 4;
    private Context mContext;
    private ArrayList<New_BaseFragment> mFragments;
    private ViewPager.LayoutParams mDefLayoutParams;

    public ViewPagerAdapter(Context context) {
        mContext = context;
        mDefLayoutParams = new ViewPager.LayoutParams();
        mDefLayoutParams.width = ViewPager.LayoutParams.FILL_PARENT;
        mDefLayoutParams.height = ViewPager.LayoutParams.FILL_PARENT;
    }
    
    public void setData(ArrayList<New_BaseFragment> fragments) {
        this.mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(View container, int position) {
        if (mFragments.get(position).getView().getParent() != null) {
            ((ViewGroup) mFragments.get(position).getView().getParent()).removeView(mFragments.get(position).getView());
        }
        ((ViewPager) container).addView(mFragments.get(position).getView(), mDefLayoutParams);
        return mFragments.get(position).getView();
    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewGroup) mFragments.get(position).getView().getParent()).removeView(mFragments.get(position).getView());
    }
}
