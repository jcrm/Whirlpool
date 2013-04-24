/*
 * Author:
 * Last Updated:
 * Content:
 * Added functionality for knowing when an animation is finished as well as sprites with multiple rows.
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

import android.graphics.Rect;

// TODO: Auto-generated Javadoc
/**
 * The Class Animate.
 */
public class Animate{
	
	/** The m frame num. */
	private int mFrameNum = 0;
	
	/** The m frame width. */
	private int mFrameWidth = 0;
	
	/** The m frame height. */
	private int mFrameHeight = 0;
	
	/** The m bitmap width. */
	private int mBitmapWidth = 0;
	
	/** The m bitmap height. */
	private int mBitmapHeight = 0;
	
	/** The m no of col. */
	private int mNoOfCol = 0;
	
	/** The m no of rows. */
	private int mNoOfRows = 0;
	
	/** The m no of frames. */
	private int mNoOfFrames = 1;
	
	/** The m delay. */
	private int mDelay = 3;
	
	/** The m counter. */
	private int mCounter = 0;
	
	/** The m portion. */
	private Rect mPortion;
	
	/** The m finished. */
	private boolean mFinished = false;

	/**
	 * Instantiates a new animate.
	 *
	 * @param totalFrames the total frames
	 * @param noOfRows the no of rows
	 * @param noOfCol the no of col
	 * @param width the width
	 * @param height the height
	 */
	public Animate(int totalFrames, int noOfRows, int noOfCol, int width, int height){
		mNoOfFrames = totalFrames;
		mBitmapWidth = width;
		mBitmapHeight = height;
		mNoOfCol = noOfCol;
		mNoOfRows = noOfRows;
		mFrameWidth = mBitmapWidth/mNoOfCol;
		mFrameHeight = mBitmapHeight/mNoOfRows;
		mPortion = new Rect(0, 0, mFrameWidth, mFrameHeight);
		Constants.getLock();
	}
	
	/**
	 * Gets the no of frames.
	 *
	 * @return the no of frames
	 */
	public int getNoOfFrames(){
		return mFrameNum;
	}
	
	/**
	 * Animate frame.
	 */
	public void animateFrame(){
		if(mCounter++ >= mDelay){
			mCounter = 0;
			mFrameNum++;
			updatePortion();
		}
	}
	
	/**
	 * Sets the delay.
	 *
	 * @param delay the new delay
	 */
	public void setDelay(int delay){
		mDelay = delay;
	}
	
	/**
	 * Update portion.
	 */
	public void updatePortion(){
		mPortion.left =  (mFrameNum%mNoOfCol) * mFrameWidth;
		mPortion.top = (mFrameNum/mNoOfCol) * mFrameHeight;
		mPortion.right = mPortion.left + mFrameWidth;
		mPortion.bottom = mPortion.top +mFrameHeight;
		if(mFrameNum >= mNoOfFrames-1){
			mFrameNum = 0;
			mFinished = true;
		}
	}

	/**
	 * Gets the portion.
	 *
	 * @return the portion
	 */
	public Rect getPortion() {
		return mPortion;
	}
	
	/**
	 * Reset.
	 *
	 * @param totalFrames the total frames
	 * @param noOfRows the no of rows
	 * @param noOfCol the no of col
	 * @param width the width
	 * @param height the height
	 * @param delay the delay
	 */
	public void Reset(int totalFrames, int noOfRows, int noOfCol, int width, int height, int delay){
		mNoOfFrames = totalFrames;
		mBitmapWidth = width;
		mBitmapHeight = height;
		mNoOfCol = noOfCol;
		mNoOfRows = noOfRows;
		mFrameWidth = mBitmapWidth/mNoOfCol;
		mFrameHeight = mBitmapHeight/mNoOfRows;
		mFinished = false;
		mDelay = delay;
		mPortion = new Rect(0, 0, mFrameWidth, mFrameHeight);
	}

	/**
	 * Gets the finished.
	 *
	 * @return the finished
	 */
	public boolean getFinished() {
		return mFinished;
	}

	/**
	 * Sets the finished.
	 *
	 * @param finished the new finished
	 */
	public void setFinished(boolean finished) {
		mFinished = finished;
	}
}
