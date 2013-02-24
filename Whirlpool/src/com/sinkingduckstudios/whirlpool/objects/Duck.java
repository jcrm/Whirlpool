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
			canvas.translate(getX(), getY());
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

		mWidth = mBitmap.getWidth()/mId.tFrames;
		mHeight = mBitmap.getHeight();

		setX(30.0f);
		setY(60.0f);

		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		mRadius =  (int) Math.sqrt(((float)mWidth*mWidth) + ((float)mHeight*mHeight));
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
			shiftX((float) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			shiftY((float) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
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
	public void borderCollision(ScreenSide side, float width, float height) {
		switch(side){
		case Top:
			mSpeed.verticalBounce();
			setActualY(-getActualY());
			break;
		case Bottom:
			mSpeed.verticalBounce();
			setActualY(height - getHeight());
			break;
		case Left:
			mSpeed.horizontalBounce();
			setActualX(-getActualX());
			break;
		case Right:
			mSpeed.horizontalBounce();
			setActualX(width - getWidth());
			break;
		case BottomLeft:
			mSpeed.verticalBounce();
			setActualY(height - getHeight());
			mSpeed.horizontalBounce();
			setActualX(-getActualX());
			break;
		case BottomRight:
			mSpeed.verticalBounce();
			setActualY(height - getHeight());
			mSpeed.horizontalBounce();
			setActualX(width - getWidth());
			break;
		case TopLeft:
			mSpeed.verticalBounce();
			setActualY(-getActualY());
			mSpeed.horizontalBounce();
			setActualX(-getActualX());
			break;
		case TopRight:
			mSpeed.verticalBounce();
			setActualY(-getActualY());
			mSpeed.horizontalBounce();
			setActualX(width - getWidth());
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
	public void checkObjCollision(GraphicObject otherGraphic){
		if(cID == coltype.cDefault){
			switch(otherGraphic.getId()){
			case tShark: 
				if(CollisionManager.boxCollision(this, otherGraphic)){
					colShark(otherGraphic.getSpeed().getSpeed(), otherGraphic.getSpeed().getAngle());
					cID = coltype.cShark;
					Constants.getSoundManager().playDucky();
				}
				break;
			case tDiver:
				if(CollisionManager.boxCollision(this, otherGraphic)){
					colDiverFrog();
					cID = coltype.cDiver;
					Constants.getSoundManager().playDucky();
				}
				break;
			case tFrog:
				//TODO bounding boxes for angled collisions
				if(CollisionManager.boxCollision(this, otherGraphic)){
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
