package com.sinkingduckstudios.whirlpool.states;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Level;
import com.sinkingduckstudios.whirlpool.logic.Panel;

public class Game extends MainActivity {
	private Panel mPanel;
	private Level mLevelOne;
	private Timer mTime;
	private Handler mGameHandler;
	private Level mCurrentLevel;
	private boolean mPaused = false;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		mPanel = (Panel) findViewById(R.id.gameview);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Constants.setState(this);
		//TODO remember to do this in all the other states
		mLevelOne = new Level();
		setCurrentLevel(mLevelOne);
		

		mPanel.init();		
		Constants.setPanel(mPanel);
		Display display = getWindowManager().getDefaultDisplay(); 

		Constants.getLevel().init(display.getHeight());
		mTime= new Timer();//init timer
		Constants.getSoundManager().playBackgroundMusic();
		// creates a handler to deal wit the return from the timer
		mGameHandler = new Handler() {

			public void handleMessage(Message aMsg) {

				if (aMsg.what == 0)//redraw
					mPanel.invalidate(); 
			}
		};


		mTime.schedule(new MainThread(),0, 25);



		((Button) findViewById(R.id.menubutton)).setOnClickListener(
			new Button.OnClickListener(){
				public void onClick(View view) {
					//MainThread tempthread = Constants.getThread();
					synchronized(Constants.getLock()){
						mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
						mTime.cancel();
						startActivity(new Intent(Game.this, Menu.class));
						Constants.getSoundManager().pauseBackground();
						finish();
					}
				}
			}
		);
	}

	@Override
	public void onPause(){
		mPaused = true;
		Constants.getSoundManager().stopAllSounds();
		super.onPause();
	}

	@Override
	public void onDestroy(){
		mPaused = true;
		Constants.getSoundManager().stopAllSounds();
		super.onDestroy();
	}
	@Override
	public void onResume(){
		mPaused = false;
		super.onResume();
		Constants.getSoundManager().initSound();
		Constants.getSoundManager().playBackgroundMusic();
	}
	public Level getCurrentLevel() {
		return mCurrentLevel;
	}

	public void setCurrentLevel(Level level) {
		mCurrentLevel = level;
		Constants.setLevel(level);
	}

	public void update() {
		getCurrentLevel().update();

	}

	class MainThread extends TimerTask {
		public void run() {
			if(!mPaused){
				update();
				mGameHandler.sendEmptyMessage(0);
			}
		}

	}
}
