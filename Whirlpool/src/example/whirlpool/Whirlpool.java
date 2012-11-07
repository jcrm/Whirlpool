package example.whirlpool;

import example.whirlpool.GraphicObject;
import example.whirlpool.GraphicObject.objtype;
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
	
	Whirlpool(){
		
	}
	public boolean getClockwise(){
		return clockwise;
	}
	
	public int Collision(GraphicObject graphic){
		
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
		float tempx = graphic.getX();
		float tempy = graphic.getY();
		float speedx = graphic.getSpeed().getXSpeed();
		float speedy = graphic.getSpeed().getYSpeed();
		float cangle = Func.calcAngle(tempx, tempy, this.getCentreX(), this.getCentreY());
		float temppower = getPower() * factor;
		
		float cx = (float) (temppower*Math.cos(cangle*Math.PI/180));
		float cy = (float) (temppower*Math.sin(cangle*Math.PI/180));
		
		/*float dist = 0;
		for(int x1 = 0; x1 < 3; x1++){
			if(_tempCollision.circleCollision(graphic.getX(), graphic.getY(), graphic.getRadius()+dist, this.getCentreX(), this.getCentreY(), this.getRadius()))
				break;
			else{cx = (float)cx/2; cy = (float)cy/2; dist += 150;}
		}*/
		speedx += cx;
		speedy += cy;
		
		//reset angle and speed
		graphic.getSpeed().setSpeed((float) (Math.sqrt(Math.pow(speedx, 2) + Math.pow(speedy, 2))));
		graphic.getSpeed().setAngle(Func.calcAngle(tempx, tempy, tempx + speedx, tempy + speedy));
	}
	//pulls different objects to the centre depending on original speed
	//sharks are pulled slower because they start faster
	//where as boats get pulled faster because they start slower
	//frogs are not effected 
	void pull(GraphicObject graphic){
		switch(graphic.id){
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
