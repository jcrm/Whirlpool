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
import android.view.Window;
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.ScoreScreenView;

public class ScoreScreen extends Activity {
	
	public static final String PREFS_NAME = "Bath_Score";
	boolean mPaused = false;
	ScoreScreenView scorescreenView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_scorescreen);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		//Score
		//Restore Preferences
		/*
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		*/
		
		
		ImageButton menuButton = ((ImageButton)findViewById(R.id.op_return));
		
		Constants.setContext(getApplicationContext());
		
		Display display = getWindowManager().getDefaultDisplay();
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);
		
		
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
