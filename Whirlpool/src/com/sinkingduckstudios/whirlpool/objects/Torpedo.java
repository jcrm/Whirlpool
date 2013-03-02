package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Imports;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;


public class Torpedo extends GraphicObject {
	private boolean mIsReadyToDestroy = false;
	private int mDuckCounter = 11;
	
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
		mBitmap = Imports.getTorpedo();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
		
		mProperties.init(x, y, mBitmap.getWidth()/mId.tFrames, mBitmap.getHeight());
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6))));
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}

	@Override
	public void init(){
		mBitmap = Imports.getTorpedo();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
		
		mProperties.init(30, 60, mBitmap.getWidth()/mId.tFrames, mBitmap.getHeight());		

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
	public void setDuckSpeed(int duckX, int duckY){
		mSpeed.setAngle(180+CollisionManager.calcAngle(duckX, duckY, getCentreX(), getCentreY()));
		float tempSpeed = mSpeed.getSpeed(); 
		if(tempSpeed<8){
			mSpeed.setSpeed(tempSpeed+1);
		}else{
			tempSpeed = 8;
		}
	}
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
}
