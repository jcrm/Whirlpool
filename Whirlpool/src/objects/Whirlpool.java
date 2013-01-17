package objects;
//updated 29/11
import example.whirlpool.R;
import objects.GraphicObject.objtype;
import states.MainActivity;
import logic.Func;
import logic.Imports;
import logic.Panel;
import logic.Screen.ScreenSide;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;
//used to create effect of whirlpool


/*
 * Can be deleted until i've fixed crashing problem
 */
public class Whirlpool extends GraphicObject{
	private final float frogFactor = 0.8f;
	private final float sharkFactor = 0.5f;
	private float power = 0.05f;
	private float radius = 40.0f;
	private float angle = 0.0f;
	private float _rot = 0.0f;
	private int dirFactor = 1;
	private final int expireTimer = 250;
	private int expireCounter = 1;
	private boolean finished = false;
	private final int afterCollisionTimer = 50;
	private int afterCollisionCounter = 0;
	private boolean collisionDone = false;
	
	public Whirlpool(){
		_id = objtype.tWhirl;
        init();
	}
	
	@Override
	public void draw(Canvas c) {
		c.save();
		
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		
		c.translate(getX(), getY());
		c.rotate(getRotation());
		c.scale(dirFactor, 1);
		
		c.drawBitmap(getGraphic(), null, rect,  null);
		
		c.restore();
		
	}
	
	@Override
	public void init() {
		_bitmap = Imports.getWhirlpool();
		_speed.setMove(false);
		
		_width = _id.width;
		_height = _id.height;
		_speed.setAngle(_id.angle);
		_speed.setSpeed(_id.speed);
		_radius =  (int) FloatMath.sqrt(((float)_width*_width) + ((float)_height*_height));
		
	}
	
	@Override
	public boolean move() {
		return false;
	}
	
	@Override
	public void borderCollision(ScreenSide side, float width, float height) {
		switch(side){
		case Top:
			_speed.VerBounce();
            setActualY(-getActualY());
			break;
		case Bottom:
			_speed.VerBounce();
        	setActualY(height - getHeight());
			break;
		case Left:
			_speed.HorBounce();
        	setActualX(-getActualX());
			break;
		case Right:
			_speed.HorBounce();
        	setActualX(width - getWidth());
			break;
		}
	}
	
	public void frame(){
		if(expireCounter > 0){
			expireCounter++;
			if(expireCounter >= expireTimer){
				finished = true;
			}
		}
		if(afterCollisionCounter > 0){
			afterCollisionCounter++;
			if(afterCollisionCounter >= afterCollisionTimer){
				finished = true;
			}
		}
	}
	
	public void checkCollision(GraphicObject a){
		if (a.getPullState() == false && !collisionDone){
			int collide = collision(a);
			
			if (collide == 1){
				a.setPull(true);
				pull(a);
				setExpireCounter(0);
			}
			else if (collide == 2){
				a.setPull(true);
				pull(a);
				setExpireCounter(0);
				if (a.getSpeed().getAngle() > getWAngle()-5.0f &&
				 a.getSpeed().getAngle() < getWAngle()+5.0f){
					a.setAngle(getWAngle());
					collisionDone = true;
					setAfterCollisionCounter(1);
				}
			}
		}
	}
	
	public boolean getFinished(){
		return finished;
	}
	
	public void setExpireCounter(int a){
		expireCounter = a;
	}
	
	public void setClockwise(boolean clockwise){
    	if (clockwise) dirFactor = 1;
    	else dirFactor = -1;
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
	
	public int collision(GraphicObject graphic){
		
		//Return 0 if there is no collision, return 1 if there is partial, 2 if there is centre collision
		
		float distX, distY, dist;
		distX = this.getX() - graphic.getX();
		distY = this.getY() - graphic.getY();
		
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
		float objX = graphic.getX();
		float objY = graphic.getY();
		//float objSpeedX = graphic.getSpeed().getXSpeed();
		//float objSpeedY = graphic.getSpeed().getYSpeed();
		float wPoolCentreX = getX();
		float wPoolCentreY = getY();
		//this is the current distance from centre to graphic
		float wPoolRadius = (float)Math.sqrt(Math.pow(wPoolCentreX-objX, 2)+(Math.pow(wPoolCentreY-objY, 2)));
		//angle of radius
		float cAngle = Func.calcAngle(wPoolCentreX, wPoolCentreY,objX, objY)+90;
		cAngle+= (2 * getClockwise());//rotate obj around wpool

		float destX = wPoolCentreX + (float)Math.sin(cAngle*Math.PI/180)*wPoolRadius;
		float destY = wPoolCentreY - (float)Math.cos(cAngle*Math.PI/180)*wPoolRadius;

		cAngle = Func.calcAngle(objX, objY,destX, destY);

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
		case tFrog:
			gravity(graphic, frogFactor);
			break;
		case tDuck:
			gravity(graphic, 4.0f);
			break;
		case tBoat:
			
			break;
		default:
			
		}
	}/*
	//changes direction by a factor depending on type of object
	//depending on quadrant depends which directions need changing
	void ChangeDir(GraphicObject graphic, float Factor){
		float tempx = graphic.GetX();
		float tempy = graphic.GetY();
		GraphicObject.Speed speed = graphic.GetSpeed();
		//checks if the speed is the same as the quadrant before
		if(tempx>=centreX && tempy>=centreY){
			if(speed.GetX()>0 && speed.GetY()>0){
				speed.SetX((float)(speed.GetX()*Factor*-1));
			}
		}
		else if(tempx<centreX && tempy>=centreY){
			if(speed.GetX()<0 && speed.GetY()>0){
				speed.SetY((float)(speed.GetY()*Factor*-1));
			}
		}
		else if(tempx<centreX && tempy<centreY){
			if(speed.GetX()<0 && speed.GetY()<0){
				speed.SetX((float)(speed.GetX()*Factor*-1));
			}
		}
		else if(tempx>=centreX && tempy<centreY){
			if(speed.GetX()>0 && speed.GetY()<0){
				speed.SetY((float)(speed.GetY()*Factor*-1));
			}
		}
		
	}*/
	public float getPower() {
		return power;
	}
	public void setPower(float power) {
		this.power = power;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public void setWAngle(float angle) {
		this.angle = angle;
	}
	public float getWAngle() {
		return this.angle;
	}

	public int getAfterCollisionCounter() {
		return afterCollisionCounter;
	}

	public void setAfterCollisionCounter(int afterCollisionCounter) {
		this.afterCollisionCounter = afterCollisionCounter;
	}
	
	
}
