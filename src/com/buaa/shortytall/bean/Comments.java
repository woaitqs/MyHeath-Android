package com.buaa.shortytall.bean;

public class Comments {
	
	private String mName;
    private String mDescription;
    private String mPoints;
    private String mTime;
    
	public Comments(String mName, String mDescription, String mPoints,
			String mTime) {
		super();
		this.mName = mName;
		this.mDescription = mDescription;
		this.mPoints = mPoints;
		this.mTime = mTime;
	}
	
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getmDescription() {
		return mDescription;
	}
	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
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

}
