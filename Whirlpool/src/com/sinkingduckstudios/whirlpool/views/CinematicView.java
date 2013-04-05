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
import com.sinkingduckstudios.whirlpool.states.Cinematic;

public class CinematicView extends View{
	private Bitmap mCinematic[] = new Bitmap[6];
	public CinematicView(Context context) {
		super(context);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
    	Constants.setRes(getResources());
		for(int i = 0; i<6; i++){
			mCinematic[i] = SpriteManager.getCinematic(i);
		}
	}
	
	public CinematicView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		Constants.setRes(getResources());
		for(int i = 0; i<6; i++){
			mCinematic[i] = SpriteManager.getCinematic(i);
		}
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas){
		Rect rect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		canvas.drawBitmap(mCinematic[Cinematic.mSlide], null, rect, null);
		
	}
	
	public void CleanUp() {
		for(int i = 0; i < 6; i++){
			mCinematic[i].recycle();
			mCinematic[i] = null;
		}
		// TODO Auto-generated method stub
		
	}
	
	public int getScale(int oW, int oH, int nW, int nH){
		int scale = 1;
		if(oW>nW || oH>nH){
			if(oW<oH){
				scale=Math.round(oW/nW);
			}else{
				scale=Math.round(oH/nH);
			}
		}
		return scale;
	}

}