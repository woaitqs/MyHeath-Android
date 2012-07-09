package com.buaa.shortytall.view;

import com.buaa.shortytall.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NavigationBar extends LinearLayout{

    private ImageView leftView;
    private ImageView rightView;
    private TextView titleView;
    
    public NavigationBar(Context context) {
        this(context, null, 0);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setLeftImage(int resId){
        leftView.setImageResource(resId);
    }
    
    public void setRightImage(int resId){
        rightView.setImageResource(resId);
    }
    
    public void setTitleContent(int resId){
        titleView.setText(resId);
    }
    
    public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        setBackgroundResource(R.drawable.navigation_bg_green);
        View contentView = inflate(context, R.layout.navigationbar, null);
        leftView = (ImageView)contentView.findViewById(R.id.nav_left_img);
        rightView = (ImageView)contentView.findViewById(R.id.nav_right_img);
        titleView = (TextView)contentView.findViewById(R.id.nav_title_text);
        this.addView(contentView);
    }

    
}
