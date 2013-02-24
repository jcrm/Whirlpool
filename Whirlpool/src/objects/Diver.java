package objects;

import java.util.Random;

import logic.Animate;
import logic.Constants;
import logic.Imports;
import logic.Screen.ScreenSide;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Diver extends GraphicObject{
	private boolean mFlipped;

	public Diver(){
		mId = objtype.tDiver;
		init();
	}
	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getX(), getY());
			canvas.rotate(mSpeed.getAngle()+180);
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
	}
	@Override
	public void init() {
		mBitmap = Imports.getDiver();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());
		
		mWidth = mBitmap.getWidth()/mId.tFrames;
		mHeight = mBitmap.getHeight();

		setX((float) (new Random().nextInt(Constants.getLevel().getLevelWidth())));
    	setY((float) (new Random().nextInt(Constants.getLevel().getLevelHeight())));
		
		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		mRadius =  (int) Math.sqrt(((float)mWidth*mWidth) + ((float)mHeight*mHeight));
	}
	@Override
	public boolean move() {
		if(mSpeed.getMove()){
			shiftX((float) (mSpeed.getSpeed()*Math.cos(mSpeed.getAngleRad())));
			shiftY((float) (mSpeed.getSpeed()*Math.sin(mSpeed.getAngleRad())));
			return true;
		}
		return false;
	}
	@Override
	public void borderCollision(ScreenSide side, float width, float height) {
		switch(side){
		case Top:
			setActualY(-getActualY());
			mSpeed.shiftAngle(180);
			break;
		case Bottom:
			setActualY(height - getHeight());        	
			mSpeed.shiftAngle(180);
			break;
		case Left:
			setActualX(-getActualX());
			mSpeed.shiftAngle(180);
			break;
		case Right:
			setActualX(width - getWidth());
			mSpeed.shiftAngle(180);
			break;
		case TopLeft:
			setActualX(-getActualX());
			setActualY(-getActualY());
			mSpeed.shiftAngle(180);
			break;
		case TopRight:
			setActualX(width - getWidth());
			setActualY(-getActualY());
			mSpeed.shiftAngle(180);
			break;
		case BottomLeft:
			setActualX(-getActualX());
			setActualY(height - getHeight());        	
			mSpeed.shiftAngle(180);
			break;
		case BottomRight:
			setActualX(width - getWidth());
			setActualY(height - getHeight());        	
			mSpeed.shiftAngle(180);
			break;
		}
	}
	public void frame(){
		// Move Objects
		if(move()){
			border();
			checkFlip();
		}
		mAnimate.animateFrame();
	}
	public void checkFlip(){
		if(!mFlipped){
			if(mSpeed.getAngle()>270 || mSpeed.getAngle()<90){
				mFlipped = true;
				mBitmap = Imports.getDiverFlipped();
				System.gc();
			}
		}else if(mFlipped){
			if(mSpeed.getAngle()<=270 && mSpeed.getAngle()>=90){
				mFlipped = false;
				mBitmap = Imports.getDiver();
				System.gc();
			}
		}
	}
}