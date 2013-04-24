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

/**
 * The Class Loading.
 */
public class Loading extends Activity {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_loading);
        
        new Handler().postDelayed (new Runnable(){
        	@Override
        	public void run(){
        		Intent game = (new Intent(getApplicationContext(),Game.class));
    			int levelSelected = getIntent().getIntExtra("levelselected", 0);
    			game.putExtra("levelselected", levelSelected);
    			
    			startActivity(game);
        		finish();
        	}
        },500);
    }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
    protected void onDestroy() {
        Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
    }
}   

