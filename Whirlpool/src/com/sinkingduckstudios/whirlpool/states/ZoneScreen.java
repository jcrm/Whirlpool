/*
 * Author:Lewis Shaw
 * Last Updated:25/04/2013
 * Content:
 * Lewis Shaw Created the ZoneSelect screen
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.ZoneScreenView;

/**
 * The Class ZoneScreen.
 */
public class ZoneScreen extends Activity{
	/** The zone screen view. */
	ZoneScreenView zoneScreenView;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_zonescreen);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		ImageButton bathZoneButton = ((ImageButton)findViewById(R.id.bathzone));
		ImageButton returnButton = ((ImageButton)findViewById(R.id.op_return));
		Constants.setContext(getApplicationContext());
		
		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);
		
		bathZoneButton.setOnClickListener(goToCinematic);
		returnButton.setOnClickListener(goToMenu);
		zoneScreenView=(ZoneScreenView)findViewById(R.id.zoneScreenView);
	}

	/** The go to cinematic button. */
	private OnClickListener goToCinematic = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

			Intent loading = (new Intent(getApplicationContext(),Cinematic.class));
			loading.putExtra("cinematic", 1);
			startActivity(loading);
    		finish();
        }
	};
	
	/** The go to menu button. */
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
    		startActivity(new Intent(getApplicationContext(), Menu.class));
    		finish();
        }
	};
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	public void onBackPressed(){
		Constants.getSoundManager().playSplash();
		startActivity(new Intent(getApplicationContext(), Menu.class));
		finish();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy(){
		zoneScreenView.CleanUp();
		zoneScreenView = null;
        Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override 
	public void onResume(){
		Constants.createSoundManager(getApplicationContext());
        Constants.getSoundManager().loadSplash();
        super.onResume();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override 
	public void onPause(){
		Constants.getSoundManager().unloadAll();
		super.onPause();
	}
}
