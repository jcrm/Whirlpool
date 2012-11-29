package example.whirlpool;

import java.util.ArrayList;
import example.whirlpool.GraphicObject.objtype;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Panel extends SurfaceView implements SurfaceHolder.Callback {
	private boolean _GameIsRunning;
    private MainThread _thread;
    final WPools _wpoolModel = new WPools();
    private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
    static public Screen sScreen = new Screen();
    static public Resources sRes;
    
    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
        _thread = new MainThread(this);
        setFocusable(true);
        setOnTouchListener(new TrackingTouchListener(_wpoolModel,getHolder()));
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        for (Whirlpool whirlpool : _wpoolModel.getWpools()) {
        	whirlpool.getGraphic().draw(canvas);
        }
        for (GraphicObject graphic : _graphics) {
        	graphic.draw(canvas);
        }
   }
  //Initialise objects to screen
    public void init(){
    	sScreen.set(getWidth(), getHeight());
    	sRes = getResources();
        _graphics.add(new GraphicObject(objtype.tDuck));
        _graphics.add(new GraphicObject(objtype.tFrog));
        _graphics.add(new GraphicObject(objtype.tDiver));
    	_graphics.add(new GraphicObject(objtype.tShark));
        _graphics.add(new GraphicObject(objtype.tBoat));
        
    }
    //move objects around screen
    public void updatePhysics() {
        for (GraphicObject graphic : _graphics) {
            // Move Objects
            if(graphic.move()){
            	Func.borders(graphic, sScreen.getWidth(), sScreen.getHeight());
            }
            boolean lAnyCollFound = false; //see if object is in a wpool
            for(Whirlpool whirl : _wpoolModel.getWpools()){
            	if (whirl.collision(graphic) == 1){
            		lAnyCollFound = true;
            		if (graphic.getPullState() == false) whirl.pull(graphic);
            	}
            	else if (whirl.collision(graphic) == 2){
            		lAnyCollFound = true;
            		if (graphic.getPullState() == false) {
            			graphic.setAngle(whirl.getWAngle());
            			graphic.cantPull();
            		}
            	}
            }
            if (lAnyCollFound == false){
            	graphic.canPull();
            }
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
    	_thread.onPause();
    }
}