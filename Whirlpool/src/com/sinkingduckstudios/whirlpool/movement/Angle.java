/*
 * Author: Jordan O'Hare
 * Content:
 * stores the angle and object.
 * 
 */
package com.sinkingduckstudios.whirlpool.movement;

import java.lang.Math;

/**
 * The Class Angle.
 */
class Angle{
	
	/** The angle. */
	private float mAngle = 0;
	
	/**
	 * Instantiates a new angle.
	 *
	 * @param a the local angle
	 */
	Angle(float a){
		mAngle = a;
	}
	
	/**
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle(){
		return mAngle;
	}
	
	/**
	 * Gets the angle in rads.
	 *
	 * @return the angle in rads
	 */
	public float getAngleRad(){
		return (float) (mAngle*Math.PI/180);
	}
	
	/**
	 * Sets the angle.
	 * Checks with in range.
	 * @param angle the new angle
	 */
	public void setAngle(float angle){
		mAngle = angle;
		checkAngle();
	}
	
	/**
	 * Shift angle.
	 * Checks with in range.
	 * @param angle the angle
	 */
	public void shiftAngle(float angle){
		mAngle += angle;
		checkAngle();
	}
	
	/**
	 * Check angle between 0 and 360.
	 */
	private void checkAngle(){		//Makes sure it's always within 0-360
		if(mAngle >= 360.0f || mAngle < 0.0f){
			mAngle = ((mAngle%360)+360)%360;
		}
	}
}