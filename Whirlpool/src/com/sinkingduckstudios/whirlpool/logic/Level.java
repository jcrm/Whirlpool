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
import android.graphics.Rect;

import com.sinkingduckstudios.whirlpool.environment.Finish;
import com.sinkingduckstudios.whirlpool.environment.GraphicEnvironment;
import com.sinkingduckstudios.whirlpool.environment.GraphicEnvironment.envtype;
import com.sinkingduckstudios.whirlpool.manager.CollisionManager;
import com.sinkingduckstudios.whirlpool.manager.SpriteManager;
import com.sinkingduckstudios.whirlpool.objects.Boat;
import com.sinkingduckstudios.whirlpool.objects.Collectable;
import com.sinkingduckstudios.whirlpool.objects.Diver;
import com.sinkingduckstudios.whirlpool.objects.Duck;
import com.sinkingduckstudios.whirlpool.objects.Frog;
import com.sinkingduckstudios.whirlpool.objects.GraphicObject;
import com.sinkingduckstudios.whirlpool.objects.GraphicObject.objtype;
import com.sinkingduckstudios.whirlpool.objects.Shark;
import com.sinkingduckstudios.whirlpool.objects.Shark.SharkType;
import com.sinkingduckstudios.whirlpool.objects.Torpedo;
import com.sinkingduckstudios.whirlpool.objects.Whirlpool;

public class Level {

	private final WPools mWPoolModel = new WPools();
	private ArrayList<GraphicObject> mGraphics = new ArrayList<GraphicObject>();
	private ArrayList<GraphicEnvironment> mEnvironments = new ArrayList<GraphicEnvironment>();
	private int mLevelWidth = 0;
	private int mLevelHeight = 0;
	private float mScrollBy = 0;
	private Bitmap mBackgroundImage;
	private Bitmap mLeftBorderImage;
	private Bitmap mRightBorderImage;
	private Bitmap mTopBorderImage;
	private static Object mScreenLock;
	private Rect mRect = new Rect();
	private GraphicObject mFollowThis;//holds which object the next collectable should follow
	public Level(){
	}
	public void init(){
		mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
		mLevelHeight = (int) (500/Constants.getScreen().getRatio());
		mBackgroundImage = SpriteManager.getBackground();
		mLeftBorderImage = SpriteManager.getLeftBorder();
		mRightBorderImage = SpriteManager.getRightBorder();
		mTopBorderImage = SpriteManager.getTopBorder();
		mWPoolModel.addWPool(125, 235, 10, -1, 1);
		mFollowThis = new Duck(40, 235);
		mGraphics.add(mFollowThis);
		Constants.setPlayer((Duck)mGraphics.get(0));
		mGraphics.add(new Diver(100, 350, 0, 0, 400, 1000, 400));
		mGraphics.add(new Diver(1000, 50, 90, 0, 0, 0, 0));
		mGraphics.add(new Diver(1600, 50, 90, 0, 0, 0, 0));
		mGraphics.add(new Diver(2100, 200, 90, 0, 0, 0, 0));
		mGraphics.add(new Diver(2600, 100, 90, 0, 0, 0, 0));
		mGraphics.add(new Boat(1200,207));//207=250-(96/2) --> 96 is height
		mGraphics.add(new Frog(600, 250, 140));
		mGraphics.add(new Frog(1200, 250, 140));		
		mGraphics.add(new Shark(600,300));
		Finish end = new Finish();
    	end.setCentreX(2900);
    	end.setCentreY(235);
    	end.setWAngle(-1);
    	end.setClockwise(1);
		mEnvironments.add(end);
		mGraphics.add(new Collectable(500,50));
		mGraphics.add(new Collectable(1000,400));
		mGraphics.add(new Collectable(2000,190));
		Constants.getPanel().setOnTouchListener(new TrackingTouchListener(mWPoolModel));
		mScreenLock=Constants.getLock();
	}//

	public int update(){
		updateList();
		//synchronized(screenLock){//synchronize whole thing, risk of null pointer large. 
		//could maybe optimise later TODO
		for(int i = 0; i < mWPoolModel.getWpools().size(); i++){
			mWPoolModel.getWpools().get(i).frame();
			if(mWPoolModel.getWpools().get(i).getFinished()){
				mWPoolModel.getWpools().remove(i);
				i--;
			}else
				mWPoolModel.getWpools().get(i).collisionDone=true;
		}
		for(GraphicEnvironment enviro: mEnvironments){
			if(enviro.getId() == envtype.tFinish){
				int count =((Finish) enviro).getEnd(); 
				if(count==1){
					Constants.getSoundManager().playPoints();
					return 1;
				}else if(count == 2){
					return 2;
				}
			}
			enviro.frame();
		}
		//}
		synchronized(mScreenLock){
			duckOnScreen();
		}
		return 0;
		//scroll();
	}
	private void updateList(){
		//create a temporary list to store objects that need to be added
		ArrayList<GraphicObject> objectToBeAdded = new ArrayList<GraphicObject>();
		
		for(Iterator<GraphicObject> mainIterator = mGraphics.listIterator(); mainIterator.hasNext();){
			GraphicObject graphic = mainIterator.next();
			
			boolean isColliding = false;
			for(Whirlpool whirl : mWPoolModel.getWpools()){
				if(whirl.checkCollision(graphic))
					isColliding=true;
			}
			if(!isColliding){
				graphic.setPulledBy(null);
				graphic.setPulledState(Constants.STATE_FREE);
			}
			
			for(GraphicEnvironment enviro: mEnvironments){
				if(enviro.getId()==envtype.tFinish && graphic.getId() == objtype.tDuck){
					if(((Finish) enviro).checkCollision(graphic)){
						((Duck) graphic).setFinished(true);
					}
				}
			}
			if(graphic.getId()==objtype.tDuck){
				duckMovement(graphic);
			}else if(graphic.getId()==objtype.tBoat){
				checkBoatTorpedoCollision(graphic);
				if(((Boat) graphic).getNewTorpedo()){
					objectToBeAdded.add(new Torpedo((int)(graphic.getCentreX()*Constants.getScreen().getRatio()),(int)(graphic.getBottomRightY()*Constants.getScreen().getRatio()),0));
				}
				graphic.frame();	//Do everything this object does every frame, like move
			}else if(graphic.getId()==objtype.tTorpedo){
				if(((Torpedo) graphic).getIsReadyToDestroy()){
					Constants.getSoundManager().playExplosion();
					mainIterator.remove();
				}else if(((Torpedo) graphic).updateDirection()){
					((Torpedo) graphic).setDuckPosition(Constants.getPlayer().getCentreX(),Constants.getPlayer().getCentreY());
				}else{
					((Torpedo) graphic).checkBeep();
					graphic.frame();	//Do everything this object does every frame, like move
				}
			}else if(graphic.getId()==objtype.tShark){
				sharkMovement(graphic);
			}else if(graphic.getId()==objtype.tCollectable){
				boolean collided = ((Collectable)graphic).getCollided();
				graphic.frame();
				if (collided!=((Collectable)graphic).getCollided()){
					((Collectable)graphic).setFollowing(mFollowThis);
					mFollowThis=graphic;
				}
			}else{
				graphic.frame();	//Do everything this object does every frame, like move
			}
		}
		mGraphics.addAll(objectToBeAdded);
	}
	public void checkBoatTorpedoCollision(GraphicObject graphic){
		for(Iterator<GraphicObject> collisionIterator = mGraphics.listIterator(); collisionIterator.hasNext();){
			GraphicObject graphic2 = collisionIterator.next();
			if(graphic2.getId()==objtype.tTorpedo){
				if(CollisionManager.circleCollision(graphic.getCollision(), graphic2.getCollision())){
					if(((Torpedo) graphic2).getHitBoat()==true){
					((Boat) graphic).setBroken(true);
						((Torpedo) graphic2).setIsReadyToDestroy(true);
					}
				}
			}
		}
	}
	public void onDraw(Canvas canvas){
		int width = Constants.getScreen().getWidth();
		int num = (int) Math.ceil((double)mLevelWidth/Constants.getScreen().getWidth());
		//used to make bath tub look right, need to check on other devices
		int bottom =Constants.getScreen().getHeight();

		//Log.e("onDraw", String.valueOf(levelWidth) + "/" + String.valueOf(Constants.getScreen().getWidth()) + "=" + String.valueOf(num));
		for(int a = 0; a < (num); a++){
			mRect.set((int) ((width*a)-mScrollBy), 0, (int)((width*(a+1)) - mScrollBy), Constants.getScreen().getHeight());
			canvas.drawBitmap(mBackgroundImage, null, mRect,  null);
		}

		canvas.translate(-mScrollBy, 0.0f);
		canvas.save();
			
			mRect.set(mLeftBorderImage.getWidth(),0,mLevelWidth-mRightBorderImage.getWidth(),bottom);
			canvas.drawBitmap(mTopBorderImage, null, mRect,  null);

			mRect.set(0, 0, mLeftBorderImage.getWidth(), bottom);
			canvas.drawBitmap(mLeftBorderImage, null, mRect,  null);
			canvas.translate(mLevelWidth-mRightBorderImage.getWidth(), 0);
	
			mRect.set(0, 0, mRightBorderImage.getWidth(), bottom);
			canvas.drawBitmap(mRightBorderImage, null, mRect,  null);

		canvas.restore();

		for (Whirlpool whirlpool : mWPoolModel.getWpools()) {
			whirlpool.draw(canvas);
		}
		
		for(GraphicEnvironment enviro : mEnvironments){
			enviro.draw(canvas);
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
		
		mScrollBy = Constants.getPlayer().getCentreX()- Constants.getScreen().getWidth()/2;
		if(mScrollBy < 0){
			mScrollBy = 0;
		}
		if(mScrollBy + Constants.getScreen().getWidth() > mLevelWidth){
			mScrollBy = mLevelWidth - Constants.getScreen().getWidth();
		}
	}
	public void duckMovement(GraphicObject graphic){
		graphic.frame();	//Do everything this object does every frame, like move
		if(((Duck) graphic).getFinished() == false){
			for(Iterator<GraphicObject> collisionIterator = mGraphics.listIterator(); collisionIterator.hasNext();){
				GraphicObject graphic2 = collisionIterator.next();
				boolean collision = false;
				if(graphic2.getId()==objtype.tBoat){
					collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),((Boat) graphic2).getBoatRadius());
					if(collision){
						((Boat) graphic2).changeAnimation();	
					}
				}else if(graphic2.getId()==objtype.tTorpedo){
					if(((Torpedo) graphic2).getIsReadyToDestroy()==false){
						collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision());
						if(collision){
							((Torpedo) graphic2).setIsReadyToDestroy(true);
						}
					}
				}else if(graphic2.getId()==objtype.tShark){
					if(((Shark) graphic2).getSharkState() == SharkType.tAsleep){
						collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),((Shark) graphic2).getSharkRadius());
						if(collision && ((Shark) graphic2).checkTime()){
							((Shark) graphic2).setSharkState(SharkType.tFollow);
						}
					}else if(((Shark) graphic2).getSharkState() == SharkType.tFollow){
						collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision());
						if(collision){							
							((Shark) graphic2).setSharkState(SharkType.tAttack);
							((Shark) graphic2).moveToDrop();
							((Duck) graphic).setSharkAttack(true);
							graphic.setSpeed(graphic2.getSpeed().getSpeed());
							graphic.setAngle(graphic2.getSpeed().getAngle());
							graphic.setCentreX((int)(graphic2.getCentreX()*Constants.getScreen().getRatio()));
							graphic.setCentreY((int)(graphic2.getCentreY()*Constants.getScreen().getRatio()));
						}else if(((Duck) graphic).getInvincibility() == true){
							collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),((Shark) graphic2).getSharkRadius());
							if(collision){
								((Shark) graphic2).setSharkState(SharkType.tWait);
							}
						}
					}
				}else{
					((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision());
				}
			}
		}
	}
	private void sharkMovement(GraphicObject graphic){
		if(((Shark) graphic).updateDirection()){
			if(((Shark) graphic).getSharkState() == SharkType.tFollow){
				((Shark) graphic).setDuckPosition(Constants.getPlayer().getCentreX(),Constants.getPlayer().getCentreY());
			}
		}
		if(((Shark) graphic).getSharkState() == SharkType.tAttack){
			((Shark) graphic).moveToDrop();
		}
		if(((Shark) graphic).getSharkState() == SharkType.tAttack){
			Constants.getPlayer().setCentre((int)(graphic.getCentreX()*Constants.getScreen().getRatio()), (int)(graphic.getCentreY()*Constants.getScreen().getRatio()));
			if(((Shark) graphic).getMovedToDrop()){
				Constants.getPlayer().setSharkAttack(false);
				((Shark) graphic).setSharkState(SharkType.tRetreat);
			}
		}
		if(((Shark) graphic).getSharkState() == SharkType.tRetreat){
			((Shark) graphic).returnToStart();						
			((Shark) graphic).checkAtStart();
		}
		if(((Shark) graphic).getSharkState() == SharkType.tWait){
			if(Constants.getPlayer().getInvincibility() == false){
				((Shark) graphic).setSharkState(SharkType.tFollow);
			}
		}
		graphic.frame();
	}
}
