package example.whirlpool;

import java.util.ArrayList;

import example.whirlpool.GraphicObject.objtype;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.FloatMath;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Panel extends SurfaceView implements SurfaceHolder.Callback {
	//private Whirlpool Whirl;
    private MainThread _thread;
    final WPools wpoolModel = new WPools();
    static public Screen screen = new Screen();
    static public Resources res;
    private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
        _thread = new MainThread(this);
        setFocusable(true);
        setOnTouchListener(new TrackingTouchListener(wpoolModel,getHolder()));
    }
    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        for (Whirlpool whirlpool : wpoolModel.getWpools()) {
        	whirlpool.getGraphic().draw(canvas);
        }
        for (GraphicObject graphic : _graphics) {
        	graphic.draw(canvas);
        }
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
  //temp stuff
    float angle1 = 0.0f;
    float x1 = 0.0f;
    float y1 = 0.0f;
    
    //changes direction of objects on screen if falls with in a circular distance from touch event
    public void changeDir(Whirlpool whirl){
    	//iterates through graphic list
    	for (GraphicObject graphic : _graphics){
    		if(Func.circleCollision(graphic.getX(), graphic.getY(), graphic.getRadius(), whirl.getCentreX(), whirl.getCentreY(), whirl.getRadius())){
    		}
    	}
    }
    //initalise objects to screen
    public void init(){
    	screen.set(getWidth(), getHeight());
    	res = getResources();
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
            if(graphic.getSpeed().getMove()){
            	graphic.shiftX(graphic.getSpeed().getSpeed()*FloatMath.cos(graphic.getSpeed().getAngleRad()));
            	graphic.shiftY(graphic.getSpeed().getSpeed()*FloatMath.sin(graphic.getSpeed().getAngleRad()));
            	Func.borders(graphic, screen.getWidth(), screen.getHeight());
            }
            boolean AnyCollFound = false; //see if object is in a wpool
            for(Whirlpool whirl : wpoolModel.getWpools()){
            	if (whirl.Collision(graphic) == 1){
            		AnyCollFound = true;
            		if (graphic.GetPullState() == false) whirl.pull(graphic);
            	}
            	else if (whirl.Collision(graphic) == 2){
            		AnyCollFound = true;
            		if (graphic.GetPullState() == false) {
            			graphic.setAngle(whirl.getWAngle());
            			graphic.CantPull();
            		}
            	}
            }
            if (AnyCollFound == false)
            	graphic.CanPull();
        }
        angle1 = Func.calcAngle(_graphics.get(0).getX(), _graphics.get(0).getY(), x1, y1);
    }
}