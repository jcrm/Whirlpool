package logic;

//TODO: header

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
	    		_noRef = 0;
	    		addRefPoint();
	    		_newGesture = 1;
	    		findCenter();
	    		mWhirl = addWPools(
    	    			_mWPools,
    	    			_wCenter[0] + Constants.getLevel().getScrollBy(),
    	    			_wCenter[1],
    	    			_wSize,
    	    			-1,// pass in -1 for no angle, probs should clean this up
    	    			isClockwise());
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
    			
    			if(_noRef < 3){//if no precise whirl motion has been completed yet, draw whirlpool in center
    				_refX[_noRef]=_last[0];
    				_refY[_noRef]=_last[1];
    			}
    			findCenter();//calc current center to position wpool
    			mWhirl.setX(_wCenter[0]+ Constants.getLevel().getScrollBy());
    			mWhirl.setY(_wCenter[1]);
    			mWhirl.setClockwise(isClockwise());
    			if ((_curr[0] - _last[0])*_xDir < 0){//change in x direction
    				if (_xToChange == false){
    					_start[0] = evt.getX();
    	    			_start[1] = evt.getY();
    	    			_last[0] = evt.getX();
    	        		_last[1] = evt.getY();
    	    			_noRef = 0;
    	    			_newGesture = 1;
    	    			break;
    				}
    				addRefPoint();
    				_xDir*=-1;
    				_yToChange = true;
    				_xToChange = false;
    			}
    			
    			if ((_curr[1] - _last[1])*_yDir < 0){//change in y direction
    				if (_yToChange == false){
    					_start[0] = evt.getX();
    	    			_start[1] = evt.getY();
    	    			_last[0] = evt.getX();
    	        		_last[1] = evt.getY();
    	    			_noRef = 0;
    	    			_newGesture = 1;
    	    			break;
    				}
    				addRefPoint();
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
    		if (_noRef > 2){//whirlpool made
    			
    			findCenter();
    			mWhirl.setX(_wCenter[0]+ Constants.getLevel().getScrollBy());
    			mWhirl.setY(_wCenter[1]);
    			mWhirl.setClockwise(isClockwise());
    		}else{
    			//remove whirl (gesture wasnt completed)
    			_mWPools.getWpools().remove(mWhirl);
    		}    		
    	}
    	break;
    	default: return false;
    	}
    }
    	return true;
    }
    
    private void addRefPoint(){
    	int refindex;
    	refindex = _noRef;
		refindex = refindex % 4;
		_refX[refindex]=_last[0];
		_refY[refindex]=_last[1];
		_lastRef=refindex;
		_noRef++;
    }
    
    private void findCenter(){
    	
    	int CountTo = _noRef;
    	if (CountTo > 4) CountTo = 4;
    	float additionX=0, additionY=0;
    	for (int i = 0; i < CountTo; i++){
    		additionX += _refX[i];
    		additionY += _refY[i];
    	}
		_wCenter[0] = additionX/CountTo;
		_wCenter[1] = additionY/CountTo;
		
		//calc size of whirlpool, simply NoRefs
		_wSize = _noRef;
    }
    
    private int isClockwise(){
    
    	if (_noRef < 3) 
    		return 0;//not enough ref points to get a direction
    	
    	//2D vectors
    	float one_x,one_y, two_x,two_y;
    	
    	int _otherRef; //reference point before last
    	_otherRef = _lastRef - 1;
    	if (_otherRef == -1) _otherRef = 3;
    	one_x = _refX[_lastRef] - _wCenter[0];
    	one_y = _refY[_lastRef] - _wCenter[1];
    	two_x = _refX[_otherRef] - _wCenter[0];
    	two_y = _refY[_otherRef] - _wCenter[1];
    	
    	//Do cross product of vectors to get if clockwise or counter
    	float crossProduct = (one_x*two_y) - (one_y*two_x);
    	
    	if (crossProduct >= 0) 
    		return -1;//counter
    	else
    		return 1;//clockwise
    	
    }
    
    private Whirlpool addWPools(WPools wpools, float x, float y, int s, float angle, int clockwise) {
    	wpools.addWPool(x, y, s, angle, clockwise);
    	return wpools.getLastWpool();
    }
}
