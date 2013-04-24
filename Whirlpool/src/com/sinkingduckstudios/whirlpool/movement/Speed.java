/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.movement;

import com.sinkingduckstudios.whirlpool.logic.Constants;

/**
 * The Class Speed.
 */
public class Speed {
	/** The move. */
	private boolean mMove = true;
	/** The speed. */
	private float mSpeed = 0;
	/** The angle. */
	private Angle mAngle = new Angle(0);
	/** The last angle. */
	private Angle mLastAngle = new Angle(0);
	/**
	 * Horizontal bounce.
	 */
	public void horizontalBounce(){
		float angletemp = mAngle.getAngle();
		switch((int)(angletemp/90)){
			case 0:
			case 2:	mAngle.shiftAngle(2*(90 - (int)(angletemp%90)));
					break;
			case 1:
			case 3:	mAngle.shiftAngle(-2*((int)(angletemp%90)));
					break;
		}
		mLastAngle.setAngle(mAngle.getAngle());
	}
	/**
	 * Vertical bounce.
	 */
	public void verticalBounce(){
		float angletemp = mAngle.getAngle();
		switch((int)(angletemp/90)){
			case 0:
			case 2:	mAngle.shiftAngle(-2*((int)(angletemp%90)));
					break;
			case 1:
			case 3:	mAngle.shiftAngle(2*(90 - (int)(angletemp%90)));
					break;
		}
	}
	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public float getSpeed(){
		return mSpeed;
	}
	/**
	 * Gets the x speed.
	 *
	 * @return the x speed
	 */
	public float getXSpeed(){
		return (float) (mSpeed*Math.cos(mAngle.getAngleRad()));
	}
	/**
	 * Gets the y speed.
	 *
	 * @return the y speed
	 */
	public float getYSpeed(){
		return (float) (mSpeed*Math.sin(mAngle.getAngleRad()));
	}
	/**
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	public void setSpeed(float speed){
		mSpeed = (speed*Constants.getScreen().getRatio());
	}
	/**
	 * Shift speed.
	 *
	 * @param speed adds the speed
	 */
	public void shiftSpeed(float speed){
		mSpeed += speed;
	}
	/**
	 * Gets the move.
	 *
	 * @return the move
	 */
	public boolean getMove(){
		return mMove;
	}
	/**
	 * Sets the move.
	 *
	 * @param move the new move
	 */
	public void setMove(boolean move){
		mMove = move;
	}
	/**
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle(){
		return mAngle.getAngle();
	}
	/**
	 * Gets the last angle.
	 *
	 * @return the last angle
	 */
	public float getLastAngle(){
		return mLastAngle.getAngle();
	}
	/**
	 * Gets the angle in rads.
	 *
	 * @return the angle in rads
	 */
	public float getAngleRad(){
		return mAngle.getAngleRad();
	}
	/**
	 * Sets the angle.
	 *
	 * @param angle the new angle
	 */
	public void setAngle(float angle){
		mLastAngle.setAngle(mAngle.getAngle());
		mAngle.setAngle(angle);
	}
	/**
	 * Shift angle.
	 *
	 * @param angle the angle
	 */
	public void shiftAngle(float angle){
		mAngle.shiftAngle(angle);
	}
}