/*
 * Author: Fraser Tomison & Lewis Shaw
 * Last Updated: 22/04/2013
 * Content: 
 * Lewis Shaw - Displaying Score achieved, saves score plus the level number together
 * Fraser Tomison: added scaling code and some score functionality
 */

package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.views.ScoreScreenView;

/**
 * The Class ScoreScreen.
 */
public class ScoreScreen extends Activity {

	/** The Constant PREFS_NAME string. */
	public static final String PREFS_NAME = "Bath_Score";
	/** The time passed. */
	private int timepassed;
	/** The level selected. */
	private int levelselected;
	/** The 2-star time required (in secs). */
	private int levelAverageTime;
	/** The 3-star time required (in secs). */
	private int levelGoodTime;
	/** The score value. */
	private int score;
	/** The next value. */
	private int next;
	/** The highscore value. */
	private int highscore;
	/** The number of stars. */
	private int stars;
	/** The Constant HIGH_SCORES string. */
	public static final String HIGH_SCORES = "HighScores";
	/** The scorescreen view. */
	ScoreScreenView scorescreenView;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_scorescreen);
		//retrieve values from previous activities
		Intent scorescreen = getIntent();
		timepassed = scorescreen.getIntExtra("timepassed", 0);
		levelselected = scorescreen.getIntExtra("levelselected", 0);
		levelAverageTime = scorescreen.getIntExtra("levelAverage",0);
		levelGoodTime = scorescreen.getIntExtra("levelGood",0);
		next = levelselected +1;

		//Way score can be calculated can be changed at a later date
		score = (500 - timepassed);
		
		SharedPreferences prefs = getSharedPreferences(HIGH_SCORES, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		Typeface face = Typeface.createFromAsset(getAssets(), "whirlpool.ttf");
		//change high score if better than previous
		if(score >= (prefs.getInt("HighScore_lvl"+levelselected, score))){
			editor.putInt("HighScore_lvl"+levelselected, score);
		}
		//display stars based upon the level average and good times
		stars=1;
		if(timepassed <= levelAverageTime)
			stars=2;
		if(timepassed <= levelGoodTime)
			stars=3;
		//change the number of stars in preferences if its lower then whats already there
		if(stars > (prefs.getInt("Stars_lvl"+levelselected, 0))){
			editor.putInt("Stars_lvl"+levelselected, stars);
		}
		editor.commit();
		highscore = prefs.getInt("HighScore_lvl"+levelselected, score);

		Constants.clearLevel();
		Constants.setState(this);
		//show score text
		TextView Score = (TextView) findViewById(R.id.score);
		Score.setText("Score: " + score);
		Score.setTextColor(Color.BLACK);
		Score.setY(Constants.getScreen().getHeight()*0.66f);
		Score.setTypeface(face);;
		//show high score text
		TextView HScore = (TextView) findViewById(R.id.highscore);
		HScore.setText("HighScore: " + highscore);
		HScore.setTextColor(Color.BLACK);
		HScore.setY(Score.getY()+Score.getHeight());
		HScore.setTypeface(face);
		//create buttons and put them in the right location
		ImageButton menuButton = ((ImageButton)findViewById(R.id.op_return));
		ImageButton nextButton = ((ImageButton)findViewById(R.id.next_level));
		Constants.setContext(getApplicationContext());		
		menuButton.setOnClickListener(goToMenu);
		nextButton.setOnClickListener(nextLevel);
		menuButton.setY(HScore.getY()+HScore.getHeight());
		nextButton.setY(HScore.getY()+HScore.getHeight());

		scorescreenView=(ScoreScreenView)findViewById(R.id.scorescreenView);
		scorescreenView.setStars(stars);
		if(levelselected == 6){
			nextButton.setVisibility(View.INVISIBLE);
		}
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
		//Constants.getSoundManager().cleanup();
		super.onPause();
	}
	/** The next level button.*/
	private OnClickListener nextLevel = new OnClickListener() {
		@Override
		public void onClick(View view) {
			finish();
			Intent nextlevel = (new Intent(getApplicationContext(), Loading.class));
			nextlevel.putExtra("levelselected", next);
			startActivity(nextlevel);
		}
	};
	/** The go to menu button.*/
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

			finish();
			startActivity(new Intent(getApplicationContext(), LevelSelect.class));
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
		scorescreenView.CleanUp();
		scorescreenView = null;
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}
}
