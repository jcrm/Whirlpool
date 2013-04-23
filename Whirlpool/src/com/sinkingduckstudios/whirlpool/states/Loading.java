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
import com.sinkingduckstudios.whirlpool.logic.Constants;

public class Loading extends Activity {
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
	@Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
    }
}   

