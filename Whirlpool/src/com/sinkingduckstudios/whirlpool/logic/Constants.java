package com.sinkingduckstudios.whirlpool.logic;

import com.sinkingduckstudios.whirlpool.manager.SoundManager;
import com.sinkingduckstudios.whirlpool.objects.Duck;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

public class Constants {
	private static Activity sState;
	private static Resources sRes;
	private static Screen sScreen;
	private static Level sLevel;
	private static Duck sPlayer;
	private static Context sContext;
	private static Panel sPanel;
	private static Object sScreenLock = 0;
	private static SoundManager sSoundManager = new SoundManager();
	
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
	public static Panel getPanel() {
		return sPanel;
	}
	public static void setPanel(Panel panel) {
		sPanel = panel;
	}
	public static SoundManager getSoundManager() {
		return sSoundManager;
	}
	public static void setSoundManager(SoundManager sSoundManager) {
		Constants.sSoundManager = sSoundManager;
	}
}
