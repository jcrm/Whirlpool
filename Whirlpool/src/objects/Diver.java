package objects;

import example.whirlpool.R;
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
		_bitmap = BitmapFactory.decodeResource(Panel.sRes, R.drawable.diver);
		setX(Panel.sScreen.getWidth()/2);
		setY(Panel.sScreen.getHeight()/2);
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
			_speed.VerBounce();
            setActualY(-getActualY());
            setFlipV(true);
			break;
		case Bottom:
			_speed.VerBounce();
        	setActualY(height - getHeight());
        	setFlipV(true);
			break;
		case Left:
			_speed.HorBounce();
        	setActualX(-getActualX());
        	setFlipH(true);
			break;
		case Right:
			_speed.HorBounce();
        	setActualX(width - getWidth());
        	setFlipH(true);
			break;
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
			Bitmap temp;
			temp = Bitmap.createBitmap(_bitmap, 0, 0, _bitmap.getWidth(), _bitmap.getHeight(), flipMatrix, false);
			
			_bitmap = temp;
		}
	}
	

}
