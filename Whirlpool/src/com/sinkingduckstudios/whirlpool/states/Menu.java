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
import com.sinkingduckstudios.whirlpool.views.MenuView;

public class Menu extends Activity {
	boolean mPaused = false;
	MenuView menuView;
    //@SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Constants.clearLevel();
        Constants.setState(this);
        
        ImageButton gameButton = ((ImageButton) findViewById(R.id.game));
        Constants.setContext(getApplicationContext());
        
        Display display = getWindowManager().getDefaultDisplay(); 
    	Screen theScreen = new Screen(display.getWidth(), display.getHeight());
    	Constants.setScreen(theScreen);
    	//RelativeLayout theLayout = (RelativeLayout) findViewById(R.id.menuLayout)
        
        gameButton.setOnClickListener(goToGame);
        menuView=(MenuView)findViewById(R.id.menuView);
    }
	private OnClickListener goToGame = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), Game.class));
    		finish();
        }
	};
	@Override
	public void onDestroy(){
		super.onDestroy();
		menuView.CleanUp();
		menuView = null;
	}
//	Intent OptionsBackIntent = new Intent(OptionsMenu.this, Menu.class);
//	startActivity(OptionsBackIntent);


}   

