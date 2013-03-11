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

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

import com.sinkingduckstudios.whirlpool.R;

public class SoundManager 
{	
	// set up variables needed for the class
	protected Context pContext;				// holds thew applications contexts, needed to load in new sounds
	protected SoundPool mSndPool;				// is the sound pool used to controll the sounds 
	protected float mRate = 1.0f;				// is the sample rate that the sounds will be played at. set to 1 to play them at the noraml rate
	protected float mLeftVolume = 1.0f;		// the volume of the left speaker, set to on full
	protected float mRightVolume = 1.0f;		// the volume of the right speaker, set to on full

	// integer values to store the ID's for the sounds needed to play them
	// the sounds for ducky
	protected int mDucky[]  = new int[6];					// an array of 6 to stor the 6 sounds of ducky
/*
	protected int mBounce[] = new int [2];					// an array of 2 to hold the sounds of ducky bouncing
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
*/

	protected float mMasterVolume;

	protected MediaPlayer mBackground = new MediaPlayer();

	// Constructor, setup the audio manager and store the app context
	public SoundManager(Context appContext){
		// the constuctor for the class.
		mSndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);		// set up the sound pool with the auidomanager
		pContext = appContext;		//get the games context 

		mRate = 1.0f;				// this is the sample rate that the sounds will be played at. set to 1 to play them at the noraml rate
		mMasterVolume = 1.0f;
		mLeftVolume =mMasterVolume;			// the volume of the left speaker, set to on full
		mRightVolume = mMasterVolume;			// the volume of the right speaker, set to on full

		loadSounds();				// load in the sound files for the game and asign them to variables 
	}//  end consturctor

	// Constructor, setup the audio manager and store the app context
	public SoundManager(){
		// the constuctor for the class.
		mSndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);		// set up the sound pool with the auidomanager

		mRate = 1.0f;				// this is the sample rate that the sounds will be played at. set to 1 to play them at the noraml rate
		mMasterVolume = 1.0f;
		mLeftVolume =mMasterVolume;			// the volume of the left speaker, set to on full
		mRightVolume = mMasterVolume;			// the volume of the right speaker, set to on full

	}//  end consturctor
	public void initContext(Context appContext){
		pContext = appContext;		//get the games context 
	}
	// a function to set all of the sound ids. use the sounds pool load function to load in a value that sounds pool will recognise
	public void loadSounds(){	
		// load in the sounds for ducky
		mDucky[0] = load(R.raw.ducky);
		mDucky[1] = load(R.raw.ducky2);
		mDucky[2] = load(R.raw.ducky3);
		mDucky[3] = load(R.raw.ducky4);
		mDucky[4] = load(R.raw.ducky5);
		mDucky[5] = load(R.raw.ducky6);
/*
		mBounce[0] = load(R.raw.bounce);
		mBounce[1] = load(R.raw.bounce2);
		mAngryDucky= load(R.raw.angryducky);
		mScaredDucky = load(R.raw.scaredducky);

		//load in the sounds for the enemies
		mDiver = load(R.raw.diver);
		mFrog = load(R.raw.frognoisemain);
		mTugBoat = load(R.raw.tugboat);

		// load in the other sounds for the game
		//mBackground = Load(R.raw.sexyandiknowit);
		mAmbientbath = load (R.raw.ambientbath);
		mDownplug = load(R.raw.downplug);
		mPlug = load(R.raw.plug);
		mPoints = load(R.raw.points);
		mSplash = load(R.raw.splash);
		mWhirlpool = load(R.raw.whirlpool2);

*/
		mBackground =MediaPlayer.create(pContext, R.raw.temp_bg_music);

	}// end LoadSounds

	// Set the volume of the sounds.
	public void setVolume(float nVol){
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
	public void increaseVolume(){
		// add 0.1 to both the left and the right speakers
		mMasterVolume += 0.1f;

		if (mMasterVolume >= 1.0f)
		{
			mMasterVolume = 1.0f;
		}

		resetVolume();
	}

	// a function to decrease the volume
	public void decreaseVolume(){
		// take away 0.1 to both the left and the right speakers
		mMasterVolume -= 0.1f;

		if (mMasterVolume <= 0.0f)
		{
			mMasterVolume = 0.0f;

		}

		resetVolume();
	}
	// set the new volumes
	public void resetVolume(){
		mLeftVolume = mMasterVolume;
		mRightVolume = mMasterVolume;
		mBackground.setVolume(mLeftVolume, mRightVolume);

		if (mMasterVolume <= 0.0f)
		{
			mSndPool.autoPause();
		}

		if (mMasterVolume > 0.0f) 
		{
			mSndPool.autoResume();
		}
	}


	// a function to play one of the ducky sounds. picked at random
	public void playDucky(){	// used to generate a random value between 0 and 5 using the varaible crated before.

		// play a sound depending on the random number generated
		// switch by the random number 
		switch (randomNumber(5)) {
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
/*
	// a function to play a ransdom bounce sound
	public void playBounce(){		  
		// play a sound depending on the random number generated
		// switch by the random number 
		switch (randomNumber(1)){
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
	public void playAngry(){
		playSound(mAngryDucky);		// play the angry ducky sound.
	}

	// a function to play the scared ducky sound
	public void playScare(){
		playSound(mScaredDucky);	// play the scared ducky sound.
	}

	// a function to play the diver's sound
	public void playDiver(){
		playSound(mDiver); 			// play the diver's sound
	}

	// a function to play the frod'g sound
	public void playFrog(){
		playSound(mFrog);			// play the Frog's sound
		//mSndPool.play(mFrog, mLeftVolume, mRightVolume, 1, 0, mRate);	
		//mSndPool.setLoop(1,-1);
	}

	// a function to play the tugBoat's sound
	public void playTugBoat(){
		playSound(mTugBoat);		// play the tugboats's sound
	}
*/
	// a function to play the background music looped
	public void playBackground(){
		//BackgroundStreamID = PlayLoopedSound(mBackground, -1);
		mBackground.setLooping(true);
		mBackground.start();
	}
/*
	// a function to play the ambient noise 
	public void playAmbient(){
		playSound(mAmbientbath);
	}

	// a function to play the down plug sound#
	public void playDownPlug(){
		playSound(mDownplug);
	}

	// a function to play the plug sound
	public void playPlug(){
		playSound(mPlug);
	}

	// a function to play the points sound
	public void playPoints(){
		playSound(mPoints);
	}

	// a function to play the splash sound
	public void playSplash(){
		playSound(mSplash);
	}
	// a function to play the whirlpool sound
	public void playWhirlpool(){
		playSound(mWhirlpool);
	}
 */

	// Free ALL the sounds
	public void unloadAll(){
		// release the sound pool.
		mSndPool.release();	
		mSndPool = null;
	}

	// a function to play a single sound based on the id plassed in

	//a function to play a single sound based on a id passed in
	protected void playSound(int SoundID){
		// the sound pool play function takes in 6 parameters - soundID, the id of the sound the user wants to play 
		//													  - leftvolume, the volume fo teh left speaker
		//													  - rightvolume, the volume of the right speaker
		//													  - priority, set to 1 for future compatibility
		//													  - loop, 0 = not looped, -1 = loop infinatly, +int = loops that number of times;
		//													  - rate, the rate at whitch the sounds is to be played 
		// play the sound required.
		mSndPool.play(SoundID, mLeftVolume, mRightVolume, 1, 0, mRate);
	}// end PlaySound

	// a function to play a looped sound based on the ID passed in
	protected int playLoopedSound(int SoundID, int LoopVal){
		// play a sound based on the sound ID pssed in and loop it the number of times asked ( if -1 loop infinatly)
		//mSndPool.play(SoundID, mLeftVolume, mRightVolume, 1, LoopVal, mRate);
		return mSndPool.play(SoundID, mLeftVolume, mRightVolume, 1, LoopVal, mRate);
	}


	// a load function that can be called to load in a file and return a value that the sounds pool will recognise
	protected int load(int soundID){
		if(mSndPool == null){
			mSndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);	
		}
		// load function takes in 3 paramiters-
		//										the application centext
		//										the id of the sound file without the file type
		//										and the priority. not in use yet set to 1 for future compatibility 
		return mSndPool.load(pContext, soundID, 1);
	}// end load


	//a function to generate a random number
	protected int randomNumber(int nMaxVal){

		nMaxVal = nMaxVal + 1;  		// add 1 to max val so that the random number generated can be the max value.
		//generate a random number between 0 and a maximuim value and play the corrosponding sound
		//generate a random number......
		Random tempRand = new Random();				// used to get a variable of type Random
		int RandNum = tempRand.nextInt(nMaxVal);	//generates a random value between 0 and the maximum value

		return RandNum;
	}

	public void pause(){
		mSndPool.autoPause();
		mBackground.pause();
	}

	public void unpause(){
		mSndPool.autoResume();
		mBackground.start();
	}

	public void cleanup(){
		if(mSndPool != null){
			mSndPool.release();
		}
		mSndPool = null;
		if(mBackground != null){
			mBackground.release();
		}
		mBackground = null;
	}


}
