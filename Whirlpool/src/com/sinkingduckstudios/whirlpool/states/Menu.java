package com.sinkingduckstudios.whirlpool.states;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends MainActivity {
    //@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Constants.clearLevel();
        Constants.setState(this);
        Constants.setContext(getApplicationContext());
        ImageButton game = ((ImageButton) findViewById(R.id.game));
        Constants.getSoundManager().initSound();
        Constants.getSoundManager().playBackgroundMusic();
        System.gc();
        game.setOnClickListener(
    		new Button.OnClickListener(){
		        public void onClick(View view) {
		        	Constants.getSoundManager().pauseBackground();
	        		startActivity(new Intent(Menu.this, Game.class));
		        }
    		}
    	);
    }
//	Intent OptionsBackIntent = new Intent(OptionsMenu.this, Menu.class);
//	startActivity(OptionsBackIntent);

	@Override
	public void update() {
		//nothing needed in Menu State
	}

    
}
