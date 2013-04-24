/*
 * Author: Jake Morey
 * Last Updated: 25/02/2013
 * Content:
 * A small class for the defining points on the screen.
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

public class Point{
	//x and y components of a point.
	private float mX = 0.0f;
	private float mY = 0.0f;
	//constructors
	public Point(){
		
	}
	public Point(float x, float y){
		mX = x;
		mY = y;
	}
	//setters
	public void setPoints(float x, float y){
		mX = x;
		mY = y;
	}
	public void setX(float x){
		mX = x;
	}
	public void setY(float y){
		mY = y;
	}
	//getters
	public float getX(){
		return mX;
	}
	public float getY(){
		return mY;
	}
}
