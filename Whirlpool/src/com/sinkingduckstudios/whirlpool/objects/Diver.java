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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Diver extends GraphicObject{
	private boolean mFlipped;

	public Diver(){
		mId = objtype.tDiver;
		init();
	}
	public Diver(int x, int y, int angle){
		mId = objtype.tDiver;
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
		Paint temp = new Paint();
		temp.setStyle(Style.STROKE);
		temp.setColor(Color.RED);
		canvas.drawCircle(getCentreX(), getCentreY(), getRadius(), temp);
	}
	@Override
	public void init() {
		mProperties.init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
						new Random().nextInt(Constants.getLevel().getLevelHeight()),
						100, 100);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));
		mBitmap = SpriteManager.getDiver();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}
	public void init(int x, int y, int angle) {
		mProperties.init(x, y, 100, 100);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));
		mBitmap = SpriteManager.getDiver();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(angle);
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
			mSpeed.shiftAngle(180);
			setTopLeftY(-getTopLeftY());
			break;
		case Bottom:
			mSpeed.shiftAngle(180);
			setTopLeftY(height-getHeight());
			break;
		case Left:
			mSpeed.shiftAngle(180);
			setTopLeftX(-getTopLeftX());
			break;
		case Right:
			mSpeed.shiftAngle(180);
			setTopLeftX(width - getWidth());
			break;
		case BottomLeft:
			mSpeed.shiftAngle(180);
			setTopLeftX(-getWidth());
			setTopLeftY(height-getHeight());
			break;
		case BottomRight:
			mSpeed.shiftAngle(180);
			setTopLeftX(width - getWidth());
			setTopLeftY(height-getHeight());
			break;
		case TopLeft:
			mSpeed.shiftAngle(180);
			setTopLeftX(-getTopLeftX());
			setTopLeftY(-getTopLeftY());
			break;
		case TopRight:
			mSpeed.shiftAngle(180);
			setTopLeftX(width - getWidth());
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
			checkFlip();
		}
		mAnimate.animateFrame();
	}
	public void checkFlip(){
		if(!mFlipped){
			if(mSpeed.getAngle()>270 || mSpeed.getAngle()<90){
				mFlipped = true;
				mBitmap = SpriteManager.getDiverFlipped();
				System.gc();
			}
		}else if(mFlipped){
			if(mSpeed.getAngle()<=270 && mSpeed.getAngle()>=90){
				mFlipped = false;
				mBitmap = SpriteManager.getDiver();
				System.gc();
			}
		}
	}
}
