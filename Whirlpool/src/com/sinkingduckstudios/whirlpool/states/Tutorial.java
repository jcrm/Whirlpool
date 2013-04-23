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
import com.sinkingduckstudios.whirlpool.views.TutorialView;

public class Tutorial extends Activity {
	static public int mSlide = -1;
	TutorialView tutorialView;
	private Timer mTime;
	private Handler mHandler;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_tutorial);
        tutorialView=(TutorialView)findViewById(R.id.tutorialView);
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
	@Override
	public void onDestroy(){
		Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
	}
	class MainThread extends TimerTask {
		public void run() {
			if(mSlide >= 3){
        		Intent loading = (new Intent(getApplicationContext(),LevelSelect.class));
    			
    			loading.putExtra("levelselected", 1);
    			mTime.cancel();
    			startActivity(loading);
        		finish();
        		tutorialView.CleanUp();
    		}else{
        		mSlide++;
    		}
			mHandler.sendEmptyMessage(0);
		}

	}

}   

