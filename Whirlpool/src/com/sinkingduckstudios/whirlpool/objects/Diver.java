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
import android.graphics.Paint.Style;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Diver extends GraphicObject{
	private boolean mFlipped;
	private int mLeftBorder;
	private int mRightBorder;
	private int mTopBorder;
	private int mBottomBorder;

	public Diver(){
		mId = objtype.tDiver;
		init();
		mLeftBorder = 0;
		mTopBorder = 0;
		mRightBorder = Constants.getLevel().getLevelWidth();
		mBottomBorder = Constants.getLevel().getLevelHeight();
		checkBorderConditions();
	}
	public Diver(int x, int y, int angle, int left, int top, int right, int bottom){
		mId = objtype.tDiver;
		init(x, y, angle);
		mLeftBorder = left;
		mTopBorder = top;
		mRightBorder = right;
		mBottomBorder = bottom;
		checkBorderConditions();
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

	public void borderCollision(ScreenSide side) {
		switch(side){
		case Top:
			mSpeed.shiftAngle(180);
			setTopLeftY(mTopBorder);
			break;
		case Bottom:
			mSpeed.shiftAngle(180);
			setTopLeftY(mBottomBorder-getHeight());
			break;
		case Left:
			mSpeed.shiftAngle(180);
			setTopLeftX(mLeftBorder);
			break;
		case Right:
			mSpeed.shiftAngle(180);
			setTopLeftX(mRightBorder - getWidth());
			break;
		case BottomLeft:
			mSpeed.shiftAngle(180);
			setTopLeftX(mLeftBorder);
			setTopLeftY(mBottomBorder-getHeight());
			break;
		case BottomRight:
			mSpeed.shiftAngle(180);
			setTopLeftX(mRightBorder - getWidth());
			setTopLeftY(mBottomBorder-getHeight());
			break;
		case TopLeft:
			mSpeed.shiftAngle(180);
			setTopLeftX(mLeftBorder);
			setTopLeftY(mTopBorder);
			break;
		case TopRight:
			mSpeed.shiftAngle(180);
			setTopLeftX(mRightBorder - getWidth());
			setTopLeftY(mTopBorder);
			break;
		default:
			break;
		}
	}
	@Override
	public boolean border(){
		boolean hit = false;
		if(mLeftBorder == mRightBorder){
			if(getTopLeftY() < mTopBorder){
				borderCollision(Screen.ScreenSide.Top);
				hit = true;
			}else if(getBottomRightY() > mBottomBorder){
				borderCollision(Screen.ScreenSide.Bottom);
				hit = true;
			}
		}else if(mTopBorder == mBottomBorder){
			if(getTopLeftX() <mLeftBorder){
				borderCollision(Screen.ScreenSide.Left);
				hit = true;
			}else if(getBottomRightX() > mRightBorder){
				borderCollision(Screen.ScreenSide.Right);
				hit = true;
			}
		}else{
			if(getTopLeftX()<mLeftBorder){
				if(getTopLeftY()<mTopBorder){
					borderCollision(Screen.ScreenSide.TopLeft);
				}else if(getBottomRightY()>mBottomBorder){
					borderCollision(Screen.ScreenSide.BottomLeft);
				}else{
					borderCollision(Screen.ScreenSide.Left);
				}
				hit = true;
			}else if(getBottomRightX() > mRightBorder){
				if(getTopLeftY() < mTopBorder){
					borderCollision(Screen.ScreenSide.TopRight);
				}else if(getBottomRightY() > mBottomBorder) {
					borderCollision(Screen.ScreenSide.BottomRight);
				}else{
					borderCollision(Screen.ScreenSide.Right);
				}
				hit = true;
			}
			if (getTopLeftY() < mTopBorder) {
				borderCollision(Screen.ScreenSide.Top);
				hit = true;
			} else if (getBottomRightY() > mBottomBorder) {
				borderCollision(Screen.ScreenSide.Bottom);
				hit = true;
			}
		}
        return hit;
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
	@Override
	public void borderCollision(ScreenSide side, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	private void checkBorderConditions(){
		if(mRightBorder < mLeftBorder){
			int temp = mRightBorder;
			mRightBorder = mLeftBorder;
			mLeftBorder = temp;
		}
		if(mBottomBorder < mTopBorder){
			int temp = mBottomBorder;
			mBottomBorder = mTopBorder;
			mTopBorder = temp;
		}
	}
}
