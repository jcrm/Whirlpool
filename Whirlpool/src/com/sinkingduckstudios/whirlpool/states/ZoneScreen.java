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
import com.sinkingduckstudios.whirlpool.views.ZoneScreenView;

public class ZoneScreen extends Activity{
	boolean mPaused = false;
	ZoneScreenView zoneScreenView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zonescreen);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		ImageButton bathZoneButton = ((ImageButton)findViewById(R.id.bathzone));
		
		Constants.setContext(getApplicationContext());
		
		Display display = getWindowManager().getDefaultDisplay();
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);
		
		bathZoneButton.setOnClickListener(goToBath);
		
		zoneScreenView=(ZoneScreenView)findViewById(R.id.zoneScreenView);
	}
	
	private OnClickListener goToBath = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), Game.class));
    		finish();
        }
	};
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		zoneScreenView.CleanUp();
		zoneScreenView = null;
	}
	
}
