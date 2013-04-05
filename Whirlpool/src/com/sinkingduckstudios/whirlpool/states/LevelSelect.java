package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
import com.sinkingduckstudios.whirlpool.views.LevelSelectView;

public class LevelSelect extends Activity{
	
	boolean mPaused = false;
	
	public int mLevelSelect;
	
	LevelSelectView levelSelectView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_levelselect);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		Constants.clearLevel();
		Constants.setState(this);
		
		ImageButton level1Button = ((ImageButton)findViewById(R.id.level1));
		ImageButton level2Button = ((ImageButton)findViewById(R.id.level2));
		ImageButton level3Button = ((ImageButton)findViewById(R.id.level3));
		ImageButton level4Button = ((ImageButton)findViewById(R.id.level4));
		ImageButton level5Button = ((ImageButton)findViewById(R.id.level5));
		ImageButton level6Button = ((ImageButton)findViewById(R.id.level6));
		ImageButton returnButton = ((ImageButton)findViewById(R.id.op_return));
		
		Constants.setContext(getApplicationContext());
		
		int theHeight = Constants.getScreen().getHeight();
		int theWidth = Constants.getScreen().getWidth();
		
		int scale = theHeight/3;
		if ((theWidth/4)< scale)
			scale = theWidth/4;
		
		int xstep=theWidth/4; int ystep=theHeight/3;
		
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
		        scale, scale);
		
		level1Button.setLayoutParams(buttonParams);
		level1Button.setX(xstep-(scale/2));
		level1Button.setY(ystep-(scale/2));
	
		xstep += theWidth/4;
		
		level2Button.setLayoutParams(buttonParams);
		level2Button.setX(xstep-(scale/2));
		level2Button.setY(ystep-(scale/2));
		
		xstep += theWidth/4;
		
		level3Button.setLayoutParams(buttonParams);
		level3Button.setX(xstep-(scale/2));
		level3Button.setY(ystep-(scale/2));	
		
		ystep+= theHeight/3;
		xstep = theWidth/4;
		
		level4Button.setLayoutParams(buttonParams);
		level4Button.setX(xstep-(scale/2));
		level4Button.setY(ystep-(scale/2));	
		
		xstep += theWidth/4;
		
		level5Button.setLayoutParams(buttonParams);
		level5Button.setX(xstep-(scale/2));
		level5Button.setY(ystep-(scale/2));	
		
		xstep += theWidth/4;
		
		level6Button.setLayoutParams(buttonParams);
		level6Button.setX(xstep-(scale/2));
		level6Button.setY(ystep-(scale/2));	
		
		level1Button.setOnClickListener(goToLevel1);
		level2Button.setOnClickListener(goToLevel2);
		level3Button.setOnClickListener(goToLevel3);
		level4Button.setOnClickListener(goToLevel4);
		level5Button.setOnClickListener(goToLevel5);
		level6Button.setOnClickListener(goToLevel6);
		returnButton.setOnClickListener(goToZone);
		levelSelectView=(LevelSelectView)findViewById(R.id.levelSelectView);
		
		SharedPreferences prefs = getSharedPreferences("HighScores", MODE_PRIVATE);
		int stars[] = new int[6];
		
		for(int i = 0; i < 6; i++){
			stars[i] =  prefs.getInt("Stars_lvl"+Integer.toString(i+1), 0);
		}
		
		levelSelectView.initStars(stars);
	}
	
	private OnClickListener goToLevel1 = new OnClickListener() {
		@Override
		public void onClick(View view){
			
			//mLevelSelect = 1;
			
			Intent loading = (new Intent(getApplicationContext(),Cinematic.class));
			
			loading.putExtra("levelselected", 1);
			
			startActivity(loading);
			
			finish();
			//Code to select level1
		}
	};
	private OnClickListener goToZone = new OnClickListener() {
		@Override
		public void onClick(View view) {
    		startActivity(new Intent(getApplicationContext(), ZoneScreen.class));
    		finish();
        }
	};
	public void onBackPressed(){
		synchronized(Constants.getLock()){
			startActivity(new Intent(getApplicationContext(), ZoneScreen.class));
			finish();
		}
	}
	private OnClickListener goToLevel2 = new OnClickListener() {
		@Override
		public void onClick(View view){
			
			//mLevelSelect = 2;
			
			Intent loading = (new Intent(getApplicationContext(),Loading.class));
			
			loading.putExtra("levelselected", 2);
			
			startActivity(loading);
			
			finish();
			//Code to select level2
		}
	};
	private OnClickListener goToLevel3 = new OnClickListener() {
		@Override
		public void onClick(View view){
			
			//mLevelSelect = 2;
			
			Intent loading = (new Intent(getApplicationContext(),Loading.class));
			
			loading.putExtra("levelselected", 3);
			
			startActivity(loading);
			
			finish();
			//Code to select level2
		}
	};
	private OnClickListener goToLevel4 = new OnClickListener() {
		@Override
		public void onClick(View view){
			
			//mLevelSelect = 2;
			
			Intent loading = (new Intent(getApplicationContext(),Loading.class));
			
			loading.putExtra("levelselected", 4);
			
			startActivity(loading);
			
			finish();
			//Code to select level2
		}
	};
	private OnClickListener goToLevel5 = new OnClickListener() {
		@Override
		public void onClick(View view){
			
			//mLevelSelect = 2;
			
			Intent loading = (new Intent(getApplicationContext(),Loading.class));
			
			loading.putExtra("levelselected", 5);
			
			startActivity(loading);
			
			finish();
			//Code to select level2
		}
	};
	private OnClickListener goToLevel6 = new OnClickListener() {
		@Override
		public void onClick(View view){
			
			//mLevelSelect = 2;
			
			Intent loading = (new Intent(getApplicationContext(),Loading.class));
			
			loading.putExtra("levelselected", 6);
			
			startActivity(loading);
			
			finish();
			//Code to select level2
		}
	};	
	@Override
	public void onDestroy(){
		super.onDestroy();
		levelSelectView.CleanUp();
		levelSelectView = null;
	}

}
