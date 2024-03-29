/*
 * Author: Fraser Tomison
 * Last Updated: 5/2/13
 * Content:
 * The purpose of this widget is to display an arrow from point A to B
 * Arrow indicates direction of whirlpool, may have different size/colour for speeds
 */

package com.sinkingduckstudios.whirlpool.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.sinkingduckstudios.whirlpool.logic.Animate;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;

/**
 * The Class Arrow.
 */
public class Arrow {

	/** The start and end points of the arrow. */
	private float mStartX, mStartY, mEndX, mEndY;
	
	/** The visibility of the arrow. */
	private boolean mVisible;
	
	/** The vector of the arrow */
	private float mAngle,mDist;
	
	/** The arrow bitmap. */
	private Bitmap mBitmap;
	
	/** The animate class for this sprite sheet. */
	private Animate mAnimate;
	
	/**Constructor for Arrow class, takes the starting position
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Arrow(float x1, float y1, float x2, float y2){
		mBitmap = SpriteManager.getArrow();
		mAnimate = new Animate(13, 1, 13, mBitmap.getWidth(), mBitmap.getHeight());
		mAnimate.setDelay(1);
		mDist = 0.0f;
		init(x1,y1,x2,y2);
	}
	
	/**
	 * Call to reposition the arrow
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void reposition(float x1, float y1, float x2, float y2){
		init(x1,y1,x2,y2);
	}
	
	/**
	 * Draws the arrow to specified canvas
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		if (mVisible){
			//Paint paint = new Paint();
			//paint.setStyle(Style.FILL);
	        ////background
	        //paint.setColor(Color.RED);
	        //paint.setStrokeWidth(4 / Constants.getRes().getDisplayMetrics().density);
			//canvas.save();//save canvas state
				//canvas.drawLine(mStartX, mStartY, mEndX, mEndY, paint);
				//canvas.drawLine(mEndX, mEndY, mPointer1X, mPointer1Y, paint);
				//canvas.drawLine(mEndX, mEndY, mPointer2X, mPointer2Y, paint);
			//canvas.restore();//restore canvas state
			
			if (!mAnimate.getFinished())
				mAnimate.animateFrame();
			
			
			canvas.save();
				Rect rect = new Rect(0, -(int)(mDist/2), (int)mDist, (int)(mDist/2));
				canvas.translate(mStartX, mStartY);
				canvas.rotate(mAngle);
				canvas.drawBitmap(mBitmap, mAnimate.getPortion(), rect,  null);
			canvas.restore();
		}
	}
	
	/**
	 * Gets the length of the arrow, in a form we can assign to the ducks speed
	 *
	 * @return the speed we should set the duck
	 */
	public float getDist(){
	    return mDist/100f;
	}
	
	/**
	 * Sets the visibility of the arrow
	 *
	 * @param visible true or false
	 */
	public void setVisible(boolean visible){
		mVisible = visible;
	}
	
	/**
	 * Gets the visibility
	 *
	 * @return the visibility
	 */
	public boolean getVisible(){
		return mVisible;
	}
	
	/**
	 * Initialise the arrow with starting coordinates
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private void init(float x1, float y1, float x2, float y2){
		float newDist;
		mStartX = x1;
		mStartY	= y1;
		mEndX	= x2;
		mEndY	= y2;
		
		newDist = (float)Math.sqrt(Math.pow(mStartX-mEndX, 2)+(Math.pow(mStartY-mEndY, 2)));
		mAngle = CollisionManager.calcAngle(mStartX, mStartY,mEndX, mEndY);
		
		if (newDist<(mDist*0.8f))
			mAnimate.setFinished(false);
		
		mDist = newDist;
		//mPointer1X = mEndX + (float)Math.sin((angle+20)*Math.PI/180)*(dist/5);
		//mPointer1Y = mEndY - (float)Math.cos((angle+20)*Math.PI/180)*(dist/5);
		//mPointer2X = mEndX + (float)Math.sin((angle-20)*Math.PI/180)*(dist/5);
		//mPointer2Y = mEndY - (float)Math.cos((angle-20)*Math.PI/180)*(dist/5);
	}
}
