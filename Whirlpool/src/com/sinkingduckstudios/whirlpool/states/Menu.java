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
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
import com.sinkingduckstudios.whirlpool.views.MenuView;

// TODO: Auto-generated Javadoc
/**
 * The Class Menu.
 */
public class Menu extends Activity {
	
	/** The m paused. */
	boolean mPaused = false;
	
	/** The menu view. */
	MenuView menuView;
    //@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_menu);
        
        
        Constants.clearLevel();
        Constants.setState(this);
        
        ImageButton gameButton = ((ImageButton) findViewById(R.id.game));
        ImageButton optionsButton = ((ImageButton) findViewById(R.id.options));
        ImageButton exitButton = ((ImageButton) findViewById(R.id.exit));
        Constants.setContext(getApplicationContext());
        

        Display display = getWindowManager().getDefaultDisplay(); 
    	@SuppressWarnings("deprecation")
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
    	Constants.setScreen(theScreen);
    	//RelativeLayout theLayout = (RelativeLayout) findViewById(R.id.menuLayout)
        
    	gameButton.setOnClickListener(goToGame);
        optionsButton.setOnClickListener(goToOptions);
        exitButton.setOnClickListener(goToExit);
        menuView=(MenuView)findViewById(R.id.menuView);
        SpriteManager.unloadBoat();
        SpriteManager.unloadDuck();
        SpriteManager.unloadDiver();
        SpriteManager.unloadFrog();
        SpriteManager.unloadShark();
        SpriteManager.unloadStar();
        SpriteManager.unloadWhirlpool();
        SpriteManager.unloadTorpedo();
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
	
	/** The go to game. */
	private OnClickListener goToGame = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

			//Constants.getScreen().set(menuView.getWidth(), menuView.getHeight());
    		startActivity(new Intent(getApplicationContext(), ZoneScreen.class));
    		finish();
        }
	};
	
	/** The go to options. */
	private OnClickListener goToOptions = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		startActivity(new Intent(getApplicationContext(), Options.class));
    		finish();
        }
	};
	
	/** The go to exit. */
	private OnClickListener goToExit = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();

    		finish();
        }
	};
	@Override
	public void onDestroy(){
		menuView.CleanUp();
		menuView = null;
		Runtime.getRuntime().gc();
        System.gc();
		super.onDestroy();
	}
//	Intent OptionsBackIntent = new Intent(OptionsMenu.this, Menu.class);
//	startActivity(OptionsBackIntent);


}   

