package logic;

import java.util.ArrayList;



import objects.GraphicObject;
import objects.GraphicObject.objtype;

import states.MainActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
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
    static MediaPlayer backgroundMusic;
    
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
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    	int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
    	int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
    	this.setMeasuredDimension(parentWidth, parentHeight);
    	sScreen.set(parentWidth, parentHeight);
    }
    public void init(){
    	//int x = findViewById(R.id.mainview).getWidth();
    	//int y = findViewById(R.id.mainview).getHeight();
    	sRes = getResources();
    	Constants.setRes(sRes);
    	if(Constants.getState().needListener()){
    		setOnTouchListener(new TrackingTouchListener(Constants.getState().getCurrentLevel().getWPoolModel(), getHolder()));    		
    	}
    	Imports.setImages();
    	backgroundMusic = Imports.getGameMusic();
    	backgroundMusic.setVolume(0.7f, 0.7f);
		//backgroundMusic.start();
		//backgroundMusic.setLooping(true);
    }
    public void start(){
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
    
    public static void stopMusic(){
    	if(backgroundMusic != null){
    		backgroundMusic.stop();
    	}
    }
    
    

}