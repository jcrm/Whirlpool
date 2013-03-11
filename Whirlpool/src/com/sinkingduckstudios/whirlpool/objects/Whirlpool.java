/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.logic.Screen.ScreenSide;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

public class Whirlpool extends GraphicObject{
	private final float sharkFactor = 0.5f;
	private float power = 0.05f;
	private float objectRadius = 40.0f; //distance of graphic to wpool center
	private float angle = 0.0f;
	private float _rot = 0.0f;
	private int dirFactor = 1;
	private final int expireTimer = 250;
	private int expireCounter = 1;
	private boolean finished = false;
	public boolean collisionDone = true;
	private Arrow mArrow = null;
	private float tangentX, tangentY;

	public Whirlpool(){
		mId = objtype.tWhirl;
		init();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getCentreX(), getCentreY());
			canvas.scale(dirFactor, 1);
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
		if (mArrow != null){
			mArrow.draw(canvas);//draw whirls directional arrow
		}
	}

	@Override
	public void init() {
		mProperties.init(0, 0, 130, 130);	

		mBitmap = SpriteManager.getWhirlpool();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		
	}
	public void init(int x, int y) {
		mProperties.init(x, y, 130, 130);	

		mBitmap = SpriteManager.getWhirlpool();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());
		
		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
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
	public Arrow getArrow(){
		return mArrow;
	}
	public void setArrow(Arrow arrow){
		mArrow = arrow;
	}

	public void frame(){
		if(expireCounter < expireTimer){
			expireCounter++;
			if(expireCounter >= expireTimer){
				finished = true;
			}
		}
		
		mAnimate.animateFrame();
	}

	public void checkCollision(GraphicObject a){
		if (a.getId()==objtype.tDuck){
			if (a.getPullState() == false && (!finished || !collisionDone)){
				int collide = collision(a);
				
				if (collide == 1 || collide == 2){
					collisionDone = false;
					a.setPull(true);
					pull(a);
					if (testAngle(a)){
						a.resetwPoolCounter();
						a.setAngle(getWAngle());
						collisionDone = true;
						finished = true;
					}
				}
			}
		}
	}

	public boolean testAngle(GraphicObject a){
		if (angle == -1)//wpool not directed yet
			return false;
		if(!a.wPoolCounter())
			return false;
		float _lastAngle;
		//check if duckie has reached his exit angle
		_lastAngle = a.getSpeed().getLastAngle();
		//for a clockwise wpool, the last angle is always gonna be smaller
		if (dirFactor == 1){
			
			if (_lastAngle > a.getSpeed().getAngle()){
				//if lastangle is bigger, its passed 360
				_lastAngle -= 360;
				if (angle > a.getSpeed().getAngle()){
					if (_lastAngle < angle-360 && a.getSpeed().getAngle() >= angle-360)
						return true;
				}else
					if (_lastAngle < angle && a.getSpeed().getAngle() >= angle)
						return true;
			}
			else if (_lastAngle < angle && a.getSpeed().getAngle() >= angle)
				return true;
		}else{
			
			if (_lastAngle < a.getSpeed().getAngle()){
				//if lastangle is bigger, its passed 360
				_lastAngle += 360;
				if (angle < a.getSpeed().getAngle()){
					if (_lastAngle > angle+360 && a.getSpeed().getAngle() <= angle+360)
						return true;
				}else
					if (_lastAngle > angle && a.getSpeed().getAngle() <= angle)
						return true;
			}
			else if (_lastAngle > angle && a.getSpeed().getAngle() <= angle)
				return true;
		}
				
		return false;
	}
	public boolean getFinished(){
		return (finished && collisionDone);
	}

	public void setExpireCounter(int a){
		expireCounter = a;
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

	public int collision(GraphicObject graphic){

		//Return 0 if there is no collision, return 1 if there is partial, 2 if there is centre collision

		float distX, distY, dist;
		distX = this.getCentreX() - graphic.getCentreX();
		distY = this.getCentreY() - graphic.getCentreY();

		dist = (distX*distX)+(distY*distY);

		//if (graphic.GetPullState()==false){ //if you can pull the object

		if (dist <= ( ((this.getRadius()/2) + (graphic.getRadius()/2)) * ((this.getRadius()/2) + (graphic.getRadius()/2)) ))
			return 2;
		else if (dist <= ( ((this.getRadius()) + graphic.getRadius()) * ((this.getRadius()) + graphic.getRadius()) ))
			return 1;

		//}

		return 0;

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
		case tShark:
			gravity(graphic, sharkFactor);
			break;
		case tDuck:
			gravity(graphic, 4.0f);
			break;
		case tBoat:

			break;
		default:

		}
	}

	//this function is called so the math is only done once per fetch
	//(rather than individually in each getter)
	public boolean calcTangentPoint(float x, float y){
		float adj1,opp1,hyp1,angle1,angle2;

		opp1 = objectRadius;
		//distance from center to point
		hyp1 = (float) Math.sqrt(((x-getCentreX())*(x-getCentreX())) + ((y-getCentreY())*(y-getCentreY())));

		if(hyp1 < opp1)
			return false;//no arrow can be drawn, point inside whirl
		//distance from point to tangentPoint
		adj1 = (float) Math.sqrt((hyp1*hyp1) - (opp1*opp1));
		//angle from point to tangentPoint
		angle1 = (float) Math.asin(opp1/hyp1);
		angle2 = (float) (((CollisionManager.calcAngle(x,y,getCentreX(),getCentreY())))*(Math.PI/180));

		angle1 *= getClockwise();
		angle1 = angle2 + angle1;
		//calc y component from circle center
		tangentX = (float) (Math.cos(angle1) * adj1) + x;
		tangentY = (float) (Math.sin(angle1) * adj1) + y;
		return true;
	}
	public float getTangentX(){
		return tangentX;
	}
	public float getTangentY(){
		return tangentY;
	}
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public void setWAngle(float angle) {
		this.angle = angle;
	}
	public float getWAngle() {
		return angle;
	}

}
