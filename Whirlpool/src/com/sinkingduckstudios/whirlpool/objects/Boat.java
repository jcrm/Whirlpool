package com.sinkingduckstudios.whirlpool.objects;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Boat extends GraphicObject{
	private enum BoatType{ bDefault, bReady, bAttack, bTorpedo, bFinishing, bWaiting};
	private BoatType mBoatState = BoatType.bDefault;
	private int mBoatRadius = Constants.getLevel().getLevelHeight()/2;
	private int mTorpedoCount = -1;
	private boolean mBroken = false;

	public Boat(){
		mId = objtype.tBoat;
		init();
	}
	public Boat(int x, int y){
		mId = objtype.tBoat;
		init(x, y);
	}
	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
		for(int i = 0; i<4;i++){
			canvas.drawPoint(mProperties.mCollisionRect[i].getX(), mProperties.mCollisionRect[i].getY(), paint);
		}
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect, null);
		canvas.restore();
	}

	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()/4));
	}
	public void init(int x, int y) {
		mGraphicType = 3;
		mIsPlaying = false;
		
		mBoatState = BoatType.bReady;
		x-=((96/Constants.getScreen().getRatio())/2);
		y-=((96/Constants.getScreen().getRatio())/2);
		mProperties.init(x, y, 96, 96);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/4)*(getHeight()/4))));
		mBitmap = SpriteManager.getBoat();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		CollisionManager.updateCollisionRect(mProperties, -mSpeed.getAngleRad());
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
	public void frame(){
		// Move Objects
		if(move()){
			border();
		}
		if(checkBroken() == false){
			incrementCounter();
			if(mBoatState == BoatType.bAttack && mAnimate.getNoOfFrames()>=44){
				mBoatState = BoatType.bTorpedo;
			}else if(mBoatState == BoatType.bFinishing && mAnimate.getFinished()){	
				mBitmap = SpriteManager.getBoat();
				mAnimate.Reset(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight(),3);
				mBoatState = BoatType.bWaiting;	
			}
		}
		mAnimate.animateFrame();
	}

	public int getBoatRadius() {
		return mBoatRadius;
	}

	public void setBoatRadius(int boatRadius) {
		mBoatRadius = boatRadius;
	}
	public int getTorpedoCount() {
		return mTorpedoCount;
	}
	public void setTorpedoCount(int torpedoCount) {
		mTorpedoCount = torpedoCount;
	}
	public void incrementCounter(){
		if((mBoatState == BoatType.bWaiting) && (++mTorpedoCount>=0)){
			if(mTorpedoCount == 120){
				mBoatState = BoatType.bReady;
				mTorpedoCount = -1;
			}
		}
	}
	public boolean getNewTorpedo() {
		if(mBoatState == BoatType.bTorpedo){
			mBoatState = BoatType.bFinishing;
			return true;
		}else{
			return false;
		}
	}
	public void changeAnimation(){
		if(mBoatState == BoatType.bReady && mBroken == false){
			mBitmap= SpriteManager.getBoatAttack();
			mAnimate.Reset(56, 7, 8, mBitmap.getWidth(), mBitmap.getHeight(),1);
			mBoatState = BoatType.bAttack;
		}
	}
	public boolean getBroken() {
		return mBroken;
	}
	public void setBroken(boolean broken) {
		mBroken = broken;
		if(mBroken == true){
			mBitmap = SpriteManager.getBoat();
			mAnimate.Reset(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight(),3);
		}
	}
	public boolean checkBroken(){
		if(mBroken == true){
			mBoatState = BoatType.bReady;
			if(++mTorpedoCount>=0){
				if(mTorpedoCount == 200){
					mTorpedoCount = -1;
					mBroken = false;
				}
			}
			return true;
		}else{
			return false;
		}
	}

}