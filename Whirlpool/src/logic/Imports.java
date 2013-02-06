package logic;

import java.io.IOException;

import example.whirlpool.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;

public class Imports {
	private static boolean onceImages = false;
	private static boolean onceAudio = false;
	private static Bitmap duck;
	private static Bitmap diver;
	private static Bitmap diverFlipped;
	private static Bitmap boat;
	private static Bitmap frog;
	private static Bitmap shark;
	private static Bitmap whirlpool;
	private static Bitmap background;
	
	private static MediaPlayer duckSound;
	private static MediaPlayer duckHit1Sound;
	
	static public void setImages(){
		if(!onceImages){
			onceImages = true;
			Matrix flipMatrix = new Matrix();
			flipMatrix.setScale(1, -1);
			
			duck = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duckleftandright2);
			diver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_sprites);
			diverFlipped = Bitmap.createBitmap(diver, 0, 0, diver.getWidth(), diver.getHeight(), flipMatrix, false);
			boat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat);
			frog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog2);
			shark = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark);
			whirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool_sprites);
			background  = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.wateroffset);
		}
	}
	static public void setAudio(){
		if(!onceAudio){
			onceAudio = true;
			duckSound = MediaPlayer.create(Constants.getContext(), R.raw.ducky);
			duckHit1Sound = MediaPlayer.create(Constants.getContext(), R.raw.ducky5);
			try{
				duckSound.prepare();
				duckHit1Sound.prepare();
			}catch(IllegalStateException e){
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static boolean scaledBitmap(int type, int nW, int nH){
		switch(type){
		case 0:
			return false;
		case 1:
			if(whirlpool.getWidth()!= nW && whirlpool.getHeight()!=nH){
				whirlpool = Bitmap.createScaledBitmap(whirlpool, nW, nH, false);
				System.gc();
				return true;
			}
		case 2:
			if(duck.getWidth()!= nW && duck.getHeight()!=nH){
				duck = Bitmap.createScaledBitmap(duck, nW, nH, false);
				System.gc();
				return true;
			}
		case 3:
			if(frog.getWidth()!= nW && frog.getHeight()!=nH){
				frog = Bitmap.createScaledBitmap(frog, nW, nH, false);
				System.gc();
				return true;
			}
		case 4:
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
			}
		case 6:
			if(diver.getWidth()!= nW && diver.getHeight()!=nH){
				diver = Bitmap.createScaledBitmap(diver, nW, nH, false);
				diverFlipped = Bitmap.createScaledBitmap(diverFlipped, nW, nH, false);
				System.gc();
				return true;
			}
		default:
			return false;
		}
	}
	public static Bitmap getDuck() {
		return duck;
	}
	public static void setDuck(Bitmap duck) {
		Imports.duck = duck;
	}
	public static Bitmap getDiver() {
		return diver;
	}
	public static void setDiver(Bitmap diver) {
		Imports.diver = diver;
	}
	public static Bitmap getDiverFlipped() {
		return diverFlipped;
	}
	public static void setDiverFlipped(Bitmap diverflipped) {
		Imports.diverFlipped = diverflipped;
	}
	public static Bitmap getBoat() {
		return boat;
	}
	public static void setBoat(Bitmap boat) {
		Imports.boat = boat;
	}
	public static Bitmap getFrog() {
		return frog;
	}
	public static void setFrog(Bitmap frog) {
		Imports.frog = frog;
	}
	public static Bitmap getShark() {
		return shark;
	}
	public static void setShark(Bitmap shark) {
		Imports.shark = shark;
	}
	public static Bitmap getWhirlpool() {
		return whirlpool;
	}
	public static void setWhirlpool(Bitmap whirlpool) {
		Imports.whirlpool = whirlpool;
	}
	public static Bitmap getBackground() {
		return background;
	}
	public static void setBackground(Bitmap background) {
		Imports.background = background;
	}
	public static MediaPlayer getDuckSound() {
		return duckSound;
	}

	public static void setDuckSound(MediaPlayer duckSound) {
		Imports.duckSound = duckSound;
	}

	public static MediaPlayer getDuckHit1Sound() {
		return duckHit1Sound;
	}

	public static void setDuckHit1Sound(MediaPlayer duckHit1Sound) {
		Imports.duckHit1Sound = duckHit1Sound;
	}
}