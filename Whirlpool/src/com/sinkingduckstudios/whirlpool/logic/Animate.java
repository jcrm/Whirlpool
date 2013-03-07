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
	private int mNoOfFrames = 1;
	private int mDelay = 3;
	private int mCounter = 0;
	private Rect mPortion;

	public Animate(int frames, int width, int height){
		mNoOfFrames = frames;
		mFrameWidth = width/mNoOfFrames ;
		mFrameHeight = height;
		mPortion = new Rect(0, 0, mFrameWidth, mFrameHeight);
		Constants.getLock();
	}

	public void animateFrame(){
		if(mCounter++ >= mDelay){
			mCounter = 0;
			mFrameNum++;
			updatePortion();
		}
	}

	public void updatePortion(){
		if(mFrameNum >= mNoOfFrames){		//TODO No clue why I have to -3 from this to make it not show blank frames D:
			mFrameNum = 0;
		}
		//synchronized(screenLock){
		mPortion.left =  mFrameNum * mFrameWidth;
		mPortion.right = getPortion().left + mFrameWidth;
		//}
	}

	public Rect getPortion() {
		return mPortion;
	}
	public void Reset(int frames, int width, int height){
		mNoOfFrames = frames;
		mFrameWidth = width/mNoOfFrames ;
		mFrameHeight = height;
		mPortion = new Rect(0, 0, mFrameWidth, mFrameHeight);
	}
}
