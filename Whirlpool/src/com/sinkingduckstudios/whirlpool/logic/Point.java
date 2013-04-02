/*
 * Author: Jake Morey
 * Last Updated: 25/02/2013
 * Content:
 * A small class for the defining points on the screen.
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

public class Point{
	private float mX = 0.0f;
	private float mY = 0.0f;
	public Point(){
		
	}
	public Point(float x, float y){
		mX = x;
		mY = y;
	}
	public void setPoints(float x, float y){
		mX = x;
		mY = y;
	}
	public float getX(){
		return mX;
	}
	public float getY(){
		return mY;
	}
	public void setX(float x){
		mX = x;
	}
	public void setY(float y){
		mY = y;
	}
}
