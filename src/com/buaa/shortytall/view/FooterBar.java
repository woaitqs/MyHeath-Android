package com.buaa.shortytall.view;

import com.buaa.shortytall.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FooterBar extends LinearLayout{

    private static final int MAX_POS = 4;
    private static final int MIN_POS = 0;
    
    private LinearLayout mMoveView;
    private ImageView mHomeView;
    private ImageView mCalcView;
    private ImageView mPersonView;
    private ImageView mSearchView;
    private ImageView mMoreView;
    
    public FooterBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FooterBar(Context context) {
        this(context,null,0);
    }

    public FooterBar(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
        View contentView = inflate(context, R.layout.footbar, null);
        mMoveView = (LinearLayout)contentView.findViewById(R.id.footbar_move_view);
        mHomeView = (ImageView)contentView.findViewById(R.id.footerbar_home);
        mCalcView = (ImageView)contentView.findViewById(R.id.footerbar_calc);
        mPersonView = (ImageView)contentView.findViewById(R.id.footerbar_personal);
        mSearchView = (ImageView)contentView.findViewById(R.id.footerbar_search);
        mMoreView = (ImageView)contentView.findViewById(R.id.footerbar_more);
        this.addView(contentView);
    }
    
    public void setPosition(int position){
        assert(position < MAX_POS);
        assert(position > MIN_POS);
        for (int i = 0 ; i < mMoveView.getChildCount(); i ++){
            if (i == position){
                mMoveView.getChildAt(i).setVisibility(View.VISIBLE);
            }else {
                mMoveView.getChildAt(i).setVisibility(View.INVISIBLE);
            }
        }
    }
}
