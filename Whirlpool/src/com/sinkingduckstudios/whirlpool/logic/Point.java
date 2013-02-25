/*
 * Author: Jake Morey
 * Last Updated: 25/02/2013
 * Content:
 * A small class for the defining points on the screen.
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

public class Point{
	private int mX = 0;
	private int mY = 0;
	public Point(){
		
	}
	public Point(int x, int y){
		mX = x;
		mY = y;
	}
	public void setPoints(int x, int y){
		mX = x;
		mY = y;
	}
	public int getX(){
		return mX;
	}
	public int getY(){
		return mY;
	}
	public void setX(int x){
		mX = x;
	}
	public void setY(int y){
		mY = y;
	}
}
