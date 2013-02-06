package objects;

import logic.Animate;
import logic.Constants;
import logic.Imports;
import logic.Panel;
import logic.Screen.ScreenSide;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;

public class Frog extends GraphicObject{
	private float _frogCentreX, _frogCentreY, _frogAngle, _frogRadius;
	Animate _animate;
	
	public Frog(){
		_id = objtype.tFrog;
        init();
	}
	@Override
	public void draw(Canvas c) {
		c.save();
		//doesn't seen to rotate
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		c.translate(getX(), getY());
		c.rotate((-_frogAngle*180/PI));
		c.drawBitmap(getGraphic(), _animate.getPortion(), rect,  null);
		
		c.restore();
	}

	@Override
	public void init() {
		_bitmap = Imports.getFrog();
		setX(Panel.sScreen.getWidth()/2);
		setY(Panel.sScreen.getHeight()/2);
		setFrogCentreX(getX());
		setFrogCentreY(getY());
		setFrogRadius((Constants.getScreen().getHeight()/2)-70);
		_width = _bitmap.getWidth()/_id.frames;
		_height = _bitmap.getHeight();
        _speed.setMove(true);
        _animate = new Animate(_id.frames, _bitmap.getWidth(), _bitmap.getHeight());
		_speed.setAngle(_id.angle);
		_speed.setSpeed(_id.speed);
		_radius =  (int) FloatMath.sqrt(((float)_width*_width) + ((float)_height*_height));
	}

	@Override
	public boolean move() {
		if(_speed.getMove()){
			setX((float)(_frogCentreX + FloatMath.sin(_frogAngle)*_frogRadius));
			setY((float)(_frogCentreY + FloatMath.cos(_frogAngle)*_frogRadius));
			_frogAngle-=_speed.getSpeed()/150;
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
		case BottomLeft:
			_speed.VerBounce();
        	setActualY(height - getHeight());
        	_speed.HorBounce();
        	setActualX(-getActualX());
			break;
		case BottomRight:
			_speed.VerBounce();
        	setActualY(height - getHeight());
        	_speed.HorBounce();
        	setActualX(width - getWidth());
			break;
		case TopLeft:
			_speed.VerBounce();
            setActualY(-getActualY());
            _speed.HorBounce();
        	setActualX(-getActualX());
			break;
		case TopRight:
			_speed.VerBounce();
            setActualY(-getActualY());
            _speed.HorBounce();
        	setActualX(width - getWidth());
			break;
		default:
			break;
		}
	}
	
	public void frame(){
		// Move Objects
        if(move()){
        	border();
        }
        _animate.animateFrame();
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
