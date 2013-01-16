package logic;

import java.util.ArrayList;

import example.whirlpool.R;


import objects.Boat;
import objects.Diver;
import objects.Duck;
import objects.Frog;
import objects.GraphicObject;
import objects.Shark;
import objects.Whirlpool;
import objects.GraphicObject.objtype;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

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
	Bitmap backgroundImage;
	
	
	void init(){
		setLevelWidth(Panel.sScreen.getWidth() + 1000);
		backgroundImage = Imports.getBackground();
		_graphics.add(new Duck());
		Constants.setPlayer((Duck)_graphics.get(0));
		_graphics.add(new Frog());
		//_graphics.add(new Diver());
		//_graphics.add(new Shark());
		//_graphics.add(new Boat());
		
		paint.setColor(Color.RED);
		paint.setStyle(Paint.Style.FILL);
	}
	
	public void update(){
		for (GraphicObject graphic : _graphics){
			graphic.setPull(false);
			for(Whirlpool whirl : _wPoolModel.getWpools()){
				whirl.checkCollision(graphic);
			}
			
			if(graphic.getId()==objtype.tDuck){
				graphic.frame();	//Do everything this object does every frame, like move
				((Duck) graphic).changeCollisionType(graphic.getPullState());
				for(GraphicObject graphic2 : _graphics){
					((Duck) graphic).checkObjCollision(graphic2);
				}
			}else{
				graphic.frame();	//Do everything this object does every frame, like move
			}

		}
		for(int a = 0; a < _wPoolModel.getWpools().size(); a++){
			_wPoolModel.getWpools().get(a).frame();
			if(_wPoolModel.getWpools().get(a).getFinished()){
				_wPoolModel.getWpools().remove(a);
				a--;
			}
		}
		duckOnScreen();
		//scroll();
	}
	
	public void onDraw(Canvas canvas){
		//canvas.drawColor(Color.BLUE);
		
		int width, num;
		num = (int) Math.ceil((double)levelWidth/Constants.getScreen().getWidth());
		width = Constants.getScreen().getWidth();
		//Log.e("onDraw", String.valueOf(levelWidth) + "/" + String.valueOf(Constants.getScreen().getWidth()) + "=" + String.valueOf(num));
		for(int a = 0; a < (num); a++){
			rect.set((int) ((width*a)-scrollBy), 0, (int)((width*(a+1)) - scrollBy), Constants.getScreen().getHeight());
			canvas.drawBitmap(backgroundImage, null, rect,  null);
		}
		
		canvas.translate(-scrollBy, 0.0f);
		
		canvas.save();
		
		rect.set(0, 0, 5, canvas.getHeight());
		canvas.drawRect(rect, paint);
		
		canvas.translate(levelWidth-5, 0);
		
		rect.set(0, 0, 5, canvas.getHeight());
		canvas.drawRect(rect, paint);
		
		canvas.restore();
		
		
		for (Whirlpool whirlpool : _wPoolModel.getWpools()) {
			whirlpool.draw(canvas);
		}
		for (GraphicObject graphic : _graphics) {
			graphic.draw(canvas);
		}
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