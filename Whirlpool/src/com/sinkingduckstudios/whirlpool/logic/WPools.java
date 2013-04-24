/*
 * Author: Fraser Tomison
 * Last Updated: 14/04/13
 * Content:
 * This is the whirlpool model for the game,
 * It holds all the whirlpool objects and
 * interfaces all interactions with them
 */
package com.sinkingduckstudios.whirlpool.logic;

import java.util.ArrayList;

import com.sinkingduckstudios.whirlpool.objects.Whirlpool;

public class WPools {

	private final ArrayList<Whirlpool> wpools = new ArrayList<Whirlpool>();

	/** @return the most recently added whirlpool. */
	public Whirlpool getLastWpool() {
		return (wpools.size() <= 0) ? null : wpools.get(wpools.size()-1);
	}

	/** @return immutable list of whirlpools. */
	public ArrayList<Whirlpool> getWpools(){
		return wpools;
	}

	/**
	 * Check point for collision with whirlpools
	 * @param x
	 * @param y
	 * @return index of whirlpool, or -1 if none
	 */
	public int checkCollision(float x, float y){
		x += Constants.getLevel().getScrollBy();
		for (int i=0; i<wpools.size(); i++)
			if (wpools.get(i).pointCollision(x, y))
				return i;

		return -1;
	}

	/**
	 * User has created a whirlpool
	 * @param x
	 * @param y
	 * @param size depreciated
	 * @param angle angle of exit (normally -1 on creation)
	 * @param clockwise direction of flow for whirlpool
	 */
	public void addWPool(int x, int y, float size, float angle, int clockwise) {
		Whirlpool whirl = new Whirlpool();
		whirl.setCentreX(x);
		whirl.setCentreY(y);
		whirl.setWAngle(angle);
		whirl.setClockwise(clockwise);
		wpools.add(whirl);
		Constants.getSoundManager().playSplash();
	}

	public void clearWPools() {
		wpools.clear();
	}

}