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
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.OptionsView;

public class Options extends Activity {
	boolean mPaused = false;
	OptionsView optionsView;
	public static final String HIGH_SCORES = "HighScores";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_options);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		ImageButton audioButton = ((ImageButton)findViewById(R.id.audio));
		ImageButton returnButton = ((ImageButton)findViewById(R.id.op_return));
		ImageButton resetButton = ((ImageButton)findViewById(R.id.resetdata));
		ImageButton creditsButton = ((ImageButton)findViewById(R.id.credits));
		ImageButton tutorialButton = ((ImageButton)findViewById(R.id.tutorial));
		ImageButton cinematicButton = ((ImageButton)findViewById(R.id.cinematic));
		

		Constants.setContext(getApplicationContext());
		
		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);
		
		audioButton.setOnClickListener(goToAudio);
		returnButton.setOnClickListener(goToMenu);
		resetButton.setOnClickListener(resetData);
		creditsButton.setOnClickListener(goToCredits);
		tutorialButton.setOnClickListener(goToTutorial);
		cinematicButton.setOnClickListener(goToCinematic);

		
		optionsView=(OptionsView)findViewById(R.id.optionsView);
		
	}
	@Override 
	public void onResume(){
		Constants.createSoundManager(getApplicationContext());
        Constants.getSoundManager().loadSplash();
        super.onResume();
	}
	@Override 
	public void onPause(){
		Constants.getSoundManager().unloadAll();
		super.onPause();
	}
	private OnClickListener goToAudio = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		startActivity(new Intent(getApplicationContext(), AudioOptions.class));
    		finish();
        }
	};
	
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		startActivity(new Intent(getApplicationContext(), Menu.class));
    		finish();
        }
	};
	
	private OnClickListener resetData = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			//Editor editor = settings.edit();
			SharedPreferences prefs = getSharedPreferences(HIGH_SCORES, MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			
			editor.clear();
			editor.commit();
    		
        }
	};
	
	private OnClickListener goToCredits = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		//Credits code
			Constants.getSoundManager().playSplash();
        }
	};
	
	private OnClickListener goToTutorial = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			Intent tutorialscreen = (new Intent(getApplicationContext(),Tutorial.class));
			tutorialscreen.putExtra("tutorial", 2);
			startActivity(tutorialscreen);
    		finish();
    		//Tutorial code
        }
	};
	
	private OnClickListener goToCinematic = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		//Cinematic code
			Constants.getSoundManager().playSplash();
			Intent cinematicscreen = (new Intent(getApplicationContext(),Cinematic.class));
			cinematicscreen.putExtra("cinematic", 2);
			startActivity(cinematicscreen);
			finish();
        }
	};

	public void onBackPressed(){
		Constants.getSoundManager().playSplash();

		startActivity(new Intent(getApplicationContext(), Menu.class));
		finish();
	}
	@Override
	public void onDestroy(){
		optionsView.CleanUp();
		optionsView = null;
		Runtime.getRuntime().gc();
        System.gc();
		super.onDestroy();
	}

}
