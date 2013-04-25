/*
 * Author: Jake Morey, Connor Nicol
 * Content:
 * Connor Nicol: added sounds
 * Jake Morey: created based upon game view to display
 * image based upon slide number in cinematic class
 */
package com.sinkingduckstudios.whirlpool.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
import com.sinkingduckstudios.whirlpool.states.Cinematic;

/**
 * The Class CinematicView.
 */
public class CinematicView extends View{
	/** The Loaded boolean. */
	protected boolean Loaded = false;
	/** The Cinematic images. */
	private Bitmap mCinematic[] = new Bitmap[6];
	
	/**
	 * Instantiates a new cinematic view.
	 *
	 * @param context 
	 */
	public CinematicView(Context context) {
		super(context);
		init();
	}
	/**
	 * Instantiates a new cinematic view.
	 *
	 * @param context
	 * @param attrs
	 */
	public CinematicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	/**
	 * Inits the view.
	 */
	private void init(){
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		Constants.setRes(getResources());
		Constants.createSoundManager(getContext());
		Constants.updateVolume();
    	Constants.getSoundManager().loadCinematic();
    	//set up cinematic images
		for(int i = 0; i<6; i++){
			mCinematic[i] = SpriteManager.getCinematic(i);
		}
	}
	/* (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas){
		//draw image base upon cinematic class slide number
		Rect rect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		if(mCinematic[Cinematic.mSlide] != null && mCinematic[Cinematic.mSlide].isRecycled() ==false){
			canvas.drawBitmap(mCinematic[Cinematic.mSlide], null, rect, null);	
			Constants.getSoundManager().playCinematic(Cinematic.mSlide);
		}
		
	}
	
	/**
	 * Clean up.
	 */
	public void CleanUp() {
		for(int i = 0; i < 6; i++){
			mCinematic[i].recycle();
			mCinematic[i] = null;
		}
		Constants.getSoundManager().CleanCinematic();
	}
}
