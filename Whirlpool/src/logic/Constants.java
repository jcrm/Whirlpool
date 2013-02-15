package logic;

import objects.Duck;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import states.Game;
import states.MainActivity;

public class Constants {
	private static Activity state;
	private static Resources res;
	private static Screen screen;
	private static Level level;
	private static Duck player;
	private static Context context;
	private static Panel panel;
	private static Object screenLock = 0;
	
	synchronized public static Object getLock() {
		return screenLock;
	}
	synchronized public static Activity getState() {
		return state;
	}
	synchronized public static void setState(Activity game) {
		Constants.state = game;
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
	synchronized public static Level getLevel() {
		return level;
	}
	synchronized public static void setLevel(Level level) {
		Constants.level = level;
	}
	synchronized public static void clearLevel() {
		Constants.level = null;
	}
	synchronized public static Duck getPlayer() {
		return player;
	}
	synchronized public static void setPlayer(Duck player) {
		Constants.player = player;
	}
	public static Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		Constants.context = context;
	}
	public static Panel getPanel() {
		return panel;
	}
	public static void setPanel(Panel panel) {
		Constants.panel = panel;
	}
}
