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
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.movement.Properties;
import com.sinkingduckstudios.whirlpool.movement.Speed;

// TODO: Auto-generated Javadoc
interface ObjectFunctions{
	public void draw(Canvas canvas);
	public void init();
	public boolean move();
}

/**
 * The Class GraphicObject.
 */
public abstract class GraphicObject {//implements ObjectFunctions{
	//enum used to decide what type of sprite
	/**
 * The Enum objtype.
 */
public enum objtype {
		
		/** The t default. */
		tDefault(0, 0, 1, 1, 1), 
		
		/** The t whirl. */
		tWhirl(0, 0, 30, 8, 4),
		
		/** The t duck. */
		tDuck(8, 0, 16, 4, 4),
		//not sure what numbers need for frame width and height
		/** The t frog. */
		tFrog(4, 0, 16, 4, 4), 
		
		/** The t shark. */
		tShark(5, 90, 31, 8, 4), 
		
		/** The t boat. */
		tBoat(0, 0, 16, 4, 4),
		
		/** The t diver. */
		tDiver(4, new Random().nextInt(360), 16, 4, 4),
		
		/** The t torpedo. */
		tTorpedo(6, new Random().nextInt(360), 10, 4, 3),
		
		/** The t collectable. */
		tCollectable(8,0,16,4,4);
		
		/** The t speed. */
		float tSpeed;
		
		/** The t angle. */
		float tAngle;
		
		/** The t frames. */
		int tFrames;
		
		/** The t no of col. */
		int tNoOfCol;
		
		/** The t no of row. */
		int tNoOfRow;
		
		/**
		 * Instantiates a new objtype.
		 *
		 * @param speed the speed
		 * @param angle the angle
		 * @param frames the frames
		 * @param noOfCol the no of col
		 * @param noOfRow the no of row
		 */
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
	/** The m id. */
	protected objtype mId = objtype.tDefault;
	
	/** The m properties. */
	protected Properties mProperties = new Properties();
	
	/** The m bitmap. */
	protected Bitmap mBitmap;
	
	/** The m speed. */
	protected Speed mSpeed = new Speed();
	
	/** The m pull state. */
	protected int mPullState;//state of object in wpool
	
	/** The m screen lock. */
	protected static Object mScreenLock;
	
	/** The m animate. */
	protected Animate mAnimate;
	
	/** The wpool counter. */
	protected int wpoolCounter;
	
	/** The m pulled by. */
	protected Whirlpool mPulledBy;
	
	/** The m is playing. */
	protected boolean mIsPlaying;
	
	/** The m graphic type. */
	protected int mGraphicType;				// for ease sake, 1 = diver, 2 = frog, 3 = boat, 4 = shark
	
    /**
     * Instantiates a new graphic object.
     */
    public GraphicObject(){
    	mScreenLock=Constants.getLock();
    }
    
    /**
     * Draw.
     *
     * @param canvas the canvas
     */
    abstract public void draw(Canvas canvas);
    
    /**
     * Inits the.
     */
    abstract public void init();
    
    /**
     * Move.
     *
     * @return true, if successful
     */
    abstract public boolean move();
    
    /**
     * Frame.
     */
    abstract public void frame();
    
    
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public objtype getId(){
		return mId;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(objtype id){
		mId = id;
	}
	
	/**
	 * Border.
	 *
	 * @return true, if successful
	 */
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
	
	/**
	 * Border collision.
	 *
	 * @param side the side
	 * @param width the width
	 * @param height the height
	 */
	public void borderCollision(ScreenSide side, int width, int height) {
		switch(side){
		case Top:
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		case Bottom:
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case Left:
			mSpeed.horizontalBounce();
			setTopLeftX(-getTopLeftX());
			break;
		case Right:
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			break;
		case BottomLeft:
			mSpeed.horizontalBounce();
			setTopLeftX(-getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case BottomRight:
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case TopLeft:
			mSpeed.horizontalBounce();
			setTopLeftX(-getTopLeftX());
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		case TopRight:
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		default:
			break;
		}
	}
	
	/**
	 * Sets the centre.
	 *
	 * @param x the x
	 * @param y the y
	 */
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
	
	/**
	 * Sets the top left.
	 *
	 * @param x the x
	 * @param y the y
	 */
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
	 * @param y the new top left y
	 */
	public void setTopLeftY(float y){
		mProperties.setTopLeftY(y);
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
	
	/**
	 * Move delta x.
	 *
	 * @param deltaX the delta x
	 */
	public void moveDeltaX(float deltaX){
		synchronized(mScreenLock){
			mProperties.moveDeltaX(deltaX);
    	}
	}
	
	/**
	 * Move delta y.
	 *
	 * @param deltaY the delta y
	 */
	public void moveDeltaY(float deltaY){
		synchronized(mScreenLock){
			mProperties.moveDeltaY(deltaY);
    	}
	}
	
	/**
	 * Move delta.
	 *
	 * @param deltaX the delta x
	 * @param deltaY the delta y
	 */
	public void moveDelta(float deltaX, float deltaY){
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
	 * Sets the speed.
	 *
	 * @param speed the new speed
	 */
	public void setSpeed(float speed){
		mSpeed.setSpeed(speed);
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
     * Sets the pulled state.
     *
     * @param state the new pulled state
     */
    public void setPulledState(int state){
    	mPullState = state;
    }
	
	/**
	 * Gets the pulled state.
	 *
	 * @return the pulled state
	 */
	public int getPulledState() {
		return mPullState;
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
	
	/**
	 * Sets the pulled by.
	 *
	 * @param whirlpool the new pulled by
	 */
	public void setPulledBy(Whirlpool whirlpool) {
		mPulledBy=whirlpool;
	}
	
	/**
	 * Gets the pulled by.
	 *
	 * @return the pulled by
	 */
	public Whirlpool getPulledBy() {
		return mPulledBy;
	}
	
	/**
	 * Sets the checks if is playing.
	 *
	 * @param IsOnPlaying the new checks if is playing
	 */
	public void setIsPlaying(boolean IsOnPlaying){
		mIsPlaying = IsOnPlaying;
	}
	
	/**
	 * Gets the checks if is playing.
	 *
	 * @return the checks if is playing
	 */
	public boolean getIsPlaying(){
		return mIsPlaying;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param enemyType the new type
	 */
	public void setType(int enemyType){
		mGraphicType = enemyType;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public int getType(){
		return mGraphicType;
	}

}
