/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.views.CinematicView;

public class Cinematic extends Activity {
	static public int mSlide = 0;
	CinematicView cinematicView;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_cinematic);
        cinematicView=(CinematicView)findViewById(R.id.cinematicView);
        mSlide = 0;
        new Handler().postDelayed (new Runnable(){
        	@Override
        	public void run(){
        		if(mSlide >= 6){
	        		Intent loading = (new Intent(getApplicationContext(),Loading.class));
	    			
	    			loading.putExtra("levelselected", getIntent().getIntExtra("levelselected", 0));
	    			
	    			startActivity(loading);
	        		finish();
        		}else{
        			mSlide++;
        		}
        	}
        },1600);
    }
	

}   

