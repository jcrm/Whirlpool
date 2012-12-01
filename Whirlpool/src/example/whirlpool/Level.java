package example.whirlpool;

import java.util.ArrayList;

import example.whirlpool.GraphicObject.objtype;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Level {
    Level(){
    	constructor();
    }
    
    //Level(other stuff){
    //	set other stuff
    //	constructor();
    //}
	
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
    	_graphics.add(new GraphicObject(objtype.tDuck));
        _graphics.add(new GraphicObject(objtype.tFrog));
        _graphics.add(new GraphicObject(objtype.tDiver));
    	_graphics.add(new GraphicObject(objtype.tShark));
        _graphics.add(new GraphicObject(objtype.tBoat));
    }
    
	void update(){
		for (GraphicObject graphic : _graphics) {
            // Move Objects
            if(graphic.move()){
            	Func.borders(graphic, levelWidth, Panel.sScreen.getHeight());
            }
            boolean lAnyCollFound = false; //see if object is in a wpool
            for(Whirlpool whirl : _wPoolModel.getWpools()){
            	if (whirl.collision(graphic) == 1){
            		lAnyCollFound = true;
            		if (graphic.getPullState() == false) whirl.pull(graphic);
            	}
            	else if (whirl.collision(graphic) == 2){
            		lAnyCollFound = true;
            		if (graphic.getPullState() == false) {
            			whirl.pull(graphic);
            			if (graphic.getSpeed().getAngle() > whirl.getWAngle()-3.0f &&
            			graphic.getSpeed().getAngle() < whirl.getWAngle()+3.0f){
            				graphic.setAngle(whirl.getWAngle());
            				graphic.cantPull();
            			}
            		}
            	}
            }
            if (lAnyCollFound == false){
            	graphic.canPull();
            }
        }
		scroll();
	}
	
	void onDraw(Canvas canvas){
		canvas.drawColor(Color.BLUE);
		canvas.translate(-scrollBy, 0.0f);
        for (Whirlpool whirlpool : _wPoolModel.getWpools()) {
        	whirlpool.getGraphic().draw(canvas);
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
	
}