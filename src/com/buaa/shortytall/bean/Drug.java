package com.buaa.shortytall.bean;

public class Drug {

   

    private String mTitle;
    private String mDrugDescription;
    private String mName;
    private String mCommentDescription;
    private String mPoints;
    private String mTime;
    private String mTotalPoints;
    private int flag; //1meansdrug 2means用户评论  3means评论list 4means评分  5means评论bar 
    
	public Drug(String mTitle, String mDrugDescription, String mName,
			String mCommentDescription, String mPoints, String mTime,String mTotalPoints) {
		super();
		this.mTitle = mTitle;
		this.mDrugDescription = mDrugDescription;
		this.mName = mName;
		this.mCommentDescription = mCommentDescription;
		this.mPoints = mPoints;
		this.mTime = mTime;
		this.mTotalPoints = mTotalPoints;
	}
    
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Drug(){
	  super();	
	}

	public String getmTitle() {
		return mTitle;
	}

	public void setmTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getmDrugDescription() {
		return mDrugDescription;
	}

	public void setmDrugDescription(String mDrugDescription) {
		this.mDrugDescription = mDrugDescription;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmCommentDescription() {
		return mCommentDescription;
	}

	public void setmCommentDescription(String mCommentDescription) {
		this.mCommentDescription = mCommentDescription;
	}

	public String getmPoints() {
		return mPoints;
	}

	public void setmPoints(String mPoints) {
		this.mPoints = mPoints;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public String getmTotalPoints() {
		return mTotalPoints;
	}

	public void setmTotalPoints(String mTotalPoints) {
		this.mTotalPoints = mTotalPoints;
	}
	
	
   
    
    

    
}
