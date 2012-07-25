package com.buaa.shortytall.activity;

import java.util.ArrayList;
import java.util.List;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.buaa.shortytall.adapter.QuestionAnswerAdapter;
import com.buaa.shortytall.bean.News;
import com.buaa.shortytall.bean.QuestionAnswer;
import com.buaa.shortytall.thread.GetPersonalQuestionThread;
import com.buaa.shortytall.thread.GetAllPersonalNewsThread;
import com.buaa.shortytall.thread.GetPersonalQuestionThread.GetQusetionDetailLisntener;

import com.buaa.shortytall.thread.GetPersonalQuestionThread.GetQuestionHandler;
import com.buaa.shortytall.util.JsonUtil;


public class ProfileQusetionAndAnswerActivity extends DefaultActivity implements GetQusetionDetailLisntener{
	
	
	private ListView myList;
	private List<QuestionAnswer> mQuestions;
	private QuestionAnswerAdapter mQuestionsAdapter;
	
	
	
	 @Override
	    public void onResume() {
	        super.onResume();
	        GetQuestionHandler handler = new GetQuestionHandler(ProfileQusetionAndAnswerActivity.this);
	        GetPersonalQuestionThread thread = new GetPersonalQuestionThread(handler);
	        thread.start();
	    }
	

	@Override
	protected String getActionBarTitle() {
		// TODO Auto-generated method stub
		return "个人问题";
	}

	@Override
	protected Context getContext() {
		// TODO Auto-generated method stub
		return ProfileQusetionAndAnswerActivity.this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.personal_question);
		 myList = (ListView)findViewById(R.id.personalquestion_listview);
		 mQuestionsAdapter =  new QuestionAnswerAdapter(this, null);
		 myList.setAdapter(mQuestionsAdapter);
	}
	

	@Override
	public void getQuestionSuccessed(String json) {
		// TODO Auto-generated method stub
		System.out.println("the json question activity get "+json);
		 ArrayList<QuestionAnswer> data = JsonUtil.prasePersonalQuestionJson(json);
		 mQuestionsAdapter.setData(data);   
	}


	@Override
	public void getQuestionFailed() {
		// TODO Auto-generated method stub
		
	}
	
}
