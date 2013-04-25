/*
 * Author: Jake Morey, Jordan 'Hare
 * Content:
 * Jordan O'Hare: Added basic functions that are inherited from parent class.
 * Jake Morey: Added all functionality for boat building upon the parent class.
 */
package com.sinkingduckstudios.whirlpool.objects;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

/**
 * The Class Boat.
 */
public class Boat extends GraphicObject{

	/**
	 * The Enum BoatType.
	 */
	private enum BoatType{ 
		bDefault, bReady, bAttack, bTorpedo, bFinishing, bWaiting, bBroken;
	}
	/** The boat state. */
	private BoatType mBoatState = BoatType.bDefault;
	/** The boat radius. */
	private int mBoatRadius = Constants.getLevel().getLevelHeight()/2;
	/** The torpedo count. */
	private int mTorpedoCount = -1;
	/** The broken boat value. */
	private boolean mBroken = false;
	/**
	 * Instantiates a new boat.
	 */
	public Boat(){
		mId = objtype.tBoat;
		init();
	}
	/**
	 * Instantiates a new boat.
	 *
	 * @param x the x position
	 * @param y the y position
	 */
	public Boat(int x, int y){
		mId = objtype.tBoat;
		init(x, y);
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect, null);
		canvas.restore();
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#init()
	 */
	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()/4));
	}

	/**
	 * Inits the boat with a set X,Y components.
	 *
	 * @param x the x position
	 * @param y the y position
	 */
	public void init(int x, int y) {
		mGraphicType = 3;
		mIsPlaying = false;

		mBoatState = BoatType.bReady;
		//set the position so that it is the centre of the image
		x-=((96/Constants.getScreen().getRatio())/2);
		y-=((96/Constants.getScreen().getRatio())/2);
		mProperties.init(x, y, 96, 96,0.9f,0.4f,0.5f,0.65f);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/4)*(getHeight()/4))));
		mBitmap = SpriteManager.getBoat();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#move()
	 */
	@Override
	public boolean move() {
		if(mSpeed.getMove()){
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
		// Move Objects
		if(move()){
			border();
		}
		//if not broken then depending on boat type do different things
		if(checkBroken() == false){
			incrementCounter();
			//if in attack mode and the frame number is right set the mode so a torpedo can be created later
			if(mBoatState == BoatType.bAttack && mAnimate.getNoOfFrames()>=44){
				mBoatState = BoatType.bTorpedo;
			}else if(mBoatState == BoatType.bFinishing && mAnimate.getFinished()){	
				//if animation has finished after firing the torpedo set back to original animation.
				mBitmap = SpriteManager.getBoat();
				mAnimate.Reset(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight(),3);
				mBoatState = BoatType.bWaiting;	
			}
		}
		mAnimate.animateFrame();
	}

	/**
	 * Gets the boat radius.
	 *
	 * @return the boat radius
	 */
	public int getBoatRadius() {
		return mBoatRadius;
	}

	/**
	 * Sets the boat radius.
	 *
	 * @param boatRadius the new boat radius
	 */
	public void setBoatRadius(int boatRadius) {
		mBoatRadius = boatRadius;
	}

	/**
	 * Gets the torpedo count.
	 *
	 * @return the torpedo count
	 */
	public int getTorpedoCount() {
		return mTorpedoCount;
	}

	/**
	 * Sets the torpedo count.
	 *
	 * @param torpedoCount the new torpedo count
	 */
	public void setTorpedoCount(int torpedoCount) {
		mTorpedoCount = torpedoCount;
	}

	/**
	 * Increment counter for waiting to fire new torpedo.
	 */
	public void incrementCounter(){
		//if just sent a torpedo then wait before being able to send another one
		if((mBoatState == BoatType.bWaiting) && (++mTorpedoCount>=0)){
			if(mTorpedoCount == 120){
				mBoatState = BoatType.bReady;
				mTorpedoCount = -1;
			}
		}
	}

	/**
	 * Gets if there is a new torpedo to fire.
	 *
	 * @return true, if can fire a new torpedo
	 */
	public boolean getNewTorpedo() {
		//if called then a torpedo will be created and set the type to be finished
		if(mBoatState == BoatType.bTorpedo){
			mBoatState = BoatType.bFinishing;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Change animation.
	 */
	public void changeAnimation(){
		//change animation and bitmap to attack images 
		if(mBoatState == BoatType.bReady && mBroken == false){
			mBitmap= SpriteManager.getBoatAttack();
			mAnimate.Reset(56, 7, 8, mBitmap.getWidth(), mBitmap.getHeight(),1);
			mBoatState = BoatType.bAttack;
		}
	}

	/**
	 * Gets the broken variable.
	 *
	 * @return the broken value
	 */
	public boolean getBroken() {
		return mBroken;
	}

	/**
	 * Sets the broken variable.
	 * If true change animation to destroyed boat image.
	 * @param broken the new broken value
	 */
	public void setBroken(boolean broken) {
		mBroken = broken;
		if(mBroken == true){
			mBoatState = BoatType.bBroken;
			mBitmap = SpriteManager.getDestroyBoat();
			mAnimate.Reset(25, 4, 8, mBitmap.getWidth(), mBitmap.getHeight(),3);
		}
	}

	/**
	 * Check broken.
	 *
	 * @return true, if broken 
	 */
	public boolean checkBroken(){
		if(mBroken == true && mBoatState == BoatType.bBroken){
			//if broken check that animation has finished if so then set boat back to default
			if(mAnimate.getFinished()== true){
				mBoatState = BoatType.bReady;
				mTorpedoCount = -1;
				mBroken = false;				
			}
			return true;
		}else{
			return false;
		}
	}

}
