package com.buaa.shortytall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.buaa.shortytall.R;
import com.buaa.shortytall.bean.Comment;
import com.buaa.shortytall.bean.Drug;
import com.buaa.shortytall.network.ImageCache;

public class DrugsAdapter extends BaseAdapter{

    private Context mContext;
    private Handler mHandler;
    private ArrayList<Drug> mDrugs;
    private int drugnumber;
    private int commentsnumber;
    
   
    
    public int getDrugnumber() {
		return drugnumber;
	}

	public void setDrugnumber(int drugnumber) {
		this.drugnumber = drugnumber;
	}

	public int getCommentsnumber() {
		return commentsnumber;
	}

	public void setCommentsnumber(int commentsnumber) {
		this.commentsnumber = commentsnumber;
	}

	public DrugsAdapter(Context context, Handler handler){
        this.mContext = context;
        this.mHandler = handler;
        this.mDrugs = new ArrayList<Drug>();
        ImageCache.getInstance().setHandler(mHandler);
    }
    
    public void setDrugData( Drug mydrug){
        this.mDrugs.add(mydrug);
        notifyDataSetChanged();
    }
       
    public void setCommentsData( ArrayList<Comment> data){
    	Drug drugusercommentTextView = new Drug();
    	drugusercommentTextView.setFlag(2);
    	mDrugs.add(drugusercommentTextView);
    	
    	Comment totalcomment = data.get(0);
		String commentstring = totalcomment.getmPoints();
		data.remove(0);
		int size = data.size();
		for(int i=0;i<size;i++)
		{
			Comment temp = data.get(i);
			Drug tempdrug = new Drug();
			tempdrug.setmName(temp.getmName());
			tempdrug.setmCommentDescription(temp.getmDescription());
			tempdrug.setmPoints(temp.getmPoints());
			tempdrug.setmTime(temp.getmTime());
		    tempdrug.setFlag(3);
			mDrugs.add(tempdrug);
		}
		
		Drug druguserpointsTextView = new Drug();
		druguserpointsTextView.setFlag(4);
		mDrugs.add(druguserpointsTextView);
		Drug druguserpointsRating = new Drug();
		druguserpointsRating.setmTotalPoints(commentstring);
		druguserpointsRating.setFlag(5);
		mDrugs.add(druguserpointsRating);
		Drug drugbuylink = new Drug();
		drugbuylink.setFlag(6);
		mDrugs.add(drugbuylink);
		notifyDataSetChanged();
    }
    
   
    @Override
    public int getCount() {
        if (mDrugs != null){
            return mDrugs.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        if (mDrugs != null){
            return mDrugs.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
    	
    	int flags =  mDrugs.get(position).getFlag();
    	
         
        if(flags==1)
        {
        	DrugsItem item =  new DrugsItem(mContext);
            if ( convertView == null || convertView.getTag() == null){
                item = new DrugsItem(mContext);
            } else{
                item = (DrugsItem)convertView.getTag();
            }
            
            final Drug drug = mDrugs.get(position);
            item.setTitle(drug.getmTitle());
            item.setDescription(drug.getmDrugDescription());
            return item.getView();
        	
        	
        }	
        
        else if (flags==2)
        {
        	View convertView1 = convertView;
        	 LayoutInflater inflater = LayoutInflater.from(mContext);
        	convertView1=inflater.inflate(R.layout.comment,null);
        	TextView singlecomment = (TextView)convertView1.findViewById(R.id.single_comment_textview);
        	return convertView1;
        	
        	
        }
        else if (flags==3)
        {
        	View mView = convertView;
        	System.out.println("comments inside position"+mDrugs.get(position));
        	 LayoutInflater inflater = LayoutInflater.from(mContext);
             mView = inflater.inflate(R.layout.comments_detail_list, null,false);
             TextView mName = (TextView)mView.findViewById(R.id.comments_username);
             TextView mDescription = (TextView)mView.findViewById(R.id.comments_detail);
             TextView mTime = (TextView)mView.findViewById(R.id.comments_time);
             RatingBar mPoints = (RatingBar)mView.findViewById(R.id.comments_points_ratingBar);
            
            final Drug drug = mDrugs.get(position);
            mName.setText(drug.getmName());
            mDescription.setText(drug.getmCommentDescription());
        	String time = drug.getmTime()+"000";
        	Long timestamp = Long.parseLong(time);
        	 CharSequence sysTimeStr = DateFormat.format("MMMM dd, yyyy h:mmaa", timestamp);
            mTime.setText(sysTimeStr);
            mPoints.setNumStars(5);
            mPoints.setStepSize((float)0.5);
            float stars = Float.parseFloat(drug.getmPoints());
            mPoints.setRating(stars);
            
            return mView;
        	
        }
        else if(flags==4)
        {
        	View convertView1 = convertView;
       	    LayoutInflater inflater = LayoutInflater.from(mContext);
         	convertView1=inflater.inflate(R.layout.nowpoints,null);
       	     TextView singlecomment = (TextView)convertView1.findViewById(R.id.single_points_textview);
          	return convertView1;
          	
        }
        else if(flags==5)
        {
        	View convertView1 = convertView;
       	    LayoutInflater inflater = LayoutInflater.from(mContext);
         	convertView1=inflater.inflate(R.layout.nowpointsbar,null);
         	final Drug drug = mDrugs.get(position);
       	    RatingBar singlecomment = (RatingBar)convertView1.findViewById(R.id.drugdetail_points_ratingBar);
       	    singlecomment.setNumStars(5);
       	    singlecomment.setStepSize((float)0.5);
       	    float totalpoints = Float.parseFloat(drug.getmTotalPoints().toString());
       	    singlecomment.setRating(totalpoints);
          	return convertView1;
        	
        }
        else if(flags==6)
        {
        	View convertView1 = convertView;
        	LayoutInflater inflater = LayoutInflater.from(mContext);
         	convertView1=inflater.inflate(R.layout.nowbuybutton,null);
         	Button mybuyButton = (Button)convertView1.findViewById(R.id.drugdetail_buyit_button);
        	return convertView1;
        }
        else
        {
        	return null;
        }
		
       
    }

}
