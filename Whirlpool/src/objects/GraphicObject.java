package objects;

import java.util.Random;

import logic.Animate;
import logic.Constants;
import logic.Imports;
import logic.Panel;
import logic.Screen;
import movement.Speed;
import android.graphics.Bitmap;
import android.graphics.Canvas;

interface ObjectFunctions{
	public void draw(Canvas canvas);
	public void init();
	public boolean move();
}
public abstract class GraphicObject {//implements ObjectFunctions{
	//enum used to decide what type of sprite
	public enum objtype {
		tDefault(0, 1, 1, 0, 0, 1), 
		tWhirl(1, 128, 128, 0, 0, 30),
		tDuck(2, 64, 64, 6, 0, 16),
		//not sure what numbers need for frame width and height
		tFrog(3, 96, 96, 4, 0, 16), 
		tShark(4, 64, 64, 5, new Random().nextInt(360)+1, 1), 
		tBoat(5,  64, 64, 0, 0, 1),
		tDiver(6, 128, 128, 4, new Random().nextInt(360), 16);
		
		int tWidth;
		int tHeight;
		float tSpeed;
		float tAngle;
		int tFrames;
		
		objtype(int type, int width, int height, float speed, float angle, int frames){
			if(type != 0){
				Imports.scaledBitmap(type, width*frames, height);
			}
			//TODO set min width/height
			tSpeed = speed;
			tAngle = angle;
			tFrames = frames;
		}
	}
	//private variables
	protected objtype mId = objtype.tDefault;
	protected int mWidth, mHeight, mRadius = 0; //TODO Don't think we use Radius, might delete.
	protected float mX = 0;
	protected float mY = 0;
	protected Bitmap mBitmap;
	protected Speed mSpeed = new Speed();
	private boolean mPull;// = false;
	private static Object mScreenLock;
	protected Animate mAnimate;
	
    public GraphicObject(){
    	mScreenLock=Constants.getLock();
    }
    
    abstract public void draw(Canvas c);
    
    abstract public void init();
    
    abstract public boolean move();
    
    abstract public void borderCollision(Screen.ScreenSide side, float width, float height);
    
    abstract public void frame();
    
    
	public objtype getId(){
		return mId;
	}
	public void setId(objtype id){
		mId = id;
	}
	
	public boolean border(){
		int HEIGHT = Panel.sScreen.getHeight();
		int WIDTH = Constants.getLevel().getLevelWidth();
		boolean hit = false;
        if (getActualX() < 0) {
        	if(getActualY() < 0){
            	borderCollision(Screen.ScreenSide.TopLeft, WIDTH, HEIGHT);
            }else if (getActualY() + getHeight() > HEIGHT){
            	borderCollision(Screen.ScreenSide.BottomLeft, WIDTH, HEIGHT);
            }else{
            	borderCollision(Screen.ScreenSide.Left, WIDTH, HEIGHT);
            }
        	hit = true;
        }else if(getActualX() + getWidth() > WIDTH){
        	if(getActualY() < 0){
            	borderCollision(Screen.ScreenSide.TopRight, WIDTH, HEIGHT);
            }else if(getActualY() + getHeight() > HEIGHT) {
            	borderCollision(Screen.ScreenSide.BottomRight, WIDTH, HEIGHT);
            }else{
            	borderCollision(Screen.ScreenSide.Right, WIDTH, HEIGHT);
            }
        	hit = true;
        }
        if (getActualY() < 0) {
        	borderCollision(Screen.ScreenSide.Top, WIDTH, HEIGHT);
        	hit = true;
        } else if (getActualY() + getHeight() > HEIGHT) {
        	borderCollision(Screen.ScreenSide.Bottom, WIDTH, HEIGHT);
        	hit = true;
        }
        return hit;
	}
	
	
	//getters and setters for X components
    public float getX() {
        return mX + getWidth() / 2;
    }
    public float getActualX() {
        return mX;
    }
    public void setX(float value) {
        mX = value - getWidth() / 2;
    }
    public void setActualX(float value) {
        mX = value;
    }
    public void shiftX(float shift){
    	synchronized(mScreenLock){
    		mX += shift;
    	}
    }
    //getters and setters for Y components
    public float getY() {
        return mY + getHeight() / 2;
    }
    public float getActualY() {
        return mY;
    }
    public void setY(float value) {
        mY = value - getHeight() / 2;
    }
    public void setActualY(float value) {
        mY = value;
    }
    public void shiftY(float shift){
    	//synchronized(screenLock){
    		mY += shift;
    	//}
    }
    
    
    public int getWidth(){
		return mWidth;
    }
    public int getHeight(){
		return mHeight;
    }
	public void setWidth(int width){
		mWidth = width;
	}
	public void setHeight(int height){
		mHeight = height;
	}
	
	
	//getters and setters for angles and radius
    public float getRadius(){
    	return mRadius;
    }
	public void setRadius(int radius){
		mRadius = radius;
	}
	
	
	public void setAngle(float a){
		mSpeed.setAngle(a);
	}
    public Bitmap getGraphic() {
        return mBitmap;
    }
    public Speed getSpeed() {
        return mSpeed;
    }
    
    public void setPull(boolean a){
    	mPull = a;
    }
	public boolean getPullState() {
		return this.mPull;
	}
}
