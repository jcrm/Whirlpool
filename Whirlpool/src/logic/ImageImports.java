package logic;

import example.whirlpool.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageImports {
	private static Bitmap duck;
	private static Bitmap diver;
	private static Bitmap boat;
	private static Bitmap frog;
	private static Bitmap shark;
	private static Bitmap whirlpool;
	private static Bitmap background;
	
	static public void setImages(){
		duck = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.duckleftandright2);
		diver = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.diver);
		boat = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.boat);
		frog = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.frog2);
		shark = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.shark);
		whirlpool = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.whirlpool);
		background  = BitmapFactory.decodeResource(Constants.getRes(), R.drawable.wateroffset);
	}
	
	public static Bitmap getDuck() {
		return duck;
	}
	public static void setDuck(Bitmap duck) {
		ImageImports.duck = duck;
	}
	public static Bitmap getDiver() {
		return diver;
	}
	public static void setDiver(Bitmap diver) {
		ImageImports.diver = diver;
	}
	public static Bitmap getBoat() {
		return boat;
	}
	public static void setBoat(Bitmap boat) {
		ImageImports.boat = boat;
	}
	public static Bitmap getFrog() {
		return frog;
	}
	public static void setFrog(Bitmap frog) {
		ImageImports.frog = frog;
	}
	public static Bitmap getShark() {
		return shark;
	}
	public static void setShark(Bitmap shark) {
		ImageImports.shark = shark;
	}
	public static Bitmap getWhirlpool() {
		return whirlpool;
	}
	public static void setWhirlpool(Bitmap whirlpool) {
		ImageImports.whirlpool = whirlpool;
	}
	public static Bitmap getBackground() {
		return background;
	}
	public static void setBackground(Bitmap background) {
		ImageImports.background = background;
	}
}
