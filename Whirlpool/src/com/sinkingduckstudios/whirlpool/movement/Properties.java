/*
 * Author: Jake Morey, Fraser
 * Last Updated: 25/02/2013
 * Content:
 * Jake Morey: A Class for corners of an object, the centre, width & height, and the radius.
 * Frase: added the scaling code.
 */
package com.sinkingduckstudios.whirlpool.movement;

import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;

/**
 * The Class Properties.
 */
public class Properties {
	/** The centre point. */
	private Point mCentre = new Point();
	/** The top left point. */
	private Point mTopLeft = new Point();
	/** The bottom right point. */
	private Point mBottomRight = new Point();
	//These relate to the real part of the bitmap the image takes up (for collision purposes)
	/** The real centre point. */
	private Point mRealCentre = new Point();
	/** The real top left point. */
	private Point mRealTopLeft = new Point();
	/** The real bottom right. */
	private Point mRealBottomRight = new Point();
	/** The radius. */
	private int mRadius = 0;
	/** The width. */
	private int mWidth = 0;
	/** The height. */
	private int mHeight = 0;
	/** The original width. */
	private int mOriginalWidth = 0;
	/** The original height. */
	private int mOriginalHeight = 0;
	/** The x & y ratio. */
	private float mXRatio=0.0f, mYRatio=0.0f;
	/** The y offset. */
	private float mXOffset = 0.5f, mYOffset=0.5f;
	/** The original rect. */
	public Point mOriginalRect[] = new Point[4];
	/** The collision rect. */
	public Point mCollisionRect[] = new Point[4];
	/** The angle. */
	public float mAngle=0.0f;
	/**
	 * Instantiates a new properties.
	 */
	public Properties(){}
	/**
	 * Inits the property class.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param width the width
	 * @param height the height
	 * @param xRatio the x ratio
	 * @param yRatio the y ratio
	 */
	public void init(float x, float y, int width, int height, float xRatio, float yRatio){
		init(x,y,width,height,xRatio,yRatio,0.5f,0.5f);
	}

	/**
	 * Inits the property class.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param width the width
	 * @param height the height
	 * @param xRatio the x ratio
	 * @param yRatio the y ratio
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public void init(float x, float y, int width, int height, float xRatio, float yRatio, float xOffset, float yOffset){
		x/=Constants.getScreen().getRatio();
		y/=Constants.getScreen().getRatio();
		mTopLeft.setPoints(x, y);
		mOriginalWidth = width;
		mOriginalHeight = height;
		mWidth = (int) (width/Constants.getScreen().getRatio());
		mHeight = (int) (height/Constants.getScreen().getRatio());
		mCentre.setPoints(x+(mWidth/2), y+(mHeight/2));
		mBottomRight.setPoints(x+mWidth, y+mHeight);
		mRadius =  (int) (Math.sqrt(((float)(mWidth/2)*(mWidth/2)) + ((float)(mHeight/2)*(mHeight/2))) -(mWidth/8));

		mXRatio = xRatio;
		mYRatio = yRatio;
		mXOffset = xOffset;
		mYOffset = yOffset;

		float x1 = (x+(mWidth*mXOffset))-((mWidth*xRatio)/2);
		float y1 = (y+(mHeight*mYOffset))-((mHeight*yRatio)/2);
		mRealTopLeft.setPoints(x1, y1);
		float x2 = (x+(mWidth*mXOffset))+((mWidth*xRatio)/2);
		float y2 = (y+(mHeight*mYOffset))+((mHeight*yRatio)/2);
		mRealBottomRight.setPoints(x2, y2);

		mRealCentre.setPoints(x1+((mWidth*xRatio)/2),y1+((mHeight*yRatio)/2));

		mOriginalRect[0] = new Point(x1, y1);
		mOriginalRect[1] = new Point(x1+(mWidth*xRatio), y1);
		mOriginalRect[2] = new Point(x1, y1+(mHeight*yRatio));
		mOriginalRect[3] = new Point(x1+(mWidth*xRatio), y1+(mHeight*yRatio));

		mCollisionRect[0] = new Point(x1, y1);
		mCollisionRect[1] = new Point(x1+(mWidth*xRatio), y1);
		mCollisionRect[2] = new Point(x1, y1+(mHeight*yRatio));
		mCollisionRect[3] = new Point(x1+(mWidth*xRatio), y1+(mHeight*yRatio));
	}

	/**
	 * Sets the centre position.
	 *
	 * @param x the x position
	 * @param y the y position
	 */
	public void setCentre(float x, float y){
		x/=Constants.getScreen().getRatio();
		y/=Constants.getScreen().getRatio();
		mCentre.setPoints(x, y);
		mTopLeft.setPoints(x-(mWidth/2), y-(mHeight/2));
		mBottomRight.setPoints(x+(mWidth/2), y+(mHeight/2));

		x = mTopLeft.getX();
		y = mTopLeft.getY();

		float x1 = (x+(mWidth*mXOffset))-((mWidth*mXRatio)/2);
		float y1 = (y+(mHeight*mYOffset))-((mHeight*mYRatio)/2);
		mRealTopLeft.setPoints(x1, y1);
		float x2 = (x+(mWidth*mXOffset))+((mWidth*mXRatio)/2);
		float y2 = (y+(mHeight*mYOffset))+((mHeight*mYRatio)/2);
		mRealBottomRight.setPoints(x2, y2);
		mRealCentre.setPoints(x1+((mWidth*mXRatio)/2),y1+((mHeight*mYRatio)/2));
	}
	/**
	 * Sets the centre x position.
	 *
	 * @param x the new centre x position.
	 */
	public void setCentreX(float x){
		x/=Constants.getScreen().getRatio();
		mCentre.setX(x);
		mTopLeft.setX(x-(mWidth/2));
		mBottomRight.setX(x+(mWidth/2));

		x = mTopLeft.getX();

		float x1 = (x+(mWidth*mXOffset))-((mWidth*mXRatio)/2);
		mRealTopLeft.setX(x1);
		float x2 = (x+(mWidth*mXOffset))+((mWidth*mXRatio)/2);
		mRealBottomRight.setX(x2);
		mRealCentre.setX(x1+((mWidth*mXRatio)/2));
	}

	/**
	 * Sets the centre y position.
	 *
	 * @param y the new centre y position.
	 */
	public void setCentreY(float y){
		y/=Constants.getScreen().getRatio();
		mCentre.setY(y);
		mTopLeft.setY(y-(mHeight/2));
		mBottomRight.setY(y+(mHeight/2));

		y = mTopLeft.getY();

		float y1 = (y+(mHeight*mYOffset))-((mHeight*mYRatio)/2);
		mRealTopLeft.setY(y1);
		float y2 = (y+(mHeight*mYOffset))+((mHeight*mYRatio)/2);
		mRealBottomRight.setY(y2);
		mRealCentre.setY(y1+((mHeight*mYRatio)/2));
	}

	/**
	 * Sets the top left position.
	 *
	 * @param x the x position.
	 * @param y the y position.
	 */
	public void setTopLeft(float x, float y){
		mTopLeft.setPoints(x, y);
		mBottomRight.setPoints(x+mWidth,y+mHeight);
		mCentre.setPoints(x+(mWidth/2),y+(mHeight/2));

		float x1 = (x+(mWidth*mXOffset))-((mWidth*mXRatio)/2);
		float y1 = (y+(mHeight*mYOffset))-((mHeight*mYRatio)/2);
		mRealTopLeft.setPoints(x1, y1);
		float x2 = (x+(mWidth*mXOffset))+((mWidth*mXRatio)/2);
		float y2 = (y+(mHeight*mYOffset))+((mHeight*mYRatio)/2);
		mRealBottomRight.setPoints(x2, y2);
		mRealCentre.setPoints(x1+((mWidth*mXRatio)/2),y1+((mHeight*mYRatio)/2));
	}

	/**
	 * Sets the top left x position.
	 *
	 * @param x the new top left x position.
	 */
	public void setTopLeftX(float x){
		mTopLeft.setX(x);
		mBottomRight.setX(x+mWidth);
		mCentre.setX(x+(mWidth/2));

		float x1 = (x+(mWidth*mXOffset))-((mWidth*mXRatio)/2);
		mRealTopLeft.setX(x1);
		float x2 = (x+(mWidth*mXOffset))+((mWidth*mXRatio)/2);
		mRealBottomRight.setX(x2);
		mRealCentre.setX(x1+((mWidth*mXRatio)/2));
	}

	/**
	 * Sets the top left y position.
	 *
	 * @param y the new top left y position.
	 */
	public void setTopLeftY(float y){
		//y/=Constants.getScreen().getRatio();
		mTopLeft.setY(y);
		mBottomRight.setY(y+mHeight);
		mCentre.setY(y+(mHeight/2));

		float y1 = (y+(mHeight*mYOffset))-((mHeight*mYRatio)/2);
		mRealTopLeft.setY(y1);
		float y2 = (y+(mHeight*mYOffset))+((mHeight*mYRatio)/2);
		mRealBottomRight.setY(y2);
		mRealCentre.setY(y1+((mHeight*mYRatio)/2));
	}

	/**
	 * Gets the centre position.
	 *
	 * @return the centre position.
	 */
	public Point getCentre(){
		return mCentre;
	}

	/**
	 * Gets the real centre position.
	 *
	 * @return the real centre position.
	 */
	public Point getRealCentre(){
		return mRealCentre;
	}

	/**
	 * Gets the top left position.
	 *
	 * @return the top left position.
	 */
	public Point getTopLeft(){
		return mTopLeft;
	}

	/**
	 * Gets the real top left position.
	 *
	 * @return the real top left position.
	 */
	public Point getRealTopLeft(){
		return mRealTopLeft;
	}

	/**
	 * Gets the bottom right position.
	 *
	 * @return the bottom right position.
	 */
	public Point getBottomRight(){
		return mBottomRight;
	}

	/**
	 * Gets the real bottom right position.
	 *
	 * @return the real bottom right position.
	 */
	public Point getRealBottomRight(){
		return mRealBottomRight;
	}

	/**
	 * Gets the centre x position.
	 *
	 * @return the centre x position.
	 */
	public float getCentreX(){
		return mCentre.getX();
	}

	/**
	 * Gets the centre y position.
	 *
	 * @return the centre y position.
	 */
	public float getCentreY(){
		return mCentre.getY();
	}

	/**
	 * Gets the top left x position.
	 *
	 * @return the top left x position.
	 */
	public float getTopLeftX(){
		return mTopLeft.getX();
	}

	/**
	 * Gets the top left y position.
	 *
	 * @return the top left y position.
	 */
	public float getTopLeftY(){
		return mTopLeft.getY();
	}

	/**
	 * Gets the bottom right x position.
	 *
	 * @return the bottom right x position.
	 */
	public float getBottomRightX(){
		return mBottomRight.getX();
	}

	/**
	 * Gets the bottom right y position.
	 *
	 * @return the bottom right y position.
	 */
	public float getBottomRightY(){
		return mBottomRight.getY();
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return mWidth;
	}

	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width) {
		mWidth = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return mHeight;
	}

	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
	public void setHeight(int height) {
		mHeight = height;
	}

	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public int getRadius() {
		return mRadius;
	}

	/**
	 * Sets the radius.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(int radius) {
		mRadius = radius;
	}

	/**
	 * Move delta x.
	 *
	 * @param deltaX move by delta x
	 */
	public void moveDeltaX(float deltaX){
		setTopLeftX(mTopLeft.getX()+deltaX);
	}

	/**
	 * Move delta y.
	 *
	 * @param deltaY move by delta y
	 */
	public void moveDeltaY(float deltaY){
		setTopLeftY(mTopLeft.getY()+deltaY);
	}

	/**
	 * Move delta.
	 *
	 * @param deltaX move by delta x
	 * @param deltaY move by delta y
	 */
	public void moveDelta(float deltaX, float deltaY){
		setTopLeft(mTopLeft.getX()+deltaX, mTopLeft.getY()+deltaY);
	}


	/**
	 * Gets the original width.
	 *
	 * @return the original width
	 */
	public int getOriginalWidth() {
		return mOriginalWidth;
	}

	/**
	 * Sets the original width.
	 *
	 * @param originalWidth the new original width
	 */
	public void setOriginalWidth(int originalWidth) {
		mOriginalWidth = originalWidth;
	}

	/**
	 * Gets the original height.
	 *
	 * @return the original height
	 */
	public int getOriginalHeight() {
		return mOriginalHeight;
	}

	/**
	 * Sets the original height.
	 *
	 * @param originalHeight the new original height
	 */
	public void setOriginalHeight(int originalHeight) {
		mOriginalHeight = originalHeight;
	}

	/**
	 * Update original rect.
	 */
	public void updateOriginal(){

		float x1 = (getTopLeftX()+(mWidth*mXOffset))-((mWidth*mXRatio)/2);
		float y1 = (getTopLeftY()+(mHeight*mYOffset))-((mHeight*mYRatio)/2);
		float x2 = (getTopLeftX()+(mWidth*mXOffset))+((mWidth*mXRatio)/2);
		float y2 = (getTopLeftY()+(mHeight*mYOffset))+((mHeight*mYRatio)/2);

		mOriginalRect[0].setPoints(x1*Constants.getScreen().getRatio(), y1*Constants.getScreen().getRatio());
		mOriginalRect[1].setPoints(x2*Constants.getScreen().getRatio(), y1*Constants.getScreen().getRatio());
		mOriginalRect[2].setPoints(x1*Constants.getScreen().getRatio(), y2*Constants.getScreen().getRatio());
		mOriginalRect[3].setPoints(x2*Constants.getScreen().getRatio(), y2*Constants.getScreen().getRatio()); 
	}

	/**
	 * Update angle.
	 *
	 * @param angle the angle
	 */
	public void updateAngle(float angle) {
		mAngle = angle;
	}

	/**
	 * Gets the angle.
	 *
	 * @return the angle
	 */
	public float getAngle() {
		return mAngle;
	}
}