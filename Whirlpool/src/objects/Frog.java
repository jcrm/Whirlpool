package objects;

import logic.Animate;
import logic.Imports;
import logic.Screen.ScreenSide;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Frog extends GraphicObject{
	private float mFrogCentreX, mFrogCentreY, mFrogAngle, mFrogRadius;

	public Frog(){
		mId = objtype.tFrog;
		init();
	}
	@Override
	public void draw(Canvas c) {
		c.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			c.translate(getX(), getY());
			c.rotate((float) (-mFrogAngle*180/Math.PI));
			c.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		c.restore();
	}

	@Override
	public void init() {
		mBitmap = Imports.getFrog();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());

		mWidth = mBitmap.getWidth()/mId.tFrames;
		mHeight = mBitmap.getHeight();

		setX(200);
		setY(170);

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		mRadius =  (int) Math.sqrt(((float)mWidth*mWidth) + ((float)mHeight*mHeight));

		setFrogCentreX(getX());
		setFrogCentreY(getY());
		setFrogRadius(80);
	}

	@Override
	public boolean move() {
		if(mSpeed.getMove()){
			setX((float)(mFrogCentreX + Math.sin(mFrogAngle)*mFrogRadius));
			setY((float)(mFrogCentreY + Math.cos(mFrogAngle)*mFrogRadius));
			mFrogAngle-=mSpeed.getSpeed()/150;
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

	public float getFrogAngle() {
		return mFrogAngle;
	}
	public float getFrogRadius() {
		return mFrogRadius;
	}
	public void setFrogCentreX(float frogCentreX) {
		mFrogCentreX = frogCentreX;
	}
	public void setFrogCentreY(float frogCentreY) {
		mFrogCentreY = frogCentreY;
	}
	public void setFrogAngle(float frogAngle) {
		mFrogAngle = frogAngle;
	}
	public void setFrogRadius(float _frogRadius) {
		mFrogRadius = _frogRadius;
	}
	public float getFrogCentreX() {
		return mFrogCentreX;
	}
	public float getFrogCentreY() {
		return mFrogCentreY;
	}
}
