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
import com.sinkingduckstudios.whirlpool.views.MenuView;

public class Loading extends Activity {
	boolean mPaused = false;
	MenuView menuView;
    //@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_loading);
        
        new Handler().postDelayed (new Runnable(){
        	@Override
        	public void run(){
        		Intent game = (new Intent(getApplicationContext(),Game.class));
    			
    			game.putExtra("levelselected", getIntent().getIntExtra("levelselected", 0));
    			
    			startActivity(game);
        		finish();
        	}
        },500);
    }
	

}   

