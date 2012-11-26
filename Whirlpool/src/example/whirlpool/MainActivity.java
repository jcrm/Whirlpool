package example.whirlpool;

import java.util.ArrayList;
import example.whirlpool.GraphicObject.objtype;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
/*
 * Here is code  so far if need any explaining just let me know.
 * I've left in whirlpool code even though it cause the app to crash.
 * I have commented it out. 
 * You can delete it and the Whirlpool.java file until i've been able to fix it
 * 
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
        
    }
    static //creates class extended from surfaceview to draw to
    class Panel extends SurfaceView implements SurfaceHolder.Callback {
    	//private Whirlpool Whirl;
        private TutorialThread _thread;
        //an array list to store objects on screen
        private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
        //private ArrayList<Whirlpool> _whirlpools = new ArrayList<Whirlpool>();
        /** The application model */
        final WPools wpoolModel = new WPools();
        static public Screen screen = new Screen();
        static public Resources res;
        private Paint _paint = new Paint();
        
        //temp stuff
        float angle1 = 0.0f;
        float x1 = 0.0f;
        float y1 = 0.0f;
        //////
 
        public Panel(Context context) {
            super(context);
            getHolder().addCallback(this);
            _thread = new TutorialThread(getHolder(), this);
            setFocusable(true);
            setOnTouchListener(new TrackingTouchListener(wpoolModel,_thread));
            
            //wpoolModel.setWpoolsChangeListener(new WPools.WPoolsChangeListener() {
               //public void onDotsChange(Dots dots) {
                    //Dot d = dots.getLastDot();
                    //dotView.invalidate();
               //} });
        }
        //currently uses on down for splash needs to be changed to double tap
        //@Override
//        public boolean onTouchEvent(MotionEvent event){
//            synchronized (_thread.getSurfaceHolder()){
//                if(event.getAction() == MotionEvent.ACTION_DOWN){
//                	wpoolModel.addWPool(event.getX(), event.getY());
//                }
//            	x1 = event.getX();
//            	y1 = event.getY();
//                return true;
//            }
//        }
        /*
         *haven't been able to get it working yet
         *so here is the code anyway
         *the whirlpool code can be left out until i've fixed it
         **/
        //public void checkWhirl(MotionEvent event){
        	
        //}
        //changes direction of objects on screen if falls with in a circular distance from touch event
        public void changeDir(Whirlpool whirl){
        	//iterates through graphic list
        	for (GraphicObject graphic : _graphics){
        		//if shark randomise a new direction and speed
        		if(Func.circleCollision(graphic.getX(), graphic.getY(), graphic.getRadius(), whirl.getCentreX(), whirl.getCentreY(), whirl.getRadius())){
        			
        			
        			/*if(graphic.id==objtype.tShark){
            			graphic.getSpeed().setX(new Random().nextInt(5) + 1);
            			graphic.getSpeed().setY(new Random().nextInt(5) + 1);
            			graphic.getSpeed().setXDirection(new Random().nextInt(3)-1);
            			graphic.getSpeed().setYDirection(new Random().nextInt(3)-1);
        			}else if(graphic.id==objtype.tBoat){
        				//if boat reduce the speed
    					float tempX = graphic.getSpeed().getX();
        				float tempY = graphic.getSpeed().getY();
        				if(tempY > 0){
        					graphic.getSpeed().setY((float)(tempY-0.3));
        				}
        				if(tempX > 0){
        					graphic.getSpeed().setX((float)(tempX-0.3));
        				}
        			}*/
        		}
        	}
        }
        
        //initalise objects to screen
        public void init(){
        	screen.set(getWidth(), getHeight());
        	res = getResources();
        	//init duck to centre of screen
        	GraphicObject TheDuck =  new GraphicObject(objtype.tDuck);
        	
            _graphics.add(TheDuck);
            //for loop for init frog objects
            //for(int i = 0; i<(new Random().nextInt(2)+3); i++){
	          //  _graphics.add(new GraphicObject(objtype.tFrog));
            //}
            //for loop for init shark objects
            //for(int i = 0; i<(new Random().nextInt(10)+5); i++){
            	//_graphics.add(new GraphicObject(objtype.tShark));
            //}
            //for loop for init boat objects
           // for(int i = 0; i < (new Random().nextInt(5)+5); i++){
	          //  _graphics.add(new GraphicObject(objtype.tBoat));
	            
        	//}
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
                			whirl.pull(graphic);
                			if (graphic.getSpeed().getAngle() > whirl.getWAngle()-3.0f &&
                			graphic.getSpeed().getAngle() < whirl.getWAngle()+3.0f){
                				graphic.setAngle(whirl.getWAngle());
                				graphic.CantPull();
                			}
                		}
                	}
                }
                if (AnyCollFound == false)
                	graphic.CanPull();
            }
            angle1 = Func.calcAngle(_graphics.get(0).getX(), _graphics.get(0).getY(), x1, y1);
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
            
            
            _paint.setTextSize(20);
            _paint.setColor(Color.GREEN);
            /*if(_graphics.size() > 0)
            	canvas.drawText(String.format("%f", _graphics.get(0).getX()) + " " + String.format("%f", _graphics.get(0).getY()) + " " + String.format("%f", _graphics.get(0).getSpeed().getAngle()), 10, 50, _paint);
            if(_whirlpools.size() > 0)
            	canvas.drawText(String.format("%f", _whirlpools.get(0).getCentreX()) + " " + String.format("%f", _whirlpools.get(0).getCentreY()) + " " + String.format("%f", angle1), 10, 70, _paint);
       		*/
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
}