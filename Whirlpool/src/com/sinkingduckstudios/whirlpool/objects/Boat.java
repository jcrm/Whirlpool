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

public class Boat extends GraphicObject{
	private int mBoatRadius = Constants.getLevel().getLevelHeight()/2;
	private final int mAttackFrames = 8;
	private int mTorpedoCount = -1;
	private boolean mCreateNewTorpedo = true;
	private boolean mAttackMode = false;
	
	public Boat(){
		mId = objtype.tBoat;
		init();
	}
	public Boat(int x, int y){
		mId = objtype.tBoat;
		init(x, y);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
		Paint temp = new Paint();
		temp.setStyle(Style.STROKE);
		temp.setColor(Color.RED);
		canvas.drawCircle(getCentreX(), getCentreY(), getRadius(), temp);
		canvas.drawCircle(getCentreX(), getCentreY(), mBoatRadius, temp);
	}

	@Override
	public void init() {
		mProperties.init(new Random().nextInt(Constants.getLevel().getLevelWidth()), 
				new Random().nextInt(Constants.getLevel().getLevelHeight()/4), 
				96, 96);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/4)*(getHeight()/4))));
		mBitmap = SpriteManager.getBoat();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
	
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}
	public void init(int x, int y) {
		mProperties.init(x, y, 96, 96);	
		mProperties.setRadius((int) Math.sqrt(((float)(getWidth()/2)*(getWidth()/2)) + ((float)(getHeight()/4)*(getHeight()/4))));
		mBitmap = SpriteManager.getBoat();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
	
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
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
		incrementCounter();
		if(mAttackMode && mAnimate.getFinished()){
			/*mBitmap = SpriteManager.getBoat();
			mAnimate.Reset(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
			mAttackMode = false;
			mCreateNewTorpedo = false;*/
		}
		mAnimate.animateFrame();
	}

	public int getBoatRadius() {
		return mBoatRadius;
	}

	public void setBoatRadius(int boatRadius) {
		mBoatRadius = boatRadius;
	}
	public int getTorpedoCount() {
		return mTorpedoCount;
	}
	public void setTorpedoCount(int torpedoCount) {
		mTorpedoCount = torpedoCount;
	}
	public void incrementCounter(){
		if((mCreateNewTorpedo == false) && (++mTorpedoCount>=0)){
			if(mTorpedoCount == 120){
				mCreateNewTorpedo = true;
				mTorpedoCount = -1;
			}
		}
	}
	public boolean getCreateNewTorpedo() {
		return mCreateNewTorpedo;
	}
	public void setCreateNewTorpedo(boolean createNewTorpedo) {
		mCreateNewTorpedo = createNewTorpedo;
	}
	public void changeAnimation(){
		/*if(mCreateNewTorpedo){
			mBitmap= SpriteManager.getBoatAttack();
			mAnimate.Reset(mAttackFrames, mBitmap.getWidth(), mBitmap.getHeight()/7);
			mAttackMode = true;
		}*/
	}

}
