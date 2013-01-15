package objects;

import java.util.Random;

import states.MainActivity;
import logic.Func;
import logic.ImageImports;
import logic.Panel;
import logic.Screen.ScreenSide;
import logic.Animate;
import example.whirlpool.R;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;

public class Duck extends GraphicObject{
	//enum for collision checking
	private enum coltype{
		cDefault, cShark, cDiver, cFrog, cWhirl;
	}
	//collision variables 
	public coltype cID = coltype.cDefault;
	private int colCount = -1;
	Animate animate;
	
	public Duck(){
		_id = objtype.tDuck;
		init();
		
	}

	@Override
	public void draw(Canvas c) {
		c.save();
		
		Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
		
		c.translate(getX(), getY());
		if(_speed.getAngle() > 90 && _speed.getAngle() < 270){
			c.scale(-1, 1);
		}
		c.drawBitmap(getGraphic(), animate.getPortion(), rect,  null);
		
		c.restore();
	}

	@Override
	public void init() {
		_bitmap = ImageImports.getDuck();
		animate = new Animate(_id.frames, _bitmap.getWidth()/_id.frames, _bitmap.getHeight());
		setX(0.0f);
		setY(0.0f);
    	//setY((int) Panel.sScreen.getCentreY() - getGraphic().getHeight() / 2);
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
	
	public void changeCollisionType(boolean a){
		if(a){
			cID = coltype.cWhirl;
		}
		else if (cID == coltype.cWhirl){
			cID = coltype.cDefault;
		}
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
		colMovement();
        if(move()){
        	border();
        }
        animate.animateFrame();
	}
	//collision checking
	public void checkObjCollision(GraphicObject otherGraphic){
		if(cID == coltype.cDefault){
			switch(otherGraphic.getId()){
			case tShark: 
				if(Func.boxCollision(this, otherGraphic)){
					colShark(otherGraphic.getSpeed().getSpeed(), otherGraphic.getSpeed().getAngle());
	 				cID = coltype.cShark;
				}
				break;
			case tDiver:
				if(Func.boxCollision(this, otherGraphic)){
					colDiverFrog();
					cID = coltype.cDiver;
				}
				break;
			case tFrog: 
				if(Func.boxCollision(this, otherGraphic)){
					colDiverFrog();
					cID = coltype.cFrog;
				}
				break;
			case tWhirl:
				cID = coltype.cWhirl;
				break;
			default: break;
			}
		}
	}
	//collision movement
	private void colMovement(){
		if((cID != coltype.cDefault && cID != coltype.cWhirl) && colCount >= 0){
			if(colCount == 30){
				getSpeed().setSpeed(0);
				getSpeed().setAngle(0);
			}else if(colCount == 60){
				getSpeed().setSpeed(4);				
				getSpeed().setAngle(0);
				cID = coltype.cDefault;
				colCount = -1;
			}
			colCount++;
		}
	}
	private void colDiverFrog(){
		getSpeed().setSpeed(5);
		getSpeed().setAngle(new Random().nextInt(90)+135);
		colCount = 0;
	}
	private void colShark(float s, float a){
		getSpeed().setSpeed(s);
    	setAngle(a);
    	colCount = 0;    	
	}
	
}
