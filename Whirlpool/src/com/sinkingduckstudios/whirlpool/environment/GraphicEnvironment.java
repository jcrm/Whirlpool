package com.sinkingduckstudios.whirlpool.environment;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.movement.Properties;
import com.sinkingduckstudios.whirlpool.movement.Speed;

/**
 * The Class GraphicEnvironment.
 */
public abstract class GraphicEnvironment {
	/**
	 * The Enum envtype.
	 */
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


	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public envtype getId(){
		return mId;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
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
	
	/**
	 * Sets the centre x.
	 *
	 * @param x the new centre x
	 */
	public void setCentreX(float x){
		mProperties.setCentreX(x);
	}
	
	/**
	 * Sets the centre y.
	 *
	 * @param y the new centre y
	 */
	public void setCentreY(float y){
		mProperties.setCentreY(y);
	}
	
	/**
	 * Gets the centre.
	 *
	 * @return the centre
	 */
	public Point getCentre(){
		return mProperties.getCentre();
	}
	
	/**
	 * Gets the centre x.
	 *
	 * @return the centre x
	 */
	public float getCentreX(){
		return mProperties.getCentreX();
	}
	
	/**
	 * Gets the centre y.
	 *
	 * @return the centre y
	 */
	public float getCentreY(){
		return mProperties.getCentreY();
	}

	public void setTopLeft(float x, float y){
		mProperties.setTopLeft(x, y);
	}
	
	/**
	 * Sets the top left x.
	 *
	 * @param x the new top left x
	 */
	public void setTopLeftX(float x){
		mProperties.setTopLeftX(x);
	}
	
	/**
	 * Sets the top left y.
	 *
	 * @param f the new top left y
	 */
	public void setTopLeftY(float f){
		mProperties.setTopLeftY(f);
	}
	
	/**
	 * Gets the top left.
	 *
	 * @return the top left
	 */
	public Point getTopLeft(){
		return mProperties.getTopLeft();
	}
	
	/**
	 * Gets the top left x.
	 *
	 * @return the top left x
	 */
	public float getTopLeftX(){
		return mProperties.getTopLeftX();
	}
	
	/**
	 * Gets the top left y.
	 *
	 * @return the top left y
	 */
	public float getTopLeftY(){
		return mProperties.getTopLeftY();
	}

	/**
	 * Gets the bottom right.
	 *
	 * @return the bottom right
	 */
	public Point getBottomRight(){
		return mProperties.getBottomRight();
	}
	
	/**
	 * Gets the bottom right x.
	 *
	 * @return the bottom right x
	 */
	public float getBottomRightX(){
		return mProperties.getBottomRightX();
	}
	
	/**
	 * Gets the bottom right y.
	 *
	 * @return the bottom right y
	 */
	public float getBottomRightY(){
		return mProperties.getBottomRightY();
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth(){
		return mProperties.getWidth();
	}
	
	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight(){
		return mProperties.getHeight();
	}
	
	/**
	 * Sets the width.
	 *
	 * @param width the new width
	 */
	public void setWidth(int width){
		mProperties.setWidth(width);
	}
	
	/**
	 * Sets the height.
	 *
	 * @param height the new height
	 */
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
	/**
	 * Gets the radius.
	 *
	 * @return the radius
	 */
	public float getRadius(){
		return mProperties.getRadius();
	}
	
	/**
	 * Sets the radius.
	 *
	 * @param radius the new radius
	 */
	public void setRadius(int radius){
		mProperties.setRadius(radius);
	}

	/**
	 * Sets the angle.
	 *
	 * @param a the new angle
	 */
	public void setAngle(float a){
		mSpeed.setAngle(a);
	}
	
	/**
	 * Gets the graphic.
	 *
	 * @return the graphic
	 */
	public Bitmap getGraphic() {
		return mBitmap;
	}
	
	/**
	 * Gets the speed.
	 *
	 * @return the speed
	 */
	public Speed getSpeed() {
		return mSpeed;
	}

	/**
	 * Sets the pull.
	 *
	 * @param pull the new pull
	 */
	public void setPull(boolean pull){
		mPull = pull;
	}
	
	/**
	 * Gets the pull state.
	 *
	 * @return the pull state
	 */
	public boolean getPullState() {
		return mPull;
	}

	/**
	 * Sets the collision.
	 *
	 * @param collision the new collision
	 */
	public void setCollision(Properties collision){
		mProperties = collision;
	}
	
	/**
	 * Gets the collision.
	 *
	 * @return the collision
	 */
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
