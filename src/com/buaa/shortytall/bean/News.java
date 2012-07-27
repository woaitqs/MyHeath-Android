package com.buaa.shortytall.bean;

import java.util.ArrayList;

public class News {

    public News(String mTitle, String mContent, String mAvatar, String mId,
            String mDate, int mCommentCount, ArrayList<Comment> mComments) {
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mAvatar = mAvatar;
        this.mId = mId;
        this.mDate = mDate;
        this.mCommentCount = mCommentCount;
        this.mComments = mComments;
    }

    private String mTitle;
    private String mContent;
    private String mAvatar;
    private String mId;
    private String mDate;
    private int mCommentCount;
    private ArrayList<Comment> mComments;
    
    public int getmCommentCount() {
        return mCommentCount;
    }

    public void setmCommentCount(int mCommentCount) {
        this.mCommentCount = mCommentCount;
    }

    public void setmComments(ArrayList<Comment> mComments) {
        this.mComments = mComments;
    }

    public ArrayList<Comment> getmComments() {
        return mComments;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public static class NewsBuider{
        private String title = "";
        private String content = "";
        private String avatar = "";
        private String id = "";
        private String date = "";
        private int comment_count = 0;
        private ArrayList<Comment> comments = new ArrayList<Comment>();
        private News news = new News(title, content, avatar, id, date, comment_count,comments);
        
        public News build(){
            return news;
        }
        
        public NewsBuider setTitle(String title){
            news.setmTitle(title);
            return this;
        }
        
        public NewsBuider setContent(String content){
            news.setmContent(content);
            return this;
        }
        
        public NewsBuider setAvatart(String url){
            news.setmAvatar(url);
            return this;
        }
        
        public NewsBuider setId(String id){
            news.setmId(id);
            return this;
        }
        
        public NewsBuider setDate(String date){
            news.setmDate(date);
            return this;
        }
        
        public NewsBuider setCommentCount(int count){
            news.setmCommentCount(count);
            return this;
        }
        
        public NewsBuider setComments(ArrayList<Comment> comments){
            news.setmComments(comments);
            return this;
        }
    }
}
