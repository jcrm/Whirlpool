package objects;

import java.util.Random;

import states.MainActivity;

import example.whirlpool.R;
import logic.ImageImports;
import logic.Panel;
import logic.Screen.ScreenSide;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;

public class Shark extends GraphicObject{
	
	public Shark(){
		_id = objtype.tShark;
		init();
	}

	@Override
	public void draw(Canvas c) {
		c.save();
		
		Rect rect = new Rect((int)getActualX(), (int)getActualY(), (int)getActualX() + _width, (int)getActualY() + _height);
		c.drawBitmap(getGraphic(), null, rect,  null);
		
		c.restore();
	}

	@Override
	public void init() {
		_bitmap = ImageImports.getShark();
		setX((float) (new Random().nextInt(Panel.sScreen.getWidth())));
    	setY((float) (new Random().nextInt(Panel.sScreen.getHeight())));
    	_speed.setMove(true);
		
		_width = _id.width;
		_height = _id.height;
		_speed.setAngle(_id.angle);
		_speed.setSpeed(_id.speed);
		_radius =  (int) FloatMath.sqrt(((float)_width*_width) + ((float)_height*_height));
	}

	@Override
	public boolean move() {
		if(_speed.getMove()){
			shiftX(_speed.getSpeed()*FloatMath.cos(_speed.getAngleRad()));
	    	shiftY(_speed.getSpeed()*FloatMath.sin(_speed.getAngleRad()));
			return true;
		}
		return false;
	}
	
	@Override
	public void borderCollision(ScreenSide side, float width, float height) {
		switch(side){
		case Top:
			_speed.VerBounce();
            setActualY(-getActualY());
			break;
		case Bottom:
			_speed.VerBounce();
        	setActualY(height - getHeight());
			break;
		case Left:
			_speed.HorBounce();
        	setActualX(-getActualX());
			break;
		case Right:
			_speed.HorBounce();
        	setActualX(width - getWidth());
			break;
		}
	}
	
	public void frame(){
		// Move Objects
        if(move()){
        	border();
        }
	}

}
