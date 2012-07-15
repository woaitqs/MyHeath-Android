package com.buaa.shortytall.adapter;

import com.buaa.shortytall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentsItem {

    private TextView mName;
    private TextView mDescription;
    private TextView mTime;
    private RatingBar mPoints;
    
    private Context mContext;
    private View mView;
    
    public CommentsItem(Context context){
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        this.mView = inflater.inflate(R.layout.comments_detail_list, null,false);
        mName = (TextView)mView.findViewById(R.id.comments_username);
        mDescription = (TextView)mView.findViewById(R.id.comments_detail);
        mTime = (TextView)mView.findViewById(R.id.comments_time);
        mPoints = (RatingBar)mView.findViewById(R.id.comments_points_ratingBar);
        mView.setTag(this);
    }

    public void setName(String mName) {
        this.mName.setText(mName);
    }
    
    public void setDescription(String mDescription) {
        this.mDescription.setText(mDescription);
    }

    public void setTime(String mTime){
    	Long timestamp = Long.parseLong(mTime);
    	 CharSequence sysTimeStr = DateFormat.format("MMMM dd, yyyy h:mmaa", timestamp);
    	 this.mTime.setText(sysTimeStr);
    }
    
    public void setPoints(String mPoints) {
        this.mPoints.setMax(5);
        this.mPoints.setNumStars(5);
        Long stars = Long.parseLong(mPoints);
        this.mPoints.setRating(stars);
    }

    public View getView(){
        return mView;
    }
}
