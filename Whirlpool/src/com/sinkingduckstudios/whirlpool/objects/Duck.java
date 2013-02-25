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

public class Duck extends GraphicObject{
	//enum for collision checking
	private enum coltype{
		cDefault, cShark, cDiver, cFrog, cWhirl;
	}
	//collision variables 
	public coltype cID = coltype.cDefault;
	private int mCollisonCount = -1;
	private int mWaitTimer = 0;
	public Duck(){
		mId = objtype.tDuck;
		init();
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
		
		mCollision.init(30, 60, mBitmap.getWidth()/mId.tFrames, mBitmap.getHeight());		

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

	public void changeCollisionType(boolean newColType){
		if(newColType){
			cID = coltype.cWhirl;
		}else if (cID == coltype.cWhirl){
			cID = coltype.cDefault;
		}
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
		colMovement();
		if(move()){
			//only detect border if not in wpool
			if (!getPullState())
				if(border()){
					Constants.getSoundManager().playDucky();
				}
		}
		mAnimate.animateFrame();
	}
	//collision checking
	public void checkObjCollision(GraphicObject.objtype id, Collision collision){
		if(cID == coltype.cDefault){
			switch(id){
			case tShark: 
				if(CollisionManager.circleCollision(this.mCollision, collision)){
					cID = coltype.cShark;
					Constants.getSoundManager().playDucky();
				}
				break;
			case tDiver:
				if(CollisionManager.circleCollision(this.mCollision, collision)){
					colDiverFrog();
					cID = coltype.cDiver;
					Constants.getSoundManager().playDucky();
				}
				break;
			case tFrog:
				//TODO bounding boxes for angled collisions
				if(CollisionManager.circleCollision(this.mCollision, collision)){
					colDiverFrog();
					cID = coltype.cFrog;
					Constants.getSoundManager().playDucky();
				}
				break;
			case tWhirl:
				cID = coltype.cWhirl;
				break;
			default: break;
			}
		}
	}
	//collision movement
	private void colMovement(){
		//TODO: This is why duck stops moving if wpool over after collide. 
		//It wont keep counting till wpool goes away
		//Wpool wont go away coz duck is in it. Deadlock
		if((cID != coltype.cDefault && cID != coltype.cWhirl) && mCollisonCount >= 0){
			if(mCollisonCount == 30){
				getSpeed().setSpeed(0);
				getSpeed().setAngle(0);
			}else if(mCollisonCount == 60){
				getSpeed().setSpeed(4);				
				getSpeed().setAngle(0);
				cID = coltype.cDefault;
				mCollisonCount = -1;
			}
			mCollisonCount++;
		}
	}
	private void colDiverFrog(){
		getSpeed().setSpeed(5);
		getSpeed().setAngle(new Random().nextInt(90)+135);
		mCollisonCount = 0;
	}
	private void colShark(float s, float a){
		getSpeed().setSpeed(s);
		setAngle(a);
		mCollisonCount = 0;    	
	}

}
