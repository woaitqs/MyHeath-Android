package com.buaa.shortytall.bean;

public class News {

    private String mTitle;
    private String mContent;
    private String mUrl;
    private String mAvatar;
    
    public News(String mTitle, String mContent, String mUrl) {
        super();
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mUrl = mUrl;
    }
    
    public String getAvatar(){
        return this.mAvatar;
    }
    
    public void setAvatar(String avatar){
        this.mAvatar = avatar;
    }
    
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getContent() {
        return mContent;
    }
    public void setContent(String mContent) {
        this.mContent = mContent;
    }
    public String getUrl() {
        return mUrl;
    }
    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
    
    public String getSubContent(){
        //TODO
        return mContent;
    }
}
