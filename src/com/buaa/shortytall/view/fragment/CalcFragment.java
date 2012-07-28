package com.buaa.shortytall.view.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;
import com.buaa.shortytall.activity.DiseaseDetailActivity;
import com.buaa.shortytall.activity.DiseaseListActivity;
import com.buaa.shortytall.activity.HealthCheckActivity;
import com.buaa.shortytall.bean.Disease;

public class CalcFragment extends New_BaseFragment {

    private ListView listview;
    
    public CalcFragment(Handler handler, Context context) {
        super(handler, context);
    }

    @Override
    protected Tab initTab() {
        Tab tab = ((SherlockFragmentActivity)context).getSupportActionBar()
                .newTab();
        tab.setText("医疗计算");
        tab.setIcon(context.getResources().getDrawable(R.drawable.calc));
        return tab;
    }

    @Override
    public void handleMessage(Message message) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getAsyncInitViewResId() {
        return R.layout.calc;
    }

    @Override
    protected void onInflated() {
        listview = (ListView)contentView.findViewById(R.id.calc_list_view);
        listview.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1,
                new ArrayList<String>(Arrays.asList("身高体重自测","肾小球自测","视力自测","心肺自测","体重自测","臂力自测","体力自测","心率自测","脉搏自测"))));
        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                startActivity(new Intent(context,HealthCheckActivity.class));
            }});
    }

}
