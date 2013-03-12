/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

import android.graphics.Rect;

public class Animate{
	private int mFrameNum = 0;
	private int mFrameWidth = 0;
	private int mFrameHeight = 0;
	private int mBitmapWidth = 0;
	private int mBitmapHeight = 0;
	private int mNoOfCol = 0;
	private int mNoOfRows = 0;
	private int mNoOfFrames = 1;
	private int mDelay = 3;
	private int mCounter = 0;
	private Rect mPortion;
	private boolean mFinished = false;

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
	public int getNoOfFrames(){
		return mFrameNum;
	}
	public void animateFrame(){
		if(mCounter++ >= mDelay){
			mCounter = 0;
			mFrameNum++;
			updatePortion();
		}
	}
	public void setDelay(int delay){
		mDelay = delay;
	}
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

	public Rect getPortion() {
		return mPortion;
	}
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

	public boolean getFinished() {
		return mFinished;
	}

	public void setFinished(boolean finished) {
		mFinished = finished;
	}
}
