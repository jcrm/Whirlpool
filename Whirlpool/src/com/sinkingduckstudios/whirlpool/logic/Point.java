/*
 * Author: Jake Morey
 * Last Updated: 25/02/2013
 * Content:
 * A small class for the defining points on the screen.
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

// TODO: Auto-generated Javadoc
/**
 * The Class Point.
 */
public class Point{
	//x and y components of a point.
	/** The m x. */
	private float mX = 0.0f;
	
	/** The m y. */
	private float mY = 0.0f;
	
	/**
	 * Instantiates a new point.
	 */
	public Point(){
		
	}
	
	/**
	 * Initialises the Point with the parameters.
	 * @param x component.
	 * @param y component.
	 */
	public Point(float x, float y){
		mX = x;
		mY = y;
	}
	/**
	 * Initialises the Point with the parameters.
	 * @param x component.
	 * @param y component.
	 */
	public void setPoints(float x, float y){
		mX = x;
		mY = y;
	}
	/**
	 * Initialises the X component with the parameter.
	 * @param x component.
	 */
	public void setX(float x){
		mX = x;
	}
	/**
	 * Initialises the Y component with the parameter.
	 * @param y component.
	 */
	public void setY(float y){
		mY = y;
	}
	/**
	 * Returns X component.
	 * @return x component.
	 */
	public float getX(){
		return mX;
	}
	/**
	 * Returns Y component.
	 * @return y component.
	 */
	public float getY(){
		return mY;
	}
}
