package example.whirlpool;

import java.util.Random;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.FloatMath;
import example.whirlpool.Panel;

class GraphicObject {
	//enum used to decide what type of sprite
	public enum objtype {
		tDefault(0, 0, 0, 0), 
		tWhirl(100, 100, 0, 0),
		tDuck(30, 30, 4, 0),
		tFrog(63, 45, 5, 0), 
		tShark(10, 40, 5, new Random().nextInt(360)+1), 
		tBoat(50, 15, 0, 0),
		tDiver(64, 64, 4, 135);

		int width;
		int height;
		float speed;
		float angle;
		objtype(int a, int b, float c, float d){
			width = a;
			height = b;
			speed = c;
			angle = d;
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
    		private void checkAngle(){		//Makes sure it's always within 0-360
    			if(_angle >= 360.0f || _angle < 0.0f){
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
    		if(_id!=objtype.tDiver){
	    		float angletemp = _angle.getAngle();
	    		switch((int)(angletemp/90)){
	    			case 0:
	    			case 2:	_angle.shiftAngle(2*(90 - (int)(angletemp%90)));
	    					break;
	    			case 1:
	    			case 3:	_angle.shiftAngle(-2*((int)(angletemp%90)));
	    					break;
	    		}
    		}else if(_id==objtype.tDiver){
    			_angle.shiftAngle(180);
    		}
    		setFlipH(true);
    		if(_flipped){
    			flip();
    		}
    	}
    	void VerBounce(){
    		if(_id!=objtype.tDiver){
	    		float angletemp = _angle.getAngle();
	    		switch((int)(angletemp/90)){
	    			case 0:
	    			case 2:	_angle.shiftAngle(-2*((int)(angletemp%90)));
	    					break;
	    			case 1:
	    			case 3:	_angle.shiftAngle(2*(90 - (int)(angletemp%90)));
	    					break;
	    		}
    		}else if(_id==objtype.tDiver){
    			_angle.shiftAngle(180);
    		}
    		setFlipH(true);
    		if(_flipped){
    			flip();
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
	private boolean _flipped, _flipV, _flipH;
	private float _x = 0;
    private float _y = 0;
    private float _rot = 0;
    private int rotAngle =1;
    private Bitmap _bitmap;
    private Speed _speed = new Speed();

    public GraphicObject(objtype type){
    	_id = type;
        init();
        _radius =  (int) FloatMath.sqrt(((float)_width*_width) + ((float)_height*_height));
    }
    public void draw(Canvas c){
    	Rect rect;
    	
    	c.save();
    	
    	if (_id == objtype.tWhirl)
    	{
    		rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
    		
    		c.translate(getX(), getY());
    		c.rotate(getRotation());
    		
    		c.drawBitmap(getGraphic(), null, rect,  null);

    	}else if(_id == objtype.tFrog){
    		rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
    		c.translate(getX(), getY());
    		c.rotate((_frogAngle*180/PI));
    		c.drawBitmap(getGraphic(), null, rect,  null);
    	}else{
    		rect = new Rect((int)getActualX(), (int)getActualY(), (int)getActualX() + _width, (int)getActualY() + _height);
    		c.drawBitmap(getGraphic(), null, rect,  null);
    	}
    	
    	
    	//c.translate(-getActualX(), -getActualY());
    	
    	//c.scale(getXScale(), getYScale());
    	//c.translate(((1/getXScale())-1)*(getX()), ((1/getYScale())-1)*(getY()));
        //c.drawBitmap(getGraphic(), matrix, null);
        c.restore();
    }
	public void init(){
		switch(_id){
		case tWhirl:
			_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.whirlpool);
			_speed.setMove(false);
			break;
		case tDuck:
			_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.duck);
			setX(0.0f);
        	setY((int) Panel.sScreen.getCentreY() - getGraphic().getHeight() / 2);
        	_speed.setMove(true);
			break;
		case tFrog:
			//create temp image to display frog until animation code has been added
			_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.frogtemp);
			setX(Panel.sScreen.getWidth()/2);
			setY(Panel.sScreen.getHeight()/2);
			setFrogCentreX(getX());
			setFrogCentreY(getY());
			setFrogRadius(50);
            _speed.setMove(true);
			break;
		case tShark:
			_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.shark);
			setX((float) (new Random().nextInt(Panel.sScreen.getWidth())));
        	setY((float) (new Random().nextInt(Panel.sScreen.getHeight())));
        	_speed.setMove(true);
			break;
		case tBoat:
			_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.boat);
			setX((float) (new Random().nextInt(Panel.sScreen.getWidth())));
            setY((float) (new Random().nextInt(Panel.sScreen.getHeight())));
            _speed.setMove(true);
			break;
		case tDiver:
			_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.diver);
			setX((float) (new Random().nextInt(Panel.sScreen.getWidth())));
            setY((float) (new Random().nextInt(Panel.sScreen.getHeight())));
            _speed.setMove(true);
            _flipped = true;
		default:
			break;
		}
		_width = _id.width;
		_height = _id.height;
		_speed.setAngle(_id.angle);
		_speed.setSpeed(_id.speed);
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
        return _x + getWidth() / 2;
    }
    public float getActualX() {
        return _x;
    }
    public float getFrogCentreX() {
		return _frogCentreX;
	}
    public void setX(float value) {
        _x = value - getWidth() / 2;
    }
    public void setActualX(float value) {
        _x = value;
    }
    public void setFrogCentreX(float frogCentreX) {
		this._frogCentreX = frogCentreX;
	}
    public void shiftX(float shift){
    	_x += shift;
    }
    //getters and setters for Y components
    public float getY() {
        return _y + getHeight() / 2;
    }
    public float getActualY() {
        return _y;
    }
    public float getFrogCentreY() {
		return _frogCentreY;
	}
    public void setY(float value) {
        _y = value - getHeight() / 2;
    }
    public void setActualY(float value) {
        _y = value;
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
	public void setClockwise(boolean clockwise){
    	if (clockwise) rotAngle = 1;
    	else rotAngle = -1;
    }
    public int getClockwise(){
    	return rotAngle;
    }
    public float getRotation(){
    	if (_id == objtype.tWhirl){
    		_rot+= (2*getClockwise());
    		if (_rot >= 360)_rot=0;
    		if (_rot < 0)_rot=360;
    		return _rot;
    	}else{
    		return 0.0f;
    	}
    }
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
			case tBoat:
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
		setX((float)(_frogCentreX + FloatMath.sin(_frogAngle)*_frogRadius));
		setY((float)(_frogCentreY + FloatMath.cos(_frogAngle)*_frogRadius));
		_frogAngle+=_speed.getSpeed()/100;
	}
	public boolean getFlipped() {
		return _flipped;
	}
	public void setFlipped(boolean _flipped) {
		this._flipped = _flipped;
	}
	public boolean getFlipV() {
		return _flipV;
	}
	public void setFlipV(boolean _flipV) {
		this._flipV = _flipV;
	}
	public boolean getFlipH() {
		return _flipH;
	}
	public void setFlipH(boolean _flipH) {
		this._flipH = _flipH;
	}
	public void flip(){
		boolean tempflip = false;
		Matrix flipMatrix = new Matrix();

		if(_flipH){
			flipMatrix.setScale(-1, 1);
			tempflip = true;
			_flipH = false;
		}else if(_flipV){
			flipMatrix.setScale(1, -1);
			tempflip = true;
			_flipV = false;
		}
		if(tempflip){
			Bitmap temp;
			temp = Bitmap.createBitmap(_bitmap, 0, 0, _bitmap.getWidth(), _bitmap.getHeight(), flipMatrix, false);
			_bitmap = temp;
		}
	}
}