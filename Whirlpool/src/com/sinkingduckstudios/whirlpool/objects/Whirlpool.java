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
import com.sinkingduckstudios.whirlpool.logic.Constants;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

// TODO: Auto-generated Javadoc
/**
 * The Class Whirlpool.
 */
public class Whirlpool extends GraphicObject{
	//private final float sharkFactor = 0.5f;
	/** The power. */
	private float power = 0.05f;
	
	/** The object radius. */
	private float objectRadius = 40.0f; //distance of graphic to wpool center
	
	/** The angle. */
	private float angle = 0.0f;
	
	/** The _rot. */
	private float _rot = 0.0f;
	
	/** The dir factor. */
	private int dirFactor = 1;
	
	/** The expire timer. */
	private final int expireTimer = 200;
	
	/** The expire counter. */
	private int expireCounter = 1;
	
	/** The finished. */
	private boolean finished = false;
	
	/** The collision done. */
	public boolean collisionDone = true;
	
	/** The m arrow. */
	private Arrow mArrow = null;
	
	/** The tangent y. */
	private float tangentX, tangentY;

	/**
	 * Instantiates a new whirlpool.
	 */
	public Whirlpool(){
		mId = objtype.tWhirl;
		init();
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#draw(android.graphics.Canvas)
	 */
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
		*/
		canvas.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		canvas.translate(getCentreX(), getCentreY());
		canvas.scale(dirFactor, 1);
		canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect, null);
		canvas.restore();
		if (mArrow != null){
			mArrow.draw(canvas);//draw whirls directional arrow
		}
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#init()
	 */
	@Override
	public void init() {
		mProperties.init(0, 0, 130, 130,1.0f,1.0f);	

		mBitmap = SpriteManager.getWhirlpool();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);

	}
	
	/**
	 * Inits the.
	 *
	 * @param x the x
	 * @param y the y
	 */
	public void init(int x, int y) {
		mProperties.init(x, y, 130, 130,1.0f,1.0f);	

		mBitmap = SpriteManager.getWhirlpool();
		mAnimate = new Animate(mId.tFrames, mId.tNoOfRow, mId.tNoOfCol, mBitmap.getWidth(), mBitmap.getHeight());

		mSpeed.setMove(false);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#move()
	 */
	@Override
	public boolean move() {
		return false;
	}
	
	/**
	 * Gets the arrow.
	 *
	 * @return the arrow
	 */
	public Arrow getArrow(){
		return mArrow;
	}
	
	/**
	 * Sets the arrow.
	 *
	 * @param arrow the new arrow
	 */
	public void setArrow(Arrow arrow){
		mArrow = arrow;
	}

	/* (non-Javadoc)
	 * @see com.sinkingduckstudios.whirlpool.objects.GraphicObject#frame()
	 */
	public void frame(){
		CollisionManager.updateCollisionRect(mProperties, mSpeed.getAngleRad());
		if(expireCounter < expireTimer){
			expireCounter++;
			if(expireCounter >= expireTimer){
				finished = true;
			}
		}

		mAnimate.animateFrame();
	}

	/**
	 * Check collision.
	 *
	 * @param a the a
	 * @return true, if successful
	 */
	public boolean checkCollision(GraphicObject a){
		if (a.getId()==objtype.tDuck || a.getId()==objtype.tTorpedo || a.getId()==objtype.tShark){
			boolean collide = collision(a);

			if (a.getPulledState()==Constants.STATE_FREE&&collide){
				a.setPulledState(Constants.STATE_PULLED);
				a.setPulledBy(this);
			}
			if (a.getPulledState()==Constants.STATE_PULLED&&collide&&a.getPulledBy()==this){//if the object is able to be pulled, and is colliding

				if(a.getId()==objtype.tTorpedo)
					((Torpedo)a).setTracking(false);//stop a torpedo tracking duck
				
				if(a.getId()==objtype.tShark || a.getId()==objtype.tTorpedo)
					collisionDone = true;
				else	//Never dissipate if a duck is inside
					collisionDone = false;
				
				pull(a);//pull round whirlpool
				if (testAngle(a)){
					a.setPulledState(Constants.STATE_LEAVING);//leaving a wpool
					a.resetwPoolCounter();
					a.setAngle(getWAngle());
					a.getSpeed().setSpeed(objtype.tDuck.tSpeed+mArrow.getDist());
					collisionDone = true;
					finished = true;
				}
			}
			return collide;
		}
		return false;
	}

	/**
	 * Test angle.
	 *
	 * @param a the a
	 * @return true, if successful
	 */
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
	
	/**
	 * Gets the finished.
	 *
	 * @return the finished
	 */
	public boolean getFinished(){
		return (finished && collisionDone);
	}

	/**
	 * Sets the expire counter.
	 *
	 * @param a the new expire counter
	 */
	public void setExpireCounter(int a){
		expireCounter = a;
	}

	/**
	 * Sets the clockwise.
	 *
	 * @param clockwise the new clockwise
	 */
	public void setClockwise(int clockwise){
		dirFactor = clockwise;
	}
	
	/**
	 * Gets the clockwise.
	 *
	 * @return the clockwise
	 */
	public int getClockwise(){
		return dirFactor;
	}
	
	/**
	 * Gets the rotation.
	 *
	 * @return the rotation
	 */
	public float getRotation(){
		_rot+= (2*getClockwise());
		if (_rot >= 360)_rot=0;
		if (_rot < 0)_rot=360;
		return _rot;
	}

	/**
	 * Point collision.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
	public boolean pointCollision(float x, float y){

		float distX, distY, dist;
		distX = this.getCentreX() - x;
		distY = this.getCentreY() - y;
		dist = (distX*distX)+(distY*distY);

		if (dist <= ( (this.getRadius()) * (this.getRadius()) ))
			return true;

		return false;

	}

	/**
	 * Collision.
	 *
	 * @param graphic the graphic
	 * @return true, if successful
	 */
	public boolean collision(GraphicObject graphic){

		float distX, distY, dist;
		distX = this.getCentreX() - graphic.getCentreX();
		distY = this.getCentreY() - graphic.getCentreY();

		dist = (distX*distX)+(distY*distY);

		return (dist <= ( ((this.getRadius()) + graphic.getRadius()) * ((this.getRadius()) + graphic.getRadius()) ));

	}

	/**
	 * Pull.
	 *
	 * @param graphic the graphic
	 */
	public void pull(GraphicObject graphic){
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

	//this function is called so the math is only done once per fetch
	//(rather than individually in each getter)
	/**
	 * Calc tangent point.
	 *
	 * @param x the x
	 * @param y the y
	 * @return true, if successful
	 */
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
	
	/**
	 * Gets the tangent x.
	 *
	 * @return the tangent x
	 */
	public float getTangentX(){
		return tangentX;
	}
	
	/**
	 * Gets the tangent y.
	 *
	 * @return the tangent y
	 */
	public float getTangentY(){
		return tangentY;
	}
	
	/**
	 * Gets the power.
	 *
	 * @return the power
	 */
	public float getPower() {
		return power;
	}
	
	/**
	 * Sets the power.
	 *
	 * @param power the new power
	 */
	public void setPower(float power) {
		this.power = power;
	}
	
	/**
	 * Sets the w angle.
	 *
	 * @param angle the new w angle
	 */
	public void setWAngle(float angle) {
		this.angle = angle;
	}
	
	/**
	 * Gets the w angle.
	 *
	 * @return the w angle
	 */
	public float getWAngle() {
		return angle;
	}

}
