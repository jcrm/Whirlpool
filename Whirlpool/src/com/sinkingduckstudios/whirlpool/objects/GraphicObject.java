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
		
		/** The default values. */
		tDefault(0, 0, 1, 1, 1), 
		/** The t whirl. */
		tWhirl(0, 0, 30, 8, 4),
		/** The duck values. */
		tDuck(8, 0, 16, 4, 4),
		/** The frog values. */
		tFrog(4, 0, 16, 4, 4), 
		/** The shark values. */
		tShark(5, 90, 31, 8, 4), 
		/** The boat values. */
		tBoat(0, 0, 16, 4, 4),
		/** The diver values. */
		tDiver(4, new Random().nextInt(360), 16, 4, 4),
		/** The torpedo values. */
		tTorpedo(6, new Random().nextInt(360), 10, 4, 3),
		/** The collectable values. */
		tCollectable(8,0,16,4,4);
		
		/** The speed of the object. */
		float tSpeed;
		/** The angle of the object. */
		float tAngle;
		/** The frames of the object. */
		int tFrames;
		/** The number of columns of the object. */
		int tNoOfCol;
		/** The number of rows of the object. */
		int tNoOfRow;
		
		/**
		 * Instantiates a new objtype.
		 *
		 * @param speed the speed of the object.
		 * @param angle the angle of the object.
		 * @param frames the frames of the object.
		 * @param noOfCol the number of columns of the object.
		 * @param noOfRow the number of rows of the object.
		 */
		objtype(float speed, float angle, int frames, int noOfCol, int noOfRow){
			tSpeed = speed;
			tAngle = angle;
			tFrames = frames;
			tNoOfCol = noOfCol;
			tNoOfRow = noOfRow;
		}
	}
	/** The id of the object. */
	protected objtype mId = objtype.tDefault;
	/** The properties of the object. */
	protected Properties mProperties = new Properties();
	/** The image for the object. */
	protected Bitmap mBitmap;
	/** The speed of the object. */
	protected Speed mSpeed = new Speed();
	/** The pull state of the object. */
	protected int mPullState;//state of object in wpool
	/** The screen lock. */
	protected static Object mScreenLock;
	/** The animation of the object. */
	protected Animate mAnimate;
	/** The wpool counter. */
	protected int wpoolCounter;
	/** The pulled by. */
	protected Whirlpool mPulledBy;
	/** The is playing. */
	protected boolean mIsPlaying;
	/** The graphic type. */
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
     * @param canvas to draw to
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
	 * @return true, if collision with border
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
	 * @param side the side of the border
	 * @param width the width of the border
	 * @param height the height of the border
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
	 * @return the centre point
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
	 * @param y the new top left y position
	 */
	public void setTopLeftY(float y){
		mProperties.setTopLeftY(y);
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
	 * @return the bottom right position
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
	 * @return the width of the object.
	 */
	public int getWidth(){
    	return mProperties.getWidth();
    }
    
    /**
     * Gets the height.
     *
     * @return the height of the object.
     */
    public int getHeight(){
    	return mProperties.getHeight();
    }
	
	/**
	 * Sets the width.
	 *
	 * @param width the new width of the object.
	 */
	public void setWidth(int width){
		mProperties.setWidth(width);
	}
	
	/**
	 * Sets the height.
	 *
	 * @param height the new height of the object.
	 */
	public void setHeight(int height){
		mProperties.setHeight(height);
	}
	
	/**
	 * Move delta x.
	 *
	 * @param deltaX move by a value.
	 */
	public void moveDeltaX(float deltaX){
		synchronized(mScreenLock){
			mProperties.moveDeltaX(deltaX);
    	}
	}
	
	/**
	 * Move delta y.
	 *
	 * @param deltaY move by a value.
	 */
	public void moveDeltaY(float deltaY){
		synchronized(mScreenLock){
			mProperties.moveDeltaY(deltaY);
    	}
	}
	/**
	 * Move delta.
	 *
	 * @param deltaX move by a value.
	 * @param deltaY move by a value.
	 */
	public void moveDelta(float deltaX, float deltaY){
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
	 * Wpool counter.
	 *
	 * @return true, if over a value of 15
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
