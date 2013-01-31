package logic;

import example.whirlpool.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaPlayer;

public class Imports {
	private static boolean once = false;
	private static Bitmap duck;
	private static Bitmap diver;
	private static Bitmap diverFlipped;
	private static Bitmap boat;
	private static Bitmap frog;
	private static Bitmap shark;
	private static Bitmap whirlpool;
	private static Bitmap background;
	
	private static MediaPlayer gameMusic;
	private static MediaPlayer duckSound;
	private static MediaPlayer duckHit1Sound;
	
	static public void setImages(){
		if(!once){
			once = true;
			Matrix flipMatrix = new Matrix();
			flipMatrix.setScale(1, -1);
			
			duck = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duckleftandright2);
			diver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver_sprites);
			diverFlipped = Bitmap.createBitmap(diver, 0, 0, diver.getWidth(), diver.getHeight(), flipMatrix, false);
			boat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat);
			frog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog2);
			shark = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark);
			whirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool);
			background  = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.wateroffset);
			
			gameMusic = MediaPlayer.create(Constants.getContext(), R.raw.sample1);
			duckSound = MediaPlayer.create(Constants.getContext(), R.raw.duck);
			duckHit1Sound = MediaPlayer.create(Constants.getContext(), R.raw.duckhit1);
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

	public static MediaPlayer getGameMusic() {
		return gameMusic;
	}

	public static void setGameMusic(MediaPlayer gameMusic) {
		Imports.gameMusic = gameMusic;
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
