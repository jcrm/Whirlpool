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
	
	public Properties(){
	}
	public void init(int x, int y, int width, int height){
		mTopLeft.setPoints(x, y);
		mOriginalWidth = width;
		mOriginalHeight = height;
		mWidth = (int) (width/Constants.getScreen().getRatio());
		mHeight = (int) (height/Constants.getScreen().getRatio());
		mCentre.setPoints(x+(mWidth/2), y+(mHeight/2));
		mBottomRight.setPoints(x+mWidth, y+mHeight);
		mRadius =  (int) (Math.sqrt(((float)(mWidth/2)*(mWidth/2)) + ((float)(mHeight/2)*(mHeight/2))) -(mWidth/8));
	}
	public void setCentre(int x, int y){
		mCentre.setPoints(x, y);
		mTopLeft.setPoints(x-(mWidth/2), y-(mHeight/2));
		mBottomRight.setPoints(x+(mWidth/2), y+(mHeight/2));
	}
	//temp comment
	public void setCentreX(int x){
		mCentre.setX(x);
		mTopLeft.setX(x-(mWidth/2));
		mBottomRight.setX(x+(mWidth/2));
	}
	public void setCentreY(int y){
		mCentre.setY(y);
		mTopLeft.setY(y-(mHeight/2));
		mBottomRight.setY(y+(mHeight/2));
	}
	
	public void setTopLeft(int x, int y){
		mTopLeft.setPoints(x, y);
		mBottomRight.setPoints(x+mWidth,y+mHeight);
		mCentre.setPoints(x+(mWidth/2),y+(mHeight/2));
	}
	public void setTopLeftX(int x){
		mTopLeft.setX(x);
		mBottomRight.setX(x+mWidth);
		mCentre.setX(x+(mWidth/2));
	}
	public void setTopLeftY(int y){
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
	public int getCentreX(){
		return mCentre.getX();
	}
	public int getCentreY(){
		return mCentre.getY();
	}
	public int getTopLeftX(){
		return mTopLeft.getX();
	}
	public int getTopLeftY(){
		return mTopLeft.getY();
	}
	public int getBottomRightX(){
		return mBottomRight.getX();
	}
	public int getBottomRightY(){
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
	
	public void moveDeltaX(int deltaX){
		setTopLeftX(mTopLeft.getX()+deltaX);
	}
	public void moveDeltaY(int deltaY){
		setTopLeftY(mTopLeft.getY()+deltaY);
	}
	public void moveDelta(int deltaX, int deltaY){
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
}
