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
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Shark extends GraphicObject{
	public enum SharkType{
		tDefault, tAsleep, tFollow, tAttack, tRetreat;
	}
	private SharkType mSharkState = SharkType.tDefault;
	private Bitmap mAttackBitmap;
	private Bitmap mAsleepBitmap;
	private Animate mAttackAnimation;
	private Animate mAsleepAnimation;
	public Shark(){
		mId = objtype.tShark;
		init();
	}
	public Shark(int x, int y){
		mId = objtype.tShark;
		init(x, y);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			canvas.scale(-1, 1);
			canvas.drawBitmap(mBitmap, mAnimate.getPortion(), rect,  null);
		canvas.restore();
	}

	@Override
	public void init() {
		mProperties.init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()),
				100, 100);	
		mBitmap = SpriteManager.getShark();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		
		mSharkState = SharkType.tAsleep;
	}
	public void init(int x, int y) {
		mProperties.init(x, y, 100, 100);	
		mBitmap = SpriteManager.getShark();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		
		mSharkState = SharkType.tAsleep;
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
		if(move()){
			border();
		}
		mAnimate.animateFrame();
	}
	public SharkType getSharkState() {
		return mSharkState;
	}
	public void setSharkState(SharkType sharkState) {
		mSharkState = sharkState;
	}

}
