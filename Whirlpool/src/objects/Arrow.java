/*
 * Author: Fraser Tomison
 * Last Updated: 5/2/13
 * Content:
 * The purpose of this widget is to display an arrow from point A to B
 * Arrow indicates direction of whirlpool, may have different size/colour for speeds
 */


package objects;

import logic.Constants;
import logic.CollisionManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Arrow {

	private float mStartX, mStartY, mEndX, mEndY;
	private float mPointer1X, mPointer2X, mPointer1Y, mPointer2Y;
	private boolean mVisible;
	
	public Arrow(float x1, float y1, float x2, float y2){
		init(x1,y1,x2,y2);
	}
	
	public void Reposition(float x1, float y1, float x2, float y2){
		init(x1,y1,x2,y2);
	}
	
	public void draw(Canvas canvas) {
		if (mVisible){
			Paint paint = new Paint();
			paint.setStyle(Style.FILL);
	        //background
	        paint.setColor(Color.RED);
	        paint.setStrokeWidth(4 / Constants.getRes().getDisplayMetrics().density);
			canvas.save();//save canvas state
				canvas.drawLine(mStartX, mStartY, mEndX, mEndY, paint);
				canvas.drawLine(mEndX, mEndY, mPointer1X, mPointer1Y, paint);
				canvas.drawLine(mEndX, mEndY, mPointer2X, mPointer2Y, paint);
			canvas.restore();//restore canvas state
		}
	}
	
	public void setVisible(boolean visible){
		mVisible = visible;
	}
	
	public boolean getVisible(){
		return mVisible;
	}
	
	private void init(float x1, float y1, float x2, float y2){
		mStartX = x1;
		mStartY	= y1;
		mEndX	= x2;
		mEndY	= y2;
		
		float dist = (float)Math.sqrt(Math.pow(mStartX-mEndX, 2)+(Math.pow(mStartY-mEndY, 2)));
		float angle = CollisionManager.calcAngle(mStartX, mStartY,mEndX, mEndY) - 90;
		
		mPointer1X = mEndX + (float)Math.sin((angle+20)*Math.PI/180)*(dist/5);
		mPointer1Y = mEndY - (float)Math.cos((angle+20)*Math.PI/180)*(dist/5);
		
		mPointer2X = mEndX + (float)Math.sin((angle-20)*Math.PI/180)*(dist/5);
		mPointer2Y = mEndY - (float)Math.cos((angle-20)*Math.PI/180)*(dist/5);
	}
}
