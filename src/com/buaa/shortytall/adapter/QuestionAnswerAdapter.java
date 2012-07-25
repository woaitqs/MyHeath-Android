package com.buaa.shortytall.adapter;

import java.util.ArrayList;
import java.util.List;

import com.buaa.shortytall.bean.Comments;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.bean.QuestionAnswer;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class QuestionAnswerAdapter extends BaseAdapter{

    private Context mContext;
    private Handler mHandler;
    private List<QuestionAnswer> mQuestion;
    
    public QuestionAnswerAdapter(Context context, Handler handler){
        this.mContext = context;
        this.mHandler = handler;
        this.mQuestion = new ArrayList<QuestionAnswer>();
    }
    
    public void setData(List<QuestionAnswer> mQuestion){
        this.mQuestion = mQuestion;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        if (mQuestion != null){
            return mQuestion.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        if (mQuestion != null){
            return mQuestion.get(arg0);
        }
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
        QuestionsItem item;
        if ( convertView == null || convertView.getTag() == null){
            item = new QuestionsItem(mContext);
        } else{
            item = (QuestionsItem)convertView.getTag();
        }
        
        final QuestionAnswer QandA = mQuestion.get(position);
        item.setTitle(QandA.getQtitle());
        item.setQDescription(QandA.getQdescription());
        item.setADescription(QandA.getAdescription());
        item.setAName(QandA.getAName());
        item.setATime(QandA.getAconsulttime());
        item.setQTime(QandA.getQconsulttime());
        return item.getView();
    }

}
