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

public class Menu extends Activity {
	boolean mPaused = false;
    //@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Constants.clearLevel();
        Constants.setState(this);
        
        ImageButton gameButton = ((ImageButton) findViewById(R.id.game));
        Constants.setContext(getApplicationContext());
        
        Screen theScreen = new Screen();
        Display display = getWindowManager().getDefaultDisplay(); 
    	//RelativeLayout theLayout = (RelativeLayout) findViewById(R.id.menuLayout)
    	theScreen.set(display.getWidth(), display.getHeight());
    	Constants.setScreen(theScreen);
        
        gameButton.setOnClickListener(goToGame);
        /*Constants.getSoundManager().initSound();
        Constants.getSoundManager().playBackgroundMusic();*/
    }
	private OnClickListener goToGame = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), Game.class));
    		finish();
        }
	};
//	Intent OptionsBackIntent = new Intent(OptionsMenu.this, Menu.class);
//	startActivity(OptionsBackIntent);


}   

