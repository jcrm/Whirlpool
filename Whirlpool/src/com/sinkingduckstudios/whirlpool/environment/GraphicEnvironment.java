package com.sinkingduckstudios.whirlpool.environment;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.movement.Properties;
import com.sinkingduckstudios.whirlpool.movement.Speed;

public abstract class GraphicEnvironment {
	//enum used to decide what type of sprite
	public enum envtype {
		tDefault(0, 0, 1, 1, 1), 
		tFinish(0, 0, 15, 4, 4);
		
		float tSpeed;
		float tAngle;
		int tFrames;
		int tNoOfCol;
		int tNoOfRow;

		envtype(float speed, float angle, int frames, int noOfCol, int noOfRow){
			//TODO set min width/height
			tSpeed = speed;
			tAngle = angle;
			tFrames = frames;
			tNoOfCol = noOfCol;
			tNoOfRow = noOfRow;
		}
	}
	//private variables
	protected envtype mId = envtype.tDefault;
	protected Properties mProperties = new Properties();
	protected Bitmap mBitmap;
	protected Speed mSpeed = new Speed();
	protected boolean mPull;// = false;
	protected static Object mScreenLock;
	protected Animate mAnimate;
	private int wpoolCounter;

	public GraphicEnvironment(){
		mScreenLock=Constants.getLock();
	}

	abstract public void draw(Canvas canvas);

	abstract public void init();

	abstract public boolean move();

	abstract public void borderCollision(Screen.ScreenSide side, int width, int height);

	abstract public void frame();


	public envtype getId(){
		return mId;
	}
	public void setId(envtype id){
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
	public void setTopLeftY(float f){
		mProperties.setTopLeftY(f);
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
