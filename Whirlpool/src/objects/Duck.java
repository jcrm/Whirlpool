package objects;

import logic.Panel;
import logic.Screen.ScreenSide;
import example.whirlpool.R;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;

public class Duck extends GraphicObject{
	
	public Duck(){
		_id = objtype.tDuck;
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
		_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.duck);
		setX(0.0f);
    	setY((int) Panel.sScreen.getCentreY() - getGraphic().getHeight() / 2);
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

}
