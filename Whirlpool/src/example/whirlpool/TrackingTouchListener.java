package example.whirlpool;

//import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;


final class TrackingTouchListener implements View.OnTouchListener{
	
	//really bad way of doing this, change asap. no structs in java.
	private float[] start = new float[2];
	private float[] last = new float[2];
	private float[] curr = new float[2];
	private float[] refX = new float[4];
	private float[] refY = new float[4];
	private int NoRef, LastRef;
	private float xdir, ydir;
	private boolean NewGesture;
	
	private boolean xToChange, yToChange; //nasty
	//end result
	private float[] Wdirection = new float[2];
	private float[] Wcenter = new float[2];
	
	private int Wsize; //whirlpool direction, center point, and size.
	private float Wangle;
	
	private final WPools mWPools;
	
	private final TutorialThread _thread;
	
    TrackingTouchListener(WPools wpools, TutorialThread Athread) {mWPools = wpools; NewGesture = false; _thread = Athread;}

    public boolean onTouch(View v, MotionEvent evt) {
        
    synchronized (_thread.getSurfaceHolder()){
    	switch(evt.getAction()){
    	
    	case MotionEvent.ACTION_DOWN:
    		start[0] = evt.getX();
    		start[1] = evt.getY();
    		last[0] = evt.getX();
    		last[1] = evt.getY();
    		NoRef = 0;
    		NewGesture = true;
    		break;
    	
    	case MotionEvent.ACTION_MOVE:
    		
    		curr[0] = evt.getX();
    		curr[1] = evt.getY();
    		
    		
    		
    		if (NewGesture == true){
    			//gesture has been started. determine direction.
    			xdir = (curr[0] - start[0]);
    			if (xdir > 0) xdir = 1; else xdir = -1;
    			ydir = (curr[1] - start[1]);
    			if (ydir > 0) ydir = 1; else ydir = -1;
    			NewGesture=false;
    			xToChange = true;
    			yToChange = true;
    		}else{
    			
    			int refindex;
    			if ((curr[0] - last[0])*xdir < 0){//change in x direction
    				if (xToChange == false){
    					start[0] = evt.getX();
    	    			start[1] = evt.getY();
    	    			NoRef = 0;
    	    			NewGesture = true;
    	    			break;
    				}
    				refindex = NoRef;
    				refindex = refindex % 4;
    				refX[refindex]=last[0];
    				refY[refindex]=last[1];
    				LastRef=refindex;
    				NoRef++;
    				xdir*=-1;
    				yToChange = true;
    				xToChange = false;
    			}
    			if ((curr[1] - last[1])*ydir < 0){//change in y direction
    				if (yToChange == false){
    					start[0] = evt.getX();
    	    			start[1] = evt.getY();
    	    			NoRef = 0;
    	    			NewGesture = true;
    	    			break;
    				}
    				refindex = NoRef;
    				refindex = refindex % 4;
    				refX[refindex]=last[0];
    				refY[refindex]=last[1];
    				LastRef=refindex;
    				NoRef++;
    				ydir*=-1;
    				yToChange = false;
    				xToChange = true;
    			}	
    		}
    		
    		last[0] = evt.getX();
    		last[1] = evt.getY();
    		
    		break;
    	
    	case MotionEvent.ACTION_UP:
    		
    		if (NoRef > 4){//whirlpool made
    			
    			//direction (last ref point to final lift point)
    			Wdirection[0] = curr[0] - refX[LastRef];
    			Wdirection[1] = curr[1] - refY[LastRef];
    			
    			Wangle = Func.calcAngle(refX[LastRef], refY[LastRef] , curr[0], curr[1]);
    			//Get center point between 4 ref points
    			
    			Wcenter[0] = (refX[0]+refX[1]+refX[2]+refX[3])/4;
    			Wcenter[1] = (refY[0]+refY[1]+refY[2]+refY[3])/4;
    				
    				
    			//calc size of whirlpool, simply NoRefs, 4 reference points being the smallest size = (1).
    			Wsize = NoRef-3;
    			
    			addWPools(
    	    			mWPools,
    	    			Wcenter[0],
    	    			Wcenter[1],
    	    			Wsize,
    	    			Wangle);
    		
    		}
    		break;
    		
    		
    	default: return false;
    	}
    	
    }
    	
    	return true;
    }
    
    private void addWPools(WPools wpools, float x, float y, int s, float angle) {
    	wpools.addWPool(
            x,
            y,
            s,
            angle);
    	
    }
}
