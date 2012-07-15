package com.buaa.shortytall.view.fragment;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.buaa.shortytall.R;

public class CalcFragment extends New_BaseFragment {

    public CalcFragment(Handler handler, Context context) {
        super(handler, context);
    }

    @Override
    protected Tab initTab() {
        Tab tab = ((SherlockFragmentActivity)context).getSupportActionBar()
                .newTab();
        tab.setText("Calc");
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
    }

}
