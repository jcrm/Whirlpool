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

// TODO: Auto-generated Javadoc
/**
 * The Class ScoreScreenView.
 */
public class ScoreScreenView extends View {
	
	/** The background image. */
	private Bitmap background;
	/** The empty star image. */
	private Bitmap mEmptyStar;
	/** The full star image. */
	private Bitmap mFullStar;
	/** The background rect. */
	private Rect mBackgroundRect;
	/** The star rects. */
	private Rect mStarRect[] = new Rect[3];
	/** The number of stars. */
	private int mStars=0;
	
	/**
	 * Instantiates a new score screen view.
	 *
	 * @param context the context
	 */
	public ScoreScreenView(Context context) {
		super(context);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		Constants.setRes(getResources());
		scaleBitmap();
		mEmptyStar = SpriteManager.getEmptyStar();
		mFullStar = SpriteManager.getFullStar();
		mBackgroundRect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		mStarRect[0] = new Rect((Constants.getScreen().getWidth()/2)-125,(Constants.getScreen().getWidth()/2)-75,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
		mStarRect[1] = new Rect((Constants.getScreen().getWidth()/2)-25,(Constants.getScreen().getWidth()/2)+25,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
		mStarRect[2] = new Rect((Constants.getScreen().getWidth()/2)+75,(Constants.getScreen().getWidth()/2)+125,(Constants.getScreen().getHeight()/4)-25, (Constants.getScreen().getHeight()/4)+25);
	}
	/**
	 * Instantiates a new score screen view.
	 *
	 * @param context
	 * @param attrs
	 */
	public ScoreScreenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		Constants.setRes(getResources());
		scaleBitmap();
		mEmptyStar = SpriteManager.getEmptyStar();
		mFullStar = SpriteManager.getFullStar();
		mBackgroundRect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		mStarRect[0] = new Rect(-(Constants.getScreen().getHeight()/6),-(Constants.getScreen().getHeight()/6),(Constants.getScreen().getHeight()/6), (Constants.getScreen().getHeight()/6));
		mStarRect[1] = new Rect(-(Constants.getScreen().getHeight()/6),-(Constants.getScreen().getHeight()/6),(Constants.getScreen().getHeight()/6), (Constants.getScreen().getHeight()/6));
		mStarRect[2] = new Rect(-(Constants.getScreen().getHeight()/6),-(Constants.getScreen().getHeight()/6),(Constants.getScreen().getHeight()/6), (Constants.getScreen().getHeight()/6));
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
		canvas.drawBitmap(background, null, mBackgroundRect, null);
		canvas.translate(Constants.getScreen().getWidth()/2,Constants.getScreen().getHeight()/3);
		canvas.drawBitmap(mFullStar, null, mStarRect[0], null);
		canvas.translate(-(Constants.getScreen().getHeight()/4),(Constants.getScreen().getHeight()/5));
		if(mStars>=2){
			canvas.drawBitmap(mFullStar, null, mStarRect[1], null);
		}else{
			canvas.drawBitmap(mEmptyStar, null, mStarRect[1], null);
		}
		canvas.translate((Constants.getScreen().getHeight()/2f),0);
		if(mStars==3){
			canvas.drawBitmap(mFullStar, null, mStarRect[2], null);
		}else{
			canvas.drawBitmap(mEmptyStar, null, mStarRect[2], null);
		}
	}
	/**
	 * Sets the stars.
	 *
	 * @param stars the new stars
	 */
	public void setStars(int stars) {
		mStars = stars;
		
	}
	/**
	 * Clean up.
	 */
	public void CleanUp() {
		background.recycle();
		background = null;
		mEmptyStar.recycle();
		mEmptyStar = null;
		mFullStar.recycle();
		mFullStar = null;
	}
	/**
	 * Scale bitmap.
	 */
	private void scaleBitmap(){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		opt.inJustDecodeBounds = false;
		background = BitmapFactory.decodeResource(getResources(), R.drawable.other_background, opt);
	}
	/**
	 * Gets the scale.
	 *
	 * @param oW the original width
	 * @param oH the original height
	 * @param nW the new width
	 * @param nH the new height
	 * @return the scale
	 */
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
