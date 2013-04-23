package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.views.LevelSelectView;

public class LevelSelect extends Activity{

	boolean mPaused = false;

	public int mLevelSelect;

	protected int score;
	protected SharedPreferences prefs;

	public static final String HIGH_SCORES = "HighScores";


	LevelSelectView levelSelectView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_levelselect);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		Constants.clearLevel();
		Constants.setState(this);

		ImageButton level1Button = ((ImageButton)findViewById(R.id.level1));
		ImageButton level2Button = ((ImageButton)findViewById(R.id.level2));
		ImageButton level2lockedButton = ((ImageButton)findViewById(R.id.level2locked));
		ImageButton level3Button = ((ImageButton)findViewById(R.id.level3));
		ImageButton level3lockedButton = ((ImageButton)findViewById(R.id.level3locked));
		ImageButton level4Button = ((ImageButton)findViewById(R.id.level4));
		ImageButton level4lockedButton = ((ImageButton)findViewById(R.id.level4locked));
		ImageButton level5Button = ((ImageButton)findViewById(R.id.level5));
		ImageButton level5lockedButton = ((ImageButton)findViewById(R.id.level5locked));
		ImageButton level6Button = ((ImageButton)findViewById(R.id.level6));
		ImageButton level6lockedButton = ((ImageButton)findViewById(R.id.level6locked));
		ImageButton returnButton = ((ImageButton)findViewById(R.id.op_return));

		prefs = getSharedPreferences("HighScores", MODE_PRIVATE);

		if(prefs.getInt("HighScore_lvl1", score) > 0)		// if previous level has a high score ( has been played)
		{
			level2Button.setVisibility(0);			// set the unlocked button to visible
			level2lockedButton.setVisibility(4);	// set the locked button to invisible
		}

		// otherwise if the previous level has not been completed
		else 
		{
			level2Button.setVisibility(4);			// set the unlocked button to invisible
			level2lockedButton.setVisibility(0);	// set the locked button to visible
		}

		if(prefs.getInt("HighScore_lvl2", score) > 0)		// if previous level has a high score ( has been played)
		{
			level3Button.setVisibility(0);			// set the unlocked button to visible
			level3lockedButton.setVisibility(4);	// set the locked button to invisible
		}

		// otherwise if the previous level has not been completed
		else 
		{
			level3Button.setVisibility(4);			// set the unlocked button to invisible
			level3lockedButton.setVisibility(0);	// set the locked button to visible
		}

		if(prefs.getInt("HighScore_lvl3", score) > 0)		// if previous level has a high score ( has been played)
		{
			level4Button.setVisibility(0);			// set the unlocked button to visible
			level4lockedButton.setVisibility(4);	// set the locked button to invisible
		}

		// otherwise if the previous level has not been completed
		else 
		{
			level4Button.setVisibility(4);			// set the unlocked button to invisible
			level4lockedButton.setVisibility(0);	// set the locked button to visible
		}

		if(prefs.getInt("HighScore_lvl4", score) > 0)		// if previous level has a high score ( has been played)
		{
			level5Button.setVisibility(0);			// set the unlocked button to visible
			level5lockedButton.setVisibility(4);	// set the locked button to invisible
		}

		// otherwise if the previous level has not been completed
		else 
		{
			level5Button.setVisibility(4);			// set the unlocked button to invisible
			level5lockedButton.setVisibility(0);	// set the locked button to visible
		}

		if(prefs.getInt("HighScore_lvl5", score) > 0)		// if previous level has a high score ( has been played)
		{
			level6Button.setVisibility(0);			// set the unlocked button to visible
			level6lockedButton.setVisibility(4);	// set the locked button to invisible
		}

		// otherwise if the previous level has not been completed
		else 
		{
			level6Button.setVisibility(4);			// set the unlocked button to invisible
			level6lockedButton.setVisibility(0);	// set the locked button to visible
		}


		Constants.setContext(getApplicationContext());

		int theHeight = Constants.getScreen().getHeight();
		int theWidth = Constants.getScreen().getWidth();

		int scale = theHeight/3;
		if ((theWidth/4)< scale)
			scale = theWidth/4;

		int xstep=theWidth/4; int ystep=theHeight/3;

		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
				scale, scale);

		level1Button.setLayoutParams(buttonParams);
		level1Button.setX(xstep-(scale/2));
		level1Button.setY(ystep-(scale/2));

		xstep += theWidth/4;

		level2Button.setLayoutParams(buttonParams);
		level2Button.setX(xstep-(scale/2));
		level2Button.setY(ystep-(scale/2));
		
		level2lockedButton.setLayoutParams(buttonParams);
		level2lockedButton.setX(xstep-(scale/2));
		level2lockedButton.setY(ystep-(scale/2));

		xstep += theWidth/4;

		level3Button.setLayoutParams(buttonParams);
		level3Button.setX(xstep-(scale/2));
		level3Button.setY(ystep-(scale/2));	

		level3lockedButton.setLayoutParams(buttonParams);
		level3lockedButton.setX(xstep-(scale/2));
		level3lockedButton.setY(ystep-(scale/2));	
		
		ystep+= theHeight/3;
		xstep = theWidth/4;

		level4Button.setLayoutParams(buttonParams);
		level4Button.setX(xstep-(scale/2));
		level4Button.setY(ystep-(scale/2));	
		
		level4lockedButton.setLayoutParams(buttonParams);
		level4lockedButton.setX(xstep-(scale/2));
		level4lockedButton.setY(ystep-(scale/2));


		xstep += theWidth/4;

		level5Button.setLayoutParams(buttonParams);
		level5Button.setX(xstep-(scale/2));
		level5Button.setY(ystep-(scale/2));	
		
		level5lockedButton.setLayoutParams(buttonParams);
		level5lockedButton.setX(xstep-(scale/2));
		level5lockedButton.setY(ystep-(scale/2));	

		xstep += theWidth/4;

		level6Button.setLayoutParams(buttonParams);
		level6Button.setX(xstep-(scale/2));
		level6Button.setY(ystep-(scale/2));	
		
		level6lockedButton.setLayoutParams(buttonParams);
		level6lockedButton.setX(xstep-(scale/2));
		level6lockedButton.setY(ystep-(scale/2));

		level1Button.setOnClickListener(goToLevel1);
		level2Button.setOnClickListener(goToLevel2);
		level3Button.setOnClickListener(goToLevel3);
		level4Button.setOnClickListener(goToLevel4);
		level5Button.setOnClickListener(goToLevel5);
		level6Button.setOnClickListener(goToLevel6);
		returnButton.setOnClickListener(goToZone);
		levelSelectView=(LevelSelectView)findViewById(R.id.levelSelectView);

		SharedPreferences prefs = getSharedPreferences("HighScores", MODE_PRIVATE);
		int stars[] = new int[6];

		for(int i = 0; i < 6; i++){
			stars[i] =  prefs.getInt("Stars_lvl"+Integer.toString(i+1), 0);
		}

		levelSelectView.initStars(stars);
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
	private OnClickListener goToLevel1 = new OnClickListener() {
		@Override
		public void onClick(View view){

			//mLevelSelect = 1;
			Constants.getSoundManager().playSplash();

			startActivity(new Intent(getApplicationContext(), Tutorial.class));

			finish();
			//Code to select level1
		}
	};
	private OnClickListener goToZone = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

			startActivity(new Intent(getApplicationContext(), ZoneScreen.class));
			finish();
		}
	};
	public void onBackPressed(){
		synchronized(Constants.getLock()){
			Constants.getSoundManager().playSplash();

			startActivity(new Intent(getApplicationContext(), ZoneScreen.class));
			finish();
		}
	}
	private OnClickListener goToLevel2 = new OnClickListener() {
		@Override
		public void onClick(View view){
			Constants.getSoundManager().playSplash();

			//mLevelSelect = 2;
			if(prefs.getInt("HighScore_lvl1", score) > 0)	// if the previous level has a high score then then level much have been completed therfor you can play the next level
			{

			Intent loading = (new Intent(getApplicationContext(),Loading.class));

			loading.putExtra("levelselected", 2);

			startActivity(loading);

			finish();
			}
			//Code to select level2
		}
	};
	private OnClickListener goToLevel3 = new OnClickListener() {
		@Override
		public void onClick(View view){
			Constants.getSoundManager().playSplash();

			//mLevelSelect = 2;
			if(prefs.getInt("HighScore_lvl2", score) > 0)	// if the previous level has a high score then then level much have been completed therfor you can play the next level
			{

			Intent loading = (new Intent(getApplicationContext(),Loading.class));

			loading.putExtra("levelselected", 3);

			startActivity(loading);

			finish();
			}
			//Code to select level2
		}
	};
	private OnClickListener goToLevel4 = new OnClickListener() {
		@Override
		public void onClick(View view){
			Constants.getSoundManager().playSplash();

			//mLevelSelect = 2;
			if(prefs.getInt("HighScore_lvl3", score) > 0)	// if the previous level has a high score then then level much have been completed therfor you can play the next level
			{

			Intent loading = (new Intent(getApplicationContext(),Loading.class));

			loading.putExtra("levelselected", 4);

			startActivity(loading);

			finish();
			}
			//Code to select level2
		}
	};
	private OnClickListener goToLevel5 = new OnClickListener() {
		@Override
		public void onClick(View view){
			Constants.getSoundManager().playSplash();

			//mLevelSelect = 2;
			if(prefs.getInt("HighScore_lvl4", score) > 0)	// if the previous level has a high score then then level much have been completed therfor you can play the next level
			{

			Intent loading = (new Intent(getApplicationContext(),Loading.class));

			loading.putExtra("levelselected", 5);

			startActivity(loading);

			finish();
			}
			//Code to select level2
		}
	};
	private OnClickListener goToLevel6 = new OnClickListener() {
		@Override
		public void onClick(View view){

			//mLevelSelect = 2;
			Constants.getSoundManager().playSplash();
			if(prefs.getInt("HighScore_lvl5", score) > 0)	// if the previous level has a high score then then level much have been completed therfor you can play the next level	
			{

			Intent loading = (new Intent(getApplicationContext(),Loading.class));

			loading.putExtra("levelselected", 6);

			startActivity(loading);

			finish();
			}
			//Code to select level2
		}
	};	
	@Override
	public void onDestroy(){
		levelSelectView.CleanUp();
		levelSelectView = null;
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}

}
