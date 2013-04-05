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
	private int mStars=0;
	
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
		Constants.setRes(getResources());
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
		Constants.setRes(getResources());
		mEmptyStar = SpriteManager.getEmptyStar();
		mFullStar = SpriteManager.getFullStar();
		mBackgroundRect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		mStarRect[0] = new Rect(-(Constants.getScreen().getHeight()/6),-(Constants.getScreen().getHeight()/6),(Constants.getScreen().getHeight()/6), (Constants.getScreen().getHeight()/6));
		mStarRect[1] = new Rect(-(Constants.getScreen().getHeight()/6),-(Constants.getScreen().getHeight()/6),(Constants.getScreen().getHeight()/6), (Constants.getScreen().getHeight()/6));
		mStarRect[2] = new Rect(-(Constants.getScreen().getHeight()/6),-(Constants.getScreen().getHeight()/6),(Constants.getScreen().getHeight()/6), (Constants.getScreen().getHeight()/6));
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas){
		
		canvas.drawBitmap(background, null, mBackgroundRect, null);
		if(mFullStar != null && mFullStar.isRecycled()){
			canvas.translate(Constants.getScreen().getWidth()/2,Constants.getScreen().getHeight()/3);
			canvas.drawBitmap(mFullStar, null, mStarRect[0], null);
		}
		canvas.translate(-(Constants.getScreen().getHeight()/4),(Constants.getScreen().getHeight()/5));
		
		if(mStars>=2)
			if(mFullStar != null && mFullStar.isRecycled()){
				canvas.drawBitmap(mFullStar, null, mStarRect[1], null);
			}
		else
			if(mEmptyStar != null && mEmptyStar.isRecycled()){
				canvas.drawBitmap(mEmptyStar, null, mStarRect[1], null);
			}
		canvas.translate((Constants.getScreen().getHeight()/2f),0);
		
		if(mStars==3)
			if(mFullStar != null && mFullStar.isRecycled()){
				canvas.drawBitmap(mFullStar, null, mStarRect[2], null);
			}
		else
			if(mEmptyStar != null && mEmptyStar.isRecycled()){
				canvas.drawBitmap(mEmptyStar, null, mStarRect[2], null);
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

	public void setStars(int stars) {
		mStars = stars;
		
	}

}
