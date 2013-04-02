/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.objects;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.movement.Properties;
import com.sinkingduckstudios.whirlpool.movement.Speed;

interface ObjectFunctions{
	public void draw(Canvas canvas);
	public void init();
	public boolean move();
}
public abstract class GraphicObject {//implements ObjectFunctions{
	//enum used to decide what type of sprite
	public enum objtype {
		tDefault(0, 0, 1, 1, 1), 
		tWhirl(0, 0, 30, 8, 4),
		tDuck(8, 0, 16, 4, 4),
		//not sure what numbers need for frame width and height
		tFrog(4, 0, 16, 4, 4), 
		tShark(5, 90, 31, 8, 4), 
		tBoat(0, 0, 16, 4, 4),
		tDiver(4, new Random().nextInt(360), 16, 4, 4),
		tTorpedo(6, new Random().nextInt(360), 10, 4, 3),
		tCollectable(8,0,16,4,4);
		
		float tSpeed;
		float tAngle;
		int tFrames;
		int tNoOfCol;
		int tNoOfRow;
		
		objtype(float speed, float angle, int frames, int noOfCol, int noOfRow){
			//TODO set min width/height
			tSpeed = speed;
			tAngle = angle;
			tFrames = frames;
			tNoOfCol = noOfCol;
			tNoOfRow = noOfRow;
		}
	}
	//private variables
	protected objtype mId = objtype.tDefault;
	protected Properties mProperties = new Properties();
	protected Bitmap mBitmap;
	protected Speed mSpeed = new Speed();
	protected int mPullState;//state of object in wpool
	protected static Object mScreenLock;
	protected Animate mAnimate;
	protected int wpoolCounter;
	protected Whirlpool mPulledBy;
	
    public GraphicObject(){
    	mScreenLock=Constants.getLock();
    }
    
    abstract public void draw(Canvas canvas);
    
    abstract public void init();
    
    abstract public boolean move();
    
    abstract public void borderCollision(Screen.ScreenSide side, int width, int height);
    
    abstract public void frame();
    
    
	public objtype getId(){
		return mId;
	}
	public void setId(objtype id){
		mId = id;
	}
	
	public boolean border(){
		int HEIGHT =Constants.getLevel().getLevelHeight();
		int WIDTH =Constants.getLevel().getLevelWidth();
		boolean hit = false;
		if(getTopLeftX()<0){
			if(getTopLeftY()<0){
				borderCollision(Screen.ScreenSide.TopLeft, WIDTH, HEIGHT);
			}else if(getBottomRightY()>HEIGHT){
				borderCollision(Screen.ScreenSide.BottomLeft, WIDTH, HEIGHT);
			}else{
				borderCollision(Screen.ScreenSide.Left, WIDTH, HEIGHT);
			}
			hit = true;
		}else if(getBottomRightX() > WIDTH){
        	if(getTopLeftY() < 0){
            	borderCollision(Screen.ScreenSide.TopRight, WIDTH, HEIGHT);
            }else if(getBottomRightY() > HEIGHT) {
            	borderCollision(Screen.ScreenSide.BottomRight, WIDTH, HEIGHT);
            }else{
            	borderCollision(Screen.ScreenSide.Right, WIDTH, HEIGHT);
            }
        	hit = true;
        }
		if (getTopLeftY() < 0) {
        	borderCollision(Screen.ScreenSide.Top, WIDTH, HEIGHT);
        	hit = true;
        } else if (getBottomRightY() > HEIGHT) {
        	borderCollision(Screen.ScreenSide.Bottom, WIDTH, HEIGHT);
        	hit = true;
        }
        return hit;
	}

	public void setCentre(float x, float y){
		mProperties.setCentre(x, y);
	}
	public void setCentreX(float x){
		mProperties.setCentreX(x);
	}
	public void setCentreY(float y){
		mProperties.setCentreY(y);
	}
	public Point getCentre(){
		return mProperties.getCentre();
	}
	public float getCentreX(){
		return mProperties.getCentreX();
	}
	public float getCentreY(){
		return mProperties.getCentreY();
	}
	
	public void setTopLeft(float x, float y){
		mProperties.setTopLeft(x, y);
	}
	public void setTopLeftX(float x){
		mProperties.setTopLeftX(x);
	}
	public void setTopLeftY(float y){
		mProperties.setTopLeftY(y);
	}
	public Point getTopLeft(){
		return mProperties.getTopLeft();
	}
	public float getTopLeftX(){
		return mProperties.getTopLeftX();
	}
	public float getTopLeftY(){
		return mProperties.getTopLeftY();
	}
	
	public Point getBottomRight(){
		return mProperties.getBottomRight();
	}
	public float getBottomRightX(){
		return mProperties.getBottomRightX();
	}
	public float getBottomRightY(){
		return mProperties.getBottomRightY();
	}
	
	public int getWidth(){
    	return mProperties.getWidth();
    }
    public int getHeight(){
    	return mProperties.getHeight();
    }
	public void setWidth(int width){
		mProperties.setWidth(width);
	}
	public void setHeight(int height){
		mProperties.setHeight(height);
	}
	
	public void moveDeltaX(float deltaX){
		synchronized(mScreenLock){
			mProperties.moveDeltaX(deltaX);
    	}
	}
	public void moveDeltaY(float deltaY){
		synchronized(mScreenLock){
			mProperties.moveDeltaY(deltaY);
    	}
	}
	public void moveDelta(float deltaX, float deltaY){
		synchronized(mScreenLock){
			mProperties.moveDelta(deltaX, deltaY);
    	}
	}
	//getters and setters for angles and radius
    public float getRadius(){
    	return mProperties.getRadius();
    }
	public void setRadius(int radius){
		mProperties.setRadius(radius);
	}
	
	public void setAngle(float a){
		mSpeed.setAngle(a);
	}
	public void setSpeed(float speed){
		mSpeed.setSpeed(speed);
	}
    public Bitmap getGraphic() {
        return mBitmap;
    }
    public Speed getSpeed() {
        return mSpeed;
    }
    
    public void setPulledState(int state){
    	mPullState = state;
    }
	public int getPulledState() {
		return mPullState;
	}
	
	public void setCollision(Properties collision){
		mProperties = collision;
	}
	public Properties getCollision(){
		return mProperties;
	}
	public boolean wPoolCounter(){
		wpoolCounter++;
		return (wpoolCounter>=15);
	}
	public void resetwPoolCounter(){
		wpoolCounter=0;
	}
	public void setPulledBy(Whirlpool whirlpool) {
		mPulledBy=whirlpool;
	}
	public Whirlpool getPulledBy() {
		return mPulledBy;
	}
}
