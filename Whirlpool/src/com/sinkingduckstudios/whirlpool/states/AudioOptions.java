/*
 * Author:Lewis Shaw, Jake Morey
 * Content:
 * Lewis Shaw - Created the AudioOptions screen with working buttons
 * Jake Morey: added function for checking what buttons to show
 */

package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;

/**
 * The Class AudioOptions.
 */
public class AudioOptions extends Activity{
	/** The audio options view. */
	/** The Constant AUDIO string. */
	public static final String AUDIO = "audio_options";
	
	/** The audio1 button. */
	ImageButton audio1Button;
	
	/** The audio2 button. */
	ImageButton audio2Button;
	
	/** The audio3 button. */
	ImageButton audio3Button;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	
	private final int screenWidth = 100; //lets split the screen up into 100 segments for relative button positioning
	private final int button1 = 18;
	private final int button2 = 49;
	private final int button3 = 80; //relative button positions to a screen width of 100
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		setContentView(R.layout.activity_audiooptions);

		Constants.clearLevel();
		Constants.setState(this);

		ImageButton backButton = ((ImageButton)findViewById(R.id.op_return));
		audio1Button = ((ImageButton)findViewById(R.id.audio1));
		audio2Button = ((ImageButton)findViewById(R.id.audio2));
		audio3Button = ((ImageButton)findViewById(R.id.audio3));

		int realScreenWidth = Constants.getScreen().getWidth();
		int buttonScale = Constants.getScreen().getHeight() / 10;
		
		RelativeLayout.LayoutParams button1Params = new RelativeLayout.LayoutParams(buttonScale,buttonScale);
		button1Params.leftMargin = (int)(((float)button1/(float)screenWidth)*realScreenWidth) - (buttonScale/2);
		button1Params.addRule(RelativeLayout.CENTER_VERTICAL);
		audio1Button.setLayoutParams(button1Params);
		
		RelativeLayout.LayoutParams button2Params = new RelativeLayout.LayoutParams(buttonScale,buttonScale);
		button2Params.leftMargin = (int)(((float)button2/(float)screenWidth)*realScreenWidth) - (buttonScale/2);
		button2Params.addRule(RelativeLayout.CENTER_VERTICAL);
		audio2Button.setLayoutParams(button2Params);
		
		RelativeLayout.LayoutParams button3Params = new RelativeLayout.LayoutParams(buttonScale,buttonScale);
		button3Params.leftMargin = (int)(((float)button3/(float)screenWidth)*realScreenWidth) - (buttonScale/2);
		button3Params.addRule(RelativeLayout.CENTER_VERTICAL);
		audio3Button.setLayoutParams(button3Params);
		
		Constants.setContext(getApplicationContext());

		backButton.setOnClickListener(goToOp);
		audio1Button.setOnClickListener(audio1);
		audio2Button.setOnClickListener(audio2);
		audio3Button.setOnClickListener(audio3);
		checkButton();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override 
	public void onResume(){
		Constants.createSoundManager(getApplicationContext());
		Constants.getSoundManager().loadSplash();
		super.onResume();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override 
	public void onPause(){
		Constants.getSoundManager().unloadAll();
		super.onPause();
	}

	/** The go to option button. */
	private OnClickListener goToOp = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.getSoundManager().playSplash();
			startActivity(new Intent(getApplicationContext(), Options.class));
			finish();
		}
	};

	/** The audio1 button. */
	private OnClickListener audio1 = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.sBackgroundVolume = 1;
			Constants.sEffectVolume = 1;
			checkButton();
		}
	};

	/** The audio2 button. */
	private OnClickListener audio2 = new OnClickListener() {

		@Override
		public void onClick(View view) {
			Constants.sBackgroundVolume = 0;
			Constants.sEffectVolume = 1;
			checkButton();
		}
	};
	
	/** The audio3 button. */
	private OnClickListener audio3 = new OnClickListener() {
		@Override
		public void onClick(View view) {
			Constants.sBackgroundVolume = 0;
			Constants.sEffectVolume = 0;
			checkButton();
		}
	};


	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy(){
		Runtime.getRuntime().gc();
		System.gc();
		super.onDestroy();
	}
	
	/**
	 * Check button.
	 */
	private void checkButton(){
		//depending on the values of the two variables set transparency of the other two buttons 
		if(Constants.sBackgroundVolume == 1 && Constants.sEffectVolume == 1){
			audio1Button.setBackgroundResource(R.drawable.selecter);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
		}else if(Constants.sBackgroundVolume == 0 && Constants.sEffectVolume == 1){
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			audio2Button.setBackgroundResource(R.drawable.selecter);
			audio3Button.setBackgroundColor(Color.TRANSPARENT);
		}else if(Constants.sBackgroundVolume == 0 && Constants.sEffectVolume == 0){
			audio1Button.setBackgroundColor(Color.TRANSPARENT);
			audio2Button.setBackgroundColor(Color.TRANSPARENT);
			audio3Button.setBackgroundResource(R.drawable.selecter);
		}
	}
}
