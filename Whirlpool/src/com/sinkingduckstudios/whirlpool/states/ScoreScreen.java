/*
 * Author: Fraser Tomison &
 * Last Updated: 22/04/2013
 * Content: Displays score acheived, handles saving highscores
 * 
 * 
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
	
	/** The score value. */
	private int score;
	
	/** The next value. */
	private int next;
	
	/** The highscore value. */
	private int highscore;
	
	/** The mini duck counter. */
	private int miniDuckCount;
	
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
		
		Intent scorescreen = getIntent();
		miniDuckCount = scorescreen.getIntExtra("duckcount", 0);
		timepassed = scorescreen.getIntExtra("timepassed", 0);
		levelselected = scorescreen.getIntExtra("levelselected", 0);
		next = levelselected +1;

		//Way score can be calculated can be changed at a later date
		score = (500 - timepassed);
		
		SharedPreferences prefs = getSharedPreferences(HIGH_SCORES, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		Typeface face = Typeface.createFromAsset(getAssets(), "whirlpool.ttf");

		switch(levelselected){
		case 1:
			if(score >= (prefs.getInt("HighScore_lvl1", score))){
				editor.putInt("HighScore_lvl1", score);
			}
			
			stars=1;
			if(timepassed <= LEVEL_ONE_AVERAGE)
				stars=2;
			if(timepassed <= LEVEL_ONE_GOOD)
				stars=3;
			
			if(stars > (prefs.getInt("Stars_lvl1", 0))){
				editor.putInt("Stars_lvl1", stars);
			}
			editor.commit();
			highscore = prefs.getInt("HighScore_lvl1", score);
			break;
		case 2:
			if(score >= (prefs.getInt("HighScore_lvl2", score))){
				editor.putInt("HighScore_lvl2", score);
			}
			
			stars=1;
			if(timepassed <= LEVEL_TWO_AVERAGE)
				stars=2;
			if(timepassed <= LEVEL_TWO_GOOD)
				stars=3;
			
			if(stars > (prefs.getInt("Stars_lvl2", 0))){
				editor.putInt("Stars_lvl2", stars);
			}
			editor.commit();
			highscore = prefs.getInt("HighScore_lvl2", score);
			break;
		case 3:
			if(score >= (prefs.getInt("HighScore_lvl3", score))){
				editor.putInt("HighScore_lvl3", score);
			}
			
			stars=1;
			if(timepassed <= LEVEL_THREE_AVERAGE)
				stars=2;
			if(timepassed <= LEVEL_THREE_GOOD)
				stars=3;
			
			if(stars > (prefs.getInt("Stars_lvl3", 0))){
				editor.putInt("Stars_lvl3", stars);
			}
			
			editor.commit();
			highscore = prefs.getInt("HighScore_lvl3", score);
			break;
		case 4:
			if(score >= (prefs.getInt("HighScore_lvl4", score))){
				editor.putInt("HighScore_lvl4", score);
			}
			
			stars=1;
			if(timepassed <= LEVEL_FOUR_AVERAGE)
				stars=2;
			if(timepassed <= LEVEL_FOUR_GOOD)
				stars=3;
			
			if(stars > (prefs.getInt("Stars_lvl4", 0))){
				editor.putInt("Stars_lvl4", stars);
			}
			editor.commit();
			highscore = prefs.getInt("HighScore_lvl4", score);
			break;
		case 5:
			if(score >= (prefs.getInt("HighScore_lvl5", score))){
				editor.putInt("HighScore_lvl5", score);
			}
			
			stars=1;
			if(timepassed <= LEVEL_FIVE_AVERAGE)
				stars=2;
			if(timepassed <= LEVEL_FIVE_GOOD)
				stars=3;
			
			if(stars > (prefs.getInt("Stars_lvl5", 0))){
				editor.putInt("Stars_lvl5", stars);
			}
			editor.commit();
			highscore = prefs.getInt("HighScore_lvl5", score);
			break;
		case 6:
			if(score >= (prefs.getInt("HighScore_lvl6", score))){
				editor.putInt("HighScore_lvl6", score);
			}
			stars=1;
			if(timepassed <= LEVEL_SIX_AVERAGE)
				stars=2;
			if(timepassed <= LEVEL_SIX_GOOD)
				stars=3;
			if(stars > (prefs.getInt("Stars_lvl6", 0))){
				editor.putInt("Stars_lvl6", stars);
			}
			editor.commit();
			highscore = prefs.getInt("HighScore_lvl6", score);
			break;
		}
		Constants.clearLevel();
		Constants.setState(this);
		
		TextView Score = (TextView) findViewById(R.id.score);
		Score.setText("Score: " + score);
		Score.setTextColor(Color.BLACK);
		Score.setY(Constants.getScreen().getHeight()*0.66f);
		Score.setTypeface(face);;

		TextView HScore = (TextView) findViewById(R.id.highscore);
		HScore.setText("HighScore: " + highscore);
		HScore.setTextColor(Color.BLACK);
		HScore.setY(Score.getY()+Score.getHeight());
		HScore.setTypeface(face);

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
		Constants.getSoundManager().unloadAll();
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
	
	/** The level one average score. */
	private int LEVEL_ONE_AVERAGE = 40;
	/** The level one good score. */
	private int LEVEL_ONE_GOOD = 20;
	/** The level two average score. */
	private int LEVEL_TWO_AVERAGE = 30;
	/** The level two good score. */
	private int LEVEL_TWO_GOOD = 15;
	/** The level three average score. */
	private int LEVEL_THREE_AVERAGE = 25;
	/** The level three good score. */
	private int LEVEL_THREE_GOOD = 15;
	/** The level four average score. */
	private int LEVEL_FOUR_AVERAGE = 35;
	/** The level four good score. */
	private int LEVEL_FOUR_GOOD = 25;
	/** The level five average score. */
	private int LEVEL_FIVE_AVERAGE = 40;
	/** The level five good score. */
	private int LEVEL_FIVE_GOOD = 25;
	/** The level six average score. */
	private int LEVEL_SIX_AVERAGE = 45;
	/** The level six good score. */
	private int LEVEL_SIX_GOOD = 27;
}
