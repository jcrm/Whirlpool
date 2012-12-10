package logic;

import java.util.ArrayList;



import objects.GraphicObject;
import objects.GraphicObject.objtype;

import states.MainActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {
	private static boolean _GameIsRunning;
    static private MainThread _thread = new MainThread();
    static public Screen sScreen = new Screen();
    static public Resources sRes;
    
    public Panel(Context context) {
        super(context);
        constructor();
    }
    public Panel(Context context, AttributeSet attrs) {
        super(context, attrs);
        constructor();
    }
    private void constructor(){
    	getHolder().addCallback(this);
        _thread.setPanel(this);
        setFocusable(true);
        Constants.setScreen(sScreen);
        Constants.setThread(_thread);
    }
    public void update(){
    	Constants.getState().update();
    }
    @Override
    public void onDraw(Canvas canvas) {
    	Constants.getState().onDraw(canvas);
    }
    public void init(){
    	//int x = findViewById(R.id.mainview).getWidth();
    	//int y = findViewById(R.id.mainview).getHeight();
    	sScreen.set(getWidth(), getHeight());
    	sRes = getResources();
    	Constants.setRes(sRes);
    	if(Constants.getState().needListener()){
    		setOnTouchListener(new TrackingTouchListener(Constants.getState().getCurrentLevel().getWPoolModel() , getHolder()));    		
    	}
    }
    public void start() {
        if (!_GameIsRunning) {
        	_thread.start();
            _GameIsRunning = true;
        } else {
            _thread.onResume();
        }
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }
    public void surfaceCreated(SurfaceHolder holder) {
    	_thread.setRunning(true);
    	start();
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
    	//_thread.onPause();
    }

}