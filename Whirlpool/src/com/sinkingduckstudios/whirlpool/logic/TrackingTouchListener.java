/*
 * Author: Fraser Tomison
 * Last Updated: 5/2/13
 * Content:
 * This is the touch listener hooked to the Panel
 * It handles the gesture of spinning a finger, 
 * and also of directing an already existing whirlpool
 * TODO: implement coordinate class to tidy things up
 */

package com.sinkingduckstudios.whirlpool.logic;

import android.view.MotionEvent;
import android.view.View;

import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.objects.Arrow;
import com.sinkingduckstudios.whirlpool.objects.Whirlpool;

final class TrackingTouchListener implements View.OnTouchListener{
	//really bad way of doing this, change asap. no structs in java.
	private float[] mStart = new float[2];
	private float[] mLast = new float[2];
	private float[] mCurrent = new float[2];
	private float[] mRefX = new float[4];
	private float[] mRefY = new float[4];
	private int mNoRef, mLastRef;
	private float mXDir, mYDir;
	private int mNewGesture;//type of gesture 1 =  whirl, 2 = direction
	private boolean mXToChange, mYToChange; //nasty
	//end result
	private float[] mWCenter = new float[2];
	private int mWSize; //whirlpool direction, center point, and size.
	private int mWPoolIndex;
	private float mWAngle;
	private final WPools mWPools;
	private Whirlpool mWhirl;
	private Arrow mArrow;
	private float mScaledX, mScaledY;
	TrackingTouchListener(WPools wpools) {
		mWPools = wpools;
		mNewGesture = 0;
	}

	public boolean onTouch(View view, MotionEvent event) {
		mScaledX = (float) (event.getX()*Constants.getScreen().getRatio());
		mScaledY = (float) (event.getY()*Constants.getScreen().getRatio());
		mScaledX = event.getX();
		mScaledY = event.getY();
		switch(event.getAction()){

		case MotionEvent.ACTION_DOWN:
			mWPoolIndex =mWPools.checkCollision(mScaledX, mScaledY);
			if (mWPoolIndex > -1){ //pointing a whirl
				mNewGesture = 2;
				mWhirl = mWPools.getWpools().get(mWPoolIndex);
				mArrow = (new Arrow(mWhirl.getCentreX(), mWhirl.getCentreY() , (mScaledX + Constants.getLevel().getScrollBy()), mScaledY));
				mWhirl.setArrow(mArrow);
			}else if (mWPoolIndex == -1){ //new whirl
				mStart[0] = mScaledX;
				mStart[1] = mScaledY;
				mLast[0] = mScaledX;
				mLast[1] = mScaledY;
				mNoRef = 0;
				addRefPoint();
				mNewGesture = 1;
				findCenter();
				mWhirl = addWPools(
						mWPools,
						(int) (mWCenter[0] + Constants.getLevel().getScrollBy()),
						(int) mWCenter[1],
						mWSize,
						-1,// pass in -1 for no angle, probs should clean this up
						isClockwise());
				break;
			}
		case MotionEvent.ACTION_MOVE:
			mCurrent[0] = mScaledX;
			mCurrent[1] = mScaledY;
			if (mNewGesture == 2){	//arrow being drawn
				mArrow.setVisible(mWhirl.calcTangentPoint((mScaledX + Constants.getLevel().getScrollBy()), mScaledY));
				if (mArrow.getVisible())
					mArrow.reposition(mWhirl.getTangentX(), mWhirl.getTangentY() , (mScaledX + Constants.getLevel().getScrollBy()), mScaledY);
			}else if (mNewGesture == 1){
				//gesture has been started. determine direction.
				mXDir = (mCurrent[0] - mStart[0]);
				if (mXDir > 0) mXDir = 1; else mXDir = -1;
				mYDir = (mCurrent[1] - mStart[1]);
				if (mYDir > 0) mYDir = 1; else mYDir = -1;
				mNewGesture=0;
				mXToChange = true;
				mYToChange = true;
			}else if (mNewGesture == 0){

				if(mNoRef < 3){//if no precise whirl motion has been completed yet, draw whirlpool in center
					mRefX[mNoRef]=mLast[0];
					mRefY[mNoRef]=mLast[1];
				}
				findCenter();//calc current center to position wpool
				mWhirl.setCentreX((int) (mWCenter[0]+ Constants.getLevel().getScrollBy()));
				mWhirl.setCentreY((int) mWCenter[1]);
				mWhirl.setClockwise(isClockwise());
				if ((mCurrent[0] - mLast[0])*mXDir < 0){//change in x direction
					if (mXToChange == false){
						mStart[0] = mScaledX;
						mStart[1] = mScaledY;
						mLast[0] = mScaledX;
						mLast[1] = mScaledY;
						mNoRef = 0;
						mNewGesture = 1;
						break;
					}
					addRefPoint();
					mXDir*=-1;
					mYToChange = true;
					mXToChange = false;
				}
				if((mCurrent[1] - mLast[1])*mYDir < 0){//change in y direction
					if(mYToChange == false){
						mStart[0] = mScaledX;
						mStart[1] = mScaledY;
						mLast[0] = mScaledX;
						mLast[1] = mScaledY;
						mNoRef = 0;
						mNewGesture = 1;
						break;
					}
					addRefPoint();
					mYDir*=-1;
					mYToChange = false;
					mXToChange = true;
				}	
			}
			mLast[0] = mScaledX;
			mLast[1] = mScaledY;
			break;
		case MotionEvent.ACTION_UP:
			if(mNewGesture == 2){	
				mArrow.setVisible(mWhirl.calcTangentPoint((mScaledX + Constants.getLevel().getScrollBy()), mScaledY));
				if (mArrow.getVisible()){
					mWAngle = CollisionManager.calcAngle(mWhirl.getTangentX(), mWhirl.getTangentY() , (mScaledX + Constants.getLevel().getScrollBy()), mScaledY);
					mWhirl.setWAngle(mWAngle);
				}
			}else if (mNewGesture == 0){
				if (mNoRef > 2){//whirlpool made
					findCenter();
					mWhirl.setCentreX((int) (mWCenter[0]+ Constants.getLevel().getScrollBy()));
					mWhirl.setCentreY((int) mWCenter[1]);
					mWhirl.setClockwise(isClockwise());
				}else{
					//remove whirl (gesture wasnt completed)
					mWPools.getWpools().remove(mWhirl);
				}    		
			}
			break;
		default: return false;
		}
		return true;
	}

	private void addRefPoint(){
		int refindex = mNoRef % 4;
		mRefX[refindex]=mLast[0];
		mRefY[refindex]=mLast[1];
		mLastRef=refindex;
		mNoRef++;
	}

	private void findCenter(){
		int CountTo = mNoRef;
		if (CountTo > 4){
			CountTo = 4;
		}
		float additionX=0, additionY=0;
		for (int i = 0; i < CountTo; i++){
			additionX += mRefX[i];
			additionY += mRefY[i];
		}
		mWCenter[0] = additionX/CountTo;
		mWCenter[1] = additionY/CountTo;

		//calc size of whirlpool, simply NoRefs
		mWSize = mNoRef;
	}

	private int isClockwise(){
		if (mNoRef < 3){
			return 0;//not enough ref points to get a direction
		}
		
		int otherRef = mLastRef - 1; //reference point before last
		if (otherRef == -1){ 
			otherRef = 3;
		}
		
		//2D vectors
		float oneX = mRefX[mLastRef] - mWCenter[0];
		float oneY = mRefY[mLastRef] - mWCenter[1];
		float twoX = mRefX[otherRef] - mWCenter[0];
		float twoY = mRefY[otherRef] - mWCenter[1];

		//Do cross product of vectors to get if clockwise or counter
		float crossProduct = (oneX*twoY) - (oneY*twoX);

		if (crossProduct >= 0) 
			return -1;//counter
		else
			return 1;//clockwise

	}

	private Whirlpool addWPools(WPools wpools, int x, int y, int s, float angle, int clockwise) {
		wpools.addWPool(x, y, s, angle, clockwise);
		return wpools.getLastWpool();
	}

	public float getScaledX() {
		return mScaledX;
	}

	public void setScaledX(float scaledX) {
		mScaledX = scaledX;
	}

	public float getScaledY() {
		return mScaledY;
	}

	public void setScaledY(float scaledY) {
		mScaledY = scaledY;
	}
}
