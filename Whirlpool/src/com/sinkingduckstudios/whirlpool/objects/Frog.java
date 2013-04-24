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
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Frog.
 */
public class Frog extends GraphicObject{
	//used for working out the location of the frog round the circle
	/** The m frog radius. */
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
		/*
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
		for(int i = 0; i<4;i++){
			canvas.drawPoint(mProperties.mCollisionRect[i].getX(), mProperties.mCollisionRect[i].getY(), paint);
		}
		paint.setColor(Color.GREEN);
		canvas.drawPoint(getCentreX(), getCentreY(), paint);
		paint.setColor(Color.WHITE);
		canvas.drawPoint(getTopLeftX(), getTopLeftY(), paint);
		canvas.drawPoint(getBottomRightX(), getBottomRightY(), paint);
		*/
		
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.rotate((float) (-mFrogAngle*180/Math.PI));
		canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect, null);
		canvas.restore();
	}

	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				(Constants.getLevel().getLevelHeight()/2)-70,
				180);	
	}
	public void init(int x, int y, int r) {
		mGraphicType = 2;
		mIsPlaying = false;
		mProperties.init(x-r, y, 80, 80,0.7f,0.6f);	

		mBitmap = SpriteManager.getFrog();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		//used for locating the frog round the circle
		setFrogCentreX(x);
		setFrogCentreY(y);
		setFrogRadius(r);
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
	}
	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, (float) (-mFrogAngle));
		if(mSpeed.getMove()){
			setCentreX((int)(mFrogCentreX + Math.sin(mFrogAngle)*mFrogRadius));
			setCentreY((int)(mFrogCentreY + Math.cos(mFrogAngle)*mFrogRadius));
			mFrogAngle-=mSpeed.getSpeed()/100;
			return true;
		}
		return false;
	}

	public void frame(){
		// Move Objects
		if(move()){
			border();
		}
		mAnimate.animateFrame();
	}

	/**
	 * Gets the frog angle.
	 *
	 * @return the frog angle
	 */
	public float getFrogAngle() {
		return mFrogAngle;
	}
	
	/**
	 * Gets the frog radius.
	 *
	 * @return the frog radius
	 */
	public float getFrogRadius() {
		return mFrogRadius;
	}
	
	/**
	 * Sets the frog centre x.
	 *
	 * @param frogCentreX the new frog centre x
	 */
	public void setFrogCentreX(float frogCentreX) {
		mFrogCentreX = frogCentreX;
	}
	
	/**
	 * Sets the frog centre y.
	 *
	 * @param frogCentreY the new frog centre y
	 */
	public void setFrogCentreY(float frogCentreY) {
		mFrogCentreY = frogCentreY;
	}
	
	/**
	 * Sets the frog angle.
	 *
	 * @param frogAngle the new frog angle
	 */
	public void setFrogAngle(float frogAngle) {
		mFrogAngle = frogAngle;
	}
	
	/**
	 * Sets the frog radius.
	 *
	 * @param frogRadius the new frog radius
	 */
	public void setFrogRadius(float frogRadius) {
		mFrogRadius = frogRadius;
	}
	
	/**
	 * Gets the frog centre x.
	 *
	 * @return the frog centre x
	 */
	public float getFrogCentreX() {
		return mFrogCentreX;
	}
	
	/**
	 * Gets the frog centre y.
	 *
	 * @return the frog centre y
	 */
	public float getFrogCentreY() {
		return mFrogCentreY;
	}
}
