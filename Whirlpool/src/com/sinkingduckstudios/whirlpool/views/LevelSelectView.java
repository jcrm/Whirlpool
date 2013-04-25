/*
 * Author:Lewis Shaw, Jake Morey, Fraser Tomison
 * Content:
 * Lewis Shaw: wrote code to display an image on the screen
 * Jake Morey: wrote the code to scale the image
 * Fraser Tomison: added scaling code and stars for each level
 */
package com.sinkingduckstudios.whirlpool.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;

/**
 * The Class LevelSelectView.
 */
public class LevelSelectView extends View{

	/** The background image. */
	private Bitmap background;
	/** The full star image. */
	private Bitmap mFullStar;
	/** The empty star image. */
	private Bitmap mEmptyStar;
	/** The scale values. */
	private int theHeight,theWidth, theScale;
	/** The scale rect. */
	private Rect scale;
	/** The stars. */
	private int stars[] = new int[6];

	/**
	 * Instantiates a new level select view.
	 *
	 * @param context
	 */
	public LevelSelectView(Context context) {
		super(context);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		scaleBitmap();
		theWidth=Constants.getScreen().getWidth();
		theHeight=Constants.getScreen().getHeight();
		theScale = theHeight/3;
		if ((theWidth/4)< theScale)
			theScale = theWidth/4;

		scale = new Rect(-(theScale/4),-(theScale/4),theScale/4,theScale/4);

		mEmptyStar =  BitmapFactory.decodeResource(getResources(), R.drawable.star_empty);
		mFullStar = BitmapFactory.decodeResource(getResources(), R.drawable.star_full);
	}
	/**
	 * Inits the stars.
	 *
	 * @param strs set the stars 
	 */
	public void initStars(int strs[]){
		for (int i = 0; i < 6; i++)
			stars[i]=strs[i];
	}
	/**
	 * Instantiates a new level select view.
	 *
	 * @param context
	 * @param attrs
	 */
	public LevelSelectView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		scaleBitmap();
		theWidth=Constants.getScreen().getWidth();
		theHeight=Constants.getScreen().getHeight();
		theScale = theHeight/3;
		if ((theWidth/4)< theScale)
			theScale = theWidth/4;

		scale = new Rect(-(theScale/4),-(theScale/4),theScale/4,theScale/4);

		mEmptyStar =  BitmapFactory.decodeResource(getResources(), R.drawable.star_empty);
		mFullStar = BitmapFactory.decodeResource(getResources(), R.drawable.star_full);
	}
	/* (non-Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	/* (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas){
		Rect rect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		canvas.drawBitmap(background, null, rect, null);

		int xstep=theWidth/4; int ystep=theHeight/3;
		//draw all the stars
		canvas.translate(xstep+(theWidth*0.01f), ystep);//translate to button
		canvas.save();
		canvas.translate(-(theScale/3),-(theScale/3));
		if(stars[0]>0)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),-(theScale/5));
		if(stars[0]>1)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),(theScale/5));
		if(stars[0]>2)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.restore();

		canvas.save();
		canvas.translate(0, ystep);//translate to button
		canvas.translate(-(theScale/3),-(theScale/3));
		if(stars[3]>0)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),-(theScale/5));
		if(stars[3]>1)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),(theScale/5));
		if(stars[3]>2)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.restore();

		canvas.translate(xstep, 0);

		canvas.save();
		canvas.translate(-(theScale/3),-(theScale/3));
		if(stars[1]>0)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),-(theScale/5));
		if(stars[1]>1)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),(theScale/5));
		if(stars[1]>2)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.restore();

		canvas.save();
		canvas.translate(0, ystep);//translate to button
		canvas.translate(-(theScale/3),-(theScale/3));
		if(stars[4]>0)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),-(theScale/5));
		if(stars[4]>1)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),(theScale/5));
		if(stars[4]>2)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.restore();

		canvas.translate(xstep, 0);

		canvas.save();
		canvas.translate(-(theScale/3),-(theScale/3));
		if(stars[2]>0)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),-(theScale/5));
		if(stars[2]>1)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),(theScale/5));
		if(stars[2]>2)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.restore();

		canvas.save();
		canvas.translate(0, ystep);//translate to button
		canvas.translate(-(theScale/3),-(theScale/3));
		if(stars[5]>0)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),-(theScale/5));
		if(stars[5]>1)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.translate((theScale/3),(theScale/5));
		if(stars[5]>2)canvas.drawBitmap(mFullStar,null,scale,null);
		else canvas.drawBitmap(mEmptyStar,null,scale,null);
		canvas.restore();
	}
	/**
	 * Clean up.
	 */
	public void CleanUp() {
		background.recycle();
		background = null;
		mEmptyStar.recycle();
		mEmptyStar=null;
		mFullStar.recycle();
		mFullStar = null;

	}
	/**
	 * Scale bitmap.
	 */
	private void scaleBitmap(){
		//scales the image to a set size by first taking a peek
		//at the size of the image then scaling it to a set size
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, 500, 500);
		opt.inJustDecodeBounds = false;
		background = BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
	}
	/**
	 * Gets the scale.
	 *
	 * @param oW the original width
	 * @param oH the original height
	 * @param nW the new width
	 * @param nH the new height
	 * @return the scale
	 */
	public int getScale(int oW, int oH, int nW, int nH){
		//work out new width and height
		int scale = 1;
		if(oW>nW || oH>nH){
			if(oW<oH){
				scale=Math.round(oW/nW);
			}else{
				scale=Math.round(oH/nH);
			}
		}
		return scale;
	}
}
