/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.sinkingduckstudios.whirlpool.manager.SoundManager;
import com.sinkingduckstudios.whirlpool.objects.Duck;
import com.sinkingduckstudios.whirlpool.views.GameView;

public class Constants {
	private static Activity sState;
	private static Resources sRes;
	private static Screen sScreen;
	private static Level sLevel;
	private static Duck sPlayer;
	private static Context sContext;
	private static GameView sPanel;
	private static Object sScreenLock = 0;
	private static SoundManager sSoundManager;


	public static int STATE_FREE = 0;
	public static int STATE_PULLED = 1;
	public static int STATE_LEAVING = 2;
	public static int STATE_FINISHING = 3;

	synchronized public static Object getLock() {
		return sScreenLock;
	}
	synchronized public static Activity getState() {
		return sState;
	}
	synchronized public static void setState(Activity game) {
		sState = game;
	}
	synchronized public static Resources getRes() {
		return sRes;
	}
	synchronized public static void setRes(Resources res) {
		sRes = res;
	}
	public static Screen getScreen() {
		return sScreen;
	}
	synchronized public static void setScreen(Screen screen) {
		sScreen = screen;
	}
	synchronized public static Level getLevel() {
		return sLevel;
	}
	synchronized public static void setLevel(Level level) {
		sLevel = level;
	}
	synchronized public static void clearLevel() {
		sLevel = null;
	}
	synchronized public static Duck getPlayer() {
		return sPlayer;
	}
	synchronized public static void setPlayer(Duck player) {
		sPlayer = player;
	}
	public static Context getContext() {
		return sContext;
	}
	public static void setContext(Context context) {
		sContext = context;
	}
	public static GameView getPanel() {
		return sPanel;
	}
	public static void setPanel(GameView panel) {
		sPanel = panel;
	}
	public static SoundManager getSoundManager() {
		return sSoundManager;
	}
	public static void createSoundManager(Context appContext) {
		if(sSoundManager==null)
			sSoundManager = new SoundManager(appContext);
	}
}
