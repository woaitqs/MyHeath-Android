package com.actionbarsherlock.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class LinearLayoutExt extends LinearLayout {
    private LayoutChangeListener layoutChangeListener;

    public LinearLayoutExt(Context context) {
        super(context);
    }
    
    public LinearLayoutExt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (layoutChangeListener != null) {
            layoutChangeListener.onLayoutChange(this, l, t, r, b);
        }
        
        super.onLayout(changed, l, t, r, b);
    }
    
    public void setOnLayoutChangeListener(LayoutChangeListener listener) {
        layoutChangeListener = listener;
    }
    
    public interface LayoutChangeListener {
        void onLayoutChange(View v, int left, int top, int right, int bottom);
    }

}
