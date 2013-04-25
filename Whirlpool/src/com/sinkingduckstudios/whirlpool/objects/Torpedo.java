/*
 * Author: Jake Morey, Fraser
 * Content:
 * Jake Morey: based upon the parent class with added functionality for following the duck
 * Fraser: added checking distance between missile and duck, and whilrpool code
 */
package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

/**
 * The Class Torpedo.
 */
public class Torpedo extends GraphicObject {
	
	/** Is torpedo ready to destroy. */
	private boolean mIsReadyToDestroy = false;
	/** The Constant mTopSpeed. */
	private static final float mTopSpeed = 12*Constants.getScreen().getRatio();
	/** The duck counter. */
	private int mDuckCounter = 10;
	/** The hit boat variable. */
	private boolean mHitBoat = false;
	/** The hit boat counter. */
	private int mHitBoatCounter = 0;
	/** The beep counter. */
	private int mBeepCounter = 31;
	/** The torpedo is tracking. */
	private boolean mIsTracking; //tracking duck?
	/** The explosion bitmap. */
	private Bitmap mExplosionBitmap;
	/** The explosion animation. */
	private Animate mExplosionAnimate;
	/** The explosion variable. */
	private boolean mExplosion = false;

	/**
	 * Instantiates a new torpedo.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param angle the angle of the torpedo
	 */
	public Torpedo(int x, int y, float angle){
		mId= objtype.tTorpedo;
		init(x, y, angle);
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.rotate(mSpeed.getAngle()+180);
		//if torpedo is exploding show exploding image 
		if(mExplosion){
			canvas.drawBitmap(mExplosionBitmap, mExplosionAnimate.getPortion(), rect, null);
		}else{
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect, null);
		}
		canvas.restore();
	}

	/**
	 * Inits the torpedo.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param angle the angle of the torpedo
	 */
	public void init(int x, int y, float angle){
		mProperties.init(x, y, 50, 50,0.5f,0.5f,0.35f,0.5f);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));

		mBitmap = SpriteManager.getTorpedo();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mExplosionBitmap = SpriteManager.getTorpedoExplosion();
		mExplosionAnimate = new Animate(11, 3, 4, mExplosionBitmap.getWidth(), mExplosionBitmap.getHeight());

		mSpeed.setMove(true);
		mSpeed.setAngle(angle);
		mSpeed.setSpeed(mId.tSpeed);
		mIsTracking=true;
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#init()
	 */
	@Override
	public void init(){
		init(30,60,mId.tAngle);
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#move()
	 */
	@Override
	public boolean move() {
		//update colision rectangle
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		//make sure it cant exploding instantly by hitting the boat
		if(mHitBoat == false && ++mHitBoatCounter > 40){
			mHitBoatCounter = 0;
			mHitBoat = true;
		}
		if(mSpeed.getMove()){
			moveDeltaX((int) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			moveDeltaY((int) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#borderCollision(com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide, int, int)
	 */
	@Override
	public void borderCollision(ScreenSide side, int width, int height) {
		//if hit border set hit to true
		boolean hit = false;
		switch(side){
		case Top:
			hit = true;
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		case Bottom:
			hit = true;
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case Left:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(-getTopLeftX());
			break;
		case Right:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			break;
		case BottomLeft:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(-getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case BottomRight:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case TopLeft:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(-getTopLeftX());
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		case TopRight:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		default:
			break;
		}
		//if already been in a whirlpool and hit a border then start explosion image
		if(hit == true && mIsTracking == false){
			mExplosion = true;
		}
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#frame()
	 */
	@Override
	public void frame() {
		//if not exploding then move
		if(mExplosion == false){
			if(move()){
				border();
			}
		}
		//play explosion animation until the animation has finished
		if(mExplosion){
			if(mExplosionAnimate.getFinished() == false){
				mExplosionAnimate.animateFrame();
			}else{
				mIsReadyToDestroy = true;
			}
		}else{
			mAnimate.animateFrame();
		}
	}

	/**
	 * Gets the checks if is ready to destroy.
	 *
	 * @return the checks if is ready to destroy
	 */
	public boolean getIsReadyToDestroy() {
		return mIsReadyToDestroy;
	}

	/**
	 * Sets the checks if is ready to destroy.
	 *
	 * @param isReadyToDestroy the new checks if is ready to destroy
	 */
	public void setIsReadyToDestroy(boolean isReadyToDestroy) {
		mIsReadyToDestroy = isReadyToDestroy;
	}
	
	/**
	 * Gets the tracking.
	 *
	 * @return the tracking
	 */
	public boolean getTracking(){
		return mIsTracking;
	}
	
	/**
	 * Sets the tracking.
	 *
	 * @param b the new tracking
	 */
	public void setTracking(boolean b){
		mIsTracking=b;
	}
	
	/**
	 * Uses duck position to calculate angle.
	 *
	 * @param f the x position of the duck
	 * @param g the y position of the duck
	 */
	public void setDuckPosition(float f, float g){
		//update angle based upon duck position, also increase speed
		mSpeed.setAngle(180+CollisionManager.calcAngle(f, g, getCentreX(), getCentreY()));
		float tempSpeed = mSpeed.getSpeed();
		if(tempSpeed<mTopSpeed){
			mSpeed.setSpeed(tempSpeed+1);
		}else{
			mSpeed.setSpeed(mTopSpeed);
		}
	}
	/**
	 * Gets the duck counter.
	 *
	 * @return the duck counter
	 */
	public int getDuckCounter() {
		return mDuckCounter;
	}
	
	/**
	 * Sets the duck counter.
	 *
	 * @param duckCounter the new duck counter
	 */
	public void setDuckCounter(int duckCounter) {
		mDuckCounter = duckCounter;
	}
	
	/**
	 * Update direction.
	 *
	 * @return true, successful if counter is ready
	 */
	public boolean updateDirection(){
		if(mIsTracking){
			mDuckCounter++;
			if(mDuckCounter>10){
				mDuckCounter = 0;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check beep.
	 */
	public void checkBeep(){
		//check if missile can play sound
		if(mExplosion == false){
			mBeepCounter++;
			if(mBeepCounter>30){
				mBeepCounter = 0;
				Constants.getSoundManager().playBeepFast();
			}
		}
	}
	
	/**
	 * Gets the hit boat.
	 *
	 * @return the hit boat
	 */
	public boolean getHitBoat() {
		return mHitBoat;
	}
	
	/**
	 * Sets the hit boat.
	 *
	 * @param hitBoat the new hit boat
	 */
	public void setHitBoat(boolean hitBoat) {
		mHitBoat = hitBoat;
	}
	
	/**
	 * Gets the top speed.
	 *
	 * @return the top speed
	 */
	public float getTopSpeed() {
		return mTopSpeed;
	}
	
	/**
	 * Gets the explosion.
	 *
	 * @return the explosion
	 */
	public boolean getExplosion() {
		return mExplosion;
	}
	
	/**
	 * Sets the explosion.
	 *
	 * @param explosion the new explosion
	 */
	public void setExplosion(boolean explosion) {
		mExplosion = explosion;
	}
	
	/**
	 * Gets the distance between missile and duck.
	 *
	 * @return the distance
	 */
	public float getDist() {
		float x1 = getCentreX();
		float y1 = getCentreY();
		float x2 = Constants.getPlayer().getCentreX();
		float y2 = Constants.getPlayer().getCentreY();

		float distX = x2-x1;
		float distY = y2-y1;

		return (distX*distX)+(distY*distY);

	}
}
