/*
 * Author: Connor Nicol
 * Last Updated: 18/02/2013
 * Content:
 * class used to control and play all of the sound in the game. this class will play the background music
 * continuously throughout the game and also play the sound effects for the game based on what's happening 
 * in the game ( i.e. ducky hits the side of the bath play one of the bounce sound effects). when needed
 * a sound effect will play at random form a list of appropriate sounds 
 */
package com.sinkingduckstudios.whirlpool.manager;

import java.util.Random;

import com.sinkingduckstudios.whirlpool.R;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager 
{	
	// set up variables needed for the class
	protected Context pContext;				// holds thew applications contexts, needed to load in new sounds
	protected SoundPool mSndPool;				// is the sound pool used to controll the sounds 
	protected float mRate = 1.0f;				// is the sample rate that the sounds will be played at. set to 1 to play them at the noraml rate
	protected float mMasterVolume = 1.0f;
	protected float mLeftVolume = mMasterVolume;		// the volume of the left speaker, set to on full
	protected float mRightVolume =mMasterVolume;		// the volume of the right speaker, set to on full

	protected float mBeepVolume = mMasterVolume / 2;

	// integer values to store the ID's for the sounds needed to play them
	// the sounds for ducky
	protected int mDucky[]  = new int[6];					// an array of 6 to stor the 6 sounds of ducky
	protected int mBounce[] = new int [2];					// an array of 2 to hold the sounds of ducky bouncing
	protected int mMissile[] = new int [3];
	protected int mExplosion[] = new int [3];
	protected int mAngryDucky;				// values for the angry and scared ducky sounds
	protected int mScaredDucky;

	// sounds for the enemies. frog, diver and tugboat
	protected int mDiver;	
	protected int mFrog;
	protected int mTugBoat;

	// other sounds for the game
	//protected int mBackground;				// the bacground music for the game
	protected int mAmbientbath;				// the ambient noises' heard in a bath
	protected int mDownplug;					// sounds to represent the noise of a plug
	protected int mPlug;
	protected int mPoints;					// sounds of points being gained
	protected int mSplash;					//  a splash of water
	protected int mWhirlpool;				// the sound of the whirlpool

	protected int mMissileStreamId;
	protected boolean mMissilePlaying;
	protected MediaPlayer mBackground;

	// Constructor, setup the audio manager and store the app context
	public SoundManager(Context appContext)
	{
		// the constuctor for the class.
				// set up the sound pool with the auidomanager
		pContext = appContext;		//get the games context 

	}//  end consturctor


	// a function to load the sounds for ducky
	public boolean loadDucky()
	{
		// load in the sounds for ducky
		if(mDucky[0] == 0){
			mDucky[0] = load(R.raw.ducky);
		}
		if(mDucky[1] == 0){
			mDucky[1] = load(R.raw.ducky2);
		}
		if(mDucky[2] == 0){
			mDucky[2] = load(R.raw.ducky3);
		}
		if(mDucky[3] == 0){
			mDucky[3] = load(R.raw.ducky4);
		}
		if(mDucky[4] == 0){
			mDucky[4] = load(R.raw.ducky5);
		}
		if(mDucky[5] == 0){
			mDucky[5] = load(R.raw.ducky6);
		}
		if(mBounce[0] == 0){
			mBounce[0] = load(R.raw.bounce);
		}
		if(mBounce[1] == 0){
			mBounce[1] = load(R.raw.bounce2);
		}
		if(mAngryDucky == 0){
			mAngryDucky= load(R.raw.angryducky);
		}
		if(mScaredDucky == 0){
			mScaredDucky = load(R.raw.scaredducky);
		}

		// check to see if the duckys sounds are loaded
		// when a sound is loaded is gets a resource id. so if the sound = 0 it isnt loaded yet.
		if (mDucky[0] == 0) { return false;}
		if (mDucky[1] == 0) { return false;}
		if (mDucky[2] == 0) { return false;}
		if (mDucky[3] == 0) { return false;}
		if (mDucky[4] == 0) { return false;}
		if (mDucky[5] == 0) { return false;}
		if (mBounce[0] == 0) { return false;}
		if (mBounce[1] == 0) { return false;}
		if (mAngryDucky == 0) { return false;}
		if (mScaredDucky == 0) { return false;}

		return true;	
	}

	// a function to load the 
	public boolean loadOtherSounds()
	{
		if(mAmbientbath == 0){
			mAmbientbath = load (R.raw.ambientbath);
		}
		if(mDownplug==0){
			mDownplug = load(R.raw.downplug);
		}
		if(mPlug==0){
			mPlug = load(R.raw.plug);
		}
		if(mPoints==0){
			mPoints = load(R.raw.points);
		}
		if(mSplash==0){
			mSplash = load(R.raw.splash);
		}
		if(mWhirlpool==0){
			mWhirlpool = load(R.raw.whirlpool2);
		}
		if(mBackground ==null){
			mBackground = new MediaPlayer();
		}
		if(!mBackground.isPlaying())
			mBackground =MediaPlayer.create(pContext, R.raw.temp_bg_music);

		if (mAmbientbath == 0){ return false;}
		if (mDownplug == 0){ return false;}
		if (mPlug == 0){ return false;}
		if (mPoints == 0){ return false;}
		if (mSplash == 0){ return false;}
		if (mWhirlpool == 0){ return false;}

		if (mBackground == null){ return false;}


		return true;
	}

	// load the frog enemy
	public boolean loadFrog()
	{
		if (mFrog == 0){
			mFrog = load(R.raw.frognoisemain);
		}

		if (mFrog == 0){ return false;}

		return true;
	}

	// load the diver enemy sounds
	public boolean loadDiver()
	{		
		if ( mDiver == 0){
			mDiver = load(R.raw.diver);
		}

		if ( mDiver == 0){ return false;}

		return true;
	}

	// load the tugboat and torpedo sounds
	public boolean loadTugBoat()
	{
		if (mTugBoat == 0){
			mTugBoat = load(R.raw.tugboat);
		}
		if (mExplosion[0] == 0){
			mExplosion[0] = load(R.raw.explosion);
		}
		if (mExplosion[1] == 0){
			mExplosion[1] = load(R.raw.explosion2);
		}
		if (mExplosion[2] == 0){
			mExplosion[2] = load(R.raw.explosion3);
		}
		if (mMissile[0] == 0){
			mMissile[0] = load(R.raw.beep_fast);
		}
		if (mMissile[1] == 0){
			mMissile[1] = load(R.raw.beep_slow);
		}
		if (mMissile[2] == 0){
			mMissile[2] = load(R.raw.caught);
		}


		if (mTugBoat == 0){return false;}
		if (mExplosion[0] == 0){return false;}
		if (mExplosion[1] == 0){return false;}
		if (mExplosion[2] == 0){return false;}
		if (mMissile[0] == 0){return false;}
		if (mMissile[1] == 0){return false;}
		if (mMissile[2] == 0){return false;}

		return true;
	}

	// Set the volume of the sounds.
	public void setVolume(float nVol)
	{
		// this volume sets the master volume for all of the sounds. 
		// the value sould be between the values 0.0f (off) and 1.0f ( on full)

		mMasterVolume = nVol;
		// check to see if the volume set is below 0.0f, if it is, set it to 0.0f
		if (mMasterVolume < 0.0f)
		{
			mMasterVolume = 0.0f;
		}

		// check to see if the volume set is above 1.0f, if it is, set to 1.0f
		if (mMasterVolume > 1.0f)
		{
			mMasterVolume = 1.0f;
		}

		resetVolume();

	}// end setVolume

	// a function to increase the volume
	public void increaseVolume()
	{
		// add 0.1 to both the left and the right speakers
		mMasterVolume += 0.1f;

		if (mMasterVolume >= 1.0f)
		{
			mMasterVolume = 1.0f;
		}

		resetVolume();
	}

	// a function to decrease the volume
	public void decreaseVolume()
	{
		// take away 0.1 to both the left and the right speakers
		mMasterVolume -= 0.1f;

		if (mMasterVolume <= 0.0f)
		{
			mMasterVolume = 0.0f;

		}

		resetVolume();
	}

	// set the new volumes
	public void resetVolume()
	{
		// set the left,right and background volumes to the master volume level
		mLeftVolume = mMasterVolume; 
		mRightVolume = mMasterVolume;
		mBackground.setVolume(mLeftVolume, mRightVolume);

		// set the volume level of the beeps to half of tyhe volume of the rest of the sounds
		mBeepVolume = mMasterVolume /2 ;

		// if the master volume is 0 or less
		if (mMasterVolume <= 0.0f)
		{
			// pause the sounds so that the sound is 'off'
			mSndPool.autoPause();
		}

		// if the sound gets loauder than 0 again
		if (mMasterVolume > 0.0f) 
		{
			// resume the sounds
			mSndPool.autoResume();
		}

	}


	// a function to play one of the ducky sounds. picked at random
	public void playDucky()
	{	// used to generate a random value between 0 and 5 using the varaible crated before.

		// play a sound depending on the random number generated
		// switch by the random number 
		switch (randomNumber(5))
		{
		case 0 : 						// if the randnum is 0...
		{
			playSound(mDucky[0]);		// play the first ducky sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 0

		case 1:						// if the randnum is 1...
		{
			playSound( mDucky[1]);	// play the second ducky sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 1

		case 2 : 						// if the randnum is 2...
		{
			playSound(mDucky[2]);		// play the third ducky sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 2

		case 3:						// if the randnum is 3...
		{
			playSound( mDucky[3]);	// play the fourth ducky sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 3

		case 4 : 						// if the randnum is 4...
		{
			playSound(mDucky[4]);		// play the fifth ducky sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 4

		case 5:						// if the randnum is 5...
		{
			playSound( mDucky[5]);	// play the sixth ducky sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 5

		default :						// if default is reached ( a number ouside the range 0-5 is given)...
		{
			Log.e("sound error", "default ducky reached. ERROR!");		// display an error message in logcat.
			break;					// break out of the switch block so taht no other ducky sounds are played
		} // end default
		}// end switch (RandomNumber(5))
	}// end PlayDucky

	public void playExplosion()
	{
		// play a sound depending on the random number generated
		// switch by the random number 
		switch (randomNumber(2))
		{
		case 0 :					// if the random number is 0...
		{
			playSound(mExplosion[0]);	// play the first explosion sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 0

		case 1 :					// if the random number is 1...
		{
			playSound(mExplosion[1]);	// play the  second explosion sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		} // end case 1

		case 2:
			playSound(mExplosion[2]);
			break;
		default:					// if default is reached ( a number outside the range 0-1 is given)...
		{
			Log.e("sound error", "default explosion reached. ERROR!");		// display an error message in logcat.
			break;					// break out of the switch block so taht no other ducky sounds are played
		} // end default
		}// end switch (RandomNumber(2))
	}

	// a function to play a ransdom bounce sound
	public void playBounce()
	{		  
		// play a sound depending on the random number generated
		// switch by the random number 
		switch (randomNumber(1))
		{
		case 0 :					// if the random number is 0...
		{
			playSound(mBounce[0]);	// play the first bounce sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		}// end case 0

		case 1 :					// if the random number is 1...
		{
			playSound(mBounce[1]);	// play the  second bounce sound
			break;					// break out of the switch block so taht no other ducky sounds are played
		} // end case 1

		default:					// if default is reached ( a number outside the range 0-1 is given)...
		{
			Log.e("sound error", "default bounce reached. ERROR!");		// display an error message in logcat.
			break;					// break out of the switch block so taht no other ducky sounds are played
		} // end default
		}// end switch (RandomNumber(1))

	}// end of playBounce

	// a function to play the angry ducky sound
	public void playAngry()
	{
		playSound(mAngryDucky);		// play the angry ducky sound.
	}

	// a function to play the scared ducky sound
	public void playScare()
	{
		playSound(mScaredDucky);	// play the scared ducky sound.
	}

	// a function to play the diver's sound
	public void playDiver()
	{
		playSound(mDiver); 			// play the diver's sound
	}

	// a function to play the frod'g sound
	public void playFrog()
	{
		playSound(mFrog);			// play the Frog's sound
	}

	// a function to play the tugBoat's sound
	public void playTugBoat()
	{
		playSound(mTugBoat);		// play the tugboats's sound
	}

	// a function to play the background music looped
	public void playBackGround ()
	{
		if(mBackground !=null){
			if(!mBackground.isPlaying()){
				mBackground.setLooping(true);
				mBackground.start();
				Log.w("backgroud","played");
			}
		}
	}

	// a function to play the ambient noise 
	public void playAmbient()
	{
		playSound(mAmbientbath);
	}

	// a function to play the down plug sound#
	public void playDownPlug()
	{
		playSound(mDownplug);
	}

	// a function to play the plug sound
	public void playPlug()
	{
		playSound(mPlug);
	}

	// a function to play the points sound
	public void playPoints()
	{
		playSound(mPoints);
	}

	// a function to play the splash sound
	public void playSplash()
	{
		playSound(mSplash);
	}

	// a function to play the whirlpool sound
	public void playWhirlpool()
	{
		playSound(mWhirlpool);
	}

	// a function to play the fast beep of the missile
	public void playBeepFast()
	{
		if(!mMissilePlaying){
			//mMissileStreamId = playSound(mMissile[0]);
			mMissileStreamId = mSndPool.play(mMissile[0], mLeftVolume, mRightVolume, 1, -1, mRate);
			mMissilePlaying=true;
			if(mMissileStreamId!=0)
				mSndPool.setVolume(mMissileStreamId, 0.1f, 0.1f);
		}
	}

	public void alterBeepVolume(float v){
		if (v==0){
			mSndPool.stop(mMissileStreamId);
			mMissileStreamId=0;
			mMissilePlaying=false;
			return;
		}
		v-=1;
		v*=4;
		v+=0.5;
		if(v<0)v=0;
		if(mMissileStreamId!=0)
			mSndPool.setVolume(mMissileStreamId, v, v);
	}

	// a function to play the slow beep of the missile
	public void playBeepSlow()
	{
		playSound(mMissile[1]);
	}

	// a function to play the sound of ducky being caught by the missile 
	public void playCaught()
	{
		playSound(mMissile[2]);
	}

	// Free ALL the sounds
	public void unloadAll()
	{
		// release the sound pool.
		if(mSndPool != null){
			mSndPool.release();	
			mSndPool = null;
		}
	}

	//a function to play a single sound based on a id passed in
	protected int playSound( int SoundID)
	{
		// the sound pool play function takes in 6 parameters - soundID, the id of the sound the user wants to play 
		//													  - leftvolume, the volume fo teh left speaker
		//													  - rightvolume, the volume of the right speaker
		//													  - priority, set to 1 for future compatibility
		//													  - loop, 0 = not looped, -1 = loop infinatly, +int = loops that number of times;
		//													  - rate, the rate at whitch the sounds is to be played 
		// play the sound required.
		if(SoundID != 0 && mSndPool != null){
			return mSndPool.play(SoundID, mLeftVolume, mRightVolume, 1, 0, mRate);
		}
		return 0;
	}// end PlaySound

	// a load function that can be called to load in a file and return a value that the sounds pool will recognise
	protected int load(int soundID)
	{

		if(mSndPool == null)
		{
			mSndPool = new SoundPool(24, AudioManager.STREAM_MUSIC, 0);	
		}
		// load function takes in 3 paramiters-
		//										the application centext
		//										the id of the sound file without the file type
		//										and the priority. not in use yet set to 1 for future compatibility 
		return mSndPool.load(pContext, soundID, 1);
	}// end load

	//a function to generate a random number
	protected int randomNumber(int nMaxVal)
	{

		nMaxVal = nMaxVal + 1;  		// add 1 to max val so that the random number generated can be the max value.
		//generate a random number between 0 and a maximuim value and play the corrosponding sound
		//generate a random number......
		Random tempRand = new Random();				// used to get a variable of type Random
		int RandNum = tempRand.nextInt(nMaxVal);	//generates a random value between 0 and the maximum value

		return RandNum;
	}

	// a function to pause just the background music
	public void pauseBackGround()
	{
		mBackground.pause();	// only pause the background music
	}

	// pause the music being played
	public void pause()
	{
		mSndPool.autoPause();	// pause the sound pool
		mBackground.pause();	// pause the background music
	}

	// start the paused music to play again
	public void unpause()
	{
		mSndPool.autoResume();	//resume the sound pools sounds
		mBackground.start();	// resume the background music
		Log.w("backgroud","resumed");

	}

	// a function to clean up/ release all of the resourses
	public void cleanup()
	{
		// if the sound pool is not allready nulll...
		if(mSndPool != null)
		{
			// release the sounds pool
			mSndPool.release();
		}
		// set sound pool to null
		mSndPool = null;

		// if the background music is not already null...
		if(mBackground != null)
		{
			// release the background music from the media player
			mBackground.release();
		}
		// set the background to null.
		mBackground = null;
		
		//TODO set all sound clips id to 0
		for(int i = 0; i < 3; i++){
			mDucky[i]  = 0;
			mDucky[i+3]  = 0;
			mMissile[i] = 0;
			mExplosion[i] = 0;
		}
		mBounce[0] = 0;
		mBounce[1] = 0;
		mAngryDucky = 0;
		mScaredDucky = 0;
		mDiver = 0;	
		mFrog = 0;
		mTugBoat = 0;

		mAmbientbath = 0;
		mDownplug = 0;					// sounds to represent the noise of a plug
		mPlug = 0;
		mPoints = 0;					// sounds of points being gained
		mSplash = 0;					//  a splash of water
		mWhirlpool = 0;
		mMissileStreamId=0;
	}


	public void init() {
		mSndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
		mRate = 1.0f;				// this is the sample rate that the sounds will be played at. set to 1 to play them at the noraml rate
		mMasterVolume = 1.0f;
		mLeftVolume =mMasterVolume;			// the volume of the left speaker, set to on full
		mRightVolume = mMasterVolume;			// the volume of the right speaker, set to on full

		mMissilePlaying=false;
		if(mBackground ==null){
			mBackground = new MediaPlayer();
		}
	}




}
