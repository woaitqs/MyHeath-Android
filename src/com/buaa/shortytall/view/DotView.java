package com.buaa.shortytall.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.buaa.shortytall.R;

public class DotView extends LinearLayout{

    private List<ImageView> mDots; 
    private int RIGHT_MAX = 3;
    private int LEFT_MIN = 1;
    
    public DotView(Context context) {
        this(context,null,0);
    }

    public DotView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    
    public void move(int position){
        if ((position + 1 > RIGHT_MAX) || (position + 1 < LEFT_MIN) ){
            return ;
        }
        for (int i = 0 ; i < mDots.size(); i ++){
            if (i == position){
                mDots.get(i).setVisibility(View.VISIBLE);
            }else{
                mDots.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }
    
    private void initDot(View content){
        ImageView dot_1 = (ImageView)content.findViewById(R.id.first_focus_img);
        ImageView dot_2 = (ImageView)content.findViewById(R.id.second_focus_img);
        ImageView dot_3 = (ImageView)content.findViewById(R.id.third_focus_img);
        mDots = new ArrayList<ImageView>();
        mDots.add(dot_1);
        mDots.add(dot_2);
        mDots.add(dot_3);
    }
    
    public DotView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        View content = inflate(context, R.layout.dotview, null);
        initDot(content);
        this.addView(content);
    }

}
