package example.whirlpool;

import java.util.ArrayList;

/** A list of whirlpools. */
public class WPools {
private final ArrayList<Whirlpool> wpools = new ArrayList<Whirlpool>();
    /** @return the most recently added whirlpools. */
	public Whirlpool getLastWpool() {
        return (wpools.size() <= 0) ? null : wpools.get(wpools.size());
    }
    /** @return immutable list of whirlpools. */
    public ArrayList<Whirlpool> getWpools() { return wpools; }
    /**
     * @param x dot horizontal coordinate.
     * @param y dot vertical coordinate.
     * @param color dot color.
     * @param diameter dot size.
     */
    public void addWPool(float x, float y, float size, float angle) {
    	Whirlpool whirl = new Whirlpool();
    	whirl.setCentreX(x);
    	whirl.setCentreY(y);
    	whirl.setWAngle(angle);
        wpools.add(whirl);
        //notifyListener();
    }
    /** Remove all whirlpools. */
    public void clearWPools() {
    	wpools.clear();
    }
}
