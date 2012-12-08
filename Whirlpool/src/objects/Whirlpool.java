package objects;
//updated 29/11
import objects.GraphicObject.objtype;
import logic.Func;
import android.util.FloatMath;
//used to create effect of whirlpool


/*
 * Can be deleted until i've fixed crashing problem
 */
public class Whirlpool {
	private final float frogFactor = 0.8f;
	private final float sharkFactor = 0.5f;
	private boolean clockwise = true;
	private float power = 0.05f;
	private float radius = 40;
	private float angle = 0.0f;
	private GraphicObject object = new GraphicObject(objtype.tWhirl);
	
	public Whirlpool(){
		
	}
	public int getClockwise(){
		if (clockwise)
			return 1;
		else 
			return -1;
	}
	public void setClockwise(boolean Clock){
		clockwise = Clock;
		object.setClockwise(clockwise);
	}
	
	public int collision(GraphicObject graphic){
		
		//Return 0 if there is no collision, return 1 if there is partial, 2 if there is centre collision
		
		float distX, distY, dist;
		distX = this.getCentreX() - graphic.getX();
		distY = this.getCentreY() - graphic.getY();
		
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
		float objSpeedX = graphic.getSpeed().getXSpeed();
		float objSpeedY = graphic.getSpeed().getYSpeed();
		float wPoolCentreX = this.getCentreX();
		float wPoolCentreY = this.getCentreY();
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
		graphic.getSpeed().setAngle(cAngle+ (5.0f * getClockwise()));
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
	public float getX() {
		return object.getActualX();
	}
	public float getCentreX() {
		return object.getX();
	}
	public void setCentreX(float centreX) {
		object.setX(centreX);
	}
	public float getY() {
		return object.getActualY();
	}
	public float getCentreY() {
		return object.getY();
	}
	public void setCentreY(float centreY) {
		object.setY(centreY);
	}
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
	public GraphicObject getGraphic() {
		return object;
	}
	public void setGraphic(GraphicObject object) {
		this.object = object;
	}
}
