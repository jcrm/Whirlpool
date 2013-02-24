package com.sinkingduckstudios.whirlpool.objects;

import java.util.Random;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Imports;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;


import android.graphics.Canvas;
import android.graphics.Rect;

public class Boat extends GraphicObject{
	private int mBoatRadius = 30;
	public Boat(){
		mId = objtype.tBoat;
		init();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getX(), getY());
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
	}

	@Override
	public void init() {
		mBitmap = Imports.getBoat();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());

		mWidth = mBitmap.getWidth()/mId.tFrames;
		mHeight = mBitmap.getHeight();

		setX((float) (new Random().nextInt(Constants.getLevel().getLevelWidth())));
		setY((float) (new Random().nextInt(Constants.getLevel().getLevelWidth())));

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		mRadius =  (int) Math.sqrt(((float)mWidth*mWidth) + ((float)mHeight*mHeight));
	}

	@Override
	public boolean move() {
		if(mSpeed.getMove()){
			shiftX((float) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			shiftY((float) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
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
		if(move()){
			border();
		}
		mAnimate.animateFrame();
	}

	public int getBoatRadius() {
		return mBoatRadius;
	}

	public void setBoatRadius(int boatRadius) {
		mBoatRadius = boatRadius;
	}

}
