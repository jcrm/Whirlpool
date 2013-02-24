package logic;

import example.whirlpool.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class Imports {
	private static boolean mOnceImages = false;
	private static Bitmap mDuck;
	private static Bitmap mDiver;
	private static Bitmap mDiverFlipped;
	private static Bitmap mBoat;
	private static Bitmap mFrog;
	private static Bitmap mShark;
	private static Bitmap mWhirlpool;
	private static Bitmap mBackground;
	
	static public void setImages(){
		if(!mOnceImages){
			mOnceImages = true;
			Matrix flipMatrix = new Matrix();
			flipMatrix.setScale(1, -1);
			System.gc();
			mDuck = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duck_left_and_right_sprites);
			mDiver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_sprites);
			mDiverFlipped = Bitmap.createBitmap(mDiver, 0, 0, mDiver.getWidth(), mDiver.getHeight(), flipMatrix, false);
			//boat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat);
			mFrog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog_sprites);
			//shark = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark);
			mWhirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool_sprites);
			mBackground  = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.wateroffset_background);
			System.gc();
		}
	}
	public static boolean scaledBitmap(int type, int newWidth, int newHeight){
		switch(type){
		case 0:
			return false;
		case 1:
			if(mWhirlpool.getWidth()!= newWidth && mWhirlpool.getHeight()!=newHeight){
				mWhirlpool = Bitmap.createScaledBitmap(mWhirlpool, newWidth, newHeight, false);
				System.gc();
				return true;
			}
		case 2:
			if(mDuck.getWidth()!= newWidth && mDuck.getHeight()!=newHeight){
				mDuck = Bitmap.createScaledBitmap(mDuck, newWidth, newHeight, false);
				System.gc();
				return true;
			}
		case 3:
			if(mFrog.getWidth()!= newWidth && mFrog.getHeight()!=newHeight){
				mFrog = Bitmap.createScaledBitmap(mFrog, newWidth, newHeight, false);
				System.gc();
				return true;
			}
		/*case 4:
			if(shark.getWidth()!= nW && shark.getHeight()!=nH){
				shark = Bitmap.createScaledBitmap(shark, nW, nH, false);
				System.gc();
				return true;
			}
		case 5:
			if(boat.getWidth()!= nW && boat.getHeight()!=nH){
				boat = Bitmap.createScaledBitmap(boat, nW, nH, false);
				System.gc();
				return true;
			}*/
		case 6:
			if(mDiver.getWidth()!= newWidth && mDiver.getHeight()!=newHeight){
				mDiver = Bitmap.createScaledBitmap(mDiver, newWidth, newHeight, false);
				mDiverFlipped = Bitmap.createScaledBitmap(mDiverFlipped, newWidth, newHeight, false);
				System.gc();
				return true;
			}
		default:
			return false;
		}
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
	/*
	public static Bitmap getBoat() {
		return boat;
	}
	public static void setBoat(Bitmap boat) {
		boat = boat;
	}*/
	public static Bitmap getFrog() {
		return mFrog;
	}
	public static void setFrog(Bitmap frog) {
		mFrog = frog;
	}
	/*public static Bitmap getShark() {
		return shark;
	}
	public static void setShark(Bitmap shark) {
		Imports.shark = shark;
	}*/
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
}