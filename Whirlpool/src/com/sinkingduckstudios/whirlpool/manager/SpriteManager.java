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

// TODO: Auto-generated Javadoc
/**
 * The Class SpriteManager.
 */
public class SpriteManager {
	
	/** The m cinematic. */
	private static Bitmap mCinematic[]=new Bitmap[6];
	
	/** The m tutorial. */
	private static Bitmap mTutorial[]=new Bitmap[4];
	
	/** The m duck. */
	private static Bitmap mDuck[]=new Bitmap[3];

	/** The m diver. */
	private static Bitmap mDiver;
	
	/** The m diver up. */
	private static Bitmap mDiverUp;
	
	/** The m diver down. */
	private static Bitmap mDiverDown;

	/** The m boat. */
	private static Bitmap mBoat;
	
	/** The m boat attack. */
	private static Bitmap mBoatAttack;
	
	/** The m destroy boat. */
	private static Bitmap mDestroyBoat;

	/** The m whirlpool. */
	private static Bitmap mWhirlpool;
	
	/** The m arrow. */
	private static Bitmap mArrow;
	
	/** The m finish. */
	private static Bitmap mFinish;
	
	/** The m finish hit. */
	private static Bitmap mFinishHit;
	
	/** The m plug. */
	private static Bitmap mPlug;

	/** The m torpedo. */
	private static Bitmap mTorpedo;
	
	/** The m torpedo explosion. */
	private static Bitmap mTorpedoExplosion;

	/** The m left border. */
	private static Bitmap mLeftBorder;
	
	/** The m right border. */
	private static Bitmap mRightBorder;
	
	/** The m top border. */
	private static Bitmap mTopBorder;	

	/** The m shark. */
	private static Bitmap mShark;
	
	/** The m shark up. */
	private static Bitmap mSharkUp;
	
	/** The m shark down. */
	private static Bitmap mSharkDown;
	
	/** The m shark sleep. */
	private static Bitmap mSharkSleep;
	
	/** The m shark attack. */
	private static Bitmap mSharkAttack;

	/** The m empty star. */
	private static Bitmap mEmptyStar;
	
	/** The m full star. */
	private static Bitmap mFullStar;

	/** The m frog. */
	private static Bitmap mFrog;
	
	/** The m background. */
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
		unloadTutorial();
	}
	public static void unloadFrog(){
		clean(mFrog);
	}
	public static void unloadCinematic(){
		for(int i = 0; i<6; i++){
			clean(mCinematic[i]);
		}
	}
	public static void unloadTutorial(){
		for(int i = 0; i<4; i++){
			clean(mTutorial[i]);
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
		clean(mPlug);
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
	public static Bitmap getTutorial(int index) {
		if(index<0||index>3)return null;
		if(empty(mTutorial[index])){
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeResource(Constants.getRes(), R.drawable.tutorial_1, opt);
			opt.inSampleSize = getScale(opt.outWidth,opt.outWidth, Constants.getScreen().getWidth(), Constants.getScreen().getHeight());
			opt.inJustDecodeBounds = false;
			mTutorial[0] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.tutorial_1, opt);
			mTutorial[1] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.tutorial_2, opt);
			mTutorial[2] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.tutorial_3, opt);
			mTutorial[3] = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.tutorial_4, opt);
		}
		return mTutorial[index];
	}
	
	/**
	 * Gets the diver.
	 *
	 * @return the diver
	 */
	public static Bitmap getDiver() {
		if(empty(mDiver)){
			mDiver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_left_and_right_sprites);
		}
		return mDiver;
	}
	
	/**
	 * Gets the boat.
	 *
	 * @return the boat
	 */
	public static Bitmap getBoat() {
		if(empty(mBoat)){
			mBoat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_sprites);
		}
		return mBoat;
	}
	
	/**
	 * Gets the frog.
	 *
	 * @return the frog
	 */
	public static Bitmap getFrog() {
		if(empty(mFrog)){
			mFrog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog_sprites);
		}
		return mFrog;
	}
	
	/**
	 * Gets the whirlpool.
	 *
	 * @return the whirlpool
	 */
	public static Bitmap getWhirlpool() {
		if(empty(mWhirlpool)){
			mWhirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool_sprites);
		}
		return mWhirlpool;
	}
	
	/**
	 * Gets the background.
	 *
	 * @return the background
	 */
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
	
	/**
	 * Gets the torpedo.
	 *
	 * @return the torpedo
	 */
	public static Bitmap getTorpedo() {
		if(empty(mTorpedo)){
			mTorpedo =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_sprites);
		}
		return mTorpedo;
	}
	
	/**
	 * Gets the boat attack.
	 *
	 * @return the boat attack
	 */
	public static Bitmap getBoatAttack() {
		if(empty(mBoatAttack)){
			mBoatAttack =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat_attack_sprites);
		}
		return mBoatAttack;
	}
	
	/**
	 * Gets the left border.
	 *
	 * @return the left border
	 */
	public static Bitmap getLeftBorder() {
		if(empty(mLeftBorder)){
			mLeftBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.left_border);
		}
		return mLeftBorder;
	}
	
	/**
	 * Gets the right border.
	 *
	 * @return the right border
	 */
	public static Bitmap getRightBorder() {
		if(empty(mRightBorder)){
			mRightBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.right_border);
		}
		return mRightBorder;
	}
	
	/**
	 * Gets the top border.
	 *
	 * @return the top border
	 */
	public static Bitmap getTopBorder() {
		if(empty(mTopBorder)){
			mTopBorder=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.top_border_tiling);
		}
		return mTopBorder;
	}
	
	/**
	 * Gets the arrow.
	 *
	 * @return the arrow
	 */
	public static Bitmap getArrow() {
		if(empty(mArrow)){
			mArrow=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.arrow_sprites);
		}
		return mArrow;
	}
	
	/**
	 * Gets the finish.
	 *
	 * @return the finish
	 */
	public static Bitmap getFinish() {
		if(empty(mFinish)){
			mFinish=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.end_point_sprites);
		}
		return mFinish;
	}
	
	/**
	 * Gets the finish hit.
	 *
	 * @return the finish hit
	 */
	public static Bitmap getFinishHit() {
		if(empty(mFinishHit)){
			mFinishHit=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.end_point_hit_sprites);
		}
		return mFinishHit;
	}
	
	/**
	 * Gets the plug.
	 *
	 * @return the plug
	 */
	public static Bitmap getPlug(){
		if(empty(mPlug)){
			mPlug =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.plug_sprite);
		}
		return mPlug;
	}
	
	/**
	 * Gets the shark.
	 *
	 * @return the shark
	 */
	public static Bitmap getShark() {
		if(empty(mShark)){
			mShark=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_left_and_right_sprites);
		}
		return mShark;
	}
	
	/**
	 * Gets the diver up.
	 *
	 * @return the diver up
	 */
	public static Bitmap getDiverUp(){
		if(empty(mDiverUp)){
			mDiverUp = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_up_sprites);
		}
		return mDiverUp;
	}
	
	/**
	 * Gets the diver down.
	 *
	 * @return the diver down
	 */
	public static Bitmap getDiverDown(){
		if(empty(mDiverDown)){
			mDiverDown = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_down_sprites);
		}
		return mDiverDown;
	}
	
	/**
	 * Gets the shark up.
	 *
	 * @return the shark up
	 */
	public static Bitmap getSharkUp() {
		if(empty(mSharkUp)){
			mSharkUp=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_up_sprites);
		}
		return mSharkUp;
	}
	
	/**
	 * Gets the shark down.
	 *
	 * @return the shark down
	 */
	public static Bitmap getSharkDown() {
		if(empty(mSharkDown)){
			mSharkDown=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_down_sprites);
		}
		return mSharkDown;
	}
	
	/**
	 * Gets the shark asleep.
	 *
	 * @return the shark asleep
	 */
	public static Bitmap getSharkAsleep() {
		if(empty(mSharkSleep)){
			mSharkSleep=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_sleeping_sprites);
		}
		return mSharkSleep;
	}
	
	/**
	 * Gets the shark attack.
	 *
	 * @return the shark attack
	 */
	public static Bitmap getSharkAttack() {
		if(empty(mSharkAttack)){
			mSharkAttack=  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark_bite_sprites);
		}
		return mSharkAttack;
	}
	
	/**
	 * Gets the torpedo explosion.
	 *
	 * @return the torpedo explosion
	 */
	public static Bitmap getTorpedoExplosion() {
		if(empty(mTorpedoExplosion)){
			mTorpedoExplosion =  BitmapFactory.decodeResource(Constants.getRes(), R.drawable.torpedo_explosion_sprites);
		}
		return mTorpedoExplosion;
	}
	
	/**
	 * Gets the empty star.
	 *
	 * @return the empty star
	 */
	public static Bitmap getEmptyStar(){
		if(empty(mEmptyStar)){
			mEmptyStar = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.star_empty);
		}
		return mEmptyStar;
	}
	
	/**
	 * Gets the full star.
	 *
	 * @return the full star
	 */
	public static Bitmap getFullStar(){
		if(empty(mFullStar)){
			mFullStar = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.star_full);
		}
		return mFullStar;
	}
	
	/**
	 * Gets the destroy boat.
	 *
	 * @return the destroy boat
	 */
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