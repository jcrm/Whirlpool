package objects;

import java.util.Random;

import logic.Panel;
import logic.Screen;
import movement.Speed;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.FloatMath;
import example.whirlpool.R;

interface ObjectFunctions{
	public void draw(Canvas c);
	public void init();
	public boolean move();
}
public abstract class GraphicObject {//implements ObjectFunctions{
	//enum used to decide what type of sprite
	public enum objtype {
		tDefault(0, 0, 0, 0), 
		tWhirl(100, 100, 0, 0),
		tDuck(30, 30, 4, 0),
		tFrog(30, 30, 5, 0), 
		tShark(10, 40, 5, new Random().nextInt(360)+1), 
		tBoat(50, 15, 0, 0),
		tDiver(64, 32, 4, 45);
		
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
	
	//private variables
	protected float PI = 3.141592653589793238f;
	protected objtype _id = objtype.tDefault;
	protected int _width, _height, _radius = 0; //TODO Don't think we use Radius, might delete.
	protected float _x = 0;
	protected float _y = 0;
	protected Bitmap _bitmap;
	protected Speed _speed = new Speed();
	private boolean _pull;
	
	
    public GraphicObject(){}
    
    abstract public void draw(Canvas c);
    
    abstract public void init();
    
    abstract public boolean move();
    
    abstract public void borderCollision(Screen.ScreenSide side, float width, float height);
    
    
	public objtype getId(){
		return _id;
	}
	public void setId(objtype id){
		_id = id;
	}
	
	
	public void border(int WIDTH,int HEIGHT){
        if (getActualX() < 0) {
        	borderCollision(Screen.ScreenSide.Left, WIDTH, HEIGHT);
        } else if (getActualX() + getWidth() > WIDTH) {
        	borderCollision(Screen.ScreenSide.Right, WIDTH, HEIGHT);
        }

        if (getActualY() < 0) {
        	borderCollision(Screen.ScreenSide.Top, WIDTH, HEIGHT);
        } else if (getActualY() + getHeight() > HEIGHT) {
        	borderCollision(Screen.ScreenSide.Bottom, WIDTH, HEIGHT);
        }
	}
	
	
	//getters and setters for X components
    public float getX() {
        return _x + getWidth() / 2;
    }
    public float getActualX() {
        return _x;
    }
    public void setX(float value) {
        _x = value - getWidth() / 2;
    }
    public void setActualX(float value) {
        _x = value;
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
    public void setY(float value) {
        _y = value - getHeight() / 2;
    }
    public void setActualY(float value) {
        _y = value;
    }
    public void shiftY(float shift){
    	_y += shift;
    }
    
    
    public int getWidth(){
		return _width;
    }
    public int getHeight(){
		return _height;
    }
	public void setWidth(int width){
		_width = width;
	}
	public void setHeight(int height){
		_height = height;
	}
	
	
	//getters and setters for angles and radius
    public float getRadius(){
    	return _radius;
    }
	public void setRadius(int radius){
		_radius = radius;
	}
	
	
	public void setAngle(float a){
		_speed.setAngle(a);
	}
    public Bitmap getGraphic() {
        return _bitmap;
    }
    public Speed getSpeed() {
        return _speed;
    }
    
    public void cantPull() {
		this._pull = true;
	}
	public void canPull() {
		this._pull = false;
	}
	public boolean getPullState() {
		return this._pull;
	}
    
}
