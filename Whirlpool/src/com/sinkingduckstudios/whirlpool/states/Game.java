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

// TODO: Auto-generated Javadoc
/**
 * The Class Game.
 */
public class Game extends Activity {
	
	/** The m panel. */
	private GameView mPanel;
	
	/** The m level. */
	private Level mLevel;
	
	/** The levelselected. */
	private int levelselected;
	
	/** The m time. */
	private Timer mTime;
	
	/** The m game handler. */
	private Handler mGameHandler;
	
	/** The m current level. */
	private Level mCurrentLevel;
	
	/** The m count down timer. */
	private CountDownTimer mCountDownTimer;
	
	/** The m timer has started. */
	private boolean mTimerHasStarted = false;
	
	/** The muted. */
	private boolean muted = true;
	
	/** The m timertext. */
	public TextView mTimertext;
	//start time in milliseconds
	//Will add a variable to change the time depending on the level
	/** The m start time. */
	private final long mStartTime = 180 * 1000;
	//Tick time in milliseconds
	/** The m interval. */
	private final long mInterval = 1 * 1000;	
	
	/** The m paused. */
	private boolean mPaused = false;
	
	/** The timepassed. */
	public int timepassed;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
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
		Constants.getLevel().init(levelselected);
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
		ImageButton quitButton = ((ImageButton)findViewById(R.id.quit));
		ImageButton restartButton = ((ImageButton)findViewById(R.id.restart));
		ImageButton volumeOffButton = ((ImageButton)findViewById(R.id.volume_off));
		ImageButton volumeOnButton = ((ImageButton)findViewById(R.id.volume_on));
		menuButton.setOnClickListener(goToMenu);
		pauseButton.setOnClickListener(pause);
		unpauseButton.setOnClickListener(unpause);
		quitButton.setOnClickListener(quit);
		restartButton.setOnClickListener(restart);
		volumeOffButton.setOnClickListener(volume_off);
		volumeOnButton.setOnClickListener(volume_on);


	}
	
	/** The quit. */
	private OnClickListener quit = new OnClickListener() {
		@Override
		public void onClick(View view) {
			synchronized(Constants.getLock()){
				mTime.cancel();
				Constants.getSoundManager().unloadAll();
				Constants.getSoundManager().cleanup();
				mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
				startActivity(new Intent(getApplicationContext(), Menu.class));
				mLevel.cleanUp();
				finish();
			}
		}
	};
	
	/** The restart. */
	private OnClickListener restart = new OnClickListener() {
		@Override
		public void onClick(View view) {
			synchronized(Constants.getLock()){
				mTime.cancel();
				Constants.getSoundManager().unloadAll();
				Constants.getSoundManager().cleanup();
				mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
				Intent restart = (new Intent(getApplicationContext(), Loading.class));
				restart.putExtra("levelselected",levelselected);
				startActivity(restart);
				mLevel.cleanUp();
				finish();
			}
		}
	};
	
	/** The volume_off. */
	private OnClickListener volume_off = new OnClickListener() {
		@Override
		public void onClick(View view) {
			
			View volumeOnButton = findViewById(R.id.volume_on);
			volumeOnButton.setVisibility(View.VISIBLE);
			View volumeOffButton = findViewById(R.id.volume_off);
			volumeOffButton.setVisibility(View.INVISIBLE);
			//Volume code here
			muted = true;
			
			
		}
	};
	
	/** The volume_on. */
	private OnClickListener volume_on = new OnClickListener() {
		@Override
		public void onClick(View view) {
			
			View volumeOffButton = findViewById(R.id.volume_off);
			volumeOffButton.setVisibility(View.VISIBLE);
			View volumeOnButton = findViewById(R.id.volume_on);
			volumeOnButton.setVisibility(View.INVISIBLE);
			//Volume code here
			muted = false;
			
			
		}
	};
	
	/*
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.volume_off:
			break;
		case R.id.volume_on:
			break;
		}
	}*/

	/** The go to menu. */
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
	
	/** The pause. */
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
			View pausebg = findViewById(R.id.pausebg);
			pausebg.setVisibility(View.VISIBLE);
			View restart = findViewById(R.id.restart);
			restart.setVisibility(View.VISIBLE);
			View quit = findViewById(R.id.quit);
			quit.setVisibility(View.VISIBLE);
			
			if(muted == true){
				View volumeOnButton = findViewById(R.id.volume_on);
				volumeOnButton.setVisibility(View.VISIBLE);
				View volumeOffButton = findViewById(R.id.volume_off);
				volumeOffButton.setVisibility(View.INVISIBLE);
			} else  {
				View volumeOnButton = findViewById(R.id.volume_on);
				volumeOnButton.setVisibility(View.INVISIBLE);
				View volumeOffButton = findViewById(R.id.volume_off);
				volumeOffButton.setVisibility(View.VISIBLE);
			}
			Constants.getSoundManager().pause();

		}
	};
	
	/** The unpause. */
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
			View pausebg = findViewById(R.id.pausebg);
			pausebg.setVisibility(View.INVISIBLE);
			View restart = findViewById(R.id.restart);
			restart.setVisibility(View.INVISIBLE);
			View quit = findViewById(R.id.quit);
			quit.setVisibility(View.INVISIBLE);
			View volumeOnButton = findViewById(R.id.volume_on);
			volumeOnButton.setVisibility(View.INVISIBLE);
			View volumeOffButton = findViewById(R.id.volume_off);
			volumeOffButton.setVisibility(View.INVISIBLE);
			Constants.getSoundManager().unpause();

		}
	};
	

	/* (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	public void onBackPressed(){
		
	}
	
	/**
	 * Gets the current level.
	 *
	 * @return the current level
	 */
	public Level getCurrentLevel() {
		return mCurrentLevel;
	}

	/**
	 * Sets the current level.
	 *
	 * @param level the new current level
	 */
	public void setCurrentLevel(Level level) {
		mCurrentLevel = level;
		Constants.setLevel(level);
	}
	
	/**
	 * Update.
	 *
	 * @return the int
	 */
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

	/**
	 * The Class MainThread.
	 */
	class MainThread extends TimerTask {
		
		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	public void onPause(){
		mPaused = true;
		mCountDownTimer.cancel();
		mTimerHasStarted = false;
		Constants.getSoundManager().unloadAll();
		Constants.getSoundManager().cleanup();
		super.onPause();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
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
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
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
	/**
	 * The Class MyCountDownTimer.
	 */
	public class MyCountDownTimer extends CountDownTimer {
		
		/**
		 * Instantiates a new my count down timer.
		 *
		 * @param startTime the start time
		 * @param interval the interval
		 */
		public MyCountDownTimer(long startTime, long interval) {
			super(startTime, interval);
		}

		/* (non-Javadoc)
		 * @see android.os.CountDownTimer#onFinish()
		 */
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

		/* (non-Javadoc)
		 * @see android.os.CountDownTimer#onTick(long)
		 */
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
