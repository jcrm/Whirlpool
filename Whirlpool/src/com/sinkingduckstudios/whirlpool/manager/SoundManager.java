/*  Start date	: 18/02/2013
 * 
 *  Description	: class used to control and play all of the sound in the game. this class will play the background music
 *			 	  continuously throughout the game and also play the sound effects for the game based on what's happening 
 *				  in the game ( i.e. ducky hits the side of the bath play one of the bounce sound effects). when needed
 *		  		  a sound effect will play at random form a list of appropriate sounds 
 * 
 * 
 * Completion date : TBC...
 * 
 */
package com.sinkingduckstudios.whirlpool.manager;

import java.io.IOException;
import java.util.Random;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;


import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
	// global variables for the class :
	// media player variables for ducky
	private MediaPlayer mDucky[] = new MediaPlayer[6];		// an array to hold the different sounds for ducky
	private MediaPlayer mBounce[] = new MediaPlayer[2];		// an array to hold the different sounds for ducky bouncing
	private MediaPlayer mAngry = new MediaPlayer();			// a variable to hold duckys angry sound
	private MediaPlayer mScared = new MediaPlayer();			// a variable to hold duckys scared sound

	// variables for the enemies sounds
	private MediaPlayer mFrog = new MediaPlayer();			// a variable to hold the sound for the frog
	private MediaPlayer mDiver = new MediaPlayer();			// a variable to hold the sound for the diver
	private MediaPlayer mTugBoat = new MediaPlayer();			// a variable to hold the sound for the tugboat

	// variables for the other sounds
	private MediaPlayer mAmbient = new MediaPlayer();			// used for the ambient sounds in the bath
	private MediaPlayer mDownPlug = new MediaPlayer();		// used fo the down the plug sound
	private MediaPlayer mPlug = new MediaPlayer();			// used to hold a different plug sound
	private MediaPlayer mPoints = new MediaPlayer();			// used to hold the sounds for the points
	private MediaPlayer mSplash = new MediaPlayer();			// used to hold the sound of a splash
	private MediaPlayer mWhirlpool = new MediaPlayer();		// used to hold the sound of the whirlpool

	//varaibles for the background music
	private MediaPlayer mBackGround = new MediaPlayer();		// used to hold the background music

	//Initialize music
	public void initSound(){
		Context GameContext = Constants.getContext();
		
		// initialise all of the sounds for the game.
		mDucky[0] =MediaPlayer.create(GameContext, R.raw.ducky); 				// load in the sounds for ducky
		mDucky[1] =MediaPlayer.create(GameContext, R.raw.ducky2); 
		mDucky[2] =MediaPlayer.create(GameContext, R.raw.ducky3); 
		mDucky[3] =MediaPlayer.create(GameContext, R.raw.ducky4); 
		mDucky[4] =MediaPlayer.create(GameContext, R.raw.ducky5); 
		mDucky[5] =MediaPlayer.create(GameContext, R.raw.ducky6); 

		mAngry = MediaPlayer.create(GameContext, R.raw.angryducky);				// load in the scared and angry ducky sounds
		mScared = MediaPlayer.create(GameContext, R.raw.scaredducky);

		mBounce[0] = MediaPlayer.create(GameContext, R.raw.bounce);				// load in the sounds of ducky bouncing
		mBounce[1] = MediaPlayer.create(GameContext, R.raw.bounce2);

		mFrog = MediaPlayer.create(GameContext, R.raw.frognoisemain); 			// load in the frog noise
		mDiver = MediaPlayer.create(GameContext, R.raw.diver);					// load in the other enemys
		mTugBoat = MediaPlayer.create(GameContext, R.raw.tugboat);
		mAmbient = MediaPlayer.create(GameContext, R.raw.ambientbath);			// load in the ambient noise
		mDownPlug = MediaPlayer.create(GameContext, R.raw.downplug);			// load in sounds for the plug
		mPlug = MediaPlayer.create(GameContext, R.raw.plug);
		mPoints = MediaPlayer.create(GameContext, R.raw.points);				// load in the sound for the points
		mSplash = MediaPlayer.create(GameContext, R.raw.splash);				// load in splash sound
		mWhirlpool = MediaPlayer.create(GameContext, R.raw.whirlpool2);			// load in whirlpool sound
		mBackGround = MediaPlayer.create(GameContext, R.raw.temp_bg_music);	//load in the background music track( temporary)
		// put all the music files through a try catch to make sure all the files are found and okay
		try{
			// try to prepare the files to makesure the application can find them and access them
			mAngry.prepare();
			mScared.prepare();
			mFrog.prepare();
			mDiver.prepare();
			mTugBoat.prepare();
			mAmbient.prepare();
			mDownPlug.prepare();
			mPlug.prepare();
			mPoints.prepare();
			mSplash.prepare();
			mWhirlpool.prepare();
			mBackGround.prepare();

			// use loops to prepare the ducky and bounce sounds
			for (int i = 0; i < 6; i ++){
				mDucky[i].prepare();
			}

			for (int j = 0; j <2; j++){
				mBounce[j].prepare();
			}
		}catch(IllegalStateException e){// check the state of the music files
			e.printStackTrace();
		}catch(IOException e) {// make sure there is not input/output errors
			e.printStackTrace();
		}
	}// end of initsounds

	// play background music
	public void playBackgroundMusic(){
		mBackGround.setLooping(true);		// set the background track to looping
		mBackGround.start();				// start the track;
	}// end of playBackgroundMusic

	// pause background music only
	public void pauseBackground(){
		mBackGround.pause();				//	pause the background music track
	}// end of PauseBackGround

	public void playDucky(){
		//generate a random number between 0 and 5 and play the corrosponding sound
		// play a sound depending on the random number generated
		// switch by the random number 
		switch (new Random().nextInt(6)){
		// if the random number is 0
		case 0 : {
			// play the first ducky sound
			mDucky[0].start();
			break;				// break out of the switch
		}// end case 0
		// if the random number is 1
		case 1 : { 
			// play the second sound
			mDucky[1].start();
			break;				// break out of the switch
		}// ens case 1
		// if the random number is 2 
		case 2 :{
			// play the thrid sound
			mDucky[2].start();
			break;				// break out of the switch
		}// end case 2
		// if the random number is 3
		case 3 :{
			// play the fourth sound
			mDucky[3].start();
			break;				// break out of the switch
		}// end case 3
		// if the random number is 4
		case 4: {
			// play the fith sound
			mDucky[4].start();
			break;				// break out of the switch
		}// end case 4

		// if the random number is 5
		case 5 :{
			// play the sixth sound
			mDucky[5].start();
			break;				// break out of the switch
		}// ens case 5
		}// end of switch (RandNum);	
	}// end of playDucky

	// play random bounce
	public void playBounce(){
		//generate a random number between 0 and 2 and play the corrosponding sound
		// use this number to decide what sound to play
		switch (new Random().nextInt(2)){
		// if the numebr is 0
		case 0 : {
			// play the first sound
			mBounce[0].start();
			break;				// break out of the switch
		}//end of case 0
		//if the number is 1 
		case 1 :{
			// play the second sound
			mBounce[1].start();
			break;				// break out of the switch
		}// end of case 1
		}// end of switch (randnum)
	}// ens of playBounce.

	// play scared ducky
	public void playScaredDucky(){
		mScared.start();				// play the scaed ducky sound
	}// end of scared ducky

	// play angry ducky
	public void playAngryDucky(){
		mAngry.start();					// play the angry ducky sound
	}// end of anrgy ducky

	//play ambient bath
	public void playAmbientNoise(){
		mAmbient.start(); 				// play the ambient bath noise
	}// end of ambient noise

	// play a random frog sound
	public void playFrogSound(){
		mFrog.start();						// play the frog noise
	}// end of frog

	// play divers sound
	public void playDiver(){
		mDiver.start();				// play the diver noise
	}// end of diver

	// play tug boat
	public void playTugBoat(){
		mTugBoat.start();				// play the tugboat noise
	}// end of tug boat

	// play down the plug
	public void playDownPlug(){
		mDownPlug.start();			// play the down the plug noise
	}// end of down plug

	// play the plug sound
	public void playPlug(){
		mPlug.start();				// play the plug noise
	}// end of play plug

	// play the points sound
	public void playPoints(){
		mPoints.start();				// play the points sound
	}// end of points

	//play splash
	public void playSplash(){
		mSplash.start();				// play the slash sound
	}// end of splash

	// play whirlpool sound
	public void playWhirlPool(){
		mWhirlpool.start();					// play the whirlpool sound
	}// end of whirlpool

	// pause all sounds
	public void pauseAllSounds(){
		// make sure all tracks are paused
		// use a loop for the bounce and ducky sounds to pause the full arrays
		for( int i = 0; i < 6; i++){
			mDucky[i].pause();
		}

		for ( int j = 0; j < 2; j++){
			mBounce[j].pause();
		}

		//go through all of the tracks and pause them all
		mAngry.pause();
		mScared.pause();
		mFrog.pause();
		mTugBoat.pause();
		mAmbient.pause();
		mDownPlug.pause();
		mPlug.pause();
		mPoints.pause();
		mSplash.pause();
		mWhirlpool.pause();
		mBackGround.pause();
	}// end of pause

	// pause all sounds
	public void stopAllSounds(){
		// make sure all tracks are paused
		// use a loop for the bounce and ducky sounds to pause the full arrays
		for( int i = 0; i < 6; i++){
			mDucky[i].stop();
		}

		for ( int j = 0; j < 2; j++){
			mBounce[j].stop();
		}

		//go through all of the tracks and pause them all
		mAngry.stop();
		mScared.stop();
		mFrog.stop();
		mTugBoat.stop();
		mAmbient.stop();
		mDownPlug.stop();
		mPlug.stop();
		mPoints.stop();
		mSplash.stop();
		mWhirlpool.stop();
		mBackGround.stop();
	}// end of pause
		
	// change the volume of all of the tracks by a set value
	public void changeVolume(float TempVol){
		mBackGround.setVolume(TempVol, TempVol);
	}

}// end of sound player class