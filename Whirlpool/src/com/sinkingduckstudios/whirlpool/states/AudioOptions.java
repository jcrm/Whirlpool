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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.AudioOptionsView;

public class AudioOptions extends Activity implements OnClickListener{
	boolean mPaused = false;
	AudioOptionsView audioOptionsView;
	RadioGroup maudioGroup;
	RadioButton audio1, audio2, audio3;

	private int audio_check;
	private int audiocheck;

	public static final String AUDIO = "audio_options";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_audiooptions);

		Constants.clearLevel();
		Constants.setState(this);

		ImageButton backButton = ((ImageButton)findViewById(R.id.op_return));
		audio1 = (RadioButton)findViewById(R.id.audio1); //Music on, SFX on
		audio2 = (RadioButton)findViewById(R.id.audio2); //Music off, SFX on
		audio3 = (RadioButton)findViewById(R.id.audio3); //Music off, SFX off
		Constants.setContext(getApplicationContext());

		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);

		backButton.setOnClickListener(goToOp);
		audio1.setOnClickListener((OnClickListener) this);
		audio2.setOnClickListener((OnClickListener) this);
		audio3.setOnClickListener((OnClickListener) this);

		audioOptionsView=(AudioOptionsView)findViewById(R.id.audioOptionsView);
		SharedPreferences prefs = getSharedPreferences(AUDIO, MODE_PRIVATE);
		audio_check = prefs.getInt("audiocheck", audiocheck);

		switch(audio_check){

		case 1:
			audio1.setChecked(true);
			break;
		case 2:
			audio2.setChecked(true);
			break;
		case 3:
			audio3.setChecked(true);
			break;

		}

	}
	public void onClick(View v){

		SharedPreferences prefs = getSharedPreferences(AUDIO, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();

		switch(v.getId()){

		case R.id.audio1:
			//Constants.getSoundManager().setVolume(1);
			audiocheck = 1; 
			editor.putInt("audiocheck", audiocheck);
			editor.commit();
			audio1.setChecked(true);
			break;
		case R.id.audio2:
			audiocheck = 2;
			editor.putInt("audiocheck",audiocheck);
			editor.commit();
			audio2.setChecked(true);
			break;
		case R.id.audio3:
			//Constants.getSoundManager().setVolume(0);
			audiocheck = 3;
			editor.putInt("audiocheck",audiocheck);
			editor.commit();
			audio3.setChecked(true);
			break;
		default:
			audiocheck = 1;
			editor.putInt("audiocheck",audiocheck);
			editor.commit();
			audio1.setChecked(true);
			break;
		}
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
	private OnClickListener goToOp = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			startActivity(new Intent(getApplicationContext(), Options.class));
			finish();
		}
	};
	public void onBackPressed(){
		Constants.getSoundManager().playSplash();
		startActivity(new Intent(getApplicationContext(), Options.class));
		finish();
	}
	@Override
	public void onDestroy(){
		audioOptionsView.CleanUp();
		audioOptionsView = null;
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}

}
