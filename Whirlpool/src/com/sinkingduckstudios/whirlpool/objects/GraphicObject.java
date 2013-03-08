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
		tDefault(0, 0, 1), 
		tWhirl(0, 0, 30),
		tDuck(8, 0, 16),
		//not sure what numbers need for frame width and height
		tFrog(4, 0, 16), 
		tShark(5, new Random().nextInt(360)+1, 1), 
		tBoat(0, 0, 15),
		tDiver(4, new Random().nextInt(360), 16),
		tTorpedo(4, new Random().nextInt(360), 10);
		
		float tSpeed;
		float tAngle;
		int tFrames;
		
		objtype(float speed, float angle, int frames){
			//TODO set min width/height
			tSpeed = speed;
			tAngle = angle;
			tFrames = frames;
		}
	}
	//private variables
	protected objtype mId = objtype.tDefault;
	protected Properties mProperties = new Properties();
	protected Bitmap mBitmap;
	protected Speed mSpeed = new Speed();
	protected boolean mPull;// = false;
	protected static Object mScreenLock;
	protected Animate mAnimate;
	private int wpoolCounter;
	
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
		int HEIGHT = Constants.getLevel().getLevelHeight();
		int WIDTH = Constants.getLevel().getLevelWidth();
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

	public void setCentre(int x, int y){
		mProperties.setCentre(x, y);
	}
	public void setCentreX(int x){
		mProperties.setCentreX(x);
	}
	public void setCentreY(int y){
		mProperties.setCentreY(y);
	}
	public Point getCentre(){
		return mProperties.getCentre();
	}
	public int getCentreX(){
		return mProperties.getCentreX();
	}
	public int getCentreY(){
		return mProperties.getCentreY();
	}
	
	public void setTopLeft(int x, int y){
		mProperties.setTopLeft(x, y);
	}
	public void setTopLeftX(int x){
		mProperties.setTopLeftX(x);
	}
	public void setTopLeftY(int y){
		mProperties.setTopLeftY(y);
	}
	public Point getTopLeft(){
		return mProperties.getTopLeft();
	}
	public int getTopLeftX(){
		return mProperties.getTopLeftX();
	}
	public int getTopLeftY(){
		return mProperties.getTopLeftY();
	}
	
	public Point getBottomRight(){
		return mProperties.getBottomRight();
	}
	public int getBottomRightX(){
		return mProperties.getBottomRightX();
	}
	public int getBottomRightY(){
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
	
	public void moveDeltaX(int deltaX){
		synchronized(mScreenLock){
			mProperties.moveDeltaX(deltaX);
    	}
	}
	public void moveDeltaY(int deltaY){
		synchronized(mScreenLock){
			mProperties.moveDeltaY(deltaY);
    	}
	}
	public void moveDelta(int deltaX, int deltaY){
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
    public Bitmap getGraphic() {
        return mBitmap;
    }
    public Speed getSpeed() {
        return mSpeed;
    }
    
    public void setPull(boolean pull){
    	mPull = pull;
    }
	public boolean getPullState() {
		return mPull;
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
}
