package example.whirlpool;

import example.whirlpool.GraphicObject;
import android.view.MotionEvent;
import java.lang.Math;
import android.util.FloatMath;

//collision class
public class Func{
	//box collision - graphic is the sprite being tested, event is the touch
	static public boolean boxCollision(GraphicObject graphic, MotionEvent event){
    	if((event.getX()>=graphic.getX()) && (event.getX()<=graphic.getX() + graphic.getGraphic().getWidth())){
    		if((event.getY()>=graphic.getY()) && (event.getY()<=graphic.getY() +graphic.getGraphic().getHeight())){
    			return true;
    		}
    	}
    	return false;
    }
	//circle collision - graphic is the sprite being tested, event is the touch
	static public boolean circleCollision(float x1, float y1, float r1, float x2, float y2, float r2){
		float width, height, radius;
		width = x1 - x2;
		height = y1 - y2;
		//10 is the collision radius of touch i created can be changed
		radius = r1+r2;
		if((width*width) + (height*height) < (radius*radius)){
			return true;
		}
		return false;
	}
	//checks borders of the screen
	//coord and speed are sub classes of graphic and can be changed when our sprite class has been created
	//WIDTH, HEIGHT are screen width and height
	static public void borders(GraphicObject graphic, int WIDTH,int HEIGHT){
		// borders for x...
        if (graphic.getActualX() < 0) {
        	graphic.getSpeed().HorBounce();
        	graphic.setActualX(-graphic.getActualX());
        } else if (graphic.getActualX() + graphic.getWidth() > WIDTH) {
        	graphic.getSpeed().HorBounce();
        	graphic.setActualX(WIDTH - graphic.getWidth());
        }
        // borders for y...
        if (graphic.getActualY() < 0) {
            graphic.getSpeed().VerBounce();
            graphic.setActualY(-graphic.getActualY());
        } else if (graphic.getActualY() + graphic.getHeight() > HEIGHT) {
        	graphic.getSpeed().VerBounce();
        	graphic.setActualY(HEIGHT - graphic.getHeight());
        }
	}
	static float fMod(float num, int divide){
		float fresult = num - FloatMath.floor(num);
		return ((float)((int)num%divide) + fresult);
	}
	static float calcAngle(float x1, float y1, float x2, float y2){
		float angle1;
		if(x2-x1 == 0) angle1 = 90.0f;
		else angle1 = (float) ((float)(180.0f/Math.PI)*Math.atan((y2 - y1)/(x2 - x1)));
		if(x2 < x1 && y2 < y1){
			angle1 += 180.0f;
		}
		else if(x2 < x1 && !(y2 < y1)){
			angle1 = 180.0f - fMod(Math.abs(angle1), 90);
		}
		else if(!(x2 < x1) && y2 < y1){
			angle1 = 360.0f - fMod(Math.abs(angle1), 90);
		}
		return fMod((fMod((angle1), 360)+360), 360);
	}
}
