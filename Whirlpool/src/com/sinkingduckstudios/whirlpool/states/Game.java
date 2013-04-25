/*
 * Author: Lewis Shaw , Fraser, Jake Morey, Jordan O'Hare 
 * Last Updated: 25/04/2013
 * Content:
 * Lewis Shaw - added a timer on screen, worked on the pause screen with multiple buttons to perform various actions
 * Fraser: added timer thread and handler
 * Jake Morey: added resume, destroy, and on pause functions and muted functionality, and some minor bug fixes
 * Jordan O'Hare created very basic and limited version of this class
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

/**
 * The Class Game.
 */
public class Game extends Activity {
	/** The game view. */
	private GameView mPanel;
	/** The level holder. */
	private Level mLevel;
	/** The level selected. */
	private int levelselected;
	/** The timer. */
	private Timer mTime;
	/** The game handler. */
	private Handler mGameHandler;
	/** The current level. */
	private Level mCurrentLevel;
	/** The count down timer. */
	private CountDownTimer mCountDownTimer;
	/** The timer has started. */
	private boolean mTimerHasStarted = false;
	/** The muted. */
	private boolean muted = true;
	/** The timer text. */
	public TextView mTimertext;
	//start time in milliseconds
	//Will add a variable to change the time depending on the level
	/** The start time. */
	private final long mStartTime = 180 * 1000;
	//Tick time in milliseconds
	/** The interval time. */
	private final long mInterval = 1 * 1000;	
	/** The paused variable. */
	private boolean mPaused = false;
	/** The time passed. */
	public int timepassed;
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//used to determine what level has been selected
		Intent game = getIntent();
		levelselected = game.getIntExtra("levelselected", 0);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_game);
		//set the view to be that of the game view
		mPanel = (GameView) findViewById(R.id.gameview);
		Constants.setContext(getApplicationContext());
		Constants.setState(this);
		//set a new level
		mLevel = new Level();
		setCurrentLevel(mLevel);
		//set the timer text
		mTimertext = (TextView) this.findViewById(R.id.time);
		//initialise the count down timer
		mCountDownTimer = new MyCountDownTimer(mStartTime, mInterval);
		mTimertext.setText(mTimertext.getText() + String.valueOf(mStartTime/100));
		//init the sound manager
		Constants.createSoundManager(getApplicationContext());
		Constants.getSoundManager().loadSplash();
		//init the view
		mPanel.init();		
		Constants.setPanel(mPanel);
		//init the level selected
		Constants.getLevel().init(levelselected);
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
					//quit the applicatiob
					mTime.cancel();
					mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
					Constants.getSoundManager().cleanup();
					//store values for time passed, level selected,
					//level average, and level good for reading across activities
					Intent scorescreen = (new Intent(getApplicationContext(), ScoreScreen.class));
					scorescreen.putExtra("timepassed", timepassed);
					scorescreen.putExtra("levelselected", levelselected);
					scorescreen.putExtra("duckcount", mLevel.getDuckCount());
					scorescreen.putExtra("levelAverage", mLevel.getAverageTime());
					scorescreen.putExtra("levelGood", mLevel.getGoodTime());
					startActivity(scorescreen);
					mLevel.cleanUp();
					finish();
				}
			}
		};
		mTime.schedule(new MainThread(),0, 25);

		timepassed=0;
		//initialise all the buttons for the ui and pause menu
		ImageButton pauseButton = ((ImageButton)findViewById(R.id.pausebutton));
		ImageButton unpauseButton = ((ImageButton)findViewById(R.id.unpausebutton));
		ImageButton quitButton = ((ImageButton)findViewById(R.id.quit));
		ImageButton restartButton = ((ImageButton)findViewById(R.id.restart));
		ImageButton volumeOffButton = ((ImageButton)findViewById(R.id.volume_off));
		ImageButton volumeOnButton = ((ImageButton)findViewById(R.id.volume_on));
		pauseButton.setOnClickListener(pause);
		unpauseButton.setOnClickListener(unpause);
		quitButton.setOnClickListener(quit);
		restartButton.setOnClickListener(restart);
		volumeOffButton.setOnClickListener(volume_off);
		volumeOnButton.setOnClickListener(volume_on);
		//depending on the volume variable set muted to be true or false
		if(Constants.sBackgroundVolume == 0 && Constants.sEffectVolume == 0){
			muted = true;
		}else {
			muted = false;
		}
		Constants.updateVolume();
	}
	
	/** The quit button. */
	private OnClickListener quit = new OnClickListener() {
		@Override
		public void onClick(View view) {
			synchronized(Constants.getLock()){
				//quit the activity
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
	
	/** The restart button. */
	private OnClickListener restart = new OnClickListener() {
		@Override
		public void onClick(View view) {
			synchronized(Constants.getLock()){
				//reload the level
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
	
	/** The volume_off button. */
	private OnClickListener volume_off = new OnClickListener() {
		@Override
		public void onClick(View view) {
			//switch images and change background and effect values
			View volumeOnButton = findViewById(R.id.volume_on);
			volumeOnButton.setVisibility(View.VISIBLE);
			View volumeOffButton = findViewById(R.id.volume_off);
			volumeOffButton.setVisibility(View.INVISIBLE);
			//Volume code here
			muted = false;
			Constants.sBackgroundVolume = 1;
			Constants.sEffectVolume = 1;
			Constants.updateVolume();
		}
	};
	
	/** The volume_on button. */
	private OnClickListener volume_on = new OnClickListener() {
		@Override
		public void onClick(View view) {
			//switch images and change background and effect values
			View volumeOffButton = findViewById(R.id.volume_off);
			volumeOffButton.setVisibility(View.VISIBLE);
			View volumeOnButton = findViewById(R.id.volume_on);
			volumeOnButton.setVisibility(View.INVISIBLE);
			//Volume code here
			muted = true;
			Constants.sBackgroundVolume = 0;
			Constants.sEffectVolume = 0;
			Constants.updateVolume();
		}
	};
	/** The pause button. */
	private OnClickListener pause = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			mPaused = true;
			mCountDownTimer.cancel();
			//show buttons for the pause menu
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
			//if not muted display volume on button else show vulme off button
			if(muted == false){
				View volumeOnButton = findViewById(R.id.volume_on);
				volumeOnButton.setVisibility(View.VISIBLE);
				View volumeOffButton = findViewById(R.id.volume_off);
				volumeOffButton.setVisibility(View.INVISIBLE);
			}else{
				View volumeOnButton = findViewById(R.id.volume_on);
				volumeOnButton.setVisibility(View.INVISIBLE);
				View volumeOffButton = findViewById(R.id.volume_off);
				volumeOffButton.setVisibility(View.VISIBLE);
			}
			Constants.getSoundManager().pause();
		}
	};
	
	/** The unpause button. */
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
		//to stop accidently going back to a previous activity
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
	 * @return the int for what stage the level is on.
	 */
	public int update() {
		//depending on value being returned stop counter or stop activity
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
		//pause the activity
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
		//clean up data
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
		if(Constants.sBackgroundVolume == 0 && Constants.sEffectVolume == 0){
			muted = true;
		}else {
			muted = false;
		}
		Constants.updateVolume();
		Constants.getSoundManager().playBackGround();

		if(!mTimerHasStarted){
			mCountDownTimer.start();
			mTimerHasStarted = true;				
		}
		super.onResume();
	}
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
