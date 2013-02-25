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
import com.sinkingduckstudios.whirlpool.logic.Imports;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.movement.Speed;

interface ObjectFunctions{
	public void draw(Canvas canvas);
	public void init();
	public boolean move();
}
public abstract class GraphicObject {//implements ObjectFunctions{
	//enum used to decide what type of sprite
	public enum objtype {
		tDefault(0, 1, 1, 0, 0, 1), 
		tWhirl(1, 128, 128, 0, 0, 30),
		tDuck(2, 64, 64, 6, 0, 16),
		//not sure what numbers need for frame width and height
		tFrog(3, 96, 96, 4, 0, 16), 
		tShark(4, 64, 64, 5, new Random().nextInt(360)+1, 1), 
		tBoat(5, 96, 96, 0, 0, 1),
		tDiver(6, 128, 128, 4, new Random().nextInt(360), 16);
		
		int tWidth;
		int tHeight;
		float tSpeed;
		float tAngle;
		int tFrames;
		
		objtype(int type, int width, int height, float speed, float angle, int frames){
			if(type != 0){
				Imports.scaledBitmap(type, width*frames, height);
			}
			//TODO set min width/height
			tSpeed = speed;
			tAngle = angle;
			tFrames = frames;
		}
	}
	//private variables
	protected objtype mId = objtype.tDefault;
	protected Collision mCollision = new Collision();
	protected Bitmap mBitmap;
	protected Speed mSpeed = new Speed();
	protected boolean mPull;// = false;
	protected static Object mScreenLock;
	protected Animate mAnimate;
	
    public GraphicObject(){
    	mScreenLock=Constants.getLock();
    }
    
    abstract public void draw(Canvas c);
    
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
		mCollision.setCentre(x, y);
	}
	public void setCentreX(int x){
		mCollision.setCentreX(x);
	}
	public void setCentreY(int y){
		mCollision.setCentreY(y);
	}
	public Point getCentre(){
		return mCollision.getCentre();
	}
	public int getCentreX(){
		return mCollision.getCentreX();
	}
	public int getCentreY(){
		return mCollision.getCentreY();
	}
	
	public void setTopLeft(int x, int y){
		mCollision.setTopLeft(x, y);
	}
	public void setTopLeftX(int x){
		mCollision.setTopLeftX(x);
	}
	public void setTopLeftY(int y){
		mCollision.setTopLeftY(y);
	}
	public Point getTopLeft(){
		return mCollision.getTopLeft();
	}
	public int getTopLeftX(){
		return mCollision.getTopLeftX();
	}
	public int getTopLeftY(){
		return mCollision.getTopLeftY();
	}
	
	public Point getBottomRight(){
		return mCollision.getBottomRight();
	}
	public int getBottomRightX(){
		return mCollision.getBottomRightX();
	}
	public int getBottomRightY(){
		return mCollision.getBottomRightY();
	}
	
	public int getWidth(){
    	return mCollision.getWidth();
    }
    public int getHeight(){
    	return mCollision.getHeight();
    }
	public void setWidth(int width){
		mCollision.setWidth(width);
	}
	public void setHeight(int height){
		mCollision.setHeight(height);
	}
	
	public void moveDeltaX(int deltaX){
		synchronized(mScreenLock){
			mCollision.moveDeltaX(deltaX);
    	}
	}
	public void moveDeltaY(int deltaY){
		synchronized(mScreenLock){
			mCollision.moveDeltaY(deltaY);
    	}
	}
	public void moveDelta(int deltaX, int deltaY){
		synchronized(mScreenLock){
			mCollision.moveDelta(deltaX, deltaY);
    	}
	}
	//getters and setters for angles and radius
    public float getRadius(){
    	return mCollision.getRadius();
    }
	public void setRadius(int radius){
		mCollision.setRadius(radius);
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
	
	public void setCollision(Collision collision){
		mCollision = collision;
	}
	public Collision getCollision(){
		return mCollision;
	}
}
