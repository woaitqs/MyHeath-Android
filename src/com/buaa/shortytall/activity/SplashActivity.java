package com.buaa.shortytall.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.buaa.shortytall.MyHealth;
import com.buaa.shortytall.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity{
	private boolean mHasTouchScreen = false;
	private long mStartTime; 
	private Timer mTimer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   WindowManager.LayoutParams.FLAG_FULLSCREEN); 
		mTimer = new Timer(true);
		mStartTime = System.currentTimeMillis();
		mTimer.schedule(task,0,1);
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			mHasTouchScreen = true;
		}
		return super.onTouchEvent(event);
	}
	
	private final TimerTask task = new TimerTask(){

		@Override
		public void run() {
			if(task.scheduledExecutionTime() - mStartTime >= MyHealth.SPALSH_TIME || mHasTouchScreen ){
				Message message = new Message();
				message.what = MyHealth.Msg.FLASH_IMG_FINISHED;
				timerHandler.sendMessage(message);
				task.cancel();
				this.cancel();
			}
		}
		
	};
	
	private final Handler timerHandler =  new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
				case MyHealth.Msg.FLASH_IMG_FINISHED:
					SplashActivity.this.finish();
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this, New_MainActivity.class);
					startActivity(intent);
					break;
				default: return;
			}
			super.handleMessage(msg);
		}
	};
}
