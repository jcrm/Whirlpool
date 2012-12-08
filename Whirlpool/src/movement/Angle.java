package movement;

import java.lang.Math;

class Angle{
	private float _angle = 0;
	Angle(float a){
		_angle = a;
	}
	public float getAngle(){
		return _angle;
	}
	public float getAngleRad(){
		return (float) (_angle*Math.PI/180);
	}
	public void setAngle(float a){
		_angle = a;
		checkAngle();
	}
	public void shiftAngle(float a){
		_angle += a;
		checkAngle();
	}
	private void checkAngle(){		//Makes sure it's always withing 0-360
		if(_angle >= 360.0f || _angle < 0.0f){
			_angle = ((_angle%360)+360)%360;
		}
	}
}