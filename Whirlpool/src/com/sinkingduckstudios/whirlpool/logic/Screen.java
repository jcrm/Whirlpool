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
	private Point mCentre = new Point();
	
	Screen(){
	}
	//getters and setter for width and height
	public int getWidth(){
		return mWidth;
	}
	public int getHeight(){
		return mHeight;
	}
	public void set(int width, int height){
		mWidth = width;
		mHeight = height;
		mCentre.setPoints(width/2, height/2);
	}
	//getters for x and y components
	public int getCentreX(){
		return mCentre.getX();
	}
	public int getCentreY(){
		return mCentre.getY();
	}
}
