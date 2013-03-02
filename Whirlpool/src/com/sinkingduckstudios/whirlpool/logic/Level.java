/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

import java.util.ArrayList;
import java.util.Iterator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.objects.Boat;
import com.sinkingduckstudios.whirlpool.objects.Diver;
import com.sinkingduckstudios.whirlpool.objects.Duck;
import com.sinkingduckstudios.whirlpool.objects.Frog;
import com.sinkingduckstudios.whirlpool.objects.GraphicObject;
import com.sinkingduckstudios.whirlpool.objects.GraphicObject.objtype;
import com.sinkingduckstudios.whirlpool.objects.Torpedo;
import com.sinkingduckstudios.whirlpool.objects.Whirlpool;

public class Level {

	private final WPools mWPoolModel = new WPools();
	private ArrayList<GraphicObject> mGraphics = new ArrayList<GraphicObject>();
	private int mLevelWidth = 0;
	private int mLevelHeight = 0;
	private float mScrollBy = 0;
	private Bitmap mBackgroundImage;
	private static Object mScreenLock;
	private Paint mPaint = new Paint();
	private Rect mRect = new Rect();

	public Level(){
	}
	public void init(){
		mLevelWidth = 2000;
		mLevelHeight = Constants.getHeight();
		mBackgroundImage = Imports.getBackground();
		mGraphics.add(new Duck());
		Constants.setPlayer((Duck)mGraphics.get(0));
		mGraphics.add(new Frog());
		mGraphics.add(new Diver());
		mGraphics.add(new Boat());
		//_graphics.add(new Shark());

		Constants.getPanel().setOnTouchListener(new TrackingTouchListener(mWPoolModel));
		mScreenLock=Constants.getLock();
	}

	public void update(){
		
		updateList();
		//synchronized(screenLock){//synchronize whole thing, risk of null pointer large. 
		//could maybe optimise later TODO
		for(int i = 0; i < mWPoolModel.getWpools().size(); i++){
			mWPoolModel.getWpools().get(i).frame();
			if(mWPoolModel.getWpools().get(i).getFinished()){
				mWPoolModel.getWpools().remove(i);
				i--;
			}
		}
		//}
		synchronized(mScreenLock){
			duckOnScreen();
		}
		//scroll();
	}
	private void updateList(){
		//create a temporary list to store objects that need to be added
		ArrayList<GraphicObject> objectToBeAdded = new ArrayList<GraphicObject>();
		
		for(Iterator<GraphicObject> mainIterator = mGraphics.listIterator(); mainIterator.hasNext();){
			GraphicObject graphic = mainIterator.next();
			graphic.setPull(false);
			for(Whirlpool whirl : mWPoolModel.getWpools()){
				whirl.checkCollision(graphic);
			}
			if(graphic.getId()==objtype.tDuck){
				graphic.frame();	//Do everything this object does every frame, like move
				for(Iterator<GraphicObject> collisionIterator = mGraphics.listIterator(); collisionIterator.hasNext();){
					GraphicObject graphic2 = collisionIterator.next();
					int tempRadius = 0;
					if(graphic2.getId()==objtype.tBoat){
						tempRadius = ((Boat) graphic2).getBoatRadius();				
					}
					boolean collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),tempRadius);
					if(collision){
						switch(graphic2.getId()){
						case tBoat:
							//cant edit an array will being used have to use an iterator google this when fixing it
							if(((Boat) graphic2).getCreateNewTorpedo()){
								objectToBeAdded.add(new Torpedo(graphic2.getCentreX(),graphic2.getBottomRightY()));
								((Boat) graphic2).setCreateNewTorpedo(false);
							}
							break;
						case tShark:
							break;
						case tTorpedo:
							((Torpedo) graphic2).setIsReadyToDestroy(true);
							break;
						default:
							break;
						}
					}	
				}
			}else if(graphic.getId()==objtype.tTorpedo){
				if(((Torpedo) graphic).getIsReadyToDestroy()){
					mainIterator.remove();
				}else{
					graphic.frame();	//Do everything this object does every frame, like move
				}
			}else{
				graphic.frame();	//Do everything this object does every frame, like move
			}
		}
		mGraphics.addAll(objectToBeAdded);
	}
	public void onDraw(Canvas canvas){
		int width = Constants.getScreen().getWidth();
		int num = (int) Math.ceil((double)mLevelWidth/Constants.getScreen().getWidth());

		mPaint.setColor(Color.RED);
		mPaint.setStyle(Paint.Style.FILL);
		//Log.e("onDraw", String.valueOf(levelWidth) + "/" + String.valueOf(Constants.getScreen().getWidth()) + "=" + String.valueOf(num));
		for(int a = 0; a < (num); a++){
			mRect.set((int) ((width*a)-mScrollBy), 0, (int)((width*(a+1)) - mScrollBy), Constants.getScreen().getHeight());
			canvas.drawBitmap(mBackgroundImage, null, mRect,  null);
		}

		canvas.translate(-mScrollBy, 0.0f);

		canvas.save();

			mRect.set(0, 0, 5, canvas.getHeight());
			canvas.drawRect(mRect, mPaint);
	
			canvas.translate(mLevelWidth-5, 0);
	
			mRect.set(0, 0, 5, canvas.getHeight());
			canvas.drawRect(mRect, mPaint);

		canvas.restore();


		for (Whirlpool whirlpool : mWPoolModel.getWpools()) {
			whirlpool.draw(canvas);
		}
		for (GraphicObject graphic : mGraphics) {
			graphic.draw(canvas);
		}
	}

	public WPools getWPoolModel() {
		return mWPoolModel;
	}
	public int getLevelWidth() {
		return mLevelWidth;
	}
	public int getLevelHeight(){
		return mLevelHeight;
	}
	public void setLevelWidth(int levelWidth) {
		mLevelWidth = levelWidth;
	}
	public void setLevelHeight(int levelHeight){
		mLevelHeight = levelHeight;
	}
	public float getScrollBy() {
		return mScrollBy;
	}
	public void setScrollBy(float scrollBy) {
		mScrollBy = scrollBy;
	}
	public void shiftScrollBy(float delta) {
	}
	public void duckOnScreen(){
		while(Constants.getPlayer().getCentreX() >= (Constants.getScreen().getWidth()/2)+mScrollBy){
			mScrollBy++;
		}
		while(Constants.getPlayer().getCentreX() < (Constants.getScreen().getWidth()/2)+mScrollBy){
			mScrollBy--;
		}
		if(mScrollBy < 0){
			mScrollBy = 0;
		}
		if(mScrollBy + Constants.getScreen().getWidth() > mLevelWidth){
			mScrollBy = mLevelWidth - Constants.getScreen().getWidth();
		}
	}

}