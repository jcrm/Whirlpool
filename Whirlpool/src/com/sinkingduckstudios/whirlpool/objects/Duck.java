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
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
import com.sinkingduckstudios.whirlpool.movement.Properties;

public class Duck extends GraphicObject{
	//enum for collision checking
	private enum CollisionType{
		cDefault, cShark, cDiver, cFrog, cBoat, cTorpedo;
	}
	//collision variables 
	public CollisionType cID = CollisionType.cDefault;
	private int mCollisionCount = -1;
	private Bitmap mBitmap[] = new Bitmap[3];
	private Animate mAnimate[] = new Animate[3];
	private boolean mInvincibility = false;
	private boolean mFinished = false;
	private static final float mTopSpeed = 8*Constants.getScreen().getRatio();
	
	public Duck(){
		mId = objtype.tDuck;
		init();
	}
	public Duck(int x, int y){
		mId = objtype.tDuck;
		init(x, y);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			/*ColorMatrix cm = new ColorMatrix();
			cm.set(new float[]{
								0.8f,0,0,0,100,
								0,0.4f,0,0,80,
								0,0,0.9f,0,20,
								0,0,0,1,0
								
			});
			Paint paint = new Paint();
			paint.setColorFilter(new ColorMatrixColorFilter(cm));
			 */
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			if(mSpeed.getAngle() > 90 && mSpeed.getAngle() < 270){
				canvas.scale(-1, 1);
			}
			int i = getSpriteSheetIndex();
			canvas.drawBitmap(mBitmap[i], mAnimate[i].getPortion(), rect,  null);
		canvas.restore();
	}

	@Override
	public void init() {
		mProperties.init(30, 60, 60, 60);		
		
		for(int i=0; i<3; i++){
			int frames;
			if(i==0){
				frames = mId.tFrames;
			}else{
				frames = mId.tFrames+3;
			}
			mBitmap[i] = SpriteManager.getDuck(i);
			mAnimate[i] = new Animate(frames, mId.tNoOfRow, frames, mBitmap[i].getWidth(), mBitmap[i].getHeight());
		}

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}
	public void init(int x, int y) {
		mProperties.init(x, y, 60, 60);
		
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
	@Override
	public boolean move() {
		if(mSpeed.getMove()){
			moveDeltaX((int) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			moveDeltaY((int) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}


	@Override
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

	public void frame(){
		// Move Objects
		collisonMovement();
		if(move()){
			//only detect border if not in wpool
			if (!getPullState()){
				if(border()){
					Constants.getSoundManager().playDucky();
				}
			}
		}
		mAnimate[getSpriteSheetIndex()].animateFrame();
	}
	public boolean checkObjectCollision(GraphicObject.objtype id, Properties otherProperties, int radius){
		boolean inRadius = false;
		switch(id){
		case tBoat:
			if(CollisionManager.circleCollision(mProperties, otherProperties.getCentreX(), otherProperties.getCentreY(), radius)){
				inRadius = true;
			}
			if(inRadius ==true){
				if(cID == CollisionType.cDefault && mInvincibility == false){
					if(CollisionManager.circleCollision(mProperties, otherProperties)){
						cID = CollisionType.cBoat;
						collisionDiverFrogBoat();
					}
				}
				return true;
			}
			break;
		case tShark:
			if(CollisionManager.circleCollision(mProperties, otherProperties.getCentreX(), otherProperties.getCentreY(), radius)){
				inRadius = true;
				return true;
			}
			break;
		default: break;
		}
		return false;
	}
	//collision checking
	public boolean checkObjectCollision(GraphicObject.objtype id, Properties otherProperties){
			switch(id){
			case tDiver:
				if(cID == CollisionType.cDefault && mInvincibility == false){
					if(CollisionManager.circleCollision(mProperties, otherProperties)){
						cID = CollisionType.cDiver;
						collisionDiverFrogBoat();
					}
				}
				break;
			case tFrog:
				if(cID == CollisionType.cDefault && mInvincibility == false){
					if(CollisionManager.circleCollision(mProperties, otherProperties)){
						cID = CollisionType.cFrog;
						collisionDiverFrogBoat();
					}
				}
				break;
			case tTorpedo:
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					if(cID != CollisionType.cShark && mInvincibility == false){
						cID = CollisionType.cTorpedo;
						collisionTorpedo();
					}
					return true;
				}
				break;
			case tShark:
				if(cID!=CollisionType.cShark && mInvincibility == false){
					if(CollisionManager.circleCollision(mProperties, otherProperties)){
						cID = CollisionType.cShark;
						collisionShark();
					}
					return true;
				}
				break;
			default: break;
			}
		return false;
	}
	//collision movement
	private void collisonMovement(){
		//TODO: This is why duck stops moving if wpool over after collide. 
		//It wont keep counting till wpool goes away
		//Wpool wont go away coz duck is in it. Deadlock
		if(mCollisionCount >=0){
			if(cID == CollisionType.cBoat || cID == CollisionType.cFrog || cID == CollisionType.cDiver){
				if(mCollisionCount == 30){
					getSpeed().setSpeed(0);
				}else if(mCollisionCount == 45){
					getSpeed().setSpeed(8);				
					getSpeed().setAngle(0);
					cID = CollisionType.cDefault;
					mInvincibility = true;
					mCollisionCount = 0;
				}
			}else if(cID == CollisionType.cTorpedo){
				if(mCollisionCount == 10){
					getSpeed().setSpeed(8);			
					getSpeed().setAngle(0);
					cID = CollisionType.cDefault;
					mInvincibility = true;
					mCollisionCount = 0;
				}
			}else if(cID == CollisionType.cShark){
				cID = CollisionType.cDefault;
				mInvincibility = false;
				mCollisionCount = -1;
			}else if(mInvincibility == true){
				if(mCollisionCount == 60){
					mInvincibility = false;
					mCollisionCount = -1;
				}
			}
			mCollisionCount++;
		}
	}
	private void collisionDiverFrogBoat(){
		getSpeed().setSpeed(5);
		getSpeed().setAngle(new Random().nextInt(90)+135);
		mCollisionCount = 0;
		Constants.getSoundManager().playDucky();
	}
	private void collisionTorpedo(){
		getSpeed().setSpeed(0);
		mCollisionCount = 0;
		Constants.getSoundManager().playDucky();
	}
	private void collisionShark(){
		mCollisionCount = 0;
		Constants.getSoundManager().playDucky();
	}
	private int getSpriteSheetIndex(){
		if (getSpeed().getAngle()>240&&getSpeed().getAngle()<300)
			return 1;
		if (getSpeed().getAngle()>60 && getSpeed().getAngle()<120)
			return 2;
		return 0;
	}
	public boolean getInvincibility() {
		return mInvincibility;
	}
	public void setInvincibility(boolean invincibility) {
		mInvincibility = invincibility;
	}
	public static float getTopSpeed() {
		return mTopSpeed;
	}
	public boolean getFinished() {
		return mFinished;
	}
	public void setFinished(boolean finished) {
		mFinished = finished;
	}
}
