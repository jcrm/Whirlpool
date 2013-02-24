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
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;


public class WPools {
    
    private final ArrayList<Whirlpool> wpools = new ArrayList<Whirlpool>();
    //private final List<Whirlpool> safeWPools = Collections.unmodifiableList(wpools);
    
    //private WPoolsChangeListener wpoolsChangeListener;
    
    /** @param l set the change listener. */
    //public void setWPoolsChangeListener(WPoolsChangeListener l) {
    //	wpoolsChangeListener = l;
    //}

    /** @return the most recently added dot. */
    public Whirlpool getLastWpool() {
        return (wpools.size() <= 0) ? null : wpools.get(wpools.size()-1); 
    }
    
    /** @return immutable list of dots. */
    public ArrayList<Whirlpool> getWpools() { return wpools; }

   
    public int checkCollision(float x, float y){
    	x += Constants.getLevel().getScrollBy();
    	for (int i=0; i<wpools.size(); i++)
    		if (wpools.get(i).pointCollision(x, y))
    			return i;
    	
    	return -1;
    }
    
    public void addWPool(float x, float y, float size, float angle, int clockwise) {
    	Whirlpool whirl = new Whirlpool();
    	whirl.setX(x);
    	whirl.setY(y);
    	whirl.setWAngle(angle);
    	whirl.setClockwise(clockwise);
        wpools.add(whirl);
    }

    public void clearWPools() {
    	wpools.clear();
    }

}
