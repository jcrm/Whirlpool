/*
 * Author: Fraser Tomison
 * Last Updated: 02/04/2013
 * Content:
 * Holder for collectable ducks, can be initialised with position
 *
 */
package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

/**
 * The Class Collectable.
 */
public class Collectable extends GraphicObject{
	/** The collectable images. */
	private Bitmap mBitmap[] = new Bitmap[3];
	/** The collectable animation. */
	private Animate mAnimate[] = new Animate[3];
	/** The paint variable. */
	private Paint mPaint;
	/** The collcetable rect. */
	private Rect mRect;
	/** The collided variable. */
	private boolean mHasCollided;
	/** Which object is it following. */
	private GraphicObject mFollowing;

	public Collectable(int x, int y){
		mId = objtype.tCollectable;
		init(x,y);
	}

	public Collectable(){
		mId = objtype.tCollectable;
		init();
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();

		canvas.translate(getCentreX(), getCentreY());
		if(mSpeed.getAngle() > 90 && mSpeed.getAngle() < 270){
			canvas.scale(-1, 1);
		}
		int i = getSpriteSheetIndex();
		canvas.drawBitmap(mBitmap[i], mAnimate[i].getPortion(), mRect, mPaint);

		canvas.restore();
	}

	/**
	 * Gets the collided.
	 *
	 * @return the collided
	 */
	public boolean getCollided(){
		return mHasCollided;
	}

	/**
	 * Sets the following.
	 *
	 * @param f the new following
	 */
	public void setFollowing(GraphicObject f){
		mFollowing = f;
	}

	/**
	 * Gets the sprite sheet index.
	 *
	 * @return the sprite sheet index
	 */
	private int getSpriteSheetIndex(){
		if (getSpeed().getAngle()>240&&getSpeed().getAngle()<300)
			return 1;
		if (getSpeed().getAngle()>60 && getSpeed().getAngle()<120)
			return 2;
		return 0;
	}
	@Override
	public void init() {
		init(0,0);

	}
	public void init(int x, int y) {
		mProperties.init(x, y, 30, 30,1.0f,1.0f);	

		for(int i=0; i<3; i++){
			int frames;
			if(i==0){
				frames = mId.tFrames;
			}else{
				frames = mId.tFrames+3;
			}
			mBitmap[i] = SpriteManager.getDuck(i);
			if (i==0)
				mAnimate[i] = new Animate(frames, mId.tNoOfRow, mId.tNoOfCol, mBitmap[i].getWidth(), mBitmap[i].getHeight());
			else
				mAnimate[i] = new Animate(19, 3, 7, mBitmap[i].getWidth(), mBitmap[i].getHeight());
		}

		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);

		mHasCollided=false;

		ColorMatrix cm = new ColorMatrix();
		cm.set(new float[]{
				0.8f,0,0,0,100,
				0,0.4f,0,0,80,
				0,0,0.9f,0,20,
				0,0,0,1,0

		});
		mPaint = new Paint();
		mPaint.setColorFilter(new ColorMatrixColorFilter(cm));

		mRect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
	}

	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		int destX,destY;

		destX=((int) (mFollowing.getCentreX()*Constants.getScreen().getRatio()));
		destX+=((int) (mFollowing.getWidth()*Math.cos(mFollowing.getSpeed().getAngleRad()+Math.PI)));
		destY=((int) (mFollowing.getCentreY()*Constants.getScreen().getRatio()));
		destY+=((int) (mFollowing.getWidth()*Math.sin(mFollowing.getSpeed().getAngleRad()+Math.PI)));

		setAngle(CollisionManager.calcAngle(getCentreX()*Constants.getScreen().getRatio(),getCentreY()*Constants.getScreen().getRatio(),destX, destY));
		setSpeed(mFollowing.getSpeed().getSpeed()/Constants.getScreen().getRatio());
		moveDeltaX((int) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
		moveDeltaY((int) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));

		return false;
	}

	public void frame(){
		if(mHasCollided)
			move();
		else
			mHasCollided = collision(Constants.getPlayer());
		mAnimate[getSpriteSheetIndex()].animateFrame();
	}

	public boolean collision(Duck graphic){

		//Return 0 if there is no collision, return 1 if there is partial, 2 if there is centre collision

		float distX, distY, dist;
		distX = this.getCentreX() - graphic.getCentreX();
		distY = this.getCentreY() - graphic.getCentreY();

		dist = (distX*distX)+(distY*distY);

		//if (graphic.GetPullState()==false){ //if you can pull the object

		return (dist <= ( ((this.getRadius()) + graphic.getRadius()) * ((this.getRadius()) + graphic.getRadius()))) ;



	}

}
