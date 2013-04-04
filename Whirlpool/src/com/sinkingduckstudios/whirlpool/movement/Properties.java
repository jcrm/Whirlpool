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
	private int mRadius = 0;
	private int mWidth = 0;
	private int mHeight = 0;
	private int mOriginalWidth = 0;
	private int mOriginalHeight = 0;
	public Point mOriginalRect[] = new Point[4];
	public Point mCollisionRect[] = new Point[4];
	
	public Properties(){
	}
	public void init(float x, float y, int width, int height){
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
		
		mOriginalRect[0] = new Point(x, y);
		mOriginalRect[1] = new Point(x+width, y);
		mOriginalRect[2] = new Point(x, y+height);
		mOriginalRect[3] = new Point(x+width, y+height);
		
		mCollisionRect[0] = new Point(x, y);
		mCollisionRect[1] = new Point(x+width, y);
		mCollisionRect[2] = new Point(x, y+height);
		mCollisionRect[3] = new Point(x+width, y+height);
	}
	public void setCentre(float x, float y){
		x/=Constants.getScreen().getRatio();
		y/=Constants.getScreen().getRatio();
		mCentre.setPoints(x, y);
		mTopLeft.setPoints(x-(mWidth/2), y-(mHeight/2));
		mBottomRight.setPoints(x+(mWidth/2), y+(mHeight/2));
	}
	//temp comment
	public void setCentreX(float x){
		x/=Constants.getScreen().getRatio();
		mCentre.setX(x);
		mTopLeft.setX(x-(mWidth/2));
		mBottomRight.setX(x+(mWidth/2));
	}
	public void setCentreY(float y){
		y/=Constants.getScreen().getRatio();
		mCentre.setY(y);
		mTopLeft.setY(y-(mHeight/2));
		mBottomRight.setY(y+(mHeight/2));
	}
	
	public void setTopLeft(float x, float y){
		mTopLeft.setPoints(x, y);
		mBottomRight.setPoints(x+mWidth,y+mHeight);
		mCentre.setPoints(x+(mWidth/2),y+(mHeight/2));
	}
	public void setTopLeftX(float x){
		mTopLeft.setX(x);
		mBottomRight.setX(x+mWidth);
		mCentre.setX(x+(mWidth/2));
	}
	public void setTopLeftY(float y){
		mTopLeft.setY(y);
		mBottomRight.setY(y+mHeight);
		mCentre.setY(y+(mHeight/2));
	}
	
	public Point getCentre(){
		return mCentre;
	}
	public Point getTopLeft(){
		return mTopLeft;
	}
	public Point getBottomRight(){
		return mBottomRight;
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
	public void updtaeOriginal(){
		mOriginalRect[0].setPoints(getTopLeftX()*Constants.getScreen().getRatio(), getTopLeftY()*Constants.getScreen().getRatio());
		mOriginalRect[1].setPoints(getBottomRightX()*Constants.getScreen().getRatio(), getTopLeftY()*Constants.getScreen().getRatio());
		mOriginalRect[2].setPoints(getTopLeftX()*Constants.getScreen().getRatio(), getBottomRightY()*Constants.getScreen().getRatio());
		mOriginalRect[3].setPoints(getBottomRightX()*Constants.getScreen().getRatio(), getBottomRightY()*Constants.getScreen().getRatio()); 
	}
}