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
import com.sinkingduckstudios.whirlpool.views.TutorialView;

/**
 * The Class Tutorial.
 */
public class Tutorial extends Activity{
	
	/** The slide counter. */
	static public int mSlide = -1;
	/** The tutorial view. */
	TutorialView tutorialView;
	/** The timer. */
	private Timer mTime;
	/** The handler. */
	private Handler mHandler;
	/** The tutorial type. */
	private int tutorial;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tutorial);
        tutorialView=(TutorialView)findViewById(R.id.tutorialView);
        
        Intent tutorialscreen = getIntent();
		tutorial = tutorialscreen.getIntExtra("tutorial",0);
        mSlide = -1;
        if(mTime!=null){
			mTime.cancel();
			mTime = null;
		}
		// creates a handler to deal wit the return from the timer
		mHandler = new Handler() {
			public void handleMessage(Message aMsg) {
				if (aMsg.what == 0){//redraw
					tutorialView.invalidate();
				}
			}
		};
		
		mTime= new Timer();//init timer
		mTime.schedule(new MainThread(),0, 2000);
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
			if(mSlide >= 3){
				finsihTutorial();
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
			finsihTutorial();
		}
		return true;
	}
	
	/**
	 * Finsih tutorial depending on where coming from.
	 */
	private void finsihTutorial(){
		switch(tutorial){
		case 1:
    		Intent loading = (new Intent(getApplicationContext(),Loading.class));
			loading.putExtra("levelselected", 1);
			mTime.cancel();
			startActivity(loading);
    		finish();
    		tutorialView.CleanUp();
		break;
		case 2:
			startActivity(new Intent(getApplicationContext(), Options.class));
			mTime.cancel();
			finish();
    		tutorialView.CleanUp();
			break;
		}
	}
}   

