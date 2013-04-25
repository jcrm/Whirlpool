/*
 * Author:Lewis Shaw, Jake Morey
 * Last Updated:25/04/2013
 * Content:
 * Lewis Shaw - Created the Options screen with working buttons
 * Jake Morey: added go to credits and tutorial functions
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

/**
 * The Class Options.
 */
public class Options extends Activity {
	OptionsView optionsView;
	public static final String HIGH_SCORES = "HighScores";
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_options);
		
		Constants.clearLevel();
		Constants.setState(this);
		//set up image buttons
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
		//set the button functions
		audioButton.setOnClickListener(goToAudio);
		returnButton.setOnClickListener(goToMenu);
		resetButton.setOnClickListener(resetData);
		creditsButton.setOnClickListener(goToCredits);
		tutorialButton.setOnClickListener(goToTutorial);
		cinematicButton.setOnClickListener(goToCinematic);
		
		optionsView=(OptionsView)findViewById(R.id.optionsView);
		
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
	/** The go to audio button.*/
	private OnClickListener goToAudio = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		startActivity(new Intent(getApplicationContext(), AudioOptions.class));
    		finish();
        }
	};
	/** The go to menu button.*/
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		startActivity(new Intent(getApplicationContext(), Menu.class));
    		finish();
        }
	};
	/** The reset data button.*/
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
	/** The go to credits button.*/
	private OnClickListener goToCredits = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		//Credits code
			Constants.getSoundManager().playSplash();
			startActivity(new Intent(getApplicationContext(), Credits.class));
    		finish();
        }
	};
	/** The go to tutorial button.*/
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
	/** The go to cinematic button.*/
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
		optionsView.CleanUp();
		optionsView = null;
		Runtime.getRuntime().gc();
        System.gc();
		super.onDestroy();
	}

}
