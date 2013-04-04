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
	private ArrayList<Torpedo> mTorpedoes = new ArrayList<Torpedo>();
	private ArrayList<GraphicEnvironment> mEnvironments = new ArrayList<GraphicEnvironment>();
	private int mLevelWidth = 0;
	private int mLevelHeight = 0;
	private float mScrollBy = 0;
	
	private Bitmap mBackgroundImage;
	private Bitmap mLeftBorderImage;
	private Bitmap mRightBorderImage;
	private Bitmap mTopBorderImage;
	
	private int DiverCounter;
	private int FrogCounter;
	private int TugBoatCounter;
	private int SharkCounter;
	
	private boolean mPointsPlaying;

	
	private static Object mScreenLock;
	private Rect mRect = new Rect();
	private GraphicObject mFollowThis;//holds which object the next collectable should follow
	
	
	public Level(){
	}
	public void init(){
		mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
		mLevelHeight = (int) (500/Constants.getScreen().getRatio());
		initImages();
		
		levelNumber(1);
		mPointsPlaying = false;
		
		DiverCounter = 0;
		FrogCounter = 0;
		TugBoatCounter = 0;
		SharkCounter = 0;

		Constants.getPanel().setOnTouchListener(new TrackingTouchListener(mWPoolModel));
		mScreenLock=Constants.getLock();
	}
	public void init(int lNumber, boolean replay){
		mLevelHeight = (int) (500/Constants.getScreen().getRatio());
		initImages();
		
		levelNumber(lNumber);
		if(replay){
			replayLevel(lNumber);
		}
		mPointsPlaying = false;
		
		DiverCounter = 0;
		FrogCounter = 0;
		TugBoatCounter = 0;
		SharkCounter = 0;

		Constants.getPanel().setOnTouchListener(new TrackingTouchListener(mWPoolModel));
		mScreenLock=Constants.getLock();
	}
	private void initImages(){
		mBackgroundImage = SpriteManager.getBackground();
		mLeftBorderImage = SpriteManager.getLeftBorder();
		mRightBorderImage = SpriteManager.getRightBorder();
		mTopBorderImage = SpriteManager.getTopBorder();
	}
	private void levelNumber(int lNumber){
		mWPoolModel.addWPool(125, 255, 10, -1, 1);
		mFollowThis = new Duck(40, 250);
		mGraphics.add(mFollowThis);
		Constants.setPlayer((Duck)mGraphics.get(0));
		switch(lNumber){
		case 1:
			mGraphics.add(new Diver(750, 350, 90, 0, 100, 0, 500));
			mGraphics.add(new Diver(500, 50, 90, 0, 0, 0, 235));
			mGraphics.add(new Diver(900, 50, 90, 0, 0, 0, 235));
			mGraphics.add(new Frog(400, 250, 80));
			mGraphics.add(new Frog(1200, 250, 70));
			mLevelWidth = (int) (1500/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(1400, 250, -1, 1));
			break;
		case 2:
			mGraphics.add(new Diver(100, 0, 45, 100, 0, 400, 250));
			mGraphics.add(new Diver(100, 500, 135, 100, 250, 400, 500));
			mGraphics.add(new Diver(600, 500, 135, 0, 0, 0, 0));
			mGraphics.add(new Frog(600, 250, 75));
			mGraphics.add(new Frog(1250, 250, 140));
			mLevelWidth = (int) (1500/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(1250, 250, -1, 1));
			break;
		case 3:
			mGraphics.add(new Diver(250, 0, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(375, 0, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(500, 50, 90, 0, 0, 0, 250));
			mGraphics.add(new Diver(500, 450, 90, 0, 250, 0, 500));
			mGraphics.add(new Diver(500, 250, 0, 0, 0, 0, 0));
			mGraphics.add(new Diver(1000, 250, 135, 1000, 0, 1300, 250));
			mGraphics.add(new Diver(1000, 250, 45, 1000, 250, 1300, 500));
			mGraphics.add(new Diver(1400, 50, 90, 0, 0, 0, 0));
			mLevelWidth = (int) (1500/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(1200, 250, -1, 1));
			break;
		case 4:
			mGraphics.add(new Diver(100, 350, 0, 0, 400, 1000, 400));
			mGraphics.add(new Diver(1000, 50, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(1600, 50, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(2100, 200, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(2600, 100, 90, 0, 0, 0, 0));
			mGraphics.add(new Boat(1200,207));//207=250-(96/2) --> 96 is height
			mGraphics.add(new Frog(600, 250, 140));
			mGraphics.add(new Frog(1200, 250, 140));		
			mGraphics.add(new Shark(600,300));
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 250, -1, 1));
			break;
		case 5: 
			mGraphics.add(new Diver(100, 50, 0, 0, 0, 900, 0));
			mGraphics.add(new Diver(100, 400, 0, 0, 0, 900, 0));
			mGraphics.add(new Diver(1600, 50, 90, 1500, 0, 0, 2000));
			mGraphics.add(new Diver(2100, 200, 45, 0, 0, 0, 0));
			mGraphics.add(new Diver(2600, 100, 135, 0, 0, 0, 0));
			mGraphics.add(new Frog(400, 250, 100));
			mGraphics.add(new Frog(900, 250, 100));	
			mGraphics.add(new Frog(1200, 200, 100));
			mGraphics.add(new Frog(1500, 350, 100));	
			mGraphics.add(new Frog(1700, 200, 100));
			mGraphics.add(new Frog(2350, 200, 100));	
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 250, -1, 1));
			break;
		case 6:
			mGraphics.add(new Diver(800, 50, 90, 0, 0, 0, 300));
			mGraphics.add(new Diver(1000, 450, 90, 0, 50, 0, 500));
			mGraphics.add(new Diver(1500, 250, 45, 1000, 0, 1500, 400));
			mGraphics.add(new Diver(1500, 250, 135, 1500, 0, 2000, 400));
			mGraphics.add(new Diver(2100, 100, 90, 0, 50, 0, 500));
			mGraphics.add(new Frog(2500, 150, 100));
			mGraphics.add(new Frog(2500, 350, 100));
			mGraphics.add(new Boat(600,250));//207=250-(96/2) --> 96 is height
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 235, -1, 1));
			break;
		default: 
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 250, -1, 1));
			break;
		}
	}
	private void replayLevel(int lNumber){
		switch(lNumber){
		case 1:
			mGraphics.add(new Collectable(600,250));
			mGraphics.add(new Collectable(1200,250));
			mGraphics.add(new Collectable(750,450));
			break;
		case 2:
			mGraphics.add(new Collectable(800,250));
			mGraphics.add(new Collectable(250,50));
			mGraphics.add(new Collectable(250,450));
			break;
		case 3:
			mGraphics.add(new Collectable(500,250));
			mGraphics.add(new Collectable(1350,50));
			mGraphics.add(new Collectable(1350,450));
			break;
		case 4:
			mGraphics.add(new Collectable(500,50));
			mGraphics.add(new Collectable(1000,400));
			mGraphics.add(new Collectable(2100,350));
			break;
		case 5: 
			mGraphics.add(new Collectable(500,50));
			mGraphics.add(new Collectable(1500,400));
			mGraphics.add(new Collectable(2000,190));
			break;
		case 6:
			mGraphics.add(new Collectable(600,425));
			mGraphics.add(new Collectable(1500,50));
			mGraphics.add(new Collectable(2500,350));
			break;
		default: break;
		}
	}
	public int update(){
		updateList();
		//synchronized(screenLock){//synchronize whole thing, risk of null pointer large. 
		//could maybe optimise later TODO
		Constants.setDuckDist(9000000);//max dist
		for(Iterator<Torpedo> tIterator = mTorpedoes.listIterator(); tIterator.hasNext();){
			Torpedo torpedo = tIterator.next();
			boolean isColliding = false;
			for(Whirlpool whirl : mWPoolModel.getWpools()){
				if(whirl.checkCollision(torpedo))
					isColliding=true;
			}
			if(!isColliding){
				torpedo.setPulledBy(null);
				torpedo.setPulledState(Constants.STATE_FREE);
			}
					
			if(torpedo.getIsReadyToDestroy()){
				Constants.getSoundManager().playExplosion();
				tIterator.remove();
			}else if(torpedo.updateDirection() && torpedo.getExplosion() == false){
				torpedo.setDuckPosition(Constants.getPlayer().getCentreX(),Constants.getPlayer().getCentreY());
				torpedo.frame();
			}else{
				torpedo.checkBeep();
				torpedo.frame();	//Do everything this object does every frame, like move
			}
			float theDist = torpedo.getDist();
			if (theDist<Constants.getDuckDist())
				Constants.setDuckDist(theDist);	
		}
		float vol = 1 - (Constants.getDuckDist()/9000000);
		Constants.getSoundManager().alterBeepVolume(vol);
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
					return 1;
				}else if(count == 2){
					return 2;
				}
			}
			enviro.frame();
		}
		
		//go through the list of graphics objects
				for(Iterator<GraphicObject> gIterator = mGraphics.listIterator(); gIterator.hasNext();){
					GraphicObject Enemy = gIterator.next();
		  			// if the enemy is on screen...
					if (enemiesOnScreen(Enemy)){
						// find out the enemies type and play the relevant type
						switch (Enemy.getType()){
							//diver
							case 1 : {
								if (Enemy.getIsPlaying() == false){
									Constants.getSoundManager().playDiver();
									Enemy.setIsPlaying(true);
									DiverCounter = 0;
								}
								DiverCounter ++;
								if (DiverCounter >= 500){
									Enemy.setIsPlaying(false);
								}
								break;
							}
							//frog
							case 2 :{
								if (Enemy.getIsPlaying() == false){
									Constants.getSoundManager().playFrog();
									Enemy.setIsPlaying(true);
									FrogCounter = 0;
								}
								
								FrogCounter ++;
								
								if (FrogCounter >= 500){
									Enemy.setIsPlaying(false);
								}
								break;
							}
							//tugbout
							case 3 :{
								if (Enemy.getIsPlaying() == false){
									Constants.getSoundManager().playTugBoat();
									Enemy.setIsPlaying(true);
									TugBoatCounter = 0;
								}
								TugBoatCounter ++;
								if (TugBoatCounter >= 500){
									Enemy.setIsPlaying(false);
								}
								break;
							}
							
							//shark
							case 4 : {
								break;
							}
						}// end switch enemytype
					}
				} //end for graphics objects
				
				

		
		synchronized(mScreenLock){
			duckOnScreen();
		}
		return 0;
	}
	private void updateList(){		
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
				duckMovementCollision(graphic);
			}else if(graphic.getId()==objtype.tBoat){
				checkBoatTorpedoCollision(graphic);
				if(((Boat) graphic).getNewTorpedo()){
					mTorpedoes.add(new Torpedo((int)(graphic.getCentreX()*Constants.getScreen().getRatio()),(int)(graphic.getBottomRightY()*Constants.getScreen().getRatio()),0));
				}
				graphic.frame();	//Do everything this object does every frame, like move
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
	}
	private void checkBoatTorpedoCollision(GraphicObject graphic){
		for(Torpedo torpedo : mTorpedoes){
			if(CollisionManager.circleCollision(graphic.getCollision(), torpedo.getCollision())){
				if(torpedo.getHitBoat()==true){
					((Boat) graphic).setBroken(true);
					torpedo.setExplosion(true);
				}
			}
		}
	}
	public void onDraw(Canvas canvas){
		int width = Constants.getScreen().getWidth();
		int num = (int) Math.ceil((double)mLevelWidth/Constants.getScreen().getWidth());
		
		for(int i = 0; i < (num); i++){
			mRect.set((int) ((width*i)-mScrollBy), 0, (int)((width*(i+1)) - mScrollBy), Constants.getScreen().getHeight());
			canvas.drawBitmap(mBackgroundImage, null, mRect,  null);
		}

		drawBathTub(canvas);

		for (Whirlpool whirlpool : mWPoolModel.getWpools()) {
			whirlpool.draw(canvas);
		}
		for(GraphicEnvironment enviro : mEnvironments){
			enviro.draw(canvas);
		}
		for(Torpedo torpedo : mTorpedoes){
			torpedo.draw(canvas);
		}
		for (GraphicObject graphic : mGraphics) {
			graphic.draw(canvas);
		}
	}
	private void drawBathTub(Canvas canvas){
		int bottom =Constants.getScreen().getHeight();
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
	private void duckOnScreen(){
		
		mScrollBy = Constants.getPlayer().getCentreX()- Constants.getScreen().getWidth()/2;
		if(mScrollBy < 0){
			mScrollBy = 0;
		}
		if(mScrollBy + Constants.getScreen().getWidth() > mLevelWidth){
			mScrollBy = mLevelWidth - Constants.getScreen().getWidth();
		}
	}
	private void duckMovementCollision(GraphicObject graphic){
		graphic.frame();	//Do everything this object does every frame, like move
		if(((Duck) graphic).getFinished() == false){
			for(Iterator<GraphicObject> collisionIterator = mGraphics.listIterator(); collisionIterator.hasNext();){
				GraphicObject graphic2 = collisionIterator.next();
				if(graphic2.getId()==objtype.tBoat){
					boolean collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),((Boat) graphic2).getBoatRadius());
					if(collision){
						((Boat) graphic2).changeAnimation();	
					}
				}else if(graphic2.getId()==objtype.tShark){
					duckSharkMovementCollision(graphic, graphic2);
				}else{
					((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision());
				}
			}
			for(Torpedo torpedo : mTorpedoes){
				boolean collision = false;
				if(torpedo.getIsReadyToDestroy()==false && torpedo.getExplosion() == false){
					collision = ((Duck) graphic).checkObjectCollision(torpedo.getId(), torpedo.getCollision());
					if(collision){
						torpedo.setExplosion(true);
					}
				}
			}
		}
	}
	private void duckSharkMovementCollision(GraphicObject graphic, GraphicObject graphic2){
		boolean collision = false;
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
	private boolean enemiesOnScreen(GraphicObject TempEnemy){
		// work out the boundries opf the screen and then see what enemies are in that boundry
		//work out the edges of the screen
		float LeftEdge = Constants.getPlayer().getCentreX()- Constants.getScreen().getWidth()/2;
		float RightEdge =Constants.getPlayer().getCentreX()+ Constants.getScreen().getWidth()/2;
		float TopEdge =Constants.getPlayer().getCentreX()- Constants.getScreen().getHeight()/2;
		float BottomEdge =Constants.getPlayer().getCentreX()+ Constants.getScreen().getHeight()/2;
		
		// get the edges of the enemy sprite
		float EnemyLeftEdge = (TempEnemy.getCentreX() -(TempEnemy.getWidth()/2));
		float EnemyRightEdge = (TempEnemy.getCentreX() + (TempEnemy.getWidth()/2));
		float EnemyTopEdge = (TempEnemy.getCentreY() - (TempEnemy.getHeight()/2));
		float EnemyBottomEdge = (TempEnemy.getCentreY() + (TempEnemy.getHeight()/2));
		
		//using the edges of the screen and the enemy sprite, do a  bounding box calculation to see if the enemy is inside the screen	
		if (RightEdge < EnemyLeftEdge) { return false; }		
		if (BottomEdge < EnemyTopEdge) { return false; }
		if (LeftEdge > EnemyRightEdge) { return false; }
		if (TopEdge > EnemyBottomEdge) { return false; }
		
		return true;
	}

}
