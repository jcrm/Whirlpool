package example.whirlpool;

import example.whirlpool.GraphicObject;
import example.whirlpool.GraphicObject.objtype;

public class Whirlpool {
	//variables
	private final float _frogFactor = 0.8f;
	private final float _sharkFactor = 0.5f;
	private boolean _clockwise = true;
	private float _power = 0.05f;
	private float _radius = 40;
	private float _angle = 0.0f;
	private GraphicObject _object = new GraphicObject(objtype.tWhirl);
	
	Whirlpool(){
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
	//pulls different objects to the centre depending on type of object
	void pull(GraphicObject graphic){
		switch(graphic.getId()){
		case tShark:
			gravity(graphic, _sharkFactor);
			break;
		case tFrog:
			gravity(graphic, _frogFactor);
			break;
		case tDuck:
			gravity(graphic, 4.0f);
			break;
		case tBoat:
			break;
		default:
			
		}
	}
	//getter for clockwise variable
	public boolean getClockwise(){
		return _clockwise;
	}
	//getters and setter for X variables
	public float getX() {
		return _object.getActualX();
	}
	public float getCentreX() {
		return _object.getX();
	}
	public void setCentreX(float centreX) {
		_object.setX(centreX);
	}
	//getters and setter for Y variables
	public float getY() {
		return _object.getActualY();
	}
	public float getCentreY() {
		return _object.getY();
	}
	public void setCentreY(float centreY) {
		_object.setY(centreY);
	}
	//getter and setter for power
	public float getPower() {
		return _power;
	}
	public void setPower(float power) {
		this._power = power;
	}
	//getter and setter for radius
	public float getRadius() {
		return _radius;
	}
	public void setRadius(float radius) {
		this._radius = radius;
	}
	//getter and setter for angel
	public void setWAngle(float angle) {
		this._angle = angle;
	}
	public float getWAngle() {
		return this._angle;
	}
	//getter and setter for object
	public GraphicObject getGraphic() {
		return _object;
	}
	public void setGraphic(GraphicObject object) {
		this._object = object;
	}
}
