/*
 * Author: Jake Morey, Jordan O'Hare
 * Content:
 * Jordan O'Hare: created basic class based upon parent class
 * Jake Morey: added function to move frog around a circle
 */
package com.sinkingduckstudios.whirlpool.objects;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
/**
 * The Class Frog.
 */
public class Frog extends GraphicObject{
	
	/** Works out the position of the frog using these variables. */
	private float mFrogCentreX, mFrogCentreY, mFrogAngle, mFrogRadius;

	/**
	 * Instantiates a new frog.
	 */
	public Frog(){
		mId = objtype.tFrog;
		init();
	}
	
	/**
	 * Instantiates a new frog.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param r the radius of the frog
	 * @param dir the direction of the frog
	 */
	public Frog(int x, int y, int r){
		mId = objtype.tFrog;
		init(x, y, r);
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		//roate the sprite based upon the angle of the frog
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.rotate((float) (-mFrogAngle*180/Math.PI));
		canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect, null);
		canvas.restore();
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#init()
	 */
	@Override
	public void init() {
		init(new Random().nextInt(Constants.getLevel().getLevelWidth()),
				(Constants.getLevel().getLevelHeight()/2)-70,
				180);	
	}
	
	/**
	 * Inits the frog.
	 *
	 * @param x the x position
	 * @param y the y position
	 * @param r the radius of the frog
	 */
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
		mFrogAngle = new Random().nextInt(360);
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
	}
	
	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#move()
	 */
	@Override
	public boolean move() {
		//update collision rectangle
		CollisionManager.updateCollisionRect(mProperties, (float) (-mFrogAngle));
		if(mSpeed.getMove()){
			//move around a circle based upon the ditance from the centre and the angle around a circle
			setCentreX((int)(mFrogCentreX + Math.sin(mFrogAngle)*mFrogRadius));
			setCentreY((int)(mFrogCentreY + Math.cos(mFrogAngle)*mFrogRadius));
			mFrogAngle-= mSpeed.getSpeed()/100;
			if(mFrogAngle>360){
				mFrogAngle-=360;
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#frame()
	 */
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
