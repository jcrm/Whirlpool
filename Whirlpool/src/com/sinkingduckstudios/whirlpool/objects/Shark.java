/*
 * Author:
 * Last Updated:
 * Content:
 *
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

// TODO: Auto-generated Javadoc
/**
 * The Class Shark.
 */
public class Shark extends GraphicObject{
	
	/**
	 * The Enum SharkType.
	 */
	public enum SharkType{
		
		/** The t default. */
		tDefault, 
 /** The t asleep. */
 tAsleep, 
 /** The t follow. */
 tFollow, 
 /** The t attack. */
 tAttack, 
 /** The t retreat. */
 tRetreat, 
 /** The t wait. */
 tWait;
	}
	
	/** The Constant mTopSpeed. */
	private static final float mTopSpeed = 8;
	
	/** The m shark state. */
	private SharkType mSharkState = SharkType.tDefault;
	
	/** The m up bitmap. */
	private Bitmap mUpBitmap;
	
	/** The m down bitmap. */
	private Bitmap mDownBitmap;
	
	/** The m asleep bitmap. */
	private Bitmap mAsleepBitmap;
	
	/** The m attack bitmap. */
	private Bitmap mAttackBitmap;
	
	/** The m up animate. */
	private Animate mUpAnimate;
	
	/** The m down animate. */
	private Animate mDownAnimate;
	
	/** The m attack animate. */
	private Animate mAttackAnimate;
	
	/** The m asleep animate. */
	private Animate mAsleepAnimate;
	
	/** The m shark radius. */
	private int mSharkRadius = Constants.getLevel().getLevelHeight()/2;
	
	/** The m duck counter. */
	private int mDuckCounter = 10;
	
	/** The m wait counter. */
	private int mWaitCounter = 40;
	
	/** The m wait time. */
	private int mWaitTime = 40;
	
	/** The m start. */
	private Point mStart;
	
	/** The m drop location. */
	private Point mDropLocation;

	public Shark(){
		mId = objtype.tShark;
		init();
	}
	public Shark(int x, int y, int dx, int dy){
		mId = objtype.tShark;
		init(x, y, dx, dy);
	}
	@Override
	public void draw(Canvas canvas) {
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

	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()),
				new Random().nextInt((int) (getCentreX()+(getCentreX()/4))),
				new Random().nextInt(Constants.getLevel().getLevelHeight()/2));
	}
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
	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		if(mSpeed.getMove() && mSharkState != SharkType.tAsleep && mSharkState != SharkType.tWait){
			moveDeltaX((int) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			moveDeltaY((int) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}
	
	public void frame(){
		if(updateDirection()){
			if(getSharkState() == SharkType.tFollow){
				setDuckPosition(Constants.getPlayer().getCentreX(),Constants.getPlayer().getCentreY());
			}
		}
		if(getSharkState() == SharkType.tAttack){
			moveToDrop();
		}
		if(getSharkState() == SharkType.tAttack){
			Constants.getPlayer().setCentre((int)(getCentreX()*Constants.getScreen().getRatio()), (int)(getCentreY()*Constants.getScreen().getRatio()));
			if(getMovedToDrop()){
				Constants.getPlayer().setSharkAttack(false);
				setSharkState(SharkType.tRetreat);
			}
		}
		if(getSharkState() == SharkType.tRetreat){
			returnToStart();						
			checkAtStart();
		}
		if(getSharkState() == SharkType.tWait){
			if(Constants.getPlayer().getInvincibility() == false){
				setSharkState(SharkType.tFollow);
			}
		}
		// Move Objects
		if(move()){
			float tempSpeed = mSpeed.getSpeed()/Constants.getScreen().getRatio();
			if(tempSpeed<mTopSpeed){
				mSpeed.setSpeed(tempSpeed+0.05f);
			}else{
				mSpeed.setSpeed(mTopSpeed);
			}
			border();
		}
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
	 * @return the sprite sheet index
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
	public void setDuckPosition(float f, float g){
		mSpeed.setAngle(180+CollisionManager.calcAngle(f, g, getCentreX(), getCentreY()));
	}
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
	public boolean checkTime(){
		mWaitCounter++;
		if(mWaitCounter>= mWaitTime){
			mWaitCounter = 0;
			return true;
		}
		return false;
	}
	public void returnToStart(){
		mSpeed.setAngle(180+CollisionManager.calcAngle(mStart.getX(), mStart.getY(), getCentreX(), getCentreY()));
	}
	public void checkAtStart(){	
		if(CollisionManager.circleCollision(getCentreX(), getCentreY(), 10, mStart.getX(), mStart.getY(), 10)){
			mSharkState = SharkType.tAsleep;
		}
	}
	public void moveToDrop(){
		mSpeed.setAngle(180+CollisionManager.calcAngle(mDropLocation.getX(), mDropLocation.getY(), getCentreX(), getCentreY()));	
	}
	
	/**
	 * Gets the moved to drop.
	 *
	 * @return the moved to drop
	 */
	public boolean getMovedToDrop(){
		if(CollisionManager.circleCollision(getCentreX(), getCentreY(), 20, mDropLocation.getX(), mDropLocation.getY(), 20)){
			return true;
		}
		return false;
	}
}
