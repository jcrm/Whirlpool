package com.sinkingduckstudios.whirlpool.environment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
import com.sinkingduckstudios.whirlpool.objects.GraphicObject;
import com.sinkingduckstudios.whirlpool.objects.GraphicObject.objtype;

public class Finish extends GraphicEnvironment{
	private float objectRadius = 40.0f; //distance of graphic to wpool center
	private float _rot = 0.0f;
	private int dirFactor = 1;
	private boolean finished = false;
	public boolean collisionDone = true;
	private boolean mHit = false;
	private int mEnd = 0;
	private Bitmap mHitBitmap,mPlugBitmap;
	private Animate mHitAnimate;
	private boolean mActive;

	public Finish(){
		mId = envtype.tFinish;
		init();
	}
	public Finish(int x, int y){
		mId = envtype.tFinish;
		init(x,y);
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.scale(dirFactor, 1);

		if (mActive){
			if(mHit == false){
				canvas.drawBitmap(mBitmap, mAnimate.getPortion(), rect,  null);
			}else{
				canvas.drawBitmap(mHitBitmap, mHitAnimate.getPortion(), rect,  null);				
			}
		}else{
			canvas.drawBitmap(mPlugBitmap, null, rect,  null);//draw plug
		}
		canvas.restore();
	}

	@Override
	public void init() {
		init(0,0);
	}
	public void init(int x, int y) {
		mProperties.init(x, y, 130, 130,1.0f,1.0f);	

		mBitmap = SpriteManager.getFinish();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mHitBitmap = SpriteManager.getFinishHit();
		mHitAnimate = new Animate(28, 4, 8, mHitBitmap.getWidth(), mHitBitmap.getHeight());

		mPlugBitmap = SpriteManager.getPlug();
		
		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);

		setCentreX(x);
		setCentreY(y);
		setClockwise(1);
		
		mActive = false;
	}
	@Override
	public boolean move() {
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

	public boolean isActive() {
		return mActive;
	}
	
	public void frame(){
		if (mActive){

			if(mHit == false){
				mAnimate.animateFrame();
			}else{
				mHitAnimate.animateFrame();
				if(mHitAnimate.getFinished() ==true){
					mEnd = 2;
				}
			}
		}
	}

	public void activate(){
		mActive=true;
	}

	public boolean checkCollision(GraphicObject a){
		if (a.getId()==objtype.tDuck ){
			boolean collide = collision(a);

			if (collide&&a.getPulledBy()==null){
				a.setPulledState(Constants.STATE_FINISHING);
			}

			if (collide&&a.getPulledState()==Constants.STATE_FINISHING){//if the duck is touching finish

				collisionDone = false;
				pull(a);//pull round whirlpool
				mHit=true;
				return true;
			}

		}
		return false;
	}

	public boolean getFinished(){
		return (finished && collisionDone);
	}

	public void setClockwise(int clockwise){
		dirFactor = clockwise;
	}
	public int getClockwise(){
		return dirFactor;
	}
	public float getRotation(){
		_rot+= (2*getClockwise());
		if (_rot >= 360)_rot=0;
		if (_rot < 0)_rot=360;
		return _rot;
	}

	public boolean pointCollision(float x, float y){

		float distX, distY, dist;
		distX = this.getCentreX() - x;
		distY = this.getCentreY() - y;
		dist = (distX*distX)+(distY*distY);

		if (dist <= ( (this.getRadius()) * (this.getRadius()) ))
			return true;

		return false;

	}

	public boolean collision(GraphicObject graphic){

		//Return 0 if there is no collision, return 1 if there is partial, 2 if there is centre collision

		float distX, distY, dist;
		distX = this.getCentreX() - graphic.getCentreX();
		distY = this.getCentreY() - graphic.getCentreY();

		dist = (distX*distX)+(distY*distY);

		return(dist <= ( ((this.getRadius()) + graphic.getRadius()) * ((this.getRadius()) + graphic.getRadius()) ));


	}

	public void gravity(GraphicObject graphic, float factor){
		float objX = graphic.getCentreX();
		float objY = graphic.getCentreY();
		//float objSpeedX = graphic.getSpeed().getXSpeed();
		//float objSpeedY = graphic.getSpeed().getYSpeed();
		float wPoolCentreX = getCentreX();
		float wPoolCentreY = getCentreY();
		//this is the current distance from centre to graphic
		objectRadius = (float)Math.sqrt(Math.pow(wPoolCentreX-objX, 2)+(Math.pow(wPoolCentreY-objY, 2)));
		//angle of radius
		float cAngle = CollisionManager.calcAngle(wPoolCentreX, wPoolCentreY,objX, objY)+90;
		cAngle+= (2 * getClockwise());//rotate obj around wpool

		float destX = wPoolCentreX + (float)Math.sin(cAngle*Math.PI/180)*objectRadius;
		float destY = wPoolCentreY - (float)Math.cos(cAngle*Math.PI/180)*objectRadius;

		cAngle = CollisionManager.calcAngle(objX, objY,destX, destY);

		//reset angle and speed
		//graphic.getSpeed().setSpeed((float) (Math.sqrt(Math.pow(speedx, 2) + Math.pow(speedy, 2))));
		cAngle = cAngle+ (5.0f * getClockwise()); //ideal angle

		float mAngle = graphic.getSpeed().getAngle();
		float clockwiseDiff;
		float anticlockwiseDiff;
		if (mAngle > cAngle){
			anticlockwiseDiff = (cAngle + 360) - mAngle;
			clockwiseDiff = (mAngle - cAngle);
		}
		else{
			clockwiseDiff = (mAngle + 360) - cAngle;
			anticlockwiseDiff = (cAngle - mAngle);
		}
		if (anticlockwiseDiff < clockwiseDiff)
			if (anticlockwiseDiff >= 10)
				mAngle+=10;
			else mAngle = cAngle;
		else
			if (clockwiseDiff >= 10)
				mAngle-=10;
			else mAngle = cAngle;

		graphic.setAngle(mAngle);
	}
	//pulls different objects to the centre depending on original speed
	//sharks are pulled slower because they start faster
	//where as boats get pulled faster because they start slower
	//frogs are not effected 
	public void pull(GraphicObject graphic){
		switch(graphic.getId()){
		case tDuck:
			gravity(graphic, 4.0f);
			break;
		default:

		}
	}

	public int getEnd() {
		return mEnd;
	}

	public void setEnd(int end) {
		mEnd = end;
	}

}
