package example.whirlpool;

import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;


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
    
    /** @param l set the change listener. */
    //public void setWPoolsChangeListener(WPoolsChangeListener l) {
    //	wpoolsChangeListener = l;
    //}

    /** @return the most recently added dot. */
    public Whirlpool getLastWpool() {
        return (wpools.size() <= 0) ? null : wpools.get(wpools.size());
    }
    
    /** @return immutable list of dots. */
    public ArrayList<Whirlpool> getWpools() { return wpools; }

    /**
     * @param x dot horizontal coordinate.
     * @param y dot vertical coordinate.
     * @param color dot color.
     * @param diameter dot size.
      */
    public void addWPool(float x, float y, float size, float angle, boolean clockwise) {
    	Whirlpool whirl = new Whirlpool();
    	whirl.setCentreX(x);
    	whirl.setCentreY(y);
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