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

public class Frog extends GraphicObject{
	//used for working out the location of the frog round the circle
	private float mFrogCentreX, mFrogCentreY, mFrogAngle, mFrogRadius;

	public Frog(){
		mId = objtype.tFrog;
		init();
	}
	public Frog(int x, int y, int r){
		mId = objtype.tFrog;
		init(x, y, r);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			canvas.rotate((float) (-mFrogAngle*180/Math.PI));
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
				(Constants.getLevel().getLevelHeight()/2)-70,
				80, 80);	

		mBitmap = SpriteManager.getFrog();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		//used for locating the frog round the circle
		setFrogCentreX(getCentreX());
		setFrogCentreY(getCentreY());
		setFrogRadius(180);
	}
	public void init(int x, int y, int r) {
		mProperties.init(x, y, 80, 80);	

		mBitmap = SpriteManager.getFrog();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		//used for locating the frog round the circle
		setFrogCentreX(getCentreX());
		setFrogCentreY(getCentreY());
		setFrogRadius(r);
	}
	@Override
	public boolean move() {
		if(mSpeed.getMove()){
			setCentreX((int)(mFrogCentreX + Math.sin(mFrogAngle)*mFrogRadius));
			setCentreY((int)(mFrogCentreY + Math.cos(mFrogAngle)*mFrogRadius));
			mFrogAngle-=mSpeed.getSpeed()/150;
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
		mAnimate.animateFrame();
	}

	public float getFrogAngle() {
		return mFrogAngle;
	}
	public float getFrogRadius() {
		return mFrogRadius;
	}
	public void setFrogCentreX(float frogCentreX) {
		mFrogCentreX = frogCentreX;
	}
	public void setFrogCentreY(float frogCentreY) {
		mFrogCentreY = frogCentreY;
	}
	public void setFrogAngle(float frogAngle) {
		mFrogAngle = frogAngle;
	}
	public void setFrogRadius(float frogRadius) {
		mFrogRadius = frogRadius;
	}
	public float getFrogCentreX() {
		return mFrogCentreX;
	}
	public float getFrogCentreY() {
		return mFrogCentreY;
	}
}
