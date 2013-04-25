/*
 * Authors: Jordan O'Hare, Fraser Tomison , Jake Morey
 * Content:
 * Jake Morey: added code for corners.
 * Used to store size of the screen. Also stores the different sides.
 * Corners are also included due to collision conflicts.
 * Fraser Tomison: added ratio code.
 */
package com.sinkingduckstudios.whirlpool.logic;

/**
 * The Class Screen.
 */
public class Screen {
	
	/**
	 * The Enum ScreenSide.
	 */
	public enum ScreenSide{
		Top, Bottom, Left, Right, TopLeft, TopRight, BottomLeft, BottomRight;
	}

	/** The width of the screen. */
	private int mWidth;
	/** The height of the screen. */
	private int mHeight;
	/** The ratio. */
	private float mRatio;
	/** The centre of the screen. */
	private Point mCentre = new Point();
	
	public Screen(){
	}
	/**
	 * Initialises the Screen with the parameters.
	 * @param width value
	 * @param height value
	 */
	public Screen(int width, int height){
		set(width, height);
	}
	/**
	 * Returns width of the screen.
	 * @return screen width.
	 */
	public int getWidth(){
		return mWidth;
	}
	/**
	 * Returns height of the screen.
	 * @return screen height.
	 */
	public int getHeight(){
		return mHeight;
	}
	/**
	 * Returns the screen ratio. 
	 * @return ratio of the screen height.
	 */
	public float getRatio(){
		return mRatio;
	}
	/**
	 * Set the width and height of the screen.
	 * Calculates the ratio of the height and the centre of the screen.
	 * @param width of the screen.
	 * @param height of the screen.
	 */
	public void set(int width, int height){
		mWidth = width;
		mHeight = height;
		//Default height for Screen is 500 units. Scale to this.
		mRatio = 500.0f/mHeight;
		mCentre.setPoints(width/2, height/2);
	}
	/**
	 * Returns the X component of the centre of the screen.
	 * @return centre of the screen x component
	 */
	public float getCentreX(){
		return mCentre.getX();
	}
	
	/**
	 * Returns the Y components of the centre of the screen.
	 *
	 * @return centre of the screen y component.
	 */
	public float getCentreY(){
		return mCentre.getY();
	}
}
