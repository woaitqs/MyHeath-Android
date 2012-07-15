package com.buaa.shortytall.view.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.buaa.shortytall.R;

public class CheckFragment extends New_BaseFragment {

    public CheckFragment(Handler handler, Context context) {
        super(handler, context);
    }

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
        // TODO Auto-generated method stub
        return R.layout.check;
    }

    @Override
    protected void onInflated() {
        // TODO Auto-generated method stub

    }

}
