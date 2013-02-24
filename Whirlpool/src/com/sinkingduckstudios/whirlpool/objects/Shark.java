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
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;

public class Shark extends GraphicObject{

	public Shark(){
		mId = objtype.tShark;
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
		//_bitmap = Imports.getShark();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());

		mWidth = mBitmap.getWidth()/mId.tFrames;
		mHeight = mBitmap.getHeight();

		setX((float) (new Random().nextInt(Constants.getScreen().getWidth())));
		setY((float) (new Random().nextInt(Constants.getScreen().getHeight())));

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

}
