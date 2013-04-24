/*
 * Author:
 * Last Updated:
 * Content:
 *
 *
 */
package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
import com.sinkingduckstudios.whirlpool.movement.Properties;

// TODO: Auto-generated Javadoc
/**
 * The Class Duck.
 */
public class Duck extends GraphicObject{
	//enum for collision checking
	/**
	 * The Enum CollisionType.
	 */
	private enum CollisionType{
		
		/** The c default. */
		cDefault, 
 /** The c shark. */
 cShark, 
 /** The c diver. */
 cDiver, 
 /** The c frog. */
 cFrog, 
 /** The c boat. */
 cBoat, 
 /** The c torpedo. */
 cTorpedo;
	}
	//collision variables
	/** The c id. */
	public CollisionType cID = CollisionType.cDefault;
	
	/** The m collision count. */
	private int mCollisionCount = -1;
	
	/** The m bitmap. */
	private Bitmap mBitmap[] = new Bitmap[3];
	
	/** The m animate. */
	private Animate mAnimate[] = new Animate[3];
	
	/** The m invincibility. */
	private boolean mInvincibility = false;
	
	/** The m finished. */
	private boolean mFinished = false;
	
	/** The m shark attack. */
	private boolean mSharkAttack = false;
	
	/** The Constant mTopSpeed. */
	private static final float mTopSpeed = 8*Constants.getScreen().getRatio();

	/**
	 * Instantiates a new duck.
	 */
	public Duck(){
		mId = objtype.tDuck;
		init();
	}
	
	/**
	 * Instantiates a new duck.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public Duck(int x, int y){
		mId = objtype.tDuck;
		init(x, y);
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		/*Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
		for(int i = 0; i<4;i++){
			canvas.drawPoint(mProperties.mCollisionRect[i].getX(), mProperties.mCollisionRect[i].getY(), paint);
		}
		paint.setColor(Color.GREEN);
		canvas.drawPoint(getCentreX(), getCentreY(), paint);
		paint.setColor(Color.WHITE);
		canvas.drawPoint(getTopLeftX(), getTopLeftY(), paint);
		canvas.drawPoint(getBottomRightX(), getBottomRightY(), paint);
*/
		if(mSharkAttack == false){
			canvas.save();

			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			if(mSpeed.getAngle() > 90 && mSpeed.getAngle() < 270){
				canvas.scale(-1, 1);
			}
			int i = getSpriteSheetIndex();
			canvas.drawBitmap(mBitmap[i], mAnimate[i].getPortion(), rect, null);
			canvas.restore();
		}
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#init()
	 */
	@Override
	public void init() {
		init(30, 60);
	}
	
	/**
	 * Inits the.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void init(int x, int y) {
		mProperties.init(x, y, 60, 60,0.6f,0.6f);

		for(int i=0; i<3; i++){
			int frames;
			if(i==0){
				frames = mId.tFrames;
			}else{
				frames = mId.tFrames+3;
			}
			mBitmap[i] = SpriteManager.getDuck(i);
			if (i==0)
				mAnimate[i] = new Animate(frames, mId.tNoOfRow, mId.tNoOfCol, mBitmap[i].getWidth(), mBitmap[i].getHeight());
			else
				mAnimate[i] = new Animate(19, 3, 7, mBitmap[i].getWidth(), mBitmap[i].getHeight());
		}

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#move()
	 */
	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		if(mSpeed.getMove() && mSharkAttack == false){
			if((mSpeed.getSpeed()/Constants.getScreen().getRatio())<mId.tSpeed)
				mSpeed.setSpeed((mSpeed.getSpeed()/Constants.getScreen().getRatio())+0.2f);
			if((mSpeed.getSpeed()/Constants.getScreen().getRatio())>mId.tSpeed)
				mSpeed.setSpeed((mSpeed.getSpeed()/Constants.getScreen().getRatio())-0.2f);
			
			moveDeltaX( (float)(mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			moveDeltaY( (float)(mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#frame()
	 */
	public void frame(){
		// Move Objects
		collisonMovement();
		if(move()){
			//only detect border if not in wpool
			if (getPulledState()==Constants.STATE_FREE){
				if(border()){
					Constants.getSoundManager().playDucky();
				}
			}
		}
		mAnimate[getSpriteSheetIndex()].animateFrame();
	}
	
	//fml plz rename 
	/**
	 * Check object collision.
	 *
	 * @param id the id
	 * @param otherProperties the other properties
	 * @param radius the radius
	 * @return true, if successful
	 */
	public boolean checkObjectCollision(GraphicObject.objtype id, Properties otherProperties, int radius){
		boolean inRadius = false;
		switch(id){
		case tBoat:
			if(CollisionManager.circleCollision(mProperties, (int)otherProperties.getCentreX(), (int)otherProperties.getCentreY(), radius)){
				inRadius = true;
			}
			if(inRadius ==true){
				if(cID == CollisionType.cDefault && mInvincibility == false){
					if(CollisionManager.circleCollision(mProperties, otherProperties)){
						if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
							cID = CollisionType.cBoat;
							collisionDiverFrogBoat(
									CollisionManager.calcAngle(	mProperties.getRealCentre().getX(), 
											mProperties.getRealCentre().getY(), 
											otherProperties.getRealCentre().getX(), 
											otherProperties.getRealCentre().getY()));
						}
					}
				}
				return true;
			}
			break;
		case tShark:
			if(CollisionManager.circleCollision(mProperties, (int)otherProperties.getCentreX(), (int)otherProperties.getCentreY(), radius)){
				return true;
			}
			break;
		default: break;
		}
		return false;
	}
	//collision checking
	/**
	 * Check object collision.
	 *
	 * @param id the id
	 * @param otherProperties the other properties
	 * @return true, if successful
	 */
	public boolean checkObjectCollision(GraphicObject.objtype id, Properties otherProperties){
		switch(id){
		case tDiver:
			if(cID == CollisionType.cDefault && mInvincibility == false){
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
						cID = CollisionType.cDiver;
						collisionDiverFrogBoat(
								CollisionManager.calcAngle(	mProperties.getRealCentre().getX(), 
										mProperties.getRealCentre().getY(), 
										otherProperties.getRealCentre().getX(), 
										otherProperties.getRealCentre().getY()));
					}
				}
			}
			break;
		case tFrog:
			if(cID == CollisionType.cDefault && mInvincibility == false){
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
						cID = CollisionType.cFrog;
						collisionDiverFrogBoat(
								CollisionManager.calcAngle(	mProperties.getRealCentre().getX(), 
										mProperties.getRealCentre().getY(), 
										otherProperties.getRealCentre().getX(), 
										otherProperties.getRealCentre().getY()));
					}
				}
			}
			break;
		case tTorpedo:
			if(CollisionManager.circleCollision(mProperties, otherProperties)){
				if(cID != CollisionType.cShark && mInvincibility == false){
					//if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
						cID = CollisionType.cTorpedo;
						collisionTorpedo();
					//}
				}
				return true;
			}
			break;
		case tShark:
			if(cID!=CollisionType.cShark && mInvincibility == false){
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
						cID = CollisionType.cShark;
						mCollisionCount = 0;
						mSharkAttack = true;
						return true;
					}
				}
			}
			break;
		default: break;
		}
		return false;
	}
	//collision movement
	/**
	 * Collison movement.
	 */
	private void collisonMovement(){
		
		if(mCollisionCount >=0){
			if(cID == CollisionType.cBoat || cID == CollisionType.cFrog || cID == CollisionType.cDiver){
				if(mCollisionCount == 1){
					cID = CollisionType.cDefault;
					mInvincibility = true;
					mCollisionCount = 0;
				}
			}else if(cID == CollisionType.cTorpedo){
				if(mCollisionCount == 1){
					cID = CollisionType.cDefault;
					mInvincibility = true;
					mCollisionCount = 0;
				}
			}else if(cID == CollisionType.cShark){
				if(mSharkAttack == false){
					if(mCollisionCount == 10){
						cID = CollisionType.cDefault;
						mInvincibility = true;
						getSpeed().setSpeed(8);	
						mCollisionCount = -1;
					}else{
						getSpeed().setSpeed(0);	
						getSpeed().setAngle(0);	
					}
				}else{
					mCollisionCount = 0;
				}
			}else if(mInvincibility == true){
				if(mCollisionCount == 60){
					mInvincibility = false;
					mCollisionCount = -1;
				}
			}
			mCollisionCount++;
		}
	}
	
	/**
	 * Collision diver frog boat.
	 *
	 * @param angle the angle
	 */
	private void collisionDiverFrogBoat(float angle){
		getSpeed().setSpeed(5);
		getSpeed().setAngle(angle+180);
		mCollisionCount = 0;
		Constants.getSoundManager().playDucky();
	}
	
	/**
	 * Collision torpedo.
	 */
	private void collisionTorpedo(){
		getSpeed().setSpeed(0);
		mCollisionCount = 0;
		Constants.getSoundManager().playDucky();
	}
	
	/**
	 * Gets the sprite sheet index.
	 *
	 * @return the sprite sheet index
	 */
	private int getSpriteSheetIndex(){
		if (getSpeed().getAngle()>240&&getSpeed().getAngle()<300)
			return 1;
		if (getSpeed().getAngle()>60 && getSpeed().getAngle()<120)
			return 2;
		return 0;
	}
	
	/**
	 * Gets the invincibility.
	 *
	 * @return the invincibility
	 */
	public boolean getInvincibility() {
		return mInvincibility;
	}
	
	/**
	 * Sets the invincibility.
	 *
	 * @param invincibility the new invincibility
	 */
	public void setInvincibility(boolean invincibility) {
		mInvincibility = invincibility;
	}
	
	/**
	 * Gets the top speed.
	 *
	 * @return the top speed
	 */
	public static float getTopSpeed() {
		return mTopSpeed;
	}
	
	/**
	 * Gets the finished.
	 *
	 * @return the finished
	 */
	public boolean getFinished() {
		return mFinished;
	}
	
	/**
	 * Sets the finished.
	 *
	 * @param finished the new finished
	 */
	public void setFinished(boolean finished) {
		mFinished = finished;
	}
	
	/**
	 * Gets the shark attack.
	 *
	 * @return the shark attack
	 */
	public boolean getSharkAttack() {
		return mSharkAttack;
	}
	
	/**
	 * Sets the shark attack.
	 *
	 * @param sharkAttack the new shark attack
	 */
	public void setSharkAttack(boolean sharkAttack) {
		mSharkAttack = sharkAttack;
	}
}
