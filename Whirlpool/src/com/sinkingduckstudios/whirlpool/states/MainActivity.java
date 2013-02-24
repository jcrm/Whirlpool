package com.sinkingduckstudios.whirlpool.states;

import android.app.Activity;
import android.os.Bundle;

import com.sinkingduckstudios.whirlpool.logic.Level;

public abstract class MainActivity extends Activity {
	static protected Level mCurrentLevel;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    
    public MainActivity getState(){
    	return this;
    }

    public abstract void update();
    
    
	
    
}
