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

public class WPools {
    
    private final ArrayList<Whirlpool> wpools = new ArrayList<Whirlpool>();

    /** @return the most recently added dot. */
    public Whirlpool getLastWpool() {
        return (wpools.size() <= 0) ? null : wpools.get(wpools.size()-1); 
    }
    
    /** @return immutable list of dots. */
    public ArrayList<Whirlpool> getWpools(){
    	return wpools;
    }

   
    public int checkCollision(float x, float y){
    	x += Constants.getLevel().getScrollBy();
    	for (int i=0; i<wpools.size(); i++)
    		if (wpools.get(i).pointCollision(x, y))
    			return i;
    	
    	return -1;
    }
    
    public void addWPool(int x, int y, float size, float angle, int clockwise) {
    	Whirlpool whirl = new Whirlpool();
    	whirl.setCentreX(x);
    	whirl.setCentreY(y);
    	whirl.setWAngle(angle);
    	whirl.setClockwise(clockwise);
        wpools.add(whirl);
    }

    public void clearWPools() {
    	wpools.clear();
    }

}
