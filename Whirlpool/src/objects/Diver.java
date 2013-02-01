package objects;

import java.util.Random;

import logic.Animate;
import logic.Imports;
import logic.Panel;
import logic.Screen.ScreenSide;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;

public class Diver extends GraphicObject{
	Animate _animate;
	boolean _flipped;
	
	public Diver(){
		_id = objtype.tDiver;
		init();
	}
	@Override
	public void draw(Canvas c) {
		c.save();
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		c.translate(getX(), getY());
		c.rotate(_speed.getAngle()+180);
		c.drawBitmap(getGraphic(), _animate.getPortion(), rect,  null);
		c.restore();
	}
	@Override
	public void init() {
		_bitmap = Imports.getDiver();
		setX((float) (new Random().nextInt(Panel.sScreen.getWidth())));
    	setY((float) (new Random().nextInt(Panel.sScreen.getHeight())));
        _speed.setMove(true);
        _animate = new Animate(_id.frames, _bitmap.getWidth(), _bitmap.getHeight());
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
            setActualY(-getActualY());
    		_speed.shiftAngle(180);
			break;
		case Bottom:
        	setActualY(height - getHeight());        	
        	_speed.shiftAngle(180);
			break;
		case Left:
        	setActualX(-getActualX());
        	_speed.shiftAngle(180);
			break;
		case Right:
        	setActualX(width - getWidth());
        	_speed.shiftAngle(180);
			break;
		case TopLeft:
			setActualX(-getActualX());
			setActualY(-getActualY());
    		_speed.shiftAngle(180);
			break;
		case TopRight:
			setActualX(width - getWidth());
			setActualY(-getActualY());
    		_speed.shiftAngle(180);
			break;
		case BottomLeft:
			setActualX(-getActualX());
			setActualY(height - getHeight());        	
        	_speed.shiftAngle(180);
			break;
		case BottomRight:
			setActualX(width - getWidth());
			setActualY(height - getHeight());        	
        	_speed.shiftAngle(180);
			break;
		}
	}
	public void frame(){
		// Move Objects
        if(move()){
        	border();
        	checkFlip();
        }
        _animate.animateFrame();
	}
	public void checkFlip(){
		if(!_flipped){
			if(_speed.getAngle()>270 || _speed.getAngle()<90){
				_flipped = true;
				_bitmap = Imports.getDiverFlipped();				
			}
		}else if(_flipped){
			if(_speed.getAngle()<=270 && _speed.getAngle()>=90){
				_flipped = false;
				_bitmap = Imports.getDiver();
			}
		}
	}

}