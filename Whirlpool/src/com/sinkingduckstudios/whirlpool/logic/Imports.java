/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.sinkingduckstudios.whirlpool.R;

public class Imports {
	private static Bitmap mDuck;
	private static Bitmap mDiver;
	private static Bitmap mDiverFlipped;
	private static Bitmap mBoat;
	private static Bitmap mFrog;
	private static Bitmap mWhirlpool;
	private static Bitmap mBackground;
	private static Bitmap mTorpedo;
	
	static public void setImages(){
		if(mBackground==null){
			mBackground  = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.wateroffset_background);
		}
	}
	public void deleteImages(){
		mDuck.recycle();
		mDuck = null;
		mDiver.recycle();
		mDiver = null;
		mDiverFlipped.recycle();
		mDiverFlipped = null;
		mBoat.recycle();
		mBoat = null;
		mFrog.recycle();
		mFrog = null;
		mWhirlpool.recycle();
		mWhirlpool = null;
		mBackground.recycle();
		mBackground = null;
		mTorpedo.recycle();
		mTorpedo = null;
	}
	public static boolean scaledBitmap(int type, int newWidth, int newHeight){
		switch(type){
		case 0:
			return false;
		case 1:
			if(mWhirlpool==null){
				mWhirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool_sprites);
			}
			if(mWhirlpool.getWidth()!= newWidth && mWhirlpool.getHeight()!=newHeight){
				mWhirlpool = Bitmap.createScaledBitmap(mWhirlpool, newWidth, newHeight, false);
				return true;
			}
			break;
		case 2:
			if(mDuck==null){
				mDuck = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_left_and_right_sprites);
			}
			if(mDuck.getWidth()!= newWidth && mDuck.getHeight()!=newHeight){
				mDuck = Bitmap.createScaledBitmap(mDuck, newWidth, newHeight, false);
				return true;
			}
			break;
		case 3:
			if(mFrog==null){
				mFrog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog_sprites);
			}
			if(mFrog.getWidth()!= newWidth && mFrog.getHeight()!=newHeight){
				mFrog = Bitmap.createScaledBitmap(mFrog, newWidth, newHeight, false);
				return true;
			}
			break;
		case 5:
			if(mBoat==null){
				mBoat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_sprites);
			}
			if(mBoat.getWidth()!= newWidth && mBoat.getHeight()!=newHeight){
				mBoat = Bitmap.createScaledBitmap(mBoat, newWidth, newHeight, false);
				return true;
			}
			break;
		case 6:
			if(mDiver == null){
				Matrix flipMatrix = new Matrix();
				flipMatrix.setScale(1, -1);
				mDiver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_sprites);
				mDiverFlipped = Bitmap.createBitmap(mDiver, 0, 0, mDiver.getWidth(), mDiver.getHeight(), flipMatrix, false);
			}
			if(mDiver.getWidth()!= newWidth && mDiver.getHeight()!=newHeight){
				mDiver = Bitmap.createScaledBitmap(mDiver, newWidth, newHeight, false);
				mDiverFlipped = Bitmap.createScaledBitmap(mDiverFlipped, newWidth, newHeight, false);
				return true;
			}
			break;
		case 7:
			if(mTorpedo==null){
				mTorpedo =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_sprites);
			}
			if(mTorpedo.getWidth()!= newWidth && mTorpedo.getHeight()!=newHeight){
				mTorpedo = Bitmap.createScaledBitmap(mTorpedo, newWidth, newHeight, false);
				return true;
			}
			break;
		default:
			return false;
		}
		return false;
	}
	public static Bitmap getDuck() {
		return mDuck;
	}
	public static void setDuck(Bitmap duck) {
		mDuck = duck;
	}
	public static Bitmap getDiver() {
		return mDiver;
	}
	public static void setDiver(Bitmap diver) {
		mDiver = diver;
	}
	public static Bitmap getDiverFlipped() {
		return mDiverFlipped;
	}
	public static void setDiverFlipped(Bitmap diverflipped) {
		mDiverFlipped = diverflipped;
	}
	public static Bitmap getBoat() {
		return mBoat;
	}
	public static void setBoat(Bitmap boat) {
		mBoat = boat;
	}
	public static Bitmap getFrog() {
		return mFrog;
	}
	public static void setFrog(Bitmap frog) {
		mFrog = frog;
	}
	public static Bitmap getWhirlpool() {
		return mWhirlpool;
	}
	public static void setWhirlpool(Bitmap whirlpool) {
		mWhirlpool = whirlpool;
	}
	public static Bitmap getBackground() {
		return mBackground;
	}
	public static void setBackground(Bitmap background) {
		mBackground = background;
	}
	public static Bitmap getTorpedo() {
		return mTorpedo;
	}
	public static void setTorpedo(Bitmap torpedo) {
		mTorpedo = torpedo;
	}
}