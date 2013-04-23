/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.states;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Level;
import com.sinkingduckstudios.whirlpool.views.GameView;

public class Game extends Activity {
	private GameView mPanel;
	private Level mLevel;
	private int levelselected;
	private Timer mTime;
	private Handler mGameHandler;
	private Level mCurrentLevel;
	private CountDownTimer mCountDownTimer;
	private boolean mTimerHasStarted = false;
	public TextView mTimertext;
	//start time in milliseconds
	//Will add a variable to change the time depending on the level
	private final long mStartTime = 180 * 1000;
	//Tick time in milliseconds
	private final long mInterval = 1 * 1000;	
	private boolean mPaused = false;
	public int timepassed;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent game = getIntent();
		levelselected = game.getIntExtra("levelselected", 0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_game);
		mPanel = (GameView) findViewById(R.id.gameview);
		Constants.setContext(getApplicationContext());
		Constants.setState(this);
		//TODO remember to do this in all the other states
		mLevel = new Level();
		setCurrentLevel(mLevel);
		mTimertext = (TextView) this.findViewById(R.id.time);

		mCountDownTimer = new MyCountDownTimer(mStartTime, mInterval);
		mTimertext.setText(mTimertext.getText() + String.valueOf(mStartTime/100));
		Constants.createSoundManager(getApplicationContext());
		Constants.getSoundManager().loadSplash();

		mPanel.init();		
		Constants.setPanel(mPanel);
		Constants.getLevel().init(levelselected,true);
		////
		//create a runable thread to pass message to handler
		if(mTime!=null){
			mTime.cancel();
			mTime = null;
		}
		mTime= new Timer();//init timer
		// creates a handler to deal wit the return from the timer
		mGameHandler = new Handler(){
			public void handleMessage(Message aMsg){
				if (aMsg.what == 0){//redraw
					mPanel.invalidate();
				}else if(aMsg.what ==1){
					mTime.cancel();
					mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
					Constants.getSoundManager().cleanup();
					Intent scorescreen = (new Intent(getApplicationContext(), ScoreScreen.class));
					scorescreen.putExtra("timepassed", timepassed);
					scorescreen.putExtra("levelselected", levelselected);
					scorescreen.putExtra("duckcount", mLevel.getDuckCount());
					startActivity(scorescreen);
					mLevel.cleanUp();
					finish();
				}
			}
		};
		mTime.schedule(new MainThread(),0, 25);

		timepassed=0;
		ImageButton menuButton = ((ImageButton) findViewById(R.id.menubutton));
		ImageButton pauseButton = ((ImageButton)findViewById(R.id.pausebutton));
		ImageButton unpauseButton = ((ImageButton)findViewById(R.id.unpausebutton));
		menuButton.setOnClickListener(goToMenu);
		pauseButton.setOnClickListener(pause);
		unpauseButton.setOnClickListener(unpause);

	}
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			synchronized(Constants.getLock()){
				Constants.getSoundManager().playSplash();
				mTime.cancel();
				Constants.getSoundManager().unloadAll();
				Constants.getSoundManager().cleanup();
				mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
				startActivity(new Intent(getApplicationContext(), LevelSelect.class));
				mLevel.cleanUp();
				finish();
			}
		}
	};
	private OnClickListener pause = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			mPaused = true;
			mCountDownTimer.cancel();
			View unpauseButton = findViewById(R.id.unpausebutton);
			unpauseButton.setVisibility(View.VISIBLE);
			View pauseButton = findViewById(R.id.pausebutton);
			pauseButton.setVisibility(View.INVISIBLE);
		}
	};
	
	private OnClickListener unpause = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			mPaused = false;
			mCountDownTimer.start();
			View pauseButton = findViewById(R.id.pausebutton);
			pauseButton.setVisibility(View.VISIBLE);
			View unpauseButton = findViewById(R.id.unpausebutton);
			unpauseButton.setVisibility(View.INVISIBLE);
		}
	};
	

	public void onBackPressed(){
		
	}
	public Level getCurrentLevel() {
		return mCurrentLevel;
	}

	public void setCurrentLevel(Level level) {
		mCurrentLevel = level;
		Constants.setLevel(level);
	}
	
	public int update() {
		int count = getCurrentLevel().update(); 
		if(count==1){
			return 1;
		}else if(count==2){
			return 2;
		}else{
			return 0;
		}
	}

	class MainThread extends TimerTask {
		public void run() {
			if(!mPaused){
				//Timer
				if(!mTimerHasStarted){
					mCountDownTimer.start();
					mTimerHasStarted = true;				
				}
				int count =update();
				if(count==1){
					mCountDownTimer.cancel();
				}else if(count==2){
					mGameHandler.sendEmptyMessage(1);					
				}
				mGameHandler.sendEmptyMessage(0);
			}
		}

	}
	@Override
	public void onPause(){
		mPaused = true;
		mCountDownTimer.cancel();
		mTimerHasStarted = false;
		Constants.getSoundManager().unloadAll();
		Constants.getSoundManager().cleanup();
		super.onPause();
	}
	@Override
	public void onDestroy(){
		mPaused = true;
		mCountDownTimer.cancel();
		Constants.getSoundManager().unloadAll();
		Constants.getSoundManager().cleanup();
		Runtime.getRuntime().gc();
        System.gc();
		super.onDestroy();
	}
	@Override
	public void onResume(){
		mPaused = false;
		Constants.createSoundManager(getApplicationContext());
		Constants.getSoundManager().loadDucky();
		Constants.getSoundManager().loadDiver();
		Constants.getSoundManager().loadFrog();
		Constants.getSoundManager().loadTugBoat();
		Constants.getSoundManager().loadShark();
		Constants.getSoundManager().loadOtherSounds();
		Constants.getSoundManager().loadSplash();
		Constants.getSoundManager().playBackGround();

		if(!mTimerHasStarted){
			mCountDownTimer.start();
			mTimerHasStarted = true;				
		}
		super.onResume();
	}
	//
	//Timer Class
	//
	public class MyCountDownTimer extends CountDownTimer {
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		@Override
		public void onFinish(){
			if(mPaused == false){
				mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
				mTime.cancel();
				Constants.getSoundManager().cleanup();
				Intent scorescreen = (new Intent(getApplicationContext(), ScoreScreen.class));
				scorescreen.putExtra("timepassed", timepassed);
				scorescreen.putExtra("levelselected", levelselected);
				scorescreen.putExtra("duckcount", mLevel.getDuckCount());
				startActivity(scorescreen);
				finish();
				//finish game
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			//The code below sets up the Minute and seconds
			//The If statement allows the seconds to stay in double digits when going below 10 by adding a 0 in front of 9,8,7, etc
			String seconds;	
			timepassed++;
			if( (timepassed)%60<10){
				seconds=new String("0" +(timepassed)%60);				
			}else{
				seconds=new String("" +(timepassed)%60);
			}
			mTimertext.setGravity(Gravity.CENTER_HORIZONTAL);
			mTimertext.setTextColor(Color.BLACK);
			mTimertext.setText("" + (timepassed)/60 + ":" + seconds);
			Typeface face = Typeface.createFromAsset(getAssets(), "whirlpool.ttf");
			mTimertext.setTypeface(face);
		}
	}
}
