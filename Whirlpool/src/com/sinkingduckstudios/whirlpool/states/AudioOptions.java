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
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.AudioOptionsView;

/**
 * The Class AudioOptions.
 */
public class AudioOptions extends Activity{
	/** The audio options view. */
	AudioOptionsView audioOptionsView;
	/** The audio check value. */
	private int audio_check;
	/** The audiocheck. */
	private int audiocheck;
	/** The Constant AUDIO string. */
	public static final String AUDIO = "audio_options";
	ImageButton audio1Button;
	ImageButton audio2Button;
	ImageButton audio3Button;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_audiooptions);

		Constants.clearLevel();
		Constants.setState(this);

		ImageButton backButton = ((ImageButton)findViewById(R.id.op_return));
		audio1Button = ((ImageButton)findViewById(R.id.audio1));
		audio2Button = ((ImageButton)findViewById(R.id.audio2));
		audio3Button = ((ImageButton)findViewById(R.id.audio3));

		Constants.setContext(getApplicationContext());

		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);

		backButton.setOnClickListener(goToOp);
		audio1Button.setOnClickListener(audio1);
		audio2Button.setOnClickListener(audio2);
		audio3Button.setOnClickListener(audio3);
		/*
		audioOptionsView=(AudioOptionsView)findViewById(R.id.audioOptionsView);
		SharedPreferences prefs = getSharedPreferences(AUDIO, MODE_PRIVATE);
		audio_check = prefs.getInt("audiocheck", audiocheck);

		switch(audio_check){

		case 1:
			audio1Button.setBackgroundResource(R.drawable.selecter);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
			break;
		case 2:
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			audio2Button.setBackgroundResource(R.drawable.selecter);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
			break;
		case 3:
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			audio3Button.setBackgroundResource(R.drawable.selecter);
			break;

		}*/
		checkButton();
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

	/** The go to option button. */
	private OnClickListener goToOp = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			startActivity(new Intent(getApplicationContext(), Options.class));
			finish();
		}
	};

	private OnClickListener audio1 = new OnClickListener() {
		@Override
		public void onClick(View view) {
			/*
			SharedPreferences prefs = getSharedPreferences(AUDIO, MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			audiocheck = 1;
			editor.putInt("audiocheck",audiocheck);
			editor.commit();

			View audio1Button = findViewById(R.id.audio1);
			audio1Button.setBackgroundResource(R.drawable.selecter);
			View audio2Button = findViewById(R.id.audio2);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			View audio3Button = findViewById(R.id.audio3);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
			 */
			Constants.sBackgroundVolume = 1;
			Constants.sEffectVolume = 1;
			checkButton();
		}
	};

	private OnClickListener audio2 = new OnClickListener() {

		@Override
		public void onClick(View view) {
			/*
			SharedPreferences prefs = getSharedPreferences(AUDIO, MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			audiocheck = 2;
			editor.putInt("audiocheck",audiocheck);
			editor.commit();

			View audio1Button = findViewById(R.id.audio1);
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			View audio2Button = findViewById(R.id.audio2);
			audio2Button.setBackgroundResource(R.drawable.selecter);
			View audio3Button = findViewById(R.id.audio3);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
			 */
			Constants.sBackgroundVolume = 0;
			Constants.sEffectVolume = 1;
			checkButton();
		}
	};
	private OnClickListener audio3 = new OnClickListener() {

		//SharedPreferences prefs = getSharedPreferences(AUDIO, MODE_PRIVATE);
		//SharedPreferences.Editor editor = prefs.edit();

		@Override
		public void onClick(View view) {
			/*
			SharedPreferences prefs = getSharedPreferences(AUDIO, MODE_PRIVATE);
			SharedPreferences.Editor editor = prefs.edit();
			audiocheck = 3;
			editor.putInt("audiocheck",audiocheck);
			editor.commit();

			View audio1Button = findViewById(R.id.audio1);
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			View audio2Button = findViewById(R.id.audio2);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			View audio3Button = findViewById(R.id.audio3);
			audio3Button.setBackgroundResource(R.drawable.selecter);*/
			Constants.sBackgroundVolume = 0;
			Constants.sEffectVolume = 0;
			checkButton();
		}
	};


	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy(){
		if(audioOptionsView != null){
			audioOptionsView.CleanUp();
			audioOptionsView = null;
		}
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}
	private void checkButton(){
		if(Constants.sBackgroundVolume == 1 && Constants.sEffectVolume == 1){
			audio1Button.setBackgroundResource(R.drawable.selecter);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
		}else if(Constants.sBackgroundVolume == 0 && Constants.sEffectVolume == 1){
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			audio2Button.setBackgroundResource(R.drawable.selecter);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
		}else if(Constants.sBackgroundVolume == 0 && Constants.sEffectVolume == 0){
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			audio3Button.setBackgroundResource(R.drawable.selecter);
		}
	}
}
