/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

public class Screen {
	public enum ScreenSide{
		Top, Bottom, Left, Right, TopLeft, TopRight, BottomLeft, BottomRight;
	}
	
	//variables
	private int mWidth;
	private int mHeight;
	private float mRatio;
	private Point mCentre = new Point();
	
	public Screen(){
	}
	public Screen(int width, int height){
		set(width, height);
	}
	//getters and setter for width and height
	public int getWidth(){
		return mWidth;
	}
	public int getHeight(){
		return mHeight;
	}
	public float getRatio(){
		return mRatio;
	}
	public void set(int width, int height){
		mWidth = width;
		mHeight = height;
		//Default height for Screen is 500 units. Scale to this.
		mRatio = 500.0f/mHeight;
		mCentre.setPoints(width/2, height/2);
	}
	//getters for x and y components
	public float getCentreX(){
		return mCentre.getX();
	}
	public float getCentreY(){
		return mCentre.getY();
	}
}
