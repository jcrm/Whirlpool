/*
 * Author: Jake Morey
 * Content:
 * Jake Morey: Based upon game activity class.
 * the slide counter represents each image in the cinemtaic view class.
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

/**
 * The Class Cinematic.
 */
public class Cinematic extends Activity {
	/** The slide counter. */
	static public int mSlide = -1;
	/** The cinematic view. */
	CinematicView cinematicView;
	/** The timer. */
	private Timer mTime;
	/** The handler. */
	private Handler mHandler;
	/** The cinematic type. */
	private int cinematic;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_cinematic);
		cinematicView=(CinematicView)findViewById(R.id.cinematicView);
		//retrieve what screen the cinematic was started
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy(){
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}
	
	/**
	 * The Class MainThread.
	 */
	class MainThread extends TimerTask {
		
		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		public void run() {
			//if the slides have finished go to the next screen else increment the slide count
			if(mSlide >= 5){
				finishCinematic();
			}else{
				mSlide++;
			}
			mHandler.sendEmptyMessage(0);
		}

	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			finishCinematic();
		}
		return true;
	}
	
	/**
	 * Finish cinematic.
	 */
	private void finishCinematic(){
		//depending on what menu the cinematic was started depends where it returns to
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
}   

