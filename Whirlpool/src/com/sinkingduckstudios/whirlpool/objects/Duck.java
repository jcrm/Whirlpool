/*
 * Author: Jake Morey, Fraser Tomison, Jordan O'Hare
 * Content:
 * Jordan O'Hare: created basic version based upon parent class.
 * Jake Morey: added most of the collision code.
 * Fraser Tomison: added shift angle from collision, and sprite index based upon angle. 
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

/**
 * The Class Duck.
 */
public class Duck extends GraphicObject{
	/**
	 * The Enum CollisionType.
	 */
	private enum CollisionType{
		cDefault, cShark, cDiver, cFrog, cBoat, cTorpedo;
	}
	/** The collision id. */
	public CollisionType cID = CollisionType.cDefault;
	/** The collision count. */
	private int mCollisionCount = -1;
	/** The duck images. */
	private Bitmap mBitmap[] = new Bitmap[3];
	/** The duck animations. */
	private Animate mAnimate[] = new Animate[3];
	/** The invincibility variable. */
	private boolean mInvincibility = false;
	/** The finished variable. */
	private boolean mFinished = false;
	/** The shark attacked. */
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
	 * @param x the x position
	 * @param y the y position
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
	 * Inits the duck.
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
		//update collision rect
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
		collisionMovement();
		if(move()){
			//only detect border if not in wpool
			if (getPulledState()==Constants.STATE_FREE){
				if(border()){
					Constants.getSoundManager().playDucky();
				}
			}
		}
		//animate depending on the angle of the object
		mAnimate[getSpriteSheetIndex()].animateFrame();
	}
	/**
	 * Check object collision.
	 *
	 * @param id the id of the object
	 * @param otherProperties the properties of the second object
	 * @param radius the radius of the second object
	 * @return true, if collision
	 */
	public boolean checkObjectCollision(GraphicObject.objtype id, Properties otherProperties, int radius){
		boolean inRadius = false;
		switch(id){
		case tBoat:
			//check if the duck is in a radius from the boat
			if(CollisionManager.circleCollision(mProperties, (int)otherProperties.getCentreX(), (int)otherProperties.getCentreY(), radius)){
				inRadius = true;
			}
			if(inRadius ==true){
				if(cID == CollisionType.cDefault && mInvincibility == false){
					//check for collision with boat
					if(CollisionManager.circleCollision(mProperties, otherProperties)){
						if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
							cID = CollisionType.cBoat;
							//do boat collision based upon angle between boat and duck
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
			//check that the duck is within a certain radius
			if(CollisionManager.circleCollision(mProperties, (int)otherProperties.getCentreX(), (int)otherProperties.getCentreY(), radius)){
				return true;
			}
			break;
		default: break;
		}
		return false;
	}
	/**
	 * Check object collision.
	 * Check for direct object collision with duck.
	 * @param id the id of the second object
	 * @param otherProperties the properties of the second object
	 * @return true, if collision
	 */
	public boolean checkObjectCollision(GraphicObject.objtype id, Properties otherProperties){
		switch(id){
		case tDiver:
			if(cID == CollisionType.cDefault && mInvincibility == false){
				//check for collision with diver
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
						cID = CollisionType.cDiver;
						//do diver collision code with angle based upon duck position and divers position
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
				//check for collision with frog
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					if(CollisionManager.boundingBoxCollision(mProperties, otherProperties)){
						cID = CollisionType.cFrog;
						//do frog collision code with angle based upon duck position and frogs position
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
			//if collision with torpedo then check being dragged by shark
			//if not colliding with shark then call torpedo collision code and return true so torpedo can be destroyed
			//if being dragged by shark the just return true
			if(CollisionManager.circleCollision(mProperties, otherProperties)){
				if(cID != CollisionType.cShark && mInvincibility == false){
					cID = CollisionType.cTorpedo;
					collisionTorpedo();
				}
				return true;
			}
			break;
		case tShark:
			//if not collided with shark or invincible check for cillision
			//if collision then change collision type
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
	/**
	 * Collision movement.
	 */
	private void collisionMovement(){
		//if recently collided with shark or duck is invincible then
		//change speed, or angle or disable invincibility
		if(mCollisionCount >=0){
			if(cID == CollisionType.cShark){
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
	 * Collision with diver or frog or boat.
	 *
	 * @param angle the angle
	 */
	private void collisionDiverFrogBoat(float angle){
		//change the speed of the duck and play the duck sound
		//set invincibility to be true and shift the angle
		getSpeed().setSpeed(5);
		getSpeed().setAngle(angle+180);
		Constants.getSoundManager().playDucky();
		cID = CollisionType.cDefault;
		mInvincibility = true;
		mCollisionCount = 0;
	}
	
	/**
	 * Collision with torpedo.
	 */
	private void collisionTorpedo(){
		//stop the speed of the duck and play the duck sound
		//set invincibility to be true
		getSpeed().setSpeed(0);
		Constants.getSoundManager().playDucky();
		cID = CollisionType.cDefault;
		mInvincibility = true;
		mCollisionCount = 0;
	}
	
	/**
	 * Gets the sprite sheet index based upon angle.
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
