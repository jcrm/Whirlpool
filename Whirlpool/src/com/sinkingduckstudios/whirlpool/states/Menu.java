/*
 * Author: Lewis Shaw, Jake Morey
 * Last Updated:25/04/2013
 * Content:
 * Lewis Shaw - Created the menu screen
 * Jake Morey: added cleaning code
 */
package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

/**
 * The Class Menu.
 */
public class Menu extends Activity {
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_menu);
        
        Constants.clearLevel();
        Constants.setState(this);
        //set up image buttons
        ImageButton gameButton = ((ImageButton) findViewById(R.id.game));
        ImageButton optionsButton = ((ImageButton) findViewById(R.id.options));
        ImageButton exitButton = ((ImageButton) findViewById(R.id.exit));
        Constants.setContext(getApplicationContext());
        //set up display size
        Display display = getWindowManager().getDefaultDisplay(); 
    	@SuppressWarnings("deprecation")
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
    	Constants.setScreen(theScreen);
    	
    	int ScreenWidth = Constants.getScreen().getWidth();
		int ScreenHeight = Constants.getScreen().getHeight();
		int buttonScale = ScreenHeight/4;
		int step = (ScreenHeight/4) - (buttonScale/2);
		
		RelativeLayout.LayoutParams gameParams = new RelativeLayout.LayoutParams(buttonScale*2,buttonScale);
		gameParams.leftMargin = (ScreenWidth/2) - (buttonScale);
		gameParams.topMargin = step;
		
		step+= (ScreenHeight/4);
		
		RelativeLayout.LayoutParams optionsParams = new RelativeLayout.LayoutParams(buttonScale*2,buttonScale);
		optionsParams.leftMargin = (ScreenWidth/2) - (buttonScale);
		optionsParams.topMargin = step;
		
		step+= (ScreenHeight/4);
		
		RelativeLayout.LayoutParams exitParams = new RelativeLayout.LayoutParams(buttonScale*2,buttonScale);
		exitParams.leftMargin = (ScreenWidth/2) - (buttonScale);
		exitParams.topMargin = step;

        //set up button function
    	gameButton.setOnClickListener(goToGame);
    	gameButton.setLayoutParams(gameParams);
        optionsButton.setOnClickListener(goToOptions);
        optionsButton.setLayoutParams(optionsParams);
        exitButton.setOnClickListener(goToExit);
        exitButton.setLayoutParams(exitParams);
        //unload all images if previously been playing levels
        SpriteManager.unloadBoat();
        SpriteManager.unloadDuck();
        SpriteManager.unloadDiver();
        SpriteManager.unloadFrog();
        SpriteManager.unloadShark();
        SpriteManager.unloadStar();
        SpriteManager.unloadWhirlpool();
        SpriteManager.unloadTorpedo();
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
	/** The go to game button. */
	private OnClickListener goToGame = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

			//Constants.getScreen().set(menuView.getWidth(), menuView.getHeight());
    		startActivity(new Intent(getApplicationContext(), ZoneScreen.class));
    		finish();
        }
	};
	/** The go to options button. */
	private OnClickListener goToOptions = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		startActivity(new Intent(getApplicationContext(), Options.class));
    		finish();
        }
	};
	/** The go to exit button. */
	private OnClickListener goToExit = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		finish();
        }
	};
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy(){
		Runtime.getRuntime().gc();
        System.gc();
		super.onDestroy();
	}
}   

