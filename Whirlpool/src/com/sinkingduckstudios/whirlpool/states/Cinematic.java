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

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.views.CinematicView;

public class Cinematic extends Activity {
	static public int mSlide = -1;
	CinematicView cinematicView;
	private Timer mTime;
	private Handler mHandler;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_cinematic);
        cinematicView=(CinematicView)findViewById(R.id.cinematicView);
        
        if(mTime!=null){
			mTime.cancel();
			mTime = null;
		}
		mTime= new Timer();//init timer
		mTime.schedule(new MainThread(),0, 1500);
		
		// creates a handler to deal wit the return from the timer
		mHandler = new Handler() {
		
			public void handleMessage(Message aMsg) {
		
				if (aMsg.what == 0){//redraw
					cinematicView.invalidate();
				}
			}
		};
    }
	class MainThread extends TimerTask {
		public void run() {
			if(mSlide >= 5){
        		Intent loading = (new Intent(getApplicationContext(),Loading.class));
    			
    			loading.putExtra("levelselected", getIntent().getIntExtra("levelselected", 0));
    			mTime.cancel();
    			startActivity(loading);
        		finish();
    		}else{
        		mSlide++;
    		}
			mHandler.sendEmptyMessage(0);
		}

	}

}   

