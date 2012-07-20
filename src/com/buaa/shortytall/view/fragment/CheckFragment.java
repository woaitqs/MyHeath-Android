package com.buaa.shortytall.view.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;
import com.buaa.shortytall.activity.DiseaseListActivity;

public class CheckFragment extends New_BaseFragment implements OnTouchListener{

    
    public CheckFragment(Handler handler, Context context) {
        super(handler, context);
    }

    private ImageView mHumanView;
    private LayoutInflater mInflater;
    private RelativeLayout mBaseView;
    
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
        mInflater = LayoutInflater.from(context);
        mBaseView = (RelativeLayout)contentView;
        
        loadData();
    }

    private void popOut(final LocationData data){
        View mOverlay = mInflater.inflate(R.layout.overlay_pop, null);
        TextView title = (TextView)mOverlay.findViewById(R.id.overlay_title);
        TextView description = (TextView)mOverlay.findViewById(R.id.overlay_description);
        title.setText(data.title);
        description.setText(data.content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        
        mOverlay.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DiseaseListActivity.class);
                intent.putExtra(MyHealth.Bundle_keys.DISEASE_TYPE, data.type);
                startActivity(intent);
            }
        });
        
        params.alignWithParent = true;
        params.leftMargin = data.x;
        params.topMargin = data.y;
        mBaseView.addView(mOverlay,params);
    }
    
    private void loadData(){
        ArrayList<LocationData> datas = new ArrayList<LocationData>();
        datas.add(new LocationData(82,297,MyHealth.Body_Area.UPPER_LIMB,"上肢部位","手掌，手腕等"));
        datas.add(new LocationData(150,100,MyHealth.Body_Area.BREAST,"胸部区域 ","肺部，胸腔等"));
        datas.add(new LocationData(108,8,MyHealth.Body_Area.HEAD,"头部","口，耳，鼻，眼等"));
        datas.add(new LocationData(239,63,MyHealth.Body_Area.NECK,"肩部","肩，颈部等"));
        datas.add(new LocationData(213,383,MyHealth.Body_Area.LOVER_LIMB,"下肢","膝盖，大腿等"));
        datas.add(new LocationData(161,577,MyHealth.Body_Area.BELLY,"腹部","胃，肠等"));
        datas.add(new LocationData(195,256,MyHealth.Body_Area.REPRODUCTIVE,"生殖部位","生殖器官等"));
        
        for(LocationData item : datas){
            popOut(item);
        }
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        int events = event.getAction();
//        switch(events){
//        case MotionEvent.ACTION_DOWN:
//            if (mOverlay != null){
//                mBaseView.removeView(mOverlay);
//            }
//            int lastX = (int)event.getX();
//            int lastY = (int)event.getY();
//            popOut(lastX,lastY);
//        }
        return false;
    }
    
    private class LocationData{
        public LocationData(int x, int y,int type, String title, String content) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.title = title;
            this.content = content;
        }
        int x;
        int y;
        int type;
        String title;
        String content;
        
    }

}
