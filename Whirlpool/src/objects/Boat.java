package objects;

import java.util.Random;

import logic.Animate;
import logic.Panel;
import logic.Screen.ScreenSide;

import android.graphics.Canvas;
import android.graphics.Rect;

public class Boat extends GraphicObject{

	public Boat(){
		mId = objtype.tBoat;
		init();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getX(), getY());
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
	}

	@Override
	public void init() {
		//_bitmap = Imports.getBoat();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());

		mWidth = mBitmap.getWidth()/mId.tFrames;
		mHeight = mBitmap.getHeight();

		setX((float) (new Random().nextInt(Panel.sScreen.getWidth())));
		setY((float) (new Random().nextInt(Panel.sScreen.getHeight())));

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
			mSpeed.VerBounce();
			setActualY(-getActualY());
			break;
		case Bottom:
			mSpeed.VerBounce();
			setActualY(height - getHeight());
			break;
		case Left:
			mSpeed.HorBounce();
			setActualX(-getActualX());
			break;
		case Right:
			mSpeed.HorBounce();
			setActualX(width - getWidth());
			break;
		case BottomLeft:
			mSpeed.VerBounce();
			setActualY(height - getHeight());
			mSpeed.HorBounce();
			setActualX(-getActualX());
			break;
		case BottomRight:
			mSpeed.VerBounce();
			setActualY(height - getHeight());
			mSpeed.HorBounce();
			setActualX(width - getWidth());
			break;
		case TopLeft:
			mSpeed.VerBounce();
			setActualY(-getActualY());
			mSpeed.HorBounce();
			setActualX(-getActualX());
			break;
		case TopRight:
			mSpeed.VerBounce();
			setActualY(-getActualY());
			mSpeed.HorBounce();
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
		mAnimate.animateFrame();
	}

}
