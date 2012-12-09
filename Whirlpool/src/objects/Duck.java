package objects;

import java.util.Random;

import states.MainActivity;
import logic.Func;
import logic.Panel;
import logic.Screen.ScreenSide;
import logic.Animate;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.FloatMath;

public class Duck extends GraphicObject{
	public enum coltype{
		cDefault, cShark, cDiver, cFrog;
	}
	public coltype cID = coltype.cDefault;
	private int colCount = -1;
	Animate animate;
	
	public Duck(){
		_id = objtype.tDuck;
		init();
		animate = new Animate(_id.frames, _id.aWidth, _id.aHeight);
	}

	@Override
	public void draw(Canvas c) {
		c.save();
		
		Rect rect = new Rect((int)getActualX(), (int)getActualY(), (int)getActualX() + _width, (int)getActualY() + _height);
		c.drawBitmap(getGraphic(), animate.getPortion(), rect,  null);
		
		c.restore();
	}

	@Override
	public void init() {
		_bitmap = BitmapFactory.decodeResource(Panel.sRes, _id.bitmap);
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
	
	public void frame(){
		// Move Objects
		colMovement();
        if(move()){
        	border(MainActivity.getCurrentLevel().getLevelWidth(), Panel.sScreen.getHeight());
        }
        animate.animateFrame();
	}
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
					colDiverFrog(otherGraphic.getX(),otherGraphic.getWidth());
					cID = coltype.cDiver;
				}
				break;
			case tFrog: 
				if(Func.boxCollision(this, otherGraphic)){
					colDiverFrog(otherGraphic.getX(),otherGraphic.getWidth());
					cID = coltype.cFrog;
				}
				break;
			default: break;
			}
		}
	}
	private void colMovement(){
		if(cID != coltype.cDefault && colCount >= 0){
			if(colCount == 10){
				getSpeed().setSpeed(0);
				getSpeed().setAngle(0);
			}else if(colCount == 20){
				getSpeed().setSpeed(4);				
				getSpeed().setAngle(0);
				cID = coltype.cDefault;
				colCount = -1;
			}
			colCount++;
		}
	}
	private void colDiverFrog(float x, float w){
		getSpeed().setSpeed(5);
		getSpeed().setAngle(new Random().nextInt(180)+90);
		colCount = 0;
	}
	private void colShark(float s, float a){
		getSpeed().setSpeed(s);
    	setAngle(a);
	}
	
}
