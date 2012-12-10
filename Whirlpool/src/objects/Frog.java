package objects;

import states.MainActivity;
import example.whirlpool.R;
import logic.Panel;
import logic.Screen.ScreenSide;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;

public class Frog extends GraphicObject{
	private float _frogCentreX, _frogCentreY, _frogAngle, _frogRadius;
	
	public Frog(){
		_id = objtype.tFrog;
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
		_bitmap = BitmapFactory.decodeResource(Panel.sRes, _id.bitmap);
		setX(Panel.sScreen.getWidth()/2);
		setY((Panel.sScreen.getHeight()/2)-(Panel.sScreen.getHeight()/4));
		setFrogCentreX(getX());
		setFrogCentreY(getY());
		setFrogRadius(100);
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
			_speed.shiftAngle(2.5f);
	    	/*
			setX((float)(_frogCentreX + FloatMath.sin(_frogAngle)*_frogRadius));
			setY((float)(_frogCentreY + FloatMath.cos(_frogAngle)*_frogRadius));
			_frogAngle+=_speed.getSpeed()/100;*/
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
	
	public float getFrogAngle() {
		return _frogAngle;
	}
	public float getFrogRadius() {
		return _frogRadius;
	}
	public void setFrogCentreX(float frogCentreX) {
		this._frogCentreX = frogCentreX;
	}
	public void setFrogCentreY(float frogCentreY) {
		this._frogCentreY = frogCentreY;
	}
	public void setFrogAngle(float frogAngle) {
		this._frogAngle = frogAngle;
	}
	public void setFrogRadius(float _frogRadius) {
		this._frogRadius = _frogRadius;
	}
	public float getFrogCentreX() {
		return _frogCentreX;
	}
	public float getFrogCentreY() {
		return _frogCentreY;
	}
}
