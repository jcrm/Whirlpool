package example.whirlpool;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.lang.Math;

import example.whirlpool.MainActivity.Panel;
import android.util.FloatMath;


class GraphicObject {
	//enum used to decide what type of sprite
	private float PI = 3.141592653589793238f;
	public enum objtype {
		tDefault(0, 0, 0), 
		tWhirl(100, 100, 0),
		tDuck(30, 30, 0),
		tFrog(30, 30, 0), 
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
	
	public objtype id = objtype.tDefault;
	public int width, height, radius = 0;
	private float xscale, yscale;
	
	private float _x = 0;
    private float _y = 0;
    
    private Bitmap _bitmap;
    private Speed _speed = new Speed();
	
	public void init(){
		switch(id){
		case tWhirl:
			_bitmap = BitmapFactory.decodeResource(Panel.res, R.drawable.whirlpool);
			break;
		case tDuck:
			_bitmap = BitmapFactory.decodeResource(Panel.res, R.drawable.duck);
			setX((int) Panel.screen.getCentreX() - getGraphic().getWidth() / 2);
        	setY((int) Panel.screen.getCentreY() - getGraphic().getHeight() / 2);
			break;
		case tFrog:
			_bitmap = BitmapFactory.decodeResource(Panel.res, R.drawable.frog);
			boolean dir = new Random().nextBoolean();
			setX((float) (new Random().nextInt(Panel.screen.getWidth())));
            setY((float) (new Random().nextInt(Panel.screen.getHeight())));
            if(dir){
            	_speed.setSpeed(3);
            	_speed.setAngle(90);
            }else{
            	_speed.setSpeed(3);
            	_speed.setAngle(0);
            }
            _speed.setMove(true);
			break;
		case tShark:
			_bitmap = BitmapFactory.decodeResource(Panel.res, R.drawable.shark);
			setX((float) (new Random().nextInt(Panel.screen.getWidth())));
        	setY((float) (new Random().nextInt(Panel.screen.getHeight())));
        	_speed.setSpeed(new Random().nextInt(5)+1);
        	_speed.setAngle(new Random().nextInt(360)+1);
        	_speed.setMove(true);
			break;
		case tBoat:
			_bitmap = BitmapFactory.decodeResource(Panel.res, R.drawable.boat);
			setX((float) (new Random().nextInt(Panel.screen.getWidth())));
            setY((float) (new Random().nextInt(Panel.screen.getHeight())));
            _speed.setSpeed(new Random().nextInt(3));
            _speed.setAngle(new Random().nextInt(360));
            if(_speed.getSpeed() == 0){
            	_speed.setSpeed(1);
            }
            _speed.setMove(true);
			break;
		default:
			break;
		}
		width = id.width;
		height = id.height;
		setScale();
	}
	
	//speed class used for determining direction from a tutorial found online 
    public class Speed {
    	
    	private class Angle{
    		private float angle = 0;
    		
    		Angle(float a){
    			angle = a;
    		}
    		
    		public float getAngle(){
    			return angle;
    		}
    		public float getAngleRad(){
    			return (angle*PI/180);
    		}
    		public void setAngle(float a){
    			angle = a;
    			checkAngle();
    		}
    		public void shiftAngle(float a){
    			angle += a;
    			checkAngle();
    		}
    		private void checkAngle(){		//Makes sure it's always withing 0-360
    			if(angle > 360.0f || angle < 0.0f){
    				angle = ((angle%360)+360)%360;
    			}
    		}
    	}
    	
    	private boolean move = true;
    	private float speed = 0;
    	private Angle angle = new Angle(0);
    	
    	public void HorBounce(){
    		float angletemp = angle.getAngle();
    		switch((int)(angletemp/90)){
    			case 0:
    			case 2:	angle.shiftAngle(2*(90 - (int)(angletemp%90)));
    					break;
    			case 1:
    			case 3:	angle.shiftAngle(-2*((int)(angletemp%90)));
    					break;
    		}
    	}

    	void VerBounce(){
    		float angletemp = angle.getAngle();
    		switch((int)(angletemp/90)){
    			case 0:
    			case 2:	angle.shiftAngle(-2*((int)(angletemp%90)));
    					break;
    			case 1:
    			case 3:	angle.shiftAngle(2*(90 - (int)(angletemp%90)));
    					break;
    		}
    	}
    	public float getXSpeed(){
    		return speed*FloatMath.cos(angle.getAngleRad());
    	}
    	public float getYSpeed(){
    		return speed*FloatMath.sin(angle.getAngleRad());
    	}
    	public boolean getMove(){
    		return move;
    	}
    	public void setMove(boolean a){
    		move = a;
    	}
    	public float getAngle(){
			return angle.getAngle();
		}
    	public float getAngleRad(){
			return angle.getAngleRad();
		}
		public void setAngle(float a){
			angle.setAngle(a);
		}
		public void shiftAngle(float a){
			angle.shiftAngle(a);
		}
		public float getSpeed(){
			return speed;
		}
		public void setSpeed(float a){
			speed = a;
		}
		public void shiftSpeed(float a){
			speed += a;
		}
    }

    public float getX() {
        return _x + _bitmap.getWidth() / 2;
    }
    public float getActualX() {
        return _x;
    }
    public void setX(float value) {
        _x = value - _bitmap.getWidth() / 2;
    }
    public void shiftX(float shift){
    	_x += shift;
    }
    public float getY() {
        return _y + _bitmap.getHeight() / 2;
    }
    public float getActualY() {
        return _y;
    }
    public void setY(float value) {
        _y = value - _bitmap.getHeight() / 2;
    }
    public void shiftY(float shift){
    	_y += shift;
    }
    public void setScale(){
		setXScale((float)width/_bitmap.getWidth());
		setYScale((float)height/_bitmap.getHeight());
	}
    public float getRadius(){
    	return radius;
    }
    //constructor requires bitmap when creating 
    public GraphicObject(objtype type){
    	id = type;
        init();
        radius =  (int) Math.sqrt(((float)width*width) + ((float)height*height));
    }
    public void draw(Canvas c){
    	c.save();
    	c.scale(getXScale(), getYScale());
    	c.translate(((1/getXScale())-1)*(getX()), ((1/getYScale())-1)*(getY()));
        c.drawBitmap(getGraphic(), getActualX(), getActualY(), null);
        c.restore();
    }
    public Bitmap getGraphic() {
        return _bitmap;
    }
    public Speed getSpeed() {
        return _speed;
    }
	public float getXScale() {
		return xscale;
	}
	public void setXScale(float xscale) {
		this.xscale = xscale;
	}
	public float getYScale() {
		return yscale;
	}
	public void setYScale(float yscale) {
		this.yscale = yscale;
	}
}
