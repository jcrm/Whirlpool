/*
 * Author:
 * Last Updated:
 * Content:
 * Original functionality coded by Jordan O'Hare.
 * Added functions by Jake Morey, Fraser 
 */
package com.sinkingduckstudios.whirlpool.logic;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.sinkingduckstudios.whirlpool.manager.SoundManager;
import com.sinkingduckstudios.whirlpool.objects.Duck;
import com.sinkingduckstudios.whirlpool.views.GameView;

// TODO: Auto-generated Javadoc
/**
 * The Class Constants.
 */
public class Constants {
	
	/** The s state. */
	private static Activity sState;
	
	/** The s res. */
	private static Resources sRes;
	
	/** The s screen. */
	private static Screen sScreen;
	
	/** The s level. */
	private static Level sLevel;
	
	/** The s player. */
	private static Duck sPlayer;
	
	/** The s context. */
	private static Context sContext;
	
	/** The s panel. */
	private static GameView sPanel;
	
	/** The s screen lock. */
	private static Object sScreenLock = 0;
	
	/** The s sound manager. */
	private static SoundManager sSoundManager;
	
	/** The s duck distance. */
	private static float sDuckDistance;

	/** The state free. */
	public static int STATE_FREE = 0;
	
	/** The state pulled. */
	public static int STATE_PULLED = 1;
	
	/** The state leaving. */
	public static int STATE_LEAVING = 2;
	
	/** The state finishing. */
	public static int STATE_FINISHING = 3;

	
	/**
	 * Gets the duck dist.
	 *
	 * @return the duck dist
	 */
	public static float getDuckDist(){
		return sDuckDistance;
	}
	
	/**
	 * Sets the duck dist.
	 *
	 * @param theDist the new duck dist
	 */
	public static void setDuckDist(float theDist){
		sDuckDistance = theDist;
	}
	
	/**
	 * Gets the lock.
	 *
	 * @return the lock
	 */
	synchronized public static Object getLock() {
		return sScreenLock;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	synchronized public static Activity getState() {
		return sState;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param game the new state
	 */
	synchronized public static void setState(Activity game) {
		sState = game;
	}
	
	/**
	 * Gets the res.
	 *
	 * @return the res
	 */
	synchronized public static Resources getRes() {
		return sRes;
	}
	
	/**
	 * Sets the res.
	 *
	 * @param res the new res
	 */
	synchronized public static void setRes(Resources res) {
		sRes = res;
	}
	
	/**
	 * Gets the screen.
	 *
	 * @return the screen
	 */
	public static Screen getScreen() {
		return sScreen;
	}
	
	/**
	 * Sets the screen.
	 *
	 * @param screen the new screen
	 */
	synchronized public static void setScreen(Screen screen) {
		sScreen = screen;
	}
	
	/**
	 * Gets the level.
	 *
	 * @return the level
	 */
	synchronized public static Level getLevel() {
		return sLevel;
	}
	
	/**
	 * Sets the level.
	 *
	 * @param level the new level
	 */
	synchronized public static void setLevel(Level level) {
		sLevel = level;
	}
	
	/**
	 * Clear level.
	 */
	synchronized public static void clearLevel() {
		sLevel = null;
	}
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	synchronized public static Duck getPlayer() {
		return sPlayer;
	}
	
	/**
	 * Sets the player.
	 *
	 * @param player the new player
	 */
	synchronized public static void setPlayer(Duck player) {
		sPlayer = player;
	}
	
	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public static Context getContext() {
		return sContext;
	}
	
	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public static void setContext(Context context) {
		sContext = context;
	}
	
	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	public static GameView getPanel() {
		return sPanel;
	}
	
	/**
	 * Sets the panel.
	 *
	 * @param panel the new panel
	 */
	public static void setPanel(GameView panel) {
		sPanel = panel;
	}
	
	/**
	 * Gets the sound manager.
	 *
	 * @return the sound manager
	 */
	public static SoundManager getSoundManager() {
		return sSoundManager;
	}
	
	/**
	 * Creates the sound manager.
	 *
	 * @param appContext the app context
	 */
	public static void createSoundManager(Context appContext) {
		if(sSoundManager==null)
			sSoundManager = new SoundManager(appContext);
		sSoundManager.init();
	}
}
