/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;

public class SpriteManager {
	private static Bitmap mDuck[]=new Bitmap[3];
	private static Bitmap mDiver;
	private static Bitmap mDiverFlipped;
	private static Bitmap mBoat;
	private static Bitmap mBoatAttack;
	private static Bitmap mFrog;
	private static Bitmap mWhirlpool;
	private static Bitmap mBackground;
	private static Bitmap mTorpedo;
	private static Bitmap mLeftBorder;
	private static Bitmap mRightBorder;
	private static Bitmap mTopBorder;
	private static Bitmap mArrow;
	private static Bitmap mFinish;
	private static Bitmap mFinishHit;
	private static Bitmap mShark;
	
	public void deleteImages(){
		mDuck[0].recycle();
		mDuck[0] = null;
		mDuck[1].recycle();
		mDuck[1] = null;
		mDuck[2].recycle();
		mDuck[2] = null;
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
		mLeftBorder.recycle();
		mLeftBorder =null;
		mRightBorder.recycle();
		mRightBorder=null;
		mTopBorder.recycle();
		mTopBorder =null;
		mArrow.recycle();
		mArrow = null;
		mFinish.recycle();
		mFinish = null;
		mShark.recycle();
		mShark = null;
	}
	public static Bitmap getDuck(int index) {
		if(index<0||index>2)return null;
		if(mDuck[index]==null){
			mDuck[0] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_left_and_right_sprites);
			mDuck[1] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_up_sprites);
			mDuck[2] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_down_sprites);
		}
		return mDuck[index];
	}
	public static void setDuck(int index, Bitmap duck) {
		mDuck[index] = duck;
	}
	public static Bitmap getDiver() {
		if(mDiver == null){
			Matrix flipMatrix = new Matrix();
			flipMatrix.setScale(1, -1);
			mDiver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_sprites);
			mDiverFlipped = Bitmap.createBitmap(mDiver, 0, 0, mDiver.getWidth(), mDiver.getHeight(), flipMatrix, false);
		}
		return mDiver;
	}
	public static void setDiver(Bitmap diver) {
		mDiver = diver;
	}
	public static Bitmap getDiverFlipped() {
		if(mDiverFlipped == null){
			Matrix flipMatrix = new Matrix();
			flipMatrix.setScale(1, -1);
			mDiver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_sprites);
			mDiverFlipped = Bitmap.createBitmap(mDiver, 0, 0, mDiver.getWidth(), mDiver.getHeight(), flipMatrix, false);
		}
		return mDiverFlipped;
	}
	public static void setDiverFlipped(Bitmap diverflipped) {
		mDiverFlipped = diverflipped;
	}
	public static Bitmap getBoat() {
		if(mBoat==null){
			mBoat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_sprites);
		}
		return mBoat;
	}
	public static void setBoat(Bitmap boat) {
		mBoat = boat;
	}
	public static Bitmap getFrog() {
		if(mFrog==null){
			mFrog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog_sprites);
		}
		return mFrog;
	}
	public static void setFrog(Bitmap frog) {
		mFrog = frog;
	}
	public static Bitmap getWhirlpool() {
		if(mWhirlpool==null){
			mWhirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool_sprites);
		}
		return mWhirlpool;
	}
	public static void setWhirlpool(Bitmap whirlpool) {
		mWhirlpool = whirlpool;
	}
	public static Bitmap getBackground() {
		if(mBackground==null){
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(Constants.getRes(), R.drawable.mainmenu_background, opt);
			opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
			opt.inJustDecodeBounds = false;
			mBackground  = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.wateroffset_background);
		}
		return mBackground;
	}
	public static void setBackground(Bitmap background) {
		mBackground = background;
	}
	public static Bitmap getTorpedo() {
		if(mTorpedo==null){
			mTorpedo =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_sprites);
		}
		return mTorpedo;
	}
	public static void setTorpedo(Bitmap torpedo) {
		mTorpedo = torpedo;
	}
	public static Bitmap getBoatAttack() {
		if(mBoatAttack==null){
			mBoatAttack =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_attack_sprites);
		}
		return mBoatAttack;
	}
	public static void setBoatAttack(Bitmap boatAttack) {
		mBoatAttack = boatAttack;
	}
	public static int getScale(int oW, int oH, int nW, int nH){
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
	public static Bitmap getLeftBorder() {
		if(mLeftBorder==null){
			mLeftBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.left_border);
		}
		return mLeftBorder;
	}
	public static void setLeftBorder(Bitmap leftBorder) {
		mLeftBorder = leftBorder;
	}
	public static Bitmap getRightBorder() {
		if(mRightBorder==null){
			mRightBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.right_border);
		}
		return mRightBorder;
	}
	public static void setRightBorder(Bitmap rightBorder) {
		mRightBorder = rightBorder;
	}
	public static Bitmap getTopBorder() {
		if(mTopBorder==null){
			mTopBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.top_border_tiling);
		}
		return mTopBorder;
	}
	public static void setTopBorder(Bitmap topBorder) {
		mTopBorder = topBorder;
	}
	public static Bitmap getArrow() {
		if(mArrow==null){
			mArrow=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.arrow_sprites);
		}
		return mArrow;
	}
	public static Bitmap getFinish() {
		if(mFinish==null){
			mFinish=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.end_point);
		}
		return mFinish;
	}
	public static void setFinish(Bitmap finish) {
		mFinish = finish;
	}
	public static Bitmap getFinishHit() {
		if(mFinishHit==null){
			mFinishHit=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.endpoint_hit);
		}
		return mFinishHit;
	}
	public static void setFinishHit(Bitmap finishHit) {
		mFinishHit = finishHit;
	}
	public static Bitmap getShark() {
		if(mShark==null){
			mShark=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_sprites);
		}
		return mShark;
	}
	public static void setShark(Bitmap shark) {
		mShark = shark;
	}
}