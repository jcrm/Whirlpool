package logic;

//import android.graphics.Color;
import objects.Whirlpool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

final class TrackingTouchListener implements View.OnTouchListener{
	//really bad way of doing this, change asap. no structs in java.
	private float[] _start = new float[2];
	private float[] _last = new float[2];
	private float[] _curr = new float[2];
	private float[] _refX = new float[4];
	private float[] _refY = new float[4];
	private int _noRef, _lastRef;
	private float _xDir, _yDir;
	private int _newGesture;//type of gesture 1 =  whirl, 2 = direction
	private boolean _xToChange, _yToChange; //nasty
	//end result
	private float[] _wDirection = new float[2];
	private float[] _wCenter = new float[2];
	private int _wSize; //whirlpool direction, center point, and size.
	private int wPoolIndex;
	private float _wAngle;
	private final WPools _mWPools;
	private final SurfaceHolder _surfaceHolder;
	private Whirlpool mWhirl;
	
    TrackingTouchListener(WPools wpools, SurfaceHolder surfaceHolder) {
    	_mWPools = wpools;
    	_newGesture = 0;
    	_surfaceHolder = surfaceHolder;
    }

    public boolean onTouch(View v, MotionEvent evt) {
    synchronized (_surfaceHolder){
    	switch(evt.getAction()){
    	
    	case MotionEvent.ACTION_DOWN:
    		
    		wPoolIndex =_mWPools.checkCollision(evt.getX(), evt.getY());
    		
    		if (wPoolIndex > -1){ //pointing a whirl
    			_newGesture = 2;
    			mWhirl = _mWPools.getWpools().get(wPoolIndex);
    		}else if (wPoolIndex == -1){ //new whirl
	    		_start[0] = evt.getX();
	    		_start[1] = evt.getY();
	    		_last[0] = evt.getX();
	    		_last[1] = evt.getY();
	    		_noRef = 1;
	    		_newGesture = 1;
	    		break;
    		}
    		
    		
    	case MotionEvent.ACTION_MOVE:
    		_curr[0] = evt.getX();
    		_curr[1] = evt.getY();
    		int numPointers = evt.getPointerCount();
            if (numPointers > 1) {
                float currX = evt.getRawX();
                float deltaX = -(currX - _last[0]);
                Constants.getLevel().shiftScrollBy(deltaX);
            }
            else if (_newGesture == 1){
    			//gesture has been started. determine direction.
    			_xDir = (_curr[0] - _start[0]);
    			if (_xDir > 0) _xDir = 1; else _xDir = -1;
    			_yDir = (_curr[1] - _start[1]);
    			if (_yDir > 0) _yDir = 1; else _yDir = -1;
    			_newGesture=0;
    			_xToChange = true;
    			_yToChange = true;
    		}else if (_newGesture == 0){
    			int refindex;
    			if ((_curr[0] - _last[0])*_xDir < 0){//change in x direction
    				if (_xToChange == false){
    					_start[0] = evt.getX();
    	    			_start[1] = evt.getY();
    	    			_noRef = 0;
    	    			_newGesture = 1;
    	    			break;
    				}
    				refindex = _noRef;
    				refindex = refindex % 4;
    				_refX[refindex]=_last[0];
    				_refY[refindex]=_last[1];
    				_lastRef=refindex;
    				_noRef++;
    				_xDir*=-1;
    				_yToChange = true;
    				_xToChange = false;
    			}
    			if ((_curr[1] - _last[1])*_yDir < 0){//change in y direction
    				if (_yToChange == false){
    					_start[0] = evt.getX();
    	    			_start[1] = evt.getY();
    	    			_noRef = 0;
    	    			_newGesture = 1;
    	    			break;
    				}
    				refindex = _noRef;
    				refindex = refindex % 4;
    				_refX[refindex]=_last[0];
    				_refY[refindex]=_last[1];
    				_lastRef=refindex;
    				_noRef++;
    				_yDir*=-1;
    				_yToChange = false;
    				_xToChange = true;
    			}	
    		}
    		_last[0] = evt.getX();
    		_last[1] = evt.getY();
    		break;
    		
    		
    	case MotionEvent.ACTION_UP:
    		
    	if (_newGesture == 2){	
    		_wAngle = Func.calcAngle(mWhirl.getX(), mWhirl.getY() , (evt.getX() + Constants.getLevel().getScrollBy()), evt.getY());
    		mWhirl.setWAngle(_wAngle);
    	}
    	
    	else if (_newGesture == 0){
    		if (_noRef > 4){//whirlpool made
    			//direction (last ref point to final lift point)
    			//_wDirection[0] = _curr[0] - _refX[_lastRef];
    			//_wDirection[1] = _curr[1] - _refY[_lastRef];
    			//_wAngle = Func.calcAngle(_refX[_lastRef], _refY[_lastRef] , _curr[0], _curr[1]);
    			
    			//Get center point between 4 ref points
    			_wCenter[0] = (_refX[0]+_refX[1]+_refX[2]+_refX[3])/4;
    			_wCenter[1] = (_refY[0]+_refY[1]+_refY[2]+_refY[3])/4;
    			//calc size of whirlpool, simply NoRefs, 4 reference points being the smallest size = (1).
    			_wSize = _noRef-3;
    			
    			
    			addWPools(
    	    			_mWPools,
    	    			_wCenter[0] + Constants.getLevel().getScrollBy(),
    	    			_wCenter[1],
    	    			_wSize,
    	    			-1,// pass in -1 for no angle, probs should clean this up
    	    			isClockwise());
    		
    		}
    		break;
    	}
    	default: return false;
    	}
    }
    	return true;
    }
    
    private boolean isClockwise(){
    	
    	float[] temp = new float[4];;//holds angle of ref points
		int lowest=0, next=1; 
		
		for (int i = 0;i<4;i++){
			temp[i] = Func.calcAngle(_wCenter[0], _wCenter[1], _refX[i], _refY[i]);
			if (temp[i] <= temp[lowest]){ 
				lowest = i;
				next = i+1;
				if (next == 4) next = 0;
			}
		}
		int secondlowest;
		secondlowest = next;
		
		for (int i = 0;i<4;i++){
			if (i != lowest)
				if (temp[i] <= temp[secondlowest]) 
					secondlowest = i;
		}
		
		//head is hurting, come back to this, just need a simple method to get lowest and second lowest angles -F
		
		if ((temp[next] == temp[secondlowest])) 
			return true;
		else 
			return false;
    }
    
    private void addWPools(WPools wpools, float x, float y, int s, float angle, boolean clockwise) {
    	wpools.addWPool(x, y, s, angle, clockwise);
    }
}
