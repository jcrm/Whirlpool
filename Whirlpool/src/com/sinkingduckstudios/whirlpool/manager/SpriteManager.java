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

import com.sinkingduckstudios.whirlpool.R;
import com.sinkingduckstudios.whirlpool.logic.Constants;

public class SpriteManager {
	private static Bitmap mCinematic[]=new Bitmap[6];
	private static Bitmap mDuck[]=new Bitmap[3];
	private static Bitmap mDiver;
	private static Bitmap mDiverUp;
	private static Bitmap mDiverDown;
	private static Bitmap mBoat;
	private static Bitmap mBoatAttack;
	private static Bitmap mFrog;
	private static Bitmap mWhirlpool;
	private static Bitmap mBackground;
	private static Bitmap mTorpedo;
	private static Bitmap mTorpedoExplosion;
	private static Bitmap mLeftBorder;
	private static Bitmap mRightBorder;
	private static Bitmap mTopBorder;
	private static Bitmap mArrow;
	private static Bitmap mFinish;
	private static Bitmap mFinishHit;
	private static Bitmap mShark;
	private static Bitmap mSharkUp;
	private static Bitmap mSharkDown;
	private static Bitmap mSharkSleep;
	private static Bitmap mSharkAttack;
	private static Bitmap mEmptyStar;
	private static Bitmap mFullStar;
	private static Bitmap mDestroyBoat;
	
	public void deleteImages(){
		mDuck[0].recycle();
		mDuck[0] = null;
		mDuck[1].recycle();
		mDuck[1] = null;
		mDuck[2].recycle();
		mDuck[2] = null;
		mDiver.recycle();
		mDiver = null;
		mDiverUp.recycle();
		mDiverUp = null;
		mDiverDown.recycle();
		mDiverDown = null;
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
		mTorpedoExplosion.recycle();
		mTorpedoExplosion = null;
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
		mSharkUp.recycle();
		mSharkUp= null;
		mSharkDown.recycle();
		mSharkDown= null;
		mSharkSleep.recycle();
		mSharkSleep= null;
		mSharkAttack.recycle();
		mSharkAttack= null;
		mEmptyStar.recycle();
		mEmptyStar = null;
		mFullStar.recycle();
		mFullStar = null;
		mDestroyBoat.recycle();
		mDestroyBoat = null;
	}
	public static Bitmap getDuck(int index) {
		if(index<0||index>2)return null;
		if(mDuck[index]==null || mDuck[index].isRecycled()){
			mDuck[0] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_left_and_right_sprites);
			mDuck[1] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_up_sprites);
			mDuck[2] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_down_sprites);
		}
		return mDuck[index];
	}
	public static Bitmap getCinematic(int index) {
		if(index<0||index>5)return null;
		if(mCinematic[index]==null || mCinematic[index].isRecycled()){
			mCinematic[0] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_1);
			mCinematic[1] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_2);
			mCinematic[2] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_3);
			mCinematic[3] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_4);
			mCinematic[4] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_5);
			mCinematic[5] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_6);
		}
		return mCinematic[index];
	}
	public static void setDuck(int index, Bitmap duck) {
		mDuck[index] = duck;
	}
	public static Bitmap getDiver() {
		if(mDiver == null || mDiver.isRecycled()){
			mDiver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_left_and_right_sprites);
		}
		return mDiver;
	}
	public static void setDiver(Bitmap diver) {
		mDiver = diver;
	}
	public static Bitmap getBoat() {
		if(mBoat==null || mBoat.isRecycled()){
			mBoat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_sprites);
		}
		return mBoat;
	}
	public static void setBoat(Bitmap boat) {
		mBoat = boat;
	}
	public static Bitmap getFrog() {
		if(mFrog==null || mFrog.isRecycled()){
			mFrog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog_sprites);
		}
		return mFrog;
	}
	public static void setFrog(Bitmap frog) {
		mFrog = frog;
	}
	public static Bitmap getWhirlpool() {
		if(mWhirlpool==null || mWhirlpool.isRecycled()){
			mWhirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool_sprites);
		}
		return mWhirlpool;
	}
	public static void setWhirlpool(Bitmap whirlpool) {
		mWhirlpool = whirlpool;
	}
	public static Bitmap getBackground() {
		if(mBackground==null || mBackground.isRecycled()){
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
		if(mTorpedo==null || mTorpedo.isRecycled()){
			mTorpedo =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_sprites);
		}
		return mTorpedo;
	}
	public static void setTorpedo(Bitmap torpedo) {
		mTorpedo = torpedo;
	}
	public static Bitmap getBoatAttack() {
		if(mBoatAttack==null || mBoatAttack.isRecycled()){
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
		if(mLeftBorder==null || mLeftBorder.isRecycled()){
			mLeftBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.left_border);
		}
		return mLeftBorder;
	}
	public static void setLeftBorder(Bitmap leftBorder) {
		mLeftBorder = leftBorder;
	}
	public static Bitmap getRightBorder() {
		if(mRightBorder==null || mRightBorder.isRecycled()){
			mRightBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.right_border);
		}
		return mRightBorder;
	}
	public static void setRightBorder(Bitmap rightBorder) {
		mRightBorder = rightBorder;
	}
	public static Bitmap getTopBorder() {
		if(mTopBorder==null || mTopBorder.isRecycled()){
			mTopBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.top_border_tiling);
		}
		return mTopBorder;
	}
	public static void setTopBorder(Bitmap topBorder) {
		mTopBorder = topBorder;
	}
	public static Bitmap getArrow() {
		if(mArrow==null || mArrow.isRecycled()){
			mArrow=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.arrow_sprites);
		}
		return mArrow;
	}
	public static Bitmap getFinish() {
		if(mFinish==null || mFinish.isRecycled()){
			mFinish=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.end_point_sprites);
		}
		return mFinish;
	}
	public static void setFinish(Bitmap finish) {
		mFinish = finish;
	}
	public static Bitmap getFinishHit() {
		if(mFinishHit==null || mFinishHit.isRecycled()){
			mFinishHit=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.end_point_hit_sprites);
		}
		return mFinishHit;
	}
	public static void setFinishHit(Bitmap finishHit) {
		mFinishHit = finishHit;
	}
	public static Bitmap getShark() {
		if(mShark==null || mShark.isRecycled()){
			mShark=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_left_and_right_sprites);
		}
		return mShark;
	}
	public static void setShark(Bitmap shark) {
		mShark = shark;
	}
	public static Bitmap getDiverUp(){
		if(mDiverUp == null || mDiverUp.isRecycled()){
			mDiverUp = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_up_sprites);
		}
		return mDiverUp;
	}
	public static void setDiverUp(Bitmap diver) {
		mDiverUp = diver;
	}
	public static Bitmap getDiverDown(){
		if(mDiverDown == null || mDiverDown.isRecycled()){
			mDiverDown = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_down_sprites);
		}
		return mDiverDown;
	}
	public static void setDiverDown(Bitmap diver) {
		mDiverDown = diver;
	}
	public static Bitmap getSharkUp() {
		if(mSharkUp==null || mSharkUp.isRecycled()){
			mSharkUp=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_up_sprites);
		}
		return mSharkUp;
	}
	public static void setSharkUp(Bitmap shark) {
		mSharkUp = shark;
	}
	public static Bitmap getSharkDown() {
		if(mSharkDown==null || mSharkDown.isRecycled()){
			mSharkDown=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_down_sprites);
		}
		return mSharkDown;
	}
	public static void setSharkDown(Bitmap shark) {
		mSharkDown = shark;
	}
	public static Bitmap getSharkAsleep() {
		if(mSharkSleep==null || mSharkSleep.isRecycled()){
			mSharkSleep=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_sleeping_sprites);
		}
		return mSharkSleep;
	}
	public static void setSharkAsleep(Bitmap shark) {
		mSharkSleep = shark;
	}
	public static Bitmap getSharkAttack() {
		if(mSharkAttack==null || mSharkAttack.isRecycled()){
			mSharkAttack=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_bite_sprites);
		}
		return mSharkAttack;
	}
	public static void setSharkAttack(Bitmap shark) {
		mSharkAttack = shark;
	}
	public static Bitmap getTorpedoExplosion() {
		if(mTorpedoExplosion==null || mTorpedoExplosion.isRecycled()){
			mTorpedoExplosion =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_explosion_sprites);
		}
		return mTorpedoExplosion;
	}
	public static void setTorpedoExplosion(Bitmap torpedo) {
		mTorpedoExplosion = torpedo;
	}
	public static Bitmap getEmptyStar(){
		if(mEmptyStar==null || mEmptyStar.isRecycled()){
			mEmptyStar = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.star_empty);
		}
		return mEmptyStar;
	}
	public static Bitmap getFullStar(){
		if(mFullStar==null || mFullStar.isRecycled()){
			mFullStar = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.star_full);
		}
		return mFullStar;
	}
	public static Bitmap getDestroyBoat(){
		if(mDestroyBoat==null || mDestroyBoat.isRecycled()){
			mDestroyBoat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_damaged_sprites);
		}
		return mDestroyBoat;
	}
}