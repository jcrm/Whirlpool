package states;

import java.util.Timer;
import java.util.TimerTask;

import logic.Constants;
import logic.Level;
import logic.Panel;
import example.whirlpool.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Game extends MainActivity {
	Panel _panel;
	private Level level1;
	private Timer t;
	private Handler gameHandler;
	private Level _currentLevel;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		_panel = (Panel) findViewById(R.id.gameview);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Constants.setState(this);
		Constants.setContext(getApplicationContext());	//TODO remember to do this in all the other states
		
		_panel.init();
		
		Constants.setPanel(_panel);
		
		level1 = new Level();
		setCurrentLevel(level1);
		
		 t= new Timer();//init timer
		 
		// creates a handler to deal wit the return from the timer
	    gameHandler = new Handler() {

	            public void handleMessage(Message msg) {
	            	
	            	if (msg.what == 0)//redraw
	            		_panel.invalidate(); 
	            }
	     	};
	        
	    
		 t.schedule(new MainThread(),0, 25);
	        
	        
	            
		((Button) findViewById(R.id.test1)).setOnClickListener(
			new Button.OnClickListener(){
				public void onClick(View v) {
					//MainThread tempthread = Constants.getThread();
					synchronized(Constants.getLock()){
						_panel.setVisibility(8);//8 = GONE - ensures no redraw -> nullpointer
						t.cancel();
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
		return _currentLevel;
	}
	
	public void setCurrentLevel(Level level) {
		_currentLevel = level;
		Constants.setLevel(level);
	}
	
	public void update() {
		getCurrentLevel().update();
		
	}

	class MainThread extends TimerTask {

	    public void run() {
	        //if(!_paused){
	    		update();
	        	gameHandler.sendEmptyMessage(0);
	        //}
	    }
	   
	}
}
