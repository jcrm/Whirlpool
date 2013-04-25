/*
 * Author: Jake Morey based upon Jordan O'Hare GraphicObject
 * Last Updated: 22/04/13
 * Content:This is a stripped down copy of the graphic objectclass,
 * implemented for creating environment objects such as the finish
 * and in future life ring, sponge
 */
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
		
		/** The default type. */
		tDefault(0, 0, 1, 1, 1), 
		/** The finish object. */
		tFinish(0, 0, 15, 4, 4);
		
		/** The speed. */
		float tSpeed;
		/** The angle. */
		float tAngle;
		/** The frames. */
		int tFrames;
		/** The number of columns. */
		int tNoOfCol;
		/** The number of row. */
		int tNoOfRow;

		/**
		 * Instantiates a new envtype.
		 *
		 * @param speed the speed.
		 * @param angle the angle.
		 * @param frames the frames.
		 * @param noOfCol the number of columns.
		 * @param noOfRow the number of row.
		 */
		envtype(float speed, float angle, int frames, int noOfCol, int noOfRow){
			tSpeed = speed;
			tAngle = angle;
			tFrames = frames;
			tNoOfCol = noOfCol;
			tNoOfRow = noOfRow;
		}
	}
	
	/** The id of the object. */
	protected envtype mId = envtype.tDefault;
	/** The properties of the object. */
	protected Properties mProperties = new Properties();
	/** The bitmap of the object. */
	protected Bitmap mBitmap;
	/** The speed of the object. */
	protected Speed mSpeed = new Speed();
	/** The pull. */
	protected boolean mPull;// = false;
	/** The screen lock. */
	protected static Object mScreenLock;
	/** The animation of the object. */
	protected Animate mAnimate;
	/** The wpool counter. */
	private int wpoolCounter;

	/**
	 * Instantiates a new graphic environment.
	 */
	public GraphicEnvironment(){
		mScreenLock=Constants.getLock();
	}

	/**
	 * Draw.
	 *
	 * @param canvas the canvas
	 */
	abstract public void draw(Canvas canvas);

	/**
	 * Inits the object.
	 */
	abstract public void init();
	/**
	 * Move.
	 *
	 * @return true, if can move
	 */
	abstract public boolean move();

	/**
	 * Border collision.
	 *
	 * @param side the side of the border
	 * @param width the width
	 * @param height the height
	 */
	abstract public void borderCollision(Screen.ScreenSide side, int width, int height);

	/**
	 * Frame. What each object does each frame.
	 */
	abstract public void frame();


	/**
	 * Gets the id.
	 *
	 * @return the id of the object.
	 */
	public envtype getId(){
		return mId;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id of the object.
	 */
	public void setId(envtype id){
		mId = id;
	}

	/**
	 * Border.
	 *
	 * @return true, if collided with border.
	 */
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

	/**
	 * Sets the centre.
	 *
	 * @param x the x position
	 * @param y the y position
	 */
	public void setCentre(float x, float y){
		mProperties.setCentre(x, y);
	}
	
	/**
	 * Sets the centre x.
	 *
	 * @param x the new centre x position
	 */
	public void setCentreX(float x){
		mProperties.setCentreX(x);
	}
	
	/**
	 * Sets the centre y.
	 *
	 * @param y the new centre y position
	 */
	public void setCentreY(float y){
		mProperties.setCentreY(y);
	}
	
	/**
	 * Gets the centre.
	 *
	 * @return the centre point.
	 */
	public Point getCentre(){
		return mProperties.getCentre();
	}
	
	/**
	 * Gets the centre x.
	 *
	 * @return the centre x position
	 */
	public float getCentreX(){
		return mProperties.getCentreX();
	}
	
	/**
	 * Gets the centre y.
	 *
	 * @return the centre y position
	 */
	public float getCentreY(){
		return mProperties.getCentreY();
	}

	/**
	 * Sets the top left.
	 *
	 * @param x the x position 
	 * @param y the y position
	 */
	public void setTopLeft(float x, float y){
		mProperties.setTopLeft(x, y);
	}
	
	/**
	 * Sets the top left x.
	 *
	 * @param x the new top left x position
	 */
	public void setTopLeftX(float x){
		mProperties.setTopLeftX(x);
	}
	
	/**
	 * Sets the top left y.
	 *
	 * @param f the new top left y position
	 */
	public void setTopLeftY(float f){
		mProperties.setTopLeftY(f);
	}
	
	/**
	 * Gets the top left.
	 *
	 * @return the top left point
	 */
	public Point getTopLeft(){
		return mProperties.getTopLeft();
	}
	
	/**
	 * Gets the top left x.
	 *
	 * @return the top left x position
	 */
	public float getTopLeftX(){
		return mProperties.getTopLeftX();
	}
	
	/**
	 * Gets the top left y.
	 *
	 * @return the top left y position
	 */
	public float getTopLeftY(){
		return mProperties.getTopLeftY();
	}

	/**
	 * Gets the bottom right.
	 *
	 * @return the bottom right point
	 */
	public Point getBottomRight(){
		return mProperties.getBottomRight();
	}
	
	/**
	 * Gets the bottom right x.
	 *
	 * @return the bottom right x position
	 */
	public float getBottomRightX(){
		return mProperties.getBottomRightX();
	}
	
	/**
	 * Gets the bottom right y.
	 *
	 * @return the bottom right y position
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

	/**
	 * Move delta x.
	 *
	 * @param deltaX move by value of
	 */
	public void moveDeltaX(int deltaX){
		synchronized(mScreenLock){
			mProperties.moveDeltaX(deltaX);
		}
	}
	
	/**
	 * Move delta y.
	 *
	 * @param deltaY move by value of
	 */
	public void moveDeltaY(int deltaY){
		synchronized(mScreenLock){
			mProperties.moveDeltaY(deltaY);
		}
	}
	
	/**
	 * Move delta.
	 *
	 * @param deltaX move by value of
	 * @param deltaY move by value of
	 */
	public void moveDelta(int deltaX, int deltaY){
		synchronized(mScreenLock){
			mProperties.moveDelta(deltaX, deltaY);
		}
	}
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
	
	/**
	 * W pool counter.
	 *
	 * @return true, if successful
	 */
	public boolean wPoolCounter(){
		wpoolCounter++;
		return (wpoolCounter>=15);
	}
	
	/**
	 * Resetw pool counter.
	 */
	public void resetwPoolCounter(){
		wpoolCounter=0;
	}	
}
