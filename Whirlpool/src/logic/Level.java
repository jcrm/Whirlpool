package logic;

import java.util.ArrayList;


import objects.Boat;
import objects.Diver;
import objects.Duck;
import objects.Frog;
import objects.GraphicObject;
import objects.Shark;
import objects.Whirlpool;
import objects.GraphicObject.objtype;



import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Level {
    public Level(){
    	constructor();
    }
    
    private void constructor(){
    	//init();
    }
    
	private final WPools _wPoolModel = new WPools();
    private ArrayList<GraphicObject> _graphics = new ArrayList<GraphicObject>();
    private int levelWidth = 0;
    private float scrollBy = 0;
    private float scrollSpeed = 0;
    Paint paint = new Paint();
    Rect rect = new Rect();
    
    
    void init(){
    	setLevelWidth(Panel.sScreen.getWidth() + 500);
    	_graphics.add(new Duck());
    	Constants.setPlayer((Duck)_graphics.get(0));
        _graphics.add(new Frog());
        //_graphics.add(new Diver());
    	//_graphics.add(new Shark());
        //_graphics.add(new Boat());
    }
    
	public void update(){
		for (GraphicObject graphic : _graphics) { 
            boolean lAnyCollFound = false; //see if object is in a wpool
            for(Whirlpool whirl : _wPoolModel.getWpools()){
            	whirl.frame();
            	if (whirl.collision(graphic) == 1){
            		lAnyCollFound = true;
            		if (graphic.getPullState() == false) whirl.pull(graphic);
            		whirl.setExpireCounter(0);
            	}
            	else if (whirl.collision(graphic) == 2){
            		lAnyCollFound = true;
            		whirl.setCollisionDone(true);
            		if (graphic.getPullState() == false) {
            			whirl.pull(graphic);
            			if (graphic.getSpeed().getAngle() > whirl.getWAngle()-3.0f &&
            			graphic.getSpeed().getAngle() < whirl.getWAngle()+3.0f){
            				graphic.setAngle(whirl.getWAngle());
            			}
            		}
            	}
            }
            for(int a = 0; a < _wPoolModel.getWpools().size(); a++){
            	if(_wPoolModel.getWpools().get(a).isDone()){
            		_wPoolModel.getWpools().remove(a);
            	}
            }
            graphic.setPull(!lAnyCollFound);
            
            //duck every frame is different so added id check so that it can check collision against other objects
            if(graphic.getId()==objtype.tDuck){
            	graphic.frame();	//Do everything this object does every frame, like move
            	((Duck) graphic).changeCollisionType(lAnyCollFound);
            	for(GraphicObject graphic2 : _graphics){
            		((Duck) graphic).checkObjCollision(graphic2);
            	}
            }else{
            	graphic.frame();	//Do everything this object does every frame, like move
            }

        }
		duckOnScreen();
		//scroll();
	}
	
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLUE);
		canvas.translate(-scrollBy, 0.0f);
        for (Whirlpool whirlpool : _wPoolModel.getWpools()) {
        	whirlpool.draw(canvas);
        }
        for (GraphicObject graphic : _graphics) {
        	graphic.draw(canvas);
        }
        
    	rect.set(0, 0, 10, canvas.getHeight());
    	
    	paint.setColor(Color.RED);
    	paint.setStyle(Paint.Style.FILL);
    	
    	canvas.drawRect(rect, paint);
    	
    	canvas.translate(levelWidth-10, 0);
    	rect.set(0, 0, 10, canvas.getHeight());
    	
    	canvas.drawRect(rect, paint);
	}

	public WPools getWPoolModel() {
		return _wPoolModel;
	}
	public int getLevelWidth() {
		return levelWidth;
	}
	public void setLevelWidth(int levelWidth) {
		this.levelWidth = levelWidth;
	}
	public float getScrollBy() {
		return scrollBy;
	}
	public void setScrollBy(float scrollBy) {
		this.scrollBy = scrollBy;
	}
	public void shiftScrollBy(float a) {
		scrollSpeed += a/2.0f;
	}
	private void scroll(){
		scrollBy += scrollSpeed;
		if(scrollSpeed > 1.0f)
			scrollSpeed /= 1.5f;
		else
			scrollSpeed = 0.0f;
		if(scrollBy < 0){
			scrollBy = 0;
		}
		if(scrollBy + Panel.sScreen.getWidth() > levelWidth){
			scrollBy = levelWidth - Panel.sScreen.getWidth();
		}
		
	}
	public void duckOnScreen(){
		while(Constants.getPlayer().getX() >= (Panel.sScreen.getWidth()/2)+scrollBy){
			scrollBy++;
		}
		while(Constants.getPlayer().getX() < (Panel.sScreen.getWidth()/2)+scrollBy){
			scrollBy--;
		}
		if(scrollBy < 0){
			scrollBy = 0;
		}
		if(scrollBy + Panel.sScreen.getWidth() > levelWidth){
			scrollBy = levelWidth - Panel.sScreen.getWidth();
		}
	}
	
}