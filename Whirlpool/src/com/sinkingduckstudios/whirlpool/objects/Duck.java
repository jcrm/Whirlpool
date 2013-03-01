/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.objects;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Imports;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.movement.Properties;

public class Duck extends GraphicObject{
	//enum for collision checking
	private enum CollisionType{
		cDefault, cShark, cDiver, cFrog, cBoat, cBoatRadius, cTorpedo;
	}
	//collision variables 
	public CollisionType cID = CollisionType.cDefault;
	private int mCollisionCount = -1;
	private int mWaitTimer = 0;
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
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			if(mSpeed.getAngle() > 90 && mSpeed.getAngle() < 270){
				canvas.scale(-1, 1);
			}
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
	}

	@Override
	public void init() {
		mBitmap = Imports.getDuck();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
		
		mProperties.init(30, 60, mBitmap.getWidth()/mId.tFrames, mBitmap.getHeight());		

		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}
	@Override
	public void init(int x, int y) {
		mBitmap = Imports.getDuck();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
	
		mProperties.init(x, y, mBitmap.getWidth()/mId.tFrames, mBitmap.getHeight());	
		
		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		
	}
	@Override
	public boolean move() {
		if(mWaitTimer >= 0){
			mWaitTimer++;
			if(mWaitTimer > 40){
				mSpeed.setMove(true);
				mWaitTimer = -1;
			}
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
		mAnimate.animateFrame();
	}
	//collision checking
	public boolean checkObjectCollision(GraphicObject.objtype id, Properties otherProperties, int boatRadius){
		if(cID == CollisionType.cDefault && mCollisionCount <0){
			switch(id){
			case tDiver:
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					cID = CollisionType.cDiver;
					collisionDiverFrogBoat();
				}
				break;
			case tFrog:
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					cID = CollisionType.cFrog;
					collisionDiverFrogBoat();
				}
				break;
			case tBoat:
				boolean inRadius = false;
				if(CollisionManager.circleCollision(mProperties, otherProperties.getCentreX(), otherProperties.getCentreY(), boatRadius)){
					Constants.getSoundManager().playDucky();
					inRadius = true;
				}
				if(inRadius ==true){
					if(CollisionManager.circleCollision(mProperties, otherProperties)){
						cID = CollisionType.cBoat;
						collisionDiverFrogBoat();
					}
					return true;
				}
				break;
			case tTorpedo:
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					Constants.getSoundManager().playDucky();
				}
				return true;
			case tShark: 
				if(CollisionManager.circleCollision(mProperties, otherProperties)){
					cID = CollisionType.cShark;
					Constants.getSoundManager().playDucky();
				}
				return true;
			default: break;
			}
		}
		return false;
	}
	//collision movement
	private void collisonMovement(){
		//TODO: This is why duck stops moving if wpool over after collide. 
		//It wont keep counting till wpool goes away
		//Wpool wont go away coz duck is in it. Deadlock
		if(cID != CollisionType.cDefault && mCollisionCount >= 0){
			if(mCollisionCount == 30){
				getSpeed().setSpeed(0);
				getSpeed().setAngle(0);
			}else if(mCollisionCount == 60){
				getSpeed().setSpeed(4);				
				getSpeed().setAngle(0);
				cID = CollisionType.cDefault;
				mCollisionCount = -1;
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

}
