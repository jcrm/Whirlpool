package logic;

import android.content.res.Resources;
import states.MainActivity;

public class Constants {
	private static MainActivity state;
	private static Resources res;
	private static Screen screen;
	private static MainThread thread;
	private static Level level;
	
	synchronized public static MainActivity getState() {
		return state;
	}
	synchronized public static void setState(MainActivity state) {
		Constants.state = state;
	}
	synchronized public static Resources getRes() {
		return res;
	}
	synchronized public static void setRes(Resources res) {
		Constants.res = res;
	}
	public static Screen getScreen() {
		return screen;
	}
	synchronized public static void setScreen(Screen screen) {
		Constants.screen = screen;
	}
	synchronized public static MainThread getThread(){
		return thread;
	}
	synchronized public static void setThread(MainThread thread) {
		Constants.thread = thread;
	}
	synchronized public static Level getLevel() {
		return level;
	}
	synchronized public static void setLevel(Level level) {
		Constants.level = level;
	}
	synchronized public static void clearLevel() {
		Constants.level = null;
	}
	
}
