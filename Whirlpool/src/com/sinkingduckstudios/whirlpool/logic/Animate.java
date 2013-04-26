/*
 * Author: Connor Nicol implemented by Jordan O'Hare edited by Jake Morey
 * Last Updated:
 * Content:
 * A class for moving a rectangle around an image
 * that describes the corners of a frame. 
 * Jake Morey:
 * Added functionality for knowing when an animation is 
 * finished as well as sprites with multiple rows.
 */
package com.sinkingduckstudios.whirlpool.logic;

import android.graphics.Rect;

/**
 * The Class Animate.
 */
public class Animate{
	
	/** The frame number. */
	private int mFrameNum = 0;
	/** The frame width. */
	private int mFrameWidth = 0;
	/** The frame height. */
	private int mFrameHeight = 0;
	/** The bitmap width. */
	private int mBitmapWidth = 0;
	/** The bitmap height. */
	private int mBitmapHeight = 0;
	/** The number of columns. */
	private int mNoOfCol = 0;
	/** The number of rows. */
	private int mNoOfRows = 0;
	/** The number of frames. */
	private int mNoOfFrames = 1;
	/** The delay. */
	private int mDelay = 3;
	/** The counter. */
	private int mCounter = 0;
	/** The portion. */
	private Rect mPortion;
	/** The finished variable. */
	private boolean mFinished = false;

	/**
	 * Instantiates a new animate.
	 *
	 * @param totalFrames the total frames
	 * @param noOfRows the number of rows.
	 * @param noOfCol the number of columns.
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
	 * @return the number of frames
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
		/*
		 * use the number of columns to work out where the top and left
		 * of the rectangle for the frame. add width and height to work
		 * out right and bottom.
		 */
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
	 * @param noOfRows the number of rows.
	 * @param noOfCol the number of columns.
	 * @param width the width
	 * @param height the height
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
