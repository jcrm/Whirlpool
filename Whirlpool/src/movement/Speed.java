package movement;

import android.util.FloatMath;

public class Speed {
	//variables
	private boolean _move = true;
	private float _speed = 0;
	private Angle _angle = new Angle(0);
	//bounce functions
	public void HorBounce(){
		float angletemp = _angle.getAngle();
		switch((int)(angletemp/90)){
			case 0:
			case 2:	_angle.shiftAngle(2*(90 - (int)(angletemp%90)));
					break;
			case 1:
			case 3:	_angle.shiftAngle(-2*((int)(angletemp%90)));
					break;
		}
	}
	public void VerBounce(){
		float angletemp = _angle.getAngle();
		switch((int)(angletemp/90)){
			case 0:
			case 2:	_angle.shiftAngle(-2*((int)(angletemp%90)));
					break;
			case 1:
			case 3:	_angle.shiftAngle(2*(90 - (int)(angletemp%90)));
					break;
		}
	}
	//getters and setters for speed
	public float getSpeed(){
		return _speed;
	}
	public float getXSpeed(){
		return _speed*FloatMath.cos(_angle.getAngleRad());
	}
	public float getYSpeed(){
		return _speed*FloatMath.sin(_angle.getAngleRad());
	}
	public void setSpeed(float a){
		_speed = a;
	}
	public void shiftSpeed(float a){
		_speed += a;
	}
	//getters and setters for move
	public boolean getMove(){
		return _move;
	}
	public void setMove(boolean a){
		_move = a;
	}
	//getters and setters for angle
	public float getAngle(){
		return _angle.getAngle();
	}
	public float getAngleRad(){
		return _angle.getAngleRad();
	}
	public void setAngle(float a){
		_angle.setAngle(a);
	}
	public void shiftAngle(float a){
		_angle.shiftAngle(a);
	}
}