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
import android.widget.TextView;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.ScoreScreenView;

public class ScoreScreen extends Activity {
	
	public static final String PREFS_NAME = "Bath_Score";
	boolean mPaused = false;
	
	public int timepassed;
	public int levelselected;
	public int score;
	public int highscore;
	public int currentHS;
	
	public static final String HIGH_SCORES = "HighScores";
	//private SharedPreferences prefs;
	
	ScoreScreenView scorescreenView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_scorescreen);
		
		Intent scorescreen = getIntent();
		timepassed = scorescreen.getIntExtra("timepassed", 0);
		levelselected = scorescreen.getIntExtra("levelselected", 0);
		
		//Way score can be calculated can be changed at a later date
		score = (500 - timepassed);
		
		SharedPreferences prefs = getSharedPreferences(HIGH_SCORES, MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		
		switch(levelselected){
		case 1:
			if(score >= (prefs.getInt("HighScore_lvl1", score))){
				editor.putInt("HighScore_lvl1", score);
			}
			
			editor.commit();
			
			highscore = prefs.getInt("HighScore_lvl1", score);
			break;
		case 2:
			if(score >= (prefs.getInt("HighScore_lvl2", score))){
				editor.putInt("HighScore_lvl2", score);
			}
			
			editor.commit();
			
			highscore = prefs.getInt("HighScore_lvl2", score);
			break;
		case 3:
			if(score >= (prefs.getInt("HighScore_lvl3", score))){
				editor.putInt("HighScore_lvl3", score);
			}
			
			editor.commit();
			
			highscore = prefs.getInt("HighScore_lvl3", score);
			break;
		case 4:
			if(score >= (prefs.getInt("HighScore_lvl4", score))){
				editor.putInt("HighScore_lvl4", score);
			}
			
			editor.commit();
			
			highscore = prefs.getInt("HighScore_lvl4", score);
			break;
		case 5:
			if(score >= (prefs.getInt("HighScore_lvl5", score))){
				editor.putInt("HighScore_lvl5", score);
			}
			
			editor.commit();
			
			highscore = prefs.getInt("HighScore_lvl5", score);
			break;
		case 6:
			if(score >= (prefs.getInt("HighScore_lvl6", score))){
				editor.putInt("HighScore_lvl6", score);
			}
			
			editor.commit();
			
			highscore = prefs.getInt("HighScore_lvl6", score);
			break;
			
		}
		

		TextView Score = (TextView) findViewById(R.id.score);
		Score.setText("Score: " + score);
		Score.setTextColor(Color.BLACK);
		TextView HScore = (TextView) findViewById(R.id.highscore);
		HScore.setText("HighScore: " + highscore);
		HScore.setTextColor(Color.BLACK);
		Constants.clearLevel();
		Constants.setState(this);
		
		
		ImageButton menuButton = ((ImageButton)findViewById(R.id.op_return));
		Constants.setContext(getApplicationContext());		
		menuButton.setOnClickListener(goToMenu);
		
		scorescreenView=(ScoreScreenView)findViewById(R.id.scorescreenView);
		
	}
	
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), Menu.class));
    		finish();
        }
	};
	public void onBackPressed(){
		startActivity(new Intent(getApplicationContext(), Menu.class));
		finish();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		scorescreenView.CleanUp();
		scorescreenView = null;
		/*
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    SharedPreferences.Editor editor = settings.edit();
	    */
	}

}
