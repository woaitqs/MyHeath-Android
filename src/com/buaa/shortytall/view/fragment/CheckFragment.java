package com.buaa.shortytall.view.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.buaa.shortytall.R;

public class CheckFragment extends New_BaseFragment implements OnTouchListener{

    
    public CheckFragment(Handler handler, Context context) {
        super(handler, context);
    }

    private ImageView mHumanView;
    private LayoutInflater mInflater;
    private View mOverlay;
    private FrameLayout mBaseView;
    
    @Override
    protected Tab initTab() {
        Tab tab = ((SherlockFragmentActivity)context).getSupportActionBar()
                .newTab();
        tab.setText("HealthTest");
        tab.setIcon(context.getResources().getDrawable(R.drawable.check));
        return tab;
    }

    
    
    @Override
    public void handleMessage(Message message) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getAsyncInitViewResId() {
        return R.layout.check;
    }

    @Override
    protected void onInflated() {
        mHumanView = (ImageView)contentView.findViewById(R.id.human_page);
        mHumanView.setOnTouchListener(CheckFragment.this);
        mInflater = LayoutInflater.from(context);
        mBaseView = (FrameLayout)contentView.findViewById(R.id.human_check_base);
    }

    private void popOut(int x ,int y){
        mOverlay = mInflater.inflate(R.layout.overlay_pop, null);
        TextView title = (TextView)mOverlay.findViewById(R.id.overlay_title);
        TextView description = (TextView)mOverlay.findViewById(R.id.overlay_description);
        
        title.setText("last X=" + x);
        description.setText("last Y=" + y);
//        mOverlay.setLeft(x);
//        mOverlay.setTop(y);
//        mOverlay.set
        mBaseView.addView(mOverlay,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int events = event.getAction();
        switch(events){
        case MotionEvent.ACTION_DOWN:
            if (mOverlay != null){
                mBaseView.removeView(mOverlay);
            }
            int lastX = (int)event.getRawX();
            int lastY = (int)event.getRawY();
            popOut(lastX,lastY);
        }
        return false;
    }

}
