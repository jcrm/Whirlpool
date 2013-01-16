package objects;

import java.util.Random;

import states.MainActivity;
import logic.Imports;
import logic.Panel;
import logic.Screen.ScreenSide;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.FloatMath;

public class Diver extends GraphicObject{
	protected boolean _flipped, _flipV, _flipH; //TODO What does _flipped even do?

	public Diver(){
		_id = objtype.tDiver;
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
		_bitmap = Imports.getDiver();
		setX((float) (new Random().nextInt(Panel.sScreen.getWidth())));
    	setY((float) (new Random().nextInt(Panel.sScreen.getHeight())));
        _speed.setMove(true);
        _flipped = true;

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
            setFlipH(true);
			break;
		case Bottom:
        	setActualY(height - getHeight());        	
        	_speed.shiftAngle(180);
        	setFlipH(true);
			break;
		case Left:
        	setActualX(-getActualX());
        	_speed.shiftAngle(180);
        	setFlipH(true);
			break;
		case Right:
        	setActualX(width - getWidth());
        	_speed.shiftAngle(180);
        	setFlipH(true);
			break;
		}
	}

	public void frame(){
		// Move Objects
        if(move()){
        	border();
        }
	}

	public boolean getFlipped() {
		return _flipped;
	}
	public void setFlipped(boolean _flipped) {
		this._flipped = _flipped;
		if(_flipped == true){
			flip();
		}
	}
	public boolean getFlipV() {
		return _flipV;
	}
	public void setFlipV(boolean _flipV) {
		this._flipV = _flipV;
		if(_flipV == true){
			flip();
		}
	}
	public boolean getFlipH() {
		return _flipH;
	}
	public void setFlipH(boolean _flipH) {
		this._flipH = _flipH;
		if(_flipH == true){
			flip();
		}
	}
	public void flip(){
		boolean tempflip = false;
		Matrix flipMatrix = new Matrix();

		if(_flipH){
			flipMatrix.setScale(-1, 1);
			tempflip = true;
			_flipH = false;
		}else if(_flipV){
			flipMatrix.setScale(1, -1);
			tempflip = true;
			_flipV = false;
		}
		if(tempflip){
			Bitmap temp = Bitmap.createBitmap(_bitmap, 0, 0, _bitmap.getWidth(), _bitmap.getHeight(), flipMatrix, false);
			_bitmap = temp;
		}
	}


}