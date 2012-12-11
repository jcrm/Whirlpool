package objects;

import java.util.Random;

import states.MainActivity;

import logic.Constants;
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
		tDefault(1, 1, 0, 0, R.drawable.ic_launcher, 						1), 
		tWhirl(	6, 3, 0, 0, R.drawable.whirlpool, 						1),
		tDuck(	13, 7, 4, 0, R.drawable.duckleftandright2, 				16),
		//not sure what numbers need for frame width and hieght
		tFrog(	7, 4, 2, 0, R.drawable.frog2, 							16), 
		tShark(	10, 40, 5, new Random().nextInt(360)+1, R.drawable.shark, 	1), 
		tBoat(	50, 15, 0, 0, R.drawable.boat, 								1),
		tDiver(	64, 64, 4, 45, R.drawable.diver, 							1);
		
		int width;
		int height;
		float speed;
		float angle;
		int bitmap;
		int frames;
		
		objtype(int a, int b, float c, float d, int e, int f){
			width = Constants.getScreen().getWidth()/a;
			height = Constants.getScreen().getHeight()/b;
			//TODO set min width/height
			speed = c;
			angle = d;
			bitmap = e;
			frames = f;
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
	private boolean _pull;// = false;
	Rect _portion;
	
	
    public GraphicObject(){}
    
    abstract public void draw(Canvas c);
    
    abstract public void init();
    
    abstract public boolean move();
    
    abstract public void borderCollision(Screen.ScreenSide side, float width, float height);
    
    abstract public void frame();
    
    
	public objtype getId(){
		return _id;
	}
	public void setId(objtype id){
		_id = id;
	}
	
	
	public void border(){
		int HEIGHT = Panel.sScreen.getHeight();
		int WIDTH = Constants.getLevel().getLevelWidth();
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
    
    public void setPull(boolean a){
    	_pull = a;
    }
	public boolean getPullState() {
		return this._pull;
	}
    
}
