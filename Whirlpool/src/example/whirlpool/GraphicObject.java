package example.whirlpool;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import java.lang.Math;
import example.whirlpool.Panel;
import android.util.FloatMath;

class GraphicObject {
	//enum used to decide what type of sprite
	public enum objtype {
		tDefault(0, 0, 0), 
		tWhirl(100, 100, 0),
		tDuck(30, 30, 0),
		tFrog(30, 30, 5), 
		tShark(10, 40, 0), 
		tBoat(50, 15, 0);
		int width;
		int height;
		float speed;
		objtype(int a, int b, float c){
			width = a;
			height = b;
			speed = c;
		}
	}
	//speed class
	public class Speed {
		//angle class
    	private class Angle{
    		private float _angle = 0;
    		Angle(float a){
    			_angle = a;
    		}
    		public float getAngle(){
    			return _angle;
    		}
    		public float getAngleRad(){
    			return (_angle*PI/180);
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
    			if(_angle > 360.0f || _angle < 0.0f){
    				_angle = ((_angle%360)+360)%360;
    			}
    		}
    	}
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
    	void VerBounce(){
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
	//private variables
	private float PI = 3.141592653589793238f;
	private objtype _id = objtype.tDefault;
	private int _width, _height, _radius = 0;
	private float _xScale, _yScale;
	private float _frogCentreX, _frogCentreY, _frogAngle, _frogRadius;
	private boolean _noPull;
	private float _x = 0;
    private float _y = 0;
    private Bitmap _bitmap;
    private Speed _speed = new Speed();
	
    public GraphicObject(objtype type){
    	_id = type;
        init();
        _radius =  (int) Math.sqrt(((float)_width*_width) + ((float)_height*_height));
    }
    public void draw(Canvas c){
    	c.save();
    	c.scale(getXScale(), getYScale());
    	c.translate(((1/getXScale())-1)*(getX()), ((1/getYScale())-1)*(getY()));
        c.drawBitmap(getGraphic(), getActualX(), getActualY(), null);
        c.restore();
    }
	public void init(){
		boolean _speedCheck = false;
		if(_id.speed!=0){
			_speedCheck = true;
			_speed.setSpeed(_id.speed);
		}
		switch(_id){
		case tWhirl:
			_bitmap = BitmapFactory.decodeResource(Panel._res, R.drawable.whirlpool);
			break;
		case tDuck:
			_bitmap = BitmapFactory.decodeResource(Panel._res, R.drawable.duck);
			setX(0.0f);
        	setY((int) Panel._screen.getCentreY() - getGraphic().getHeight() / 2);
        	if(!_speedCheck){
        		_speed.setSpeed(4);
        	}
        	_speed.setAngle(0);
			break;
		case tFrog:
			_bitmap = BitmapFactory.decodeResource(Panel._res, R.drawable.frog);
			boolean dir = new Random().nextBoolean();
			setX(Panel._screen.getWidth()/2);
			setY(Panel._screen.getHeight()/2);
			setFrogCentreX(getX());
			setFrogCentreY(getY());
			setFrogAngle(0);
			setFrogRadius(100);
			if(!_speedCheck){
        		_speed.setSpeed(3);
        	}
            if(dir){
            	_speed.setAngle(90);
            }else{
            	_speed.setAngle(0);
            }
            _speed.setMove(true);
			break;
		case tShark:
			_bitmap = BitmapFactory.decodeResource(Panel._res, R.drawable.shark);
			setX((float) (new Random().nextInt(Panel._screen.getWidth())));
        	setY((float) (new Random().nextInt(Panel._screen.getHeight())));
        	if(!_speedCheck){
        		_speed.setSpeed(new Random().nextInt(5)+1);
        	}
        	_speed.setAngle(new Random().nextInt(360)+1);
        	_speed.setMove(true);
			break;
		case tBoat:
			_bitmap = BitmapFactory.decodeResource(Panel._res, R.drawable.boat);
			setX((float) (new Random().nextInt(Panel._screen.getWidth())));
            setY((float) (new Random().nextInt(Panel._screen.getHeight())));
            if(!_speedCheck){
        		_speed.setSpeed(new Random().nextInt(3)+1);
        	}
            _speed.setAngle(new Random().nextInt(360));
            _speed.setMove(true);
			break;
		default:
			break;
		}
		_width = _id.width;
		_height = _id.height;
		setScale();
	}
	//getter and setter for object type
	public objtype getId(){
		return _id;
	}
	public void setId(objtype id){
		_id = id;
	}
	//getters and setters for X components
    public float getX() {
        return _x + _bitmap.getWidth() / 2;
    }
    public float getActualX() {
        return _x;
    }
    public float getFrogCentreX() {
		return _frogCentreX;
	}
    public void setX(float value) {
        _x = value - _bitmap.getWidth() / 2;
    }
    public void setFrogCentreX(float frogCentreX) {
		this._frogCentreX = frogCentreX;
	}
    public void shiftX(float shift){
    	_x += shift;
    }
    //getters and setters for Y components
    public float getY() {
        return _y + _bitmap.getHeight() / 2;
    }
    public float getActualY() {
        return _y;
    }
    public float getFrogCentreY() {
		return _frogCentreY;
	}
    public void setY(float value) {
        _y = value - _bitmap.getHeight() / 2;
    }
	public void setFrogCentreY(float frogCentreY) {
		this._frogCentreY = frogCentreY;
	}
    public void shiftY(float shift){
    	_y += shift;
    }
    //getters and setters for scale, width and height
    public float getXScale() {
		return _xScale;
	}
    public float getYScale() {
		return _yScale;
	}
    public int getWidth(){
		return _width;
    }
    public int getHeight(){
		return _height;
    }
	public void setScale(){
		setXScale((float)_width/_bitmap.getWidth());
		setYScale((float)_height/_bitmap.getHeight());
	}
	public void setXScale(float xscale) {
		this._xScale = xscale;
	}
	public void setYScale(float yscale) {
		this._yScale = yscale;
	}
	public void setWidth(int width){
		_width = width;
	}
	public void setHeight(int height){
		_height = height;
	}
	//getters and setters for angels and radius
    public float getRadius(){
    	return _radius;
    }
	public float getFrogAngle() {
		return _frogAngle;
	}
	public float getFrogRadius() {
		return _frogRadius;
	}
	public void setRadius(int radius){
		_radius = radius;
	}
	public void setFrogAngle(float frogAngle) {
		this._frogAngle = frogAngle;
	}
	public void setFrogRadius(float _frogRadius) {
		this._frogRadius = _frogRadius;
	}
	public void setAngle(float a){
		_speed.setAngle(a);
	}
	//getters for graphics and speed
    public Bitmap getGraphic() {
        return _bitmap;
    }
    public Speed getSpeed() {
        return _speed;
    }
    //pull variable functions 
	public void cantPull() {
		this._noPull = true;
	}
	public void canPull() {
		this._noPull = false;
	}
	public boolean getPullState() {
		return this._noPull;
	}
	//move functions
	public boolean move(){
		if(_speed.getMove()){
			switch(_id){
			case tFrog:
				moveFrog();
				break;
			case tWhirl:
				break;
			default:
				shiftX(_speed.getSpeed()*FloatMath.cos(_speed.getAngleRad()));
		    	shiftY(_speed.getSpeed()*FloatMath.sin(_speed.getAngleRad()));
				break;
			}
			return true;
		}
		return false;
	}
	private void moveFrog(){
		setX((float)(_frogCentreX + Math.sin(_frogAngle)*_frogRadius));
		setY((float)(_frogCentreY + Math.cos(_frogAngle)*_frogRadius));
		_frogAngle+=_speed.getSpeed()/100;
	}
}
