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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
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
	private CountDownTimer mcountDownTimer;
	private boolean mtimerHasStarted = false;
	public TextView mTimertext;
	//start time in milliseconds
	//Will add a variable to change the time depending on the level
	private final long startTime = 80 * 1000;
	//Tick time in milliseconds
	private final long interval = 1 * 1000;	
	private boolean mPaused = false;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		mPanel = (GameView) findViewById(R.id.gameview);
		Constants.setContext(getApplicationContext());
		Constants.setState(this);
		//TODO remember to do this in all the other states
		mLevelOne = new Level();
		setCurrentLevel(mLevelOne);
		mTimertext = (TextView) this.findViewById(R.id.time);
		
		mcountDownTimer = new MyCountDownTimer(startTime, interval);
		mTimertext.setText(mTimertext.getText() + String.valueOf(startTime/100));

		mPanel.init();		
		Constants.setPanel(mPanel);
		Constants.getLevel().init();
		mTime= new Timer();//init timer
		Constants.getSoundManager().loadSounds();
		Constants.getSoundManager().playBackground();
		// creates a handler to deal wit the return from the timer
		mGameHandler = new Handler() {

			public void handleMessage(Message aMsg) {

				if (aMsg.what == 0)//redraw
					mPanel.invalidate(); 
			}
		};


		mTime.schedule(new MainThread(),0, 25);


		ImageButton menuButton = ((ImageButton) findViewById(R.id.menubutton));
		menuButton.setOnClickListener(goToMenu);
	}
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
			synchronized(Constants.getLock()){
				mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
				startActivity(new Intent(getApplicationContext(), Menu.class));
				mTime.cancel();
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

	public void update() {
		getCurrentLevel().update();

	}

	class MainThread extends TimerTask {
		public void run() {
			if(!mPaused){
				//Timer
				if(!mtimerHasStarted)
				{
					mcountDownTimer.start();
					mtimerHasStarted = true;				
				}
				update();
				mGameHandler.sendEmptyMessage(0);
			}
		}

	}
	@Override
	public void onPause(){
		mPaused = true;
		Constants.getSoundManager().cleanup();
		super.onPause();
	}

	@Override
	public void onDestroy(){
		mPaused = true;
		Constants.getSoundManager().cleanup();
		super.onDestroy();
	}
	@Override
	public void onResume(){
		mPaused = false;
		super.onResume();
		Constants.getSoundManager().loadSounds();
		Constants.getSoundManager().playBackground();
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
			
			mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
			Constants.getSoundManager().cleanup();
			startActivity(new Intent(getApplicationContext(), ScoreScreen.class));
			mTime.cancel();
			finish();
			//finish game
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
			
			//The code below sets up the Minute and seconds
			//The If statement allows the seconds to stay in double digits when going below 10 by adding a 0 in front of 9,8,7, etc
			String seconds;	
			if( (millisUntilFinished/1000)%60<10)
			{
				seconds=new String("0" +(millisUntilFinished/1000)%60);				
			}
			else
			{
				seconds=new String("" +(millisUntilFinished/1000)%60);
			}

			mTimertext.setText("" + (millisUntilFinished/1000)/60 + ":" + seconds);
		}
	}
}
