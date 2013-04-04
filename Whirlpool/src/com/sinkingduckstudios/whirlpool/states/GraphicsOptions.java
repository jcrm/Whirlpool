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
import android.widget.ImageButton;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.views.GraphicsOptionsView;

public class GraphicsOptions extends Activity{
	boolean mPaused = false;
	GraphicsOptionsView graphicsOptionsView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_graphicsoptions);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		ImageButton backButton = ((ImageButton)findViewById(R.id.op_return));
		
		Constants.setContext(getApplicationContext());
		
		Display display = getWindowManager().getDefaultDisplay();
		@SuppressWarnings("deprecation")
		Screen theScreen = new Screen(display.getWidth(), display.getHeight());
		Constants.setScreen(theScreen);
		
		backButton.setOnClickListener(goToOp);
		
		graphicsOptionsView=(GraphicsOptionsView)findViewById(R.id.graphicsOptionsView);
	}
	
	private OnClickListener goToOp = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), Options.class));
    		finish();
        }
	};
	public void onBackPressed(){
		startActivity(new Intent(getApplicationContext(), Options.class));
		finish();
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		graphicsOptionsView.CleanUp();
		graphicsOptionsView = null;
	}

	
}
