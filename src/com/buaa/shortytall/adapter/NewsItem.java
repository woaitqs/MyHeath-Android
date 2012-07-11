package com.buaa.shortytall.adapter;

import com.buaa.shortytall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsItem {

    private TextView mTitle;
    private TextView mSubTitle;
    private Context mContext;
    private ImageView mAvatar;
    private View mView;
    
    public NewsItem(Context context){
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        this.mView = inflater.inflate(R.layout.news, null,false);
        mTitle = (TextView)mView.findViewById(R.id.news_title);
        mSubTitle = (TextView)mView.findViewById(R.id.news_subtitle);
        mAvatar = (ImageView)mView.findViewById(R.id.news_avatar);
        mView.setTag(this);
    }

    public void setTitle(String mTitle) {
        this.mTitle.setText(mTitle);
    }

    public void setAvatar(Bitmap avatar){
        if (avatar != null){
            this.mAvatar.setImageBitmap(avatar);
        }
    }
    
    public void setSubTitle(String mSubTitle) {
        this.mSubTitle.setText(mSubTitle);
    }

    public View getView(){
        return mView;
    }
}
