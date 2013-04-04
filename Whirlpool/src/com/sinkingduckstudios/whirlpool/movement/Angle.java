/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.movement;

import java.lang.Math;

class Angle{
	private float mAngle = 0;
	Angle(float a){
		mAngle = a;
	}
	public float getAngle(){
		return mAngle;
	}
	public float getAngleRad(){
		return (float) (mAngle*Math.PI/180);
	}
	public void setAngle(float angle){
		mAngle = angle;
		checkAngle();
	}
	public void shiftAngle(float angle){
		mAngle += angle;
		checkAngle();
	}
	private void checkAngle(){		//Makes sure it's always within 0-360
		if(mAngle >= 360.0f || mAngle < 0.0f){
			mAngle = ((mAngle%360)+360)%360;
		}
	}
}