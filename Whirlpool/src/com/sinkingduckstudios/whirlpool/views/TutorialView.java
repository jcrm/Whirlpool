/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
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
import com.sinkingduckstudios.whirlpool.states.Tutorial;;

public class TutorialView extends View{
	private Bitmap mTutorial[] = new Bitmap[4];
	public TutorialView(Context context) {
		super(context);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
    	Constants.setRes(getResources());

		for(int i = 0; i<4; i++){
			mTutorial[i] = SpriteManager.getTutorial(i);
		}
	}
	
	public TutorialView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		Constants.setRes(getResources());

		for(int i = 0; i<4; i++){
			mTutorial[i] = SpriteManager.getTutorial(i);
		}
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas){
		Rect rect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		if(mTutorial[Tutorial.mSlide] != null && mTutorial[Tutorial.mSlide].isRecycled() ==false){
			canvas.drawBitmap(mTutorial[Tutorial.mSlide], null, rect, null);	
		}
		
	}
	public void CleanUp() {
		for(int i = 0; i < 4; i++){
			mTutorial[i].recycle();
			mTutorial[i] = null;
		}
	}
}
