/*
 * Author:
 * Last Updated:
 * Content:
 * 
 * 
 */
package com.sinkingduckstudios.whirlpool.logic;

import java.util.ArrayList;

import com.sinkingduckstudios.whirlpool.objects.Whirlpool;

// TODO: Auto-generated Javadoc
/**
 * The Class WPools.
 */
public class WPools {
    
    /** The wpools. */
    private final ArrayList<Whirlpool> wpools = new ArrayList<Whirlpool>();

    /**
     * Gets the last wpool.
     *
     * @return the most recently added dot.
     */
    public Whirlpool getLastWpool() {
        return (wpools.size() <= 0) ? null : wpools.get(wpools.size()-1); 
    }
    
    /**
     * Gets the wpools.
     *
     * @return immutable list of dots.
     */
    public ArrayList<Whirlpool> getWpools(){
    	return wpools;
    }

   
    /**
     * Check collision.
     *
     * @param x the x
     * @param y the y
     * @return the int
     */
    public int checkCollision(float x, float y){
    	x += Constants.getLevel().getScrollBy();
    	for (int i=0; i<wpools.size(); i++)
    		if (wpools.get(i).pointCollision(x, y))
    			return i;
    	
    	return -1;
    }
    
    /**
     * Adds the w pool.
     *
     * @param x the x
     * @param y the y
     * @param size the size
     * @param angle the angle
     * @param clockwise the clockwise
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

    /**
     * Clear w pools.
     */
    public void clearWPools() {
    	wpools.clear();
    }

}
