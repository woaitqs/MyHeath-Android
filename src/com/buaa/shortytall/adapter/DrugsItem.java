package com.buaa.shortytall.adapter;

import com.buaa.shortytall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DrugsItem {

    private TextView mTitle;
    private TextView mDescription;
    private Context mContext;
    private View mView;
    
    public DrugsItem(Context context){
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        this.mView = inflater.inflate(R.layout.drug, null,false);
        mTitle = (TextView)mView.findViewById(R.id.drug_title_textview);
        mDescription = (TextView)mView.findViewById(R.id.drug_description_textview);
        mView.setTag(this);
    }

    public void setTitle(String mTitle) {
        this.mTitle.setText(mTitle);
    }

    public void setDescription(String mDescription) {
        this.mDescription.setText(mDescription);
    }  

    public View getView(){
        return mView;
    }
}
