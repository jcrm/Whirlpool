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
import android.util.Log;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Point;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Shark extends GraphicObject{
	public enum SharkType{
		tDefault, tAsleep, tFollow, tAttack, tRetreat, tWait;
	}
	private static final float mTopSpeed = 8;
	private SharkType mSharkState = SharkType.tDefault;
	private Bitmap mUpBitmap;
	private Bitmap mDownBitmap;
	private Bitmap mAsleepBitmap;
	private Bitmap mAttackBitmap;
	private Animate mUpAnimate;
	private Animate mDownAnimate;
	private Animate mAttackAnimate;
	private Animate mAsleepAnimate;
	private int mSharkRadius = Constants.getLevel().getLevelHeight()/2;
	private int mDuckCounter = 10;
	private int mWaitCounter = 40;
	private int mWaitTime = 40;
	private Point mStart;
	private Point mDropLocation;

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
		switch(getSpriteSheetIndex()){
		case 0:
			if(mSpeed.getAngle() >=270 || mSpeed.getAngle() <=90){
				canvas.scale(-1, 1);
			}
			canvas.drawBitmap(mBitmap, mAnimate.getPortion(), rect, null); break;
		case 1: canvas.drawBitmap(mUpBitmap, mUpAnimate.getPortion(), rect, null); break;
		case 2: canvas.drawBitmap(mDownBitmap, mDownAnimate.getPortion(), rect, null); break;
		case 3: canvas.drawBitmap(mAsleepBitmap, mAsleepAnimate.getPortion(), rect, null); break;
		case 4: canvas.drawBitmap(mAttackBitmap, mAttackAnimate.getPortion(), rect, null); break;
		default: break;
		}
		canvas.restore();
	}

	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()));
	}
	public void init(int x, int y) {
		mGraphicType = 4;
		mIsPlaying = false;
		mProperties.init(x, y, 100, 100,0.65f,0.65f);

		mStart = new Point(getCentreX(), getCentreY());
		float dX = new Random().nextInt((int)getCentreX());
		float dY = new Random().nextInt(Constants.getLevel().getLevelHeight());
		mDropLocation = new Point((int)dX,(int)dY);

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
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/4)*(getHeight()/4))));
		mSharkRadius = mProperties.getRadius()*2;

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);

		mSharkState = SharkType.tAsleep;
	}
	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		if(mSpeed.getMove() && mSharkState != SharkType.tAsleep && mSharkState != SharkType.tWait){
			moveDeltaX((int) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			moveDeltaY((int) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}
	
	public void frame(){
		// Move Objects
		if(move()){
			float tempSpeed = mSpeed.getSpeed()/Constants.getScreen().getRatio();
			if(tempSpeed<mTopSpeed){
				mSpeed.setSpeed(tempSpeed+0.05f);
				Log.v("Shark Speed", Float.toString(mSpeed.getSpeed()));
			}else{
				mSpeed.setSpeed(mTopSpeed);
			}
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
	public void setDuckPosition(float f, float g){
		mSpeed.setAngle(180+CollisionManager.calcAngle(f, g, getCentreX(), getCentreY()));
	}
	public boolean updateDirection(){
		if(this.getPulledState()!=Constants.STATE_PULLED){
			mDuckCounter++;
			if(mDuckCounter>10){
				mDuckCounter = 0;
				return true;
			}
		}
		return false;
	}
	public boolean checkTime(){
		mWaitCounter++;
		if(mWaitCounter>= mWaitTime){
			mWaitCounter = 0;
			return true;
		}
		return false;
	}
	public void returnToStart(){
		mSpeed.setAngle(180+CollisionManager.calcAngle(mStart.getX(), mStart.getY(), getCentreX(), getCentreY()));
	}
	public void checkAtStart(){	
		if(CollisionManager.circleCollision(getCentreX(), getCentreY(), 10, mStart.getX(), mStart.getY(), 10)){
			mSharkState = SharkType.tAsleep;
		}
	}
	public void moveToDrop(){
		mSpeed.setAngle(180+CollisionManager.calcAngle(mDropLocation.getX(), mDropLocation.getY(), getCentreX(), getCentreY()));	
	}
	public boolean getMovedToDrop(){
		if(CollisionManager.circleCollision(getCentreX(), getCentreY(), 20, mDropLocation.getX(), mDropLocation.getY(), 20)){
			return true;
		}
		return false;
	}
}
