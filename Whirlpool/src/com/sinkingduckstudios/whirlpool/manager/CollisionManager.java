/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.manager;

import android.util.Log;

import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.movement.Properties;

//collision class
/**
 * The Class CollisionManager.
 */
public class CollisionManager{
	/**
	 * Checks for box collision between two objects.
	 * @param box1 collision properties of the first object.
	 * @param box2 collision properties of the second object.
	 * @return true or false if collided or not
	 */
	static public boolean boxCollision(Properties box1, Properties box2){
		if((box2.getTopLeftX()<box1.getBottomRightX()) && (box2.getBottomRightX()>box1.getTopLeftX())){
			if((box2.getBottomRightY()>box1.getTopLeftY()) && (box2.getTopLeftY()<box1.getBottomRightY())){
				return true;
			}
		}
		return false;
	}
	/**
	 * Checks for box collision between two objects.
	 * @param box1 collision properties of the first object.
	 * @param left left position of the second object.
	 * @param right position of the second object.
	 * @param top top position of the second object.
	 * @param bottom bottom position of the second object.
	 * @return true or false if collided or not
	 */
	static public boolean boxCollision(Properties box1, int left, int right, int top, int bottom){
		if((left<box1.getBottomRightX()) && (right>box1.getTopLeftX())){
			if((bottom>box1.getTopLeftY()) && (top<box1.getBottomRightY())){
				return true;
			}
		}
		return false;
	}
	/**
	 * Rotational Bounding Box Collision.
	 * Only call after a bounding circle check, which is cheaper
	 * @param box1 collision properties of the first object.
	 * @param box2 collision properties of the second object.
	 * @return true or false if collided or not
	 */
	static public boolean boundingBoxCollision(Properties box1, Properties box2){
		double boxAngle = box2.getAngle();
    	//carAngle = 360 - carAngle;
    	double s = Math.sin(boxAngle);
    	double c = Math.cos(boxAngle);
    	float testX, testY;
    	
    	for (int i = 0; i < 4; i++){
    	// translate point back to origin:
    	testX = box1.mCollisionRect[i].getX() - box2.getRealCentre().getX();
    	testY = box1.mCollisionRect[i].getY() - box2.getRealCentre().getY();
    	// rotate point
    	float newX = (int)(testX * c - testY * s);
    	float newY = (int)(testX * s + testY * c);
    	// translate point back:
    	testX = newX + box2.getRealCentre().getX();
    	testY = newY + box2.getRealCentre().getY();
    	
    	if ((testX > box2.getRealTopLeft().getX()) && (testX < box2.getRealBottomRight().getX()))
    		if ((testY > box2.getRealTopLeft().getY()) && (testY < box2.getRealBottomRight().getY()))
    			return true;
    	}
    	return false;
	}
	//circle collision - graphic is the sprite being tested, event is the touch
	/**
	 * 
	 * @param box1
	 * @param box2
	 * @return
	 */
	static public boolean circleCollision(Properties box1, Properties box2){
		float distanceX = box1.getCentreX() - box2.getCentreX();
		float distanceY = box1.getCentreY() - box2.getCentreY();
		
		float radius = box1.getRadius() + box2.getRadius();
		float magnatiude = (distanceX*distanceX) + (distanceY*distanceY);
		if(magnatiude< radius*radius){
			return true;
		}
		return false;
	}
	//circle collision - graphic is the sprite being tested, event is the touch
	/**
	 * 
	 * @param box1
	 * @param x
	 * @param y
	 * @param r
	 * @return
	 */
	static public boolean circleCollision(Properties box1, int x, int y, int r){
		float distanceX = box1.getCentreX() - x;
		float distanceY = box1.getCentreY() - y;
		
		float radius = box1.getRadius() + r;
		float magnatiude = (distanceX*distanceX) + (distanceY*distanceY);
		if(magnatiude< radius*radius){
			return true;
		}
		return false;
	}
	//circle collision - graphic is the sprite being tested, event is the touch
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param r1
	 * @param x2
	 * @param y2
	 * @param r2
	 * @return
	 */
	static public boolean circleCollision(float x1, float y1, float r1, float x2, float y2, float r2){
		float distanceX = x1 - x2;
		float distanceY = y1 - y2;
		
		float radius = r1 + r2;
		float magnatiude = (distanceX*distanceX) + (distanceY*distanceY);
		if(magnatiude< radius*radius){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param num
	 * @param divide
	 * @return
	 */
	static float fMod(float num, int divide){
		float fresult = (float) (num - Math.floor(num));
		return ((float)((int)num%divide) + fresult);
	}
	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	static public float calcAngle(float x1, float y1, float x2, float y2){
		float angle1;
		if(x2-x1 == 0) {
			angle1 = 90.0f;
			Log.e("stupid angle error","remove");
		}
		else angle1 = (float) ((float)(180.0f/Math.PI)*Math.atan((y2 - y1)/(x2 - x1)));
		if(x2 < x1 && y2 < y1){
			angle1 += 180.0f;
		}
		else if(x2 < x1 && !(y2 < y1)){
			angle1 = 180.0f - fMod(Math.abs(angle1), 90);
		}
		else if(!(x2 < x1) && y2 < y1){
			angle1 = 360.0f - fMod(Math.abs(angle1), 90);
		}
		return fMod((fMod((angle1), 360)+360), 360);
	}
	/**
	 * 
	 * @param box1
	 * @param angle
	 */
	static public void updateCollisionRect(Properties box1, float angle){
		box1.updateOriginal();
		box1.updateAngle(angle);
		for(int i = 0; i<4; i++){
			box1.mCollisionRect[i].setPoints(box1.mOriginalRect[i].getX(), box1.mOriginalRect[i].getY());
			RotatePoint(box1.getCentre(),angle,box1.mCollisionRect[i]);
		}
	}
	/**
	 * 
	 * @param centre
	 * @param angle
	 * @param point
	 */
	private static void RotatePoint(Point centre, float angle, Point point){
		float sine = (float) Math.sin(angle);
		float cosine = (float) Math.cos(angle);
		
		point.setX(point.getX()-(centre.getX()*Constants.getScreen().getRatio()));
		point.setY(point.getY()-(centre.getY()*Constants.getScreen().getRatio()));
		
		float newX = (point.getX() * cosine) - (point.getY() * sine);
		float newY = (point.getX() * sine) + (point.getY() * cosine);
		
		point.setX((newX+(centre.getX()*Constants.getScreen().getRatio()))/Constants.getScreen().getRatio());
		point.setY((newY+(centre.getY()*Constants.getScreen().getRatio()))/Constants.getScreen().getRatio());
	}
}
