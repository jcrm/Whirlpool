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
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.AudioOptionsView;

public class AudioOptions extends Activity{
	boolean mPaused = false;
	AudioOptionsView audioOptionsView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_audiooptions);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		ImageButton backButton = ((ImageButton)findViewById(R.id.op_return));
		
		Constants.setContext(getApplicationContext());
		
		Display display = getWindowManager().getDefaultDisplay();
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);
		
		backButton.setOnClickListener(goToOp);
		
		audioOptionsView=(AudioOptionsView)findViewById(R.id.audioOptionsView);
	}
	
	private OnClickListener goToOp = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), Options.class));
    		finish();
        }
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		audioOptionsView.CleanUp();
		audioOptionsView = null;
	}

}
