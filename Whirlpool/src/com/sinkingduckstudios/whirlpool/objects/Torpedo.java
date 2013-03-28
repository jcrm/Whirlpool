package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;


public class Torpedo extends GraphicObject {
	private boolean mIsReadyToDestroy = false;
	private static final float mTopSpeed = 12*Constants.getScreen().getRatio();
	private int mDuckCounter = 10;
	private boolean mHitBoat = false;
	private int mHitBoatCounter = 0;
	private int mBeepCounter = 31;
	
	public Torpedo(int x, int y, float angle){
		mId= objtype.tTorpedo;
		init(x, y, angle);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			canvas.rotate(mSpeed.getAngle()+180);
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
	}

	public void init(int x, int y, float angle){
		mProperties.init(x, y, 50, 50);		
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));
		
		mBitmap = SpriteManager.getTorpedo();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}

	@Override
	public void init(){
		mProperties.init(30, 60, 50, 50);		
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));
		
		mBitmap = SpriteManager.getTorpedo();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}
	
	@Override
	public boolean move() {
		if(mHitBoat == false && ++mHitBoatCounter > 40){
			mHitBoatCounter = 0;
			mHitBoat = true;
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

	@Override
	public void frame() {
		if(move()){
			border();
		}
		mAnimate.animateFrame();
	}

	public boolean getIsReadyToDestroy() {
		return mIsReadyToDestroy;
	}

	public void setIsReadyToDestroy(boolean isReadyToDestroy) {
		mIsReadyToDestroy = isReadyToDestroy;
	}
	public void setDuckPosition(int duckX, int duckY){
		mSpeed.setAngle(180+CollisionManager.calcAngle(duckX, duckY, getCentreX(), getCentreY()));
		float tempSpeed = mSpeed.getSpeed(); 
		if(tempSpeed<mTopSpeed){
			mSpeed.setSpeed(tempSpeed+1);
		}else{
			mSpeed.setSpeed(mTopSpeed);
		}
	}
	//try get view by id , get id layout, get height and width of view; on button click of menu
	public int getDuckCounter() {
		return mDuckCounter;
	}
	public void setDuckCounter(int duckCounter) {
		mDuckCounter = duckCounter;
	}
	public boolean updateDirection(){
		mDuckCounter++;
		if(mDuckCounter>10){
			mDuckCounter = 0;
			return true;
		}
		return false;
	}
	public void checkBeep(){
		mBeepCounter++;
		if(mBeepCounter>30){
			mBeepCounter = 0;
			Constants.getSoundManager().playBeepFast();
		}
	}
	public boolean getHitBoat() {
		return mHitBoat;
	}
	public void setHitBoat(boolean hitBoat) {
		mHitBoat = hitBoat;
	}
	public float getTopSpeed() {
		return mTopSpeed;
	}
}
