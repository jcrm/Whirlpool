/*
 * Author: Jake Morey
 * Last Updated: 25/02/2013
 * Content:
 * A Class for corners of an object, the centre, width & height, and the radius.
 * 
 */
package com.sinkingduckstudios.whirlpool.movement;

import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;

public class Properties {
	private Point mCentre = new Point();
	private Point mTopLeft = new Point();
	private Point mBottomRight = new Point();
	//These relate to the real part of the bitmap the image takes up (for collision purposes)
	private Point mRealCentre = new Point();
	private Point mRealTopLeft = new Point();
	private Point mRealBottomRight = new Point();
	private int mRadius = 0;
	private int mWidth = 0;
	private int mHeight = 0;
	private int mOriginalWidth = 0;
	private int mOriginalHeight = 0;
	private float mXRatio=0.0f, mYRatio=0.0f;
	private float mXOffset = 0.5f, mYOffset=0.5f;
	public Point mOriginalRect[] = new Point[4];
	public Point mCollisionRect[] = new Point[4];
	public float mAngle=0.0f;
	
	public Properties(){
	}
	
	public void init(float x, float y, int width, int height, float xRatio, float yRatio){
	init(x,y,width,height,xRatio,yRatio,0.5f,0.5f);
	}
	
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
	//temp comment
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
	
	public void setTopLeft(float x, float y){
		//THIS BREAKS LIFE IF YOU INCLUDE, DONT KNOW WHY, YOLO.
		//x/=Constants.getScreen().getRatio();
		//y/=Constants.getScreen().getRatio();
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
	public void setTopLeftX(float x){
		//x/=Constants.getScreen().getRatio();
		mTopLeft.setX(x);
		mBottomRight.setX(x+mWidth);
		mCentre.setX(x+(mWidth/2));
		
		float x1 = (x+(mWidth*mXOffset))-((mWidth*mXRatio)/2);
		mRealTopLeft.setX(x1);
		float x2 = (x+(mWidth*mXOffset))+((mWidth*mXRatio)/2);
		mRealBottomRight.setX(x2);
		mRealCentre.setX(x1+((mWidth*mXRatio)/2));
	}
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
	
	public Point getCentre(){
		return mCentre;
	}
	public Point getRealCentre(){
		return mRealCentre;
	}
	public Point getTopLeft(){
		return mTopLeft;
	}
	public Point getRealTopLeft(){
		return mRealTopLeft;
	}
	public Point getBottomRight(){
		return mBottomRight;
	}
	public Point getRealBottomRight(){
		return mRealBottomRight;
	}
	public float getCentreX(){
		return mCentre.getX();
	}
	public float getCentreY(){
		return mCentre.getY();
	}
	public float getTopLeftX(){
		return mTopLeft.getX();
	}
	public float getTopLeftY(){
		return mTopLeft.getY();
	}
	public float getBottomRightX(){
		return mBottomRight.getX();
	}
	public float getBottomRightY(){
		return mBottomRight.getY();
	}
	
	public int getWidth() {
		return mWidth;
	}
	public void setWidth(int width) {
		mWidth = width;
	}
	public int getHeight() {
		return mHeight;
	}
	public void setHeight(int height) {
		mHeight = height;
	}
	
	public int getRadius() {
		return mRadius;
	}
	public void setRadius(int radius) {
		mRadius = radius;
	}
	
	public void moveDeltaX(float deltaX){
		setTopLeftX(mTopLeft.getX()+deltaX);
	}
	public void moveDeltaY(float deltaY){
		setTopLeftY(mTopLeft.getY()+deltaY);
	}
	public void moveDelta(float deltaX, float deltaY){
		setTopLeft(mTopLeft.getX()+deltaX, mTopLeft.getY()+deltaY);
	}
	
	
	public int getOriginalWidth() {
		return mOriginalWidth;
	}
	public void setOriginalWidth(int originalWidth) {
		mOriginalWidth = originalWidth;
	}
	public int getOriginalHeight() {
		return mOriginalHeight;
	}
	public void setOriginalHeight(int originalHeight) {
		mOriginalHeight = originalHeight;
	}
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

	public void updateAngle(float angle) {
		mAngle = angle;
	}
	public float getAngle() {
		return mAngle;
	}
}