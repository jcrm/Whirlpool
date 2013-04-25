/*
 * Author: Jake Morey, Fraser, Jordan O'Hare, Connor Nicol, Lewis
 * Content:
 * Jordan O'Hare: created original file which has been added to
 * Jake Morey: add code about updating enemies, drawing the bath tub, and adding objects based upon level number.
 * Connor Nicol - added in the sounds for the enemies
 * 				- made sure that the sounds are only played once per enemy on screen( so if there are 2 divers on screen, sound will only play once)
 * 				- made sure the sounds did not play continuously 
 * Lewis: added scrolling code.
 * Fraser: added level times
 */
package com.sinkingduckstudios.whirlpool.logic;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
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

/**
 * The Class Level.
 */
public class Level extends Activity{
	/** The wpool model. */
	private final WPools mWPoolModel = new WPools();
	/** The graphics list. */
	private ArrayList<GraphicObject> mGraphics = new ArrayList<GraphicObject>();
	/** The torpedoes list. */
	private ArrayList<Torpedo> mTorpedoes = new ArrayList<Torpedo>();
	/** The environments list. */
	private ArrayList<GraphicEnvironment> mEnvironments = new ArrayList<GraphicEnvironment>();
	/** The 2-star time required (in secs). */
	private int mLevelAverageTime;
	/** The 3-star time required (in secs). */
	private int mLevelGoodTime;
	/** The m level width. */
	private int mLevelWidth = 0;
	/** The level height. */
	private int mLevelHeight = 0;
	/** The scroll by value. */
	private float mScrollBy = 0;
	/** The background image. */
	private Bitmap mBackgroundImage;
	/** The left border image. */
	private Bitmap mLeftBorderImage;
	/** The right border image. */
	private Bitmap mRightBorderImage;
	/** The top border image. */
	private Bitmap mTopBorderImage;
	/** The Diver counter. */
	private int DiverCounter;
	/** The Frog counter. */
	private int FrogCounter;
	/** The Tug boat counter. */
	private int TugBoatCounter;
	/** The Shark counter. */
	private int SharkCounter;
	/** The Diver playing. */
	private boolean DiverPlaying;
	/** The Frog playing. */
	private boolean FrogPlaying;
	/** The Tug boat playing. */
	private boolean TugBoatPlaying;
	/** The Shark playing. */
	private boolean SharkPlaying;
	/** The collectables value. */
	private int mCollectables;
	/** The screen lock. */
	private static Object mScreenLock;
	/** The rect. */
	private Rect mRect = new Rect();
	/** The follow this. */
	private GraphicObject mFollowThis;//holds which object the next collectable should follow

	/**
	 * Instantiates a new level.
	 */
	public Level() {
	}
	/**
	 * Inits the level.
	 */
	public void init(){
		init(1);
	}
	/**
	 * Inits the level with the correct level number.
	 *
	 * @param lNumber the level number
	 */
	public void init(int lNumber){
		mLevelHeight = (int) (500/Constants.getScreen().getRatio());
		initImages();
		levelNumber(lNumber);

		DiverCounter = 0;
		FrogCounter = 0;
		TugBoatCounter = 0;
		SharkCounter = 0;
		DiverPlaying = false;
		FrogPlaying= false;
		TugBoatPlaying = false;
		SharkPlaying= false;
		Constants.getPanel().setOnTouchListener(new TrackingTouchListener(mWPoolModel));
		mScreenLock=Constants.getLock();
	}

	/**
	 * Inits the images.
	 */
	private void initImages(){
		mBackgroundImage = SpriteManager.getBackground();
		mLeftBorderImage = SpriteManager.getLeftBorder();
		mRightBorderImage = SpriteManager.getRightBorder();
		mTopBorderImage = SpriteManager.getTopBorder();
	}

	/**
	 * Update all objects on screen.
	 *
	 * @return the value of the stage of the end point.
	 */
	public int update(){
		//update graphic objects
		updateList();
		Constants.setDuckDist(9000000);//max distance
		//iterate through torpedo list
		for(Iterator<Torpedo> tIterator = mTorpedoes.listIterator(); tIterator.hasNext();){
			Torpedo torpedo = tIterator.next();
			boolean isColliding = false;
			//check for collision with whirlpools
			for(Whirlpool whirl : mWPoolModel.getWpools()){
				if(whirl.checkCollision(torpedo))
					isColliding=true;
			}
			if(!isColliding){
				torpedo.setPulledBy(null);
				torpedo.setPulledState(Constants.STATE_FREE);
			}
			if(torpedo.getIsReadyToDestroy()){		//check to see if the torpedo needs to be destroyed
				Constants.getSoundManager().playExplosion();
				tIterator.remove();
			}else if(torpedo.updateDirection() && torpedo.getExplosion() == false){	//checks if the direction needs to get updated 
				torpedo.setDuckPosition(Constants.getPlayer().getCentreX(),Constants.getPlayer().getCentreY());
				torpedo.frame();
			}else{	//checks to see if need to play missile sound
				torpedo.checkBeep();
				torpedo.frame();	//Do everything this object does every frame, like move
			}
			//set the distance between the torpedo and duck
			float theDist = torpedo.getDist();
			if (theDist<Constants.getDuckDist())
				Constants.setDuckDist(theDist);	
		}
		//play the missile sound depending on the closest missile
		float vol = 1 - (Constants.getDuckDist()/9000000);
		Constants.getSoundManager().alterBeepVolume(vol);
		//iterate through the whirlpool list
		for(int i = 0; i < mWPoolModel.getWpools().size(); i++){
			//do things the whilrpool needs to do each frame
			mWPoolModel.getWpools().get(i).frame();
			//if finished remove whilrpool
			if(mWPoolModel.getWpools().get(i).getFinished()){
				mWPoolModel.getWpools().remove(i);
				i--;
			}else
				mWPoolModel.getWpools().get(i).collisionDone=true;
		}
		//iterate through the environment list
		for(GraphicEnvironment enviro: mEnvironments){
			if(enviro.getId() == envtype.tFinish){
				//if all three baby ducks collected then activate the finish
				if(getDuckCount()==3)
					((Finish)enviro).activate();
				//depending on the stage of the finish point return a different value
				//return one if hit the end so that the timer can be stopped
				//return two when the hit animation has finished so the level can end
				int count =((Finish) enviro).getEnd(); 
				if(count==1){
					enviro.frame();
					return 1;
				}else if(count == 2){
					return 2;
				}
				enviro.frame();
			}else{
				enviro.frame();
			}
		}
		//play the enemy sounds
		enemySounds();
		synchronized(mScreenLock){
			duckOnScreen();
		}
		return 0;
	}

	/**
	 * Update list of graphic objects.
	 */
	private void updateList(){
		//check list of graphic objects for movement
		for(Iterator<GraphicObject> mainIterator = mGraphics.listIterator(); mainIterator.hasNext();){
			GraphicObject graphic = mainIterator.next();
			//check through list of whirlpools for interactions
			boolean isColliding = false;
			//check for collision with whirlpools
			for(Whirlpool whirl : mWPoolModel.getWpools()){
				if(whirl.checkCollision(graphic))
					isColliding=true;
			}
			if(!isColliding){
				graphic.setPulledBy(null);
				graphic.setPulledState(Constants.STATE_FREE);
			}
			//check through lists of environments for collision
			for(GraphicEnvironment enviro: mEnvironments){
				//check for collision against end point
				if(enviro.getId()==envtype.tFinish && graphic.getId() == objtype.tDuck && ((Finish)enviro).isActive()){
					if(((Finish) enviro).checkCollision(graphic)){
						((Duck) graphic).setFinished(true);
					}
				}
			}
			//depending on type of object do different things
			if(graphic.getId()==objtype.tDuck){
				//check duck for collisions
				duckMovementCollision(graphic);
			}else if(graphic.getId()==objtype.tBoat){
				//check boat for torpedo collision
				checkBoatTorpedoCollision(graphic);
				//check if a new torpedo needs to be created
				if(((Boat) graphic).getNewTorpedo()){
					mTorpedoes.add(new Torpedo((int)(graphic.getCentreX()*Constants.getScreen().getRatio()),(int)(graphic.getBottomRightY()*Constants.getScreen().getRatio()),0));
				}
				graphic.frame();	//Do everything this object does every frame, like move
			}else if(graphic.getId()==objtype.tCollectable){
				//check to see if coleectable has been hit
				boolean collided = ((Collectable)graphic).getCollided();
				graphic.frame();
				if (collided!=((Collectable)graphic).getCollided()){
					((Collectable)graphic).setFollowing(mFollowThis);
					mFollowThis=graphic;
					mCollectables ++;
				}
			}else{
				graphic.frame();	//Do everything this object does every frame, like move
			}
		}
	}

	/**
	 * Check boat torpedo collision.
	 *
	 * @param graphic the graphic
	 */
	private void checkBoatTorpedoCollision(GraphicObject graphic){
		//check the list of torpedos for collisions with the boat
		for(Torpedo torpedo : mTorpedoes){
			if(CollisionManager.circleCollision(graphic.getCollision(), torpedo.getCollision())){
				//if collided with boat then destroy torpedo and brake boat for a bit
				if(torpedo.getHitBoat()==true){
					((Boat) graphic).setBroken(true);
					torpedo.setExplosion(true);
				}
			}
		}
	}

	/**
	 * On draw.
	 *
	 * @param canvas the canvas
	 */
	public void onDraw(Canvas canvas){
		//draw background, bath tub, and all other objects.
		int width = Constants.getScreen().getWidth();
		int num = (int) Math.ceil((double)mLevelWidth/Constants.getScreen().getWidth());
		//draw background
		for(int i = 0; i < (num); i++){
			mRect.set((int) ((width*i)-mScrollBy), 0, (int)((width*(i+1)) - mScrollBy), Constants.getScreen().getHeight());
			canvas.drawBitmap(mBackgroundImage, null, mRect,  null);
		}
		//draw bath tub
		drawBathTub(canvas);
		for (Whirlpool whirlpool : mWPoolModel.getWpools()){
			whirlpool.draw(canvas);
		}
		//draw environments
		for(GraphicEnvironment enviro : mEnvironments){
			enviro.draw(canvas);
		}
		//draw torpedoes
		for(Torpedo torpedo : mTorpedoes){
			torpedo.draw(canvas);
		}
		//draw enemies and duck
		for (GraphicObject graphic : mGraphics){
			graphic.draw(canvas);
		}
	}

	/**
	 * Draw bath tub.
	 *
	 * @param canvas the canvas
	 */
	private void drawBathTub(Canvas canvas){
		//draw bath tub around the screen
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

	/**
	 * Gets the w pool model.
	 *
	 * @return the w pool model
	 */
	public WPools getWPoolModel(){
		return mWPoolModel;
	}

	/**
	 * Gets the level width.
	 *
	 * @return the level width
	 */
	public int getLevelWidth(){
		return mLevelWidth;
	}

	/**
	 * Gets the level height.
	 *
	 * @return the level height
	 */
	public int getLevelHeight(){
		return mLevelHeight;
	}

	/**
	 * Sets the level width.
	 *
	 * @param levelWidth the new level width
	 */
	public void setLevelWidth(int levelWidth){
		mLevelWidth = levelWidth;
	}

	/**
	 * Sets the level height.
	 *
	 * @param levelHeight the new level height
	 */
	public void setLevelHeight(int levelHeight){
		mLevelHeight = levelHeight;
	}

	/**
	 * Gets the scroll by.
	 *
	 * @return the scroll by
	 */
	public float getScrollBy(){
		return mScrollBy;
	}

	/**
	 * Sets the scroll by.
	 *
	 * @param scrollBy the new scroll by
	 */
	public void setScrollBy(float scrollBy){
		mScrollBy = scrollBy;
	}

	/**
	 * Gets the duck count.
	 *
	 * @return the duck count
	 */
	public int getDuckCount(){
		return mCollectables;
	}

	/**
	 * Duck on screen.
	 */
	private void duckOnScreen(){
		//calculate how much to translate the background by
		mScrollBy = Constants.getPlayer().getCentreX()- Constants.getScreen().getWidth()/2;
		//make sure it is inside the size of the level
		if(mScrollBy < 0){
			mScrollBy = 0;
		}
		if(mScrollBy + Constants.getScreen().getWidth() > mLevelWidth){
			mScrollBy = mLevelWidth - Constants.getScreen().getWidth();
		}
	}

	/**
	 * Duck movement collision.
	 *
	 * @param graphic the enemy object
	 */
	private void duckMovementCollision(GraphicObject graphic){
		graphic.frame();	//Do everything this object does every frame, like move
		//if not swimming around the end point do other collision
		if(((Duck) graphic).getFinished() == false){
			//iterate through other objects
			for(Iterator<GraphicObject> collisionIterator = mGraphics.listIterator(); collisionIterator.hasNext();){
				GraphicObject graphic2 = collisionIterator.next();
				//if collision with boat change boat animation
				if(graphic2.getId()==objtype.tBoat){
					boolean collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),((Boat) graphic2).getBoatRadius());
					if(collision){
						((Boat) graphic2).changeAnimation();	
					}
				}else if(graphic2.getId()==objtype.tShark){
					//do shark and duck collision
					duckSharkMovementCollision(graphic, graphic2);
				}else{
					//check for collision agiant other objects
					((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision());
				}
			}
			//iterate through the torpedo list
			for(Torpedo torpedo : mTorpedoes){
				boolean collision = false;
				//if torpedo is not being destroyed then check for collision
				if(torpedo.getIsReadyToDestroy()==false && torpedo.getExplosion() == false){
					collision = ((Duck) graphic).checkObjectCollision(torpedo.getId(), torpedo.getCollision());
					//if collision play explosion animation
					if(collision){
						torpedo.setExplosion(true);
					}
				}
			}
		}
	}

	/**
	 * Duck shark movement collision.
	 *
	 * @param graphic the duck object
	 * @param graphic2 the shark object
	 */
	private void duckSharkMovementCollision(GraphicObject graphic, GraphicObject graphic2){
		boolean collision = false;
		//if the shark is asleep then check that the duck is in a certain radius
		if(((Shark) graphic2).getSharkState() == SharkType.tAsleep){
			collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),((Shark) graphic2).getSharkRadius());
			//check if the shark can attack again
			if(collision && ((Shark) graphic2).checkTime()){
				((Shark) graphic2).setSharkState(SharkType.tFollow);
			}
		}else if(((Shark) graphic2).getSharkState() == SharkType.tFollow){ //check if the shark is following the duck
			collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision());
			//if collision disable the duck graphic, and set it to shark position, speed, and angle
			if(collision){							
				((Shark) graphic2).setSharkState(SharkType.tAttack);
				((Shark) graphic2).moveToDrop();
				((Duck) graphic).setSharkAttack(true);
				graphic.setSpeed(graphic2.getSpeed().getSpeed());
				graphic.setAngle(graphic2.getSpeed().getAngle());
				graphic.setCentreX((int)(graphic2.getCentreX()*Constants.getScreen().getRatio()));
				graphic.setCentreY((int)(graphic2.getCentreY()*Constants.getScreen().getRatio()));
			}else if(((Duck) graphic).getInvincibility() == true){
				//if the duck is invincibable and with in a certain radius then wait until the duck has left the radius
				collision = ((Duck) graphic).checkObjectCollision(graphic2.getId(), graphic2.getCollision(),((Shark) graphic2).getSharkRadius());
				if(collision){
					((Shark) graphic2).setSharkState(SharkType.tWait);
				}
			}
		}
	}
	
	/**
	 * Enemy sounds.
	 */
	private void enemySounds(){
		//go through the list of graphics objects
		for(Iterator<GraphicObject> gIterator = mGraphics.listIterator(); gIterator.hasNext();){
			GraphicObject Enemy = gIterator.next();
			// if the enemy is on screen...
			if(enemiesOnScreen(Enemy)){
				// find out the enemies type and play the relevant type
				switch (Enemy.getType()){
				case 1:{												// the diver
					if(Enemy.getIsPlaying() == false){					//if that enemies sound is not already playing...
						if( DiverPlaying == false){						// if the diver sound is not already playing
							Constants.getSoundManager().playDiver();	// play the enemies sound
							Enemy.setIsPlaying(true);					// show that the sound is playing
							DiverCounter = 0;							// re-set the counter to 0
							DiverPlaying = true;						// show that the diver sound is playing
						}
					}
					DiverCounter ++;									// increment the counter for the diver
					if(DiverCounter >= 500){							// if the counter has reached 500...
						Enemy.setIsPlaying(false);						// set the sound playing to false to show that it can be played again
						DiverPlaying = false;							// show that the diver sound is no longer playing
					}
					break;												// break to stop the next sound playing
				}
				case 2:{												// the frog
					if (Enemy.getIsPlaying() == false){					//if that enemies sound is not already playing... 
						if( FrogPlaying == false){						// if the frog sound is not already playing
							Constants.getSoundManager().playFrog();		// play the enemies sound
							Enemy.setIsPlaying(true);					// show that the sound is playing
							FrogCounter = 0;							// re-set the counter to 0
							FrogPlaying = true;							// show that the frog sound is playing
						}
					}
					FrogCounter ++;										// increment the counter for the frog
					if(FrogCounter >= 500){								// if the counter has reached 500...
						Enemy.setIsPlaying(false);						// set the sound playing to false to show that it can be played again
						FrogPlaying = false; 							// show that the sound is no longer playing 
					}
					break;												// break to stop the next sound playing
				}
				case 3:{												// the tugboat
					if(Enemy.getIsPlaying() == false){					//if that enemies sound is not already playing...
						if(TugBoatPlaying == false){					// if the tug boat is not already playing
							Constants.getSoundManager().playTugBoat();	// play the enemies sound
							Enemy.setIsPlaying(true);					// show that the sound is playing
							TugBoatCounter = 0;							// re-set the counter to 0
							TugBoatPlaying = true;						// show that the sound is already playing
						}
					}
					TugBoatCounter ++;									// increment the counter for the tug boat
					if(TugBoatCounter >= 500){							// if the counter has reached 500...
						Enemy.setIsPlaying(false);						// set the sound playing to false to show that it can be played again
						TugBoatPlaying = false;							// show that the sound is no longer playing
					}
					break;												// break to stop the next sound playing
				}
				case 4:{												// the shark
					if(Enemy.getIsPlaying() == false){					//if that enemies sound is not already playing...
						if( SharkPlaying == false){						// if the shark sound is not already playing
							Constants.getSoundManager().playShark();	// play the enemies sound
							Enemy.setIsPlaying(true);					// show that the sound is playing
							SharkCounter = 0;							// re-set the counter to 0
							SharkPlaying = true;						// show that the sound is playing
						}
					}
					SharkCounter ++;									// increment the counter for the shark
					if(SharkCounter >= 500)	{							// if the counter has reached 500...
						Enemy.setIsPlaying(false);						// set the sound playing to false to show that it can be played again
						SharkPlaying = false;							// show that the sound is no longer playing
					}
					break;
				}
				}
			}
		}
	}
	/**
	 * Enemies on screen.
	 *
	 * @param TempEnemy the enemies
	 * @return true, if successful
	 */
	private boolean enemiesOnScreen(GraphicObject TempEnemy){
		// work out the boundaries of the screen and then see what enemies are in that boundary
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

	/**
	 * Level number.
	 * Add different objects depending on level number.
	 * @param lNumber the level number
	 */
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
			mGraphics.add(new Collectable(600,250));
			mGraphics.add(new Collectable(1200,250));
			mGraphics.add(new Collectable(750,450));
			mLevelWidth = (int) (1500/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(1400, 250));
			
			mLevelAverageTime = 40;	//seconds to get 2 stars
			mLevelGoodTime = 20;	//seconds to get 3 stars
			break;
		case 2:
			mGraphics.add(new Diver(100, 0, 45, 100, 0, 400, 250));
			mGraphics.add(new Diver(100, 500, 135, 100, 250, 400, 500));
			mGraphics.add(new Diver(600, 500, 135, 0, 0, 0, 0));
			mGraphics.add(new Frog(600, 250, 75));
			mGraphics.add(new Frog(1250, 250, 140));
			mGraphics.add(new Collectable(800,250));
			mGraphics.add(new Collectable(250,50));
			mGraphics.add(new Collectable(250,450));
			mLevelWidth = (int) (1500/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(1250, 250));
			
			mLevelAverageTime = 30;	//seconds to get 2 stars
			mLevelGoodTime = 15;	//seconds to get 3 stars
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
			mGraphics.add(new Collectable(500,250));
			mGraphics.add(new Collectable(1350,50));
			mGraphics.add(new Collectable(1350,450));
			mLevelWidth = (int) (1500/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(1200, 250));
			
			mLevelAverageTime = 25;	//seconds to get 2 stars
			mLevelGoodTime = 15;	//seconds to get 3 stars
			break;
		case 4:
			mGraphics.add(new Diver(100, 350, 0, 0, 400, 1000, 400));
			mGraphics.add(new Diver(1000, 50, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(1600, 50, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(2100, 200, 90, 0, 0, 0, 0));
			mGraphics.add(new Diver(2600, 100, 90, 0, 0, 0, 0));
			mGraphics.add(new Boat(1200,207));
			mGraphics.add(new Frog(600, 250, 140));
			mGraphics.add(new Frog(1200, 250, 140));		
			mGraphics.add(new Shark(600,300,300,150));
			mGraphics.add(new Collectable(500,50));
			mGraphics.add(new Collectable(1000,400));
			mGraphics.add(new Collectable(2100,350));
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 250));
			
			mLevelAverageTime = 35;	//seconds to get 2 stars
			mLevelGoodTime = 25;	//seconds to get 3 stars
			break;
		case 5: 
			mGraphics.add(new Diver(100, 50, 0, 0, 0, 900, 0));
			mGraphics.add(new Diver(100, 400, 0, 0, 0, 900, 0));
			mGraphics.add(new Diver(1600, 50, 90, 1500, 0, 0, 500));
			mGraphics.add(new Diver(2100, 200, 45, 0, 0, 0, 0));
			mGraphics.add(new Diver(2600, 100, 135, 0, 0, 0, 0));
			mGraphics.add(new Frog(400, 250, 100));
			mGraphics.add(new Frog(900, 250, 100));	
			mGraphics.add(new Frog(1200, 200, 100));
			mGraphics.add(new Frog(1500, 350, 100));	
			mGraphics.add(new Frog(1700, 200, 100));
			mGraphics.add(new Frog(2350, 200, 100));	
			mGraphics.add(new Collectable(500,50));
			mGraphics.add(new Collectable(1500,400));
			mGraphics.add(new Collectable(2000,190));
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 250));
			
			mLevelAverageTime = 40;	//seconds to get 2 stars
			mLevelGoodTime = 25;	//seconds to get 3 stars
			break;
		case 6:
			mGraphics.add(new Diver(800, 50, 90, 0, 0, 0, 300));
			mGraphics.add(new Diver(1000, 450, 90, 0, 50, 0, 500));
			mGraphics.add(new Diver(1500, 250, 45, 1000, 0, 1500, 400));
			mGraphics.add(new Diver(1500, 250, 135, 1500, 0, 2000, 400));
			mGraphics.add(new Diver(2100, 100, 90, 0, 50, 0, 500));
			mGraphics.add(new Frog(2500, 150, 100));
			mGraphics.add(new Frog(2500, 350, 100));
			mGraphics.add(new Boat(600,250));
			mGraphics.add(new Collectable(600,425));
			mGraphics.add(new Collectable(1500,50));
			mGraphics.add(new Collectable(2500,350));
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 235));
			
			mLevelAverageTime = 45;	//seconds to get 2 stars
			mLevelGoodTime = 27;	//seconds to get 3 stars
			break;
		default: 
			mLevelWidth = (int) (3000/Constants.getScreen().getRatio());
			mEnvironments.add(new Finish(2900, 250));
			break;
		}
	}

	/**
	 * Clean up.
	 */
	public void cleanUp(){
		SpriteManager.unloadBoat();
		SpriteManager.unloadDiver();
		SpriteManager.unloadFrog();
		SpriteManager.unloadShark();
	}
	/**
	 * Get Average Time
	 * @return the level average time
	 */
	public int getAverageTime() {
		return mLevelAverageTime;
	}
	/**
	 * Get Good Time
	 * @return the level good time
	 */
	public int getGoodTime() {
		return mLevelGoodTime;
	}

}
