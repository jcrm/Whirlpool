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
	private static Bitmap mDestroyBoat;

	private static Bitmap mWhirlpool;
	private static Bitmap mArrow;
	private static Bitmap mFinish;
	private static Bitmap mFinishHit;

	private static Bitmap mTorpedo;
	private static Bitmap mTorpedoExplosion;

	private static Bitmap mLeftBorder;
	private static Bitmap mRightBorder;
	private static Bitmap mTopBorder;	

	private static Bitmap mShark;
	private static Bitmap mSharkUp;
	private static Bitmap mSharkDown;
	private static Bitmap mSharkSleep;
	private static Bitmap mSharkAttack;

	private static Bitmap mEmptyStar;
	private static Bitmap mFullStar;

	private static Bitmap mFrog;
	private static Bitmap mBackground;

	public void deleteAllImages(){
		unloadDuck();
		unloadShark();
		unloadDiver();
		unloadWhirlpool();
		unloadBorder();
		unloadBoat();
		unloadFrog();
		unloadBackground();
		unloadTorpedo();
		unloadStar();
		unloadCinematic();
	}
	public static void unloadFrog(){
		clean(mFrog);
	}
	public static void unloadCinematic(){
		for(int i = 0; i<6; i++){
			clean(mCinematic[i]);
		}
	}
	public static void unloadBackground(){
		clean(mBackground);
	}
	public static void unloadTorpedo(){
		clean(mTorpedo);
		clean(mTorpedoExplosion);
	}
	public static void unloadStar(){
		clean(mEmptyStar);
		clean(mFullStar);
	}
	public static void unloadBoat(){
		clean(mBoat);
		clean(mBoatAttack);
		clean(mDestroyBoat);
	}
	public static void unloadBorder(){
		clean(mLeftBorder);
		clean(mRightBorder);
		clean(mTopBorder);
	}
	public static void unloadDuck(){
		for(int i = 0; i<3; i++){
			clean(mDuck[i]);
		}
	}
	public static void unloadWhirlpool(){
		clean(mWhirlpool);
		clean(mFinish);
		clean(mFinishHit);
		clean(mArrow);
	}
	public static void unloadDiver(){
		clean(mDiver);
		clean(mDiverUp);
		clean(mDiverDown);
	}
	public static void unloadShark(){
		clean(mShark);
		clean(mSharkUp);
		clean(mSharkDown);
		clean(mSharkSleep);
		clean(mSharkAttack);
	}
	private static void clean(Bitmap bitmap){
		if(bitmap != null){
			bitmap.recycle();
			bitmap = null;
		}
	}
	private static boolean empty(Bitmap bitmap){
		if(bitmap==null || bitmap.isRecycled()){
			return true;
		}
		return false;
	}
	public static Bitmap getDuck(int index) {
		if(index<0||index>2)return null;
		if(empty(mDuck[index])){
			mDuck[0] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_left_and_right_sprites);
			mDuck[1] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_up_sprites);
			mDuck[2] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_down_sprites);			
		}
		return mDuck[index];
	}
	public static Bitmap getCinematic(int index) {
		if(index<0||index>5)return null;
		if(empty(mCinematic[index])){
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_1, opt);
			opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
			opt.inJustDecodeBounds = false;
			mCinematic[0] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_1, opt);
			mCinematic[1] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_2, opt);
			mCinematic[2] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_3, opt);
			mCinematic[3] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_4, opt);
			mCinematic[4] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_5, opt);
			mCinematic[5] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.cinematic_6, opt);
		}
		return mCinematic[index];
	}
	public static Bitmap getDiver() {
		if(empty(mDiver)){
			mDiver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_left_and_right_sprites);
		}
		return mDiver;
	}
	public static Bitmap getBoat() {
		if(empty(mBoat)){
			mBoat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_sprites);
		}
		return mBoat;
	}
	public static Bitmap getFrog() {
		if(empty(mFrog)){
			mFrog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog_sprites);
		}
		return mFrog;
	}
	public static Bitmap getWhirlpool() {
		if(empty(mWhirlpool)){
			mWhirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool_sprites);
		}
		return mWhirlpool;
	}
	public static Bitmap getBackground() {
		if(empty(mBackground)){
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(Constants.getRes(), R.drawable.mainmenu_background, opt);
			opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
			opt.inJustDecodeBounds = false;
			mBackground  = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.wateroffset_background, opt);
		}
		return mBackground;
	}
	public static Bitmap getTorpedo() {
		if(empty(mTorpedo)){
			mTorpedo =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_sprites);
		}
		return mTorpedo;
	}
	public static Bitmap getBoatAttack() {
		if(empty(mBoatAttack)){
			mBoatAttack =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_attack_sprites);
		}
		return mBoatAttack;
	}
	public static Bitmap getLeftBorder() {
		if(empty(mLeftBorder)){
			mLeftBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.left_border);
		}
		return mLeftBorder;
	}
	public static Bitmap getRightBorder() {
		if(empty(mRightBorder)){
			mRightBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.right_border);
		}
		return mRightBorder;
	}
	public static Bitmap getTopBorder() {
		if(empty(mTopBorder)){
			mTopBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.top_border_tiling);
		}
		return mTopBorder;
	}
	public static Bitmap getArrow() {
		if(empty(mArrow)){
			mArrow=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.arrow_sprites);
		}
		return mArrow;
	}
	public static Bitmap getFinish() {
		if(empty(mFinish)){
			mFinish=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.end_point_sprites);
		}
		return mFinish;
	}
	public static Bitmap getFinishHit() {
		if(empty(mFinishHit)){
			mFinishHit=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.end_point_hit_sprites);
		}
		return mFinishHit;
	}
	public static Bitmap getShark() {
		if(empty(mShark)){
			mShark=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_left_and_right_sprites);
		}
		return mShark;
	}
	public static Bitmap getDiverUp(){
		if(empty(mDiverUp)){
			mDiverUp = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_up_sprites);
		}
		return mDiverUp;
	}
	public static Bitmap getDiverDown(){
		if(empty(mDiverDown)){
			mDiverDown = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_down_sprites);
		}
		return mDiverDown;
	}
	public static Bitmap getSharkUp() {
		if(empty(mSharkUp)){
			mSharkUp=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_up_sprites);
		}
		return mSharkUp;
	}
	public static Bitmap getSharkDown() {
		if(empty(mSharkDown)){
			mSharkDown=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_down_sprites);
		}
		return mSharkDown;
	}
	public static Bitmap getSharkAsleep() {
		if(empty(mSharkSleep)){
			mSharkSleep=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_sleeping_sprites);
		}
		return mSharkSleep;
	}
	public static Bitmap getSharkAttack() {
		if(empty(mSharkAttack)){
			mSharkAttack=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_bite_sprites);
		}
		return mSharkAttack;
	}
	public static Bitmap getTorpedoExplosion() {
		if(empty(mTorpedoExplosion)){
			mTorpedoExplosion =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_explosion_sprites);
		}
		return mTorpedoExplosion;
	}
	public static Bitmap getEmptyStar(){
		if(empty(mEmptyStar)){
			mEmptyStar = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.star_empty);
		}
		return mEmptyStar;
	}
	public static Bitmap getFullStar(){
		if(empty(mFullStar)){
			mFullStar = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.star_full);
		}
		return mFullStar;
	}
	public static Bitmap getDestroyBoat(){
		if(empty(mDestroyBoat)){
			mDestroyBoat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_damaged_sprites);
		}
		return mDestroyBoat;
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
}