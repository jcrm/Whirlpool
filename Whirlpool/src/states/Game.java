package states;

import java.util.Timer;
import java.util.TimerTask;

import example.whirlpool.R;

import logic.Constants;
import logic.Level;
import logic.Panel;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Game extends MainActivity {
	Panel mPanel;
	private Level mLevelOne;
	private Timer mTime;
	private Handler mGameHandler;
	private Level mCurrentLevel;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		mPanel = (Panel) findViewById(R.id.gameview);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Constants.setState(this);
		Constants.setContext(getApplicationContext());	//TODO remember to do this in all the other states

		mLevelOne = new Level();
		setCurrentLevel(mLevelOne);


		mPanel.init();		
		Constants.setPanel(mPanel);
		Constants.getLevel().init();
		mTime= new Timer();//init timer

		// creates a handler to deal wit the return from the timer
		mGameHandler = new Handler() {

			public void handleMessage(Message aMsg) {

				if (aMsg.what == 0)//redraw
					mPanel.invalidate(); 
			}
		};


		mTime.schedule(new MainThread(),0, 25);



		((Button) findViewById(R.id.test1)).setOnClickListener(
			new Button.OnClickListener(){
				public void onClick(View view) {
					//MainThread tempthread = Constants.getThread();
					synchronized(Constants.getLock()){
						mPanel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
						mTime.cancel();
						startActivity(new Intent(Game.this, Menu.class));

						finish();
					}
				}
			}
		);
	}

	@Override
	public void onPause(){
		Panel.stopMusic();
		super.onPause();
	}

	@Override
	public void onDestroy(){
		Panel.stopMusic();
		super.onDestroy();
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
			//if(!_paused){
			update();
			mGameHandler.sendEmptyMessage(0);
			//}
		}

	}
}
