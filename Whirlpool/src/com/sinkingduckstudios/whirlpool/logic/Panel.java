/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class Panel extends View{
	private static  Resources sRes;
    private static Object sScreenLock;
    
    public Panel(Context context) {
        super(context);
        constructor();
    }
    public Panel(Context context, AttributeSet attributes) {
        super(context, attributes);
        constructor();
    }
    private void constructor(){
    	
        setFocusable(true);
        sScreenLock = Constants.getLock();
    }

    @Override
    public void onDraw(Canvas canvas) {
    	synchronized(sScreenLock){
    		if(Constants.getLock()!=null)
    		Constants.getLevel().onDraw(canvas);
    	}
    }
    public void init(){
    	//int x = findViewById(R.id.mainview).getWidth();
    	//int y = findViewById(R.id.mainview).getHeight();
    	sRes = getResources();
    	Constants.setRes(sRes);
    }
}