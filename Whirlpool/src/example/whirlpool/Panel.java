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
	//private Whirlpool Whirl;
    private MainThread _thread;
    final WPools _wpoolModel = new WPools();
    static public Screen _screen = new Screen();
    static public Resources _res;
    private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
    
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
  //initalise objects to screen
    public void init(){
    	_screen.set(getWidth(), getHeight());
    	_res = getResources();
    	GraphicObject TheDuck =  new GraphicObject(objtype.tDuck);
        _graphics.add(TheDuck);
        _graphics.add(new GraphicObject(objtype.tFrog));
    	_graphics.add(new GraphicObject(objtype.tShark));
        _graphics.add(new GraphicObject(objtype.tBoat));
    }
    //move objects around screen
    public void updatePhysics() {
        for (GraphicObject graphic : _graphics) {
            // Move Objects
            if(graphic.move()){
            	Func.borders(graphic, _screen.getWidth(), _screen.getHeight());
            }
            boolean AnyCollFound = false; //see if object is in a wpool
            for(Whirlpool whirl : _wpoolModel.getWpools()){
            	if (whirl.Collision(graphic) == 1){
            		AnyCollFound = true;
            		if (graphic.getPullState() == false) whirl.pull(graphic);
            	}
            	else if (whirl.Collision(graphic) == 2){
            		AnyCollFound = true;
            		if (graphic.getPullState() == false) {
            			graphic.setAngle(whirl.getWAngle());
            			graphic.cantPull();
            		}
            	}
            }
            if (AnyCollFound == false){
            	graphic.canPull();
            }
        }
        //angle1 = Func.calcAngle(_graphics.get(0).getX(), _graphics.get(0).getY(), x1, y1);
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
    }
    public void surfaceCreated(SurfaceHolder holder) {
        _thread.setRunning(true);
        _thread.start();
    }
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        _thread.setRunning(false);
        while (retry) {
            try {
                _thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
    }
}