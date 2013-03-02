/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.movement;

import com.sinkingduckstudios.whirlpool.logic.Constants;

public class Speed {
	//variables
	private boolean mMove = true;
	private float mSpeed = 0;
	private Angle mAngle = new Angle(0);
	private Angle mLastAngle = new Angle(0);
	//bounce functions
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
	//getters and setters for speed
	public float getSpeed(){
		return mSpeed;
	}
	public float getXSpeed(){
		return (float) (mSpeed*Math.cos(mAngle.getAngleRad()));
	}
	public float getYSpeed(){
		return (float) (mSpeed*Math.sin(mAngle.getAngleRad()));
	}
	public void setSpeed(float angle){
		mSpeed = (angle*Constants.getScreen().getRatio());
	}
	public void shiftSpeed(float angle){
		mSpeed += angle;
	}
	//getters and setters for move
	public boolean getMove(){
		return mMove;
	}
	public void setMove(boolean move){
		mMove = move;
	}
	//getters and setters for angle
	public float getAngle(){
		return mAngle.getAngle();
	}
	public float getLastAngle(){
		return mLastAngle.getAngle();
	}
	public float getAngleRad(){
		return mAngle.getAngleRad();
	}
	public void setAngle(float angle){
		mLastAngle.setAngle(mAngle.getAngle());
		mAngle.setAngle(angle);
	}
	public void shiftAngle(float angle){
		mAngle.shiftAngle(angle);
	}
}