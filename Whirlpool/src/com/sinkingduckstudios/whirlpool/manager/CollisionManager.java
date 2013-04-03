/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.manager;

import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.movement.Properties;


//collision class
public class CollisionManager{
	static public boolean boxCollision(Properties box1, Properties box2){
		if((box2.getTopLeftX()<box1.getBottomRightX()) && (box2.getBottomRightX()>box1.getTopLeftX())){
			if((box2.getBottomRightY()>box1.getTopLeftY()) && (box2.getTopLeftY()<box1.getBottomRightY())){
				return true;
			}
		}
		return false;
	}
	static public boolean boxCollision(Properties box1, int left, int right, int top, int bottom){
		if((left<box1.getBottomRightX()) && (right>box1.getTopLeftX())){
			if((bottom>box1.getTopLeftY()) && (top<box1.getBottomRightY())){
				return true;
			}
		}
		return false;
	}
	//circle collision - graphic is the sprite being tested, event is the touch
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
	static float fMod(float num, int divide){
		float fresult = (float) (num - Math.floor(num));
		return ((float)((int)num%divide) + fresult);
	}
	static public float calcAngle(float x1, float y1, float x2, float y2){
		float angle1;
		if(x2-x1 == 0) angle1 = 90.0f;
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
	static public void updateCollisionRect(Properties box1, float angle){
		float sAngle = (float) Math.sin(angle);
		float cAngle = (float) Math.cos(angle);
		Point topleft = box1.mOriginalRect[0];
		Point topright = box1.mOriginalRect[1];
		Point bottomleft = box1.mOriginalRect[2];
		Point bottomright = box1.mOriginalRect[3];
		float aboutX = box1.getCentreX();
		float aboutY = box1.getCentreY();
		box1.mCollisionRect[0].setX((topleft.getX()*cAngle) - (topleft.getY()*sAngle) +((aboutX*(1-cAngle))+(aboutY*sAngle)));
		box1.mCollisionRect[1].setX((topright.getX()*cAngle) - (topright.getY()*sAngle) +((aboutX*(1-cAngle))+(aboutY*sAngle)));
		box1.mCollisionRect[2].setX((bottomleft.getX()*cAngle) - (bottomleft.getY()*sAngle) +((aboutX*(1-cAngle))+(aboutY*sAngle)));
		box1.mCollisionRect[3].setX((bottomright.getX()*cAngle) - (bottomright.getY()*sAngle) +((aboutX*(1-cAngle))+(aboutY*sAngle)));
		
		box1.mCollisionRect[0].setY((topleft.getX()*sAngle) + (topleft.getY()*cAngle) +((aboutY*(1-cAngle))-(aboutX*sAngle)));
		box1.mCollisionRect[1].setY((topright.getX()*sAngle) + (topright.getY()*cAngle) +((aboutY*(1-cAngle))-(aboutX*sAngle)));
		box1.mCollisionRect[2].setY((bottomleft.getX()*sAngle) + (bottomleft.getY()*cAngle) +((aboutY*(1-cAngle))-(aboutX*sAngle)));
		box1.mCollisionRect[3].setY((bottomright.getX()*sAngle) + (bottomright.getY()*cAngle) +((aboutY*(1-cAngle))-(aboutX*sAngle)));
	}
}
