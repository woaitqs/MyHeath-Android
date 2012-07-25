package com.buaa.shortytall.adapter;

import com.buaa.shortytall.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class QuestionsItem {

    private TextView mTitle;
    private TextView mDescription;
    private TextView mQTime;
    private TextView mATime;
    private TextView mAName;
    private TextView mADescription;
    //private Button mMoreAnswer;
    
    private Context mContext;
    private View mView;
    
    public QuestionsItem(Context context){
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        this.mView = inflater.inflate(R.layout.questionanswer_detail_list, null,false);
        mTitle = (TextView)mView.findViewById(R.id.myquestion_title_textview);
        mDescription = (TextView)mView.findViewById(R.id.myquestion_description_textview);
        mQTime = (TextView)mView.findViewById(R.id.myquestion_time_textview);
        //mMoreAnswer = (Button)mView.findViewById(R.id.mymoreanswer_button);
        mATime = (TextView)mView.findViewById(R.id.myanswer_time_textview);
        mAName = (TextView)mView.findViewById(R.id.myanswer_name_textview);
        mADescription = (TextView)mView.findViewById(R.id.myanswer_description_textview);
        mView.setTag(this);
    }
    
    public void setTitle(String mTitle) {
        this.mTitle.setText(mTitle);
    }
    
    public void setAName(String mName) {
        this.mAName.setText(mName);
    }
    
    public void setQDescription(String mDescription) {
        this.mDescription.setText(mDescription);
    }
    public void setADescription(String mDescription) {
        this.mADescription.setText(mDescription);
    }

    
    public void setQTime(String mTime){
    	String time = mTime+"000";
    	Long timestamp = Long.parseLong(time);
    	 CharSequence sysTimeStr = DateFormat.format("MMMM dd, yyyy h:mmaa", timestamp);
    	 this.mQTime.setText(sysTimeStr);
    }
    
    public void setATime(String mTime){
    	String time = mTime+"000";
    	Long timestamp = Long.parseLong(time);
    	 CharSequence sysTimeStr = DateFormat.format("MMMM dd, yyyy h:mmaa", timestamp);
    	 this.mATime.setText(sysTimeStr);
    }
    
    public View getView(){
        return mView;
    }
}
