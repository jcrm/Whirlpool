/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.states;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.views.CinematicView;

public class Cinematic extends Activity {
	static public int mSlide = -1;
	CinematicView cinematicView;
	private Timer mTime;
	private Handler mHandler;
	private int cinematic;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_cinematic);
		cinematicView=(CinematicView)findViewById(R.id.cinematicView);

		Intent cinematicscreen = getIntent();
		cinematic = cinematicscreen.getIntExtra("cinematic",0);
		mSlide = -1;
		if(mTime!=null){
			mTime.cancel();
			mTime = null;
		}

		// creates a handler to deal wit the return from the timer
		mHandler = new Handler() {

			public void handleMessage(Message aMsg) {

				if (aMsg.what == 0){//redraw
					cinematicView.invalidate();
				}
			}
		};

		mTime= new Timer();//init timer
		mTime.schedule(new MainThread(),0, 1500);
	}
	@Override
	public void onDestroy(){
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}
	class MainThread extends TimerTask {
		public void run() {
			if(mSlide >= 5){

				switch(cinematic){
				case 1:
					Intent loading = (new Intent(getApplicationContext(),LevelSelect.class));
					mTime.cancel();
					startActivity(loading);
					finish();
					cinematicView.CleanUp();
					break;
				case 2:
					startActivity(new Intent(getApplicationContext(), Options.class));
					mTime.cancel();
					finish();
					cinematicView.CleanUp();
					break;
				}

			}else{
				mSlide++;
			}
			mHandler.sendEmptyMessage(0);
		}

	}
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			switch(cinematic){
			case 1:
				Intent loading = (new Intent(getApplicationContext(),LevelSelect.class));
				mTime.cancel();
				startActivity(loading);
				finish();
				cinematicView.CleanUp();
				break;
			case 2:
				startActivity(new Intent(getApplicationContext(), Options.class));
				mTime.cancel();
				finish();
				cinematicView.CleanUp();
				break;
			}
		}
		return true;
	}
}   

