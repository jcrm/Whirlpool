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
 * The Class MenuView.
 */
public class MenuView extends View{
	
	/** The background image. */
	private Bitmap background;
	/**
	 * Instantiates a new menu view.
	 *
	 * @param context
	 */
	public MenuView(Context context) {
		super(context);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		scaleBitmap();
	}
	/**
	 * Instantiates a new menu view.
	 *
	 * @param context
	 * @param attrs
	 */
	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		scaleBitmap();
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
		
	}
	/**
	 * Clean up.
	 */
	public void CleanUp(){
		background.recycle();
		background = null;
	}
	/**
	 * Scale bitmap.
	 */
	private void scaleBitmap(){
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.mainmenu_background, opt);
		opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, 500, 500);
		opt.inJustDecodeBounds = false;
		background = BitmapFactory.decodeResource(getResources(), R.drawable.mainmenu_background, opt);
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
