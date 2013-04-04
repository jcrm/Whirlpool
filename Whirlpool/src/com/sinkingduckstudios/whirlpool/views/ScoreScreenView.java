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
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class ScoreScreenView extends View {
	private Bitmap background;
	private Bitmap mEmptyStar;
	private Bitmap mFullStar;
	private Rect mBackgroundRect;
	private Rect mStarRect[] = new Rect[3];
	
	public ScoreScreenView(Context context) {
		super(context);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		opt.inJustDecodeBounds = false;
		background = BitmapFactory.decodeResource(getResources(), R.drawable.other_background, opt);
		mEmptyStar = SpriteManager.getEmptyStar();
		mFullStar = SpriteManager.getFullStar();
		mBackgroundRect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		mStarRect[0] = new Rect((Constants.getScreen().getWidth()/2)-125,(Constants.getScreen().getWidth()/2)-75,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
		mStarRect[1] = new Rect((Constants.getScreen().getWidth()/2)-25,(Constants.getScreen().getWidth()/2)+25,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
		mStarRect[2] = new Rect((Constants.getScreen().getWidth()/2)+75,(Constants.getScreen().getWidth()/2)+125,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
	}
	
	public ScoreScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, 500, 500);
		opt.inJustDecodeBounds = false;
		background = BitmapFactory.decodeResource(getResources(), R.drawable.other_background, opt);
		mEmptyStar = SpriteManager.getEmptyStar();
		mFullStar = SpriteManager.getFullStar();
		mBackgroundRect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		mStarRect[0] = new Rect((Constants.getScreen().getWidth()/2)-125,(Constants.getScreen().getWidth()/2)-75,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
		mStarRect[1] = new Rect((Constants.getScreen().getWidth()/2)-25,(Constants.getScreen().getWidth()/2)+25,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
		mStarRect[2] = new Rect((Constants.getScreen().getWidth()/2)+75,(Constants.getScreen().getWidth()/2)+125,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas){
		
		canvas.drawBitmap(background, null, mBackgroundRect, null);
		for(int i = 0; i<3; i++){			
			canvas.drawBitmap(mEmptyStar, null, mStarRect[i], null);
		}
	}
	
	public void CleanUp() {
		background.recycle();
		background = null;
		mEmptyStar.recycle();
		mEmptyStar = null;
		mFullStar.recycle();
		mFullStar = null;
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
