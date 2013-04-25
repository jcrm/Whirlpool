/*
 * Author:Lewis Shaw
 * Last Updated:25/04/2013
 * Content:
 * Used to show the game activity
 */
package com.sinkingduckstudios.whirlpool.views;

import com.sinkingduckstudios.whirlpool.logic.Constants;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;//
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * The Class GameView.
 */
public class GameView extends View{
	/** The resources. */
	private static  Resources sRes;
    /** The screen lock. */
    private static Object sScreenLock;
    
    /**
     * Instantiates a new game view.
     *
     * @param context
     */
    public GameView(Context context) {
        super(context);
        setFocusable(true);
        sScreenLock = Constants.getLock();
    }
    
    /**
     * Instantiates a new game view.
     *
     * @param context
     * @param attributes
     */
    public GameView(Context context, AttributeSet attributes) {
        super(context, attributes);
        setFocusable(true);
        sScreenLock = Constants.getLock();
    }
    /* (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @SuppressLint("WrongCall")
	@Override
    public void onDraw(Canvas canvas) {
    	synchronized(sScreenLock){
    		if(Constants.getLock()!=null)
    		Constants.getLevel().onDraw(canvas);
    	}
    }
    
    /**
     * Inits the view.
     */
    public void init(){
    	sRes = getResources();
    	Constants.setRes(sRes);
    }
}