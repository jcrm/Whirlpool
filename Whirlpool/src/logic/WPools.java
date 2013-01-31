package logic;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;

import objects.Whirlpool;


/** A list of dots. */
public class WPools {
    /** DotChangeListener. */
    //public interface WPoolsChangeListener {
        /** @param dots the dots that changed. */
      //  void onWPoolsChange(WPools wpools);
   // }
    
    private final ArrayList<Whirlpool> wpools = new ArrayList<Whirlpool>();
    //private final List<Whirlpool> safeWPools = Collections.unmodifiableList(wpools);
    
    //private WPoolsChangeListener wpoolsChangeListener;
    

    public Whirlpool getLastWpool() {
        return (wpools.size() <= 0) ? null : wpools.get(wpools.size()-1); 
    }
    
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
        //notifyListener();
    }

    /** Remove all dots. */
    public void clearDots() {
    	wpools.clear();
        //notifyListener();
    }

   // private void notifyListener() {
    //    if (null != wpoolsChangeListener) {
    //    	wpoolsChangeListener.onWPoolsChange(this); 
   //     }
   // }
}
