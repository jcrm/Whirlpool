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
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Collectable extends GraphicObject{
	private Bitmap mBitmap[] = new Bitmap[3];
	private Animate mAnimate[] = new Animate[3];
	private Paint mPaint;
	private Rect mRect;
	private boolean mHasCollided;
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
		canvas.save();

		canvas.translate(getCentreX(), getCentreY());
		if(mSpeed.getAngle() > 90 && mSpeed.getAngle() < 270){
			canvas.scale(-1, 1);
		}
		int i = getSpriteSheetIndex();
		canvas.drawBitmap(mBitmap[i], mAnimate[i].getPortion(), mRect, mPaint);

		canvas.restore();
	}

	public boolean getCollided(){
		return mHasCollided;
	}
	public void setFollowing(GraphicObject f){
		mFollowing = f;
	}
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

		//setCentreX(destX);
		//setCentreY(destY);
		return false;
	}

	@Override
	public void borderCollision(ScreenSide side, int width, int height) {
		// switch(side){
		// case Top:
		// mSpeed.verticalBounce();
		// setTopLeftY(-getTopLeftY());
		// break;
		// case Bottom:
		// mSpeed.verticalBounce();
		// setTopLeftY(height-getHeight());
		// break;
		// case Left:
		// mSpeed.horizontalBounce();
		// setTopLeftX(-getTopLeftX());
		// break;
		// case Right:
		// mSpeed.horizontalBounce();
		// setTopLeftX(width - getWidth());
		// break;
		// case BottomLeft:
		// mSpeed.horizontalBounce();
		// setTopLeftX(-getWidth());
		// mSpeed.verticalBounce();
		// setTopLeftY(height-getHeight());
		// break;
		// case BottomRight:
		// mSpeed.horizontalBounce();
		// setTopLeftX(width - getWidth());
		// mSpeed.verticalBounce();
		// setTopLeftY(height-getHeight());
		// break;
		// case TopLeft:
		// mSpeed.horizontalBounce();
		// setTopLeftX(-getTopLeftX());
		// mSpeed.verticalBounce();
		// setTopLeftY(-getTopLeftY());
		// break;
		// case TopRight:
		// mSpeed.horizontalBounce();
		// setTopLeftX(width - getWidth());
		// mSpeed.verticalBounce();
		// setTopLeftY(-getTopLeftY());
		// break;
		// default:
		// break;
		// }
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