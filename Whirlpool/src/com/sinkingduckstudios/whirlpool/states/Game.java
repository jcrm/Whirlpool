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
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Level;
import com.sinkingduckstudios.whirlpool.views.GameView;

public class Game extends Activity {
	private GameView mPanel;
	private Level mLevelOne;
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
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPanel = (GameView) findViewById(R.id.gameview);
		Constants.setContext(getApplicationContext());
		Constants.setState(this);
		//TODO remember to do this in all the other states
		mLevelOne = new Level();
		setCurrentLevel(mLevelOne);
		mTimertext = (TextView) this.findViewById(R.id.time);
		
		mCountDownTimer = new MyCountDownTimer(mStartTime, mInterval);
		mTimertext.setText(mTimertext.getText() + String.valueOf(mStartTime/100));

		Constants.getSoundManager().loadSounds();
		Constants.getSoundManager().playBackground();
		mPanel.init();		
		Constants.setPanel(mPanel);
		Constants.getLevel().init();
		//create a runable thread to pass message to handler
		if(mTime!=null){
			mTime.cancel();
			mTime = null;
		}
		mTime= new Timer();//init timer
		// creates a handler to deal wit the return from the timer
		mGameHandler = new Handler() {

			public void handleMessage(Message aMsg) {

				if (aMsg.what == 0){//redraw
					mPanel.invalidate();
				}else if(aMsg.what ==1){
					mTime.cancel();
					mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
					Constants.getSoundManager().cleanup();
					startActivity(new Intent(getApplicationContext(), ScoreScreen.class));
					finish();
				}
			}
		};


		mTime.schedule(new MainThread(),0, 50);


		ImageButton menuButton = ((ImageButton) findViewById(R.id.menubutton));
		menuButton.setOnClickListener(goToMenu);
	}
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			synchronized(Constants.getLock()){
				mTime.cancel();
				mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
				startActivity(new Intent(getApplicationContext(), Menu.class));
				finish();
			}
        }
	};
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
		mCountDownTimer.cancel();
		mPaused = true;
		Constants.getSoundManager().cleanup();
		super.onPause();
	}

	@Override
	public void onDestroy(){
		mCountDownTimer.cancel();
		mPaused = true;
		Constants.getSoundManager().cleanup();
		super.onDestroy();
	}
	@Override
	public void onResume(){
		mPaused = false;
		super.onResume();/*
		Constants.getSoundManager().initContext(getApplicationContext());
		Constants.getSoundManager().loadSounds();
		Constants.getSoundManager().playBackground();*/
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
				Constants.getSoundManager().cleanup();
				startActivity(new Intent(getApplicationContext(), ScoreScreen.class));
				mTime.cancel();
				finish();
				//finish game
			}
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
			
			//The code below sets up the Minute and seconds
			//The If statement allows the seconds to stay in double digits when going below 10 by adding a 0 in front of 9,8,7, etc
			String seconds;	
			if( (millisUntilFinished/1000)%60<10){
				seconds=new String("0" +(millisUntilFinished/1000)%60);				
			}else{
				seconds=new String("" +(millisUntilFinished/1000)%60);
			}

			mTimertext.setText("" + (millisUntilFinished/1000)/60 + ":" + seconds);
		}
	}
}
