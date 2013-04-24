/*
 * Author: Fraser Tomison
 * Last Updated: 5/2/13
 * Content:
 * This is the touch listener hooked to the Panel
 * It handles the gesture of spinning a finger, 
 * and also of directing an already existing whirlpool
 */

package com.sinkingduckstudios.whirlpool.logic;

import android.view.MotionEvent;
import android.view.View;

import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.objects.Arrow;
import com.sinkingduckstudios.whirlpool.objects.Whirlpool;

final class TrackingTouchListener implements View.OnTouchListener{

	private final int GESTURE_NONE = -1;
	private final int GESTURE_WHIRLPOOL = 0;
	private final int GESTURE_NEW_WHIRLPOOL = 1;
	private final int GESTURE_ARROW = 2;

	//x/y coords of points
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

	/**
	 * Constructor for game touch listener, 
	 * pass in reference to whirlpool model
	 * 
	 * @param wpools reference to whirlpool model
	 */
	TrackingTouchListener(WPools wpools) {
		mWPools = wpools;
		mNewGesture = 0;
	}


	public boolean onTouch(View view, MotionEvent event) {
		mScaledX = event.getX();
		mScaledY = event.getY();
		switch(event.getAction()){

		//If finger down, a new gesture has begun. 
		//Check if finger collides with any whirlpools
		case MotionEvent.ACTION_DOWN:
			mWPoolIndex =mWPools.checkCollision(mScaledX, mScaledY);
			if (mWPoolIndex > -1){ //pointing a whirl
				mNewGesture = GESTURE_ARROW;
				mWhirl = mWPools.getWpools().get(mWPoolIndex);
				mArrow = (new Arrow(mWhirl.getCentreX(), mWhirl.getCentreY() , (mScaledX + Constants.getLevel().getScrollBy()), mScaledY));
				mWhirl.setArrow(mArrow);		
			}
			//If finger is not touching a whirlpool, indicate that a new whirlpool may be created
			else if (mWPoolIndex == -1){ //new whirl
				mStart[0] = mScaledX;
				mStart[1] = mScaledY;
				mLast[0] = mScaledX;
				mLast[1] = mScaledY;
				mNoRef = 0;
				//Add the start point as a reference point
				addRefPoint();
				mNewGesture = GESTURE_NEW_WHIRLPOOL;
				mWhirl=null;
				break;
			}

			//If finger is moved, detect which gesture we are drawing
		case MotionEvent.ACTION_MOVE:
			mCurrent[0] = mScaledX;
			mCurrent[1] = mScaledY;

			//An arrow is being placed, if the arrow position is valid, reposition it to users finger.
			//Always draw the start point of the arrow to the tangent of the whirlpool
			if (mNewGesture == GESTURE_ARROW){	
				mArrow.setVisible(mWhirl.calcTangentPoint((mScaledX + Constants.getLevel().getScrollBy()), mScaledY));
				if (mArrow.getVisible())
					mArrow.reposition(mWhirl.getTangentX(), mWhirl.getTangentY() , (mScaledX + Constants.getLevel().getScrollBy()), mScaledY);

				//If a whirlpool has just been created, initialise a few things
				//We need to know the direction of the first touch event after creation, so we can define a spiral
			}else if (mNewGesture == GESTURE_NEW_WHIRLPOOL){
				//gesture has been started. determine direction.
				mXDir = (mCurrent[0] - mStart[0]);
				if (mXDir > 0) mXDir = 1; else mXDir = -1;
				mYDir = (mCurrent[1] - mStart[1]);
				if (mYDir > 0) mYDir = 1; else mYDir = -1;
				//Once initialised, set gesture to whirlpool
				mNewGesture=GESTURE_WHIRLPOOL;
				mXToChange = true;
				mYToChange = true;

				//If a whirlpool gesture is being drawn, 
				//ensure the users touch is continuing in a spiralling direction
			}else if (mNewGesture == GESTURE_WHIRLPOOL){

				/*
				 * The nature of a spiral means that the change in each axis has to happen alternatively
				 * For example, if the initial direction was in the gradient -1/2
				 * Not the first axis to flip, eg the next gradient is 1/2
				 * For a spiral gesture to continue, we know the next change in direction must be to 1/-2
				 * thus, this is how the spiral gesture is defined
				 */
				if ((mCurrent[0] - mLast[0])*mXDir < 0){//change in x direction
					if (mXToChange == false){
						//If the users touch has changed in the x direction, 
						//but the ydirection was set to change, reset the gesture
						mStart[0] = mScaledX;
						mStart[1] = mScaledY;
						mLast[0] = mScaledX;
						mLast[1] = mScaledY;
						mNoRef = 0;
						mNewGesture = GESTURE_NEW_WHIRLPOOL;
						break;
					}
					//A direction has flipped, which means we are at a vertex, 
					//so log this point as a reference point
					addRefPoint();
					findCenter();	//get average position between all reference points
					if(mNoRef==3){  //if there are three reference points, we have enough data
						//to create a whirlpool, so do. 
						mWhirl = addWPools(
								mWPools,
								(int) ((mWCenter[0] + Constants.getLevel().getScrollBy())*Constants.getScreen().getRatio()),
								(int) (mWCenter[1]*Constants.getScreen().getRatio()),
								mWSize,
								-1,					// pass in -1 for no angle set yet
								isClockwise());		//isClockwise() examines the ref points, and calculates the direction
						mNewGesture = GESTURE_NONE;	//gesture finished, do not log anymore actions till the next touch down
					}
					//note that the x direction has changed, and that y is next to change
					mXDir*=-1;
					mYToChange = true;
					mXToChange = false;
				}
				if((mCurrent[1] - mLast[1])*mYDir < 0){//change in y direction
					if(mYToChange == false){
						//If the users touch has changed in the y direction, 
						//but the xdirection was set to change, reset the gesture
						mStart[0] = mScaledX;
						mStart[1] = mScaledY;
						mLast[0] = mScaledX;
						mLast[1] = mScaledY;
						mNoRef = 0;
						mNewGesture = GESTURE_NEW_WHIRLPOOL;
						break;
					}
					//A direction has flipped, which means we are at a vertex, 
					//so log this point as a reference point
					addRefPoint();
					findCenter();	//get average position between all reference points
					if(mNoRef==3){	//if there are three reference points, we have enough data
						//to create a whirlpool, so do. 
						mWhirl = addWPools(
								mWPools,
								(int) ((mWCenter[0] + Constants.getLevel().getScrollBy())*Constants.getScreen().getRatio()),
								(int) (mWCenter[1]*Constants.getScreen().getRatio()),
								mWSize,
								-1,					// pass in -1 for no angle set yet
								isClockwise());		//isClockwise() examines the ref points, and calculates the direction
						mNewGesture = GESTURE_NONE;	//gesture finished, do not log anymore actions till the next touch down
					}
					//note that the y direction has changed, and that x is next to change
					mYDir*=-1;
					mYToChange = false;
					mXToChange = true;
				}	
			}

			//save the current coordinates in the last coordinates for reference
			mLast[0] = mScaledX;
			mLast[1] = mScaledY;
			break;

			//If finger is lifted, check if we are drawing an arrow
			//If we are, if it is in a valid position, finalise its position and set whirlpool exit angle
		case MotionEvent.ACTION_UP:
			if(mNewGesture == GESTURE_ARROW){	
				mArrow.setVisible(mWhirl.calcTangentPoint((mScaledX + Constants.getLevel().getScrollBy()), mScaledY));
				if (mArrow.getVisible()){
					mWAngle = CollisionManager.calcAngle(mWhirl.getTangentX(), mWhirl.getTangentY() , (mScaledX + Constants.getLevel().getScrollBy()), mScaledY);
					mWhirl.setWAngle(mWAngle);
				}
			}
			break;
		default: return false;
		}
		return true;
	}

	/**
	 * If we have detected a vertex (change in axis direction)
	 * then log this point as a reference point
	 */
	private void addRefPoint(){
		int refindex = mNoRef % 4;
		mRefX[refindex]=mLast[0];
		mRefY[refindex]=mLast[1];
		mLastRef=refindex;
		mNoRef++;
	}

	/**
	 * Call this to find the centre point of all current reference points
	 */
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

	/**
	 * Call to assertain the direction the whirlpool gesture was drawn
	 * @return direction, 
	 * 0 for can not calculate.
	 * 1 for clockwise.
	 * -1 for counter clockwise.
	 */
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
