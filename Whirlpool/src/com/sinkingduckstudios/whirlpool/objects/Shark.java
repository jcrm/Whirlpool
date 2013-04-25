/*
 * Author: Jake Morey
 * Content:
 * Jake Morey: based upon the parent class with added functionality for the different shark states.
 *
 */
package com.sinkingduckstudios.whirlpool.objects;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

/**
 * The Class Shark.
 */
public class Shark extends GraphicObject{
	
	/**
	 * The Enum SharkType.
	 */
	public enum SharkType{
		tDefault, tAsleep, tFollow, tAttack, tRetreat, tWait;
	}
	
	/** The Constant TopSpeed. */
	private static final float mTopSpeed = 8;
	/** The shark state. */
	private SharkType mSharkState = SharkType.tDefault;
	/** The up bitmap image. */
	private Bitmap mUpBitmap;
	/** The down bitmap image. */
	private Bitmap mDownBitmap;
	/** The asleep bitmap image. */
	private Bitmap mAsleepBitmap;
	/** The attack bitmap image. */
	private Bitmap mAttackBitmap;
	/** The up animation. */
	private Animate mUpAnimate;
	/** The down animation. */
	private Animate mDownAnimate;
	/** The attack animation. */
	private Animate mAttackAnimate;
	/** The asleep animation. */
	private Animate mAsleepAnimate;
	/** The shark radius. */
	private int mSharkRadius = Constants.getLevel().getLevelHeight()/2;
	/** The duck counter. */
	private int mDuckCounter = 10;
	/** The wait counter. */
	private int mWaitCounter = 40;
	/** The wait time. */
	private int mWaitTime = 40;
	/** The start position. */
	private Point mStart;
	/** The drop location. */
	private Point mDropLocation;

	/**
	 * Instantiates a new shark.
	 */
	public Shark(){
		mId = objtype.tShark;
		init();
	}
	
	/**
	 * Instantiates a new shark.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param dx the drop x position
	 * @param dy the drop y position
	 */
	public Shark(int x, int y, int dx, int dy){
		mId = objtype.tShark;
		init(x, y, dx, dy);
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		//draw different shark images based upon the state and angle
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		switch(getSpriteSheetIndex()){
		case 0:
			if(mSpeed.getAngle() >=270 || mSpeed.getAngle() <=90){
				canvas.scale(-1, 1);
			}
			canvas.drawBitmap(mBitmap, mAnimate.getPortion(), rect, null); break;
		case 1: canvas.drawBitmap(mUpBitmap, mUpAnimate.getPortion(), rect, null); break;
		case 2: canvas.drawBitmap(mDownBitmap, mDownAnimate.getPortion(), rect, null); break;
		case 3: canvas.drawBitmap(mAsleepBitmap, mAsleepAnimate.getPortion(), rect, null); break;
		case 4: 
			if(mSpeed.getAngle() >=270 || mSpeed.getAngle() <=90){
				canvas.scale(-1, 1);
			}
			canvas.drawBitmap(mAttackBitmap, mAttackAnimate.getPortion(), rect, null); break;
		default: break;
		}
		canvas.restore();
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#init()
	 */
	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()),
				new Random().nextInt((int) (getCentreX()+(getCentreX()/4))),
				new Random().nextInt(Constants.getLevel().getLevelHeight()/2));
	}
	
	/**
	 * Inits the shark.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param dx the drop x position
	 * @param dy the drop y position
	 */
	public void init(int x, int y, int dx, int dy) {
		mGraphicType = 4;
		mIsPlaying = false;
		mProperties.init(x, y, 100, 100,0.65f,0.65f);

		mStart = new Point(getCentreX(), getCentreY());
		float dX = dx/Constants.getScreen().getRatio();
		float dY = dy/Constants.getScreen().getRatio();
		mDropLocation = new Point((int)dX,(int)dY);

		mBitmap = SpriteManager.getShark();
		mUpBitmap = SpriteManager.getSharkUp();
		mDownBitmap = SpriteManager.getSharkDown();
		mAsleepBitmap = SpriteManager.getSharkAsleep();
		mAttackBitmap = SpriteManager.getSharkAttack();

		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		mUpAnimate = new Animate(10, 3, 4, mUpBitmap.getWidth(), mUpBitmap.getHeight());
		mDownAnimate = new Animate(29, 8, 4, mDownBitmap.getWidth(), mDownBitmap.getHeight());
		mAsleepAnimate = new Animate(33, 5, 8, mAsleepBitmap.getWidth(), mAsleepBitmap.getHeight());
		mAttackAnimate = new Animate(8, 2, 4, mAttackBitmap.getWidth(), mAttackBitmap.getHeight());
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/4)*(getHeight()/4))));
		mSharkRadius = mProperties.getRadius()*2;

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);

		mSharkState = SharkType.tAsleep;
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#move()
	 */
	@Override
	public boolean move() {
		//update the collision rectangle
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		//move the shark if not asleep or waiting
		if(mSpeed.getMove() && mSharkState != SharkType.tAsleep && mSharkState != SharkType.tWait){
			moveDeltaX((int) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			moveDeltaY((int) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#frame()
	 */
	public void frame(){
		//if time to update direction as long as following set angle based upon angle between duck and shark
		if(updateDirection()){
			if(getSharkState() == SharkType.tFollow){
				setDuckPosition(Constants.getPlayer().getCentreX(),Constants.getPlayer().getCentreY());
			}
		}
		//if got duck move to drop location
		if(getSharkState() == SharkType.tAttack){
			moveToDrop();
		}
		//set position of duck based upon shark
		if(getSharkState() == SharkType.tAttack){
			Constants.getPlayer().setCentre((int)(getCentreX()*Constants.getScreen().getRatio()), (int)(getCentreY()*Constants.getScreen().getRatio()));
			//if at drop location change state for duck and shark
			if(getMovedToDrop()){
				Constants.getPlayer().setSharkAttack(false);
				setSharkState(SharkType.tRetreat);
			}
		}
		//if returning back to start change angle based upon location, then check if at start
		if(getSharkState() == SharkType.tRetreat){
			returnToStart();						
			checkAtStart();
		}
		//if waiting check to see if still need to wait
		if(getSharkState() == SharkType.tWait){
			if(Constants.getPlayer().getInvincibility() == false){
				setSharkState(SharkType.tFollow);
			}
		}
		// Move Objects
		if(move()){
			//increase or decrease speed around a set speed
			float tempSpeed = mSpeed.getSpeed()/Constants.getScreen().getRatio();
			if(tempSpeed<mTopSpeed){
				mSpeed.setSpeed(tempSpeed+0.05f);
			}else{
				mSpeed.setSpeed(mTopSpeed);
			}
			border();
		}
		//animate based upon shark state and angle
		switch(getSpriteSheetIndex()){
		case 0: mAnimate.animateFrame(); break;
		case 1: mUpAnimate.animateFrame(); break;
		case 2: mDownAnimate.animateFrame(); break;
		case 3: mAsleepAnimate.animateFrame(); break;
		case 4: mAttackAnimate.animateFrame(); break;
		default: break;
		}
	}
	
	/**
	 * Gets the shark state.
	 *
	 * @return the shark state
	 */
	public SharkType getSharkState() {
		return mSharkState;
	}
	
	/**
	 * Sets the shark state.
	 *
	 * @param sharkState the new shark state
	 */
	public void setSharkState(SharkType sharkState) {
		mSharkState = sharkState;
	}
	
	/**
	 * Gets the sprite sheet index.
	 *
	 * @return the sprite sheet index based upon angle and state
	 */
	private int getSpriteSheetIndex(){
		if(mSharkState == SharkType.tAsleep){
			return 3;
		}
		if(mSharkState == SharkType.tAttack){
			return 4;
		}
		if (getSpeed().getAngle()>240&&getSpeed().getAngle()<300){
			return 1;
		}
		if (getSpeed().getAngle()>60 && getSpeed().getAngle()<120){
			return 2;
		}
		return 0;
	}
	
	/**
	 * Gets the shark radius.
	 *
	 * @return the shark radius
	 */
	public int getSharkRadius() {
		return mSharkRadius;
	}
	
	/**
	 * Sets the shark radius.
	 *
	 * @param sharkRadius the new shark radius
	 */
	public void setSharkRadius(int sharkRadius) {
		mSharkRadius = sharkRadius;
	}
	
	/**
	 * Calculates the angle between duck and shark.
	 *
	 * @param f the x position of the duck
	 * @param g the g position of the duck
	 */
	public void setDuckPosition(float f, float g){
		mSpeed.setAngle(180+CollisionManager.calcAngle(f, g, getCentreX(), getCentreY()));
	}
	
	/**
	 * Update direction.
	 *
	 * @return true, if not in whirlpool and timer is ready
	 */
	public boolean updateDirection(){
		if(this.getPulledState()!=Constants.STATE_PULLED){
			mDuckCounter++;
			if(mDuckCounter>10){
				mDuckCounter = 0;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check time.
	 *
	 * @return true, if waited long enough before attacking again.
	 */
	public boolean checkTime(){
		mWaitCounter++;
		if(mWaitCounter>= mWaitTime){
			mWaitCounter = 0;
			return true;
		}
		return false;
	}
	
	/**
	 * Return to start.
	 */
	public void returnToStart(){
		//move towards the start location
		mSpeed.setAngle(180+CollisionManager.calcAngle(mStart.getX(), mStart.getY(), getCentreX(), getCentreY()));
	}
	
	/**
	 * Check if at start position.
	 */
	public void checkAtStart(){	
		//check to see if returned to asleep position
		if(CollisionManager.circleCollision(getCentreX(), getCentreY(), 10, mStart.getX(), mStart.getY(), 10)){
			mSharkState = SharkType.tAsleep;
		}
	}
	
	/**
	 * Move to drop location.
	 */
	public void moveToDrop(){
		//move towards the drop location
		mSpeed.setAngle(180+CollisionManager.calcAngle(mDropLocation.getX(), mDropLocation.getY(), getCentreX(), getCentreY()));	
	}
	
	/**
	 * Gets the moved to drop location.
	 *
	 * @return true if at drop location
	 */
	public boolean getMovedToDrop(){
		//check if the shark is with in a certain distance from the drop location
		if(CollisionManager.circleCollision(getCentreX(), getCentreY(), 20, mDropLocation.getX(), mDropLocation.getY(), 20)){
			return true;
		}
		return false;
	}
}
