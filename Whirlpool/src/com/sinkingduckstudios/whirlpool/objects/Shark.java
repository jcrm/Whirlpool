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
	private Bitmap mUpBitmap;
	private Bitmap mDownBitmap;
	private Bitmap mAsleepBitmap;
	private Bitmap mAttackBitmap;
	private Animate mUpAnimate;
	private Animate mDownAnimate;
	private Animate mAttackAnimate;
	private Animate mAsleepAnimate;
	private int mSharkRadius = Constants.getLevel().getLevelHeight()/4;
	
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
			switch(getSpriteSheetIndex()){
			case 0: canvas.scale(-1, 1); canvas.drawBitmap(mBitmap, mAnimate.getPortion(), rect,  null); break;
			case 1: canvas.drawBitmap(mUpBitmap, mUpAnimate.getPortion(), rect,  null); break;
			case 2: canvas.drawBitmap(mDownBitmap, mDownAnimate.getPortion(), rect,  null); break;
			case 3: canvas.drawBitmap(mAsleepBitmap, mAsleepAnimate.getPortion(), rect,  null); break;
			case 4: canvas.drawBitmap(mAttackBitmap, mAttackAnimate.getPortion(), rect,  null); break;
				default: break;
			}
		canvas.restore();
	}

	@Override
	public void init() {
		mProperties.init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()),
				100, 100);	
		
		mBitmap = SpriteManager.getShark();
		mUpBitmap = SpriteManager.getSharkUp();
		mDownBitmap = SpriteManager.getSharkDown();
		mAsleepBitmap = SpriteManager.getSharkAsleep();
		mAttackBitmap = SpriteManager.getSharkAttack();
		
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		mUpAnimate = new Animate(10, 3, 4, mUpBitmap.getWidth(), mUpBitmap.getHeight());
		mDownAnimate = new Animate(29, 8, 4, mDownBitmap.getWidth(), mDownBitmap.getHeight());
		mAsleepAnimate = new Animate(33, 5, 8, mAsleepBitmap.getWidth(), mAsleepBitmap.getHeight());
		mAttackAnimate = new Animate(8, 2, 4, mAttackBitmap.getWidth(), mAttackBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		
		mSharkState = SharkType.tAsleep;
	}
	public void init(int x, int y) {
		mProperties.init(x, y, 100, 100);
		
		mBitmap = SpriteManager.getShark();
		mUpBitmap = SpriteManager.getSharkUp();
		mDownBitmap = SpriteManager.getSharkDown();
		mAsleepBitmap = SpriteManager.getSharkAsleep();
		mAttackBitmap = SpriteManager.getSharkAttack();
		
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		mUpAnimate = new Animate(10, 3, 4, mUpBitmap.getWidth(), mUpBitmap.getHeight());
		mDownAnimate = new Animate(29, 8, 4, mDownBitmap.getWidth(), mDownBitmap.getHeight());
		mAsleepAnimate = new Animate(33, 5, 8, mAsleepBitmap.getWidth(), mAsleepBitmap.getHeight());
		mAttackAnimate = new Animate(8, 2, 4, mAttackBitmap.getWidth(), mAttackBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		
		mSharkState = SharkType.tAsleep;
	}
	@Override
	public boolean move() {
		if(mSpeed.getMove() && mSharkState != SharkType.tAsleep){
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
		switch(getSpriteSheetIndex()){
		case 0: mAnimate.animateFrame(); break;
		case 1: mUpAnimate.animateFrame(); break;
		case 2: mDownAnimate.animateFrame(); break;
		case 3: mAsleepAnimate.animateFrame(); break;
		case 4: mAttackAnimate.animateFrame(); break;
			default: break;
		}
	}
	public SharkType getSharkState() {
		return mSharkState;
	}
	public void setSharkState(SharkType sharkState) {
		mSharkState = sharkState;
	}
	private int getSpriteSheetIndex(){
		if(mSharkState == SharkType.tAsleep){
			return 3;
		}
		if(mSharkState == SharkType.tAttack){
			return 4;
		}
		if (getSpeed().getAngle()>240&&getSpeed().getAngle()<300){
			return 1;
		}
		if (getSpeed().getAngle()>60 && getSpeed().getAngle()<120){
			return 2;
		}
		return 0;
	}
	public int getSharkRadius() {
		return mSharkRadius;
	}
	public void setSharkRadius(int sharkRadius) {
		mSharkRadius = sharkRadius;
	}
}
