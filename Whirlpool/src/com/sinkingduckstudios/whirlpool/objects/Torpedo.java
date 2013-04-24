package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;


// TODO: Auto-generated Javadoc
/**
 * The Class Torpedo.
 */
public class Torpedo extends GraphicObject {
	
	/** The m is ready to destroy. */
	private boolean mIsReadyToDestroy = false;
	
	/** The Constant mTopSpeed. */
	private static final float mTopSpeed = 12*Constants.getScreen().getRatio();
	
	/** The m duck counter. */
	private int mDuckCounter = 10;
	
	/** The m hit boat. */
	private boolean mHitBoat = false;
	
	/** The m hit boat counter. */
	private int mHitBoatCounter = 0;
	
	/** The m beep counter. */
	private int mBeepCounter = 31;
	
	/** The m is tracking. */
	private boolean mIsTracking; //tracking duck?
	
	/** The m explosion bitmap. */
	private Bitmap mExplosionBitmap;
	
	/** The m explosion animate. */
	private Animate mExplosionAnimate;
	
	/** The m explosion. */
	private boolean mExplosion = false;

	public Torpedo(int x, int y, float angle){
		mId= objtype.tTorpedo;
		init(x, y, angle);
	}
	@Override
	public void draw(Canvas canvas) {
		/*
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
		for(int i = 0; i<4;i++){
			canvas.drawPoint(mProperties.mCollisionRect[i].getX(), mProperties.mCollisionRect[i].getY(), paint);
		}
		*/
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.rotate(mSpeed.getAngle()+180);
		if(mExplosion){
			canvas.drawBitmap(mExplosionBitmap, mExplosionAnimate.getPortion(), rect, null);
		}else{
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect, null);
		}
		canvas.restore();
	}

	public void init(int x, int y, float angle){
		mProperties.init(x, y, 50, 50,0.5f,0.5f,0.35f,0.5f);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));

		mBitmap = SpriteManager.getTorpedo();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mExplosionBitmap = SpriteManager.getTorpedoExplosion();
		mExplosionAnimate = new Animate(11, 3, 4, mExplosionBitmap.getWidth(), mExplosionBitmap.getHeight());

		mSpeed.setMove(true);
		mSpeed.setAngle(angle);
		mSpeed.setSpeed(mId.tSpeed);
		mIsTracking=true;
	}

	@Override
	public void init(){
		init(30,60,mId.tAngle);
	}

	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		if(mHitBoat == false && ++mHitBoatCounter > 40){
			mHitBoatCounter = 0;
			mHitBoat = true;
		}
		if(mSpeed.getMove()){
			moveDeltaX((int) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			moveDeltaY((int) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}

	@Override
	public void borderCollision(ScreenSide side, int width, int height) {
		boolean hit = false;
		switch(side){
		case Top:
			hit = true;
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		case Bottom:
			hit = true;
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case Left:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(-getTopLeftX());
			break;
		case Right:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			break;
		case BottomLeft:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(-getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case BottomRight:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(height-getHeight());
			break;
		case TopLeft:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(-getTopLeftX());
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		case TopRight:
			hit = true;
			mSpeed.horizontalBounce();
			setTopLeftX(width - getWidth());
			mSpeed.verticalBounce();
			setTopLeftY(-getTopLeftY());
			break;
		default:
			break;
		}
		if(hit == true && mIsTracking == false){
			mExplosion = true;
		}
	}

	@Override
	public void frame() {
		if(mExplosion == false){
			if(move()){
				border();
			}
		}
		if(mExplosion){
			if(mExplosionAnimate.getFinished() == false){
				mExplosionAnimate.animateFrame();
			}else{
				mIsReadyToDestroy = true;
			}
		}else{
			mAnimate.animateFrame();
		}
	}

	/**
	 * Gets the checks if is ready to destroy.
	 *
	 * @return the checks if is ready to destroy
	 */
	public boolean getIsReadyToDestroy() {
		return mIsReadyToDestroy;
	}

	/**
	 * Sets the checks if is ready to destroy.
	 *
	 * @param isReadyToDestroy the new checks if is ready to destroy
	 */
	public void setIsReadyToDestroy(boolean isReadyToDestroy) {
		mIsReadyToDestroy = isReadyToDestroy;
	}
	
	/**
	 * Gets the tracking.
	 *
	 * @return the tracking
	 */
	public boolean getTracking(){
		return mIsTracking;
	}
	
	/**
	 * Sets the tracking.
	 *
	 * @param b the new tracking
	 */
	public void setTracking(boolean b){
		mIsTracking=b;
	}
	public void setDuckPosition(float f, float g){
		mSpeed.setAngle(180+CollisionManager.calcAngle(f, g, getCentreX(), getCentreY()));
		float tempSpeed = mSpeed.getSpeed();
		if(tempSpeed<mTopSpeed){
			mSpeed.setSpeed(tempSpeed+1);
		}else{
			mSpeed.setSpeed(mTopSpeed);
		}
	}
	//try get view by id , get id layout, get height and width of view; on button click of menu
	/**
	 * Gets the duck counter.
	 *
	 * @return the duck counter
	 */
	public int getDuckCounter() {
		return mDuckCounter;
	}
	
	/**
	 * Sets the duck counter.
	 *
	 * @param duckCounter the new duck counter
	 */
	public void setDuckCounter(int duckCounter) {
		mDuckCounter = duckCounter;
	}
	public boolean updateDirection(){
		if(mIsTracking){
			mDuckCounter++;
			if(mDuckCounter>10){
				mDuckCounter = 0;
				return true;
			}
		}
		return false;
	}
	public void checkBeep(){
		if(mExplosion == false){
			mBeepCounter++;
			if(mBeepCounter>30){
				mBeepCounter = 0;
				Constants.getSoundManager().playBeepFast();
			}
		}
	}
	
	/**
	 * Gets the hit boat.
	 *
	 * @return the hit boat
	 */
	public boolean getHitBoat() {
		return mHitBoat;
	}
	
	/**
	 * Sets the hit boat.
	 *
	 * @param hitBoat the new hit boat
	 */
	public void setHitBoat(boolean hitBoat) {
		mHitBoat = hitBoat;
	}
	
	/**
	 * Gets the top speed.
	 *
	 * @return the top speed
	 */
	public float getTopSpeed() {
		return mTopSpeed;
	}
	
	/**
	 * Gets the explosion.
	 *
	 * @return the explosion
	 */
	public boolean getExplosion() {
		return mExplosion;
	}
	
	/**
	 * Sets the explosion.
	 *
	 * @param explosion the new explosion
	 */
	public void setExplosion(boolean explosion) {
		mExplosion = explosion;
	}
	
	/**
	 * Gets the dist.
	 *
	 * @return the dist
	 */
	public float getDist() {
		float x1 = getCentreX();
		float y1 = getCentreY();
		float x2 = Constants.getPlayer().getCentreX();
		float y2 = Constants.getPlayer().getCentreY();

		float distX = x2-x1;
		float distY = y2-y1;

		return (distX*distX)+(distY*distY);

	}
}
