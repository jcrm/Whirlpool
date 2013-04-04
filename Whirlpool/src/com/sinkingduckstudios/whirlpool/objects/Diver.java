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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Diver extends GraphicObject{
	private int mLeftBorder;
	private int mRightBorder;
	private int mTopBorder;
	private int mBottomBorder;
	private Bitmap mUpBitmap;
	private Bitmap mDownBitmap;
	private Animate mUpAnimate;
	private Animate mDownAnimate;

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
		mLeftBorder = (int) (left/Constants.getScreen().getRatio());
		mTopBorder = (int) (top/Constants.getScreen().getRatio());
		mRightBorder = (int) (right/Constants.getScreen().getRatio());
		mBottomBorder = (int) (bottom/Constants.getScreen().getRatio());
		checkBorderConditions();
	}
	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
		for(int i = 0; i<4;i++){
			canvas.drawPoint(mProperties.mCollisionRect[i].getX(), mProperties.mCollisionRect[i].getY(),paint);
		}
		paint.setColor(Color.WHITE);
		canvas.drawPoint(getTopLeftX(), getTopLeftY(), paint);
		canvas.drawPoint(getBottomRightX(), getBottomRightY(), paint);
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		switch(getSpriteSheetIndex()){
		case 0: if(mSpeed.getAngle()<=270 && mSpeed.getAngle()>90){
			canvas.rotate(mSpeed.getAngle()+180);	
		}else if (mSpeed.getAngle() ==0){
			canvas.scale(1, -1);
			canvas.rotate(mSpeed.getAngle()+180);	
		}else if(mSpeed.getAngle()>270){
			canvas.rotate(mSpeed.getAngle()+180);	
			canvas.scale(1, -1);
		}else if(mSpeed.getAngle()<=90){
			canvas.scale(-1, 1);
			canvas.rotate(mSpeed.getAngle()-90);
		}
		canvas.drawBitmap(mBitmap, mAnimate.getPortion(), rect, null);
		break;
		case 1: canvas.drawBitmap(mUpBitmap, mUpAnimate.getPortion(), rect, null);
		break;
		case 2: canvas.drawBitmap(mDownBitmap, mDownAnimate.getPortion(), rect, null);
		break;
		default: break;
		}
		canvas.restore();
	}
	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				new Random().nextInt(Constants.getLevel().getLevelHeight()),
				0);	
	}
	public void init(int x, int y, int angle) {
		mProperties.init(x, y, 100, 100,0.85f,0.35f);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));

		mBitmap = SpriteManager.getDiver();
		mUpBitmap = SpriteManager.getDiverUp();
		mDownBitmap = SpriteManager.getDiverDown();

		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		mUpAnimate = new Animate(28, 7, 4, mUpBitmap.getWidth(), mUpBitmap.getHeight());
		mUpAnimate.setDelay(2);
		mDownAnimate = new Animate(28, 7, 4, mDownBitmap.getWidth(), mDownBitmap.getHeight());
		mDownAnimate.setDelay(2);

		mSpeed.setMove(true);
		mSpeed.setAngle(angle);
		mSpeed.setSpeed(mId.tSpeed);
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
	}
	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
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
		}
		switch(getSpriteSheetIndex()){
		case 0: mAnimate.animateFrame(); break;
		case 1: mUpAnimate.animateFrame(); break;
		case 2: mDownAnimate.animateFrame(); break;
		default: break;
		}
	}
	@Override
	public void borderCollision(ScreenSide side, int width, int height) {
		borderCollision(side);
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
		if(mRightBorder ==0 && mLeftBorder ==0 && mTopBorder == 0 && mBottomBorder ==0){
			mLeftBorder = 0;
			mTopBorder = 0;
			mRightBorder = Constants.getLevel().getLevelWidth();
			mBottomBorder = Constants.getLevel().getLevelHeight();
		}
	}
	private int getSpriteSheetIndex(){
		if (getSpeed().getAngle()>240&&getSpeed().getAngle()<300)
			return 1;
		if (getSpeed().getAngle()>60 && getSpeed().getAngle()<120)
			return 2;
		return 0;
	}
}