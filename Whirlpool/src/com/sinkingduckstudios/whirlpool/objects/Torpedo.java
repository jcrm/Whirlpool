package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;


public class Torpedo extends GraphicObject {
	private boolean mIsReadyToDestroy = false;
	private static final float mTopSpeed = 12*Constants.getScreen().getRatio();
	private int mDuckCounter = 10;
	private boolean mHitBoat = false;
	private int mHitBoatCounter = 0;
	private int mBeepCounter = 31;
	private boolean mIsTracking; //tracking duck?
	private Bitmap mExplosionBitmap;
	private Animate mExplosionAnimate;
	private boolean mExplosion = false;
	
	public Torpedo(int x, int y, float angle){
		mId= objtype.tTorpedo;
		init(x, y, angle);
	}
	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(10);
		canvas.drawPoint(mProperties.mCollisionRect[0].getX(), mProperties.mCollisionRect[0].getY(), paint);
		paint.setColor(Color.BLACK);
		canvas.drawPoint(mProperties.mCollisionRect[1].getX(), mProperties.mCollisionRect[1].getY(), paint);
		paint.setColor(Color.GREEN);
		canvas.drawPoint(mProperties.mCollisionRect[2].getX(), mProperties.mCollisionRect[2].getY(), paint);
		paint.setColor(Color.MAGENTA);
		canvas.drawPoint(mProperties.mCollisionRect[3].getX(), mProperties.mCollisionRect[3].getY(), paint);
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			canvas.rotate(mSpeed.getAngle()+180);
			if(mExplosion){
				canvas.drawBitmap(mExplosionBitmap, mExplosionAnimate.getPortion(), rect,  null);
			}else{
				canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
			}
		canvas.restore();
	}

	public void init(int x, int y, float angle){
		mProperties.init(x, y, 50, 50);		
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/6)*(getHeight()/6)))-(mProperties.getWidth()/8));
		
		mBitmap = SpriteManager.getTorpedo();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mExplosionBitmap = SpriteManager.getTorpedoExplosion();
		mExplosionAnimate = new Animate(11, 3, 4, mExplosionBitmap.getWidth(), mExplosionBitmap.getHeight());
		
		mSpeed.setMove(true);
		mSpeed.setAngle(angle);
		mSpeed.setSpeed(mId.tSpeed);
		mIsTracking=true;
	}

	@Override
	public void init(){
		init(30,60,mId.tAngle);
	}
	
	@Override
	public boolean move() {
		CollisionManager.updateCollisionRect(mProperties, -mSpeed.getAngleRad());
		if(mHitBoat == false && ++mHitBoatCounter > 40){
			mHitBoatCounter = 0;
			mHitBoat = true;
		}
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

	@Override
	public void frame() {
		if(mExplosion == false){
			if(move()){
				border();
			}
		}
		if(mExplosion){
			if(mExplosionAnimate.getFinished() == false){
				mExplosionAnimate.animateFrame();
			}else{
				mIsReadyToDestroy = true;
			}
		}else{
			mAnimate.animateFrame();
		}
	}

	public boolean getIsReadyToDestroy() {
		return mIsReadyToDestroy;
	}

	public void setIsReadyToDestroy(boolean isReadyToDestroy) {
		mIsReadyToDestroy = isReadyToDestroy;
	}
	public boolean getTracking(){
		return mIsTracking;
	}
	public void setTracking(boolean b){
		mIsTracking=b;
	}
	public void setDuckPosition(float f, float g){
		mSpeed.setAngle(180+CollisionManager.calcAngle(f, g, getCentreX(), getCentreY()));
		float tempSpeed = mSpeed.getSpeed(); 
		if(tempSpeed<mTopSpeed){
			mSpeed.setSpeed(tempSpeed+1);
		}else{
			mSpeed.setSpeed(mTopSpeed);
		}
	}
	//try get view by id , get id layout, get height and width of view; on button click of menu
	public int getDuckCounter() {
		return mDuckCounter;
	}
	public void setDuckCounter(int duckCounter) {
		mDuckCounter = duckCounter;
	}
	public boolean updateDirection(){
		if(mIsTracking){
			mDuckCounter++;
			if(mDuckCounter>10){
				mDuckCounter = 0;
				return true;
			}
		}
		return false;
	}
	public void checkBeep(){
		if(mExplosion == false){
			mBeepCounter++;
			if(mBeepCounter>30){
				mBeepCounter = 0;
				Constants.getSoundManager().playBeepFast();
			}
		}
	}
	public boolean getHitBoat() {
		return mHitBoat;
	}
	public void setHitBoat(boolean hitBoat) {
		mHitBoat = hitBoat;
	}
	public float getTopSpeed() {
		return mTopSpeed;
	}
	public boolean getExplosion() {
		return mExplosion;
	}
	public void setExplosion(boolean explosion) {
		mExplosion = explosion;
	}
	public float getDist() {
		float x1 = getCentreX();
		float y1 = getCentreY();
		float x2 = Constants.getPlayer().getCentreX();
		float y2 = Constants.getPlayer().getCentreY();
		
		float distX = x2-x1;
		float distY = y2-y1;
		
		return (distX*distX)+(distY*distY); 
		
	}
}
