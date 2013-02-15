package logic;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.View;

public class Panel extends View{
	private static boolean _GameIsRunning;
    static public Screen sScreen = new Screen();
    static public Resources sRes;
    static MediaPlayer backgroundMusic;
    private static Object screenLock;
    
    public Panel(Context context) {
        super(context);
        constructor();
    }
    public Panel(Context context, AttributeSet attrs) {
        super(context, attrs);
        constructor();
    }
    private void constructor(){
        setFocusable(true);
        Constants.setScreen(sScreen);
        screenLock = Constants.getLock();
    }

    @Override
    public void onDraw(Canvas canvas) {
    	synchronized(screenLock){
    		Constants.getLevel().onDraw(canvas);
    	}
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
    	Imports.setImages();
    	Imports.setAudio();
    }
    
    public static void stopMusic(){
    	if(backgroundMusic != null){
    		backgroundMusic.stop();
    	}
    }
    
    

}