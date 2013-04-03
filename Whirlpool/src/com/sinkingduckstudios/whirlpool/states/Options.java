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
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.OptionsView;

public class Options extends Activity {
	boolean mPaused = false;
	OptionsView optionsView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_options);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		ImageButton audioButton = ((ImageButton)findViewById(R.id.audio));
		ImageButton graphicsButton = ((ImageButton)findViewById(R.id.graphics));
		ImageButton returnButton = ((ImageButton)findViewById(R.id.op_return));
		
		Constants.setContext(getApplicationContext());
		
		Display display = getWindowManager().getDefaultDisplay();
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);
		
		audioButton.setOnClickListener(goToAudio);
		graphicsButton.setOnClickListener(goToGraphics);
		returnButton.setOnClickListener(goToMenu);
		
		optionsView=(OptionsView)findViewById(R.id.optionsView);
		
	}
	
	private OnClickListener goToAudio = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), AudioOptions.class));
    		finish();
        }
	};
	
	private OnClickListener goToGraphics = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), GraphicsOptions.class));
    		finish();
        }
	};
	
	private OnClickListener goToMenu = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), Menu.class));
    		finish();
        }
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		optionsView.CleanUp();
		optionsView = null;
	}

}
