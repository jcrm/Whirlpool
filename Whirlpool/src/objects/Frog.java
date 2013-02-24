package objects;

import logic.Animate;
import logic.Constants;
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
	public void draw(Canvas canvas) {
		canvas.save();
			Rect rect = new Rect(-(getWidth()/2), -(getHeight()/2), getWidth()/2, getHeight()/2);
			canvas.translate(getX(), getY());
			canvas.rotate((float) (-mFrogAngle*180/Math.PI));
			canvas.drawBitmap(getGraphic(), mAnimate.getPortion(), rect,  null);
		canvas.restore();
	}

	@Override
	public void init() {
		mBitmap = Imports.getFrog();
		mAnimate = new Animate(mId.tFrames, mBitmap.getWidth(), mBitmap.getHeight());

		mWidth = mBitmap.getWidth()/mId.tFrames;
		mHeight = mBitmap.getHeight();

		setX(Constants.getLevel().getLevelWidth()/2);
		setY(Constants.getLevel().getLevelHeight()/2);

		mSpeed.setMove(true);
		mSpeed.setAngle(mId.tAngle);
		mSpeed.setSpeed(mId.tSpeed);
		mRadius =  (int) Math.sqrt(((float)mWidth*mWidth) + ((float)mHeight*mHeight));

		setFrogCentreX(getX());
		setFrogCentreY(getY());
		setFrogRadius((Constants.getLevel().getLevelHeight()/2)-70);
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
			mSpeed.verticalBounce();
			setActualY(-getActualY());
			break;
		case Bottom:
			mSpeed.verticalBounce();
			setActualY(height - getHeight());
			break;
		case Left:
			mSpeed.horizontalBounce();
			setActualX(-getActualX());
			break;
		case Right:
			mSpeed.horizontalBounce();
			setActualX(width - getWidth());
			break;
		case BottomLeft:
			mSpeed.verticalBounce();
			setActualY(height - getHeight());
			mSpeed.horizontalBounce();
			setActualX(-getActualX());
			break;
		case BottomRight:
			mSpeed.verticalBounce();
			setActualY(height - getHeight());
			mSpeed.horizontalBounce();
			setActualX(width - getWidth());
			break;
		case TopLeft:
			mSpeed.verticalBounce();
			setActualY(-getActualY());
			mSpeed.horizontalBounce();
			setActualX(-getActualX());
			break;
		case TopRight:
			mSpeed.verticalBounce();
			setActualY(-getActualY());
			mSpeed.horizontalBounce();
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
