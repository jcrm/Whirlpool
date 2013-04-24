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
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.TextView;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;

public class Credits extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_credits);
        
        int height = Constants.getScreen().getHeight()/10;
        Typeface face = Typeface.createFromAsset(getAssets(), "whirlpool.ttf");
        TextView Jake = (TextView) findViewById(R.id.credits1);
        Jake.setY(height);
		Jake.setTextColor(Color.BLACK);
        Jake.setTypeface(face);
        
        TextView Fraser = (TextView) findViewById(R.id.credits2);
        Fraser.setY(2*height);
        Fraser.setTextColor(Color.BLACK);
        Fraser.setTypeface(face);
        
        TextView Lewis = (TextView) findViewById(R.id.credits3);
        Lewis.setY(3*height);
        Lewis.setTextColor(Color.BLACK);
        Lewis.setTypeface(face);
        
        TextView Connor = (TextView) findViewById(R.id.credits4);
        Connor.setY(4*height);
        Connor.setTextColor(Color.BLACK);
        Connor.setTypeface(face);
        
        TextView Jordan = (TextView) findViewById(R.id.credits8);
        Jordan.setY(5*height);
        Jordan.setTextColor(Color.BLACK);
        Jordan.setTypeface(face);
        
        TextView Noah = (TextView) findViewById(R.id.credits5);
        Noah.setY(6*height);
        Noah.setTextColor(Color.BLACK);
        Noah.setTypeface(face);
        
        TextView Aiste = (TextView) findViewById(R.id.credits6);
        Aiste.setY(7*height);
        Aiste.setTextColor(Color.BLACK);
        Aiste.setTypeface(face);
        
        TextView Allan = (TextView) findViewById(R.id.credits7);
        Allan.setY(8*height);
        Allan.setTextColor(Color.BLACK);
        Allan.setTypeface(face);
        
        TextView Andrew = (TextView) findViewById(R.id.credits9);
        Andrew.setY(9*height);
        Andrew.setTextColor(Color.BLACK);
        Andrew.setTypeface(face);
        
        new Handler().postDelayed (new Runnable(){
        	@Override
        	public void run(){    			
    			startActivity(new Intent(getApplicationContext(),Options.class));
        		finish();
        	}
        },3000);
    }
	@Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        Runtime.getRuntime().gc();
        System.gc();
        super.onDestroy();
    }
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if(e.getAction() == MotionEvent.ACTION_DOWN){
			startActivity(new Intent(getApplicationContext(),Options.class));
    		finish();
		}
		return true;
	}
}   

