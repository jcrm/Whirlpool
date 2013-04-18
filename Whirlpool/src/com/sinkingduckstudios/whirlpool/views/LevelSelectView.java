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

public class LevelSelectView extends View{
	private Bitmap background;
	private Bitmap mFullStar;
	private Bitmap mEmptyStar;
	private int theHeight,theWidth, theScale;
	private Rect scale;
	private int stars[] = new int[6];
	
	public LevelSelectView(Context context) {
		
		super(context);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		opt.inJustDecodeBounds = false;
		background = BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		theWidth=Constants.getScreen().getWidth();
		theHeight=Constants.getScreen().getHeight();
		theScale = theHeight/3;
		if ((theWidth/4)< theScale)
			theScale = theWidth/4;
		
		scale = new Rect(-(theScale/4),-(theScale/4),theScale/4,theScale/4);
		
		mEmptyStar =  BitmapFactory.decodeResource(getResources(), R.drawable.star_empty);
		mFullStar = BitmapFactory.decodeResource(getResources(), R.drawable.star_full);

	}
	
	public void initStars(int strs[]){
		for (int i = 0; i < 6; i++)
			stars[i]=strs[i];
	}
	public LevelSelectView(Context context, AttributeSet attrs) {
		
		super(context, attrs);
		setFocusable(true);
		setMinimumWidth(100);
		setMinimumHeight(100);
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, 500, 500);
		opt.inJustDecodeBounds = false;
		background = BitmapFactory.decodeResource(getResources(), R.drawable.options_background, opt);
		
		theWidth=Constants.getScreen().getWidth();
		theHeight=Constants.getScreen().getHeight();
		theScale = theHeight/3;
		if ((theWidth/4)< theScale)
			theScale = theWidth/4;
		
		scale = new Rect(-(theScale/4),-(theScale/4),theScale/4,theScale/4);
		
		mEmptyStar =  BitmapFactory.decodeResource(getResources(), R.drawable.star_empty);
		mFullStar = BitmapFactory.decodeResource(getResources(), R.drawable.star_full);
		
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onDraw(Canvas canvas){
		Rect rect = new Rect(0,0,Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
		canvas.drawBitmap(background, null, rect, null);
		
		int xstep=theWidth/4; int ystep=theHeight/3;
		
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
	
	public void CleanUp() {
		background.recycle();
		background = null;
		mEmptyStar.recycle();
		mEmptyStar=null;
		mFullStar.recycle();
		mFullStar = null;
		
	}
	
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
